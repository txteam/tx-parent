/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月18日
 * <修改描述:>
 */
package com.tx.component.event.context;

/**
 * 事件监听支持类生成工厂<br/>
 *     用以支持生成事件监听生成工厂<br/>
 *     如果事件容器，准备开始监听某事件类型，当发现对应事件类型的支持类不存在，应当在此进行创建<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface EventListenerSupportFactory {
    
    /**
      * 
      *<功能详细描述>
      * @param eventType
      * @return [参数说明]
      * 
      * @return EventListenerSupport [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public EventListenerSupport create(String eventType);
}
