/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月17日
 * <修改描述:>
 */
package com.tx.component.event.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tx.component.event.event.Event;
import com.tx.component.event.listener.EventListenerScopeEnum;

/**
 * 事件监听器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListener {
    
    /**
      * 事件类型名<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String value() default "";
    
    /**
      * 事件监听器排序值<br/> 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    int order() default 0;
    
    /**
      * 事件类型<br/>
      *     value与eventType不能同时为空
      *     如果value不为空，优先根据value进行解析
      *     当value为空时
      *         分为eventType为Event子类
      *         eventType不为Event子类的情况进行处理
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Class<? extends Event> eventType() default Event.class;
    
    /**
      * 事件监听器生命周期范围 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return EventListenerScopeEnum [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    EventListenerScopeEnum scope() default EventListenerScopeEnum.AROUND;
}
