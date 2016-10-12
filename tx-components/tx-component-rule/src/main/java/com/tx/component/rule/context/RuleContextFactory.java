/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.rule.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 规则容器工厂类
 *     注入spring容器后，长生对应的规则容器工厂<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleContextFactory extends RuleContext implements
        FactoryBean<RuleContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public RuleContext getObject() throws Exception {
        return RuleContext.getContext(this.beanName);
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return RuleContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
