/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.support;

import com.tx.component.rule.model.Rule;

/**
 * 规则会话工具类<br/>
 *     1、根据规则生成对应规则会话对象<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleSessionFactory {
    
    /**
     * 创建方法类型的规则会话
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RuleSession [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public RuleSession createRuleSession(Rule rule);
}
