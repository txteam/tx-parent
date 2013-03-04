/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-16
 * <修改描述:>
 */
package com.tx.component.workflow.service.impl;

import java.io.InputStream;

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

import com.tx.component.workflow.model.impl.ActivitiProcessDefinition;
import com.tx.component.workflow.service.ProcessDefinitionService;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;
import com.tx.core.exceptions.parameter.ParameterIsInvalidException;

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
@Component("processDefinitionService")
public class ActivitiProcessDefinitionServiceImpl implements InitializingBean,
        ProcessDefinitionService {
    
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;
    
    private RepositoryService repositoryService;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.repositoryService = this.processEngine.getRepositoryService();
    }
    
    /**
     * 根据指定名，以及资源名,以及输入流部署对应的流程
     * @param deployName
     * @param resourceName
     * @param inputStream
     * @return
     */
    @Transactional
    public com.tx.component.workflow.model.ProcessDef deploy(
            String deployName, String resourceName, InputStream inputStream) {
        //验证参数合法性
        if (StringUtils.isEmpty(resourceName) || inputStream == null) {
            throw new ParameterIsEmptyException(
                    "ProcessDeployService.deploy resourceName or inputStream is empty");
        }
        if (!resourceName.endsWith(".bpmn")) {
            resourceName = resourceName + ".bpmn";
        }
        if (StringUtils.isEmpty(deployName)) {
            deployName = resourceName;
        }
        
        //部署流程
        DeploymentEntity deployment = deployToActiviti(deployName,
                resourceName,
                inputStream);
        
        //获取部署的流程定义
        String deploymentId = deployment.getId();
        ProcessDefinition processDef = getProcessDefinitionByDeployId(deploymentId);
        
        //持久化到流程定义中
        return new ActivitiProcessDefinition(processDef);
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
    private DeploymentEntity deployToActiviti(String deployName,
            String resourceName, InputStream inputStream) {
        DeploymentEntity deployment = (DeploymentEntity) this.repositoryService.createDeployment()
                .name(deployName)
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
    private ProcessDefinition getProcessDefinitionByDeployId(String deploymentId) {
        ProcessDefinitionQuery pdQuery = this.repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId);
        
        ProcessDefinition processDef = pdQuery.singleResult();
        
        return processDef;
    }
}
