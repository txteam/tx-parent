package com.tx.core.datasource;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.tx.core.datasource.finder.JNDIDataSourceFinder;

/**
 * jndi数据源工厂类<br/>
 * 从[数据源查找生成器]列表中遍历返回数据源,直到一个可用的数据源则停止遍历
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataSourceFactoryBean implements
        FactoryBean<javax.sql.DataSource>, InitializingBean {
    
    private Logger logger = LoggerFactory.getLogger(DataSourceFactoryBean.class);
    
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
        //datasourceFinderList.add(new ConfigDataSourceFinder());
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Start init datasource................................");
        
        if (datasourceFinderList == null) {
            logger.info("Init datasource fail. datasourceFinderList is empty.");
            return;
        }
        
        for (DataSourceFinder finderTemp : this.datasourceFinderList) {
            logger.info("Try to init DataSource By finder : "
                    + finderTemp.getClass().getName()
                    + " . Start...............");
            
            this.ds = finderTemp.getDataSource();
            
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
        
        throw new BeanInitializationException(
                "init DataSource fail. jndiName :");
        
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

    /**
     * @return 返回 datasourceFinderList
     */
    public List<DataSourceFinder> getDatasourceFinderList() {
        return datasourceFinderList;
    }

    /**
     * @param 对datasourceFinderList进行赋值
     */
    public void setDatasourceFinderList(List<DataSourceFinder> datasourceFinderList) {
        this.datasourceFinderList = datasourceFinderList;
    }
}
