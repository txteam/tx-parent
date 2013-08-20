/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-5
 * <修改描述:>
 */
package com.tx.component.workflow.activiti5.impl;

import java.io.InputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.workflow.activiti5.ActivitiProcessDefinitionSupport;
import com.tx.component.workflow.exceptions.WorkflowAccessException;

/**
 * 默认的activiti流程调度辅助类
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class DefaultActiviti5ProcessDefSupport implements
        ActivitiProcessDefinitionSupport, InitializingBean {
    
    @Resource(name = "processEngine")
    protected ProcessEngine processEngine;
    
    protected RepositoryService repositoryService;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.repositoryService = this.processEngine.getRepositoryService();
    }
    
    /**
     * 部署到activiti中
     * <功能详细描述>
     * @param resourceName
     * @param inputStream
     * @return [参数说明]
     * 
     * @return DeploymentEntity [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public ProcessDefinition deployToActiviti(String deployName,
            String resourceName, InputStream inputStream) {
        DeploymentEntity deployment = (DeploymentEntity) this.repositoryService.createDeployment()
                .name(deployName)
                .addInputStream(resourceName, inputStream)
                .deploy();
        //获取部署的流程定义
        String deploymentId = deployment.getId();
        return getProcessDefIdByDeployId(deploymentId);
    }
    
    /**
      * 获取最新版本的最新部署流程定义
      * <功能详细描述>
      * @param deploymentId
      * @param resourceName
      * @return [参数说明]
      * 
      * @return ProcessDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private ProcessDefinition getProcessDefIdByDeployId(String deploymentId) {
        ProcessDefinitionQuery pdQuery = this.repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId);
        
        ProcessDefinition processDef = pdQuery.singleResult();
        return processDef;
    }
    
    /**
     * 获取当前流程activiti定义
     * @param processDefinitionId
     * @return [参数说明]
     * 
     * @return ProcessDefinitionEntity [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Override
    public ProcessDefinition getProcessDefinitionById(String processDefinitionId) {
        ProcessDefinitionEntity res = (ProcessDefinitionEntity) this.repositoryService.getProcessDefinition(processDefinitionId);
        if (res == null) {
            throw new WorkflowAccessException("id为:{}流程定义不存在",
                    new Object[]{processDefinitionId});
        }
        return res;
    }
    
    /**
     * @param processDefKey
     * @return
     */
    @Override
    public ProcessDefinition getLastVersionProcessDefinitionByKey(
            String processDefinitionKey) {
        ProcessDefinition res = this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();
        return res;
    }
}
