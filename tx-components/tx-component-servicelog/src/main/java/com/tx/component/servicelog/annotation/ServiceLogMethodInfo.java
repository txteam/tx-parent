/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-12
 * <修改描述:>
 */
package com.tx.component.servicelog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


 /**
  * 业务日志 方法信息
  * 1、用以添加在方法上，
  * 2、用以辅助记录的业务日志，记录对应操作的名称
  * 3、如果不添加该注解不会影响到具体的业务日志记录
  * 4、如果不添加、name将默认 方法名
  * 
  * @author  brady
  * @version  [版本号, 2012-12-12]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceLogMethodInfo {
    
    /**
      * 业务日志记录时，记录的操作名
      * 1、当某方法上含有该注解，在该方法内部进行业务日志记录时
      * 
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String name();
    
    /**
      * 业务日志方法描述
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String description() default "";
}
