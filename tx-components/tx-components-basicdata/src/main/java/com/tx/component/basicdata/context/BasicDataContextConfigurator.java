/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.List;

import javax.sql.DataSource;

import net.sf.ehcache.CacheManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.basicdata.plugin.BasicDataExecutorPlugin;
import com.tx.component.basicdata.plugin.BasicDataExecutorPluginRegistry;
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
public class BasicDataContextConfigurator implements InitializingBean {
    
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    /** 是否在启动期间就加载基础数据执行器 */
    private boolean loadExecutorOnStartup = true;
    
    /** 在启动期间如果构建基础数据查询器异常是否停止 */
    private boolean stopOnBuildBasicDataExecutorWhenStartup = false;
    
    /** 基础数据所在扫描包 */
    private String basePackages;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 事务处理器 */
    private PlatformTransactionManager platformTransactionManager;
    
    /** 数据源类型 */
    private DataSourceTypeEnum dataSourceType;
    
    /** 缓存 */
    private CacheManager cacheManager;
    
    /**
     * 插件基础包
     */
    private String pluginBasePackages;
    
    /** 基础数据执行器插件 */
    private List<BasicDataExecutorPlugin> plugins;
    
    /** 基础数据执行器插件注册器 */
    private BasicDataExecutorPluginRegistry basicDataExecutorPluginRegistry = new BasicDataExecutorPluginRegistry();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(plugins)) {
            for (BasicDataExecutorPlugin pluginTemp : plugins) {
                basicDataExecutorPluginRegistry.register(pluginTemp);
            }
        }
        if (!StringUtils.isBlank(pluginBasePackages)) {
            basicDataExecutorPluginRegistry.register(pluginBasePackages);
        }
        if (platformTransactionManager == null) {
            platformTransactionManager = new DataSourceTransactionManager(
                    this.dataSource);
        }
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
    
    /**
     * @return 返回 basicDataExecutorPluginRegistry
     */
    public BasicDataExecutorPluginRegistry getBasicDataExecutorPluginRegistry() {
        return basicDataExecutorPluginRegistry;
    }
    
    /**
     * @param 对basicDataExecutorPluginRegistry进行赋值
     */
    protected void setBasicDataExecutorPluginRegistry(
            BasicDataExecutorPluginRegistry basicDataExecutorPluginRegistry) {
        this.basicDataExecutorPluginRegistry = basicDataExecutorPluginRegistry;
    }
    
    /**
     * @return 返回 pluginBasePackages
     */
    public String getPluginBasePackages() {
        return pluginBasePackages;
    }
    
    /**
     * @param 对pluginBasePackages进行赋值
     */
    public void setPluginBasePackages(String pluginBasePackages) {
        this.pluginBasePackages = pluginBasePackages;
    }
    
    /**
     * @return 返回 plugins
     */
    public List<BasicDataExecutorPlugin> getPlugins() {
        return plugins;
    }
    
    /**
     * @param 对plugins进行赋值
     */
    public void setPlugins(List<BasicDataExecutorPlugin> plugins) {
        this.plugins = plugins;
    }
    
    /**
     * @return 返回 platformTransactionManager
     */
    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }
    
    /**
     * @param 对platformTransactionManager进行赋值
     */
    public void setPlatformTransactionManager(
            PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }
}
