/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.store.chm.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.support.RuleSession;
import com.tx.component.rule.support.RuleSessionFactory;
import com.tx.component.rule.support.impl.DefaultRuleSessionFactory;
import com.tx.component.rule.transation.RuleSessionTransactionFactory;
import com.tx.component.rule.transation.impl.DefaultRuleSessionTransactionFactory;
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
@Component("ruleContext")
public class RuleContext implements InitializingBean, FactoryBean<RuleContext>,
        ApplicationListener<RuleLoaderInitializeComplete> {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(RuleContext.class);
    
    /** 单例的rule容器 */
    private static RuleContext ruleContext;
    
    /** 规则会话事务工厂  */
    private RuleSessionTransactionFactory ruleSessionTransactionFactory;
    
    /** 规则会话工厂类 */
    private RuleSessionFactory ruleSessionFactory;
    
    /** 缓存 */
    @Resource(name = "cache")
    private Ehcache ehcache;
    
    /** 注册的规则加载器 */
    private static Map<String, Boolean> registeredRuleLoaderNameMap = new HashMap<String, Boolean>();
    
    /** 注册的规则加载器 */
    private static Map<String, RuleLoader> registeredRuleLoaderMap = new HashMap<String, RuleLoader>();
    
    /** 规则验证器映射 */
    private static Map<Class<? extends Rule>, RuleValidator<? extends Rule>> ruleValidatorMap = new HashMap<Class<? extends Rule>, RuleValidator<? extends Rule>>();
    
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
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.ruleKeyMapCache = new SimpleEhcacheMap<String, Rule>(
                "cache.ruleKeyMapCache", ehcache,
                new ConcurrentHashMap<String, Rule>());
        this.multiRuleMapCache = new SimpleMultiValueEhcacheMap<String, Rule>(
                "cache.multiRuleMapCache", ehcache,
                new ConcurrentHashMap<String, List<Rule>>());
        
        if (ruleSessionFactory == null) {
            this.ruleSessionFactory = new DefaultRuleSessionFactory();
        }
        if (ruleSessionTransactionFactory == null) {
            this.ruleSessionTransactionFactory = new DefaultRuleSessionTransactionFactory();
        }
        //完成属性设置后,加载规则
        setRuleContext(this);
    }
    
    /**
     * 私有化构造函数<默认构造函数>
     */
    private RuleContext() {
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
    public static void registerRuleLoader(String ruleLoaderBeanName) {
        registeredRuleLoaderNameMap.put(ruleLoaderBeanName,
                false);
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
    public static void registerRuleValidator(
            RuleValidator<? extends Rule> ruleValidator) {
        ruleValidatorMap.put(ruleValidator.validateType(), ruleValidator);
    }
    
    /**
      * 返回ruleContext容器
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return RuleContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static RuleContext getRuleContext() {
        return ruleContext;
    }
    
    /**
     * @param event
     */
    @Override
    public void onApplicationEvent(RuleLoaderInitializeComplete event) {
        RuleLoader ruleLoader = event.getRuleLoader();
        String ruleLoaderName = event.getRuleLoaderName();
        
        //指定规则已经加载
        registeredRuleLoaderNameMap.put(ruleLoaderName, true);
        registeredRuleLoaderMap.put(ruleLoaderName, ruleLoader);
        
        //设置为对应ruleLoader已加载
        if (isLoadFinish()) {
            putInCacheWhenLoadFinish();
            
            List<RuleAndValidatorWrap> ruleAndValidatorWrapList = new ArrayList<RuleContext.RuleAndValidatorWrap>();
            if (this.ruleKeyMapCache.values() != null) {
                for (Rule ruleTemp : this.ruleKeyMapCache.values()) {
                    if (!RuleStateEnum.OPERATION.equals(ruleTemp.getState())) {
                        //非运营态的规则无需进行验证
                        continue;
                    }
                    RuleValidator<? extends Rule> ruleValidatorTemp = ruleValidatorMap.get(ruleTemp.getClass());
                    RuleAndValidatorWrap rvWrapTemp = new RuleAndValidatorWrap(
                            ruleTemp, ruleValidatorTemp);
                    ruleAndValidatorWrapList.add(rvWrapTemp);
                }
            }
            validateWhenLoadFinish(ruleAndValidatorWrapList);
        }
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
    private void putInCacheWhenLoadFinish() {
        //获取ruleLoader列表
        List<RuleLoader> ruleLoaderList = new ArrayList<RuleLoader>(
                registeredRuleLoaderMap.values());
        
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
            for (Entry<String, Boolean> entryTemp : registeredRuleLoaderNameMap.entrySet()) {
                if (!entryTemp.getValue()) {
                    return false;
                }
            }
            loadOver = true;
            return true;
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
        return ruleContext;
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
     * @param 对ruleContext进行赋值
     */
    public void setRuleContext(RuleContext ruleContext) {
        RuleContext.ruleContext = ruleContext;
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
        if (isCoverWhenSame) {
            this.ruleKeyMapCache.put(getRuleCacheKey(rule), rule);
        } else if (this.ruleKeyMapCache.containsKey(getRuleCacheKey(rule))) {
            throw new RuleAccessException(rule.rule(), null, null, "重复的规则项:{}",
                    rule.rule());
        } else {
            this.ruleKeyMapCache.put(getRuleCacheKey(rule), rule);
            logger.warn("规则项{}被同名规则项覆盖.", rule.rule());
        }
        
        //如果名字重复了
        //则不放置该规则则规则简名中，如果遇到需要提取该集合的情况
        this.multiRuleMapCache.add(rule.rule(), rule);
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
        private RuleValidator<? extends Rule> ruleValidator;
        
        /** <默认构造函数> */
        public RuleAndValidatorWrap(Rule rule,
                RuleValidator<? extends Rule> ruleValidator) {
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
        public RuleValidator<? extends Rule> getRuleValidator() {
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
}
