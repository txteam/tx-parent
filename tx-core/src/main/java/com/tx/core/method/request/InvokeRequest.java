/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月15日
 * <修改描述:>
 */
package com.tx.core.method.request;

import java.util.Iterator;
import java.util.Map;

/**
 * Invoke请求对象<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年2月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface InvokeRequest {
    
    /**
     * 获取指定参数名的参数对象
     * <功能详细描述>
     * @param paramName
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    <T> T getParameter(String paramName, Class<T> type);
    
    /**
     * 获取所有的参数值
     * <功能详细描述>
     * @param paramName
     * @return [参数说明]
     * 
     * @return String[] [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    <T> T[] getParameterValues(String paramName, Class<T> type);
    
    /**
     * 获取指定参数名的参数对象
     * <功能详细描述>
     * @param paramName
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    Object getParameter(String paramName);
    
    /**
      * 获取所有的参数值
      * <功能详细描述>
      * @param paramName
      * @return [参数说明]
      * 
      * @return String[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Object[] getParameterValues(String paramName);
    
    /**
      * 获取参数名集合的迭代器<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Iterator<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Iterator<String> getParameterNames();
    
    /**
      * 获取参数映射<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,Object[]> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Map<String, Object[]> getParameterMap();
}
