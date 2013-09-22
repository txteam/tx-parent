package com.tx.component.servicelog.context;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.dialect.Dialect;

import com.tx.component.servicelog.defaultimpl.ServiceLog;
import com.tx.component.servicelog.logger.ServiceLogPersister;
import com.tx.core.dbscript.model.DataSourceTypeEnum;

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
public class ServiceLoggerConfigurator {
    
    /** 懒汉模式获取日志容器句柄 */
    private static Map<Class<? extends ServiceLog>, ServiceLogPersister> loggerContextMapping = new HashMap<Class<? extends ServiceLog>, ServiceLogPersister>();
    
    /** 业务日志实例工厂 */
    private ServiceLoggerContextBuilder serviceLoggerContextFactory;
    
    /** 数据源类型 */
    private DataSourceTypeEnum dataSourceType;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 方言类 */
    private Dialect dialect;
    
    /**
     * 私有化构造方法
     */
    protected ServiceLoggerConfigurator() {
        super();
    }
}
