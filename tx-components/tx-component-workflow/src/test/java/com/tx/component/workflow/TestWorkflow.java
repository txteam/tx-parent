/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-18
 * <修改描述:>
 */
package com.tx.component.workflow;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskDelegateExpressionActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.workflow.service.ProcessDefinitionService;
import com.tx.component.workflow.service.ProcessInstanceService;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml", "classpath:spring/beans.xml",
        "classpath:spring/beans-wf.xml" })
//@ActiveProfiles("dev")
@ActiveProfiles("production")
public class TestWorkflow extends TestWFBase implements InitializingBean {
    
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;
    
    private RuntimeService runtimeService;
    
    private TaskService taskService;
    
    private RepositoryService repositoryService;
    
    @Resource(name = "processInstanceService")
    private ProcessInstanceService processInstanceService;
    
    @Resource(name = "processDefinitionService")
    private ProcessDefinitionService processDefinitionService;
    
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskService = processEngine.getTaskService();
        this.repositoryService = processEngine.getRepositoryService();
        this.runtimeService = processEngine.getRuntimeService();
        
    }
    
    private String processDefId;
    
    private String processDefKey = "test";
    
    private String processInsId = "5901";
    
    private ProcessInstance processInstance;
    
    private ProcessDefinition processDefinition;
    
    //@Test
    public void testDeploy() throws Exception {
        
        //需要以非“/”开始
        //        processEngine.getRepositoryService()
        //                .createDeployment()
        //                .name("test2")
        //                .addClasspathResource("workflow/test1/process.bpmn")
        //                .deploy();
        
        org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:workflow/test1/process.bpmn");
        
        try {
            com.tx.component.workflow.model.ProcessDefinition t = processDefinitionService.deploy("test1",
                    "process/test",
                    resource.getInputStream());
            System.out.println("......................");
            System.out.println("id:" + t.getId());
            System.out.println("key:" + t.getKey());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testNow() {
        String proDefId = "test:19:7504";
        
        processInsId = null;
        //com.tx.component.workflow.model.ProcessInstance proIns = processInstanceService.startByDefId(proDefId);
        //processInsId = proIns.getId();
        System.out.println(processInsId);
        
        //根据流程实例处理
        processInsId = processInsId == null ? "7601" : processInsId;
        //5901
        
        //this.processInstanceService.process(processInsId, "通过",null);
        
        //5701
        //this.processInstance = 
        Task currentTask = this.taskService.createTaskQuery()
                .processInstanceId(processInsId)
                .singleResult();
        System.out.println(currentTask.getName());
        TaskDefinition currentTaskDefinition = null;
        
        this.processDefinition = processEngine.getRepositoryService()
                .getProcessDefinition(currentTask.getProcessDefinitionId());
        
        ProcessDefinitionEntity pd = (ProcessDefinitionEntity) this.processDefinition;
        pd.getTaskDefinitions();
        
        for (Entry<String, TaskDefinition> ttTemp : pd.getTaskDefinitions()
                .entrySet()) {
            if (ttTemp.getValue()
                    .getKey()
                    .equals(currentTask.getTaskDefinitionKey())) {
                currentTaskDefinition = ttTemp.getValue();
            }
        }
        System.out.println("currentTask:" + currentTask.getTaskDefinitionKey());
        
        List<ActivityImpl> tt = pd.getActivities();
        
        Map<String, TaskDefinition> taskMap = pd.getTaskDefinitions();
        //pd.getActivities();
        System.out.println("task..............");
        for(Entry<String, TaskDefinition> entryTemp : taskMap.entrySet()){
            System.out.println(entryTemp.getKey() + " : " + entryTemp);
        }
        
        for (ActivityImpl te : tt) {
            System.out.println("");
            System.out.println("activityImpl:");
            System.out.println(te.getId());
            System.out.println(te.getParent());
            System.out.println(te.getActivityBehavior());
            
            System.out.println("-------------incoming----------------");
            for (PvmTransition ttte : te.getIncomingTransitions()) {
                System.out.println(ttte.getId() + " : "
                        + ttte.getProperty("name") + " : "
                        + ttte.getSource().getId() + " : "
                        + ttte.getDestination().getId());
            }
            
            System.out.println("-------------outgoing----------------");
            for (PvmTransition ttte : te.getOutgoingTransitions()) {
                System.out.println(ttte.getId() + " : "
                        + ttte.getProperty("name") + " : "
                        + ttte.getSource().getId() + " : "
                        + ttte.getDestination().getId());
            }
            
        }
        
    }
    
    //@Test
    public void testStartProcessInstanceByKey() {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("_processTypeId", "create");
        this.processInstance = this.runtimeService.startProcessInstanceByKey(this.processDefKey,
                para);
        System.out.println(processInstance.getId());
        //3101
        //this.runtimeService.start
    }
    
    //@Test
    public void test1111() {
        //this.processInstanceService.process(this.processInsId, "submit");
    }
    
    //@Test
    public void testGetProcessAllTask() {
        //        ProcessDefinition pdTemp = processEngine.getRepositoryService()
        //                .createProcessDefinitionQuery()
        //                .processDefinitionKey(this.processDefKey)
        //                .latestVersion()
        //                .singleResult();
        //        
        //        //this.processDefinition = pdTemp;
        //        System.out.println(pdTemp.getId());
        System.out.println("start..........................");
        this.processDefinition = processEngine.getRepositoryService()
                .getProcessDefinition("test:13:5504");
        this.processDefinition = processEngine.getRepositoryService()
                .getProcessDefinition("test:13:5504");
        this.processDefinition = processEngine.getRepositoryService()
                .getProcessDefinition("test:13:5504");
        this.processDefinition = processEngine.getRepositoryService()
                .getProcessDefinition("test:13:5504");
        this.processDefinition = processEngine.getRepositoryService()
                .getProcessDefinition("test:13:5504");
        System.out.println("end 测试缓存完毕..........................");
        
        ProcessDefinitionEntity pde = (ProcessDefinitionEntity) this.processDefinition;
        
        Map<String, TaskDefinition> tdm = pde.getTaskDefinitions();
        
        System.out.println("taskEntry:");
        for (Entry<String, TaskDefinition> taskEntry : tdm.entrySet()) {
            System.out.println(taskEntry.getKey() + " : "
                    + taskEntry.getValue());
        }
        
        System.out.println("");
        System.out.println("getActivities:");
        List<ActivityImpl> tt = pde.getActivities();
        for (ActivityImpl ac : tt) {
            System.out.println(ac.getId() + " : " + ac.getActivityBehavior());
            //ac.getActivityBehavior().
            
            ActivityBehavior ab = ac.getActivityBehavior();
            if (ab instanceof ServiceTaskDelegateExpressionActivityBehavior) {
                ServiceTaskDelegateExpressionActivityBehavior sab = (ServiceTaskDelegateExpressionActivityBehavior) ab;
                
                MetaObject mo = MetaObject.forObject(sab);
                
                System.out.println(mo.getValue("expression"));
            }
        }
        
        //pde.get
        //未产生分支的情况
        Task task = getTaskByProInsId(this.processInsId);
        System.out.println(task.getExecutionId());
        ExecutionEntity ec = getExecutionEntityByProInsId(getTaskByProInsId(this.processInsId).getExecutionId());
        //System.out.println(ec.getTransition());
        //System.out.println(ec.getTransitionBeingTaken());
        //System.out.println(ec.getTasks().size());
        //System.out.println(ec.getActivityId());
        //System.out.println(ec.getActivity() == null);
        
        //this.processDefinition = processEngine.getRepositoryService().getProcessDefinition(task.getProcessDefinitionId());
        //this.processDefinition.get
        
        //        for(PvmTransition tr : getExecutionEntityByProInsId(this.processInsId).getActivity().getOutgoingTransitions()){
        //            System.out.println(tr.getId());
        //            System.out.println(tr.getProperty("Name"));
        //        }
        
        //System.out.println(getExecutionEntityByProInsId(this.processInsId).get);
    }
    
    //@Test
    public void testCurrentProcessInsTaskAndToNext() {
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .processInstanceId(this.processInsId)
                .list();
        
        for (Task taskTemp : taskList) {
            System.out.println(taskTemp.getName());
            System.out.println(taskTemp.getTaskDefinitionKey());
        }
        
        //Task currentTask = processEngine.getTaskService().createTaskQuery().processInstanceId(this.processInsId).singleResult();
        
        //this.taskService.
        //this.taskService.complete(currentTask.getId());
        
        List<Task> taskList1 = processEngine.getTaskService()
                .createTaskQuery()
                .processInstanceId(this.processInsId)
                .list();
        
        for (Task taskTemp : taskList1) {
            System.out.println(taskTemp.getName());
            System.out.println(taskTemp.getTaskDefinitionKey());
        }
    }
    
    //@Test
    public void test1() {
        String pInsId = "401";//pi.getId();
        
        ProcessInstance pIns = runtimeService.createProcessInstanceQuery()
                .processInstanceId(pInsId)
                .singleResult();
        
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(pInsId)
                .list();
        
    }
    
    //@Test
    public void test() {
        
        //需要以非“/”开始
        //        processEngine.getRepositoryService()
        //                .createDeployment()
        //                .name("test1")
        //                .addClasspathResource("workflow/test1/process.bpmn")
        //                .deploy();
        
        //启动流程
        //ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("test1");
        //根据事件启动
        //...
        //空启动
        //...
        
        String pInsId = "701";//pi.getId();
        System.out.println(pInsId);
        
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .processInstanceId(pInsId)
                .list();
        
        for (Task taskTemp : taskList) {
            System.out.println(taskTemp.getName());
            System.out.println(taskTemp.getTaskDefinitionKey());
        }
        
        //查询已经生成的对应
        List<Task> taskList1 = processEngine.getTaskService()
                .createTaskQuery()
                .processDefinitionKey("test1")
                .list();
        
        for (Task taskTemp : taskList1) {
            System.out.println(taskTemp.getName());
            System.out.println(taskTemp.getTaskDefinitionKey());
        }
        
        //processEngine.getRuntimeService().createNativeProcessInstanceQuery().
        //processEngine.getRuntimeService().createExecutionQuery().processInstanceId(pInsId).list();
        //System.out.println(pd instanceof ProcessDefinitionEntity);
        //processEngine.getRepositoryService().createDeploymentQuery().deploymentId("test1").la
        
        ProcessDefinition pd2 = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionKey("test1")
                .latestVersion()
                .singleResult();
        ProcessDefinition pd1 = processEngine.getRepositoryService()
                .getProcessDefinition(pd2.getId());
        
        processEngine.getRepositoryService().getProcessDefinition(pd2.getId());
        processEngine.getRepositoryService().getProcessDefinition(pd2.getId());
        processEngine.getRepositoryService().getProcessDefinition(pd2.getId());
        processEngine.getRepositoryService().getProcessDefinition(pd2.getId());
        processEngine.getRepositoryService().getProcessDefinition(pd2.getId());
        processEngine.getRepositoryService().getProcessDefinition(pd2.getId());
        
        System.out.println(pd1 instanceof ProcessDefinitionEntity);
        ProcessDefinitionEntity pd = (ProcessDefinitionEntity) pd1;
        Map<String, TaskDefinition> tdm = pd.getTaskDefinitions();
        MapUtils.verbosePrint(System.out, ":", tdm);
        
        for (TaskDefinition tdTemp : tdm.values()) {
            
            System.out.println(tdTemp.getNameExpression());
            
            List<ActivityImpl> tt = pd.getActivities();
            
            for (ActivityImpl te : tt) {
                System.out.println(te.getParent());
                System.out.println(te.getActivityBehavior());
                
                List<PvmTransition> ttt = null;
                for (PvmTransition ttte : ttt) {
                    System.out.println(ttte.getId());
                }
                
                System.out.println("-------------incoming----------------");
                for (PvmTransition ttte : te.getIncomingTransitions()) {
                    System.out.println(ttte.getId());
                }
                
                System.out.println("-------------incoming----------------");
                for (PvmTransition ttte : te.getOutgoingTransitions()) {
                    System.out.println(ttte.getId());
                }
                
            }
            
        }
        
        processEngine.getRuntimeService().setVariable(pInsId,
                "testKey",
                "testValue");
        
        //processEngine.getRuntimeService().
        
        //processEngine.getTaskService().
        
        Assert.assertTrue(pInsId != null);
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
            String taskDefinitionKey) {
        TaskQuery taskQuery = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey(taskDefinitionKey);
        
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
    @SuppressWarnings("unused")
    private Task getTaskByProInsId(String processInstanceId) {
        TaskQuery taskQuery = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId);
        
        //如果流程流转为并行节点，则不适合调用该方法，调用该方法这里讲会抛出异常
        Task task = taskQuery.singleResult();
        return task;
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
    private ExecutionEntity getExecutionEntityByProInsId(
            String processInstanceId) {
        ExecutionQuery exeQuery = this.runtimeService.createExecutionQuery()
                .processInstanceId(processInstanceId);
        
        //如果赌赢流程id对应到了多个excution此处将会抛出异常
        Execution res = exeQuery.singleResult();
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
    
}
