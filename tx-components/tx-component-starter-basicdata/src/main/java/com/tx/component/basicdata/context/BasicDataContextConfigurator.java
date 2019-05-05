/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.basicdata.registry.BasicDataEntityRegistry;

/**
 * 基础数据容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BasicDataContextConfigurator
        implements ApplicationContextAware, InitializingBean {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(BasicDataContext.class);
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /** 注册表 */
    protected BasicDataEntityRegistry registry;
    
    /** <默认构造函数> */
    public BasicDataContextConfigurator() {
        super();
    }
    
    /** <默认构造函数> */
    public BasicDataContextConfigurator(BasicDataEntityRegistry registry) {
        super();
        this.registry = registry;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public final void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        BasicDataContextConfigurator.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        logger.info("基础数据容器开始构建: ......");
        
        //进行容器构建
        doBuild();
        
        //初始化容器
        doInitContext();
        
        logger.info("基础数据容器构建完成: ......");
    }
    
    /**
     * 基础数据容器构建<br/>
     */
    protected void doBuild() throws Exception {
        
    }
    
    /**
     * 容器初始化<br/>
     */
    protected void doInitContext() throws Exception {
        
    }
    
    /**
     * @return 返回 registry
     */
    public BasicDataEntityRegistry getRegistry() {
        return registry;
    }
    
    /**
     * @param 对registry进行赋值
     */
    public void setRegistry(BasicDataEntityRegistry registry) {
        this.registry = registry;
    }
    
}
