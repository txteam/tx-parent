package com.tx.component.servicelog.context;

import java.beans.PropertyDescriptor;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
  * 日志容器<br/>
  * 1、用以提供业务日志记录功能
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-17]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class LoggerContext implements FactoryBean<LoggerContext>,
        InitializingBean {
    
    /** 懒汉模式获取日志容器句柄 */
    private static LoggerContext context = new LoggerContext();
    
    private static final String[] loggerInterfacePath = { "com.boda.web.controller" };
    
    /** 用以冲在配置属性使用 */
    private PropertyDescriptor properties;

    /**
     * 私有化构造方法
     */
    private LoggerContext() {
        super();
        
    }
    
    private void setContext(LoggerContext context) {
        LoggerContext.context = context;
    }
    
    /**
     * 获取日志容器
     * 
     * @return
     */
    public static LoggerContext getContext() {
        return LoggerContext.context;
    }
    
    /**
     * 初始化读取配置文件
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //reLoadConfig();
        
        setContext(this);
    }
    
    @Override
    public LoggerContext getObject() throws Exception {
        return LoggerContext.context;
    }
    
    @Override
    public Class<?> getObjectType() {
        return LoggerContext.class;
    }
    
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * LoggerInterface配置类是配置配置在类上还是在方法上
     * 
     * @author liujun
     * */
    public enum loggerInterfaceEnum {
        clazz, method
    }
}
