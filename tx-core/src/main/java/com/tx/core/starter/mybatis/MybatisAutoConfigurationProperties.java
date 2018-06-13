/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

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
@ConfigurationProperties(prefix = "tx.core.mybatis")
public class MybatisAutoConfigurationProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 基础包集合 */
    private String basePackages = "com.tx";
    
    /** sqlSessionTemplate的bean名称 */
    private String sqlSessionTemplateRef = "sqlSessionTemplate";
    
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
     * @return 返回 sqlSessionTemplateRef
     */
    public String getSqlSessionTemplateRef() {
        return sqlSessionTemplateRef;
    }

    /**
     * @param 对sqlSessionTemplateRef进行赋值
     */
    public void setSqlSessionTemplateRef(String sqlSessionTemplateRef) {
        this.sqlSessionTemplateRef = sqlSessionTemplateRef;
    }
}
