/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 基础数据容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContextConfigurator implements ApplicationContextAware,
        InitializingBean, BeanNameAware {
    
    /** spring容器句柄 */
    protected ApplicationContext applicationContext;
    
    /** 包名 */
    protected String packages = "com.tx";
    
    /** mybatis配置文件 */
    protected String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** mybatis配置文件 */
    protected String[] mybatisMapperLocations = new String[] { "classpath*:com/tx/component/basicdata/dao/impl/DataDictSqlMap.xml" };
    
    /** beanName实例 */
    protected String beanName;
    
    /** 数据源:dataSource */
    protected DataSource dataSource;
    
    /** jdbcTemplate句柄 */
    protected JdbcTemplate jdbcTemplate;
    
    /** transactionManager */
    protected PlatformTransactionManager transactionManager;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    protected TransactionTemplate transactionTemplate;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public final void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @param name
     */
    @Override
    public final void setBeanName(String name) {
        this.beanName = name;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        AssertUtils.notTrue(dataSource == null && jdbcTemplate == null,
                "dataSource or jdbcTemplate all is null.");
        
        if (this.dataSource == null) {
            this.dataSource = this.jdbcTemplate.getDataSource();
        }
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        }
        if (this.transactionManager == null) {
            this.transactionManager = new DataSourceTransactionManager(
                    this.dataSource);
        }
        this.transactionTemplate = new TransactionTemplate(
                this.transactionManager);
        
        //初始化包名
        if (StringUtils.isEmpty(packages)) {
            this.packages = "com.tx";
        }
        
        //进行容器构建
        doBuild();
        
        //初始化容器
        doInitContext();
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
     * @param 对mybatisConfigLocation进行赋值
     */
    public void setMybatisConfigLocation(String mybatisConfigLocation) {
        this.mybatisConfigLocation = mybatisConfigLocation;
    }
    
    /**
     * @param 对mybatisMapperLocations进行赋值
     */
    public void setMybatisMapperLocations(String[] mybatisMapperLocations) {
        this.mybatisMapperLocations = mybatisMapperLocations;
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
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * @param 对transactionTemplate进行赋值
     */
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
