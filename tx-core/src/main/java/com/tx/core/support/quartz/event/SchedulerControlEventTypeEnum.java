/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-22
 * <修改描述:>
 */
package com.tx.core.support.quartz.event;

/**
 * 调度控制事件类型枚举
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum SchedulerControlEventTypeEnum {
    /**
     * 命令对应调度器启动
     */
    启动事件,
    /**
     * 命令对应掉对其关闭
     */
    关闭事件;
    
}
