/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.servicelogger.starter;

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
@ConfigurationProperties(prefix = "tx.servicelogger")
public class ServiceLoggerContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** 基础包集合 */
    private String basePackages = "com.tx";
    
    /** 数据源:dataSource */
    private String dataSourceRef;
    
    /** 数据源:dataSource */
    private String mybatisDaoSupportRef;
    
    /** transactionManager */
    private String transactionManagerRef;
    
    /** 表是否自动初始化 */
    private boolean tableAutoInitialize;
    
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
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @return 返回 dataSourceRef
     */
    public String getDataSourceRef() {
        return dataSourceRef;
    }
    
    /**
     * @param 对dataSourceRef进行赋值
     */
    public void setDataSourceRef(String dataSourceRef) {
        this.dataSourceRef = dataSourceRef;
    }
    
    /**
     * @return 返回 mybatisDaoSupportRef
     */
    public String getMybatisDaoSupportRef() {
        return mybatisDaoSupportRef;
    }
    
    /**
     * @param 对mybatisDaoSupportRef进行赋值
     */
    public void setMybatisDaoSupportRef(String mybatisDaoSupportRef) {
        this.mybatisDaoSupportRef = mybatisDaoSupportRef;
    }
    
    /**
     * @return 返回 transactionManagerRef
     */
    public String getTransactionManagerRef() {
        return transactionManagerRef;
    }
    
    /**
     * @param 对transactionManagerRef进行赋值
     */
    public void setTransactionManagerRef(String transactionManagerRef) {
        this.transactionManagerRef = transactionManagerRef;
    }

    /**
     * @return 返回 tableAutoInitialize
     */
    public boolean isTableAutoInitialize() {
        return tableAutoInitialize;
    }

    /**
     * @param 对tableAutoInitialize进行赋值
     */
    public void setTableAutoInitialize(boolean tableAutoInitialize) {
        this.tableAutoInitialize = tableAutoInitialize;
    }
}
