/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月6日
 * <修改描述:>
 */
package com.tx.core.ddlutil.helper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;

import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.model.JPAEntityColumnDef;
import com.tx.core.ddlutil.model.JPAEntityTableDef;
import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * JPA实体表工具类<br/>
 *    该类的实现本来就不是制作一个JPA实现的替代，所以无需解析JPA中所有的特性。
 *    仅在部分特性支撑上就足够了。
 *    所以考虑无需采用hibernate-core包，或spring-data-jpa包中解析jpa注解的相关类
 *    并且减少对别的项目的jar包依赖，有利于该功能在后续版本升级时不会受到影响.
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class JPAEntityDDLHelper {
    
    /** 类型与表定义间的映射关联 */
    private static final Map<Class<?>, JPAEntityTableDef> TYPE_2_TABLEDEF_MAP = new HashMap<Class<?>, JPAEntityTableDef>();
    
    /**
     * 解析类型为表定义详细实例<br/>
     *    ：实例中将含有对应的索引以及字段和索引<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return TableDef [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static TableDef analyzeToTableDefDetail(Class<?> type,
            DDLDialect ddlDialect) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.notNull(ddlDialect, "ddlDialect is null.");
        
        if (TYPE_2_TABLEDEF_MAP.containsKey(type)) {
            return TYPE_2_TABLEDEF_MAP.get(type);
        }
        JPAEntityTableDef tableDef = doAnalyzeToTableDefDetail(type,
                ddlDialect);
        TYPE_2_TABLEDEF_MAP.put(type, tableDef);
        
        return tableDef;
    }
    
    /**
      * 解析类或接口生成对应的表定义详情(含字段以及索引)<br/>
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return JPAEntityTableDef [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static JPAEntityTableDef doAnalyzeToTableDefDetail(Class<?> type,
            DDLDialect ddlDialect) {
        JPAEntityTableDef tableDef = doAnalyzeTableDef(type);//解析表定义
        
        List<JPAEntityColumnDef> columnDefs = doAnalyzeCoumnDefs(
                tableDef.getTableName(), type, ddlDialect);//解析字段集合
        tableDef.setColumns(columnDefs);
        
        return tableDef;
    }
    
    /**
      * 从类定义中解析表定义<br/>
      *    仅解析传入类型：不对传入类型的接口，以及父类进行注解获取<br/>
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return JPAEntityTableDef [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static JPAEntityTableDef doAnalyzeTableDef(Class<?> type) {
        //没有注解的时候默认使用类名当作表名
        String tableName = type.getSimpleName();
        String comment = "";
        //如果存在javax注解中的Entity读取其Name
        //org.hibernate.annotations.Entity该类中不含有表名，不进行解析
        if (type.isAnnotationPresent(Entity.class)) {
            String entityName = type.getAnnotation(Entity.class).name();
            if (!StringUtils.isEmpty(entityName)) {
                tableName = entityName.toUpperCase();
            }
        }
        if (type.isAnnotationPresent(Table.class)) {
            //如果含有注解：javax.persistence.Table
            String annoTableName = type.getAnnotation(Table.class).name();
            if (!StringUtils.isEmpty(annoTableName)) {
                tableName = annoTableName.toUpperCase();
            }
        }
        if (type.isAnnotationPresent(org.hibernate.annotations.Table.class)) {
            //如果含有注解：javax.persistence.Table
            String annoTableComment = type
                    .getAnnotation(org.hibernate.annotations.Table.class)
                    .comment();
            if (!StringUtils.isEmpty(annoTableComment)) {
                comment = annoTableComment;
            }
        }
        
        JPAEntityTableDef tableDef = new JPAEntityTableDef();
        tableDef.setTableName(tableName);
        tableDef.setComment(comment);
        return tableDef;
    }
    
    /**
     * 解析字段集合定义<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return List<JPAEntityColumnDef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static List<JPAEntityColumnDef> doAnalyzeCoumnDefs(String tableName,
            Class<?> type, DDLDialect ddlDialect) {
        List<JPAEntityColumnDef> colDefList = new ArrayList<>();
        
        boolean hasPrimaryKey = false;
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type);
        for (PropertyDescriptor pd : pds) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                //writeMethod、readMethod都存在的属性才认为需要建为对象字段
                continue;
            }
            JPAEntityColumnDef colDef = doAnalyzeCoumnDef(tableName,
                    type,
                    pd,
                    ddlDialect);
            
            if (colDef != null) {
                colDefList.add(colDef);
                if (colDef.isPrimaryKey()) {
                    hasPrimaryKey = true;//如果已经有字段被设置为了主键
                }
            }
        }
        
        if (!hasPrimaryKey) {
            //如果没有主键：判断是否有id,code这样的字段
            //如果有id
            for (JPAEntityColumnDef col : colDefList) {
                if (StringUtils.equalsAnyIgnoreCase("id",
                        col.getColumnName())) {
                    col.setPrimaryKey(true);
                    col.setRequired(true);
                    hasPrimaryKey = true;
                    break;
                }
            }
        }
        if (!hasPrimaryKey) {
            //如果没有主键：判断是否有id,code这样的字段
            //如果有code
            for (JPAEntityColumnDef col : colDefList) {
                if (StringUtils.equalsAnyIgnoreCase("code",
                        col.getColumnName())) {
                    col.setPrimaryKey(true);
                    col.setRequired(true);
                    hasPrimaryKey = true;
                    break;
                }
            }
        }
        return colDefList;
    }
    
    /**
      * 解析字段定义<br>
      * <功能详细描述>
      * @param type
      * @param pd
      * @return [参数说明]
      * 
      * @return JPAEntityColumnDef [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static JPAEntityColumnDef doAnalyzeCoumnDef(String tableName,
            Class<?> type, PropertyDescriptor pd, DDLDialect ddlDialect) {
        JPAEntityColumnDef colDef = null;
        
        String columnComment = "";
        String propertyName = pd.getName();
        String columnName = propertyName;
        Class<?> javaType = pd.getPropertyType();
        int size = 255;
        int scale = 0;
        boolean required = false;
        boolean primaryKey = false;
        boolean hasAnnotation = false;
        //获取字段
        Field field = FieldUtils.getField(type, propertyName, true);
        if (field != null) {
            if (field.isAnnotationPresent(Transient.class)) {
                //如果含有忽略的字段则跳过该字段
                return null;
            }
            if (field.isAnnotationPresent(OneToMany.class)) {
                //如果含有忽略的字段则跳过该字段
                return null;
            }
            if (field.isAnnotationPresent(Column.class)) {
                hasAnnotation = true;
                Column columnAnno = field.getAnnotation(Column.class);
                columnName = StringUtils.isBlank(columnAnno.name()) ? columnName
                        : columnAnno.name();
                required = !columnAnno.nullable();
                size = Math.max(columnAnno.length(), columnAnno.precision());
                scale = columnAnno.scale();
            }
            if (field.isAnnotationPresent(Id.class)) {
                primaryKey = true;
            }
            if (field.isAnnotationPresent(
                    org.springframework.data.annotation.Id.class)) {
                primaryKey = true;
            }
            if (field.isAnnotationPresent(Primary.class)) {
                primaryKey = true;
            }
        }
        
        //获取读方法
        Method readMethod = pd.getReadMethod();
        if (readMethod != null) {
            if (readMethod.isAnnotationPresent(Transient.class)) {
                //如果含有忽略的字段则跳过该字段
                return null;
            }
            if (readMethod.isAnnotationPresent(OneToMany.class)) {
                //如果含有忽略的字段则跳过该字段
                return null;
            }
            if (readMethod.isAnnotationPresent(Column.class)) {
                hasAnnotation = true;
                Column columnAnno = readMethod.getAnnotation(Column.class);
                columnName = StringUtils.isBlank(columnAnno.name()) ? columnName
                        : columnAnno.name();
                required = !columnAnno.nullable();
                size = Math.max(columnAnno.length(), columnAnno.precision());
                scale = columnAnno.scale();
            }
            if (readMethod.isAnnotationPresent(Id.class)) {
                primaryKey = true;
            }
            if (readMethod.isAnnotationPresent(
                    org.springframework.data.annotation.Id.class)) {
                primaryKey = true;
            }
            if (readMethod.isAnnotationPresent(Primary.class)) {
                primaryKey = true;
            }
            if (readMethod.isAnnotationPresent(Primary.class)) {
                primaryKey = true;
            }
        }
        
        JdbcTypeEnum jdbcType = ddlDialect.getJdbcType(javaType);//获取对应的jdbcType
        if (!hasAnnotation) {
            int defaultSizeByType = ddlDialect.getDefaultLengthByType(javaType);
            int defaultScaleByType = ddlDialect.getDefaultScaleByType(javaType);
            size = defaultSizeByType >= 0 ? defaultSizeByType : size;
            scale = defaultScaleByType >= 0 ? defaultScaleByType : scale;
            
            int defaultSizeByName = ddlDialect.getDefaultLengthByName(javaType,
                    propertyName);
            int defaultScaleByName = ddlDialect.getDefaultScaleByName(javaType,
                    propertyName);
            size = defaultSizeByName >= 0 ? defaultSizeByName : size;
            scale = defaultScaleByName >= 0 ? defaultScaleByName : scale;
        }
        
        colDef = new JPAEntityColumnDef(columnName, javaType, jdbcType, size,
                scale, required, primaryKey);
        colDef.setComment(columnComment);
        
        return colDef;
    }
}
