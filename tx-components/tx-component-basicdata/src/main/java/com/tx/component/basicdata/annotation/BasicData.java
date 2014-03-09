/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tx.component.basicdata.executor.BasicDataExecutor;
import com.tx.component.basicdata.executor.SqlSourceBasicDataExecutor;

/**
 * 基础数据模型<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface BasicData {
    
    /**
      * 对象是否需要缓存<br/>
      *     如果对应对象为枚举这里指定合值，最终仍然会是不需要缓存<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isCache() default true;
    
    /**
      * 基础数据的默认执行器类型<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<BasicDataExecutor> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public Class<? extends BasicDataExecutor> executor() default SqlSourceBasicDataExecutor.class;
}
