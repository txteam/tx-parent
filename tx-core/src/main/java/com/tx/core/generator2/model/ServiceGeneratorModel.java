/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.core.generator2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.JPAParseUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceGeneratorModel {
    
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
    
    /** jpa字段列表 */
    private final List<JPAColumnInfo> pkColumnList;
    
    /** 主键字段列表 */
    private final JPAColumnInfo pkColumn;
    
    /** 是否有编码属性 */
    private JPAColumnInfo codeColumn;
    
    /** 是否有是否有效的属性 */
    private JPAColumnInfo validColumn;
    
    /** <默认构造函数> */
    public ServiceGeneratorModel(Class<?> entityType) {
        super();
        String basePath = ClassUtils.convertClassNameToResourcePath(
                entityType.getName()) + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        this.basePackage = ClassUtils.convertResourcePathToClassName(basePath);
        
        this.entityType = entityType;
        this.entityTypeName = entityType.getName();
        this.entityTypeSimpleName = entityType.getSimpleName();
        
        this.columnList = JPAParseUtils.parseTableColumns(entityType);
        this.pkColumnList = this.columnList.stream().filter(column -> {
            return column.isPrimaryKey();
        }).collect(Collectors.toList());
        
        this.pkColumn = this.pkColumnList.get(0);
        AssertUtils.isTrue(this.pkColumn != null, "没有找到主键字段");
        AssertUtils.isTrue(
                String.class.isAssignableFrom(this.pkColumn.getPropertyType()),
                "主键字段应为String");
        
        this.columnList.stream().forEach(column -> {
            if (StringUtils.equals("code", column.getPropertyName())
                    && !column.isPrimaryKey()) {
                //如果主键就是code，则无需标定hasCodeProperty
                this.codeColumn = column;
                AssertUtils.isTrue(
                        String.class.isAssignableFrom(column.getPropertyType())
                                || boolean.class
                                        .equals(column.getPropertyType()),
                        "code type should is String.");
            } else if (StringUtils.equals("valid", column.getPropertyName())) {
                this.validColumn = column;
                AssertUtils.isTrue(
                        Boolean.class.isAssignableFrom(column.getPropertyType())
                                || boolean.class
                                        .equals(column.getPropertyType()),
                        "valid type should is boolean or Boolean.");
            }
        });
    }
    
    /**
     * @return 返回 basePackage
     */
    public String getBasePackage() {
        return basePackage;
    }
    
    /**
     * @return 返回 entityType
     */
    public Class<?> getEntityType() {
        return entityType;
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
    
    /**
     * @return 返回 pkColumnList
     */
    public List<JPAColumnInfo> getPkColumnList() {
        return pkColumnList;
    }

    /**
     * @return 返回 codeColumn
     */
    public JPAColumnInfo getCodeColumn() {
        return codeColumn;
    }

    /**
     * @return 返回 validColumn
     */
    public JPAColumnInfo getValidColumn() {
        return validColumn;
    }
}
