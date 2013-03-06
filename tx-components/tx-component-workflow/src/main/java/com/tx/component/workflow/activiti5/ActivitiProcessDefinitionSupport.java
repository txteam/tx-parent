/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-5
 * <修改描述:>
 */
package com.tx.component.workflow.activiti5;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;

import com.tx.component.workflow.model.ProcessDiagramResource;

/**
 * Activiti调用辅助类<br/>
 *     通过该类实现对activiti调用时的缓存<br/>
 *     
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ActivitiProcessDefinitionSupport {
    
    /**
     * 部署到activiti中<br/>
     * <功能详细描述>
     * @param resourceName
     * @param inputStream
     * @return [参数说明]
     * 
     * @return DeploymentEntity [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    ProcessDefinition deployToActiviti(String deployName,
            String resourceName, InputStream inputStream);
    
    /**
      * 根据流程定义id获取流程定义实例<br/> 
      *<功能详细描述>
      * @param processDefId
      * @return [参数说明]
      * 
      * @return ProcessDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    ProcessDefinition getProcessDefinitionById(String processDefId);
    
    /**
      * 根据流程定义key获取最新版本的流程定义实例<br/>
      * <功能详细描述>
      * @param processDefKey
      * @return [参数说明]
      * 
      * @return ProcessDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    ProcessDefinition getLastVersionProcessDefinitionByKey(
            String processDefKey);
    
    /**
      * 获取指定流程定义中，指定activityId对应的节点activity对象
      *<功能详细描述>
      * @param processDefinitionId
      * @param activityId
      * @return [参数说明]
      * 
      * @return ActivityImpl [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    ActivityImpl getActivityById(String processDefinitionId,
            String activityId);
    
    /**
      * 根据流程实例id获取流程图资源定义类
      * <功能详细描述>
      * @param processDefinitionId
      * @return [参数说明]
      * 
      * @return ProcessDiagramResource [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    ProcessDiagramResource getProcessDiagramResource(String processDefinitionId);
    
    /**
      * 获取任务定义映射列表
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,TaskDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Map<String, TaskDefinition> getTaskDefinitions(String processDefinitionId);
    
    /**
      * 根据接口类型获得匹配的业务定义实例
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ActivityImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    List<ActivityImpl> getServiceTaskDefinitionsByType(String processDefinitionId);
}
