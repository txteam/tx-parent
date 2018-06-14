/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.core.mybatis.support.MyBatisDaoSupport;

/**
 * 实体持久层注册器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerFactory<T>
        implements FactoryBean<ServiceLogger<T>>, InitializingBean {
    
    private Logger logger = org.slf4j.LoggerFactory
            .getLogger(ServiceLoggerFactory.class);
    
    private Class<T> beanType;
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    private TransactionTemplate transactionTemplate;
    
    protected SqlSessionFactory sqlSessionFactory;
    
    protected Configuration configuration;
    
    private LoggerMapperBuilderAssistant assistant;
    
    private ServiceLogger<T> loggerService;
    
    /** <默认构造函数> */
    public ServiceLoggerFactory(Class<T> beanType,
            MyBatisDaoSupport myBatisDaoSupport,
            TransactionTemplate transactionTemplate) {
        super();
        this.beanType = beanType;
        this.myBatisDaoSupport = myBatisDaoSupport;
        this.transactionTemplate = transactionTemplate;
        
        this.sqlSessionFactory = this.myBatisDaoSupport.getSqlSessionFactory();
        this.configuration = this.myBatisDaoSupport.getSqlSessionFactory()
                .getConfiguration();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        logger.info("始构建实体自动持久层，开始.beanType:{}", this.beanType.getName());
        
        //构建SqlMap
        this.assistant = new LoggerMapperBuilderAssistant(this.configuration,
                beanType);
        this.assistant.registe();
        logger.info("构建实体自动持久层：sqlmap:{}",
                this.assistant.getCurrentNamespace());
        
        //构建Dao
        this.loggerService = new DefaultServiceLoggerImpl<>(this.beanType,
                this.myBatisDaoSupport, this.transactionTemplate,
                this.assistant);
        
        logger.info("构建实体自动持久层：完成.beanType:{}", this.beanType.getName());
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return ServiceLogger.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public ServiceLogger<T> getObject() throws Exception {
        return this.loggerService;
    }
}
