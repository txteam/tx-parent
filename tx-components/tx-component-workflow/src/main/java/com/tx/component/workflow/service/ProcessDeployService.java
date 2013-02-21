/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-16
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.io.InputStream;
import java.util.Date;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.drools.core.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.workflow.WorkFlowConstants;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * 流程部署业务层<br/>
 *     1、主要用于启东时，或启动期间，加载新流程所用
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("processDeployService")
public class ProcessDeployService implements InitializingBean {
    
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;
    
    private RepositoryService repositoryService;
    
    @Resource(name = "processDefinitionService")
    private ProcessDefinitionService processDefinitionService;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.repositoryService = this.processEngine.getRepositoryService();
    }
    
    /**
      * 更具指定名，以及输入流部署对应的流程
      * @param key
      * @param inputStream [参数说明]
      * @param serviceType [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void deploy(String resourceName, InputStream inputStream,
            String serviceType) {
        //验证参数合法性
        if (StringUtils.isEmpty(resourceName) || inputStream == null) {
            throw new ParameterIsEmptyException(
                    "ProcessDeployService.deploy resourceName or inputStream is empty");
        }
        
        //部署流程
        DeploymentEntity deployment = deployToActiviti(resourceName,
                inputStream);
        
        //获取部署的流程定义
        String deploymentId = deployment.getId();
        ProcessDefinition processDef = getLastVersionProcessDefinition(deploymentId,
                resourceName);
        
        //持久化到流程定义中
        deployToLocal(processDef, serviceType);
    }
    
    /**
      * 部署到本地流程引擎中
      * <功能详细描述>
      * @param processDef
      * @param serviceType
      * @return [参数说明]
      * 
      * @return com.tx.component.workflow.model.ProcessDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private com.tx.component.workflow.model.ProcessDefinition deployToLocal(
            ProcessDefinition processDef, String serviceType) {
        com.tx.component.workflow.model.ProcessDefinition processDefinition = new com.tx.component.workflow.model.ProcessDefinition();
        
        processDefinition.setCategory(processDef.getCategory());
        processDefinition.setWfdId(processDef.getId());
        processDefinition.setKey(processDef.getKey());
        processDefinition.setVersion(String.valueOf(processDef.getVersion()));
        processDefinition.setName(processDef.getName());
        
        processDefinition.setCreateDate(new Date());
        processDefinition.setLastUpdateDate(new Date());
        
        processDefinition.setServiceType(serviceType);
        processDefinition.setState(WorkFlowConstants.PROCESS_DEFINITION_STATE_CONFIG);
        
        this.processDefinitionService.insertProcessDefinition(processDefinition);
        return null;
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
    private DeploymentEntity deployToActiviti(String resourceName,
            InputStream inputStream) {
        DeploymentEntity deployment = (DeploymentEntity) this.repositoryService.createDeployment()
                .addInputStream(resourceName, inputStream)
                .deploy();
        return deployment;
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
    private ProcessDefinition getLastVersionProcessDefinition(
            String deploymentId, String resourceName) {
        ProcessDefinitionQuery pdQuery = this.repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .processDefinitionResourceName(resourceName);
        
        ProcessDefinition processDef = pdQuery.latestVersion().singleResult();
        
        return processDef;
    }
    
}
