package com.tx.component.command.context;

/**
 * 操作返回接口对象
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  bobby
  * @version  [版本号, 2015年11月30日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface CommandResponse {
    
    /**
      * 取回值
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object getAttribute(String key);
    
    /**
      * 设置值
      *<功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setAttribute(String key, Object value);
}
