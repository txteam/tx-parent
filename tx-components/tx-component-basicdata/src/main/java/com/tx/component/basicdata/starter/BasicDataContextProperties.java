/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
public class BasicDataContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 基础包集合 */
    private String basePackages = "com.tx";
    
    /** mybatis配置文件 */
    private String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** cacheManager */
    private String cacheManager;
    
    /** 数据源:dataSource */
    private String dataSource;
    
    /** transactionManager */
    protected String transactionManager;
    
    /**
     * @return 返回 enable
     */
    public boolean isEnable() {
        return enable;
    }
    
    /**
     * @param 对enable进行赋值
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    /**
     * @return 返回 basePackages
     */
    public String getBasePackages() {
        return basePackages;
    }
    
    /**
     * @param 对basePackages进行赋值
     */
    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
    
    /**
     * @return 返回 cacheManager
     */
    public String getCacheManager() {
        return cacheManager;
    }
    
    /**
     * @param 对cacheManager进行赋值
     */
    public void setCacheManager(String cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    /**
     * @return 返回 dataSource
     */
    public String getDataSource() {
        return dataSource;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @return 返回 mybatisConfigLocation
     */
    public String getMybatisConfigLocation() {
        return mybatisConfigLocation;
    }
    
    /**
     * @param 对mybatisConfigLocation进行赋值
     */
    public void setMybatisConfigLocation(String mybatisConfigLocation) {
        this.mybatisConfigLocation = mybatisConfigLocation;
    }
    
    /**
     * @return 返回 transactionManager
     */
    public String getTransactionManager() {
        return transactionManager;
    }
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(String transactionManager) {
        this.transactionManager = transactionManager;
    }
}
