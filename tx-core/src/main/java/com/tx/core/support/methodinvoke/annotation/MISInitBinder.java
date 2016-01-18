/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月31日
 * <修改描述:>
 */
package com.tx.core.support.methodinvoke.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


 /**
  * 类SpringMVC的注解式方法调用支撑注解：
  *     binder的扩展入口
  *     Method Invoke Support InitBinder
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2015年12月31日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MISInitBinder {
    
    /**
      * 初始化binder的值<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String[] value() default {};
}
