/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-16
 * <修改描述:>
 */
package com.tx.component.workflow.service.impl;

import java.io.InputStream;

import javax.annotation.Resource;

import org.activiti.engine.repository.ProcessDefinition;
import org.drools.core.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.workflow.activiti5.ActivitiProcessDefinitionSupport;
import com.tx.component.workflow.model.ProcessDiagramResource;
import com.tx.component.workflow.model.impl.ActivitiProcessDefinition;
import com.tx.component.workflow.service.ProcessDefinitionService;
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
@Component("processDefinitionService")
public class ActivitiProcessDefinitionServiceImpl implements
        ProcessDefinitionService {
    
    @Resource(name = "activitiProcessDefinitionSupport")
    private ActivitiProcessDefinitionSupport activiti5ProcessDefSupport;
    
    /**
     * @param processDefinitionId
     * @return
     */
    @Override
    public ProcessDiagramResource getProcessDiagramResource(
            String processDefinitionId) {
        ProcessDiagramResource res = activiti5ProcessDefSupport.getProcessDiagramResource(processDefinitionId);
        
        return res;
    }
    
    /**
     * 根据指定名，以及资源名,以及输入流部署对应的流程
     * @param deployName
     * @param resourceName
     * @param inputStream
     * @return
     */
    @Transactional
    public com.tx.component.workflow.model.ProcessDefinition deploy(
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
        
        //部署流程并获取部署成功的流程定义
        ProcessDefinition processDef = activiti5ProcessDefSupport.deployToActiviti(deployName,
                resourceName,
                inputStream);
        
        //持久化到流程定义中
        return new ActivitiProcessDefinition(processDef);
    }
    
    /**
     * @param processDefinitionId
     * @return
     */
    @Override
    public com.tx.component.workflow.model.ProcessDefinition getProcessDefinitionById(
            String processDefinitionId) {
        ProcessDefinition processDef = activiti5ProcessDefSupport.getProcessDefinitionById(processDefinitionId);
        return new ActivitiProcessDefinition(processDef);
    }
    
}
