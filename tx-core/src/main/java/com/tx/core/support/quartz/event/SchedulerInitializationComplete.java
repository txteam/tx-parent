/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-28
 * <修改描述:>
 */
package com.tx.core.support.quartz.event;

import org.quartz.Scheduler;
import org.springframework.context.ApplicationEvent;

/**
 * Scheduler初始化完成事件<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SchedulerInitializationComplete extends ApplicationEvent {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4125528140233480627L;
    
    private String schedulerName;
    
    private Scheduler scheduler;
    
    /**
     * Scheduler初始化完成事件
     */
    public SchedulerInitializationComplete(String schedulerName,Scheduler scheduler, Object source) {
        super(source);
        this.schedulerName = schedulerName;
        this.scheduler = scheduler;
    }

    /**
     * @return 返回 schedulerName
     */
    public String getSchedulerName() {
        return schedulerName;
    }

    /**
     * @param 对schedulerName进行赋值
     */
    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    /**
     * @return 返回 scheduler
     */
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * @param 对scheduler进行赋值
     */
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
