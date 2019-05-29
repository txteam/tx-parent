/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月29日
 * <修改描述:>
 */
package com.tx.core.generator2.util;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.generator.annotation.Comment;
import com.tx.core.util.JPAParseUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 生成工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GeneratorUtils {
    
    /**
     * 解析注释<br/>
     * <功能详细描述>
     * @param beanType
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String parseEntityComment(Class<?> beanType) {
        AssertUtils.notNull(beanType, "beanType is null.");
        String entityComment = beanType.getSimpleName();
        if (beanType.isAnnotationPresent(ApiModel.class)) {
            ApiModel anno = beanType.getAnnotation(ApiModel.class);
            if (StringUtils.isNotBlank(anno.value())) {
                entityComment = anno.value();
                return entityComment;
            } else if (StringUtils.isNotBlank(anno.description())) {
                entityComment = anno.description();
                return entityComment;
            }
        }
        if (beanType.isAnnotationPresent(Comment.class)) {
            Comment anno = beanType.getAnnotation(Comment.class);
            if (StringUtils.isNotBlank(anno.value())) {
                entityComment = anno.value();
                return entityComment;
            }
        }
        return entityComment;
    }
    
    /**
     * 解析表字段<br/>
     * <功能详细描述>
     * @param beanType
     * @param jpaPropertySimpleTypeFilter
     * @return [参数说明]
     * 
     * @return List<JPAProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<EntityProperty> parseEntityPropertyList(
            Class<?> beanType) {
        AssertUtils.notNull(beanType, "beanType is null.");
        
        //解析jpa属性
        List<JPAColumnInfo> columns = JPAParseUtils.parseTableColumns(beanType);
        List<EntityProperty> propertyList = columns.stream().map(column -> {
            return new EntityProperty(column);
        }).collect(Collectors.toList());
        return propertyList;
    }
    
    public static class EntityProperty extends JPAColumnInfo {
        
        /** <默认构造函数> */
        public EntityProperty(JPAColumnInfo column) {
            super(column.getPropertyDescriptor(), column.getTypeDescriptor());
            
            setPrimaryKey(column.isPrimaryKey());
        }
        
        /**
         * 获取字段描述<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return String [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        public String getPropertyComment() {
            String propertyComment = getPropertyName();
            if (this.typeDescriptor.hasAnnotation(ApiModelProperty.class)) {
                ApiModelProperty anno = this.typeDescriptor
                        .getAnnotation(ApiModelProperty.class);
                if (StringUtils.isNotBlank(anno.value())) {
                    propertyComment = anno.value();
                    return propertyComment;
                }
            }
            if (this.typeDescriptor.hasAnnotation(Comment.class)) {
                Comment anno = this.typeDescriptor.getAnnotation(Comment.class);
                if (StringUtils.isNotBlank(anno.value())) {
                    propertyComment = anno.value();
                    return propertyComment;
                }
            }
            
            switch (propertyComment) {
                case "id":
                    propertyComment = "主键";
                    break;
                case "parentId":
                    propertyComment = "上级ID";
                    break;
                case "code":
                    propertyComment = "编码";
                    break;
                case "type":
                    propertyComment = "类型";
                    break;
                case "catalog":
                    propertyComment = "分类";
                    break;
                case "name":
                    propertyComment = "名称";
                    break;
                case "value":
                    propertyComment = "值";
                    break;
                case "valid":
                    propertyComment = "是否有效";
                    break;
                case "modifyAble":
                    propertyComment = "是否可编辑";
                    break;
                case "remark":
                    propertyComment = "备注";
                    break;
                case "description":
                    propertyComment = "描述";
                    break;
                case "createDate":
                    propertyComment = "创建时间";
                    break;
                case "createOperatorId":
                    propertyComment = "创建人ID";
                    break;
                case "lastUpdateDate":
                    propertyComment = "最后更新时间";
                    break;
                case "lastUpdateOperatorId":
                    propertyComment = "最后更新人ID";
                    break;
                case "effictiveDate":
                    propertyComment = "生效时间";
                    break;
                case "expiryDate":
                    propertyComment = "到期时间";
                    break;
            }
            return propertyComment;
        }
    }
}
