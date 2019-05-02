/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月1日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MybatisProperties {
    
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    
    /** Indicates whether perform presence check of the MyBatis xml config file. */
    private boolean checkConfigLocation = false;
    
    /** mybatis配置文件 */
    private String configLocation;
    
    /** mybatis的SqlMap文件配置 */
    private String[] mapperLocations;
    
    /** Packages to search for type handlers. (Package delimiters are ",; \t\n") */
    private String typeHandlersPackage;
    
    /** Execution mode for {@link org.mybatis.spring.SqlSessionTemplate}. */
    private ExecutorType executorType;
    
    /** Packages to search type aliases. (Package delimiters are ",; \t\n") */
    private String typeAliasesPackage;
    
    /** The super class for filtering type alias. If this not specifies, the MyBatis deal as type alias all classes that searched from typeAliasesPackage.*/
    private Class<?> typeAliasesSuperType;
    
    /** Externalized properties for MyBatis configuration. */
    private Properties configurationProperties;
    
    /** A Configuration object for customize default settings. If {@link #configLocation}is specified, this property is not used. */
    @NestedConfigurationProperty
    private Configuration configuration;
    
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
    
    /**
     * @return 返回 configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }
    
    /**
     * @param 对configuration进行赋值
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
    
    public Resource[] resolveMapperLocations() {
        return Stream
                .of(Optional.ofNullable(this.mapperLocations)
                        .orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location)))
                .toArray(Resource[]::new);
    }
    
    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }
}
