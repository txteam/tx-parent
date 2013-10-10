/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import javax.sql.DataSource;

import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.dbscript.model.DataSourceTypeEnum;

/**
 * 基础数据容器配置加载<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContextConfigurator {
    
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    /** 是否在启动期间就加载基础数据执行器 */
    private boolean loadExecutorOnStartup = true;
    
    /** 在启动期间如果构建基础数据查询器异常是否停止 */
    private boolean stopOnBuildBasicDataExecutorWhenStartup = false;
    
    /** 基础数据所在扫描包 */
    private String basePackages;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 数据源类型 */
    private DataSourceTypeEnum dataSourceType;
    
    /** 缓存 */
    private CacheManager cacheManager;
    
    //    /**
    //     * @return
    //     */
    //    @Override
    //    public int getOrder() {
    //        return this.order;
    //    }
    //    
    //    /**
    //     * @param beanFactory
    //     * @throws BeansException
    //     */
    //    @Override
    //    public void postProcessBeanFactory(
    //            ConfigurableListableBeanFactory beanFactory) throws BeansException {
    //        if(this.loadExecutorOnStartup){
    //            loadExecutorOnStartup();
    //        }
    //    }
    
    /**
      * 在启动期间装载基础数据执行器
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void loadExecutorOnStartup() {
        //在启动期间装载基础数据执行器
    }
    
    /**
     * @return 返回 basePackage
     */
    public String getBasePackages() {
        return basePackages;
    }
    
    /**
     * @param 对basePackage进行赋值
     */
    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
    
    /**
     * @return 返回 dataSourceType
     */
    public DataSourceTypeEnum getDataSourceType() {
        return dataSourceType;
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
    
    /**
     * @return 返回 cacheManager
     */
    public CacheManager getCacheManager() {
        return cacheManager;
    }
    
    /**
     * @param 对cacheManager进行赋值
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    /**
     * @return 返回 dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @return 返回 loadExecutorOnStartup
     */
    public boolean isLoadExecutorOnStartup() {
        return loadExecutorOnStartup;
    }
    
    /**
     * @param 对loadExecutorOnStartup进行赋值
     */
    public void setLoadExecutorOnStartup(boolean loadExecutorOnStartup) {
        this.loadExecutorOnStartup = loadExecutorOnStartup;
    }
    
    /**
     * @return 返回 stopOnBuildBasicDataExecutorWhenStartup
     */
    public boolean isStopOnBuildBasicDataExecutorWhenStartup() {
        return stopOnBuildBasicDataExecutorWhenStartup;
    }
    
    /**
     * @param 对stopOnBuildBasicDataExecutorWhenStartup进行赋值
     */
    public void setStopOnBuildBasicDataExecutorWhenStartup(
            boolean stopOnBuildBasicDataExecutorWhenStartup) {
        this.stopOnBuildBasicDataExecutorWhenStartup = stopOnBuildBasicDataExecutorWhenStartup;
    }
}
