/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-17
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.util.List;
import java.util.Map;

import com.tx.component.workflow.model.ProTaskInstance;
import com.tx.component.workflow.model.ProcessInstance;

/**
 * 工作流实例业务层逻辑<br/>
 *     注：process,reject,cancel如果和compelet混用会发生线程安全问题
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
    ProcessInstance start(String processDefinitionKey);
    
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
    ProcessInstance startByDefId(String processDefinitionId);
    
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
    ProcessInstance start(String processDefinitionKey,
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
    ProcessInstance startByDefId(String processDefinitionId,
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
    void setVariables(String executionId, Map<String, Object> variables);
    
    /**
     * 设置流程过程对象变量
     * @param executionId
     * @param variableName
     * @param value
     */
    void setVariable(String executionId, String variableName, String value);
    
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
    void setVariableLocal(String executionId, String variableName, String value);
    
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
    void setVariablesLocal(String executionId, Map<String, Object> variables);
    
    /**
      * 获取流程环节实例变量集合
      *<功能简述>
      *<功能详细描述>
      * @param executionId
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getVariables(String executionId);
    
    /**
      * 获取持久的流程变量
      *<功能简述>
      *<功能详细描述>
      * @param executionId
      * @param variableName
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Object getVariableLocal(String executionId, String variableName);
    
    /**
      * 获取流程环节实例变量集合
      *<功能简述>
      *<功能详细描述>
      * @param executionId
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getVariablesLocal(String executionId);
    
    /**
      * 获流程变量
      *<功能详细描述>
      * @param executionId
      * @param variableName
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Object getVariable(String executionId, String variableName);
    
    /**
      * 为流程任务实例设置变量
      *     1、如果对应流程实例，当前存在多个流程环节不能调用该方法
      *     2、在该方法实现中事实上市通过流程实例id去获取当前的流程环节，如果查询出有1个以上的流程实例存在，将会抛出异常
      * <功能详细描述>
      * @param processInsId
      * @param variableName
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setTaskVariable(String processInsId, String variableName, Object value);
    
    /**
      * 为流程任务实例设置变量
      *<功能详细描述>
      * @param processInsId
      * @param currentTaskDefKey
      * @param variableName
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setTaskVariable(String processInsId, String currentTaskDefKey,
            String variableName, Object value);
    
    /**
      * 为流程任务实例设置变量集合
      *     1、如果对应流程实例，当前存在多个流程环节不能调用该方法
      *     2、在该方法实现中事实上市通过流程实例id去获取当前的流程环节，如果查询出有1个以上的流程实例存在，将会抛出异常
      *<功能详细描述>
      * @param processInstanceId
      * @param variables [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setTaskVariables(String processInstanceId,
            Map<String, Object> variables);
    
    /**
      * 为流程任务实例设置变量集合
      * <功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param variables [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setTaskVariables(String processInstanceId, String variableName,
            Map<String, Object> variables);
    
    /**
      * 查询流程任务环节变量
      *     1、如果对应流程实例，当前存在多个流程环节不能调用该方法
      *     2、在该方法实现中事实上市通过流程实例id去获取当前的流程环节，如果查询出有1个以上的流程实例存在，将会抛出异常
      * @param processInstanceId
      * @param variableName
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Object getTaskVarible(String processInstanceId, String variableName);
    
    /**
      * 获流程任务环节变量
      * <功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param variableName
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Object getTaskVarible(String processInstanceId, String currentTaskDefKey,
            String variableName);
    
    /**
      * 获流程任务环节变量集合
      *     1、如果对应流程实例，当前存在多个流程环节不能调用该方法
      *     2、在该方法实现中事实上市通过流程实例id去获取当前的流程环节，如果查询出有1个以上的流程实例存在，将会抛出异常
      * @param processInstanceId
      * @param variableName
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getTaskVaribles(String processInstanceId);
    
    /**
      * 获流程任务环节变量集合
      *<功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param variableName
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getTaskVaribles(String processInstanceId,
            String currentTaskDefKey);
    
    /**
     * 为流程任务实例设置变量
     *     1、如果对应流程实例，当前存在多个流程环节不能调用该方法
     *     2、在该方法实现中事实上市通过流程实例id去获取当前的流程环节，如果查询出有1个以上的流程实例存在，将会抛出异常
     * <功能详细描述>
     * @param processInsId
     * @param variableName
     * @param value [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    void setTaskVariableLocal(String processInsId, String variableName,
            Object value);
    
    /**
      * 为流程任务实例设置变量
      *<功能详细描述>
      * @param processInsId
      * @param currentTaskDefKey
      * @param variableName
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setTaskVariableLocal(String processInsId, String currentTaskDefKey,
            String variableName, Object value);
    
    /**
      * 为流程任务实例设置变量集合
      *     1、如果对应流程实例，当前存在多个流程环节不能调用该方法
      *     2、在该方法实现中事实上市通过流程实例id去获取当前的流程环节，如果查询出有1个以上的流程实例存在，将会抛出异常
      *<功能详细描述>
      * @param processInstanceId
      * @param variables [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setTaskVariablesLocal(String processInstanceId,
            Map<String, Object> variables);
    
    /**
      * 为流程任务实例设置变量集合
      * <功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param variables [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setTaskVariablesLocal(String processInstanceId,
            String currentTaskDefKey, Map<String, Object> variables);
    
    /**
      * 查询流程任务环节变量
      *     1、如果对应流程实例，当前存在多个流程环节不能调用该方法
      *     2、在该方法实现中事实上市通过流程实例id去获取当前的流程环节，如果查询出有1个以上的流程实例存在，将会抛出异常
      * @param processInstanceId
      * @param variableName
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Object getTaskVaribleLocal(String processInstanceId, String variableName);
    
    /**
      * 获流程任务环节变量
      * <功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param variableName
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Object getTaskVaribleLocal(String processInstanceId,
            String currentTaskDefKey, String variableName);
    
    /**
      * 获流程任务环节变量集合
      *     1、如果对应流程实例，当前存在多个流程环节不能调用该方法
      *     2、在该方法实现中事实上市通过流程实例id去获取当前的流程环节，如果查询出有1个以上的流程实例存在，将会抛出异常
      * @param processInstanceId
      * @param variableName
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getTaskVariblesLocal(String processInstanceId);
    
    /**
      * 获流程任务环节变量集合
      *<功能详细描述>
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param variableName
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getTaskVariblesLocal(String processInstanceId,
            String currentTaskDefKey);
    
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
    
    void complete(String processInstanceId, Map<String, Object> taskVaribals);
    
    /**
      * 完成当前流程环节任务，使其流入下一个流程环节任务<br/>
      *     1、如果当前流程实例存在多个在并行的任务将抛出异常<br/>
      *     2、
      * @param processInstanceId
      * @param currentTaskDefKey
      * @param taskVaribals [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    
    void complete(String processInstanceId, String currentTaskDefKey,
            Map<String, Object> taskVaribals);
    
//    /**
//     * 将流程流转入指定操作名的方向
//     *     1、如果当前流程存在并行流程，调用该方法可能会发生异常
//     * <功能详细描述>
//     * @param processInstanceId(流程实例id)
//     * @return [参数说明]
//     * 
//     * @return ProTransitionDefinition [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    void process(String processInstanceId, String transitionName,
//            Map<String, Object> taskVaribals);
//    
//    /**
//      * 将流程流转入指定操作id的方向
//      *     1、如果当前流程存在并行流程，调用该方法可能会发生异常
//      *<功能详细描述>
//      * @param processInstanceId
//      * @param transitionId
//      * @param taskVaribals [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    void processById(String processInstanceId, String transitionId,
//            Map<String, Object> taskVaribals);
//    
//    /**
//      * 将流程流转入指定操作名的方向
//      *     1、如果当前流程存在并行流程，调用该方法可能会发生异常
//      * <功能详细描述>
//      * @param processInstanceId
//      * @param currentTaskDefKey
//      * @param transitionName 
//      * @return [参数说明]
//      * 
//      * @return String [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    void process(String processInstanceId, String currentTaskDefKey,
//            String transitionName, Map<String, Object> taskVaribals);
//    
//    /**
//     * 将流程流转入指定操作名的方向
//     *     1、如果当前流程存在并行流程，调用该方法可能会发生异常
//     * <功能详细描述>
//     * @param processInstanceId
//     * @param currentTaskDefKey
//     * @param transitionId 
//     * @return [参数说明]
//     * 
//     * @return String [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    void processById(String processInstanceId, String currentTaskDefKey,
//            String transitionId, Map<String, Object> taskVaribals);
    
    //TODO: 支持process By transition And taskDefKey 因当前节是可以知道下一个可触及的任务节点的流向的
    //TODO: 支持reject,cancel
    
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
    List<ProTaskInstance> getCurrentProTaskList(String processInstanceId);
    
    /**
      * 获取指定流程实例的当前流程环节<br/>
      *     1、如果当前存在并行流程环节该方法将会抛出异常<br/>
      * <功能详细描述>
      * @param processInstanceId
      * @return [参数说明]
      * 
      * @return ProTaskDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    ProTaskInstance getCurrentProTask(String processInstanceId);
    
    /**
      * 根据当前流程实例id,以及任务定义key获取当前的流程任务实例
      *<功能详细描述>
      * @param processInstanceId
      * @param taskDefinitionKey
      * @return [参数说明]
      * 
      * @return ProTaskInstance [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    ProTaskInstance getCurrentProTask(String processInstanceId,String currentTaskDefKey);
    
}
