package com.tx.component.servicelog.context;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;

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
public class ServiceLoggerConfigurator implements InitializingBean {
    
    /** 数据源类型 */
    protected static DataSourceTypeEnum dataSourceType;
    
    /** 数据源 */
    protected static DataSource dataSource;
    
    /** jdbcTemplate */
    protected static JdbcTemplate jdbcTemplate;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(dataSource, "dataSource is null");
        AssertUtils.notNull(dataSourceType, "dataSourceType is null");
        
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    /**
     * 私有化构造方法
     */
    protected ServiceLoggerConfigurator() {
        super();
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        ServiceLoggerConfigurator.dataSourceType = dataSourceType;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        ServiceLoggerConfigurator.dataSource = dataSource;
    }
}
