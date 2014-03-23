/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.session;

import java.util.List;
import java.util.Map;

import com.tx.component.rule.context.Rule;

/**
 * 规则会话定义<br/>
 *     该接口的实现为具体的一个规则会话<br/>
 *     1、规则回话，具有一个名字<br/>
 *     2、规则回话，具有具体的执行方法<br/>
 *     3、规则回话，具有一个回话结果（推论，符合规则的事实）的输出句柄<br/>
 *     4、规则回话，具有一个会话，输入事实的输入句柄<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleSession {
    
    /**
      * 规则名
      *     [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Rule getRule();
    
    /**
      * 会话执行,传入一个事实的情况<br/>
      *     1、该方法被触发后，将会<br/>
      *         首先初始化回话，插入事实，执行规则，销毁回话<br/>
      *     
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> void execute(Map<String, Object> fact,
            CallbackHandler<T> resultHandle) throws Exception;
    
    /**
     * 会话执行,传入多个事实的情况<br/>
     *     1、该方法被触发后，将会<br/>
     *         首先初始化回话，插入事实，执行规则，销毁回话<br/>
     *     
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public <T> void executeAll(List<Map<String, Object>> facts,
            CallbackHandler<T> resultHandle) throws Exception;
}
