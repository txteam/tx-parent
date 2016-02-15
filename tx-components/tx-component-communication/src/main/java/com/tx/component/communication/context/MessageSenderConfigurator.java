/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年11月12日
 * <修改描述:>
 */
package com.tx.component.communication.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.communication.exception.MessageSenderContextInitException;

/**
 * 消息路由服务配置容器<br/>
 * 
 * @author Rain.he
 * @version [版本号, 2015年5月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageSenderConfigurator implements InitializingBean,
        ApplicationContextAware {
    
    /** 日志 */
    protected static final Logger logger = LoggerFactory.getLogger(MessageSenderContext.class);
    
    /** springContext */
    protected ApplicationContext applicationContext;
    
    /** 消息发送拦截器 */
    protected MessageSenderInterceptor interceptors;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
