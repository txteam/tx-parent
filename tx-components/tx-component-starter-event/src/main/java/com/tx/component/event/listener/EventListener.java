/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月18日
 * <修改描述:>
 */
package com.tx.component.event.listener;

import java.util.Map;

import org.springframework.core.Ordered;

import com.tx.component.event.event.Event;

/**
 * 事件监听器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface EventListener extends Ordered {
    
    /**
      * 事件监听器<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String eventType();
    
    /**
      * 事件监听器范围<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return EventListenerScopeEnum [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public EventListenerScopeEnum scope();
    
    /**
     * 事件监听执行处<br/> 
     *<功能详细描述>
     * @param event
     * @param params [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public void execute(Event event, Map<String, Object> params);
}
