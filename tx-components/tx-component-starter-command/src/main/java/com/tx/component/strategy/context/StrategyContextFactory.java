/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.strategy.context;

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
public class StrategyContextFactory extends StrategyContext implements
        FactoryBean<StrategyContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public StrategyContext getObject() throws Exception {
        if (StrategyContext.context == null) {
            return this;
        } else {
            return StrategyContext.context;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return StrategyContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
