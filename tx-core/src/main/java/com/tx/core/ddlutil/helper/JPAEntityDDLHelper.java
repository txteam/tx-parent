/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月6日
 * <修改描述:>
 */
package com.tx.core.ddlutil.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.model.JPAEntityColumnDef;
import com.tx.core.ddlutil.model.JPAEntityTableDef;
import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.JPAParseUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

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
        String tableName = JPAParseUtils.parseTableName(type);
        String comment = "";
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
        for (JPAColumnInfo column : JPAParseUtils.parseTableColumns(type)) {
            JPAEntityColumnDef colDef = doAnalyzeCoumnDef(tableName,
                    type,
                    column,
                    ddlDialect);
            colDefList.add(colDef);
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
            Class<?> type, JPAColumnInfo column, DDLDialect ddlDialect) {
        JPAEntityColumnDef colDef = null;
        
        String columnComment = "";
        String propertyName = column.getNestedPropertyName();
        Class<?> javaType = column.getNestedPropertyType();
        String columnName = column.getColumnName();
        boolean required = !column.isNullable();
        boolean primaryKey = column.isPrimaryKey();
        JdbcTypeEnum jdbcType = ddlDialect.getJdbcType(javaType);//获取对应的jdbcType
        
        int size = 255;
        int scale = 0;
        
        //根据类型取默认值
        {
            int defaultSizeByType = ddlDialect.getDefaultLengthByType(javaType);
            int defaultScaleByType = ddlDialect.getDefaultScaleByType(javaType);
            size = defaultSizeByType >= 0 ? defaultSizeByType : size;
            scale = defaultScaleByType >= 0 ? defaultScaleByType : scale;
        }
        
        //根据名称取值+
        {
            int defaultSizeByName = ddlDialect.getDefaultLengthByName(javaType,
                    propertyName);
            int defaultScaleByName = ddlDialect.getDefaultScaleByName(javaType,
                    propertyName);
            size = defaultSizeByName >= 0 ? defaultSizeByName : size;
            scale = defaultScaleByName >= 0 ? defaultScaleByName : scale;
        }
        
        //如果存在注解则根据注解取值
        if (column.getColumnAnnotation() != null) {
            size = column.getPrecision() > 0 ? column.getPrecision() : size;//次优先使用pricision的值
            size = column.getLength() != 255 ? column.getLength() : size;//优先使用length值
            //在注解中scale默认为0,此处判断如果>0,就说明一定通过注解写入了其他值，并且我们前面的逻辑已经让默认值为0了，如果此处==0，则使用上面得到的值即可
            scale = column.getScale() > 0 ? column.getScale() : size;
        }
        
        colDef = new JPAEntityColumnDef(columnName, javaType, jdbcType, size,
                scale, required, primaryKey);
        colDef.setComment(columnComment);
        
        return colDef;
    }
}
