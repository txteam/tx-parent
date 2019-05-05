/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.basicdata.registry;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

import com.tx.component.basicdata.annotation.BasicDataEntity;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.BasicDataService;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.component.basicdata.service.impl.DefaultDBBasicDataService;
import com.tx.component.basicdata.service.impl.DefaultDBTreeAbleBasicDataService;
import com.tx.component.basicdata.service.impl.DefaultRemoteBasicDataService;
import com.tx.component.basicdata.service.impl.DefaultRemoteTreeAbleBasicDataService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;

/**
 * 数据字典业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataServiceRegistry
        implements InitializingBean, BeanFactoryAware, ApplicationContextAware {
    
    //日志记录句柄
    private Logger logger = LoggerFactory
            .getLogger(BasicDataServiceRegistry.class);
    
    /** spring容器 */
    private ApplicationContext applicationContext;
    
    /** bean定义注册机 */
    private BeanDefinitionRegistry beanDefinitionRegistry;
    
    /** 别名注册机 */
    private AliasRegistry aliasRegistry;
    
    /** 基础数据扫表包路径 */
    private String basePackages;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** 基础数据：数据字典业务层 */
    private DataDictService dataDictService;
    
    /** 基础数APIClient注册表实例 */
    private BasicDataAPIClientRegistry basicDataAPIClientRegistry;
    
    /** 基础数据实体注册表 */
    private BasicDataEntityRegistry registry;
    
    /** 记录已经完成注册的class即可 */
    private final Set<Class<?>> loadedClassSet = new HashSet<>();
    
    /** <默认构造函数> */
    public BasicDataServiceRegistry(String basePackages, String module,
            DataDictService dataDictService,
            BasicDataAPIClientRegistry basicDataAPIClientRegistry,
            BasicDataEntityRegistry registry) {
        super();
        this.basePackages = basePackages;
        this.module = module;
        this.dataDictService = dataDictService;
        this.basicDataAPIClientRegistry = basicDataAPIClientRegistry;
        this.registry = registry;
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
        
        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
                beanFactory,
                "beanFactory is not BeanDefinitionRegistry instance.");
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
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
            
            this.loadedClassSet.add(service.getRawType()); //注册处理的业务类型
            this.registry.register(this.module, service);
        }
        
        //注册Bean定义
        registerBeanDefinitions(this.beanDefinitionRegistry);
    }
    
    /**
     * @param importingClassMetadata
     * @param registry
     */
    public void registerBeanDefinitions(BeanDefinitionRegistry registry) {
        //扫描基础数据类,自动注册其对应的业务层类
        Set<Class<? extends BasicData>> bdClassSet = new HashSet<>();
        String[] packageArray = StringUtils
                .splitByWholeSeparator(this.basePackages, ",");
        for (String packageTemp : packageArray) {
            if (StringUtils.isEmpty(packageTemp)) {
                continue;
            }
            Set<Class<? extends BasicData>> bdClassSetTemp = ClassScanUtils
                    .scanByParentClass(BasicData.class, packageTemp);
            bdClassSet.addAll(bdClassSetTemp);
        }
        //加载类与业务层的映射关联
        for (Class<? extends BasicData> bdType : bdClassSet) {
            if (this.loadedClassSet.contains(bdType)) {
                //跳过已经加载，或已经具有自定义实现的基础数据类型
                continue;
            }
            this.loadedClassSet.add(bdType);
            
            if (bdType.isInterface()
                    || Modifier.isAbstract(bdType.getModifiers())) {
                //如果是接口或抽象类直接跳过
                continue;
            }
            if (!bdType.isAnnotationPresent(BasicDataEntity.class)) {
                //如果没有基础数据注解，无法识别用远端加载还是本地加载
                continue;
            }
            
            BasicDataEntity annoInfo = bdType
                    .getAnnotation(BasicDataEntity.class);
            BasicDataService<? extends BasicData> service = null;
            if (StringUtils.isEmpty(annoInfo.module()) || StringUtils
                    .equalsAnyIgnoreCase(annoInfo.module(), this.module)) {
                service = buildDefaultDBBasicDataService(registry, bdType);
                
                //注册处理的业务类型
                this.registry.register(this.module, service);
            } else {
                service = buildDefaultRemoteBasicDataService(registry,
                        bdType,
                        annoInfo.module());
                
                if (service != null) {
                    //注册处理的业务类型
                    this.registry.register(annoInfo.module(), service);
                }
            }
            
        }
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
    @SuppressWarnings({ "unchecked" })
    public BasicDataService<? extends BasicData> buildDefaultDBBasicDataService(
            BeanDefinitionRegistry registry,
            Class<? extends BasicData> rawType) {
        BeanDefinitionBuilder builder = null;
        if (TreeAbleBasicData.class.isAssignableFrom(rawType)) {
            Class<?> defaultServiceType = DefaultDBTreeAbleBasicDataService.class;
            
            builder = BeanDefinitionBuilder
                    .genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("rawType", rawType);
            builder.addPropertyValue("dataDictService", this.dataDictService);
            builder.addPropertyValue("transactionTemplate",
                    this.dataDictService.getTransactionTemplate());
        } else {
            Class<?> defaultServiceType = DefaultDBBasicDataService.class;
            
            builder = BeanDefinitionBuilder
                    .genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("rawType", rawType);
            builder.addPropertyValue("dataDictService", this.dataDictService);
        }
        
        //注册，生成，并返回业务层
        String beanName = generateServiceBeanName(rawType);
        if (!registry.containsBeanDefinition(beanName)) {
            logger.debug(
                    "动态注入基础数据业务层定义: beanName:{} Type:com.tx.component.basicdata.context.DefaultBasicDataService",
                    beanName);
            registry.registerBeanDefinition(beanName,
                    builder.getBeanDefinition());
        }
        //利用有参构造函数
        BasicDataService<? extends BasicData> service = (BasicDataService<? extends BasicData>) this.applicationContext
                .getBean(beanName);
        return service;
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
    @SuppressWarnings({ "unchecked" })
    public BasicDataService<? extends BasicData> buildDefaultRemoteBasicDataService(
            BeanDefinitionRegistry registry, Class<? extends BasicData> rawType,
            String module) {
        if (this.basicDataAPIClientRegistry == null) {
            //如果远端客户端不存在，则直接不进行生成
            return null;
        }
        
        //利用有参构造函数,(Object) type
        BeanDefinitionBuilder builder = null;
        if (TreeAbleBasicData.class.isAssignableFrom(rawType)) {
            Class<?> defaultServiceType = DefaultRemoteTreeAbleBasicDataService.class;
            
            builder = BeanDefinitionBuilder
                    .genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("rawType", rawType);
            builder.addPropertyValue("client",
                    this.basicDataAPIClientRegistry
                            .getBasicDataAPIClient(module));
        } else {
            Class<?> defaultServiceType = DefaultRemoteBasicDataService.class;
            
            builder = BeanDefinitionBuilder
                    .genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("rawType", rawType);
            builder.addPropertyValue("client",
                    this.basicDataAPIClientRegistry
                            .getBasicDataAPIClient(module));
        }
        String beanName = generateServiceBeanName(rawType);
        if (!registry.containsBeanDefinition(beanName)) {
            logger.debug(
                    "动态注入基础数据业务层定义: beanName:{} Type:com.tx.component.basicdata.context.DefaultBasicDataService",
                    beanName);
            registry.registerBeanDefinition(beanName,
                    builder.getBeanDefinition());
        }
        //利用有参构造函数
        BasicDataService<? extends BasicData> service = (BasicDataService<? extends BasicData>) this.applicationContext
                .getBean(beanName);
        
        return service;
    }
    
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
