/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月22日
 * <修改描述:>
 */
package com.tx.component.event.context.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.event.context.EventListenerSupport;
import com.tx.component.event.context.EventListenerSupportFactory;
import com.tx.component.event.context.support.DefaultEventListenerSupport;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 默认的事件监听器支持工厂类
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultEventListenerSupportFactory implements
        EventListenerSupportFactory {
    
    private PlatformTransactionManager transactionManager;
    
    /** 事件监听支持器映射 */
    private Map<String, EventListenerSupport> eventListenerSupportMap = new ConcurrentHashMap<>();
    
    /** <默认构造函数> */
    public DefaultEventListenerSupportFactory(
            PlatformTransactionManager transactionManager) {
        super();
        AssertUtils.notNull(transactionManager,
                "transactionManager is null.");
        this.transactionManager = transactionManager;
    }
    
    /**
     * @param eventType
     * @return
     */
    @Override
    public EventListenerSupport create(String eventType) {
        AssertUtils.notEmpty(eventType, "eventType is empty.");
        
        EventListenerSupport support = null;
        if (eventListenerSupportMap.containsKey(eventType)) {
            support = eventListenerSupportMap.get(eventType);
        } else {
            support = new DefaultEventListenerSupport(eventType,
                    transactionManager);
            eventListenerSupportMap.put(eventType, support);
        }
        return support;
    }
    
}
