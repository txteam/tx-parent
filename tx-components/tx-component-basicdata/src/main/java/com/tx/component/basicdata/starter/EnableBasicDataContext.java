/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月30日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
  * 启用基础数据容器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年4月30日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(BasicDataContextAutoConfiguration.class)
public @interface EnableBasicDataContext {
    
}
