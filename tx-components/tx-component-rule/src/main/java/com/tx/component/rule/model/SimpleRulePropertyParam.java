/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-28
 * <修改描述:>
 */
package com.tx.component.rule.model;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum SimpleRulePropertyParam {
    
    /**
     * DROOLS_DRL_BYTE属性：
     *     持久化到数据库的规则资源
     */
    DROOLS_DRL_BYTE_RESOURCE_CONTENT_BYTE(RuleTypeEnum.DROOLS_DRL_BYTE,
            "RES_CONTENT_BYTE", "", "", false, true),
    /**
     * DROOLS_PKG_URL属性：
     *      加载规则的url路径(存储相对路径)
     */
    DROOLS_PKG_URL(RuleTypeEnum.DROOLS_DRL_BYTE, "RES_CONTENT_BYTE", "", "", false,
            true),
    /**
     * Collection属性：
     *     持久化到数据库的规则资源
     */
    COLLECTION_QUOTE_RULE(RuleTypeEnum.COLLECTION, "QUOTE_RULE", "", "", true,
            false);
    
    /**
     * 规则类型
     */
    private RuleTypeEnum ruleType;
    
    /** 关键字 */
    private String key;
    
    /**
     * 合法性验证表达式
     */
    private String validatorExpression;
    
    /**
     * 合法性验证提示信息
     */
    private String validatorMessage;
    
    /** 
     * 是否是多值属性 
     * 是否是一对多属性
     */
    private boolean isMultiProerty = false;
    
    /**
     * 是否需要以二进制进行存储
     */
    private boolean isBlob = false;
    
    /**
     * 规则属性构造函数
     */
    private SimpleRulePropertyParam(RuleTypeEnum ruleType, String key,
            String validatorExpression, String validatorMessage,
            boolean isMultiProerty, boolean isBlob) {
        this.ruleType = ruleType;
        this.key = key;
        this.validatorExpression = validatorExpression;
        this.validatorMessage = validatorMessage;
        this.isMultiProerty = isMultiProerty;
        this.isBlob = isBlob;
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
     * @return 返回 validatorExpression
     */
    public String getValidatorExpression() {
        return validatorExpression;
    }
    
    /**
     * @param 对validatorExpression进行赋值
     */
    public void setValidatorExpression(String validatorExpression) {
        this.validatorExpression = validatorExpression;
    }
    
    /**
     * @return 返回 validatorMessage
     */
    public String getValidatorMessage() {
        return validatorMessage;
    }
    
    /**
     * @param 对validatorMessage进行赋值
     */
    public void setValidatorMessage(String validatorMessage) {
        this.validatorMessage = validatorMessage;
    }
    
    /**
     * @return 返回 isMultiProerty
     */
    public boolean isMultiProerty() {
        return isMultiProerty;
    }
    
    /**
     * @param 对isMultiProerty进行赋值
     */
    public void setMultiProerty(boolean isMultiProerty) {
        this.isMultiProerty = isMultiProerty;
    }
    
    /**
     * @return 返回 isBlob
     */
    public boolean isBlob() {
        return isBlob;
    }
    
    /**
     * @param 对isBlob进行赋值
     */
    public void setBlob(boolean isBlob) {
        this.isBlob = isBlob;
    }
    
    /**
     * @return 返回 ruleType
     */
    public RuleTypeEnum getRuleType() {
        return ruleType;
    }
    
    /**
     * @param 对ruleType进行赋值
     */
    public void setRuleType(RuleTypeEnum ruleType) {
        this.ruleType = ruleType;
    }
}
