/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月6日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;

/**
 * 基础数据业务层工厂<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataServiceRegistry implements ApplicationContextAware,
        InitializingBean, FactoryBean<BasicDataServiceRegistry>,
        BeanFactoryAware {
    
    private static BasicDataServiceRegistry factory;
    
    private static Map<Class<?>, BasicDataService<?>> type2serviceMap = new HashMap<Class<?>, BasicDataService<?>>();
    
    private static Map<String, BasicDataService<?>> typecode2serviceMap = new HashMap<String, BasicDataService<?>>();
    
    private ApplicationContext applicationContext;
    
    private SingletonBeanRegistry singletonBeanRegistry;
    
    private BeanDefinitionRegistry beanDefinitionRegistry;
    
    @Resource(name = "basicdata.dataDictService")
    private DataDictService dataDictService;
    
    private String packages = "com.tx";
    
    /** <默认构造函数> */
    public BasicDataServiceRegistry() {
        super();
    }
    
    /** <默认构造函数> */
    public BasicDataServiceRegistry(String packages) {
        super();
        this.packages = packages;
    }
    
    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
                beanFactory,
                "beanFactory is not BeanDefinitionRegistry instance.");
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
        
        AssertUtils.isInstanceOf(SingletonBeanRegistry.class,
                beanFactory,
                "beanFactory is not SingletonBeanRegistry instance.");
        this.singletonBeanRegistry = (SingletonBeanRegistry) beanFactory;
    }
    
    /**
     * @desc 向spring容器注册BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    protected void registerBeanDefinition(String beanName,
            BeanDefinition beanDefinition) {
        if (!this.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            this.beanDefinitionRegistry.registerBeanDefinition(beanName,
                    beanDefinition);
        }
    }
    
    /**
     * @desc 向spring容器注册bean
     * @param beanName
     * @param beanDefinition
     */
    protected void registerSingletonBean(String beanName, Object bean) {
        if (!this.singletonBeanRegistry.containsSingleton(beanName)) {
            this.singletonBeanRegistry.registerSingleton(beanName, bean);
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
    @SuppressWarnings("rawtypes")
    public BasicDataService buildDefaultBasicDataService(
            Class<? extends BasicData> type) {
        Class<?> defaultServiceType = DefaultBasicDataServiceFactory.class;
        
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(defaultServiceType);
        builder.addPropertyValue("type", type);
        //builder.setScope("prototype");
        //builder.addConstructorArgValue(type);
        //        BeanDefinition bd = new AnnotatedGenericBeanDefinition(
        //                defaultServiceType);
        //        bd.setAttribute("type", type);
        String beanName = "default." + type.getSimpleName()
                + ".basicDataService";
        registerBeanDefinition(beanName, builder.getBeanDefinition());
        
        //利用有参构造函数,(Object) type
        BasicDataService service = (BasicDataService) this.applicationContext.getBean(beanName);
        return service;
    }
    
    //    /**
    //     * 
    //      *<功能简述>
    //      *<功能详细描述>
    //      * @param type
    //      * @return [参数说明]
    //      * 
    //      * @return BasicDataService [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    @SuppressWarnings("rawtypes")
    //    public BasicDataService buildDefaultBasicDataService(
    //            Class<? extends BasicData> type) {
    //        BasicDataService bdService = (BasicDataService) this.applicationContext.getBean("basicdata.defaultBasicDataService",
    //                new Object[] { type });
    //        
    //        System.out.println("type:" + type + " | service.type: " + bdService.type());
    //        return bdService;
    //    }
    
    /**
      * 单例基础数据工厂类<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return BasicDataServiceFactory [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static BasicDataServiceRegistry getFactory() {
        AssertUtils.notNull(BasicDataServiceRegistry.factory,
                "factory not inited.");
        
        return factory;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public BasicDataServiceRegistry getObject() throws Exception {
        if (BasicDataServiceRegistry.factory == null) {
            return this;
        } else {
            return BasicDataServiceRegistry.factory;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return BasicDataServiceRegistry.class;
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void afterPropertiesSet() throws Exception {
        AssertUtils.isNull(BasicDataServiceRegistry.factory,
                "factory already inited.");
        BasicDataServiceRegistry.factory = this;
        
        //查找spring容器中已经存在的业务层
        Collection<BasicDataService> basicDataServices = this.applicationContext.getBeansOfType(BasicDataService.class)
                .values();
        for (BasicDataService service : basicDataServices) {
            if (service.type() == null) {
                //跳过注册的LazyBean
                continue;
            }
            //注册处理的业务类型
            registeType2Service(service);
        }
        
        //扫描基础数据类,自动注册其对应的业务层类
        Set<Class<? extends BasicData>> bdClassSet = new HashSet<>();
        String[] packageArray = StringUtils.splitByWholeSeparator(this.packages,
                ",");
        for (String packageTemp : packageArray) {
            if (StringUtils.isEmpty(packageTemp)) {
                continue;
            }
            Set<Class<? extends BasicData>> bdClassSetTemp = ClassScanUtils.scanByParentClass(BasicData.class,
                    packageTemp);
            bdClassSet.addAll(bdClassSetTemp);
        }
        //加载类与业务层的映射关联
        for (Class<? extends BasicData> bdType : bdClassSet) {
            if (Modifier.isInterface(bdType.getModifiers())
                    || Modifier.isAbstract(bdType.getModifiers())) {
                //如果是接口或抽象类直接跳过
                continue;
            }
            if (type2serviceMap.containsKey(bdType)) {
                //如果已经存在对应的业务逻辑层
                continue;
            }
            BasicDataService<? extends BasicData> bdService = buildDefaultBasicDataService(bdType);
            //注册业务层逻辑
            registeType2Service(bdService);
        }
    }
    
    @SuppressWarnings("rawtypes")
    private void registeType2Service(BasicDataService service) {
        AssertUtils.notNull(service.type(), "type is null.");
        AssertUtils.notNull(service, "service is null.");
        
        AssertUtils.isTrue(!type2serviceMap.containsKey(service.type()),
                "type:{} : service :{} is exist.",
                new Object[] { service.type(), service });
        AssertUtils.isTrue(!typecode2serviceMap.containsKey(service.code()),
                "typecoe:{} : service :{} is exist.",
                new Object[] { service.type(), service });
        
        type2serviceMap.put(service.type(), service);
        typecode2serviceMap.put(service.code(), service);
    }
    
    /**
     * 根据基础数据类型挪去对应的基础数据处理业务层逻辑<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return BasicDataService<BDTYPE> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("unchecked")
    public <BDTYPE extends BasicData> BasicDataService<BDTYPE> getBasicDataService(
            Class<BDTYPE> type) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.isTrue(type2serviceMap.containsKey(type),
                "type handler service is not exist.type:{}",
                new Object[] { type });
        
        BasicDataService<BDTYPE> service = (BasicDataService<BDTYPE>) type2serviceMap.get(type);
        return service;
    }
    
    /**
     * @param 对packages进行赋值
     */
    public void setPackages(String packages) {
        this.packages = packages;
    }
}
