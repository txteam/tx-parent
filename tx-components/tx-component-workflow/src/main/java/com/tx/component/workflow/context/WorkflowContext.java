/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-16
 * <修改描述:>
 */
package com.tx.component.workflow.context;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.SchedulingException;

/**
 * 流程容器<br/>
 * 1、用以在容器中，利用线程变量获取到权限，当前登录人员等信息<br/>
 * (注：具备工作流容器了，但具体接口实现，扔保留了操作人员id的概念，<br/>
 *     主要是用以支持当登录人，以及操作人不同的情况)<br/>
 * 2、实现非流程关键信息的，隐形传参<br/>
 * @author  brady
 * @version  [版本号, 2013-1-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Resource(name = "workflowContext")
public class WorkflowContext implements InitializingBean, SmartLifecycle {
    
    //日志记录器
    private static Logger logger = LoggerFactory.getLogger(WorkflowContext.class);
    
    //processEngine启动保证了流程引擎自动加载
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;
    
    private RuntimeService runtimeService;
    
    private TaskService taskService;
    
    private RepositoryService repositoryService;
    
    private org.springframework.core.io.Resource[] deploymentResources;
    
    private boolean autoStartup;
    
    private int phase = Integer.MAX_VALUE;
    
    private int startupDelay = 0;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskService = processEngine.getTaskService();
        this.repositoryService = processEngine.getRepositoryService();
        this.runtimeService = processEngine.getRuntimeService();
    }
    
    /**
     * @return
     */
    @Override
    public int getPhase() {
        return this.phase;
    }
    
    /**
     * 
     */
    @Override
    public void start() {
        if (this.repositoryService != null) {
//            try {
//                startWorkflowContext(this.repositoryService, this.deploymentResources);
//            } catch (SchedulerException ex) {
//                throw new SchedulingException(
//                        "Could not start Quartz Scheduler", ex);
//            }
        }
    }
    
    /**
     * 
     */
    @Override
    public void stop() {
        //doNoThing
    }
    
    /**
     * @return
     */
    @Override
    public boolean isRunning() {
        return true;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isAutoStartup() {
        return this.autoStartup;
    }
    
    /**
     * @param callback
     */
    @Override
    public void stop(Runnable callback) {
        //doNoThing
    }
    
    /**
      * 加载系统流程<br/>
      * <功能详细描述>
      * @param repositoryServiceParameter
      * @param deploymentResourcesParameter
      * @throws SchedulerException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void startWorkflowContext(final RepositoryService repositoryServiceParameter,
            final org.springframework.core.io.Resource[] deploymentResourcesParameter){
        //启动加载工作流
//        if (startupDelay <= 0) {
//            logger.info("Starting WorkflowContext Scheduler now");
//            scheduler.start();
//        } else {
//            if (logger.isInfoEnabled()) {
//                logger.info("Will start Quartz Scheduler ["
//                        + scheduler.getSchedulerName() + "] in " + startupDelay
//                        + " seconds");
//            }
//            Thread schedulerThread = new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(startupDelay * 1000);
//                    } catch (InterruptedException ex) {
//                        // simply proceed
//                    }
//                    if (logger.isInfoEnabled()) {
//                        logger.info("Starting Quartz Scheduler now, after delay of "
//                                + startupDelay + " seconds");
//                    }
//                    try {
//                        scheduler.start();
//                    } catch (SchedulerException ex) {
//                        throw new SchedulingException(
//                                "Could not start Quartz Scheduler after delay",
//                                ex);
//                    }
//                }
//            };
//            schedulerThread.setName("Quartz Scheduler ["
//                    + scheduler.getSchedulerName() + "]");
//            schedulerThread.setDaemon(true);
//            schedulerThread.start();
//        }
    }
}
