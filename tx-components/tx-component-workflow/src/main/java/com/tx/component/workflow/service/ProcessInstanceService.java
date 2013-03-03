/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-17
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tx.component.workflow.model.ProTaskDefinition;
import com.tx.component.workflow.model.ProTransitionDefinition;

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
   public abstract String start(String processDefinitionKey);
   
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
   public abstract String startByDefId(String processDefinitionId);
   
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
   public abstract String start(String processDefinitionKey,
           Map<String, Object> variables);
   
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
   public abstract String startByDefId(String processDefinitionId,
           Map<String, Object> variables);
   
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
   public abstract void setVariables(String executionId,
           Map<String, Object> variables);
   
   /**
    * 设置流程过程对象变量
    * @param executionId
    * @param variableName
    * @param value
    */
   @Transactional
   public abstract void setVariable(String executionId, String variableName,
           String value);
   
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
   public abstract void setVariableLocal(String executionId,
           String variableName, String value);
   
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
   public abstract void setVariablesLocal(String executionId,
           Map<String, Object> variables);
   
   /**
    * 获取流程环节实例变量集合
    * @param executionId
    * @return
    */
   public abstract Map<String, Object> getVariables(String executionId);
   
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
   public abstract Map<String, Object> getVariables(String executionId,
           Collection<String> variableNames);
   
   /**
    * @param executionId
    * @param key
    * @return
    */
   public abstract Object getVariable(String executionId, String variableName);
   
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
   public abstract List<ProTaskDefinition> getCurrentProTaskList(
           String processInstanceId);
   
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
   public abstract ProTaskDefinition getCurrentProTask(String processInstanceId);
   
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
   public abstract void complete(String processInstanceId);
   
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
   public abstract void complete(String processInstanceId,
           Map<String, Object> taskVaribals);
   
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
   public abstract void complete(String processInstanceId,
           String currentTaskDefKey);
   
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
   public abstract void complete(String processInstanceId,
           String currentTaskDefKey, Map<String, Object> taskVaribals);
   
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
   public abstract void pass(String processInstanceId);
   
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
   public abstract void pass(String processInstanceId,
           String currentTaskDefKey);
   
   /**
    * @return
    */
   @Transactional
   public abstract void process(String processInstanceId,
           String transitionName);
   
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
   public abstract void process(String processInstanceId,
           String currentTaskDefKey, String transitionName);
   
   /**
    * @param processInsId
    * @return
    */
   public abstract List<ProTransitionDefinition> getCurrentTaskAllTransition(
           String processInsId);
   
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
   public abstract List<ProTaskDefinition> getCurrentTasks(String executionId);
   
   public abstract ProTaskDefinition getCurrentTask(String processInstanceId);
   
   public abstract List<ProTaskDefinition> getCurrentTaskList(
           String processInstanceId);

}
