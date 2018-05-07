/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = "basicdata")
public class BasicDataContextProperties{

    /** 基础包集合 */
    private String basePackages = "com.tx";
    
    /** cacheManager */
    private CacheManager cacheManager;
    
    /** 数据源:dataSource */
    protected DataSource dataSource;
}
