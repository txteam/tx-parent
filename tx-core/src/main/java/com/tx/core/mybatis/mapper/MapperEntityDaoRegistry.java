/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月9日
 * <修改描述:>
 */
package com.tx.core.mybatis.mapper;

import java.util.HashMap;
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

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.annotation.MapperEntity;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.util.ClassScanUtils;

/**
 * 实体自动持久注册机<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MapperEntityDaoRegistry implements ApplicationContextAware,
        InitializingBean, BeanFactoryAware, BeanNameAware {
    
    private Logger logger = LoggerFactory
            .getLogger(MapperEntityDaoRegistry.class);
    
    private static String beanName;
    
    private static ApplicationContext applicationContext;
    
    private static MapperEntityDaoRegistry instance;
    
    private AliasRegistry aliasRegistry;
    
    private BeanDefinitionRegistry beanDefinitionRegistry;
    
    private SingletonBeanRegistry singletonBeanRegistry;
    
    /** 实体持久层实现映射 */
    private static Map<Class<?>, String> type2nameMap = new HashMap<Class<?>, String>();
    
    /** 实体持久层实现映射 */
    private static Map<String, MapperEntityDao<?, ?>> name2daoMap = new HashMap<String, MapperEntityDao<?, ?>>();
    
    /** 扫描包范围 */
    private String basePackages;
    
    /** mybatisDaoSupport句柄 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public MapperEntityDaoRegistry() {
        super();
    }
    
    /** <默认构造函数> */
    public MapperEntityDaoRegistry(String basePackages,
            MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.basePackages = basePackages;
        this.myBatisDaoSupport = myBatisDaoSupport;
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
    public static MapperEntityDaoRegistry getInstance() {
        AssertUtils.notEmpty(MapperEntityDaoRegistry.beanName,
                "beanName is empty.");
        
        if (MapperEntityDaoRegistry.instance == null) {
            MapperEntityDaoRegistry.instance = applicationContext.getBean(
                    MapperEntityDaoRegistry.beanName,
                    MapperEntityDaoRegistry.class);
        }
        
        AssertUtils.notNull(MapperEntityDaoRegistry.instance,
                "factory not inited.");
        
        return instance;
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
    public static MapperEntityDao<?, ?> getEntityDao(Class<?> modelType) {
        AssertUtils.notNull(modelType, "modelType is null.");
        AssertUtils.isTrue(type2nameMap.containsKey(modelType),
                "type2nameMap is not contains:{}",
                new Object[] { modelType });
        AssertUtils.notNull(MapperEntityDaoRegistry.applicationContext,
                "applicationContext is null.");
        
        //实体持久层Bean名称
        String entityDaoName = type2nameMap.get(modelType);
        MapperEntityDao<?, ?> entityDao = null;
        if (name2daoMap.containsKey(entityDaoName)) {
            entityDao = name2daoMap.get(entityDaoName);
        } else {
            entityDao = applicationContext.getBean(entityDaoName,
                    MapperEntityDao.class);
            name2daoMap.put(entityDaoName, entityDao);
        }
        
        return entityDao;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        MapperEntityDaoRegistry.applicationContext = applicationContext;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String beanName) {
        MapperEntityDaoRegistry.beanName = beanName;
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
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void afterPropertiesSet() throws Exception {
        //查找spring容器中已经存在的业务层
        Map<String, MapperEntityDao> basicDataServiceMap = MapperEntityDaoRegistry.applicationContext
                .getBeansOfType(MapperEntityDao.class);
        for (Entry<String, MapperEntityDao> entry : basicDataServiceMap
                .entrySet()) {
            MapperEntityDao<?, ?> dao = entry.getValue();
            String beanName = entry.getKey();
            if (dao.getEntityType() == null
                    || !Class.class.isInstance(dao.getEntityType())
                    || Object.class.equals(dao.getEntityType())) {
                continue;
            }
            Class<?> beanType = (Class<?>) dao.getEntityType();
            if (!beanType.isAnnotationPresent(MapperEntity.class)) {
                continue;
            }
            
            String generateDaoName = generateDaoNameByType(beanType);
            //注册单例Bean进入Spring容器
            //registerSingletonBean(beanName, service);
            if (!beanName.equals(generateDaoName)) {
                registerAlise(beanName, generateDaoName);
            }
            //注册处理的业务类型
            MapperEntityDaoRegistry.type2nameMap.put(beanType, generateDaoName);
        }
        
        //扫描遍历，如果已经存在持久层的实体类，则不再添加
        String[] basePackageArray = StringUtils
                .splitByWholeSeparator(basePackages, ",");
        Set<Class<?>> types = ClassScanUtils
                .scanByAnnotation(MapperEntity.class, basePackageArray);
        
        for (Class<?> beanType : types) {
            //注册实体持久层
            BeanDefinition daoBeanDefinition = generateEntityDaoBeanDefinition(
                    beanType, myBatisDaoSupport);
            String entityDaoName = generateDaoNameByType(beanType);
            registerBeanDefinition(entityDaoName, daoBeanDefinition);
            MapperEntityDaoRegistry.type2nameMap.put(beanType, entityDaoName);
        }
    }
    
    /**
     * 注册实体的持久层实现<br/>
     * <功能详细描述>
     * @param registry
     * @param beanType
     * @param myBatisDaoSupport [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private BeanDefinition generateEntityDaoBeanDefinition(Class<?> beanType,
            MyBatisDaoSupport myBatisDaoSupport) {
        AssertUtils.notNull(beanType, "beanType is null.");
        AssertUtils.notNull(myBatisDaoSupport, "myBatisDaoSupport is null.");
        
        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .genericBeanDefinition(MapperEntityDaoFactory.class);
        builder.addConstructorArgValue(beanType);
        builder.addConstructorArgValue(myBatisDaoSupport);
        
        return builder.getBeanDefinition();
    }
    
    /**
     * 根据类型获取bean名称<br/>
     * <功能详细描述>
     * @param beanType
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String generateDaoNameByType(Class<?> beanType) {
        AssertUtils.notNull(beanType, "beanType is null.");
        
        String beanName = beanType.getName() + "Dao";
        return beanName;
    }
    
    /**
     * @return 返回 basePackages
     */
    public String getBasePackages() {
        return basePackages;
    }
    
    /**
     * @param 对basePackages进行赋值
     */
    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
    
    /**
     * @return 返回 myBatisDaoSupport
     */
    public MyBatisDaoSupport getMyBatisDaoSupport() {
        return this.myBatisDaoSupport;
    }
    
    /**
     * @param 对myBatisDaoSupport进行赋值
     */
    public void setMyBatisDaoSupport(MyBatisDaoSupport myBatisDaoSupport) {
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
}
