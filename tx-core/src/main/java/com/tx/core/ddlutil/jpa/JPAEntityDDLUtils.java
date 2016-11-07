/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月6日
 * <修改描述:>
 */
package com.tx.core.ddlutil.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;

import com.tx.core.ddlutil.model.JPAEntityColumnDef;
import com.tx.core.ddlutil.model.JPAEntityIndexDef;
import com.tx.core.ddlutil.model.JPAEntityTableDef;
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
public abstract class JPAEntityDDLUtils {
    
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
    public static TableDef parseClassToTableDefDetail(Class<?> type) {
        AssertUtils.notNull(type, "type is null.");
        
        if (TYPE_2_TABLEDEF_MAP.containsKey(type)) {
            return TYPE_2_TABLEDEF_MAP.get(type);
        }
        JPAEntityTableDef tableDef = doParseClassToTableDefDetail(type);
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
    private static JPAEntityTableDef doParseClassToTableDefDetail(Class<?> type) {
        
        JPAEntityTableDef tableDef = doParseTableDef(type);
        
        List<JPAEntityColumnDef> columnDefs = doParseCoumnDefs(type);
        List<JPAEntityIndexDef> indexDefs = doParseCoumnIndexes(type);
        
        tableDef.setColumns(columnDefs);
        tableDef.setIndexes(indexDefs);
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
    private static JPAEntityTableDef doParseTableDef(Class<?> type) {
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
            String annoTableComment = type.getAnnotation(org.hibernate.annotations.Table.class)
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
    
    private static List<JPAEntityColumnDef> doParseCoumnDefs(Class<?> type) {
        
        return null;
    }
    
    private static List<JPAEntityIndexDef> doParseCoumnIndexes(Class<?> type) {
        
        return null;
    }
}
