/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.support;

import java.util.Map;

import com.tx.component.rule.session.CallbackHandler;
import com.tx.component.rule.session.RuleSession;

/**
 * 规则回话<br/>
 * 规则回话接口，用以描述各种规则会话<br/>
 * 类似mybatis中的sqlSession的类似作用<br/>
 *     将会封装类似的ruleTemplate用以支持多线程规则，以及多种规则的实现<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleSessionSupport {
    
    /**
      * 解析列表规则结果（推导结论或推论事实）触发某规则执行<br/>
      * <功能详细描述>
      * @param ruleSession
      *<功能详细描述>
      * @param ruleSession
      * @param fact
      * @param global
      * @param resultHandle
      * @return [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <R> void evaluate(RuleSession ruleSession, Map<String, Object> fact,
            Map<String, Object> global, CallbackHandler<R> resultHandle);
    
}
