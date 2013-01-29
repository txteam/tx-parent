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

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;
import com.tx.component.rule.support.RuleSessionFactory;



 /**
  * 规则容器<br/>
  *     系统启动时通过该规则容器加载已有的规则<br/>
  *     可以通过该容器实现规则重加载，添加新的规则<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-23]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleContext implements InitializingBean,FactoryBean<RuleContext>{
    
    private static RuleContext ruleContext;
    
    /**
     * 规则加载器
     */
    private List<RuleLoader> ruleLoaders;
    
    /**
     * 规则会话工厂类
     */
    private RuleSessionFactory ruleSessionFactory;
    
    /**
     * 规则缓存
     */
    private Map<String, Rule> ruleMapCache = new HashMap<String, Rule>();

    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //完成属性设置后,加载规则
        load();
        setRuleContext(this);
    }

    /**
      * 初始化规则容器
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void load(){
        Map<String, Rule> newCacheMap = new HashMap<String, Rule>();
        for(RuleLoader ruleLoaderTemp : ruleLoaders){
            List<Rule> ruleList = ruleLoaderTemp.load();
            if(CollectionUtils.isEmpty(ruleList)){
                //如果加载规则为空:
                continue;
            }
            for(Rule ruleTemp : ruleList){
                newCacheMap.put(ruleTemp.rule(), ruleTemp);
            }
        }
        this.ruleMapCache = newCacheMap;
    }
    
    /**
      * <功能简述>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void reLoad(){
        load();
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
    public boolean contains(String rule){
        
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
    public RuleSession newRuleSession(Rule rule){
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
     * @return 返回 ruleContext
     */
    public static RuleContext getRuleContext() {
        return ruleContext;
    }

    /**
     * @param 对ruleContext进行赋值
     */
    public void setRuleContext(RuleContext ruleContext) {
        RuleContext.ruleContext = ruleContext;
    }
}
