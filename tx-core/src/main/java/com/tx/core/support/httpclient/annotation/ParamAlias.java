/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-7
 * <修改描述:>
 */
package com.tx.core.support.httpclient.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数别名<br/>
 *     参数别名
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface ParamAlias {
    
    /**
      * 参数别名默认值<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String value();
    
    /**
      * 实现类<br/>
      *     适用于注解处，类型为接口的形式<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Class<?> impl() default Void.class;
}
