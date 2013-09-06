/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlsource.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


 /**
  * 查询条件注解<br/>
  *     key对应到对象中的getterName,如果不指定将使用所在字段的getterName
  *     lable服务于界面显示
  *     condition为条件
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-6]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD})
public @interface QueryConditionLessOrEqual {
    
    public String key();
    
    public String lable() default "";
}
