/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.rule.context;

import javax.sql.DataSource;

import net.sf.ehcache.Ehcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.OracleLobHandler;

import com.tx.component.rule.impl.drools.drlbyte.DRLByteDroolsRuleRegister;
import com.tx.component.rule.impl.drools.drlfile.DRLFileDroolsRuleRegister;
import com.tx.component.rule.impl.java.JavaMethodRuleRegister;
import com.tx.component.rule.loader.RuleItemPersister;
import com.tx.component.rule.loader.java.JavaMethodRuleItemLoader;
import com.tx.component.rule.loader.persister.RuleItemPersisterImpl;
import com.tx.component.rule.loader.persister.dao.RuleItemByteParamDao;
import com.tx.component.rule.loader.persister.dao.RuleItemDao;
import com.tx.component.rule.loader.persister.dao.RuleItemValueParamDao;
import com.tx.component.rule.loader.persister.dao.impl.RuleItemByteParamDaoImpl;
import com.tx.component.rule.loader.persister.dao.impl.RuleItemDaoImpl;
import com.tx.component.rule.loader.persister.dao.impl.RuleItemValueParamDaoImpl;
import com.tx.component.rule.loader.persister.service.RuleItemService;
import com.tx.component.rule.loader.xml.XMLRuleItemConfigLoader;
import com.tx.component.rule.transation.RuleSessionTransactionFactory;
import com.tx.component.rule.transation.impl.DefaultRuleSessionTransactionFactory;
import com.tx.core.dbscript.context.DBScriptExecutorContext;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 规则容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleContextConfigurator implements InitializingBean {
    
    @Bean(name = "ruleItemDao")
    public RuleItemDao ruleItemDao() {
        RuleItemDao ruleItemDao = new RuleItemDaoImpl(this.jdbcTemplate);
        return ruleItemDao;
    }
    
    @Bean(name = "ruleItemValueParamDao")
    public RuleItemValueParamDao ruleItemValueParamDao() {
        RuleItemValueParamDao res = new RuleItemValueParamDaoImpl(
                this.jdbcTemplate);
        return res;
    }
    
    @Bean(name = "ruleItemByteParamDao")
    public RuleItemByteParamDao ruleItemByteParamDao() {
        RuleItemByteParamDao ruleItemByteParamDao = new RuleItemByteParamDaoImpl(
                jdbcTemplate, lobHandler);
        return ruleItemByteParamDao;
    }
    
    @Bean(name = "ruleItemService")
    public RuleItemService ruleItemService() {
        RuleItemService res = new RuleItemService();
        return res;
    }
    
    @Bean(name = "ruleItemPersister")
    public RuleItemPersister ruleItemPersister() {
        RuleItemPersister ruleItemPersister = new RuleItemPersisterImpl();
        return ruleItemPersister;
    }
    
    @Bean(name = "javaMethodRuleItemLoader")
    public JavaMethodRuleItemLoader javaMethodRuleItemLoader() {
        JavaMethodRuleItemLoader javaMethodRuleItemLoader = new JavaMethodRuleItemLoader();
        return javaMethodRuleItemLoader;
    }
    
    @Bean(name = "xmlRuleItemConfigLoader")
    public XMLRuleItemConfigLoader xmlRuleItemConfigLoader() {
        XMLRuleItemConfigLoader xmlRuleItemConfigLoader = new XMLRuleItemConfigLoader();
        xmlRuleItemConfigLoader.setConfigLocations(this.configLocations);
        return xmlRuleItemConfigLoader;
    }
    
    @Bean(name = "drlByteDroolsRuleRegister")
    public DRLByteDroolsRuleRegister drlByteDroolsRuleRegister() {
        DRLByteDroolsRuleRegister drlByteDroolsRuleRegister = new DRLByteDroolsRuleRegister();
        return drlByteDroolsRuleRegister;
    }
    
    @Bean(name = "drlFileDroolsRuleRegister")
    public DRLFileDroolsRuleRegister drlFileDroolsRuleRegister(){
        DRLFileDroolsRuleRegister drlFileDroolsRuleRegister = new DRLFileDroolsRuleRegister();
        return drlFileDroolsRuleRegister;
    }
    
    @Bean(name = "javaMethodRuleRegister")
    public JavaMethodRuleRegister javaMethodRuleRegister() {
        JavaMethodRuleRegister javaMethodRuleRegister = new JavaMethodRuleRegister();
        return javaMethodRuleRegister;
    }
    
    @Bean(name = "ruleContext")
    public RuleContextFactory ruleContext() {
        RuleContextFactory ruleContextFactory = new RuleContextFactory();
        ruleContextFactory.setDatabaseSchemaUpdate(databaseSchemaUpdate);
        ruleContextFactory.setDataSource(dataSource);
        ruleContextFactory.setDataSourceType(dataSourceType);
        ruleContextFactory.setDbScriptExecutorContext(dbScriptExecutorContext);
        ruleContextFactory.setEhcache(ehcache);
        ruleContextFactory.setJdbcTemplate(jdbcTemplate);
        ruleContextFactory.setLobHandler(lobHandler);
        ruleContextFactory.setRuleSessionTransactionFactory(ruleSessionTransactionFactory);
        ruleContextFactory.setTableSuffix(tableSuffix);
        return ruleContextFactory;
    }
    
    /** 日志记录器 */
    protected static Logger logger = LoggerFactory.getLogger(RuleContext.class);
    
    /** 数据库脚本是否自动执行 */
    protected boolean databaseSchemaUpdate = false;
    
    /** 配置资源集 */
    private String configLocations;
    
    /** 数据脚本自动执行器 */
    protected DBScriptExecutorContext dbScriptExecutorContext;
    
    /** 表后缀名 */
    protected String tableSuffix = "";
    
    /** 数据库类型 */
    private DataSourceTypeEnum dataSourceType;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** lob操作句柄 */
    private LobHandler lobHandler;
    
    /** jdbc句柄:如果dataSource为空则该值不能为空 */
    protected JdbcTemplate jdbcTemplate;
    
    /** 缓存 */
    protected Ehcache ehcache;
    
    /** 规则会话事务工厂  */
    protected RuleSessionTransactionFactory ruleSessionTransactionFactory;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        
        if (lobHandler == null) {
            AssertUtils.notNull(this.dataSourceType, "dataSourceType is null.");
            if (DataSourceTypeEnum.ORACLE9I.equals(this.dataSourceType)) {
                lobHandler = new OracleLobHandler();
            } else {
                lobHandler = new DefaultLobHandler();
            }
        }
        //当jdbcTemplate,或platformTransactionManager为空时dataSource不能为空
        if (jdbcTemplate == null) {
            AssertUtils.notNull(this.dataSource, "dataSource is null.");
            if (this.jdbcTemplate == null) {
                this.jdbcTemplate = new JdbcTemplate(this.dataSource);
            }
        }
        if (this.ruleSessionTransactionFactory == null) {
            this.ruleSessionTransactionFactory = new DefaultRuleSessionTransactionFactory();
        }
    }
    

    
    /**
     * @param 对databaseSchemaUpdate进行赋值
     */
    public void setDatabaseSchemaUpdate(boolean databaseSchemaUpdate) {
        this.databaseSchemaUpdate = databaseSchemaUpdate;
    }
    
    /**
     * @param 对dbScriptExecutorContext进行赋值
     */
    public void setDbScriptExecutorContext(
            DBScriptExecutorContext dbScriptExecutorContext) {
        this.dbScriptExecutorContext = dbScriptExecutorContext;
    }
    
    /**
     * @param 对tableSuffix进行赋值
     */
    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
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
     * @param 对lobHandler进行赋值
     */
    public void setLobHandler(LobHandler lobHandler) {
        this.lobHandler = lobHandler;
    }
    
    /**
     * @param 对jdbcTemplate进行赋值
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * @param 对ehcache进行赋值
     */
    public void setEhcache(Ehcache ehcache) {
        this.ehcache = ehcache;
    }
    
    /**
     * @param 对ruleSessionTransactionFactory进行赋值
     */
    public void setRuleSessionTransactionFactory(
            RuleSessionTransactionFactory ruleSessionTransactionFactory) {
        this.ruleSessionTransactionFactory = ruleSessionTransactionFactory;
    }

    /**
     * @param 对configLocations进行赋值
     */
    public void setConfigLocations(String configLocations) {
        this.configLocations = configLocations;
    }
}
