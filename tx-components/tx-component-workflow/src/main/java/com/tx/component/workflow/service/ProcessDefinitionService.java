/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-3
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.tx.component.workflow.model.ProTaskDefinition;
import com.tx.component.workflow.model.ProTransitionDefinition;
import com.tx.component.workflow.model.ProcessDefinition;
import com.tx.component.workflow.model.ProcessDiagramResource;

/**
 * 流程定义相关业务逻辑层
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ProcessDefinitionService {
    
    /**
     * 根据指定名，以及输入流部署对应的流程
     * @param key
     * @param inputStream [参数说明]
     * @param serviceType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    ProcessDefinition deploy(String deployName, String resourceName,
            InputStream inputStream);
    
    /**
      * 根据流程定义id获取流程实例
      * <功能详细描述>
      * @param processDefinitionId
      * @return [参数说明]
      * 
      * @return ProcessDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    ProcessDefinition getProcessDefinitionById(String processDefinitionId);
    
    /**
      * 获取流程图资源
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
   ProTaskDefinition getProTaskDefinitionByKey(String processDefinitionId, String taskDefinitionKey);
   
   /**
     * 获取任务定义映射列表
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,TaskDefinition> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   Map<String, ProTaskDefinition> getProTaskDefinitions(String processDefinitionId);
   
   /**
     * 根据接口类型获得匹配的代理类
     *     1、是根据代理类的实际实现类进行获取
     *     暂支持：
     *         ServiceTaskDelegateExpressionActivityBehavior
     *         ClassDelegate
     *         两个类实现的提取
     *         如果提取的类实际不存在，则抛出异常
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<ActivityImpl> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   List<ProTaskDefinition> getProTaskDefinitionsByType(
           String processDefinitionId, Class<?> classType);
   
   /**
     * 获取当前流程定义对应节点的流出节点转向实例
     * <功能详细描述>
     * @param processDefinitionId
     * @param activityId
     * @return [参数说明]
     * 
     * @return List<ProTransitionDefinition> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   List<ProTransitionDefinition> getOutTransitionDefinitions(String processDefinitionId,String activityId);
   
   
}
