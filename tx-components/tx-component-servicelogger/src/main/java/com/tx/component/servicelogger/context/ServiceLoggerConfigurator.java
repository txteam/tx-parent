package com.tx.component.servicelogger.context;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * 业务日志<br/>
 * 1、用以提供业务日志记录功能
 * 
 * @author brady
 * @version [版本号, 2012-12-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerConfigurator implements InitializingBean, ApplicationContextAware, BeanNameAware {
    
    /** 数据源类型 */
    @Deprecated
    protected DataSourceTypeEnum dataSourceType;
    
    /** 数据源 */
    @Deprecated
    protected DataSource dataSource;
    
    /** 日志对象建造者 */
    @Deprecated
    protected ServiceLoggerBuilder serviceLoggerBuilder;
    
    /** 日志 */
    protected Logger logger = LoggerFactory.getLogger(ServiceLoggerContext.class);
    
    /** spring容器 */
    protected ApplicationContext applicationContext;
    
    /** 业务日志 Bean 名称 */
    protected String beanName;
    
    protected ServiceLoggerConfigurator() {
        super();
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
    
    /** @param 对 dataSource 进行赋值 */
    @Deprecated
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /** @param 对 dataSourceType 进行赋值 */
    @Deprecated
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
    
    /** @param 对 serviceLoggerBuilder 进行赋值 */
    @Deprecated
    public void setServiceLoggerBuilder(ServiceLoggerBuilder serviceLoggerBuilder) {
        this.serviceLoggerBuilder = serviceLoggerBuilder;
    }
}
