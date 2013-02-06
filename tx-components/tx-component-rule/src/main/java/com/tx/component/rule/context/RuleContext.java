/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;
import com.tx.component.rule.support.RuleSessionFactory;

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
        ApplicationListener<LoadRuleEvent> {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(RuleContext.class);
    
    /** 单例的rule容器 */
    private static RuleContext ruleContext;
    
    /**
     * 规则会话工厂类
     */
    private RuleSessionFactory ruleSessionFactory;
    
    /**
     * 规则缓存
     */
    private Map<String, Rule> ruleMapCache = new HashMap<String, Rule>();
    
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
    public void onApplicationEvent(LoadRuleEvent event) {
        List<Rule> ruleList = event.getRuleList();
        putInCache(ruleList, true);
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //完成属性设置后,加载规则
        setRuleContext(this);
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
        return ruleMapCache.containsKey(rule);
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
            if (isCoverWhenSame) {
                this.ruleMapCache.put(ruleTemp.rule(), ruleTemp);
            }
            else if (this.ruleMapCache.containsKey(ruleTemp.rule())) {
                throw new RuleAccessException(ruleTemp.rule(), null, null, "重复的规则项:{}",
                        ruleTemp.rule());
            }
            else {
                this.ruleMapCache.put(ruleTemp.rule(), ruleTemp);
            }
            
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
    @SuppressWarnings("unused")
    private void putInCache(Rule rule, boolean isCoverWhenSame) {
        if (rule == null) {
            return;
        }
        if (isCoverWhenSame) {
            this.ruleMapCache.put(rule.rule(), rule);
        }
        else if (this.ruleMapCache.containsKey(rule.rule())) {
            throw new RuleAccessException(rule.rule(), null, null, "重复的规则项:{}",
                    rule.rule());
        }
        else {
            this.ruleMapCache.put(rule.rule(), rule);
            logger.warn("规则项{}被同名规则项覆盖.", rule.rule());
        }
        
    }
}
