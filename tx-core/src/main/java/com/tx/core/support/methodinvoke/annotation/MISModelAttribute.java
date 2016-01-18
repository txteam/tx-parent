/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月3日
 * <修改描述:>
 */
package com.tx.core.support.methodinvoke.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


 /**
  * modelAttribute处理方法注解<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年1月3日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MISModelAttribute {
    
    /**
      * 含有该注解的方法对modelAttribute的映射<br/>
      *     value为空时，自动根据类名为对应的名字：例子：package.XxxXxx > xxxXxx
      *     如果含有值，则使用对应的值<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String value() default "";
}
