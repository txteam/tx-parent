/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support.mybatis;

import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.servicelogger.support.ServiceLogger;
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
    
    /** 日志类型 */
    private Class<T> beanType;
    
    /** myBatisDaoSupport句柄 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** 事务句柄 */
    private TransactionTemplate transactionTemplate;
    
    protected Configuration configuration;
    
    private LoggerMapperBuilderAssistant assistant;
    
    private ServiceLogger<T> serviceLogger;
    
    /** <默认构造函数> */
    public ServiceLoggerFactory(Class<T> beanType,
            MyBatisDaoSupport myBatisDaoSupport,
            TransactionTemplate transactionTemplate) {
        super();
        this.beanType = beanType;
        this.myBatisDaoSupport = myBatisDaoSupport;
        this.transactionTemplate = transactionTemplate;
        
        this.configuration = this.myBatisDaoSupport.getSqlSessionFactory()
                .getConfiguration();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        //构建SqlMap
        this.assistant = new LoggerMapperBuilderAssistant(this.configuration,
                beanType);
        this.assistant.registe();
        logger.info("   --- 构业务日志SqlMap.{}",
                this.assistant.getCurrentNamespace());
        
        //构建Dao
        this.serviceLogger = new DefaultServiceLoggerImpl<>(this.beanType,
                this.myBatisDaoSupport, this.transactionTemplate,
                this.assistant);
        logger.info("   --- 构建业务日志句柄.BeanType:{}", this.beanType.getName());
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
     */
    @Override
    public Class<?> getObjectType() {
        return ServiceLogger.class;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public ServiceLogger<T> getObject() throws Exception {
        return this.serviceLogger;
    }
}
