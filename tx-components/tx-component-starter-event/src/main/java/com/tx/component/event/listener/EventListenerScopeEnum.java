/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.component.event.listener;

/**
 * 事件监听器生命周期范围枚举型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum EventListenerScopeEnum {
    
    /** 前置事件监听器 */
    BEFORE,
    /** 前置事件监听器 */
    AROUND,
    /** 后置事件监听器 */
    AFTER;
}
