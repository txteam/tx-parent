/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleItemLoader;
import com.tx.component.rule.loader.RuleItemParam;
import com.tx.component.rule.loader.RuleItemPersister;
import com.tx.component.rule.loader.RuleRegister;
import com.tx.component.rule.loader.RuleTypeEnum;
import com.tx.core.dbscript.TableDefinition;
import com.tx.core.dbscript.XMLTableDefinition;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.cache.LazyCacheValueFactory;
import com.tx.core.support.cache.TransactionAwareLazyEhCacheMap;

/**
 * 规则容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleContextBuilder extends RuleContextConfigurator implements
        BeanNameAware, ApplicationContextAware {
    
    /** ruleItem脚本路径 */
    private static final String RULEITEM_SCRIPT_PATH = "classpath:/com/tx/component/rule/script/RULE_RULEITEM.xml";
    
    /** ruleItemByteParam脚本路径 */
    private static final String RULEITEM_BYTE_PARAM_SCRIPT_PATH = "classpath:/com/tx/component/rule/script/RULE_BYTE_PARAM.xml";
    
    /** ruleItemValueParam脚本路径 */
    private static final String RULEITEM_VALUE_PARAM_SCRIPT_PATH = "classpath:/com/tx/component/rule/script/RULE_VALUE_PARAM.xml";
    
    /** bean名  */
    protected String beanName;
    
    /** 当前spring容器 */
    protected ApplicationContext applicationContext;
    
    /** 规则项代理映 */
    protected Map<String, Rule> ruleMap;
    
    /** 规则验证器映射 */
    protected Map<RuleTypeEnum, RuleRegister<? extends Rule>> ruleRegisterMap = new HashMap<RuleTypeEnum, RuleRegister<? extends Rule>>();
    
    @Resource(name = "ruleItemPersister")
    protected RuleItemPersister ruleItemPersister;
    
    /** 规则加载器 */
    private List<RuleItemLoader> ruleItemLoaderList = new ArrayList<RuleItemLoader>();
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
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
        logger.info("开始初始化规则容器....");
        super.afterPropertiesSet();
        if (this.databaseSchemaUpdate) {
            //自动执行脚本升级
            databaseSchemaUpdate();
        }
        
        logger.info("   加载规则注册器...");
        @SuppressWarnings("rawtypes")
        Collection<RuleRegister> ruleRegisters = this.applicationContext.getBeansOfType(RuleRegister.class)
                .values();
        registeRuleRegister(ruleRegisters);
        
        logger.info("   初始化规则加载器....");
        Collection<RuleItemLoader> ruleItemLoaders = this.applicationContext.getBeansOfType(RuleItemLoader.class)
                .values();
        registeRuleItemLoader(ruleItemLoaders);
        
        logger.info("   初始化规则项....");
        loadRule();
        
        logger.info("初始化规则容器成功...");
    }
    
    /** 加载规则项 */
    private void loadRule() {
        //加载不可编辑的规则集合，放入unModifyAbleLocalMap
        Map<String, Rule> unModifyRuleItemMap = new HashMap<String, Rule>();
        for (RuleItemLoader ruleItemLoaderTemp : this.ruleItemLoaderList) {
            List<RuleItem> resListTemp = ruleItemLoaderTemp.load();
            if (CollectionUtils.isEmpty(resListTemp)) {
                continue;
            }
            //迭代写入
            for (RuleItem ruleItemTemp : resListTemp) {
                Rule ruleTemp = buildRuleByRuleItem(ruleItemTemp);
                unModifyRuleItemMap.put(ruleTemp.getKey(), ruleTemp);
            }
        }
        
        Map<String, Rule> ruleMapTemp = null;
        AssertUtils.notNull(ehcache, "ehcache is null.");
        ruleMapTemp = new TransactionAwareLazyEhCacheMap<Rule>(
                unModifyRuleItemMap, new EhCacheCache(ehcache),
                new LazyCacheValueFactory<String, Rule>() {
                    /**
                     * @param key
                     * @return
                     */
                    @Override
                    public Rule find(String key) {
                        RuleItem ruleItem = ruleItemPersister.find(key);
                        if (ruleItem == null) {
                            return null;
                        }
                        Rule ruleTemp = buildRuleByRuleItem(ruleItem);
                        return ruleTemp;
                    }
                    
                    /**
                     * @return
                     */
                    @Override
                    public Map<String, Rule> listMap() {
                        Map<String, Rule> resMap = new HashMap<String, Rule>();
                        List<RuleItem> resListTemp = ruleItemPersister.list();
                        if (CollectionUtils.isEmpty(resListTemp)) {
                            return resMap;
                        }
                        //迭代写入
                        for (RuleItem ruleItemTemp : resListTemp) {
                            Rule ruleTemp = buildRuleByRuleItem(ruleItemTemp);
                            resMap.put(ruleTemp.getKey(), ruleTemp);
                        }
                        return resMap;
                    }
                }, false);
        for (Entry<String, Rule> entryTemp : ruleMapTemp.entrySet()) {
            logger.info("Load Rule. ruleKey:{} serviceType:{} state:{}",
                    new Object[] { entryTemp.getValue().getKey(),
                            entryTemp.getValue().getServiceType(),
                            entryTemp.getValue().getState() });
        }
        this.ruleMap = ruleMapTemp;
    }
    
    /**
      * 根据规则项构建规则<br/>
      *<功能详细描述>
      * @param ruleItem
      * @return [参数说明]
      * 
      * @return Rule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected Rule buildRuleByRuleItem(RuleItem ruleItem) {
        validateRuleItem(ruleItem);
        
        RuleRegister<? extends Rule> ruleRegister = this.ruleRegisterMap.get(ruleItem.getRuleType());
        Rule rule = ruleRegister.registe(ruleItem);
        return rule;
    }
    
    /**
      * 校验ruleItem是否合法<br/>
      *<功能详细描述>
      * @param ruleItem [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void validateRuleItem(RuleItem ruleItem) {
        AssertUtils.notNull(ruleItem, "ruleItem is null.");
        AssertUtils.notEmpty(ruleItem.getKey(), "ruleItem.key is empty.");
        AssertUtils.notEmpty(ruleItem.getServiceType(),
                "ruleItem.serviceType is empty.");
        AssertUtils.notNull(ruleItem.getRuleType(),
                "ruleItem.ruleType is null.");
        
        RuleRegister<? extends Rule> ruleRegisterTemp = ruleRegisterMap.get(ruleItem.getRuleType());
        AssertUtils.isTrue(ruleRegisterTemp != null,
                "ruleType register not matched. ruleType:{}",
                new Object[] { ruleItem.getRuleType() });
        Set<RuleItemParam> ruleItemParamSet = ruleRegisterTemp.ruleItemParam();
        for (RuleItemParam ruleItemParamTemp : ruleItemParamSet) {
            if (!ruleItemParamTemp.isRequired()) {
                continue;
            }
            switch (ruleItemParamTemp.getType()) {
                case BYTE:
                    AssertUtils.notNull(ruleItem.getByteParam(ruleItemParamTemp.getKey()),
                            "ruleItem param:{} is null.",
                            new Object[] { ruleItemParamTemp.getKey() });
                    break;
                case VALUE:
                    AssertUtils.notNull(ruleItem.getValueParam(ruleItemParamTemp.getKey()),
                            "ruleItem param:{} is null.",
                            new Object[] { ruleItemParamTemp.getKey() });
                    break;
                case OBJECT:
                    AssertUtils.notNull(ruleItem.getByteParam(ruleItemParamTemp.getKey()),
                            "ruleItem param:{} is null.",
                            new Object[] { ruleItemParamTemp.getKey() });
                    break;
                default:
                    break;
            }
        }
    }
    
    /**
      * 注册规则项目加载器<br/>
      *<功能详细描述>
      * @param ruleItemLoaders [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void registeRuleItemLoader(
            Collection<RuleItemLoader> ruleItemLoaders) {
        if (CollectionUtils.isEmpty(ruleItemLoaders)) {
            return;
        }
        //将对应的规则加载器压入对应的规则项加载器中
        for (RuleItemLoader ruleItemLoaderTemp : ruleItemLoaders) {
            this.ruleItemLoaderList.add(ruleItemLoaderTemp);
        }
    }
    
    /**
      * 向容器中注册规则注册机<br/> 
      *<功能详细描述>
      * @param ruleRegisters [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void registeRuleRegister(
            @SuppressWarnings("rawtypes") Collection<RuleRegister> ruleRegisters) {
        if (CollectionUtils.isEmpty(ruleRegisters)) {
            return;
        }
        for (RuleRegister<?> ruleRegisterTemp : ruleRegisters) {
            this.ruleRegisterMap.put(ruleRegisterTemp.ruleType(),
                    ruleRegisterTemp);
        }
    }
    
    /**
     * 规则容器相关表脚本自动创建或升级<br/> 
     *<功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void databaseSchemaUpdate() {
        AssertUtils.notNull(this.dbScriptExecutorContext,
                "dbScriptExecutorContext is null.");
        logger.info("   规则容器允许脚本自动升级.开始自动执行规则容器脚本检测.tableSuffix:{}",
                this.tableSuffix);
        
        Map<String, String> replaceDataMap = new HashMap<String, String>();
        replaceDataMap.put("tableSuffix", this.tableSuffix);
        TableDefinition ruleItemTableDefinition = new XMLTableDefinition(
                RULEITEM_SCRIPT_PATH, replaceDataMap);
        TableDefinition ruleItemByteParamTableDefinition = new XMLTableDefinition(
                RULEITEM_BYTE_PARAM_SCRIPT_PATH, replaceDataMap);
        TableDefinition ruleItemValueParamTableDefinition = new XMLTableDefinition(
                RULEITEM_VALUE_PARAM_SCRIPT_PATH, replaceDataMap);
        //执行脚本
        logger.info("   ------检测ruleItemTable:");
        this.dbScriptExecutorContext.createOrUpdateTable(ruleItemTableDefinition);
        logger.info("   ------检测ruleItemTable:");
        this.dbScriptExecutorContext.createOrUpdateTable(ruleItemByteParamTableDefinition);
        logger.info("   ------检测ruleItemTable:");
        this.dbScriptExecutorContext.createOrUpdateTable(ruleItemValueParamTableDefinition);
    }
}
