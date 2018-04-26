/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-11
 * <修改描述:>
 */
package com.tx.component.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
public @interface CheckOperateAuth {
    
    /**
     * 权限
     */
    String key();
    
    /**
      * 父级权限key
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String parentKey() default "";
    
    /**
      * 权限名
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String name() default "";
    
    /**
      * 权限描述
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String description() default "";
    
    /**
      * 是否可配置，对应权限是否可赋给超级管理员以外的人员 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean configAble() default true;
}
