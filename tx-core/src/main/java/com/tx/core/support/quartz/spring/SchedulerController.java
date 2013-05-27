/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-22
 * <修改描述:>
 */
package com.tx.core.support.quartz.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;

import com.tx.core.support.quartz.event.SchedulerControlEvent;
import com.tx.core.support.quartz.event.SchedulerControlEventTypeEnum;

/**
 * 调度器控制器<br/>
 *     用于添加到配置文件中，实现代码级别的调度器启停<br/
 *     因注解内直接注入Scheduler实例启动时要抛出异常<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SchedulerController implements
        ApplicationListener<SchedulerControlEvent>, InitializingBean{
    
    /** 日志记录器 */
    private Logger logger = LoggerFactory.getLogger(SchedulerController.class);
    
    /** 注入的调度器 */
    private List<Scheduler> schedulers = new ArrayList<Scheduler>();
    
    private Map<String, Scheduler> schedulerMapping = new HashMap<String, Scheduler>();
    
    
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("初始化调度控制器.");
        
        if (CollectionUtils.isEmpty(schedulers)) {
            logger.info("初始化调度控制器完成.共托管调度控制器0个.");
            return;
        }
        
        for (Scheduler sTemp : schedulers) {
            schedulerMapping.put(sTemp.getSchedulerName(), sTemp);
            logger.info("      托管调度控制器scheduleName:{}是否启动:{}.", new Object[] {
                    sTemp.getSchedulerName(), sTemp.isStarted() });
        }
        
        logger.info("初始化调度控制器完成.共托管调度控制器{}个.",
                String.valueOf(schedulers.size()));
    }
    
    /**
     * @param event
     */
    @Override
    public void onApplicationEvent(SchedulerControlEvent event) {
        logger.error("  接收到调度器控制事件.");
        if (event == null || StringUtils.isEmpty(event.getSchedulerName())
                || event.getType() == null) {
            logger.error("接收事件调度事件异常，调度器名不能为空，调度事件类型不能为空.");
            return;
        }
        
        String scheduleName = event.getSchedulerName();
        SchedulerControlEventTypeEnum type = event.getType();
        if (!schedulerMapping.containsKey(scheduleName)) {
            logger.error("接收事件调度事件异常，调度器{}未托管入调度控制中.", scheduleName);
            return;
        }
        Scheduler currentScheduler = schedulerMapping.get(scheduleName);
        boolean isStarted = false;
        try {
            isStarted = currentScheduler.isStarted();
        } catch (SchedulerException e) {
            logger.error("接收事件调度事件异常，获取调度器{}当前状态出现异常：{}.", new Object[] {
                    scheduleName, e });
            return;
        }
        
        logger.error("接收事件调度事件:调度器{},事件类型:{},对应调度器状态:{}.", new Object[] {
                scheduleName, type, isStarted });
        switch (type) {
            case 启动事件:
                logger.error("      接收到启动调度器{}事件:",scheduleName);
                if (!isStarted){
                    try {
                        currentScheduler.start();
                    } catch (SchedulerException e) {
                        logger.error("      调度器{}启动异常，异常信息:{}", new Object[] {
                                scheduleName, e });
                        return;
                    }
                }else{
                    logger.error("      接收到启动调度器{}事件:对应调度器已启动",scheduleName);
                }
                break;
            case 关闭事件:
                logger.error("      接收到关闭调度器{}事件:",scheduleName);
                if (isStarted){
                    try {
                        currentScheduler.start();
                    } catch (SchedulerException e) {
                        logger.error("      调度器{}关闭异常，异常信息:{}", new Object[] {
                                scheduleName, e });
                        return;
                    }
                }else{
                    logger.error("      接收到关闭调度器{}事件:对应调度器未启动",scheduleName);
                }
                break;
        }
    }

    /**
     * @return 返回 schedulers
     */
    public List<Scheduler> getSchedulers() {
        return schedulers;
    }

    /**
     * @param 对schedulers进行赋值
     */
    public void setSchedulers(List<Scheduler> schedulers) {
        this.schedulers = schedulers;
    }
}
