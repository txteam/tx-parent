/**
 * 文 件 名:  ConfigContext.java
 * 版    权:  TX Workgroup . Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  PengQingyang
 * 修改时间:  2012-10-5
 * <修改描述:>
 */
package com.tx.component.config.context;

import org.quartz.Scheduler;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * <配置容器>
 * <用以加载系统配置，支持动态加载系统中各配置>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContext extends PropertyPlaceholderConfigurer {
    
    private Scheduler scheduler;

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
