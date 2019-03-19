/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月19日
 * <修改描述:>
 */
package com.tx.component.event.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


 /**
  * 在类上添加该注解<br/>
  *     具有该注解的类中的方法将被注解解析<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年4月19日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListeners {
    
}
