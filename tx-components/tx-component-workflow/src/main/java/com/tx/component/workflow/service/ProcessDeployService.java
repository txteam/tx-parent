/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-16
 * <修改描述:>
 */
package com.tx.component.workflow.service;

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
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void deploy(String key, InputStream inputStream) {
        //验证参数合法性
        if (StringUtils.isEmpty(key) || inputStream == null) {
            throw new ParameterIsEmptyException(
                    "ProcessDeployService.deploy key or inputStream is empty");
        }
        
        //部署流程
        DeploymentEntity deployment = (DeploymentEntity) this.repositoryService.createDeployment()
                .addInputStream(key, inputStream)
                .deploy();
        deployment.getCategory();
        String deploymentId = deployment.getId();
        
        //获取部署的流程定义
        ProcessDefinitionQuery pdQuery = this.repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .processDefinitionName(key)
                .latestVersion();
        ProcessDefinition processDef = pdQuery.singleResult();
        
        //processDef.get
    }
    
}
