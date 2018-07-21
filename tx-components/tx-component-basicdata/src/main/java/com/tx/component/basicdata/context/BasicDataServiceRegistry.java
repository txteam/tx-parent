/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月6日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.AliasRegistry;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.BasicDataType;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.BasicDataRemoteService;
import com.tx.component.basicdata.service.BasicDataTypeService;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.component.basicdata.service.DefaultDBBasicDataService;
import com.tx.component.basicdata.service.DefaultDBTreeAbleBasicDataService;
import com.tx.component.basicdata.service.DefaultRemoteBasicDataService;
import com.tx.component.basicdata.service.DefaultRemoteTreeAbleBasicDataService;
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
        InitializingBean, BeanFactoryAware, BeanNameAware {
    
    private Logger logger = LoggerFactory
            .getLogger(BasicDataServiceRegistry.class);
    
    private static String beanName;
    
    private static ApplicationContext applicationContext;
    
    private static BasicDataServiceRegistry instance;
    
    private static Map<Class<?>, BasicDataService<?>> type2serviceMap = new HashMap<Class<?>, BasicDataService<?>>();
    
    private static Map<String, BasicDataService<?>> code2serviceMap = new HashMap<String, BasicDataService<?>>();
    
    private AliasRegistry aliasRegistry;
    
    private BeanDefinitionRegistry beanDefinitionRegistry;
    
    private SingletonBeanRegistry singletonBeanRegistry;
    
    /** 所属模块：如果对象已经存在，module不等于当前模块，则默认调用remoteBasicDataSer */
    private String module;
    
    /** 扫描包路径 */
    private String basePackages = "com.tx";
    
    /** 基础数据类型 */
    private BasicDataTypeService basicDataTypeService;
    
    /** 数据字典业务层 */
    private DataDictService dataDictService;
    
    /** 基础数据远程调用消费逻辑层 */
    private BasicDataRemoteService basicDataRemoteService;
    
    /** <默认构造函数> */
    public BasicDataServiceRegistry() {
        super();
    }
    
    /** <默认构造函数> */
    public BasicDataServiceRegistry(String module, String basePackages,
            BasicDataTypeService basicDataTypeService,
            DataDictService dataDictService,
            BasicDataRemoteService basicDataRemoteService) {
        super();
        AssertUtils.notEmpty(module, "module is null.");
        
        this.module = module;
        this.basePackages = basePackages;
        this.basicDataTypeService = basicDataTypeService;
        this.dataDictService = dataDictService;
        this.basicDataRemoteService = basicDataRemoteService;
    }
    
    /**
     * 单例基础数据工厂类<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataServiceFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static BasicDataServiceRegistry getInstance() {
        AssertUtils.notEmpty(BasicDataServiceRegistry.beanName,
                "beanName is empty.");
        
        if (BasicDataServiceRegistry.instance == null) {
            BasicDataServiceRegistry.instance = applicationContext.getBean(
                    BasicDataServiceRegistry.beanName,
                    BasicDataServiceRegistry.class);
        }
        
        AssertUtils.notNull(BasicDataServiceRegistry.instance,
                "instance not inited.");
        
        return instance;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String beanName) {
        BasicDataServiceRegistry.beanName = beanName;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        BasicDataServiceRegistry.applicationContext = applicationContext;
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
        
        AssertUtils.isInstanceOf(AliasRegistry.class,
                beanFactory,
                "beanFactory is not SingletonBeanRegistry instance.");
        this.aliasRegistry = (AliasRegistry) beanFactory;
        
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
            logger.debug(
                    "动态注入基础数据业务层定义: beanName:{} Type:com.tx.component.basicdata.context.DefaultBasicDataService",
                    beanName);
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
            logger.debug("注入基础数据业务层实例: beanName:{} Type:{}",
                    beanName,
                    bean.getClass().getName());
            this.singletonBeanRegistry.registerSingleton(beanName, bean);
        }
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
     * 构建默认的基础数据业务类<br/>
     * <功能详细描述>
     * @param type [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public BasicDataService buildDefaultDBBasicDataService(String module,
            Class<? extends BasicData> type) {
        String beanName = generateServiceBeanName(type);
        
        if (type.isAssignableFrom(TreeAbleBasicData.class)) {
            Class<?> defaultServiceType = DefaultDBTreeAbleBasicDataService.class;
            
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                    .genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("module", module);
            builder.addPropertyValue("type", type);
            builder.addPropertyValue("dataDictService", this.dataDictService);
            registerBeanDefinition(beanName, builder.getBeanDefinition());
        } else {
            Class<?> defaultServiceType = DefaultDBBasicDataService.class;
            
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                    .genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("module", module);
            builder.addPropertyValue("type", type);
            builder.addPropertyValue("dataDictService", this.dataDictService);
            registerBeanDefinition(beanName, builder.getBeanDefinition());
        }
        
        //利用有参构造函数,(Object) type
        BasicDataService service = (BasicDataService) BasicDataServiceRegistry.applicationContext
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
    @SuppressWarnings("rawtypes")
    public BasicDataService buildDefaultRemoteBasicDataService(String module,
            Class<? extends BasicData> type) {
        String beanName = generateServiceBeanName(type);
        
        //利用有参构造函数,(Object) type
        BasicDataService service = null;
        if (type.isAssignableFrom(TreeAbleBasicData.class)) {
            Class<?> defaultServiceType = DefaultRemoteTreeAbleBasicDataService.class;
            
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                    .genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("module", module);
            builder.addPropertyValue("type", type);
            builder.addPropertyValue("client", this.basicDataRemoteService);
            registerBeanDefinition(beanName, builder.getBeanDefinition());
            
            service = (BasicDataService) BasicDataServiceRegistry.applicationContext
                    .getBean(beanName);
            
        } else {
            Class<?> defaultServiceType = DefaultRemoteBasicDataService.class;
            
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                    .genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("module", module);
            builder.addPropertyValue("type", type);
            builder.addPropertyValue("client", this.basicDataRemoteService);
            registerBeanDefinition(beanName, builder.getBeanDefinition());
            
            service = (BasicDataService) BasicDataServiceRegistry.applicationContext
                    .getBean(beanName);
        }
        
        return service;
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
    
    /**
     * @throws Exception
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void afterPropertiesSet() throws Exception {
        //查找spring容器中已经存在的业务层
        Map<String, BasicDataService> basicDataServiceMap = BasicDataServiceRegistry.applicationContext
                .getBeansOfType(BasicDataService.class);
        for (Entry<String, BasicDataService> entry : basicDataServiceMap
                .entrySet()) {
            BasicDataService service = entry.getValue();
            String beanName = entry.getKey();
            if (service.type() == null
                    || DataDict.class.equals(service.type())) {
                continue;
            }
            String alias = generateServiceBeanName(service.type());
            //注册单例Bean进入Spring容器
            //registerSingletonBean(beanName, service);
            if (!beanName.equals(alias)) {
                registerAlise(beanName, alias);
            }
            
            //注册处理的业务类型
            registeType2Service(service);
        }
        
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
            if (Modifier.isInterface(bdType.getModifiers())
                    || Modifier.isAbstract(bdType.getModifiers())) {
                //如果是接口或抽象类直接跳过
                continue;
            }
            if (type2serviceMap.containsKey(bdType)
                    || DataDict.class.equals(bdType)) {
                //如果已经存在对应的业务逻辑层
                continue;
            }
            
            BasicDataService<? extends BasicData> serviceTemp = null;
            if (bdType.isAnnotationPresent(
                    com.tx.component.basicdata.annotation.BasicDataType.class)
                    && !StringUtils.isEmpty(bdType
                            .getAnnotation(
                                    com.tx.component.basicdata.annotation.BasicDataType.class)
                            .module())
                    && !this.module.equals(bdType
                            .getAnnotation(
                                    com.tx.component.basicdata.annotation.BasicDataType.class)
                            .module())) {
                serviceTemp = buildDefaultRemoteBasicDataService(this.module,
                        bdType);
            } else {
                serviceTemp = buildDefaultDBBasicDataService(this.module,
                        bdType);
            }
            
            //注册业务层逻辑
            if (serviceTemp != null) {
                registeType2Service(serviceTemp);
            }
        }
        
        //初始化基础数据类型
        initBasicDataType();
    }
    
    @SuppressWarnings("rawtypes")
    private void registeType2Service(BasicDataService service) {
        if (DataDict.class.equals(service.type())) {
            return;
        }
        AssertUtils.notNull(service.type(), "type is null.");
        AssertUtils.notNull(service, "service is null.");
        
        AssertUtils.isTrue(!type2serviceMap.containsKey(service.type()),
                "type:{} : service :{} is exist.",
                new Object[] { service.type(), service });
        AssertUtils.isTrue(!code2serviceMap.containsKey(service.code()),
                "code:{} : service :{} is exist.",
                new Object[] { service.code(), service });
        
        type2serviceMap.put(service.type(), service);
        code2serviceMap.put(service.code(), service);
    }
    
    /**
     * 初始化基础数据类型<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void initBasicDataType() {
        List<BasicDataService<?>> services = getAllBasicDataServices();
        //List<BasicDataType> resListOfCfg = new ArrayList<>();
        for (BasicDataService<? extends BasicData> s : services) {
            Class<? extends BasicData> type = s.type();
            String code = s.code();
            String tableName = s.tableName();
            String name = s.type().getSimpleName();
            String moduleTemp = s.module();
            AssertUtils.notNull(type,
                    "type is null.BasicDataService:{}",
                    new Object[] { s });
            AssertUtils.notEmpty(code,
                    "code is empty.BasicDataService:{}",
                    new Object[] { s });
            AssertUtils.notEmpty(tableName,
                    "tableName is empty.BasicDataService:{}",
                    new Object[] { s });
            
            BasicDataType bdType = new BasicDataType();
            bdType.setCode(code);
            bdType.setType(type);
            bdType.setTableName(tableName);
            bdType.setName(name);
            bdType.setModifyAble(false);
            if (StringUtils.isEmpty(moduleTemp)) {
                bdType.setModule(this.module);
            } else {
                bdType.setModule(moduleTemp);
            }
            
            if (type.isAnnotationPresent(
                    com.tx.component.basicdata.annotation.BasicDataType.class)) {
                com.tx.component.basicdata.annotation.BasicDataType anno = type
                        .getAnnotation(
                                com.tx.component.basicdata.annotation.BasicDataType.class);
                //读取注解中值
                bdType.setCommon(anno.common());
                bdType.setViewType(anno.viewType());
                bdType.setRemark(anno.remark());
                
                if (StringUtils.isNotEmpty(anno.name())) {
                    bdType.setName(anno.name());//覆写名称
                }
            }
            
            //resListOfCfg.add(bdType);
            this.basicDataTypeService.insert(bdType);
        }
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
        
        BasicDataService<BDTYPE> service = (BasicDataService<BDTYPE>) type2serviceMap
                .get(type);
        return service;
    }
    
    /**
      * 获取所有注册的基础数据业务方法<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<BasicDataService<?>> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<BasicDataService<?>> getAllBasicDataServices() {
        List<BasicDataService<?>> resList = new ArrayList<BasicDataService<?>>(
                type2serviceMap.values());
        return resList;
    }
    
    /**
     * @param 对packages进行赋值
     */
    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
}
