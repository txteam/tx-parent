/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月10日
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.service.impl.LocalConfigPropertyManager;
import com.tx.component.configuration.util.ConfigContextUtils;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 配置实体工厂lei
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigEntityFactory implements InitializingBean {
    
    /** 转换业务层 */
    private ConfigurableConversionService conversionService = new DefaultConversionService();
    
    /** 事务控制 */
    private TransactionTemplate transactionTemplate;
    
    /** 本地配置属性管理器 */
    private LocalConfigPropertyManager localConfigPropertyManager;
    
    /** 配置实体初始化 */
    private ConfigEntityInitializer configEntityInitializer;
    
    /** 模块对类型对前置映射 */
    private Map<Class<?>, Object> configEntityMap = new HashMap<Class<?>, Object>();
    
    /** <默认构造函数> */
    public ConfigEntityFactory() {
        super();
    }
    
    /** <默认构造函数> */
    public ConfigEntityFactory(TransactionTemplate transactionTemplate,
            LocalConfigPropertyManager localConfigPropertyManager) {
        super();
        this.transactionTemplate = transactionTemplate;
        this.localConfigPropertyManager = localConfigPropertyManager;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(this.conversionService,
                "conversionService is null.");
        AssertUtils.notNull(this.transactionTemplate,
                "transactionTemplate is null.");
        AssertUtils.notNull(this.localConfigPropertyManager,
                "localConfigPropertyManager is null.");
        
        //配置实体初始化器
        this.configEntityInitializer = new ConfigEntityInitializer(
                conversionService, transactionTemplate,
                localConfigPropertyManager);
    }
    
    /**
     * 安装配置实体<br/>
     * <功能详细描述>
     * @param prefix
     * @param configEntityType
     * @return [参数说明]
     * 
     * @return C [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public synchronized List<ConfigProperty> parse(String prefix,
            Class<?> configEntityType) {
        AssertUtils.notNull(configEntityType, "configEntityType is null.");
        
        //预处理前置
        prefix = ConfigContextUtils.preprocessPrefix(prefix, configEntityType);
        List<ConfigProperty> resList = this.configEntityInitializer
                .parse(prefix, configEntityType);
        return resList;
    }
    
    /**
     * 安装配置实体<br/>
     * <功能详细描述>
     * @param prefix
     * @param configEntityType
     * @return [参数说明]
     * 
     * @return C [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public synchronized <C> C setup(String prefix, Class<C> configEntityType) {
        AssertUtils.notNull(configEntityType, "configEntityType is null.");
        
        //预处理前置
        prefix = ConfigContextUtils.preprocessPrefix(prefix, configEntityType);
        if (configEntityMap.containsKey(configEntityType)) {
            //如果存在则直接返回
            Object proxy = configEntityMap.get(configEntityType);
            AssertUtils.isTrue(configEntityType.isInstance(proxy),
                    "proxy:{} should type:{} instance.",
                    new Object[] { proxy, configEntityType });
            return (C) proxy;
        }
        
        //构建对象
        C target = this.configEntityInitializer.install(prefix,
                configEntityType);
        ConfigEntityInvocationHandler invocationHandler = new ConfigEntityInvocationHandler(
                prefix, target);
        invocationHandler.setConversionService(this.conversionService);
        invocationHandler.init();
        
        //被代理类
        // 代理类，采用cglib
        // 核心类
        Enhancer enhancer = new Enhancer();
        // 确定父类
        enhancer.setSuperclass(configEntityType);
        enhancer.setCallback(invocationHandler);
        C proxy = (C) enhancer.create();
        
        //将构建出的对象写入映射中
        configEntityMap.put(configEntityType, proxy);
        return proxy;
    }
    
    /**
     * 卸载<br/>
     * <功能详细描述>
     * @param prefix
     * @param configEntityType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public synchronized void uninstall(String prefix,
            Class<?> configEntityType) {
        //预处理前置
        prefix = ConfigContextUtils.preprocessPrefix(prefix, configEntityType);
        if (!configEntityMap.containsKey(configEntityType)) {
            //如果不存在则直接返回
            return;
        }
        
        this.configEntityInitializer.uninstall(prefix, configEntityType);
        configEntityMap.remove(configEntityType);
    }
    
    /**
     * @param 对conversionService进行赋值
     */
    public void setConversionService(
            ConfigurableConversionService conversionService) {
        this.conversionService = conversionService;
    }
    
    /**
     * @param 对localConfigPropertyManager进行赋值
     */
    public void setLocalConfigPropertyManager(
            LocalConfigPropertyManager localConfigPropertyManager) {
        this.localConfigPropertyManager = localConfigPropertyManager;
    }
    
    /**
     * @param 对transactionTemplate进行赋值
     */
    public void setTransactionTemplate(
            TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
