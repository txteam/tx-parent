/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月17日
 * <修改描述:>
 */
package com.tx.component.event.event;

import java.util.Map;

 /**
  * 事件接口，实现该接口的类可以通过事件容器将事件广播给监听改事件的监听器<br/>
  *     事件执行实际就是当前事务中的一个调度过程<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年4月17日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface Event {
    
    /**
      * 事件类型<br/>
      *     实际就是事件名,在触发和监听的设计中借鉴了jquery的设计，所以这里也就和jquery一样叫做type<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
      */
    public String type();
    
    /**
     * 向事件实现中注册事件回调方法<br/> 
     *<功能详细描述>
     * @param callback [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public void register(EventCallbackHandler callback);
   
   /**
     * 事件执行后的回调<br/> 
     *<功能详细描述>
     * @param callback [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public void callback(Map<String, Object> params);
}
