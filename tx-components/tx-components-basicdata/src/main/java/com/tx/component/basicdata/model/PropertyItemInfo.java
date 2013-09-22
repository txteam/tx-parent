/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import com.tx.component.basicdata.valuegenerator.ValueGenerator;


 /**
  * 属性项信息<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class PropertyItemInfo {
    
    /** 字段对应的java类型 */
    private Class<?> type;
    
    /** 属性界面展示名称 */
    private String name;
    
    /** 属性项label名 */
    private String propertyName;
    
    /** 属性对应字段名 */
    private String column;
    
    /** 对应属性是否可插入 */
    private boolean insertAble;
    
    /** 对应属性是否可更新 */
    private boolean updateAble;
    
    /** 属性项在列表中是否隐藏 */
    private boolean hidden = false;
    
    /** 是否是可见字段 */
    private boolean visibal = true;
    
    /** 属性项排序值 */
    private int order;
    
    /** 验证表达式 */
    private String validateExpression;
    
    /** 提示信息，字段验证前显示提示信息 */
    private String tipMessage;
    
    /** 字段验证错误信息 */
    private String errorMessage;
    
    /** 是否可以编辑 */
    private boolean modifyAble;
    
    /** 字段值生成器 */
    private ValueGenerator<?> valueGenerator;

    /**
     * @return 返回 type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(Class<?> type) {
        this.type = type;
    }

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 返回 propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param 对propertyName进行赋值
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * @return 返回 column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param 对column进行赋值
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * @return 返回 insertAble
     */
    public boolean isInsertAble() {
        return insertAble;
    }

    /**
     * @param 对insertAble进行赋值
     */
    public void setInsertAble(boolean insertAble) {
        this.insertAble = insertAble;
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

    /**
     * @return 返回 hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @param 对hidden进行赋值
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return 返回 visibal
     */
    public boolean isVisibal() {
        return visibal;
    }

    /**
     * @param 对visibal进行赋值
     */
    public void setVisibal(boolean visibal) {
        this.visibal = visibal;
    }

    /**
     * @return 返回 order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param 对order进行赋值
     */
    public void setOrder(int order) {
        this.order = order;
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
     * @return 返回 tipMessage
     */
    public String getTipMessage() {
        return tipMessage;
    }

    /**
     * @param 对tipMessage进行赋值
     */
    public void setTipMessage(String tipMessage) {
        this.tipMessage = tipMessage;
    }

    /**
     * @return 返回 errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param 对errorMessage进行赋值
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return 返回 modifyAble
     */
    public boolean isModifyAble() {
        return modifyAble;
    }

    /**
     * @param 对modifyAble进行赋值
     */
    public void setModifyAble(boolean modifyAble) {
        this.modifyAble = modifyAble;
    }

    /**
     * @return 返回 valueGenerator
     */
    public ValueGenerator<?> getValueGenerator() {
        return valueGenerator;
    }

    /**
     * @param 对valueGenerator进行赋值
     */
    public void setValueGenerator(ValueGenerator<?> valueGenerator) {
        this.valueGenerator = valueGenerator;
    }
}
