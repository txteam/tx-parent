/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-22
 * <修改描述:>
 */
package com.tx.core.support.quartz.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * 调度控制器事件
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SchedulerControlEvent extends ApplicationContextEvent {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2229376564165573731L;
    
    private String schedulerName;
    
    private SchedulerControlEventTypeEnum type;
    
    /** <默认构造函数> */
    public SchedulerControlEvent(String schedulerName,
            SchedulerControlEventTypeEnum type, ApplicationContext source) {
        super(source);
        
        this.schedulerName = schedulerName;
        this.type = type;
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
     * @return 返回 type
     */
    public SchedulerControlEventTypeEnum getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(SchedulerControlEventTypeEnum type) {
        this.type = type;
    }
}
