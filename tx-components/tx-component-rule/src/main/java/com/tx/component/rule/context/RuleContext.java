/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.store.chm.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MultiValueMap;

import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.exceptions.RuleRegisteException;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.component.rule.service.SimplePersistenceRuleService;
import com.tx.component.rule.support.RuleSession;
import com.tx.component.rule.support.RuleSessionFactory;
import com.tx.component.rule.support.impl.DefaultRuleSessionFactory;
import com.tx.component.rule.transation.RuleSessionTransactionFactory;
import com.tx.component.rule.transation.impl.DefaultRuleSessionTransactionFactory;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.spring.event.BeansInitializedEvent;
import com.tx.core.support.cache.ehcache.SimpleEhcacheMap;
import com.tx.core.support.cache.ehcache.SimpleMultiValueEhcacheMap;

/**
 * 规则容器<br/>
 *     系统启动时通过该规则容器加载已有的规则<br/>
 *     可以通过该容器实现规则重加载，添加新的规则<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleContext implements BeanNameAware, FactoryBean<RuleContext>,
        InitializingBean, ApplicationContextAware {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(RuleContext.class);
    
    /** 单例的rule容器 */
    private static Map<String, RuleContext> ruleContextMapping = new HashMap<String, RuleContext>();
    
    /** spring容器引用 */
    private ApplicationContext applicationContext;
    
    /** rulecontext name */
    private String beanName;
    
    /** 规则会话事务工厂  */
    private RuleSessionTransactionFactory ruleSessionTransactionFactory;
    
    /** 规则会话工厂类 */
    private RuleSessionFactory ruleSessionFactory;
    
    /** 缓存 */
    @Resource(name = "cache")
    private Ehcache ehcache;
    
    /** 持久化规则业务层 */
    @Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    /** 注册的规则加载器 */
    private List<RuleLoader> registeredRuleLoaderList = new ArrayList<RuleLoader>();
    
    /** 规则验证器映射 */
    private Map<RuleTypeEnum, RuleRegister<? extends Rule>> ruleRegisterMap = new HashMap<RuleTypeEnum, RuleRegister<? extends Rule>>();
    
    /** 规则缓存:key为 serviceType + "." + rule */
    private Map<String, Rule> ruleKeyMapCache;
    
    /** 规则缓存:key为 rule 如果存在两个同rule serviceType时，则在该缓存中不进行存入 */
    private MultiValueMap<String, Rule> multiRuleMapCache;
    
    /** 是否加载完成 */
    private boolean loadOver = false;
    
    /** 最大等待加载时间 */
    private long maxLoadTimeout = 1000 * 60 * 5;
    
    /** 等待加载的线程映射 */
    private Map<Thread, Integer> waitThreadMap = new HashMap<Thread, Integer>();
    
    /**
     * <默认构造函数>
     */
    public RuleContext() {
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        logger.info("RuleContext name:{} Initializing setBeanName.");
        this.beanName = name;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        logger.info("RuleContext init applicationContext.");
        this.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("开始初始化规则容器....");
        
        this.ruleKeyMapCache = new SimpleEhcacheMap<String, Rule>(
                "cache.ruleKeyMapCache", ehcache,
                new ConcurrentHashMap<String, Rule>());
        this.multiRuleMapCache = new SimpleMultiValueEhcacheMap<String, Rule>(
                "cache.multiRuleMapCache", ehcache,
                new ConcurrentHashMap<String, List<Rule>>());
        
        logger.info("加载规则加载器....");
        Collection<RuleLoader> ruleLoaders = this.applicationContext.getBeansOfType(RuleLoader.class)
                .values();
        registeRuleLoader(ruleLoaders);
        logger.info("加载规则注册器...");
        @SuppressWarnings("rawtypes")
        Collection<RuleRegister> ruleRegisters = this.applicationContext.getBeansOfType(RuleRegister.class)
                .values();
        registeRuleRegister(ruleRegisters);
        
        if (ruleSessionFactory == null) {
            this.ruleSessionFactory = new DefaultRuleSessionFactory();
        }
        if (ruleSessionTransactionFactory == null) {
            this.ruleSessionTransactionFactory = new DefaultRuleSessionTransactionFactory();
        }
        
        init(this.applicationContext);
        
        RuleContext.ruleContextMapping.put(this.beanName, this);
        
        logger.info("加载规则注册器...");
    }
    
    /**
     * @param event
     */
    //@Override
    public void onApplicationEvent(BeansInitializedEvent<RuleLoader> event) {
        logger.info("onApplicationEvent listener...");
        if (RuleLoader.class.isAssignableFrom(event.getType())) {
            init(event.getApplicationContext());
        }
        logger.info("onApplicationEvent init success...");
    }
    
    /**
      * 注册规则加载器
      * <功能详细描述>
      * @param ruleLoader [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void registeRuleLoader(Collection<RuleLoader> ruleLoaders) {
        if (!CollectionUtils.isEmpty(ruleLoaders)) {
            registeredRuleLoaderList.addAll(ruleLoaders);
        }
    }
    
    /**
      * 注册规则验证器
      * <功能详细描述>
      * @param ruleValidator [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void registeRuleRegister(
            @SuppressWarnings("rawtypes") Collection<RuleRegister> ruleValidators) {
        if (!CollectionUtils.isEmpty(ruleValidators)) {
            for (RuleRegister<? extends Rule> ruleRegisterTemp : ruleValidators) {
                ruleRegisterMap.put(ruleRegisterTemp.ruleType(),
                        ruleRegisterTemp);
            }
        }
    }
    
    /**
      * 将规则注册入规则容器中
      * <功能详细描述>
      * @param spRule [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public Rule registeRule(SimplePersistenceRule spRule) {
        if (spRule == null || StringUtils.isEmpty(spRule.getRule())
                || StringUtils.isEmpty(spRule.getServiceType())
                || spRule.getRuleType() == null) {
            throw new RuleRegisteException(
                    "spRule or rule or serviceType or ruleType is null");
        }
        
        RuleRegister<? extends Rule> ruleRegisterTemp = ruleRegisterMap.get(spRule.getRuleType());
        if (ruleRegisterTemp == null) {
            throw new RuleRegisteException(
                    "ruleType:{} RuleRegister not exist.", spRule.getRuleType()
                            .toString());
        }
        
        //调用对应注册器方法，将规则注册入容器中
        final Rule realRule = ruleRegisterTemp.registe(spRule);
        if (realRule != null) {
            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                //如果在事务逻辑中执行
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        putInCache(realRule, true);
                    }
                });
            } else {
                //如果在非事务中执行
                putInCache(realRule, true);
            }
        } else {
            throw new RuleRegisteException(
                    "ruleType:{} RuleRegister call registe return null realRule.",
                    spRule.getRuleType().toString());
        }
        return realRule;
    }
    
    /**
      * 反注册规则
      * <功能详细描述>
      * @param rule [参数说明] serviceType.rule
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void unRegisteRule(String rule) {
        Rule ruleImpl = getRule(rule);
        if (ruleImpl == null) {
            return;
        }
        
        //删除持久化数据
        this.simplePersistenceRuleService.deleteById(ruleImpl.getId());
        
        //从缓存中移除对应规则
        removeFromCache(ruleImpl);
    }
    
    /**
      * 初始化容器
      * <功能详细描述>
      * @param currentApplicationContext [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void init(ApplicationContext currentApplicationContext) {
        //启动时，利用ruleLoad加载，并将规则压入缓存中
        loadAndPutInCacheWhenStart();
        
        //根据规则类型，对加载的规则进行校验
        List<RuleAndValidatorWrap> ruleAndValidatorWrapList = new ArrayList<RuleContext.RuleAndValidatorWrap>();
        if (this.ruleKeyMapCache.values() != null) {
            for (Rule ruleTemp : this.ruleKeyMapCache.values()) {
                if (!RuleStateEnum.OPERATION.equals(ruleTemp.getState())) {
                    //非运营态的规则无需进行验证
                    continue;
                }
                RuleRegister<? extends Rule> ruleValidatorTemp = ruleRegisterMap.get(ruleTemp.getRuleType());
                if (ruleValidatorTemp != null) {
                    RuleAndValidatorWrap rvWrapTemp = new RuleAndValidatorWrap(
                            ruleTemp, ruleValidatorTemp);
                    ruleAndValidatorWrapList.add(rvWrapTemp);
                }
            }
        }
        validateWhenLoadFinish(ruleAndValidatorWrapList);
        
        //规则加载完成后，发出规则加载完成事件
        currentApplicationContext.publishEvent(new RuleContextInitializedEvent(
                currentApplicationContext));
    }
    
    /**
      * 如果加载的对应规则为运营态则对该规则进行校验
      * <功能详细描述>
      * @param ruleList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void validateWhenLoadFinish(
            List<RuleAndValidatorWrap> ruleAndValidatorWrapList) {
        for (RuleAndValidatorWrap rvWrapTemp : ruleAndValidatorWrapList) {
            if (rvWrapTemp.getRuleValidator() != null
                    && RuleStateEnum.OPERATION.equals(rvWrapTemp.getRule()
                            .getState())) {
                //如果规则为运营态，并且规则校验器不为空时，对规则进行校验
                rvWrapTemp.getRuleValidator().validate(rvWrapTemp.getRule());
            }
        }
    }
    
    /**
      * 当加载完成压入缓存中
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void loadAndPutInCacheWhenStart() {
        //获取ruleLoader列表
        List<RuleLoader> ruleLoaderList = registeredRuleLoaderList;
        
        //对ruleLoader进行排序
        Collections.sort(ruleLoaderList, new Comparator<RuleLoader>() {
            /**
             * @param o1
             * @param o2
             * @return
             */
            @Override
            public int compare(RuleLoader o1, RuleLoader o2) {
                if (o1 != null && o2 != null) {
                    if (o1.getOrder() == o2.getOrder()) {
                        return 0;
                    } else if (o1.getOrder() > o2.getOrder()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else if (o1 == null && o2 == null) {
                    return 0;
                } else if (o1 == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        
        //根据顺序调用规则验证器
        for (RuleLoader ruleLoaderTemp : ruleLoaderList) {
            List<Rule> ruleList = ruleLoaderTemp.load();
            
            //将对应规则压入容器中
            putInCache(ruleList, true);
        }
        
        loadOver = true;
    }
    
    /**
      * 判断规则是否完成加载
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isLoadFinish() {
        if (loadOver) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
      * 如果规则尚未加载完成，则使当前线程暂停
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void waitLoading() {
        if (isLoadFinish()) {
            return;
        } else {
            try {
                Integer waitTimes = waitThreadMap.get(Thread.currentThread());
                if (waitTimes == null || waitTimes.intValue() < 3) {
                    wait(this.maxLoadTimeout);
                } else {
                    throw new RuleAccessException(null, null, null,
                            "规则容器尚未完成规则加载请等待...");
                }
            } catch (InterruptedException e) {
                logger.error("RuleContext.waitLoading exception:"
                        + e.toString(),
                        e);
            }
        }
    }
    
    /**
      * 获取缓存的规则总数
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int size() {
        waitLoading();
        return ruleKeyMapCache.size();
    }
    
    /**
      * 判断容器中是否含有对应的规则<br/>
      *     1、如果存在规则则返回true,不存在返回false
      * <功能详细描述>
      * @param rule
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean contains(String rule) {
        waitLoading();
        if (ruleKeyMapCache.containsKey(rule)) {
            return true;
        } else if (multiRuleMapCache.containsKey(rule)) {
            if (multiRuleMapCache.get(rule) != null
                    && multiRuleMapCache.get(rule).size() > 1) {
                throw new RuleAccessException(rule, null, null,
                        "未带业务类型（命名空间）的规则，检索到超过多个规则:{}", rule);
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    /**
      * 根据规则名获取规则实例
      * <功能详细描述>
      * @param rule
      * @return [参数说明]
      * 
      * @return Rule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Rule getRule(String rule) {
        waitLoading();
        if (ruleKeyMapCache.containsKey(rule)) {
            return ruleKeyMapCache.get(rule);
        } else if (multiRuleMapCache.containsKey(rule)) {
            if (multiRuleMapCache.get(rule) != null
                    && multiRuleMapCache.get(rule).size() > 1) {
                throw new RuleAccessException(rule, null, null,
                        "未带业务类型（命名空间）的规则，检索到超过多个规则:{}", rule);
            } else {
                return multiRuleMapCache.getFirst(rule);
            }
        } else {
            return null;
        }
    }
    
    /**
      * 获取规则实体
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Rule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleSession newRuleSession(Rule rule) {
        return this.ruleSessionFactory.createRuleSession(rule);
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public RuleContext getObject() throws Exception {
        return this;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return RuleContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
    /**
     * 将指定规则列表，压入规则缓存中<br/>
     *     1、同名规则将会被缓存<br/>
     * @param ruleList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void putInCache(List<Rule> ruleList, boolean isCoverWhenSame) {
        if (ruleList == null) {
            return;
        }
        for (Rule ruleTemp : ruleList) {
            putInCache(ruleTemp, isCoverWhenSame);
        }
    }
    
    /**
     * 将指定规则压入规则缓存中<br/>
     *    1、同名规则将会被缓存<br/>
     * @param ruleList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void putInCache(Rule rule, boolean isCoverWhenSame) {
        if (rule == null) {
            return;
        }
        
        //保证如果serviceType.rule不重复，则将同名的规则，压入multiRuleMapCache中
        //该缓存主要为了兼容处理，不带serviceType的规则访问情况
        if (!this.ruleKeyMapCache.containsKey(getRuleCacheKey(rule))) {
            //如果名字重复了
            //则不放置该规则则规则简名中，如果遇到需要提取该集合的情况
            this.multiRuleMapCache.add(rule.rule(), rule);
        }
        
        //规则放置主容器
        if (isCoverWhenSame) {
            this.ruleKeyMapCache.put(getRuleCacheKey(rule), rule);
        } else if (this.ruleKeyMapCache.containsKey(getRuleCacheKey(rule))) {
            throw new RuleAccessException(rule.rule(), null, null, "重复的规则项:{}",
                    rule.rule());
        } else {
            this.ruleKeyMapCache.put(getRuleCacheKey(rule), rule);
            logger.warn("规则项{}被同名规则项覆盖.", rule.rule());
        }
    }
    
    /**
      * 从缓存中将规则实体移除
      * <功能详细描述>
      * @param rule [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void removeFromCache(Rule rule) {
        if (rule == null) {
            return;
        }
        
        //从缓存中移除
        if (!this.ruleKeyMapCache.containsKey(getRuleCacheKey(rule))) {
            //从ruleKeyMapCache中移除
            this.ruleKeyMapCache.remove(getRuleCacheKey(rule));
            //移除重复的
            List<Rule> ruleList = this.multiRuleMapCache.get(rule.rule());
            if (ruleList != null && ruleList.size() > 1) {
                ruleList.remove(rule);
            } else {
                this.multiRuleMapCache.remove(rule.rule());
            }
        }
    }
    
    /**
     * 获取规则关键字
     * <功能详细描述>
     * @param rule
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public String getRuleKey(Rule rule) {
        AssertUtils.notNull(rule, "rule is emtpy.");
        
        return getRuleCacheKey(rule);
    }
    
    /**
      * 获取规则缓存键
      * <功能详细描述>
      * @param rule [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected String getRuleCacheKey(Rule rule) {
        return rule.getServiceType() + "." + rule.rule();
    }
    
    /**
      * 规则以及对应验证器的包装器
      * <功能详细描述>
      * 
      * @author  brady
      * @version  [版本号, 2013-3-15]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    private static class RuleAndValidatorWrap implements
            Comparable<RuleAndValidatorWrap> {
        
        /** 规则本身 */
        private Rule rule;
        
        /** 规则验证器 */
        private RuleRegister<? extends Rule> ruleValidator;
        
        /** <默认构造函数> */
        public RuleAndValidatorWrap(Rule rule,
                RuleRegister<? extends Rule> ruleValidator) {
            super();
            this.rule = rule;
            this.ruleValidator = ruleValidator;
        }
        
        /**
         * @param o
         * @return
         */
        @Override
        public int compareTo(RuleAndValidatorWrap o) {
            if (o == null) {
                return 1;
            }
            if (this.ruleValidator == null && o.ruleValidator == null) {
                return 0;
            } else if (this.ruleValidator == null) {
                return -1;
            } else if (o.ruleValidator == null) {
                return 1;
            } else {
                return (new Integer(this.ruleValidator.getOrder())).compareTo(new Integer(
                        o.ruleValidator.getOrder()));
            }
        }
        
        /**
         * @return 返回 rule
         */
        public Rule getRule() {
            return rule;
        }
        
        /**
         * @return 返回 ruleValidator
         */
        public RuleRegister<? extends Rule> getRuleValidator() {
            return ruleValidator;
        }
    }
    
    /**
     * @return 返回 ruleSessionTransactionFactory
     */
    public RuleSessionTransactionFactory getRuleSessionTransactionFactory() {
        return ruleSessionTransactionFactory;
    }
    
    /**
     * @param 对ruleSessionTransactionFactory进行赋值
     */
    public void setRuleSessionTransactionFactory(
            RuleSessionTransactionFactory ruleSessionTransactionFactory) {
        this.ruleSessionTransactionFactory = ruleSessionTransactionFactory;
    }
    
    /**
     * @return 返回 ruleSessionFactory
     */
    public RuleSessionFactory getRuleSessionFactory() {
        return ruleSessionFactory;
    }
    
    /**
     * @param 对ruleSessionFactory进行赋值
     */
    public void setRuleSessionFactory(RuleSessionFactory ruleSessionFactory) {
        this.ruleSessionFactory = ruleSessionFactory;
    }
    
    /**
     * @return 返回 ehcache
     */
    public Ehcache getEhcache() {
        return ehcache;
    }
    
    /**
     * @param 对ehcache进行赋值
     */
    public void setEhcache(Ehcache ehcache) {
        this.ehcache = ehcache;
    }
    
    /**
     * @return 返回 loadOver
     */
    public boolean isLoadOver() {
        return loadOver;
    }
    
    /**
     * @param 对loadOver进行赋值
     */
    public void setLoadOver(boolean loadOver) {
        this.loadOver = loadOver;
    }
    
    /**
     * @return 返回 maxLoadTimeout
     */
    public long getMaxLoadTimeout() {
        return maxLoadTimeout;
    }
    
    /**
     * @param 对maxLoadTimeout进行赋值
     */
    public void setMaxLoadTimeout(long maxLoadTimeout) {
        this.maxLoadTimeout = maxLoadTimeout;
    }
    
    /**
     * @return 返回 waitThreadMap
     */
    public Map<Thread, Integer> getWaitThreadMap() {
        return waitThreadMap;
    }
    
    /**
     * @param 对waitThreadMap进行赋值
     */
    public void setWaitThreadMap(Map<Thread, Integer> waitThreadMap) {
        this.waitThreadMap = waitThreadMap;
    }
}
