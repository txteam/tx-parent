/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-18
 * <修改描述:>
 */
package com.tx.core.spring.factorybean;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 属性文件读取器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-2-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ObjectPropertiesFactoryBean implements InitializingBean,
        FactoryBean<Properties> {
    
    /** 配置文件路径 */
    private Object[] beans;
    
    /** key字段 */
    private String keyField;
    
    /** value字段 */
    private String valueField;
    
    /** 属性 */
    private Properties props = new Properties();
    
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
        //加载配置文件
        loadProperties(this.beans);
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
    private void loadProperties(Object[] beans) throws IOException {
        if (ArrayUtils.isEmpty(beans)) {
            return;
        }
        AssertUtils.notEmpty(this.keyField, "keyField is empty.");
        AssertUtils.notEmpty(this.valueField, "valueField is empty.");
        
        for (Object beanTemp : beans) {
            BeanWrapper bw = new BeanWrapperImpl(beanTemp);
            
            String key = bw.getPropertyValue(this.keyField).toString();
            String value = bw.getPropertyValue(this.valueField).toString();
            
            this.props.setProperty(key, value);
        }
    }
}
