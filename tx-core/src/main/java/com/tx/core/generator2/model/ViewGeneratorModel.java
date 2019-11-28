/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.core.generator2.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.generator2.util.GeneratorUtils;
import com.tx.core.generator2.util.GeneratorUtils.EntityProperty;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 控制层生成模型<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ViewGeneratorModel {
    
    /** 视图类型 */
    private final ViewTypeEnum viewType;
    
    /** 所在包名 */
    private final String basePackage;
    
    /** 实体类型 */
    private final Class<?> entityType;
    
    /** 实体注释 */
    private final String entityComment;
    
    /** 实体类型name */
    private final String entityTypeName;
    
    /** 实体类型simpleName */
    private final String entityTypeSimpleName;
    
    /** jpa字段列表 */
    private final List<EntityProperty> propertyList;
    
    /** jpa字段列表 */
    private final List<EntityProperty> viewablePropertyList;
    
    /** 主键字段列表 */
    private final EntityProperty pkProperty;
    
    /** 是否有编码属性 */
    private EntityProperty codeProperty;
    
    /** 是否有是否有效的属性 */
    private EntityProperty validProperty;
    
    /** 父节点id对应的属性 */
    private EntityProperty parentIdProperty;
    
    /** <默认构造函数> */
    public ViewGeneratorModel(Class<?> entityType) {
        this(entityType, ViewTypeEnum.LIST);
    }
    
    /** <默认构造函数> */
    public ViewGeneratorModel(Class<?> entityType,
            ViewTypeEnum viewType) {
        super();
        String basePath = ClassUtils.convertClassNameToResourcePath(
                entityType.getName()) + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        this.basePackage = ClassUtils.convertResourcePathToClassName(basePath);
        
        this.viewType = viewType == null ? ViewTypeEnum.LIST : viewType;
        
        this.entityType = entityType;
        this.entityTypeName = entityType.getName();
        this.entityTypeSimpleName = entityType.getSimpleName();
        this.entityComment = GeneratorUtils.parseEntityComment(entityType);
        
        this.propertyList = GeneratorUtils.parseEntityPropertyList(entityType);
        this.pkProperty = this.propertyList.stream().filter(column -> {
            return column.isPrimaryKey();
        }).collect(Collectors.toList()).get(0);
        AssertUtils.isTrue(this.pkProperty != null, "没有找到主键字段");
        
        this.viewablePropertyList = this.propertyList.stream()
                .filter(column -> {
                    if (column.getTypeDescriptor()
                            .hasAnnotation(ApiIgnore.class)) {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
        
        Set<String> propertyNameSet = new HashSet<>();
        this.propertyList.stream().forEach(property -> {
            propertyNameSet.add(property.getPropertyName());
            if (StringUtils.equals("code", property.getPropertyName())
                    && !property.isPrimaryKey()) {
                //如果主键就是code，则无需标定hasCodeProperty
                this.codeProperty = property;
                AssertUtils.isTrue(
                        String.class
                                .isAssignableFrom(property.getPropertyType()),
                        "code type should is String.");
            } else if (StringUtils.equals("valid",
                    property.getPropertyName())) {
                this.validProperty = property;
                AssertUtils.isTrue(Boolean.class
                        .isAssignableFrom(property.getPropertyType())
                        || boolean.class.equals(property.getPropertyType()),
                        "valid type should is boolean or Boolean.");
            } else if (StringUtils.equals("parentId",
                    property.getPropertyName())) {
                this.parentIdProperty = property;
                AssertUtils.isTrue(
                        this.pkProperty.getPropertyType()
                                .equals(property.getPropertyType()),
                        "parentId type:{} should equals pk type:{}.",
                        new Object[] { property.getPropertyType(),
                                this.pkProperty.getPropertyType() });
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
     * @return 返回 entityComment
     */
    public String getEntityComment() {
        return entityComment;
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
     * @return 返回 propertyList
     */
    public List<EntityProperty> getPropertyList() {
        return propertyList;
    }
    
    /**
     * @return 返回 viewablePropertyList
     */
    public List<EntityProperty> getViewablePropertyList() {
        return viewablePropertyList;
    }
    
    /**
     * @return 返回 pkProperty
     */
    public EntityProperty getPkProperty() {
        return pkProperty;
    }
    
    /**
     * @return 返回 codeProperty
     */
    public EntityProperty getCodeProperty() {
        return codeProperty;
    }
    
    /**
     * @return 返回 validProperty
     */
    public EntityProperty getValidProperty() {
        return validProperty;
    }
    
    /**
     * @return 返回 parentIdProperty
     */
    public EntityProperty getParentIdProperty() {
        return parentIdProperty;
    }

    /**
     * @return 返回 viewType
     */
    public ViewTypeEnum getViewType() {
        return viewType;
    }
}
