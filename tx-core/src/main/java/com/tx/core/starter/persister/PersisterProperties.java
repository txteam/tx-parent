/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.core.starter.persister;

import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tx.core.starter.mybatis.MybatisProperties;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = PersisterProperties.PERSISTER_PREFIX)
public class PersisterProperties {
    
    /** 常量 */
    public static final String PERSISTER_PREFIX = "tx.core.persister";
    
    /** 持久化类型 */
    private PersisterTypeEnum type = PersisterTypeEnum.mybatis;
    
    /** 数据源方言对应的字符串 */
    private String databasePlatform = MySQL5Dialect.class.getName();
    
    /** mybatis配置文件:service需要该逻辑 */
    private MybatisProperties mybatis;
    
    /** 数据源引用 */
    private String dataSourceRef;
    
    /** 事务以引用 */
    private String transactionManagerRef;
    
    /**
     * @return 返回 type
     */
    public PersisterTypeEnum getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(PersisterTypeEnum type) {
        this.type = type;
    }
    
    /**
     * @return 返回 databasePlatform
     */
    public String getDatabasePlatform() {
        return databasePlatform;
    }
    
    /**
     * @param 对databasePlatform进行赋值
     */
    public void setDatabasePlatform(String databasePlatform) {
        this.databasePlatform = databasePlatform;
    }
    
    /**
     * @return 返回 mybatis
     */
    public MybatisProperties getMybatis() {
        return mybatis;
    }
    
    /**
     * @param 对mybatis进行赋值
     */
    public void setMybatis(MybatisProperties mybatis) {
        this.mybatis = mybatis;
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
}
