/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.util.HashMap;
import java.util.Map;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;


 /**
  * 方法规则回话工厂
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class MethodRuleSessionFactory {
    
    /** 方法类规则工厂 */
    private static MethodRuleSessionFactory factory = new MethodRuleSessionFactory();
    
    /** 规则会话缓存 */
    private static Map<MethodRule, RuleSession> ruleSessionMap = new HashMap<MethodRule, RuleSession>();
    
    /**
     * 私有化构造函数，值返回具体的一个工厂
     * 该工厂实例将被当做关闭规则会话的key在方法类的规则中，该key可以是同一个
     * 所以需要改功能为一个单例
     */
    private MethodRuleSessionFactory(){
        //私有化构造函数，值返回具体的一个工厂
        //该工厂实例将被当做关闭规则会话的key在方法类的规则中，该key可以是同一个
        //所以需要改功能为一个单例
    }
    
    /**
      * 返回规则会话工厂实例
      * @param rule
      * @return [参数说明]
      * 
      * @return MethodRuleSessionFactory [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static MethodRuleSessionFactory newInstance(Rule rule){
        return factory;
    }
    
    /**
      * 创建规则会话实例
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return RuleSession [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleSession getRuleSession(MethodRule rule){
        if(ruleSessionMap.containsKey(rule)){
            return ruleSessionMap.get(rule);
        }
        
        return null;
    }
}
