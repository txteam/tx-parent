/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.communication.context;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 
 * 消息路由服务工厂
 * 
 * @author Rain.he
 * @version [版本号, 2015年11月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component("mrs.context")
public class CommunicationContextFactory extends CommunicationContextContext implements FactoryBean<CommunicationContextContext> {
    
    @Override
    public CommunicationContextContext getObject() throws Exception {
        return CommunicationContextContext.getContext();
    }
    
    @Override
    public Class<?> getObjectType() {
        return CommunicationContextContext.class;
    }
    
    @Override
    public boolean isSingleton() {
        return true;
    }
}
