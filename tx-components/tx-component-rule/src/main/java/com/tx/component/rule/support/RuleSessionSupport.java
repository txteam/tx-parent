/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.support;

import java.util.List;
import java.util.Map;

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
      * 解析boolean值规则结果（推导结论或推论事实）<br/>
      * @param rule 规则名
      * @param fact 事实
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean evaluateBoolean(String rule, Map<String, ?> fact);
    
    /**
      * 解析boolean值规则结果（推导结论或推论事实）<br/>
      * @param rule 规则名
      * @param fact 事实
      * @param global 全局参数，在规则执行期间能够通过线程取到，将在规则执行完成后进行销毁
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean evaluateBoolean(String rule, Map<String, ?> fact,
            Map<String, ?> global);
    
    /**
      * 解析列表规则结果（推导结论或推论事实）<br/>
      *<功能详细描述>
      * @param rule 规则名
      * @param fact 事实
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> List<T> evaluateList(String rule, Map<String, ?> fact);
    
    /**
      * 解析列表规则结果（推导结论或推论事实）<br/>
      * @param rule 规则名
      * @param fact 事实
      * @param global 全局参数，在规则执行期间能够通过线程取到，将在规则执行完成后进行销毁
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> List<T> evaluateList(String rule, Map<String, ?> fact,
            Map<String, ?> global);
    
    /**
     * 解析列表规则结果（推导结论或推论事实）<br/>
     *<功能详细描述>
      * @param rule 规则名
      * @param fact 事实
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public <T> Map<String, T> evaluateMap(String rule, Map<String, ?> fact);
    
    /**
      * 解析列表规则结果（推导结论或推论事实）<br/>
      * @param rule 规则名
      * @param fact 事实
      * @param global 全局参数，在规则执行期间能够通过线程取到，将在规则执行完成后进行销毁
      * @return [参数说明]
      * 
      * @return Map<String,T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> Map<String, T> evaluateMap(String rule, Map<String, ?> fact,
            Map<String, ?> global);
    
    /**
     * 解析列表规则结果（推导结论或推论事实）<br/>
     *<功能详细描述>
      * @param rule 规则名
      * @param fact 事实
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public <T> T evaluateObject(String rule, Map<String, ?> fact);
    
    /**
      * 解析列表规则结果（推导结论或推论事实）<br/>
      * @param rule 规则名
      * @param fact 事实
      * @param global 全局参数，在规则执行期间能够通过线程取到，将在规则执行完成后进行销毁
      * @return [参数说明]
      * 
      * @return Map<String,T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> T evaluateObject(String rule, Map<String, ?> fact,
            Map<String, ?> global);
    
    /**
     * 解析列表规则结果（推导结论或推论事实）<br/>
      * <功能详细描述>
      * @param rule 规则名
      * @param fact 事实
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void evalute(String rule, Map<String, Object> fact);
    
    /**
      * 解析列表规则结果（推导结论或推论事实）<br/>
      * @param rule 规则名
      * @param fact 事实
      * @param global 全局参数，在规则执行期间能够通过线程取到，将在规则执行完成后进行销毁
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void evalute(String rule, Map<String, ?> fact, Map<String, ?> global);
    
}
