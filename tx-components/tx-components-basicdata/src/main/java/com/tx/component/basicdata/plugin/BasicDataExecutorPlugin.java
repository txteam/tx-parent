/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.plugin;

import java.lang.reflect.InvocationHandler;

import org.springframework.core.Ordered;

import com.tx.component.basicdata.executor.BasicDataExecutor;

/**
 * 基础数据执行器插件<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface BasicDataExecutorPlugin extends InvocationHandler, Ordered {
    
    String METHOD_NAME_EXECUTE = "execute";
    
    String METHOD_NAME_GETMULTIVALUEMAP = "getMultiValueMap";
    
    String METHOD_NAME_FIND = "find";
    
    String METHOD_NAME_LIST = "list";
    
    String METHOD_NAME_QUERY = "query";
    
    String METHOD_NAME_QUERYPAGEDLIST = "queryPagedList";
    
    String METHOD_NAME_COUNT = "count";
    
    String METHOD_NAME_INSERT = "insert";
    
    String METHOD_NAME_DELETE = "delete";
    
    String METHOD_NAME_UPDATE = "update";
    
    /**
      * 是否支持当前类型
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> boolean isSupportType(Class<T> type);
    
    /**
      * 传入基础数据执行器获取对应插件实例<br/>
      *<功能详细描述>
      * @param basicDataExecutor
      * @return [参数说明]
      * 
      * @return BasicDataExecutorPlugin [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public BasicDataExecutorPlugin plugin(
            BasicDataExecutor<?> basicDataExecutor, Class<?> type);
    
    /**
      * 设置当前插件对应的执行器对象 
      *<功能详细描述>
      * @param basicDataExecutor [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setBasicDataExecutor(BasicDataExecutor<?> basicDataExecutor);
    
    /**
      * 设置当前类型<br/> 
      *<功能详细描述>
      * @param type [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setType(Class<?> type);
}
