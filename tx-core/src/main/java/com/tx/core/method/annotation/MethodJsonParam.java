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
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String value() default "";
    
    /**
     * 是否必填<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean required() default false;
}
