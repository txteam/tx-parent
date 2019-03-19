/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.component.event.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 事件会话容器<br/>
 *     利用线程变量实现
 *     open 初始化线程变量中的事件容器<br/>
 *     close
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventSessionContext {
    
    /** 事件会话容器 */
    private static ThreadLocal<EventSessionContext> context = new ThreadLocal<EventSessionContext>() {
        
        /**
         * @return
         */
        @Override
        protected EventSessionContext initialValue() {
            EventSessionContext eventSessionContext = new EventSessionContext();
            return eventSessionContext;
        }
    };
    
    /**
      * 开启一个事件会话，在该方法中，初始化事件会话实例
      *<功能详细描述>
      * @param eventListenerSupport [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void open() {
        EventSessionContext eventSessionContext = EventSessionContext.context.get();
        if (!eventSessionContext.isOpen()) {
            eventSessionContext.init();
        }
        eventSessionContext.requested();
    }
    
    /**
      * 关闭一个事件会话，在该方法中，清空事件会话实例<br/> 
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void close() {
        EventSessionContext eventSessionContext = EventSessionContext.context.get();
        eventSessionContext.released();
        if (!eventSessionContext.isOpen()) {
            eventSessionContext.clear();
            EventSessionContext.context.remove();
        }
    }
    
    /**
      * 获取当前线程中事件会话容器实例<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return EventSessionContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static EventSessionContext getSession() {
        EventSessionContext session = EventSessionContext.context.get();
        return session;
    }
    
    /** 引用数 */
    private int referenceCount = 0;
    
    /** 事件会话容器中的参数 */
    private Map<String, Object> attributes;
    
    /** <默认构造函数> */
    private EventSessionContext() {
        super();
    }
    
    /**
      * 初始化会话实例<br/>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void init() {
        this.attributes = new HashMap<String, Object>();
    }
    
    /**
      * 清空会话实例<br/>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void clear() {
        this.attributes.clear();
        this.attributes = null;
    }
    
    /**
     * @return 返回 params
     */
    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }
    
    /**
      * 将值压入线程变量中<br/>
      *<功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }
    
    /**
      * 移除线程变量中值 
      *<功能详细描述>
      * @param key [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void removeAttribute(String key) {
        this.attributes.remove(key);
    }
    
    /**
      * 获取指定的线程变量值<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }
    
    /**
     * 计数器++
     */
    private void requested() {
        this.referenceCount++;
    }
    
    /**
      * 检查计数器是否已经到0 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private boolean isOpen() {
        boolean open = this.referenceCount > 0;
        return open;
    }
    
    /**
      * 计数器--
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void released() {
        this.referenceCount--;
    }
}
