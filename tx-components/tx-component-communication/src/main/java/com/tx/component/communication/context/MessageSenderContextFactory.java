/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.communication.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 
 * 消息路由服务工厂
 * 
 * @author Rain.he
 * @version [版本号, 2015年11月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageSenderContextFactory extends MessageSenderContext implements
        FactoryBean<MessageSenderContext> {
    
    @Override
    public MessageSenderContext getObject() throws Exception {
        if (MessageSenderContextFactory.context == null) {
            return this;
        } else {
            return MessageSenderContextFactory.context;
        }
    }
    
    @Override
    public Class<?> getObjectType() {
        return MessageSenderContext.class;
    }
    
    @Override
    public boolean isSingleton() {
        return true;
    }
}
