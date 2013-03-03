/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-4
 * <修改描述:>
 */
package com.tx.component.workflow.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.ArrayUtils;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.workflow.exceptions.WorkflowAccessException;
import com.tx.component.workflow.model.ProTaskDefinition;
import com.tx.component.workflow.model.ProTransitionDefinition;
import com.tx.component.workflow.service.ProcessInstanceService;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * 流程实例业务层实例<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("processInstanceService")
public class ActivitiProcessServiceImpl implements InitializingBean,
        ProcessInstanceService {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(ActivitiProcessServiceImpl.class);
    
    /** activiti流程引擎 */
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;
    
    /** activiti 运行时业务方法服务 */
    private RuntimeService runtimeService;
    
    /** activiti 运行时任务方法服务 */
    private TaskService taskService;
    
    private RepositoryService repositoryService;
    
    /** 系统内流程定义业务层 */
    //@Resource(name = "processDefinitionService")
    //private ProcessDefinitionService processDefinitionService;
    
    //@Resource(name = "processEngineConfiguration")
    //private ProcessEngineConfigurationImpl processEngineConfiguration;
    
    /** 最大并发流程实例锁数，避免同一流程并发调用同一实例进行操作的情况 */
    private int processInsLockNum = 256;
    
    /** 流程实例锁 */
    private static Object[] processInsLocks;
    
    /**
     * 流程定义缓存
     */
    private Map<String, ProcessDefinitionEntity> processDefCache;
    
    /**
     * 流程环节缓存
     */
    private Map<String, Map<String, ActivityImpl>> activityImplCache;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskService = processEngine.getTaskService();
        this.repositoryService = processEngine.getRepositoryService();
        this.runtimeService = processEngine.getRuntimeService();
        
        //流程实例锁
        processInsLocks = new Object[processInsLockNum <= 0 ? 256
                : processInsLockNum];
        for (int i = 0; i < (processInsLockNum <= 0 ? 256 : processInsLockNum); i++) {
            processInsLocks[i] = new Object();
        }
        
        processDefCache = new HashMap<String, ProcessDefinitionEntity>();
        activityImplCache = new HashMap<String, Map<String, ActivityImpl>>();
    }
    
    /**
      * 开始一条流程实例，更多适用于，启动一条非最新版本的流程实例<br/>
      *     1、根据流程的最新版本创建一个流程实例<br/>
      *
      * @param processDefinitionKey 流程定义key
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public String start(String processDefinitionKey) {
        if (StringUtils.isEmpty(processDefinitionKey)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.start processDefinitionKey is empty.");
        }
        
        ProcessInstance pIns = this.runtimeService.startProcessInstanceByKey(processDefinitionKey);
        String pInsId = pIns.getProcessInstanceId();
        
        logger.debug("生成key为[{}]的流程实例.生成的流程实例id为:[{}]",
                processDefinitionKey,
                pInsId);
        return pInsId;
    }
    
    /**
      * 开始一条流程实例<br/>
      * id对应流程实例的唯一版本,key对应流程实例的多个版本<br/>
      *     1、根据流程的最新版本创建一个流程实例<br/>
      * @param processDefinitionId
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public String startByDefId(String processDefinitionId) {
        if (StringUtils.isEmpty(processDefinitionId)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.startByDefId processDefinitionId is empty.");
        }
        
        ProcessInstance pIns = this.runtimeService.startProcessInstanceById(processDefinitionId);
        String pInsId = pIns.getProcessInstanceId();
        
        logger.debug("生成id为[{}]的流程实例.生成的流程实例id为:[{}]",
                processDefinitionId,
                pInsId);
        return pInsId;
    }
    
    /**
      * 开始一条流程实例<br/>
      *     1、根据流程的最新版本创建一个流程实例<br/>
      *     
      * @param processDefinitionKey  流程定义key
      * @param variables  压入流程中当做流程变量的map可以为空
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public String start(String processDefinitionKey,
            Map<String, Object> variables) {
        if (StringUtils.isEmpty(processDefinitionKey)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.start processDefinitionKey is empty.");
        }
        
        ProcessInstance pIns = this.runtimeService.startProcessInstanceByKey(processDefinitionKey,
                variables);
        String pInsId = pIns.getProcessInstanceId();
        
        logger.debug("生成key为[{}]的流程实例.生成的流程实例id为:[{}]",
                processDefinitionKey,
                pInsId);
        return pInsId;
    }
    
    /**
      * 开始一条流程实例,更多适用于，启动一条非最新版本的流程实例<br/>
      * <功能详细描述>
      * @param processDefinitionId 流程定义id
      * @param variables 压入流程中当做流程变量的map可以为空
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public String startByDefId(String processDefinitionId,
            Map<String, Object> variables) {
        if (StringUtils.isEmpty(processDefinitionId)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.startByDefId processDefinitionId is empty.");
        }
        
        ProcessInstance pIns = this.runtimeService.startProcessInstanceById(processDefinitionId);
        String pInsId = pIns.getProcessInstanceId();
        
        logger.debug("生成id为[{}]的流程实例.生成的流程实例id为:[{}]",
                processDefinitionId,
                pInsId);
        return pInsId;
    }
    
    /**
      * 设置流程过程对象变量
      *     Execution的含义就是一个流程实例（ProcessInstance）具体要执行的过程对象<br/>
      *     ProcessInstance（1）--->Execution(N)，其中N >= 1<br/>
      *     值相等的情况：<br/>
      *     除了在流程中启动的子流程之外，流程启动之后在表ACT_RU_EXECUTION中的字段ID_和PROC_INST_ID_字段值是相同的。<br/>
      *     值不相等的情况：<br/>
      *     不相等的情况目前只会出现在子流程中（包含：嵌套、引入），<br/>
      *     例如一个购物流程中除了下单、出库节点之外可能还有一个付款子流程，<br/>
      *     在实际企业应用中付款流程通常是作为公用的，所以使用子流程作为主流程（购物流程）的一部分。<br/>
      *     当任务到达子流程时引擎会自动创建一个付款流程，但是这个流程有一个特殊的地方，在数据库可以直观体现，如下图。<br/>
      *     上图中有两条数据，第二条数据（嵌入的子流程）的PARENT_ID_等于第一条数据的ID_和PROC_INST_ID_，并且两条数据的PROC_INST_ID_相同。
      * <功能详细描述>
      * @param executionId
      * @param varibals [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void setVariables(String executionId, Map<String, Object> variables) {
        this.runtimeService.setVariables(executionId, variables);
    }
    
    /**
     * 设置流程过程对象变量
     * @param executionId
     * @param variableName
     * @param value
     */
    @Transactional
    public void setVariable(String executionId, String variableName,
            String value) {
        this.runtimeService.setVariable(executionId, variableName, value);
    }
    
    /**
     * 设置流程过程对象变量
     * <功能详细描述>
     * @param executionId
     * @param variables [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public void setVariablesLocal(String executionId,
            Map<String, Object> variables) {
        this.runtimeService.setVariablesLocal(executionId, variables);
    }
    
    /**
      * 设置流程过程对象变量
      *<功能简述>
      *<功能详细描述>
      * @param executionId
      * @param variableName
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void setVariableLocal(String executionId, String variableName,
            String value) {
        this.runtimeService.setVariableLocal(executionId, variableName, value);
    }
    
    /**
      * 设置流程当前任务节点变量
      * <功能详细描述>
      * @param processInstanceId
      * @param variableName
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void setTaskVaribal(String processInstanceId, String variableName,
            Object value) {
        Task task = getTaskByProInsId(processInstanceId);
        this.taskService.setVariable(task.getId(), variableName, value);
    }
    
    /**
      * 设置流程当前任务节点变量
      * <功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param variableName
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void setTaskVariableLocal(String processInstanceId,
            String currentTaskDefKey, String variableName, Object value) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                currentTaskDefKey);
        this.taskService.setVariableLocal(task.getId(), variableName, value);
    }
    
    /**
     * 获取流程环节实例变量集合
     * @param executionId
     * @return
     */
    public Map<String, Object> getVariables(String executionId) {
        return this.runtimeService.getVariables(executionId);
    }
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param executionId
      * @param variableNames
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, Object> getVariables(String executionId,
            Collection<String> variableNames) {
        return this.runtimeService.getVariables(executionId, variableNames);
    }
    
    /**
     * @param executionId
     * @param key
     * @return
     */
    public Object getVariable(String executionId, String variableName) {
        return this.runtimeService.getVariable(executionId, variableName);
    }
    
    /**
      * 获取指定流程实例的当前流程环节<br/>
      * <功能详细描述>
      * @param processInstanceId
      * @return [参数说明]
      * 
      * @return List<ProTaskDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ProTaskDefinition> getCurrentProTaskList(
            String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.complete processInstanceId is empty.");
        }
        
        List<Task> taskList = getTaskListByProInsId(processInstanceId);
        return null;
    }
    
    /**
      * <功能简述>
      * <功能详细描述>
      * @param processInstanceId
      * @return [参数说明]
      * 
      * @return ProTaskDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ProTaskDefinition getCurrentProTask(String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.complete processInstanceId is empty.");
        }
        Task task = getTaskByProInsId(processInstanceId);
        String processDefId = task.getProcessDefinitionId();
        String taskDefKey = task.getTaskDefinitionKey();
        return null;
    }
    
    /**
      * 完成当前流程环节任务，使其流入下一个流程环节任务,
      *     方法适用于不存在多个流程过程对象的情况，不适用于存在子流程的情况 <br/>
      *     1、如果当前流程实例存在多个在并行的任务将抛出异常 <br/>
      * <功能详细描述>
      * @param processInstanceId [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void complete(String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.complete processInstanceId is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //由流程实例id获取任务实例，如果存在并行任务，调用该方法将抛出异常
            Task task = getTaskByProInsId(processInstanceId);
            //完成并行任务
            this.taskService.complete(task.getId());
        }
    }
    
    /**
      * 完成指定流程环节任务，使流程流向下一个节点
      *<功能详细描述>
      * @param processInstanceId
      * @param taskVaribals [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void complete(String processInstanceId,
            Map<String, Object> taskVaribals) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.complete processInstanceId is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //由流程实例id获取任务实例，如果存在并行任务，调用该方法将抛出异常
            Task task = getTaskByProInsId(processInstanceId);
            //完成并行任务
            this.taskService.complete(task.getId(), taskVaribals);
        }
    }
    
    /**
      * 完成当前流程环节任务，使其流入下一个流程环节任务<br/>
      *     1、如果当前流程实例存在多个在并行的任务将抛出异常<br/>
      *     2、
      * <功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void complete(String processInstanceId, String currentTaskDefKey) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(currentTaskDefKey)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.complete processInstanceId or taskDefKey is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //完成当前任务实例id
            Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                    currentTaskDefKey);
            //完成并行任务
            this.taskService.complete(task.getId());
        }
    }
    
    /**
      * 完成当前流程环节任务，使其流入下一个流程环节任务<br/>
      * <功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param taskVaribals [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void complete(String processInstanceId, String currentTaskDefKey,
            Map<String, Object> taskVaribals) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(currentTaskDefKey)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.complete processInstanceId or currentTaskDefKey is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //完成当前任务实例id
            Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                    currentTaskDefKey);
            //完成并行任务
            this.taskService.complete(task.getId(), taskVaribals);
        }
    }
    
    /**
      * 完成当前流程任务环节，并返回下一个流程环节节点定义<br/>
      * 如果当前节点进行某一操作后可能出现分支并行任务时不能使用该方法
      *     1、如果当前流程环节存在多个，将会抛出异常<br/>
      *     2、如果下一个流程环节存在多个，也将会抛出异常<br/>
      *     3、调用该方法的地方需要自行考虑，如果发生并发时流程的流转问题<br/>
      * <功能详细描述>
      * @param processInstanceId(流程实例id)
      * @return [参数说明]
      * 
      * @return ProTransitionDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void pass(String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.pass processInstanceId is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            Task task = getTaskByProInsId(processInstanceId);
            
            this.taskService.complete(task.getId());
        }
    }
    
    /**
      * 完成当前流程任务环节，并返回下一个流程环节节点定义<br/>
      * 如果当前节点进行某一操作后可能出现分支并行任务时不能使用该方法
      * <功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @return [参数说明]
      * 
      * @return ProTaskDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void pass(String processInstanceId, String currentTaskDefKey) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(currentTaskDefKey)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.pass processInstanceId or taskDefKey is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //如果流程流转为并行节点，则不适合调用该方法，调用该方法这里讲会抛出异常
            Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                    currentTaskDefKey);
            
            this.taskService.complete(task.getId());
            
            Task newTask = getTaskByProInsId(processInstanceId);
        }
    }
    
    /**
     * 将流程流转入指定操作名的方向
     *     1、如果当前流程存在并行流程，调用该方法可能会发生异常
     * @param processInstanceId
     * @param transitionName
     */
    @Transactional
    public void process(String processInstanceId, String transitionName) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(transitionName)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.process processInstanceId or transitionName is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //获取当前流程执行过程
            ExecutionEntity exeEntity = getExecutionEntityByProInsId(processInstanceId);
            //获取流程当前任务
            Task task = getTaskByProInsId(processInstanceId);
            ActivityImpl currentActivity = getActivityById(exeEntity.getProcessDefinitionId(),
                    exeEntity.getActivityId());
            
            //获取当前流程实例的可流向操作
            List<PvmTransition> outGoingTransitionList = currentActivity.getOutgoingTransitions();
            
            //跳转到匹配的操作名节点方向
            List<PvmTransition> bakOutGoingTransitionList = new ArrayList<PvmTransition>();
            bakOutGoingTransitionList.addAll(outGoingTransitionList);
            for (PvmTransition transition : outGoingTransitionList) {
                //根据操作名进行进行流程流转
                if (transitionName.equals(transition.getProperty("name"))) {
                    outGoingTransitionList.clear();
                    outGoingTransitionList.add(transition);
                    this.taskService.complete(task.getId());
                    outGoingTransitionList.clear();
                    outGoingTransitionList.addAll(bakOutGoingTransitionList);
                    return;
                }
            }
            
            //如果不存在指定名称的操作
            throw new WorkflowAccessException(
                    "ProcessInstanceServic.process processInstanceId or taskDefKey is empty.");
        }
    }
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param transitionName 
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void process(String processInstanceId, String currentTaskDefKey,
            String transitionName) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(currentTaskDefKey)) {
            throw new ParameterIsEmptyException(
                    "ProcessInstanceServic.process processInstanceId or taskDefKey is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //获取当前流程执行过程
            ExecutionEntity exeEntity = getExecutionEntityByProInsIdAndTaskDefKey(processInstanceId,
                    currentTaskDefKey);
            //获取流程当前任务
            Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                    currentTaskDefKey);
            ActivityImpl currentActivity = getActivityById(exeEntity.getProcessDefinitionId(),
                    exeEntity.getActivityId());
            
            //获取当前流程实例的可流向操作
            List<PvmTransition> outGoingTransitionList = currentActivity.getOutgoingTransitions();
            
            //跳转到匹配的操作名节点方向
            List<PvmTransition> bakOutGoingTransitionList = new ArrayList<PvmTransition>();
            bakOutGoingTransitionList.addAll(outGoingTransitionList);
            for (PvmTransition transition : outGoingTransitionList) {
                //根据操作名进行进行流程流转
                if (transitionName.equals(transition.getProperty("name"))) {
                    outGoingTransitionList.clear();
                    outGoingTransitionList.add(transition);
                    this.taskService.complete(task.getId());
                    outGoingTransitionList.clear();
                    outGoingTransitionList.addAll(bakOutGoingTransitionList);
                    return;
                }
            }
            
            throw new WorkflowAccessException(
                    "ProcessInstanceServic.process processInstanceId or taskDefKey is empty.");
            
        }
    }
    
    /**
     * @param processInsId
     * @return
     */
    public List<ProTransitionDefinition> getCurrentTaskAllTransition(
            String processInsId) {
        // TODO Auto-generated method stub
        
        return null;
    }
    
    /**
      * 获取流程当前任务
      * <功能详细描述>
      * @param executionId
      * @return [参数说明]
      * 
      * @return List<ProTaskDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ProTaskDefinition> getCurrentTasks(String executionId) {
        //this.taskService.
        
        return null;
    }
    
    public ProTaskDefinition getCurrentTask(String processInstanceId) {
        TaskQuery taskQuery = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId);
        //如果流程流转为并行节点，则不适合调用该方法，调用该方法这里讲会抛出异常
        taskQuery.singleResult();
        
        return null;
    }
    
    public List<ProTaskDefinition> getCurrentTaskList(String processInstanceId) {
        TaskQuery taskQuery = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId);
        //
        taskQuery.singleResult();
        
        return null;
    }
    
    /**  
     * 流程转向操作  
     *   
     * @param taskId 当前任务ID  
     * @param activityId 目标节点任务ID  
     * @param variables 流程变量  
     * @throws Exception  
     */
    private static void turnTransition(String taskId, String activityId,
            Map<String, Object> variables) throws Exception {
        //        // 当前节点    
        //        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        //        // 清空当前流向    
        //        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);
        //        
        //        // 创建新流向    
        //        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        //        // 目标节点    
        //        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        //        // 设置新流向的目标节点    
        //        newTransition.setDestination(pointActivity);
        //        
        //        // 执行转向任务    
        //        taskService.complete(taskId, variables);
        //        // 删除目标节点新流入    
        //        pointActivity.getIncomingTransitions().remove(newTransition);
        //        
        //        // 还原以前流向    
        //        restoreTransition(currActivity, oriPvmTransitionList);
    }
    
    /**
      * 根据流程实例id以及任务定义key找到对应的task实例
      *
      * @param processInstanceId
      * @param taskDefinitionKey
      * @return [参数说明]
      * 
      * @return Task [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Task getTaskByProInsIdAndTaskDefKey(String processInstanceId,
            String currentTaskDefKey) {
        TaskQuery taskQuery = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey(currentTaskDefKey);
        
        Task task = taskQuery.singleResult();
        return task;
    }
    
    /**
      * 由流程实例id查询当前的任务环节<br/>
      *     1、如果当前流程环节存在多个，则不能使用该方法，使用该方法，将会导致系统抛出异常<br/>
      *     2、存在并行节点建议使用getTaskListByProcessInstanceId,或根据流程实例id以及当前的taskDefId去查询<br/>
      * <功能详细描述>
      * @param processInstanceId
      * @return [参数说明]
      * 
      * @return Task [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Task getTaskByProInsId(String processInstanceId) {
        TaskQuery taskQuery = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId);
        
        //如果流程流转为并行节点，则不适合调用该方法，调用该方法这里讲会抛出异常
        Task task = taskQuery.singleResult();
        return task;
    }
    
    //    
    //    /**
    //      * 由执行过程id查询当前任务环节<br/>
    //      *     在不存在子流程的情况下Process Excution是同一个实例<br/>
    //      *     1、如果当前执行过程中存在多个正在并行的流程节点，调用该方法将抛出异常<br/>
    //      * <功能详细描述>
    //      * @param executionId
    //      * @return [参数说明]
    //      * 
    //      * @return Task [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    @SuppressWarnings("unused")
    //    private Task getTaskByExecutionId(String executionId) {
    //        TaskQuery taskQuery = this.taskService.createTaskQuery()
    //                .executionId(executionId);
    //        //如果流程流转为并行节点，则不适合调用该方法，调用该方法这里讲会抛出异常
    //        Task task = taskQuery.singleResult();
    //        return null;
    //    }
    
    /**
      * 由当前流程实例id查询当前任务列表<br/>
      *     
      * <功能详细描述>
      * @param processInstanceId
      * @return [参数说明]
      * 
      * @return List<Task> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private List<Task> getTaskListByProInsId(String processInstanceId) {
        TaskQuery taskQuery = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId);
        List<Task> taskList = taskQuery.list();
        return taskList;
    }
    
    //    /**
    //      * <功能简述>
    //      * <功能详细描述>
    //      * @param executionId
    //      * @return [参数说明]
    //      * 
    //      * @return List<Task> [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    @SuppressWarnings("unused")
    //    private List<Task> getTaskListByExecutionId(String executionId) {
    //        TaskQuery taskQuery = this.taskService.createTaskQuery()
    //                .executionId(executionId);
    //        //如果流程流转为并行节点，则不适合调用该方法，调用该方法这里讲会抛出异常
    //        List<Task> taskList = taskQuery.list();
    //        return taskList;
    //    }
    
    /**
      * 根据流程实例id获取流程实例的过程对象<br/>
      *     Execution的含义就是一个流程实例（ProcessInstance）具体要执行的过程对象 <br/>
      *     ProcessInstance（1）--->Execution(N)，其中N >= 1 <br/>
      *     值相等的情况：<br/>
      *     除了在流程中启动的子流程之外，流程启动之后在表ACT_RU_EXECUTION中的字段ID_和PROC_INST_ID_字段值是相同的。<br/>
      *     值不相等的情况：<br/>
      *     不相等的情况目前只会出现在子流程中（包含：嵌套、引入），<br/>
      *     例如一个购物流程中除了下单、出库节点之外可能还有一个付款子流程，<br/>
      *     在实际企业应用中付款流程通常是作为公用的，所以使用子流程作为主流程（购物流程）的一部分。<br/>
      *     当任务到达子流程时引擎会自动创建一个付款流程，但是这个流程有一个特殊的地方，在数据库可以直观体现，如下图。<br/>
      *     上图中有两条数据，第二条数据（嵌入的子流程）的PARENT_ID_等于第一条数据的ID_和PROC_INST_ID_，并且两条数据的PROC_INST_ID_相同。
      * @param processInstanceId 流程实例id
      * @param forceSingle 
      *     是否强制转换为单一的流程实例<br/>
      *     如果为false如果对应流程对象存在多个过程对象时将会抛出异常<br/>
      *     ActivitiException("Query return "+results.size()+" results instead of max 1");<br/>
      * @return [参数说明]
      * 
      * @return ExecutionEntity [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private ExecutionEntity getExecutionEntityByProInsId(
            String processInstanceId) {
        ExecutionQuery exeQuery = this.runtimeService.createExecutionQuery()
                .processInstanceId(processInstanceId);
        
        //如果赌赢流程id对应到了多个excution此处将会抛出异常
        Execution res = exeQuery.singleResult();
        return (ExecutionEntity) res;
    }
    
    /**
      * 获取指定刘晨故事里id,以及任务定义key对应的流程过程对象实例<br/>
      * <功能详细描述>
      * @param processInstanceId
      * @param taskDefKey
      * @return [参数说明]
      * 
      * @return ExecutionEntity [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private ExecutionEntity getExecutionEntityByProInsIdAndTaskDefKey(
            String processInstanceId, String taskDefKey) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                taskDefKey);
        String executionId = task.getExecutionId();
        Execution res = getExecutionByExecutionId(executionId);
        return (ExecutionEntity) res;
    }
    
    /**
     * 私有方法:获取当前实例的过程对象,入参为流程实例的过程对象 <br/>

     *<功能详细描述>
     * @param executionId
     * @return [参数说明]
     * 
     * @return ExecutionEntity [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private Execution getExecutionByExecutionId(String executionId) {
        ExecutionQuery exeQuery = this.runtimeService.createExecutionQuery()
                .executionId(executionId);
        return exeQuery.singleResult();
    }
    
    /**
      * 由流程实例id获取锁对象
      * <功能详细描述>
      * @param proInsId
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Object getLockByProInsId(String proInsId) {
        int proInsIdHashCode = proInsId.hashCode();
        int proInsLockIndex = proInsIdHashCode
                % (processInsLockNum <= 0 ? 256 : processInsLockNum);
        return processInsLocks[proInsLockIndex];
    }
    
    /**
     * 判断是否传入了有效的businessKey
     *     1、根据流程的最新版本创建一个流程实例<br/>
     *     
     * @param businessKey
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("unused")
    private boolean businessKeyIsExsit(String[] businessKey) {
        if (ArrayUtils.isEmpty(businessKey)
                || StringUtils.isEmpty(businessKey[0])) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
      * 获取当前流程activiti定义
      * @param processDefinitionId
      * @return [参数说明]
      * 
      * @return ProcessDefinitionEntity [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private ProcessDefinitionEntity getProcessDefinitionByDefId(
            String processDefinitionId) {
        //DeploymentCache dc = this.processEngineConfiguration.getDeploymentCache();
        //ProcessDefinitionEntity pde = dc.findDeployedProcessDefinitionById(processDefinitionId);
        if (this.processDefCache.containsKey(processDefinitionId)) {
            return this.processDefCache.get(processDefinitionId);
        }
        
        ProcessDefinitionEntity res = (ProcessDefinitionEntity) this.repositoryService.getProcessDefinition(processDefinitionId);
        //ProcessDefinitionQuery pdQ  = this.repositoryService.createProcessDefinitionQuery().processDefinitionId();
        //ProcessDefinitionEntity res = (ProcessDefinitionEntity) pdQ.active().singleResult();
        
        if (res != null) {
            this.processDefCache.put(processDefinitionId, res);
        } else {
            throw new WorkflowAccessException("id为:{}流程定义不存在",
                    processDefinitionId);
        }
        
        return res;
    }
    
    /**
      * 获取指定流程定义中，指定activityId对应的节点activity对象
      * <功能详细描述>
      * @param processDefinitionId
      * @param activitiId
      * @return [参数说明]
      * 
      * @return ActivityImpl [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private ActivityImpl getActivityById(String processDefinitionId,
            String activityId) {
        //如果存在缓存，直接提取缓存中的数据
        if (activityImplCache.containsKey(processDefinitionId)) {
            if (activityImplCache.get(processDefinitionId)
                    .containsKey(activityId)) {
                return activityImplCache.get(processDefinitionId)
                        .get(activityId);
            }
        } else {
            activityImplCache.put(processDefinitionId,
                    new HashMap<String, ActivityImpl>());
        }
        
        //获取流程定义
        ProcessDefinitionEntity pde = getProcessDefinitionByDefId(processDefinitionId);
        Map<String, ActivityImpl> actMap = activityImplCache.get(processDefinitionId);
        for (ActivityImpl activityTemp : pde.getActivities()) {
            actMap.put(activityTemp.getId(), activityTemp);
        }
        
        //返回映射的activityId 
        return actMap.get(activityId);
    }
}
