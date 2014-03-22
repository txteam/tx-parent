/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.util.HashMap;
import java.util.Map;

import com.tx.component.rule.exceptions.RuleContextInitException;
import com.tx.component.rule.loader.RuleRegister;
import com.tx.component.rule.loader.RuleStateEnum;
import com.tx.component.rule.session.RuleSession;
import com.tx.component.rule.transation.RuleSessionTransactionFactory;
import com.tx.core.exceptions.util.AssertUtils;

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
public class RuleContext extends RuleContextBuilder {
    
    /** 单例的rule容器 */
    private static Map<String, RuleContext> ruleContextMapping = new HashMap<String, RuleContext>();
    
    /** 获取规则容器实例 */
    public static RuleContext getContext(String name) {
        AssertUtils.notEmpty(name, "name is empty.");
        AssertUtils.isTrue(ruleContextMapping.containsKey(name),
                "ruleContext:[{}] is not exist or not inited.",
                new Object[] { name });
        
        RuleContext context = ruleContextMapping.get(name);
        return context;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            super.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuleContextInitException("规则容器初始化错误.", e);
        }
        ruleContextMapping.put(beanName, this);
    }
    
    /**
      * 获取规则会话事务工厂
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return RuleSessionTransactionFactory [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleSessionTransactionFactory getRuleSessionTransactionFactory() {
        RuleSessionTransactionFactory res = this.ruleSessionTransactionFactory;
        return res;
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
    public boolean contains(String ruleKey) {
        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
        
        if (this.ruleMap.containsKey(ruleKey)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
      * 根据规则key获取规则实例
      *     当对应规则不存在时，则返回null.
      *<功能详细描述>
      * @param ruleKey
      * @return [参数说明]
      * 
      * @return Rule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Rule getRuleByRuleKey(String ruleKey) {
        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
        Rule resRule = this.ruleMap.get(ruleKey);
        return resRule;
    }
    
    /**
      * 根据规则实例构建规则会话实例<br/> 
      *<功能详细描述>
      * @param rule
      * @return [参数说明]
      * 
      * @return RuleSession [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleSession buildRuleSession(Rule rule) {
        AssertUtils.notNull(rule, "rule is null.");
        AssertUtils.notEmpty(rule.getKey(), "rule.key is empty.");
        AssertUtils.notNull(rule.getRuleType(),
                "rule.ruleType is null.ruleKey:{}",
                rule.getKey());
        AssertUtils.isTrue(RuleStateEnum.OPERATION.equals(rule.getState()),
                "rule.state is not operation.ruleKey:{}",
                rule.getKey());
        AssertUtils.isTrue(this.ruleRegisterMap.containsKey(rule.getRuleType()),
                "un support ruleType.ruleKey:{} ruleType:{}",
                new Object[] { rule.getKey(), rule.getRuleType() });
        
        @SuppressWarnings("rawtypes")
        RuleRegister ruleRegister = this.ruleRegisterMap.get(rule.getRuleType());
        @SuppressWarnings("unchecked")
        RuleSession ruleSession = ruleRegister.buildRuleSession(rule);
        return ruleSession;
        
    }
    
    //    /**
    //      * 将规则注册入规则容器中
    //      * <功能详细描述>
    //      * @param spRule [参数说明]
    //      * 
    //      * @return void [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    @Transactional
    //    public Rule registeRule(RuleItem ruleItem) {
    //        if (spRule == null || StringUtils.isEmpty(spRule.getRule())
    //                || StringUtils.isEmpty(spRule.getServiceType())
    //                || spRule.getRuleType() == null) {
    //            throw new RuleRegisteException(
    //                    "spRule or rule or serviceType or ruleType is null");
    //        }
    //        
    //        RuleRegister<? extends Rule> ruleRegisterTemp = ruleRegisterMap.get(spRule.getRuleType());
    //        if (ruleRegisterTemp == null) {
    //            throw new RuleRegisteException(
    //                    "ruleType:{} RuleRegister not exist.",
    //                    new Object[] { spRule.getRuleType() });
    //        }
    //        
    //        //调用对应注册器方法，将规则注册入容器中
    //        final Rule realRule = ruleRegisterTemp.registe(spRule);
    //        if (realRule != null) {
    //            if (TransactionSynchronizationManager.isSynchronizationActive()) {
    //                //如果在事务逻辑中执行
    //                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
    //                    @Override
    //                    public void afterCommit() {
    //                        putInCache(realRule, true);
    //                    }
    //                });
    //            } else {
    //                //如果在非事务中执行
    //                putInCache(realRule, true);
    //            }
    //        } else {
    //            throw new RuleRegisteException(
    //                    "ruleType:{} RuleRegister call registe return null realRule.",
    //                    new Object[] { spRule.getRuleType() });
    //        }
    //        return realRule;
    //    }
    //    
    //    /**
    //      * 反注册规则
    //      * <功能详细描述>
    //      * @param rule [参数说明] serviceType.rule
    //      * 
    //      * @return void [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    public void unRegisteRule(String rule) {
    //        Rule ruleImpl = getRule(rule);
    //        if (ruleImpl == null) {
    //            return;
    //        }
    //        
    //        //删除持久化数据
    //        this.simplePersistenceRuleService.deleteById(ruleImpl.getId());
    //        
    //        //从缓存中移除对应规则
    //        removeFromCache(ruleImpl);
    //    }
    
}
