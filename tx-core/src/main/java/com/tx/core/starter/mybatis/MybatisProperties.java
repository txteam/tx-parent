/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月1日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import java.util.Properties;

import org.apache.ibatis.session.ExecutorType;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tx.core.exceptions.SILException;

/**
 * Mybatis配置属性<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
public class MybatisProperties implements Cloneable {
    
    /** 常量 */
    public static final String MYBATIS_PREFIX = "tx.core.mybatis";
    
    /** 数据源方言对应的字符串 */
    private String databasePlatform = MySQL5Dialect.class.getName();
    
    /** Indicates whether perform presence check of the MyBatis xml config file. */
    private boolean checkConfigLocation = false;
    
    /** Packages to search for type handlers. (Package delimiters are ",; \t\n") */
    private String typeHandlersPackage;
    
    /** Execution mode for {@link org.mybatis.spring.SqlSessionTemplate}. */
    private ExecutorType executorType;
    
    /** Packages to search type aliases. (Package delimiters are ",; \t\n") */
    private String typeAliasesPackage;
    
    /** The super class for filtering type alias. If this not specifies, the MyBatis deal as type alias all classes that searched from typeAliasesPackage.*/
    private Class<?> typeAliasesSuperType;
    
    /** mybatis的SqlMap文件配置 */
    private String[] mapperLocations;
    
    /** mybatis配置文件 */
    private String configLocation;
    
    /** Externalized properties for MyBatis configuration. */
    private Properties configurationProperties;
    
    //    /** A Configuration object for customize default settings. If {@link #configLocation}is specified, this property is not used. */
    //    @NestedConfigurationProperty
    //    private Configuration configuration;
    
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
     * @return 返回 checkConfigLocation
     */
    public boolean isCheckConfigLocation() {
        return checkConfigLocation;
    }
    
    /**
     * @param 对checkConfigLocation进行赋值
     */
    public void setCheckConfigLocation(boolean checkConfigLocation) {
        this.checkConfigLocation = checkConfigLocation;
    }
    
    /**
     * @since 1.1.0
     */
    public String getConfigLocation() {
        return this.configLocation;
    }
    
    /**
     * @since 1.1.0
     */
    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }
    
    /**
     * @return 返回 mapperLocations
     */
    public String[] getMapperLocations() {
        return mapperLocations;
    }
    
    /**
     * @param 对mapperLocations进行赋值
     */
    public void setMapperLocations(String[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }
    
    /**
     * @return 返回 typeAliasesPackage
     */
    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }
    
    /**
     * @param 对typeAliasesPackage进行赋值
     */
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }
    
    /**
     * @return 返回 typeHandlersPackage
     */
    public String getTypeHandlersPackage() {
        return typeHandlersPackage;
    }
    
    /**
     * @param 对typeHandlersPackage进行赋值
     */
    public void setTypeHandlersPackage(String typeHandlersPackage) {
        this.typeHandlersPackage = typeHandlersPackage;
    }
    
    /**
     * @since 1.3.3
     */
    public Class<?> getTypeAliasesSuperType() {
        return typeAliasesSuperType;
    }
    
    /**
     * @since 1.3.3
     */
    public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType) {
        this.typeAliasesSuperType = typeAliasesSuperType;
    }
    
    /**
     * @return 返回 executorType
     */
    public ExecutorType getExecutorType() {
        return executorType;
    }
    
    /**
     * @param 对executorType进行赋值
     */
    public void setExecutorType(ExecutorType executorType) {
        this.executorType = executorType;
    }
    
    /**
     * @since 1.2.0
     */
    public Properties getConfigurationProperties() {
        return configurationProperties;
    }
    
    /**
     * @since 1.2.0
     */
    public void setConfigurationProperties(Properties configurationProperties) {
        this.configurationProperties = configurationProperties;
    }
    
    //    /**
    //     * @return 返回 configuration
    //     */
    //    public Configuration getConfiguration() {
    //        return configuration;
    //    }
    //    
    //    /**
    //     * @param 对configuration进行赋值
    //     */
    //    public void setConfiguration(Configuration configuration) {
    //        this.configuration = configuration;
    //    }
    
    /**
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() {
        MybatisProperties properties = null;
        try {
            properties = (MybatisProperties) super.clone();
            properties.setConfigurationProperties(
                    this.configurationProperties == null ? null
                            : (Properties) this.configurationProperties
                                    .clone());
        } catch (Exception e) {
            throw new SILException("CloneNotSupportedException", e);
        }
        //        MybatisProperties properties = new MybatisProperties();
        //        properties.setCheckConfigLocation(this.checkConfigLocation);
        //        properties.setConfigLocation(this.configLocation);
        //        properties.setDatabasePlatform(this.databasePlatform);
        //        
        //        properties.setExecutorType(this.executorType);
        //        properties.setMapperLocations(this.mapperLocations == null ? null : this.mapperLocations.clone());
        //        properties.setTypeAliasesPackage(typeAliasesPackage);
        //        properties.setTypeAliasesSuperType(typeAliasesSuperType);
        //        properties.setTypeHandlersPackage(typeHandlersPackage);
        //        properties.setConfigurationProperties(configurationProperties);
        return properties;
    }
    
    public static void main(String[] args) {
        MybatisProperties pro = new MybatisProperties();
        pro.setCheckConfigLocation(true);
        pro.setConfigLocation("test");
        pro.setConfigurationProperties(new Properties());
        pro.getConfigurationProperties().put("key1", "value1");
        
        MybatisProperties pro2 = (MybatisProperties) pro.clone();
        System.out.println(pro2.getConfigLocation());
        System.out.println(pro2.getConfigurationProperties().get("key1"));
        
        System.out.println(pro == pro2);
        System.out.println(pro.getConfigurationProperties().get("key1") == pro2
                .getConfigurationProperties().get("key1"));
        System.out.println(pro.getConfigurationProperties() == pro2
                .getConfigurationProperties());
        
    }
}
