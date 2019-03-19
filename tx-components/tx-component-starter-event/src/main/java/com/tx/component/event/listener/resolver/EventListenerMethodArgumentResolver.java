/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.component.event.listener.resolver;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;

import com.tx.component.event.event.Event;

/**
 * 事件监听器处理方法的参数解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface EventListenerMethodArgumentResolver extends Ordered{
    
    /**
      * 方法参数解析器<br/> 
      *<功能详细描述>
      * @param parameter
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean supportsParameter(MethodParameter parameter);
    
    /**
      * 解析参数<br/> 
      *<功能详细描述>
      * @param parameter
      * @param event
      * @param params
      * @return
      * @throws Exception [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Object resolveArgument(MethodParameter parameter, Event event,
            Map<String, Object> params) throws Exception;
}
