/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-12
 * <修改描述:>
 */
package com.tx.component.servicelog.annotation;


 /**
  * 业务日志 类信息
  * 
  * @author  brady
  * @version  [版本号, 2012-12-12]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public @interface ServiceLogClassInfo {
    
    /**
      * 模块名
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String module();
    
    /**
      * 业务功能所属组（类名）
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String name();
    
    /**
      * 业务类描述
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String description();
}
