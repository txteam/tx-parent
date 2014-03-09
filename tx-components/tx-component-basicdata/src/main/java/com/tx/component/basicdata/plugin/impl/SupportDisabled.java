/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.plugin.impl;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;


 /**
  * 支持禁用<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-10]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface SupportDisabled {
    
    /**
      * 是否有效
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //考虑到方法覆盖，覆盖后现框架中暂不支持直接读取接口中的注解
    @UpdateAble
    @QueryConditionEqual
    public boolean isValid();
    
    /**
      * 设置对象是否有效
      * true有效
      * false无效
      *<功能详细描述>
      * @param isValid [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setValid(boolean isValid);
}
