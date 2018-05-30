/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月4日
 * <修改描述:>
 */
package com.tx.core.method.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 参数设置类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年1月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodJsonParam {
    
    /**
     * 参数默认名称<br/>
     * Alias for {@link #name}.
     */
    @AliasFor("name")
    String value() default "";
    
    /**
     * 参数默认名称<br/>
     * The name of the request parameter to bind to.
     * @since 4.2
     */
    @AliasFor("value")
    String name() default "";
}
