/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月26日
 * <修改描述:>
 */
package com.tx.component.security.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 权限容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年2月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class SecurityContextConfigurator
        implements InitializingBean, ApplicationContextAware {
    
    /** 日志记录器 */
    protected static final Logger logger = LoggerFactory
            .getLogger(SecurityContextConfigurator.class);
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /**
     * @param arg0
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SecurityContextConfigurator.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("权限控制容器开始构建: ......");
        
        //进行容器构建
        doBuild();
        
        //初始化容器
        doInitContext();
        
        logger.info("权限控制容器构建完成: ......");
    }
    
    /**
     * 基础数据容器构建<br/>
     */
    protected abstract void doBuild() throws Exception;
    
    /**
     * 容器初始化<br/>
     */
    protected abstract void doInitContext() throws Exception;
    
}
