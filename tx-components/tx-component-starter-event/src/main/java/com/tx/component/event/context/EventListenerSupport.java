/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月17日
 * <修改描述:>
 */
package com.tx.component.event.context;

import java.util.Map;

import com.tx.component.event.event.Event;
import com.tx.component.event.listener.EventListener;

/**
 * 事件监听<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface EventListenerSupport {
    
    /**
      * 监听的事件名<br/> 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String eventType();
    
    /**
      * 事件监听器执行支持<br/> 
      *<功能详细描述>
      * @param event
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void execute(Event event, Map<String, Object> params);
    
    /**
      * 添加事件监听 
      *<功能详细描述>
      * @param eventListener [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void addEventListener(EventListener eventListener);
}
