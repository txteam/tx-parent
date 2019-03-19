/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月21日
 * <修改描述:>
 */
package com.tx.component.event.listener;

import java.util.Map;

import com.tx.component.event.event.Event;


 /**
  * 事件监听器的处理器接口<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年4月21日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface EventListenerHandler {
    
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
   public void handle(Event event, Map<String, Object> params);
}
