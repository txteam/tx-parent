/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月19日
 * <修改描述:>
 */
package com.tx.component.event.context.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.OrderComparator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tx.component.event.context.EventListenerSupport;
import com.tx.component.event.context.EventSessionContext;
import com.tx.component.event.event.Event;
import com.tx.component.event.listener.EventListener;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 默认的事件监听支持<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultEventListenerSupport implements EventListenerSupport {
    
    /** 事务句柄 */
    private PlatformTransactionManager transactionManager;
    
    /** 事件类型 */
    private String eventType;
    
    /** 前置事件监听器集 */
    private List<EventListener> eventBeforeListeners = new ArrayList<EventListener>();
    
    /** 事件监听器集 */
    private List<EventListener> eventListeners = new ArrayList<EventListener>();
    
    /** 事件后置监听器集 */
    private List<EventListener> eventAfterListeners = new ArrayList<EventListener>();
    
    /** <默认构造函数> */
    public DefaultEventListenerSupport(String eventType,
            PlatformTransactionManager transactionManager) {
        super();
        AssertUtils.notEmpty(eventType, "eventType is empty.");
        AssertUtils.notNull(transactionManager, "transactionManager is null.");
        
        this.transactionManager = transactionManager;
        this.eventType = eventType;
    }
    
    /**
     * @return
     */
    @Override
    public String eventType() {
        return this.eventType;
    }
    
    /**
     * @param event
     * @param params
     */
    @Override
    public void execute(Event event, Map<String, Object> params) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("DefaultEventListenerSupport.execute");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        
        EventSessionContext.open();
        TransactionStatus status = this.transactionManager.getTransaction(def);
        try {
            //触发前置监听
            triggerBefore(event, params);
            //触发环绕监听
            trigger(event, params);
            //触发后置监听
            triggerAfter(event, params);
            
            this.transactionManager.commit(status);
        } catch (RuntimeException e) {
            this.transactionManager.rollback(status);
            throw e;
        } finally {
            EventSessionContext.close();
        }
    }
    
    /**
      * 触发事件前置监听<br/>
      *<功能详细描述>
      * @param event
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void triggerBefore(Event event, Map<String, Object> params) {
        if (!CollectionUtils.isEmpty(this.eventBeforeListeners)) {
            for (EventListener eventListenerTemp : this.eventBeforeListeners) {
                eventListenerTemp.execute(event, params);
            }
        }
    }
    
    /**
      * 触发环绕监听<br/>
      *<功能详细描述>
      * @param event
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void trigger(Event event, Map<String, Object> params) {
        if (!CollectionUtils.isEmpty(this.eventListeners)) {
            for (EventListener eventListenerTemp : this.eventListeners) {
                eventListenerTemp.execute(event, params);
            }
        }
    }
    
    /**
      * 触发后置监听<br/> 
      *<功能详细描述>
      * @param event
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void triggerAfter(Event event, Map<String, Object> params) {
        if (!CollectionUtils.isEmpty(this.eventAfterListeners)) {
            for (EventListener eventListenerTemp : this.eventAfterListeners) {
                eventListenerTemp.execute(event, params);
            }
        }
    }
    
    /**
     * @param eventListener
     */
    @Override
    public void addEventListener(EventListener eventListener) {
        AssertUtils.notNull(eventListener, "eventListener is null.");
        AssertUtils.notEmpty(eventListener.eventType(),
                "eventListener.eventType is empty.");
        AssertUtils.notNull(eventListener.scope(),
                "eventListener.scope:eventType:{} is null.",
                new Object[] { eventListener.eventType() });
        
        switch (eventListener.scope()) {
            case BEFORE:
                this.eventBeforeListeners.add(eventListener);
                break;
            case AROUND:
                this.eventListeners.add(eventListener);
                break;
            case AFTER:
                this.eventAfterListeners.add(eventListener);
                break;
            default:
                AssertUtils.isTrue(false,
                        "eventListener.scope:eventType:{} scope:{} is invalid.",
                        new Object[] { eventListener.eventType(),
                                eventListener.scope() });
        }
        Collections.sort(this.eventBeforeListeners, OrderComparator.INSTANCE);
        Collections.sort(this.eventListeners, OrderComparator.INSTANCE);
        Collections.sort(this.eventAfterListeners, OrderComparator.INSTANCE);
    }
}
