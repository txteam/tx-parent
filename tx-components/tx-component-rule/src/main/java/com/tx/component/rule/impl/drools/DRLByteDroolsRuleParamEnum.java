/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月17日
 * <修改描述:>
 */
package com.tx.component.rule.impl.drools;

import com.tx.component.rule.loader.RuleItemParamEnumInterface;
import com.tx.component.rule.loader.RuleItemParamTypeEnum;

/**
 * 方法类型规则
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum DRLByteDroolsRuleParamEnum implements RuleItemParamEnumInterface<DRLByteDroolsRule> {
    /** 对应规则项bean实例 */
    DRL_BYTE(RuleItemParamTypeEnum.BYTE, false,true, null,"");
    
    /** 规则项目参数类型 */
    private RuleItemParamTypeEnum type;
    
    /** 规则项是否为多值 */
    private boolean multiple;
    
    /** 规则项目参数验证表达式 */
    private String validateExpression;
    
    /** 规则项目参数验证表达式，错误提示信息 */
    private String errorMessage;
    
    /** 是否必填 */
    private boolean required;
    
    /** <默认构造函数> */
    private DRLByteDroolsRuleParamEnum(RuleItemParamTypeEnum type, boolean multiple,boolean required,
            String validateExpression, String errorMessage) {
        this.type = type;
        this.multiple = multiple;
        this.validateExpression = validateExpression;
        this.errorMessage = errorMessage;
    }
    
    /**
     * @return
     */
    @Override
    public RuleItemParamTypeEnum getType() {
        return this.type;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isMultiple() {
        return this.multiple;
    }
    
    /**
     * @return
     */
    @Override
    public String getValidateExpression() {
        return this.validateExpression;
    }
    
    /**
     * @return
     */
    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * @return 返回 required
     */
    public boolean isRequired() {
        return required;
    }
    
}
