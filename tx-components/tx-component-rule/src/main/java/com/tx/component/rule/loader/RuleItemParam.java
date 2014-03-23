/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.rule.loader;

/**
 * 规则类型参数<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleItemParam {
    
    /** 关键字 */
    private String key;
    
    /** 参数类型 */
    private RuleItemParamTypeEnum type;
    
    /** 
     * 是否是多值属性 
     * 是否是一对多属性
     */
    private boolean multiple = false;
    
    /**
     * 是否是必填项
     */
    private boolean required = false;
    
    /**
     * 合法性验证表达式
     */
    private String validateExpression;
    
    /**
     * 合法性验证提示信息
     */
    private String errorMessage;
    
    /** <默认构造函数> */
    public RuleItemParam() {
        super();
    }
    
    /** <默认构造函数> */
    public RuleItemParam(String key, RuleItemParamTypeEnum type,
            boolean multiple, boolean required, String validateExpression,
            String errorMessage) {
        super();
        this.key = key;
        this.type = type;
        this.multiple = multiple;
        this.required = required;
        this.validateExpression = validateExpression;
        this.errorMessage = errorMessage;
    }
    
    /** <默认构造函数> */
    public RuleItemParam(
            RuleItemParamEnumInterface<?> ruleItemParamEnumInterface) {
        super();
        this.key = ruleItemParamEnumInterface.toString();
        this.type = ruleItemParamEnumInterface.getType();
        this.multiple = ruleItemParamEnumInterface.isMultiple();
        this.validateExpression = ruleItemParamEnumInterface.getValidateExpression();
        this.errorMessage = ruleItemParamEnumInterface.getErrorMessage();
        this.required = ruleItemParamEnumInterface.isRequired();
    }
    
    /**
     * @return 返回 type
     */
    public RuleItemParamTypeEnum getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(RuleItemParamTypeEnum type) {
        this.type = type;
    }
    
    /**
     * @return 返回 key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * @param 对key进行赋值
     */
    public void setKey(String key) {
        this.key = key;
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
     * @return 返回 multiple
     */
    public boolean isMultiple() {
        return multiple;
    }
    
    /**
     * @param 对multiple进行赋值
     */
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
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
}
