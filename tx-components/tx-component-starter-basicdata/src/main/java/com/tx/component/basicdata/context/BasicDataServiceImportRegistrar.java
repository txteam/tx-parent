/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.AliasRegistry;
import org.springframework.core.type.AnnotationMetadata;

import com.tx.component.basicdata.client.BasicDataAPIClient;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.registry.BasicDataAPIClientRegistry;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.component.basicdata.service.DefaultDBBasicDataService;
import com.tx.component.basicdata.service.DefaultDBTreeAbleBasicDataService;
import com.tx.component.basicdata.service.DefaultRemoteBasicDataService;
import com.tx.component.basicdata.starter.BasicDataPersisterConfiguration;
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
@Configuration
public class BasicDataServiceImportRegistrar
        implements ImportBeanDefinitionRegistrar, InitializingBean,
        BeanFactoryAware, ApplicationContextAware {
    
    /** 别名注册机 */
    private AliasRegistry aliasRegistry;
    
    /** spring容器 */
    private ApplicationContext applicationContext;
    
    /** 基础数据：数据字典业务层 */
    private DataDictService dataDictService;
    
    private BasicDataAPIClientRegistry BasicDataAPIClientRegistry;
    
    /** <默认构造函数> */
    public BasicDataServiceImportRegistrar(DataDictService dataDictService) {
        super();
        this.dataDictService = dataDictService;
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
        this.aliasRegistry = (AliasRegistry) beanFactory;
    }
    
    /**
     * @throws Exception
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void afterPropertiesSet() throws Exception {
        //查找spring容器中已经存在的业务层
        Map<String, BasicDataService> basicDataServiceMap = this.applicationContext
                .getBeansOfType(BasicDataService.class);
        
        for (Entry<String, BasicDataService> entry : basicDataServiceMap
                .entrySet()) {
            BasicDataService service = entry.getValue();
            String beanName = entry.getKey();
            if (service.type() == null
                    || DataDict.class.equals(service.type())) {
                continue;
            }
            
            //生成业务层BeanName
            String alias = generateServiceBeanName(service.getRawType());
            //注册单例Bean进入Spring容器
            if (!beanName.equals(alias)) {
                registerAlise(beanName, alias);
            }
            //注册处理的业务类型
            //registeType2Service(service);
        }
    }
    
    /**
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {
        // TODO Auto-generated method stub
        
    }
    
    //    /**
    //     * 构建默认的基础数据业务类<br/>
    //     * <功能详细描述>
    //     * @param type [参数说明]
    //     * 
    //     * @return void [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @SuppressWarnings("rawtypes")
    //    public BasicDataService buildDefaultDBBasicDataService(String module,
    //            Class<? extends BasicData> type) {
    //        String beanName = generateServiceBeanName(type);
    //        
    //        if (type.isAssignableFrom(TreeAbleBasicData.class)) {
    //            Class<?> defaultServiceType = DefaultDBTreeAbleBasicDataService.class;
    //            
    //            BeanDefinitionBuilder builder = BeanDefinitionBuilder
    //                    .genericBeanDefinition(defaultServiceType);
    //            builder.addPropertyValue("module", module);
    //            builder.addPropertyValue("type", type);
    //            builder.addPropertyValue("dataDictService", this.dataDictService);
    //            
    //            registerBeanDefinition(beanName, builder.getBeanDefinition());
    //        } else {
    //            Class<?> defaultServiceType = DefaultDBBasicDataService.class;
    //            
    //            BeanDefinitionBuilder builder = BeanDefinitionBuilder
    //                    .genericBeanDefinition(defaultServiceType);
    //            builder.addPropertyValue("module", module);
    //            builder.addPropertyValue("type", type);
    //            builder.addPropertyValue("dataDictService", this.dataDictService);
    //            
    //            registerBeanDefinition(beanName, builder.getBeanDefinition());
    //        }
    //        
    //        //利用有参构造函数
    //        BasicDataService service = (BasicDataService) BasicDataServiceRegistry.applicationContext
    //                .getBean(beanName);
    //        
    //        return service;
    //    }
    
    /**
     * @desc 向spring容器注册bean
     * @param beanName
     * @param beanDefinition
     */
    protected void registerAlise(String beanName, String alias) {
        if (!this.aliasRegistry.isAlias(beanName)
                && !this.aliasRegistry.isAlias(alias)) {
            this.aliasRegistry.registerAlias(beanName, alias);
        }
    }
    
    /**
     * 生成对应的业务层Bean名称<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String generateServiceBeanName(Class<? extends BasicData> type) {
        String beanName = "basicdata."
                + StringUtils.uncapitalize(type.getSimpleName()) + "Service";
        return beanName;
    }
}
