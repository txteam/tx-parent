/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-18
 * <修改描述:>
 */
package com.tx.component.workflow;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


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
        "classpath:spring/beans-tx.xml", "classpath:spring/beans-mybatis.xml",
        "classpath:spring/beans.xml", "classpath:spring/beans-wf.xml" })
public class TestWorkflow extends TestWFBase implements InitializingBean{
    
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
    
    private String processDefId;
    
    private String processDefKey = "testLoanBillProcess";
    
    private String processInsId = "1301";
    
    private ProcessInstance processInstance;
    
    private ProcessDefinition processDefinition;
    
    //@Test
    public void testDeploy() {
        
        //需要以非“/”开始
        processEngine.getRepositoryService()
                .createDeployment()
                .name("test1")
                .addClasspathResource("workflow/test1/process.bpmn")
                .deploy();
    }
    
    //@Test
    public void testStartProcessInstanceByKey(){
        this.processInstance = this.runtimeService.startProcessInstanceByKey(this.processDefKey);
        System.out.println(processInstance.getId());
    }
    
    //@Test
    public void testGetProcessAllTask(){
        ProcessDefinition pdTemp = processEngine.getRepositoryService().createProcessDefinitionQuery().
                processDefinitionKey(this.processDefKey).latestVersion().singleResult();
        
        this.processDefinition = processEngine.getRepositoryService().getProcessDefinition(pdTemp.getId());
        
        ProcessDefinitionEntity pde = (ProcessDefinitionEntity)this.processDefinition;
        
        Map<String, TaskDefinition> tdm = pde.getTaskDefinitions();
        
        for(Entry<String, TaskDefinition> taskEntry : tdm.entrySet()){
            System.out.println(taskEntry.getKey() + " : " + taskEntry.getValue());
        }
    }
  
    @Test
    public void testCurrentProcessInsTaskAndToNext(){
        List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(this.processInsId).list();
        
        for(Task taskTemp : taskList){
            System.out.println(taskTemp.getName());
            System.out.println(taskTemp.getTaskDefinitionKey());
        }
        
        Task currentTask = processEngine.getTaskService().createTaskQuery().processInstanceId(this.processInsId).singleResult();
        
        //this.taskService.
        this.taskService.complete(currentTask.getId());
        
        List<Task> taskList1 = processEngine.getTaskService().createTaskQuery().processInstanceId(this.processInsId).list();
        
        for(Task taskTemp : taskList1){
            System.out.println(taskTemp.getName());
            System.out.println(taskTemp.getTaskDefinitionKey());
        }
    }

    
    
    //@Test
    public void test1(){
        String pInsId = "401";//pi.getId();
        
        ProcessInstance pIns = runtimeService.createProcessInstanceQuery().processInstanceId(pInsId).singleResult();
    
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(pInsId).list();

        
        
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
        
        List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(pInsId).list();
        
        for(Task taskTemp : taskList){
            System.out.println(taskTemp.getName());
            System.out.println(taskTemp.getTaskDefinitionKey());
        }
        
        //查询已经生成的对应
        List<Task> taskList1 = processEngine.getTaskService().createTaskQuery().processDefinitionKey("test1").list();
        
        for(Task taskTemp : taskList1){
            System.out.println(taskTemp.getName());
            System.out.println(taskTemp.getTaskDefinitionKey());
        }
        
        //processEngine.getRuntimeService().createNativeProcessInstanceQuery().
        //processEngine.getRuntimeService().createExecutionQuery().processInstanceId(pInsId).list();
        //System.out.println(pd instanceof ProcessDefinitionEntity);
        //processEngine.getRepositoryService().createDeploymentQuery().deploymentId("test1").la
        
        ProcessDefinition pd2 = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey("test1").latestVersion().singleResult();
        ProcessDefinition pd1 = processEngine.getRepositoryService().getProcessDefinition(pd2.getId());
        System.out.println(pd1 instanceof ProcessDefinitionEntity);
        ProcessDefinitionEntity pd = (ProcessDefinitionEntity)pd1;
        Map<String, TaskDefinition> tdm = pd.getTaskDefinitions();
        MapUtils.verbosePrint(System.out, ":", tdm);
        
        for(TaskDefinition tdTemp : tdm.values()){
            
            System.out.println(tdTemp.getNameExpression());
            
            List<ActivityImpl> tt = pd.getActivities();
            
            for(ActivityImpl te : tt){
                System.out.println(te.getParent());
                System.out.println(te.getActivityBehavior());
                
                List<PvmTransition> ttt = null;
                for(PvmTransition ttte : ttt){
                    System.out.println(ttte.getId());
                }
                
                System.out.println("-------------incoming----------------");
                for(PvmTransition ttte : te.getIncomingTransitions()){
                    System.out.println(ttte.getId());
                }
                
                System.out.println("-------------incoming----------------");
                for(PvmTransition ttte : te.getOutgoingTransitions()){
                    System.out.println(ttte.getId());
                }
                
            }
            
        }
        
        processEngine.getRuntimeService().setVariable(pInsId, "testKey", "testValue");
        
        
        //processEngine.getRuntimeService().
        
        //processEngine.getTaskService().
        
        Assert.assertTrue(pInsId != null);
    }
    
 
    
}
