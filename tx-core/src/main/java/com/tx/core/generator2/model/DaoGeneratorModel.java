/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.core.generator2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.ClassUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.JPAParseUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * 持久层代码生成模型<br/>
 *     用以支持*Dao,*DaoImpl实现的生成<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DaoGeneratorModel {
    
    /** 所在包名 */
    private final String basePackage;
    
    /** 实体类型 */
    private final Class<?> entityType;
    
    /** 实体类型name */
    private final String entityTypeName;
    
    /** 实体类型simpleName */
    private final String entityTypeSimpleName;
    
    /** jpa字段列表 */
    private final List<JPAColumnInfo> columnList;
    
    /** 主键字段列表 */
    private final JPAColumnInfo pkColumn;
    
    /** <默认构造函数> */
    public DaoGeneratorModel(Class<?> entityType) {
        super();
        this.entityType = entityType;
        
        String basePath = ClassUtils.convertClassNameToResourcePath(
                entityType.getName()) + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        this.basePackage = ClassUtils.convertResourcePathToClassName(basePath);
        
        this.entityTypeName = entityType.getName();
        this.entityTypeSimpleName = entityType.getSimpleName();
        
        this.columnList = JPAParseUtils.parseTableColumns(entityType);
        this.pkColumn = this.columnList.stream().filter(column -> {
            return column.isPrimaryKey();
        }).collect(Collectors.toList()).get(0);
        AssertUtils.isTrue(this.pkColumn != null, "没有找到主键字段");
    }
    
    /**
     * @return 返回 entityType
     */
    public Class<?> getEntityType() {
        return entityType;
    }
    
    /**
     * @return 返回 basePackage
     */
    public String getBasePackage() {
        return basePackage;
    }
    
    /**
     * @return 返回 entityTypeName
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    /**
     * @return 返回 entityTypeSimpleName
     */
    public String getEntityTypeSimpleName() {
        return entityTypeSimpleName;
    }

    /**
     * @return 返回 columnList
     */
    public List<JPAColumnInfo> getColumnList() {
        return columnList;
    }

    /**
     * @return 返回 pkColumn
     */
    public JPAColumnInfo getPkColumn() {
        return pkColumn;
    }
    
    
}
