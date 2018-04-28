/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年12月25日
 * <修改描述:>
 */
package com.tx.component.command.context;

/**
 * 值处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年12月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ValueHandler<T> {
    
    /**
     * 获取值<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public abstract T getValue();
    
    /**
      * 设置值
      * <功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setValue(String key, T value) {
    }
}
