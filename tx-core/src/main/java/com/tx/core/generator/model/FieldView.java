/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.generator.model;



/**
 * 页面显示字段信息
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Deprecated
public class FieldView {
    
    /** 是否为主键字段 */
    private boolean id = false;
    
    /** 是佛能够被更新 */
    private boolean updateAble = false;
    
    /** 是否必填 */
    private boolean required = false;
    
    /** 是否为日期类型 */
    private boolean date = false;


    /** 
     * 是否为基本类型
     * 是否为typeHandle能够处理的类型  
     */
    private boolean simpleType;
    
    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段中文名称
     */
    private String fieldComment;
    
    /**
     * 字段类型名
     */
    private Class<?> javaType;
    
    /**
     * 外键字段名
     */
    private String foreignKeyFieldName;
    
    /** 验证表达式 */
    private String validateExpression;
    
    /**
     * @return 返回 date
     */
    public boolean isDate() {
        return date;
    }

    /**
     * @param 对date进行赋值
     */
    public void setDate(boolean date) {
        this.date = date;
    }

    /**
     * @return 返回 required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param 对required进行赋值
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return 返回 id
     */
    public boolean isId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(boolean id) {
        this.id = id;
    }

    /**
     * @return 返回 simpleType
     */
    public boolean isSimpleType() {
        return simpleType;
    }

    /**
     * @param 对simpleType进行赋值
     */
    public void setSimpleType(boolean simpleType) {
        this.simpleType = simpleType;
    }

    /**
     * @return 返回 fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param 对fieldName进行赋值
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return 返回 javaType
     */
    public Class<?> getJavaType() {
        return javaType;
    }

    /**
     * @param 对javaType进行赋值
     */
    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    /**
     * @return 返回 foreignKeyFieldName
     */
    public String getForeignKeyFieldName() {
        return foreignKeyFieldName;
    }

    /**
     * @param 对foreignKeyFieldName进行赋值
     */
    public void setForeignKeyFieldName(String foreignKeyFieldName) {
        this.foreignKeyFieldName = foreignKeyFieldName;
    }

    /**
     * @return 返回 validateExpression
     */
    public String getValidateExpression() {
        return validateExpression;
    }

    /**
     * @param 对validateExpression进行赋值
     */
    public void setValidateExpression(String validateExpression) {
        this.validateExpression = validateExpression;
    }

    /**
     * @return 返回 updateAble
     */
    public boolean isUpdateAble() {
        return updateAble;
    }

    /**
     * @param 对updateAble进行赋值
     */
    public void setUpdateAble(boolean updateAble) {
        this.updateAble = updateAble;
    }



    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }
}
