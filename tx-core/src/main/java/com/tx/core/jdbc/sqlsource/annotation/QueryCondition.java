/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlsource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询条件注解<br/>
 *     key对应到对象中的getterName,如果不指定将使用所在字段的getterName
 *     lable服务于界面显示
 *     如果对应注解存在与type上，则condition应该为不含有?的条件，
 *     会作为otherCondition直接添加到查询中
 *     condition为条件
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-6]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
public @interface QueryCondition {
    
    public String key() default "";
    
    public String lable() default "";
    
    public String condition();
}
