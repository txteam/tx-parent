/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.configuration.registry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.AliasRegistry;

import com.tx.component.configuration.service.impl.RemoteConfigPropertyManager;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 数据字典业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RemoteConfigPropertyManagerRegistry
        implements BeanFactoryAware, ApplicationContextAware, InitializingBean {
    
    //日志记录句柄
    private Logger logger = LoggerFactory
            .getLogger(RemoteConfigPropertyManagerRegistry.class);
    
    /** spring容器 */
    private ApplicationContext applicationContext;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** bean定义注册机 */
    private BeanDefinitionRegistry beanDefinitionRegistry;
    
    /** 配置客户端注册表 */
    private ConfigAPIClientRegistry configAPIClientRegistry;
    
    /** <默认构造函数> */
    public RemoteConfigPropertyManagerRegistry(String module,
            ConfigAPIClientRegistry configAPIClientRegistry) {
        super();
        this.module = module;
        this.configAPIClientRegistry = configAPIClientRegistry;
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
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AssertUtils.isInstanceOf(AliasRegistry.class,
                beanFactory,
                "beanFactory is not SingletonBeanRegistry instance.");
        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
                beanFactory,
                "beanFactory is not BeanDefinitionRegistry instance.");
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(this.module, "module is empty.");
        AssertUtils.notNull(this.configAPIClientRegistry,
                "configAPIClientRegistry is null.");
    }
    
    /**
    * 构建默认的基础数据业务类<br/>
    * <功能详细描述>
    * @param type [参数说明]
    * 
    * @return void [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    public RemoteConfigPropertyManager buildRemoteConfigPropertyManager(
            String module) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.isTrue(module.equals(this.module),
                "module:{} should not equal projectModule:{}.",
                module,
                this.module);
        
        //利用有参构造函数,(Object) type
        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .genericBeanDefinition(RemoteConfigPropertyManager.class);
        builder.addPropertyValue("module", module);
        builder.addPropertyValue("client",
                this.configAPIClientRegistry.getConfigAPIClient(module));
        String beanName = generateServiceBeanName(module);
        if (!this.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            logger.debug(
                    "动态注入基础数据业务层定义: beanName:{} Type:com.tx.component.basicdata.context.DefaultBasicDataService",
                    beanName);
            this.beanDefinitionRegistry.registerBeanDefinition(beanName,
                    builder.getBeanDefinition());
        }
        
        //利用有参构造函数
        RemoteConfigPropertyManager service = (RemoteConfigPropertyManager) this.applicationContext
                .getBean(beanName);
        return service;
    }
    
    /**
     * 生成对应的业务层Bean名称<br/>
     * <功能详细描述>
     * @param module
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String generateServiceBeanName(String module) {
        String beanName = "remote." + StringUtils.uncapitalize(module) + "."
                + "configPropertyService";
        return beanName;
    }
}
