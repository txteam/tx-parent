/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月2日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import java.util.List;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * 抽象的Mybatis配置容器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractMybatisConfiguration
        implements ResourceLoaderAware {
    
    /** 日志记录句柄 */
    protected static final Logger logger = LoggerFactory
            .getLogger(AbstractMybatisConfiguration.class);
    
    /** spring容器 */
    protected ApplicationContext applicationContext;
    
    /** 资源加载器 */
    protected ResourceLoader resourceLoader;
    
    /** 数据源 */
    protected final DatabaseIdProvider databaseIdProvider;
    
    /** 拦截器 */
    protected final Interceptor[] interceptors;
    
    /** 自定义配置 */
    protected final List<ConfigurationCustomizer> configurationCustomizers;
    
    /** <默认构造函数> */
    public AbstractMybatisConfiguration(
            ObjectProvider<DatabaseIdProvider> databaseIdProvider,
            ObjectProvider<Interceptor[]> interceptorsProvider,
            ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        
        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
        this.interceptors = interceptorsProvider.getIfAvailable();
        this.configurationCustomizers = configurationCustomizersProvider
                .getIfAvailable();
    }
    
    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
}
