/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月26日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import javax.sql.DataSource;

import net.sf.ehcache.Cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.auth.context.authchecker.AuthChecker;
import com.tx.core.dbscript.context.DBScriptExecutorContext;

/**
 * 权限容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年2月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthContextConfigurator implements ApplicationContextAware{
    
    /** 日志记录器 */
    protected static final Logger logger = LoggerFactory.getLogger(AuthContextConfigurator.class);

    /** 默认的权限检查器 */
    protected AuthChecker defaultAuthChecker;
    
    /** 权限项缓存对应的缓存生成器 */
    protected Cache cache;
    
    /** 系统id 64，用以与其他系统区分 */
    protected String systemId;
    
    /** 表后缀名 */
    protected String tableSuffix;
    
    /** 数据库脚本是否自动执行 */
    protected boolean databaseSchemaUpdate = false;
    
    /** 数据脚本自动执行器 */
    protected DBScriptExecutorContext dbScriptExecutorContext;
    
    /** 当前spring容器 */
    protected ApplicationContext applicationContext;
    
    /** 事务处理器:如果dataSource为空则该值不能为空 */
    protected PlatformTransactionManager platformTransactionManager;
    
    /** 数据源 */
    protected DataSource dataSource;
    
    /** jdbc句柄:如果dataSource为空则该值不能为空 */
    protected JdbcTemplate jdbcTemplate;
    
    /** 权限配置地址 */
    protected String[] authConfigLocaions;
    
    /**
     * @param arg0
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    

    /**
     * @param 对defaultAuthChecker进行赋值
     */
    public void setDefaultAuthChecker(AuthChecker defaultAuthChecker) {
        this.defaultAuthChecker = defaultAuthChecker;
    }
    
    /**
     * @param 对cache进行赋值
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }
    
    /**
     * @param 对systemId进行赋值
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    /**
     * @param 对tableSuffix进行赋值
     */
    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }
    
    /**
     * @param 对databaseSchemaUpdate进行赋值
     */
    public void setDatabaseSchemaUpdate(boolean databaseSchemaUpdate) {
        this.databaseSchemaUpdate = databaseSchemaUpdate;
    }
    
    /**
     * @return 返回 dbScriptExecutorContext
     */
    public DBScriptExecutorContext getDbScriptExecutorContext() {
        return dbScriptExecutorContext;
    }
    
    /**
     * @param 对dbScriptExecutorContext进行赋值
     */
    public void setDbScriptExecutorContext(
            DBScriptExecutorContext dbScriptExecutorContext) {
        this.dbScriptExecutorContext = dbScriptExecutorContext;
    }


    /**
     * @param 对platformTransactionManager进行赋值
     */
    public void setPlatformTransactionManager(
            PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }


    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * @param 对jdbcTemplate进行赋值
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
