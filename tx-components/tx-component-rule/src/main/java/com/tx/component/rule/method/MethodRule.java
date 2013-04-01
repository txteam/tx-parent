/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.lang.reflect.Method;

import com.tx.component.rule.method.annotation.RuleMethod;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;

/**
 * 方法类型的规则<br/>
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodRule implements Rule {
    
    /** 注释内容 */
    private static final long serialVersionUID = 8734799947265605205L;
    
    private Method method;
    
    private Object object;
    
    private com.tx.component.rule.method.annotation.RuleMethod ruleAnnotation;
    
    /** 数据库中存储的规则实体 */
    private SimplePersistenceRule simplePersistenceRule;
    
    /**
     * 方法类型规则构造函数
     */
    public MethodRule(Method method,
            Object object, RuleMethod ruleAnnotation) {
        super();
        this.method = method;
        this.object = object;
        this.ruleAnnotation = ruleAnnotation;
    }
    
    /**
     * 方法类型规则构造函数
     */
    public MethodRule(SimplePersistenceRule simplePersistenceRule) {
        super();
        this.simplePersistenceRule = simplePersistenceRule;
    }
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return this.simplePersistenceRule.getId();
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return ruleAnnotation.name();
    }
    
    /**
     * @return
     */
    @Override
    public String rule() {
        return ruleAnnotation.rule();
    }
    
    /**
     * @return
     */
    @Override
    public String getServiceType() {
        return ruleAnnotation.serviceType();
    }
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.METHOD;
    }
    
    /**
     * @return 返回 method
     */
    public Method getMethod() {
        return method;
    }
    
    /**
     * @param 对method进行赋值
     */
    public void setMethod(Method method) {
        this.method = method;
    }
    
    /**
     * @return 返回 ruleAnnotation
     */
    public com.tx.component.rule.method.annotation.RuleMethod getRuleAnnotation() {
        return ruleAnnotation;
    }
    
    /**
     * @param 对ruleAnnotation进行赋值
     */
    public void setRuleAnnotation(
            com.tx.component.rule.method.annotation.RuleMethod ruleAnnotation) {
        this.ruleAnnotation = ruleAnnotation;
    }
    
    /**
     * @return 返回 object
     */
    public Object getObject() {
        return object;
    }
    
    /**
     * @param 对object进行赋值
     */
    public void setObject(Object object) {
        this.object = object;
    }
    
    /**
     * @return 返回 state
     */
    public RuleStateEnum getState() {
        if(this.simplePersistenceRule == null){
            return RuleStateEnum.ERROR;
        }else{
            return this.simplePersistenceRule.getState();
        }
    }
    
    /**
     * @param 对state进行赋值
     */
    public void setState(RuleStateEnum state) {
        if (state != null && this.simplePersistenceRule != null
                && !state.equals(this.simplePersistenceRule.getState())) {
            this.simplePersistenceRule.setState(state);
        }
    }

    /**
     * @return 返回 simplePersistenceRule
     */
    public SimplePersistenceRule getSimplePersistenceRule() {
        return simplePersistenceRule;
    }

    /**
     * @param 对simplePersistenceRule进行赋值
     */
    public void setSimplePersistenceRule(SimplePersistenceRule simplePersistenceRule) {
        this.simplePersistenceRule = simplePersistenceRule;
    }
}
