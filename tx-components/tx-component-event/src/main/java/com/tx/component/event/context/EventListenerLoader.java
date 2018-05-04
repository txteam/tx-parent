/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月19日
 * <修改描述:>
 */
package com.tx.component.event.context;

import java.util.List;

import com.tx.component.event.listener.EventListener;


 /**
  * 事件监听器加载器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年4月19日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface EventListenerLoader {
    
    public List<EventListener> load();
}
