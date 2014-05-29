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
public class WorkflowContext implements InitializingBean{
    
    @SuppressWarnings("unused")
    //日志记录器
    private static Logger logger = LoggerFactory.getLogger(WorkflowContext.class);
    
    //processEngine启动保证了流程引擎自动加载
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;
    
    @SuppressWarnings("unused")
    private RuntimeService runtimeService;
    
    @SuppressWarnings("unused")
    private TaskService taskService;
    
    @SuppressWarnings("unused")
    private RepositoryService repositoryService;
    
    @SuppressWarnings("unused")
    private org.springframework.core.io.Resource[] deploymentResources;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskService = processEngine.getTaskService();
        this.repositoryService = processEngine.getRepositoryService();
        this.runtimeService = processEngine.getRuntimeService();
    }
}
