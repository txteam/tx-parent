/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 基础数据容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContextConfigurator
        implements ApplicationContextAware, InitializingBean {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(BasicDataContext.class);
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /** 包名 */
    protected String packages = "com.tx";
    
    /** 数据源:dataSource */
    protected DataSource dataSource;
    
    /** transactionManager */
    protected PlatformTransactionManager transactionManager;
    
    /** cacheManager */
    protected CacheManager cacheManager;
    
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
    
    protected void doInitContext() throws Exception {
        
    }
    
    /**
     * @param 对packages进行赋值
     */
    public void setPackages(String packages) {
        this.packages = packages;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * @param 对cacheManager进行赋值
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
}
