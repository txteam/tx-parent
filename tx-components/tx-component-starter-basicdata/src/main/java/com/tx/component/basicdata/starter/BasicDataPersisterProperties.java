/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
public class BasicDataPersisterProperties {
    
    /** 持久化类型 */
    private String type = "mybatis";
    
    /** mybatis配置文件:service需要该逻辑 */
    private String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** 数据源:dataSource */
    private String dataSourceRef;
    
    /** transactionManager */
    protected String transactionManagerRef;
    
    /**
     * @return 返回 type
     */
    public String getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
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
}
