/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月13日
 * <修改描述:>
 */
package com.tx.core.dbscript.initializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.MapUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;

/**
 * 表初始化加载器，主要适用于生成脚本阶段<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TableInitializerLoader4Generator {
    
    private boolean initialized;
    
    private ApplicationContext applicationContext;
    
    private BeanDefinitionRegistry beanDefinitionRegistry;
    
    private String basePackages;
    
    private Map<String, TableInitializer> tableInitializerMap = new HashMap<>();
    
    /** <默认构造函数> */
    public TableInitializerLoader4Generator(
            ApplicationContext applicationContext,
            BeanDefinitionRegistry beanDefinitionRegistry,
            String basePackages) {
        super();
        this.applicationContext = applicationContext;
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.basePackages = basePackages;
        
        initialize();
    }
    
    /** 初始化 */
    public void initialize() {
        if (this.initialized) {
            return;
        }
        AssertUtils.notNull(applicationContext, "applicationContext is null.");
        AssertUtils.notEmpty(basePackages, "basePackages is empty.");
        
        Set<Class<?>> tableInitalizerClasses = new HashSet<>();
        for (Entry<String, TableInitializer> entryTemp : applicationContext
                .getBeansOfType(TableInitializer.class).entrySet()) {
            tableInitalizerClasses
                    .add(AopUtils.getTargetClass(entryTemp.getValue()));
            
            tableInitializerMap.put(entryTemp.getKey(), entryTemp.getValue());
        }
        
        Set<Class<? extends TableInitializer>> initializerTypes = ClassScanUtils
                .scanByParentClass(TableInitializer.class, basePackages);
        for (Class<? extends TableInitializer> type : initializerTypes) {
            if (tableInitalizerClasses.contains(type)) {
                continue;
            }
            
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                    .genericBeanDefinition(type);
            //builder.setAutowireMode(autowireMode)
            registerBeanDefinition(type.getName(), builder.getBeanDefinition());
            
            tableInitializerMap.put(type.getName(),
                    applicationContext.getBean(type.getName(),
                            TableInitializer.class));
        }
        
        this.initialized = true;
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
     * 根据表初始化定义生成升级脚本<br/>
     * <功能详细描述>
     * @param tableDDLExecutor
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String generateUpdateScript(TableDDLExecutor tableDDLExecutor) {
        if (MapUtils.isEmpty(tableInitializerMap)) {
            return "";
        }
        
        BatchTableInitializer bti = new BatchTableInitializer(
                new ArrayList<>(this.tableInitializerMap.values()));
        
        return bti.initialize(tableDDLExecutor, false);
    }
    
}
