package com.tx.core.datasource;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.tx.core.datasource.finder.ConfigDataSourceFinder;
import com.tx.core.datasource.finder.JNDIDataSourceFinder;

/**
 * jndi数据源工厂类
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataSourceFactoryBean implements
        FactoryBean<javax.sql.DataSource>, InitializingBean {
    private Logger logger = LoggerFactory.getLogger(DataSourceFactoryBean.class);
    
    private String jndiName;
    
    private DataSource ds = null;
    
    private List<DataSourceFinder> datasourceFinderList = new ArrayList<DataSourceFinder>();
    
    private boolean isSupportP6spy = false;
    
    //com.p6spy.engine.spy.P6ConnectionPoolDataSource
    private String p6spyDataSourceClassName = "com.p6spy.engine.spy.P6DataSource";
    
    /**
     * <默认构造函数>
     */
    public DataSourceFactoryBean() {
        super();
        datasourceFinderList = new ArrayList<DataSourceFinder>();
        datasourceFinderList.add(new JNDIDataSourceFinder());
        datasourceFinderList.add(new ConfigDataSourceFinder());
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Start init datasource................................");
        
        if (StringUtils.isEmpty(this.jndiName)) {
            logger.info("Init datasource fail. jndiname is empty.");
            throw new BeanInitializationException("jndiname is empty.");
        }
        logger.info("Start init datasource jndiname:{}",this.jndiName);
        
        if (datasourceFinderList == null) {
            logger.info("Init datasource fail. datasourceFinderList is empty.");
            return;
        }
        
        for (DataSourceFinder finderTemp : this.datasourceFinderList) {
            logger.info("Try to init DataSource By finder : "
                    + finderTemp.getClass().getName()
                    + " . Start...............");
            
            this.ds = finderTemp.getDataSource(jndiName);
            
            if (this.ds != null) {
                logger.info("Try to init DataSource By finder : "
                        + finderTemp.getClass().getName()
                        + " . Success...............");
                break;
            }
            
            logger.info("Try to init DataSource By finder : "
                    + finderTemp.getClass().getName() + " . End...............");
        }
        
        if (this.ds != null) {
            logger.info("Init DataSource by configDataSource success.");
            
            if (isSupportP6spy) {
                UseP6spyProxy();
            }
            
            return;
        }
        
        logger.info("Init DataSource by configDataSource fail.");
        logger.error("Init DataSource fail. With Name: " + jndiName);
        
        throw new BeanInitializationException(
                "init DataSource fail. jndiName :" + this.jndiName);
        
    }
    
    /** 
     *<用p6spy代理生成日志记录>
     *<功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void UseP6spyProxy() {
        logger.info("Use p6spy proxy datasource................................");
        try {
            final String p6spyDataSourceClassNameTemp = this.p6spyDataSourceClassName;
            @SuppressWarnings("rawtypes")
            Class dataSourceProxyClass = Class.forName(p6spyDataSourceClassNameTemp);
            @SuppressWarnings("unchecked")
            Constructor<DataSource> dsConstructor = dataSourceProxyClass.getConstructor(DataSource.class);
            this.ds = dsConstructor.newInstance(this.ds);
        }
        catch (Exception e) {
            logger.info("Use p6spy proxy datasource false................................");
        }
        logger.info("Use p6spy proxy datasource success................................");
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public DataSource getObject() throws Exception {
        return this.ds;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
    /**
     * @return 返回 jndiName
     */
    public String getJndiName() {
        return jndiName;
    }
    
    /**
     * @param 对jndiName进行赋值
     */
    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }
    
    public boolean isSupportP6spy() {
        return isSupportP6spy;
    }
    
    public void setSupportP6spy(boolean isSupportP6spy) {
        this.isSupportP6spy = isSupportP6spy;
    }
    
    public String getP6spyDataSourceClassName() {
        return p6spyDataSourceClassName;
    }
    
    public void setP6spyDataSourceClassName(String p6spyDataSourceClassName) {
        this.p6spyDataSourceClassName = p6spyDataSourceClassName;
    }
}
