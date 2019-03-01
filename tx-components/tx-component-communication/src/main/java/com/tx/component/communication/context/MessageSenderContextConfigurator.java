/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年11月12日
 * <修改描述:>
 */
package com.tx.component.communication.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 消息路由服务配置容器<br/>
 * 
 * @author Rain.he
 * @version [版本号, 2015年5月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageSenderContextConfigurator implements InitializingBean,
        ApplicationContextAware {
    
    /** 日志 */
    protected static final Logger logger = LoggerFactory.getLogger(MessageSenderContext.class);
    
    /** springContext */
    protected static ApplicationContext applicationContext;
    
    /** 消息发送拦截器 */
    protected MessageSenderInterceptor interceptors;
    
    @Override
    public final void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        MessageSenderContextConfigurator.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        //进行容器构建
        doBuild();
        
        //初始化容器
        doInitContext();
    }
    
    /**
     * 基础数据容器构建
     * <功能详细描述>
     * @throws Exception [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void doBuild() throws Exception {
    }
    
    /**
      * 初始化容器<br/>
      * <功能详细描述>
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doInitContext() throws Exception {
    }
}
