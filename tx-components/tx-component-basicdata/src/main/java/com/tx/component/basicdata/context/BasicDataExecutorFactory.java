package com.tx.component.basicdata.context;

import com.tx.component.basicdata.executor.BasicDataExecutor;

/**
  * 基础数据执行器工厂<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-10]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface BasicDataExecutorFactory {
    
    /**
      * 根据类型获取执行器
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return BasicDataExecutor<TYPE> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract <TYPE> BasicDataExecutor<TYPE> getExecutor(Class<TYPE> type);
    
}