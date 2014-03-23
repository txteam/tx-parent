/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.loader.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


 /**
  * 规则结果句柄<br/>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RuleResultBody {
    
}
