/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-20
 * <修改描述:>
 */
package com.tx.component.rule.loader;

import java.util.List;

/**
 * 规则项目持久器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleItemPersister {
    
    /**
     * 加载指定key的规则项<br/>
     *     如果不存在则返回null
     *<功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return RuleItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    RuleItem find(String key);
    
    /**
     * 加载规则项<br/>
     *     提供该方法用以加载所有的规则<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<Rule> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public List<RuleItem> list();
    
    /**
      * 根据规则关键字key删除规则项<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean deleteRuleByRuleKey(String ruleKey);
}
