/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月6日
 * <修改描述:>
 */
package com.tx.core.support.mqmsg8583.annotation;


 /**
  * 报文8583数据项<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年9月6日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public @interface Msg8583Item {
    
    /**
      * 报文8583项索引位置<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    int index() default 0;
    
    /**
      * start与index其中之一必填<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    int start() default -1;
    
    int length() default -1;
    
    int end() default -1;
}
