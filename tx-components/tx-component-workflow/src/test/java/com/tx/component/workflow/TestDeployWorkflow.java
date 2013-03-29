/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-18
 * <修改描述:>
 */
package com.tx.component.workflow;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试流程部署
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:spring/beans-wf.xml" })
//@ActiveProfiles("dev")
@ActiveProfiles("production")
public class TestDeployWorkflow extends TestWFBase {
    
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;
    
    @Test
    public void test() {
        
        //需要以非“/”开始
//        processEngine.getRepositoryService()
//                .createDeployment()
//                .name("test1")
//                .addClasspathResource("workflow/test1/process.bpmn")
//                .deploy();
        
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("test1");
        
        
        Assert.assertTrue(pi != null);
    }
}
