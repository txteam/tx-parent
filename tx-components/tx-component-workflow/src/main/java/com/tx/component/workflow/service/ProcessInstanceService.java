/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-17
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.task.TaskDefinition;

import com.tx.component.workflow.model.ProcessTransitionDefinition;

/**
 * 工作流实例业务层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ProcessInstanceService {
    
    /**
      * 开始一条流程实例<br/>
      *     根据流程的最新版本创建一个流程实例<br/>
      * @param processKey
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String start(String processKey);
    
    /**
      * 开始一条流程实例<br/>
      *     根据流程的最新版本创建一个流程实例<br/>
      *     一并传入流程变量<br/>
      * @param processKey
      * @param varibalMap
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String start(String processKey, Map<String, String> varibalMap);
    
    /**
      * 开始一条流程实例<br/>
      *     根据流程的最新版本创建一个流程实例<br/>
      *     指定流程实例id<br/>
      * @param processKey
      * @param processInstanceId
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String start(String processKey, String processInstanceId);
    
    /**
      * 开始一条流程实例<br/>
      *     根据流程的最新版本创建一个流程实例<br/>
      *     一并传入流程变量<br/>
      *     指定流程实例id<br/>
      * @param processKey
      * @param processInstanceId
      * @param varibalMap
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String start(String processKey, String processInstanceId,
            Map<String, String> varibalMap);
    
    /**
      * 将变量放入流程环节变量中<br/>
      *     
      * @param processInsId
      * @param varibals [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void putProcessInsVaribals(String processInsId,
            Map<String, Object> varibals);
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param processInsId
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void putProcessInsVaribal(String processInsId, String key,
            String value);
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param processInsId
      * @return [参数说明]
      * 
      * @return Map<String,String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, String> getProcessVaribals(String processInsId);
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param processInsId
      * @param key
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getProcessInsVaribal(String processInsId, String key);
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param processInsId
      * @return [参数说明]
      * 
      * @return List<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<TaskDefinition> getCurrentTasks(String processInsId);
    
    /**
      * 获取当前流程实例的当前节点对应的节点定义<br/>
      *     如果在并行流程中，如果存在多个当前节点，调用该方法将抛出异常<br/>
      *<功能详细描述>
      * @param processInsId
      * @return [参数说明]
      * 
      * @return TaskDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public TaskDefinition getCurrentTask(String processInsId);
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param processInsId
      * @return [参数说明]
      * 
      * @return List<ProcessTransitionDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ProcessTransitionDefinition> getCurrentTaskAllTransition(
            String processInsId);
    
    public String process();
}
