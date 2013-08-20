/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-4
 * <修改描述:>
 */
package com.tx.component.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.workflow.activiti5.ActivitiProcessDefinitionSupport;
import com.tx.component.workflow.exceptions.WorkflowAccessException;
import com.tx.component.workflow.model.ProTaskInstance;
import com.tx.component.workflow.model.impl.ActivitiProTaskInstance;
import com.tx.component.workflow.model.impl.ActivitiProcessInstance;
import com.tx.component.workflow.service.ProcessInstanceService;
import com.tx.core.exceptions.argument.NullArgException;

/**
 * 流程实例业务层实例<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("processInstanceService")
public class ActivitiProcessInstanceServiceImpl implements InitializingBean,
        ProcessInstanceService {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(ActivitiProcessInstanceServiceImpl.class);
    
    @Resource(name = "activitiProcessDefinitionSupport")
    private ActivitiProcessDefinitionSupport activitiProcessDefinitionSupport;
    
    /** activiti流程引擎 */
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;
    
    /** activiti 运行时业务方法服务 */
    private RuntimeService runtimeService;
    
    /** activiti 运行时任务方法服务 */
    private TaskService taskService;
    
    /** 最大并发流程实例锁数，避免同一流程并发调用同一实例进行操作的情况 */
    private int processInsLockNum = 256;
    
    /** 流程实例锁 */
    private static Object[] processInsLocks;
    
    private int processDefLockNum = 256;
    
    private static Object[] processDefLocks;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskService = processEngine.getTaskService();
        this.runtimeService = processEngine.getRuntimeService();
        
        //流程实例锁
        processInsLocks = new Object[processInsLockNum <= 0 ? 256
                : processInsLockNum];
        for (int i = 0; i < (processInsLockNum <= 0 ? 256 : processInsLockNum); i++) {
            processInsLocks[i] = new Object();
        }
        
        //流程定义处理
        processDefLocks = new Object[processDefLockNum <= 0 ? 256
                : processDefLockNum];
        for (int i = 0; i < (processDefLockNum <= 0 ? 256 : processDefLockNum); i++) {
            processDefLocks[i] = new Object();
        }
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
    @Override
    @Transactional
    public com.tx.component.workflow.model.ProcessInstance start(
            String processDefinitionKey) {
        if (StringUtils.isEmpty(processDefinitionKey)) {
            throw new NullArgException(
                    "ProcessInstanceServic.start processDefinitionKey is empty.");
        }
        
        ProcessInstance pIns = this.runtimeService.startProcessInstanceByKey(processDefinitionKey);
        
        String pInsId = pIns.getProcessInstanceId();
        logger.debug("生成key为[{}]的流程实例.生成的流程实例id为:[{}]",
                processDefinitionKey,
                pInsId);
        
        return new ActivitiProcessInstance(pIns);
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
    @Override
    @Transactional
    public com.tx.component.workflow.model.ProcessInstance startByDefId(
            String processDefinitionId) {
        if (StringUtils.isEmpty(processDefinitionId)) {
            throw new NullArgException(
                    "ProcessInstanceServic.startByDefId processDefinitionId is empty.");
        }
        
        ProcessInstance pIns = this.runtimeService.startProcessInstanceById(processDefinitionId);
        
        String pInsId = pIns.getProcessInstanceId();
        logger.debug("生成id为[{}]的流程实例.生成的流程实例id为:[{}]",
                processDefinitionId,
                pInsId);
        
        return new ActivitiProcessInstance(pIns);
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
    @Override
    @Transactional
    public com.tx.component.workflow.model.ProcessInstance start(
            String processDefinitionKey, Map<String, Object> variables) {
        if (StringUtils.isEmpty(processDefinitionKey)) {
            throw new NullArgException(
                    "ProcessInstanceServic.start processDefinitionKey is empty.");
        }
        
        ProcessInstance pIns = this.runtimeService.startProcessInstanceByKey(processDefinitionKey,
                variables);
        
        String pInsId = pIns.getProcessInstanceId();
        logger.debug("生成key为[{}]的流程实例.生成的流程实例id为:[{}]",
                processDefinitionKey,
                pInsId);
        
        return new ActivitiProcessInstance(pIns);
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
    @Override
    @Transactional
    public com.tx.component.workflow.model.ProcessInstance startByDefId(
            String processDefinitionId, Map<String, Object> variables) {
        if (StringUtils.isEmpty(processDefinitionId)) {
            throw new NullArgException(
                    "ProcessInstanceServic.startByDefId processDefinitionId is empty.");
        }
        
        ProcessInstance pIns = this.runtimeService.startProcessInstanceById(processDefinitionId);
        
        String pInsId = pIns.getProcessInstanceId();
        logger.debug("生成id为[{}]的流程实例.生成的流程实例id为:[{}]",
                processDefinitionId,
                pInsId);
        
        return new ActivitiProcessInstance(pIns);
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    @Transactional
    public void setVariableLocal(String executionId, String variableName,
            String value) {
        this.runtimeService.setVariableLocal(executionId, variableName, value);
    }
    
    /**
     * @param executionId
     * @param variableName
     * @return
     */
    @Override
    @Transactional
    public Object getVariableLocal(String executionId, String variableName) {
        return this.runtimeService.getVariableLocal(executionId, variableName);
    }
    
    /**
     * @param executionId
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> getVariablesLocal(String executionId) {
        return this.runtimeService.getVariablesLocal(executionId);
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
    @Override
    @Transactional
    public void setTaskVariableLocal(String processInstanceId,
            String currentTaskDefKey, String variableName, Object value) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                currentTaskDefKey);
        this.taskService.setVariableLocal(task.getId(), variableName, value);
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
    @Override
    @Transactional
    public void setTaskVariable(String processInstanceId, String variableName,
            Object value) {
        Task task = getTaskByProInsId(processInstanceId);
        this.taskService.setVariable(task.getId(), variableName, value);
    }
    
    /**
     * 获取流程环节实例变量集合
     * @param executionId
     * @return
     */
    @Override
    public Map<String, Object> getVariables(String executionId) {
        return this.runtimeService.getVariables(executionId);
    }
    
    /**
     * 获取流程实例变量
     * @param executionId
     * @param variableName
     * @return
     */
    @Override
    public Object getVariable(String executionId, String variableName) {
        return this.runtimeService.getVariable(executionId, variableName);
    }
    
    /**
     * @param processInstanceId
     * @param currentTaskDefKey
     * @param variableName
     * @param value
     */
    @Override
    @Transactional
    public void setTaskVariable(String processInstanceId,
            String currentTaskDefKey, String variableName, Object value) {
        Task task = getTaskByProInsId(processInstanceId);
        this.taskService.setVariable(task.getId(), variableName, value);
    }
    
    /**
     * @param processInstanceId
     * @param variableName
     * @param variables
     */
    @Override
    @Transactional
    public void setTaskVariables(String processInstanceId,
            Map<String, Object> variables) {
        Task task = getTaskByProInsId(processInstanceId);
        this.taskService.setVariables(task.getId(), variables);
    }
    
    /**
     * @param processInstanceId
     * @param currentTaskDefKey
     * @param variableName
     * @param variables
     */
    @Override
    @Transactional
    public void setTaskVariables(String processInstanceId,
            String currentTaskDefKey, Map<String, Object> variables) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                currentTaskDefKey);
        this.taskService.setVariables(task.getId(), variables);
    }
    
    /**
     * @param processInstanceId
     * @param variableName
     * @return
     */
    @Override
    public Object getTaskVarible(String processInstanceId, String variableName) {
        Task task = getTaskByProInsId(processInstanceId);
        return this.taskService.getVariable(task.getId(), variableName);
    }
    
    /**
     * @param processInstanceId
     * @param currentTaskDefKey
     * @param variableName
     * @return
     */
    @Override
    public Object getTaskVarible(String processInstanceId,
            String currentTaskDefKey, String variableName) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                currentTaskDefKey);
        return this.taskService.getVariable(task.getId(), variableName);
    }
    
    /**
     * @param processInstanceId
     * @param variableName
     * @return
     */
    @Override
    public Map<String, Object> getTaskVaribles(String processInstanceId) {
        Task task = getTaskByProInsId(processInstanceId);
        return this.taskService.getVariables(task.getId());
    }
    
    /**
     * @param processInstanceId
     * @param currentTaskDefKey
     * @param variableName
     * @return
     */
    @Override
    public Map<String, Object> getTaskVaribles(String processInstanceId,
            String currentTaskDefKey) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                currentTaskDefKey);
        return this.taskService.getVariables(task.getId());
    }
    
    /**
     * @param processInstanceId
     * @param variableName
     * @param value
     */
    @Override
    @Transactional
    public void setTaskVariableLocal(String processInstanceId,
            String variableName, Object value) {
        Task task = getTaskByProInsId(processInstanceId);
        this.taskService.setVariableLocal(task.getId(), variableName, value);
    }
    
    /**
     * @param processInstanceId
     * @param variableName
     * @param variables
     */
    @Override
    @Transactional
    public void setTaskVariablesLocal(String processInstanceId,
            Map<String, Object> variables) {
        Task task = getTaskByProInsId(processInstanceId);
        this.taskService.setVariablesLocal(task.getId(), variables);
    }
    
    /**
     * @param processInstanceId
     * @param currentTaskDefKey
     * @param variableName
     * @param variables
     */
    @Override
    @Transactional
    public void setTaskVariablesLocal(String processInstanceId,
            String currentTaskDefKey, Map<String, Object> variables) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                currentTaskDefKey);
        this.taskService.setVariablesLocal(task.getId(), variables);
    }
    
    /**
     * @param processInstanceId
     * @param variableName
     * @return
     */
    @Override
    public Object getTaskVaribleLocal(String processInstanceId,
            String variableName) {
        Task task = getTaskByProInsId(processInstanceId);
        return this.taskService.getVariableLocal(task.getId(), variableName);
    }
    
    /**
     * @param processInstanceId
     * @param currentTaskDefKey
     * @param variableName
     * @return
     */
    @Override
    public Object getTaskVaribleLocal(String processInstanceId,
            String currentTaskDefKey, String variableName) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                currentTaskDefKey);
        return this.taskService.getVariableLocal(task.getId(), variableName);
    }
    
    /**
     * @param processInstanceId
     * @param variableName
     * @return
     */
    @Override
    public Map<String, Object> getTaskVariblesLocal(String processInstanceId) {
        Task task = getTaskByProInsId(processInstanceId);
        return this.taskService.getVariablesLocal(task.getId());
    }
    
    /**
     * @param processInstanceId
     * @param currentTaskDefKey
     * @param variableName
     * @return
     */
    @Override
    public Map<String, Object> getTaskVariblesLocal(String processInstanceId,
            String currentTaskDefKey) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                currentTaskDefKey);
        return this.taskService.getVariablesLocal(task.getId());
    }
    
    /**
      * 完成当前流程环节任务，使其流入下一个流程环节任务,
      *     方法适用于不存在多个流程过程对象的情况，不适用于存在子流程的情况 <br/>
      *     1、如果当前流程实例存在多个在并行的任务将抛出异常 <br/>
      *<功能详细描述>
      * @param processInstanceId
      * @param taskVaribals [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Override
    @Transactional
    public void complete(String processInstanceId,
            Map<String, Object> taskVaribals) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new NullArgException(
                    "ProcessInstanceServic.complete processInstanceId is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //由流程实例id获取任务实例，如果存在并行任务，调用该方法将抛出异常
            Task task = getTaskByProInsId(processInstanceId);
            
            //完成并行任务
            if (taskVaribals != null) {
                this.taskService.complete(task.getId(), taskVaribals);
            } else {
                this.taskService.complete(task.getId());
            }
        }
    }
    
    /**
      * 完成当前流程环节任务，使其流入下一个流程环节任务<br/>
      *     1、如果当前流程实例存在多个在并行的任务将抛出异常<br/>
      * <功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param taskVaribals(流程变量)
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Override
    @Transactional
    public void complete(String processInstanceId, String currentTaskDefKey,
            Map<String, Object> taskVaribals) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(currentTaskDefKey)) {
            throw new NullArgException(
                    "ProcessInstanceServic.complete processInstanceId or currentTaskDefKey is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //完成当前任务实例id
            Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                    currentTaskDefKey);
            //完成并行任务
            if (taskVaribals != null) {
                this.taskService.complete(task.getId(), taskVaribals);
            } else {
                this.taskService.complete(task.getId());
            }
        }
    }
    
    /**
     * 将流程流转入指定操作名的方向
     *     1、如果当前流程存在并行流程，调用该方法可能会发生异常
     * @param processInstanceId
     * @param transitionName
     * @param taskVaribals
     */
    @Override
    @Transactional
    public void process(String processInstanceId, String transitionName,
            Map<String, Object> taskVaribals) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(transitionName)) {
            throw new NullArgException(
                    "ProcessInstanceServic.process processInstanceId or transitionName is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //获取流程当前任务
            TaskEntity task = (TaskEntity) getTaskByProInsId(processInstanceId);
            //获取当前的activity实例
            //ActivityImpl currentExecutionActivity = getExecutionByExecutionId(task.getExecutionId()).getActivity();
            ActivityImpl currentTaskActivity = activitiProcessDefinitionSupport.getActivityById(task.getProcessDefinitionId(),
                    task.getTaskDefinitionKey());
            synchronized (getLockByProDefId(task.getProcessDefinitionId(),
                    task.getTaskDefinitionKey())) {
                turnByTransitionName(processInstanceId,
                        transitionName,
                        task,
                        currentTaskActivity,
                        taskVaribals);
            }
        }
    }
    
    /**
      * 将流程流转入指定操作名的方向
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param transitionName 
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Override
    @Transactional
    public void process(String processInstanceId, String currentTaskDefKey,
            String transitionName, Map<String, Object> taskVaribals) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(currentTaskDefKey)) {
            throw new NullArgException(
                    "ProcessInstanceServic.process processInstanceId or currentTaskDefKey is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //获取流程当前任务
            TaskEntity task = (TaskEntity) getTaskByProInsIdAndTaskDefKey(processInstanceId,
                    currentTaskDefKey);
            //获取当前的activity实例
            //ActivityImpl currentExecutionActivity = getExecutionByExecutionId(task.getExecutionId()).getActivity();
            ActivityImpl currentTaskActivity = activitiProcessDefinitionSupport.getActivityById(task.getProcessDefinitionId(),
                    task.getTaskDefinitionKey());
            synchronized (getLockByProDefId(task.getProcessDefinitionId(),
                    task.getTaskDefinitionKey())) {
                turnByTransitionName(processInstanceId,
                        transitionName,
                        task,
                        currentTaskActivity,
                        taskVaribals);
            }
        }
    }
    
    /** 
     * 将流程根据指定操作进行流转
     * @param processInstanceId
     * @param transitionName
     * @param task
     * @param currentActivity
     * @param taskVaribals [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void turnByTransitionName(String processInstanceId,
            String transitionName, TaskEntity task,
            ActivityImpl currentActivity, Map<String, Object> taskVaribals) {
        //获取当前流程实例的可流向操作
        List<PvmTransition> outGoingTransitionList = currentActivity.getOutgoingTransitions();
        //跳转到匹配的操作名节点方向
        //备份原节点所有流向
        List<PvmTransition> bakOutGoingTransitionList = new ArrayList<PvmTransition>();
        bakOutGoingTransitionList.addAll(outGoingTransitionList);
        List<PvmTransition> newTransitionList = new ArrayList<PvmTransition>();
        for (PvmTransition transition : outGoingTransitionList) {
            //根据操作名进行进行流程流转
            //同操作名的pvmTransition保留，由condition自行进行判断
            if (transitionName.equals(transition.getProperty("name"))) {
                newTransitionList.add(transition);
            }
        }
        if (newTransitionList.size() == 0) {
            //如果不存在指定名称的操作
            throw new WorkflowAccessException(
                    "ProcessInstanceServic.process:{} transition:{} not exist.",
                    processInstanceId, transitionName);
        }
        
        //清空原流向集合
        outGoingTransitionList.clear();
        //写入新的流向集合
        outGoingTransitionList.addAll(newTransitionList);
        //执行流向
        if (taskVaribals != null) {
            this.taskService.complete(task.getId(), taskVaribals);
        } else {
            this.taskService.complete(task.getId());
        }
        //清空新的流向
        outGoingTransitionList.clear();
        //写入原来流向
        outGoingTransitionList.addAll(bakOutGoingTransitionList);
    }
    
    /**
     * @param processInstanceId
     * @param transitionId
     * @param taskVaribals
     */
    @Override
    public void processById(String processInstanceId, String transitionId,
            Map<String, Object> taskVaribals) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(transitionId)) {
            throw new NullArgException(
                    "ProcessInstanceServic.process processInstanceId or transitionId is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //获取流程当前任务
            TaskEntity task = (TaskEntity) getTaskByProInsId(processInstanceId);
            //获取当前的activity实例
            //ActivityImpl currentExecutionActivity = getExecutionByExecutionId(task.getExecutionId()).getActivity();
            
            ActivityImpl currentTaskActivity = activitiProcessDefinitionSupport.getActivityById(task.getProcessDefinitionId(),
                    task.getTaskDefinitionKey());
            synchronized (getLockByProDefId(task.getProcessDefinitionId(),
                    task.getTaskDefinitionKey())) {
                turnByTransitionId(processInstanceId,
                        transitionId,
                        task,
                        currentTaskActivity,
                        taskVaribals);
            }
        }
    }
    
    /**
     * @param processInstanceId
     * @param currentTaskDefKey
     * @param transitionId
     * @param taskVaribals
     */
    @Override
    public void processById(String processInstanceId, String currentTaskDefKey,
            String transitionId, Map<String, Object> taskVaribals) {
        if (StringUtils.isEmpty(processInstanceId)
                || StringUtils.isEmpty(currentTaskDefKey)
                || StringUtils.isEmpty(transitionId)) {
            throw new NullArgException(
                    "ProcessInstanceServic.process processInstanceId or currentTaskDefKey or transitionId is empty.");
        }
        synchronized (getLockByProInsId(processInstanceId)) {
            //获取流程当前任务
            TaskEntity task = (TaskEntity) getTaskByProInsIdAndTaskDefKey(processInstanceId,
                    currentTaskDefKey);
            //获取当前的activity实例
            //ActivityImpl currentExecutionActivity = getExecutionByExecutionId(task.getExecutionId()).getActivity();
            
            ActivityImpl currentTaskActivity = activitiProcessDefinitionSupport.getActivityById(task.getProcessDefinitionId(),
                    task.getTaskDefinitionKey());
            synchronized (getLockByProDefId(task.getProcessDefinitionId(),
                    task.getTaskDefinitionKey())) {
                turnByTransitionId(processInstanceId,
                        transitionId,
                        task,
                        currentTaskActivity,
                        taskVaribals);
            }
        }
    }
    
    /** 
     * 将流程根据指定操作进行流转
     * @param processInstanceId
     * @param transitionId
     * @param task
     * @param currentActivity
     * @param taskVaribals [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void turnByTransitionId(String processInstanceId,
            String transitionId, TaskEntity task, ActivityImpl currentActivity,
            Map<String, Object> taskVaribals) {
        //获取当前流程实例的可流向操作
        List<PvmTransition> outGoingTransitionList = currentActivity.getOutgoingTransitions();
        //跳转到匹配的操作名节点方向
        //备份原节点所有流向
        List<PvmTransition> bakOutGoingTransitionList = new ArrayList<PvmTransition>();
        bakOutGoingTransitionList.addAll(outGoingTransitionList);
        List<PvmTransition> newTransitionList = new ArrayList<PvmTransition>();
        for (PvmTransition transition : outGoingTransitionList) {
            //根据操作名进行进行流程流转
            //同操作名的pvmTransition保留，由condition自行进行判断
            if (transitionId.equals(transition.getId())) {
                newTransitionList.add(transition);
            }
        }
        if (newTransitionList.size() == 0) {
            //如果不存在指定名称的操作
            throw new WorkflowAccessException(
                    "ProcessInstanceServic.process:{} transition:{} not exist.",
                    processInstanceId, transitionId);
        }
        
        //清空原流向集合
        outGoingTransitionList.clear();
        //写入新的流向集合
        outGoingTransitionList.addAll(newTransitionList);
        //执行流向
        if (taskVaribals != null) {
            this.taskService.complete(task.getId(), taskVaribals);
        } else {
            this.taskService.complete(task.getId());
        }
        //清空新的流向
        outGoingTransitionList.clear();
        //写入原来流向
        outGoingTransitionList.addAll(bakOutGoingTransitionList);
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
    @Override
    public List<ProTaskInstance> getCurrentProTaskList(String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new NullArgException(
                    "ProcessInstanceServic.complete processInstanceId is empty.");
        }
        
        List<ProTaskInstance> res = new ArrayList<ProTaskInstance>();
        List<Task> taskList = getTaskListByProInsId(processInstanceId);
        for(Task taskTemp : taskList){
            res.add(new ActivitiProTaskInstance(taskTemp));
        }
        return res;
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
    @Override
    public ProTaskInstance getCurrentProTask(String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new NullArgException(
                    "ProcessInstanceServic.complete processInstanceId is empty.");
        }
        Task task = getTaskByProInsId(processInstanceId);
        ProTaskInstance res = new ActivitiProTaskInstance(task);
        return res;
    }
    
    /**
     * @param processInstanceId
     * @param taskDefinitionKey
     * @return
     */
    @Override
    public ProTaskInstance getCurrentProTask(String processInstanceId,
            String currentTaskDefKey) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new NullArgException(
                    "ProcessInstanceServic.complete processInstanceId is empty.");
        }
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId, currentTaskDefKey);
        ProTaskInstance res = new ActivitiProTaskInstance(task);
        return res;
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
    @SuppressWarnings("unused")
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
      * @param currentTaskDefKey
      * @return [参数说明]
      * 
      * @return ExecutionEntity [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unused")
    private ExecutionEntity getExecutionEntityByProInsIdAndTaskDefKey(
            String processInstanceId, String currentTaskDefKey) {
        Task task = getTaskByProInsIdAndTaskDefKey(processInstanceId,
                currentTaskDefKey);
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
    private ExecutionEntity getExecutionByExecutionId(String executionId) {
        ExecutionQuery exeQuery = this.runtimeService.createExecutionQuery()
                .executionId(executionId);
        return (ExecutionEntity) exeQuery.singleResult();
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
        proInsLockIndex = Math.abs(proInsLockIndex);
        return processInsLocks[proInsLockIndex];
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
    private Object getLockByProDefId(String proDefId,
            String currentTaskDefinitionKey) {
        int proDefIdHashCode = proDefId.hashCode()
                + currentTaskDefinitionKey.hashCode();
        int proDefLockIndex = proDefIdHashCode
                % (processDefLockNum <= 0 ? 256 : processDefLockNum);
        proDefLockIndex = Math.abs(proDefLockIndex);
        return processDefLocks[proDefLockIndex];
    }
}
