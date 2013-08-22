/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-24
 * <修改描述:>
 */
package com.tx.core.util;



 /**
  * 用以支持ognl判断的表达式工具类<br/>
  * 主要是根据对象的实际类型，使用合适的方法进行判定
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class OgnlUtils {
    
    /**
      * 判断入参数是否为空<br/>
      * " "不会被判定为false<br/>
      * <功能详细描述>
      * @param obj
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(Object obj){
        //为空时认为是empty的
        return ObjectUtils.isEmpty(obj);
    }
    
    /**
      * 判断对象是否不为空
      * <功能详细描述>
      * @param obj
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isNotEmpty(Object obj){
        return !ObjectUtils.isEmpty(obj);
    }
    
}
