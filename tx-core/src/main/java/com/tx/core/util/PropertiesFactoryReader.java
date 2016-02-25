/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-18
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 属性文件读取器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-2-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PropertiesFactoryReader implements ApplicationContextAware,
        InitializingBean, FactoryBean<Properties> {
    
    private Logger logger = LoggerFactory.getLogger(PropertiesFactoryReader.class);
    
    /** 配置文件路径 */
    private String location;
    
    /** 文件编码 */
    private String fileEncoding = "UTF-8";
    
    /** spring容器 */
    private ApplicationContext applicationContext;
    
    /** 属性 */
    private Properties props = new Properties();
    
    /** 是否忽略资源未找到异常 */
    private boolean ignoreResourceNotFound = false;
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public Properties getObject() throws Exception {
        return props;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return Properties.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }
    
    /**
      * 获取属性值<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getValue(String key) {
        String value = this.props.getProperty(key);
        return value;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Resource[] resources = this.applicationContext.getResources(this.location);
        //加载配置文件
        loadProperties(resources);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
      * 加载属性
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
     * @throws IOException 
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void loadProperties(Resource[] resources) throws IOException {
        if (ArrayUtils.isEmpty(resources)) {
            return;
        }
        for (Resource resourceTemp : resources) {
            try {
                PropertiesLoaderUtils.fillProperties(this.props,
                        new EncodedResource(resourceTemp, this.fileEncoding));
            } catch (IOException ex) {
                if (this.ignoreResourceNotFound) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Could not load properties from "
                                + location + ": " + ex.getMessage());
                    }
                } else {
                    throw ex;
                }
            }
        }
    }
    
    /**
     * @param 对location进行赋值
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * @param 对fileEncoding进行赋值
     */
    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }
    
    /**
     * @param 对ignoreResourceNotFound进行赋值
     */
    public void setIgnoreResourceNotFound(boolean ignoreResourceNotFound) {
        this.ignoreResourceNotFound = ignoreResourceNotFound;
    }
}
