/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.support;

/**
 * 规则回话实例接口<br/>
 *     1、规划了规则会话实例应该具有的功能<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleSessionInstance<FACT_TYPE, RESULT_TYPE> extends
        RuleSession {
    
    /**
     * 获取规则回话的初始化句柄
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RuleSessionInitHandle<IH> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public RuleSessionInitHandle<FACT_TYPE> getInitHandle();
    
    /**
      * 获取规则回话结果句柄
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return RuleSessionResultHandle<RH> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleSessionResultHandle<RESULT_TYPE> getResultHandle();
    
    /** 
      * 规则回话执行器句柄
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return RuleSessionExecutorHandle [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleSessionExecutorHandle getExecutor();
}
