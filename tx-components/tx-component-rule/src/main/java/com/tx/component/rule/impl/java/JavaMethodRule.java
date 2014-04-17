/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.impl.java;

import java.lang.reflect.Method;

import org.springframework.web.method.HandlerMethod;

import com.tx.component.rule.context.BaseRule;
import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleStateEnum;
import com.tx.component.rule.loader.RuleTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * JAVA方法类型的规则<br/>
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JavaMethodRule extends BaseRule {
    
    /** 注释内容 */
    private static final long serialVersionUID = 8734799947265605205L;
    
    /** 规则状态枚举 */
    private RuleStateEnum state;
    
    /** 规则方法 */
    private HandlerMethod handlerMethod;
    
    /** <默认构造函数> */
    public JavaMethodRule(RuleItem ruleItem) {
        super(ruleItem);
        AssertUtils.notNull(this.ruleItem, "ruleItem is null");
        
        //从规则项目中提取Object以及Method用以构建handlerMethod
        //如果提取失败，则状态为：
        Object bean = ruleItem.getObjectParam(JavaMethodRuleParamEnum.BEAN.toString());
        Object methodObject = ruleItem.getObjectParam(JavaMethodRuleParamEnum.METHOD.toString());
        //如果提取成功,则状态为:
        if (bean != null && methodObject != null
                && methodObject instanceof Method) {
            this.state = RuleStateEnum.OPERATION;
            this.handlerMethod = new HandlerMethod(bean, (Method) methodObject);
        } else {
            this.state = RuleStateEnum.ERROR;
        }
    }
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.JAVA_METHOD;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isModifyAble() {
        return false;
    }
    
    /**
     * @return
     */
    @Override
    public RuleStateEnum getState() {
        return this.state;
    }
    
    /**
     * @return 返回 handlerMethod
     */
    public HandlerMethod getHandlerMethod() {
        return handlerMethod;
    }
    
    /**
     * @param 对handlerMethod进行赋值
     */
    public void setHandlerMethod(HandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }
    
    /**
     * @param 对state进行赋值
     */
    public void setState(RuleStateEnum state) {
        this.state = state;
    }
}
