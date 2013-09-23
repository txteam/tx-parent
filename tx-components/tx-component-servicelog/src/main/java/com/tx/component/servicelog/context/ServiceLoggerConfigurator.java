package com.tx.component.servicelog.context;

import javax.sql.DataSource;

import org.hibernate.dialect.Dialect;

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
    
    /** 数据源类型 */
    protected DataSourceTypeEnum dataSourceType;
    
    /** 数据源 */
    protected DataSource dataSource;
    
    /** 方言类 */
    protected Dialect dialect;
    
    /**
     * 私有化构造方法
     */
    protected ServiceLoggerConfigurator() {
        super();
    }
}
