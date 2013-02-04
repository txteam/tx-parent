/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-4
 * <修改描述:>
 */
package com.tx.component.workflow.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.drools.core.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.tx.component.workflow.WorkFlowConstants;
import com.tx.component.workflow.model.ProTransitionDefinition;

/**
 * 流程实例业务层实例<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("ProcessInstanceService")
public class ProcessInstanceServiceImpl implements InitializingBean {
    
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;
    
    private RuntimeService runtimeService;
    
    private TaskService taskService;
    
    private RepositoryService repositoryService;
    
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
      * 判断是否传入了有效的businessKey
      * <功能详细描述>
      * @param businessKey
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private boolean businessKeyIsValid(String[] businessKey) {
        if (ArrayUtils.isEmpty(businessKey)
                || StringUtils.isEmpty(businessKey[0])) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
      * 开始一条流程实例<br/>
      *     1、根据流程的最新版本创建一个流程实例<br/>
      *     2、可以传入一个操作<br/>
      * @param processDefinitionKey  流程定义key
      * @param processName 操作name对应流程图中的一个transition可以为空
      * @param variables 压入流程中当做流程变量的map可以为空
      * @param businessKey 用以支持区分流程实例类型的饿一个businessKey可以为空
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String start(String processDefinitionKey, String processName,
            Map<String, Object> variables, String... businessKey) {
        ProcessInstance pIns = null;
        if (!StringUtils.isEmpty(processName)) {
            if (variables == null) {
                variables = new HashMap<String, Object>();
            }
            variables.put(WorkFlowConstants.PROCESS_NAME, processName);
        }
        
        if (MapUtils.isEmpty(variables)) {
            if (businessKeyIsValid(businessKey)) {
                pIns = this.runtimeService.startProcessInstanceByKey(processDefinitionKey,
                        businessKey[0]);
            } else {
                pIns = this.runtimeService.startProcessInstanceByKey(processDefinitionKey);
            }
        } else {
            if (businessKeyIsValid(businessKey)) {
                pIns = this.runtimeService.startProcessInstanceByKey(processDefinitionKey,
                        businessKey[0],
                        variables);
            } else {
                pIns = this.runtimeService.startProcessInstanceByKey(processDefinitionKey,
                        variables);
            }
        }
        
        String pInsId = pIns.getProcessInstanceId();
        this.runtimeService.removeVariable(pInsId,
                WorkFlowConstants.PROCESS_NAME);
        return pInsId;
    }
    
    /**
     * @param processInsId
     * @param varibals
     */
    public void putProcessInsVaribals(String processInsId,
            Map<String, Object> varibals) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param processInsId
     * @param key
     * @param value
     */
    public void putProcessInsVaribal(String processInsId, String key,
            String value) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param processInsId
     * @return
     */
    public Map<String, String> getProcessVaribals(String processInsId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param processInsId
     * @param key
     * @return
     */
    public String getProcessInsVaribal(String processInsId, String key) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param processInsId
     * @return
     */
    public List<TaskDefinition> getCurrentTasks(String processInsId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param processInsId
     * @return
     */
    public TaskDefinition getCurrentTask(String processInsId) {
        // TODO Auto-generated method stub
        return null;
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
     * @return
     */
    public String process() {
        
        return null;
    }
    
}
