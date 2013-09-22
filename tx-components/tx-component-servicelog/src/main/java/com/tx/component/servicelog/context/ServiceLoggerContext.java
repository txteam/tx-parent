package com.tx.component.servicelog.context;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.dialect.Dialect;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.servicelog.defaultimpl.ServiceLog;
import com.tx.component.servicelog.logger.ServiceLogger;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.jdbc.sqlsource.SqlSourceBuilder;

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
public class ServiceLoggerContext implements InitializingBean {
    
    /** 懒汉模式获取日志容器句柄 */
    private static Map<Class<? extends ServiceLog>, ServiceLogger> loggerContextMapping = new HashMap<Class<? extends ServiceLog>, ServiceLogger>();
    
    /** 业务日志实例工厂 */
    private ServiceLoggerFactory serviceLogInstanceFactory;
    
    /** 数据源类型 */
    private DataSourceTypeEnum dataSourceType;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 方言类 */
    private Dialect dialect;
    
    /**
     * 私有化构造方法
     */
    protected ServiceLoggerContext() {
        super();
    }
    
    /**
     * 获取日志容器
     * 
     * @return
     */
    public static ServiceLogger getLogger(
            Class<? extends ServiceLog> serviceLogClass) {
        AssertUtils.notNull(serviceLogClass, "serviceLogClass is null");
        
        ServiceLogger res = null;
        synchronized (serviceLogClass) {
            if (loggerContextMapping.containsKey(serviceLogClass)) {
                res = loggerContextMapping.get(serviceLogClass);
            } else {
                
            }
        }
        
        return res;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
    }

    /**
     * @param 对loggerContextMapping进行赋值
     */
    public static void setLoggerContextMapping(
            Map<Class<? extends ServiceLog>, ServiceLogger> loggerContextMapping) {
        ServiceLoggerContext.loggerContextMapping = loggerContextMapping;
    }

    /**
     * @param 对serviceLogInstanceFactory进行赋值
     */
    public void setServiceLogInstanceFactory(
            ServiceLoggerFactory serviceLogInstanceFactory) {
        this.serviceLogInstanceFactory = serviceLogInstanceFactory;
    }

    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @param 对dialect进行赋值
     */
    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
