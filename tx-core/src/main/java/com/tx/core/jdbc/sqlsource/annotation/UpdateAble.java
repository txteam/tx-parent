/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlsource.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解在可更新的字段<br/>
 *     添加了该标志的字段将可以被更新<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-6]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, FIELD })
@Inherited
public @interface UpdateAble {
    
}
