/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月6日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 基础数据业务层工厂<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataServiceFactory implements ApplicationContextAware,
        InitializingBean, FactoryBean<BasicDataServiceFactory> {
    
    private static BasicDataServiceFactory factory;
    
    private static Map<Class<?>, BasicDataService<?>> type2serviceMap = new HashMap<Class<?>, BasicDataService<?>>();
    
    private ApplicationContext applicationContext;
    
    @Resource(name = "dataDictService")
    private DataDictService dataDictService;
    
    public static BasicDataServiceFactory getFactory() {
        AssertUtils.notNull(BasicDataServiceFactory.factory,
                "factory already inited.");
        
        return factory;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public BasicDataServiceFactory getObject() throws Exception {
        if (BasicDataServiceFactory.factory == null) {
            return this;
        } else {
            return BasicDataServiceFactory.factory;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return BasicDataServiceFactory.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
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
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.isNull(BasicDataServiceFactory.factory,
                "factory already inited.");
        
        BasicDataServiceFactory.factory = this;
    }
    
}
