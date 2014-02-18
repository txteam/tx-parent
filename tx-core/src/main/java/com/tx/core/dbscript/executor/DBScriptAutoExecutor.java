/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-28
 * <修改描述:>
 */
package com.tx.core.dbscript.executor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * 数据库脚本自动执行器<br/>
 *     1、有了这个再次封装，简化原dataSourceInitializer配置
 *     2、数据脚本自动执行器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Deprecated
public class DBScriptAutoExecutor implements InitializingBean, DisposableBean,
        ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    private boolean enabled = true;
    
    private boolean continueOnError = true;
    
    private boolean ignoreFailedDrops = true;
    
    private String sqlScriptEncoding = "UTF-8";
    
    private DataSource dataSource;
    
    private List<String> initScriptLocations;
    
    private List<String> destroyScriptLocations;
    
    private List<Resource> initScriptResources;
    
    private List<Resource> destroyScriptResources;
    
    private DatabasePopulator initDatabasePopulator;
    
    private DatabasePopulator destoryDatabasePopulator;
    
    /** <默认构造函数> */
    public DBScriptAutoExecutor() {
    }
    
    /** <默认构造函数> */
    public DBScriptAutoExecutor(DataSource dataSource,
            String initScript, boolean enabled) {
        super();
        this.enabled = enabled;
        this.dataSource = dataSource;
        
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setContinueOnError(this.continueOnError);
        resourceDatabasePopulator.setIgnoreFailedDrops(this.ignoreFailedDrops);
        resourceDatabasePopulator.setSqlScriptEncoding(sqlScriptEncoding);
        
        resourceDatabasePopulator.setScripts(new Resource[] { new ByteArrayResource(initScript.getBytes()) });
        this.initDatabasePopulator = resourceDatabasePopulator;
    }
    
    /** <默认构造函数> */
    public DBScriptAutoExecutor(DataSource dataSource,
            InputStream initScriptInputStream, boolean enabled) {
        super();
        this.enabled = enabled;
        this.dataSource = dataSource;
        
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setContinueOnError(this.continueOnError);
        resourceDatabasePopulator.setIgnoreFailedDrops(this.ignoreFailedDrops);
        resourceDatabasePopulator.setSqlScriptEncoding(sqlScriptEncoding);
        
        resourceDatabasePopulator.setScripts(new Resource[] { new InputStreamResource(
                initScriptInputStream) });
        this.initDatabasePopulator = resourceDatabasePopulator;
    }
    
    /** <默认构造函数> */
    public DBScriptAutoExecutor(DataSource dataSource,
            Resource initScriptResources, boolean enabled) {
        super();
        this.enabled = enabled;
        this.dataSource = dataSource;
        
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setContinueOnError(this.continueOnError);
        resourceDatabasePopulator.setIgnoreFailedDrops(this.ignoreFailedDrops);
        resourceDatabasePopulator.setSqlScriptEncoding(sqlScriptEncoding);
        
        resourceDatabasePopulator.setScripts(new Resource[] { initScriptResources });
        this.initDatabasePopulator = resourceDatabasePopulator;
    }
    
    /** <默认构造函数> */
    public DBScriptAutoExecutor(DataSource dataSource,
            List<Resource> initScriptResources, boolean enabled) {
        super();
        this.enabled = enabled;
        this.dataSource = dataSource;
        
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setContinueOnError(this.continueOnError);
        resourceDatabasePopulator.setIgnoreFailedDrops(this.ignoreFailedDrops);
        resourceDatabasePopulator.setSqlScriptEncoding(sqlScriptEncoding);
        
        resourceDatabasePopulator.setScripts(initScriptResources.toArray(new Resource[initScriptResources.size()]));
        this.initDatabasePopulator = resourceDatabasePopulator;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * 初始化后开始自动执行脚本
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(dataSource, "dataSource is null");
        
        this.initScriptResources = getResourcesByLocationList(this.initScriptLocations);
        this.destroyScriptResources = getResourcesByLocationList(this.destroyScriptLocations);
        
        if (!CollectionUtils.isEmpty(this.initScriptResources)) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.setContinueOnError(this.continueOnError);
            resourceDatabasePopulator.setIgnoreFailedDrops(this.ignoreFailedDrops);
            resourceDatabasePopulator.setSqlScriptEncoding(sqlScriptEncoding);
            
            resourceDatabasePopulator.setScripts(this.initScriptResources.toArray(new Resource[this.initScriptResources.size()]));
            this.initDatabasePopulator = resourceDatabasePopulator;
        }
        if (!CollectionUtils.isEmpty(this.destroyScriptResources)) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.setContinueOnError(this.continueOnError);
            resourceDatabasePopulator.setIgnoreFailedDrops(this.ignoreFailedDrops);
            resourceDatabasePopulator.setSqlScriptEncoding(sqlScriptEncoding);
            
            resourceDatabasePopulator.setScripts(this.destroyScriptResources.toArray(new Resource[this.destroyScriptResources.size()]));
            this.destoryDatabasePopulator = resourceDatabasePopulator;
        }
        
        //自动执行脚本
        execute();
    }
    
    /**
      * 开始自动执行脚本<br/>
      *<功能详细描述>
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void execute() throws Exception {
        if (this.initDatabasePopulator != null && this.enabled) {
            DatabasePopulatorUtils.execute(this.initDatabasePopulator,
                    this.dataSource);
        }
    }
    
    /**
      * 根据资源地址列表返回资源列表<br/>
      *<功能详细描述>
      * @param locations
      * @return [参数说明]
      * 
      * @return List<Resource> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private List<Resource> getResourcesByLocationList(List<String> locations) {
        List<Resource> resList = new ArrayList<Resource>();
        if (CollectionUtils.isEmpty(initScriptLocations)) {
            return resList;
        }
        
        for (String initScriptLocationTemp : initScriptLocations) {
            if (StringUtils.isBlank(initScriptLocationTemp)) {
                continue;
            }
            Resource[] patternResources = null;
            try {
                patternResources = this.applicationContext.getResources(initScriptLocationTemp);
            } catch (IOException e) {
                throw ExceptionWrapperUtils.wrapperIOException(e,
                        "get resource by location:{} exception. e{}",
                        new Object[] { initScriptLocationTemp, e });
            }
            if (patternResources == null) {
                continue;
            }
            //逐个遍历返回的资源，仅返回存在的，不重复的添加入初始化脚本资源中
            for (Resource resourceTemp : patternResources) {
                if (resourceTemp == null || !resourceTemp.exists()
                        || resList.contains(resourceTemp)) {
                    continue;
                }
                resList.add(resourceTemp);
            }
        }
        return resList;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        if (this.destoryDatabasePopulator != null && this.enabled) {
            DatabasePopulatorUtils.execute(this.destoryDatabasePopulator,
                    this.dataSource);
        }
    }
    
    /**
     * @param 对enabled进行赋值
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * @param 对continueOnError进行赋值
     */
    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }
    
    /**
     * @param 对ignoreFailedDrops进行赋值
     */
    public void setIgnoreFailedDrops(boolean ignoreFailedDrops) {
        this.ignoreFailedDrops = ignoreFailedDrops;
    }
    
    /**
     * @param 对sqlScriptEncoding进行赋值
     */
    public void setSqlScriptEncoding(String sqlScriptEncoding) {
        this.sqlScriptEncoding = sqlScriptEncoding;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param 对initScriptLocations进行赋值
     */
    public void setInitScriptLocations(List<String> initScriptLocations) {
        this.initScriptLocations = initScriptLocations;
    }
    
    /**
     * @param 对destroyScriptLocations进行赋值
     */
    public void setDestroyScriptLocations(List<String> destroyScriptLocations) {
        this.destroyScriptLocations = destroyScriptLocations;
    }
    
}
