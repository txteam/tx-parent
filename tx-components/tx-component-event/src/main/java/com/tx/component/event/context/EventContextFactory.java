/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月17日
 * <修改描述:>
 */
package com.tx.component.event.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 事件容器工厂<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventContextFactory extends EventContext implements
        FactoryBean<EventContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public EventContext getObject() throws Exception {
        return getContext();
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return EventContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
