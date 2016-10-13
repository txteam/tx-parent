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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.core.AliasRegistry;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.BasicDataType;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.BasicDataTypeService;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.initable.helper.ConfigInitAbleHelper;
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
    
    private Logger logger = LoggerFactory.getLogger(BasicDataServiceRegistry.class);
    
    private static BasicDataServiceRegistry factory;
    
    private static Map<Class<?>, BasicDataService<?>> type2serviceMap = new HashMap<Class<?>, BasicDataService<?>>();
    
    private static Map<String, BasicDataService<?>> typecode2serviceMap = new HashMap<String, BasicDataService<?>>();
    
    private ApplicationContext applicationContext;
    
    private SingletonBeanRegistry singletonBeanRegistry;
    
    private BeanDefinitionRegistry beanDefinitionRegistry;
    
    private AliasRegistry aliasRegistry;
    
    @Resource(name = "basicdata.dataDictService")
    private DataDictService dataDictService;
    
    @Resource(name = "basicdata.basicDataTypeService")
    private BasicDataTypeService basicDataTypeService;
    
    @Resource(name = "basicdata.transactionTemplate")
    private TransactionTemplate transactionTemplate;
    
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
        
        AssertUtils.isInstanceOf(AliasRegistry.class,
                beanFactory,
                "beanFactory is not SingletonBeanRegistry instance.");
        this.aliasRegistry = (AliasRegistry) beanFactory;
    }
    
    /**
     * @desc 向spring容器注册BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    protected void registerBeanDefinition(String beanName,
            BeanDefinition beanDefinition) {
        if (!this.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            logger.info("动态注入基础数据业务层定义: beanName:{} Type:com.tx.component.basicdata.context.DefaultBasicDataService",
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
            logger.info("注入基础数据业务层实例: beanName:{} Type:{}",
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
    public BasicDataService buildDefaultBasicDataService(
            Class<? extends BasicData> type) {
        String beanName = generateServiceBeanName(type);
        
        if (type.isAssignableFrom(TreeAbleBasicData.class)) {
            //Class<?> defaultServiceType = DefaultTreeAbleBasicDataServiceFactory.class;
            Class<?> defaultServiceType = DefaultTreeAbleBasicDataService.class;
            
            //                        AnnotatedGenericBeanDefinition bd = new AnnotatedGenericBeanDefinition(
            //                                defaultServiceType);
            //                        bd.setAttribute("type", type);
            //                        registerBeanDefinition(beanName, bd);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("type", type);
            registerBeanDefinition(beanName, builder.getBeanDefinition());
        } else {
            Class<?> defaultServiceType = DefaultBasicDataService.class;
            //                        AnnotatedGenericBeanDefinition bd = new AnnotatedGenericBeanDefinition(
            //                                defaultServiceType);
            //                        bd.setAttribute("type", type);
            //                        registerBeanDefinition(beanName, bd);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(defaultServiceType);
            builder.addPropertyValue("type", type);
            registerBeanDefinition(beanName, builder.getBeanDefinition());
        }
        
        //利用有参构造函数,(Object) type
        BasicDataService service = (BasicDataService) this.applicationContext.getBean(beanName);
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
        Map<String, BasicDataService> basicDataServiceMap = this.applicationContext.getBeansOfType(BasicDataService.class);
        for (Entry<String, BasicDataService> entry : basicDataServiceMap.entrySet()) {
            BasicDataService service = entry.getValue();
            String beanName = entry.getKey();
            if (service.type() == null || DataDict.class.equals(service.type())) {
                continue;
            }
            String alias = generateServiceBeanName(service.type());
            //注册单例Bean进入Spring容器
            //registerSingletonBean(beanName, service);
            registerAlise(beanName, alias);
            
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
            if (type2serviceMap.containsKey(bdType)
                    || DataDict.class.equals(bdType)) {
                //如果已经存在对应的业务逻辑层
                continue;
            }
            BasicDataService<? extends BasicData> bdService = buildDefaultBasicDataService(bdType);
            //注册业务层逻辑
            registeType2Service(bdService);
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
        AssertUtils.isTrue(!typecode2serviceMap.containsKey(service.code()),
                "typecoe:{} : service :{} is exist.",
                new Object[] { service.type(), service });
        
        type2serviceMap.put(service.type(), service);
        typecode2serviceMap.put(service.code(), service);
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
        ConfigInitAbleHelper<BasicDataType> helper = new ConfigInitAbleHelper<BasicDataType>() {
            /**
             * @param ciaOfDBTemp
             * @param ciaOfConfig
             * @return
             */
            @Override
            protected boolean isNeedUpdate(BasicDataType ciaOfDBTemp,
                    BasicDataType ciaOfConfig) {
                if (!StringUtils.equals(ciaOfDBTemp.getCode(),
                        ciaOfConfig.getCode())) {
                    return true;
                }
                if (!StringUtils.equals(ciaOfDBTemp.getName(),
                        ciaOfConfig.getName())) {
                    return true;
                }
                if (!StringUtils.equals(ciaOfDBTemp.getModule(),
                        ciaOfConfig.getModule())) {
                    return true;
                }
                if (!StringUtils.equals(ciaOfDBTemp.getTableName(),
                        ciaOfConfig.getTableName())) {
                    return true;
                }
                if (!StringUtils.equals(ciaOfDBTemp.getRemark(),
                        ciaOfConfig.getRemark())) {
                    return true;
                }
                if (ciaOfDBTemp.isCommon() != ciaOfConfig.isCommon()) {
                    return true;
                }
                if (!ciaOfDBTemp.getViewType()
                        .equals(ciaOfConfig.getViewType())) {
                    return true;
                }
                return false;
            }
            
            /**
             * @param ciaOfDB
             * @param ciaOfCfg
             */
            @Override
            protected void doBeforeUpdate(BasicDataType ciaOfDB,
                    BasicDataType ciaOfCfg) {
                ciaOfDB.setModule(ciaOfCfg.getModule());
                ciaOfDB.setCode(ciaOfCfg.getCode());
                ciaOfDB.setName(ciaOfCfg.getName());
                ciaOfDB.setTableName(ciaOfCfg.getTableName());
                ciaOfDB.setRemark(ciaOfCfg.getRemark());
                
                ciaOfDB.setCommon(ciaOfCfg.isCommon());
                ciaOfDB.setViewType(ciaOfCfg.getViewType());
            }
            
            @Override
            protected String getSingleCode(BasicDataType cia) {
                return cia.getType().getName();
            }
            
            @Override
            protected List<BasicDataType> queryListFromDB() {
                return basicDataTypeService.queryList(null, null);
            }
            
            @Override
            protected List<BasicDataType> queryListFromConfig() {
                List<BasicDataService<?>> services = getAllBasicDataServices();
                List<BasicDataType> resListOfCfg = new ArrayList<>();
                for (BasicDataService<? extends BasicData> s : services) {
                    Class<? extends BasicData> type = s.type();
                    String code = s.code();
                    String tableName = s.tableName();
                    String name = s.type().getSimpleName();
                    
                    BasicDataType bdType = new BasicDataType();
                    bdType.setCode(code);
                    bdType.setType(type);
                    bdType.setTableName(tableName);
                    bdType.setName(name);
                    bdType.setModifyAble(false);
                    bdType.setModule("basicdata");
                    
                    if (type.isAnnotationPresent(com.tx.component.basicdata.annotation.BasicDataType.class)) {
                        com.tx.component.basicdata.annotation.BasicDataType anno = type.getAnnotation(com.tx.component.basicdata.annotation.BasicDataType.class);
                        //读取注解中值
                        bdType.setCommon(anno.common());
                        bdType.setViewType(anno.viewType());
                        bdType.setRemark(anno.remark());
                        
                        if (StringUtils.isNotEmpty(anno.name())) {
                            bdType.setName(anno.name());//覆写名称
                        }
                        if (StringUtils.isNotEmpty(anno.module())) {
                            bdType.setModule(anno.module().toLowerCase());//覆写模块名称
                        }
                    }
                    resListOfCfg.add(bdType);
                }
                return resListOfCfg;
            }
            
            @Override
            protected void batchUpdate(List<BasicDataType> needUpdateList) {
                for (BasicDataType bdType : needUpdateList) {
                    basicDataTypeService.updateById(bdType);
                }
            }
            
            @Override
            protected void batchInsert(List<BasicDataType> needInsertList) {
                for (BasicDataType bdType : needInsertList) {
                    basicDataTypeService.insert(bdType);
                }
            }
        };
        helper.init(this.transactionTemplate);
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
    public void setPackages(String packages) {
        this.packages = packages;
    }
}
