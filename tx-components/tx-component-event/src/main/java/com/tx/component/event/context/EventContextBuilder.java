/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月17日
 * <修改描述:>
 */
package com.tx.component.event.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;

import com.tx.component.event.event.Event;
import com.tx.component.event.listener.EventListener;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 事件容器构建器<br/>
 *     提供构建EventListenerSupport的protected方法
 *     提供bind,trigger的底层实现方法
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventContextBuilder extends EventContextConfigurator {
    
    /** 事件监听器支持映射 */
    protected Map<String, EventListenerSupport> eventListenerSupportMap = new ConcurrentHashMap<String, EventListenerSupport>();
    
    /** 事件监听加载器 */
    private List<EventListenerLoader> eventListenerLoaders = new ArrayList<>();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("事件容器开始初始化......");
        super.afterPropertiesSet();
        
        logger.info("   开始加载EventListenerLoader...");
        eventListenerLoaders.addAll(this.applicationContext.getBeansOfType(EventListenerLoader.class)
                .values());
        
        logger.info("   开始加载EventListener...");
        loadEventListeners();
        
        logger.info("事件容器初始化成功......");
    }
    
    /**
      * 加载事件监听器<br/> 
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void loadEventListeners() {
        if (CollectionUtils.isEmpty(this.eventListenerLoaders)) {
            return;
        }
        
        for (EventListenerLoader eventListenerLoaderTemp : this.eventListenerLoaders) {
            List<EventListener> eventListeners = eventListenerLoaderTemp.load();
            
            if (CollectionUtils.isEmpty(eventListeners)) {
                continue;
            }
            
            //添加事件监听
            for (EventListener listenerTemp : eventListeners) {
                doBind(listenerTemp);
            }
        }
    }
    
    /**
      * 触发一个事件，由容器中注册的监听改事件的对应类方法进行响应<br/> 
      * <功能详细描述>
      * @param event
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doTrigger(Event event, Map<String, Object> params) {
        AssertUtils.notNull(event, "event is null.");
        if(params == null){
            params = new HashMap<String, Object>();
        }
        String eventType = event.type();
        if (!eventListenerSupportMap.containsKey(eventType)) {
            //TODO:根据配置判断，在不存在对应事件支持类的情况时是否应当抛出异常
            return;
        }
        EventListenerSupport support = eventListenerSupportMap.get(event.type());
        
        //执行事件触发逻辑
        support.execute(event, params);
    }
    
    /**
      * 添加事件监听器<br/>
      *<功能详细描述>
      * @param eventListener [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doBind(EventListener eventListener) {
        AssertUtils.notNull(eventListener, "eventListener is null.");
        AssertUtils.notEmpty(eventListener.eventType(),
                "event.eventType is null.");
        AssertUtils.notNull(eventListener.scope(),
                "eventListener.scope is null.");
        
        String eventType = eventListener.eventType();
        EventListenerSupport support = null;
        if (eventListenerSupportMap.containsKey(eventType)) {
            support = eventListenerSupportMap.get(eventType);
        } else {
            support = eventListenerSupportFactory.create(eventType);
            eventListenerSupportMap.put(eventType, support);
        }
        
        logger.info("   通过事件容器添加事件监听器：EventType:'{}' Scope:'{}' Order:'{}'...",
                new Object[] { eventListener.eventType(),
                        eventListener.scope(), eventListener.getOrder() });
        
        //将事件监听器添加进support中
        support.addEventListener(eventListener);
    }
}
