/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-5
 * <修改描述:>
 */
package com.tx.component.workflow.activiti5.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.tx.component.workflow.exceptions.WorkflowAccessException;
import com.tx.component.workflow.model.ProcessDiagramResource;

/**
 * activiti5流程辅助类接口实现<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("activitiProcessDefinitionSupport")
public class CacheActiviti5ProcessDefSupport extends
        DefaultActiviti5ProcessDefSupport implements InitializingBean {
    
    /**
     * 流程定义缓存
     */
    private Map<String, ProcessDefinitionEntity> processDefCache;
    
    /**
     * 流程环节缓存
     */
    private Map<String, Map<String, ActivityImpl>> activityImplCache;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        //缓存实例
        processDefCache = new ConcurrentHashMap<String, ProcessDefinitionEntity>();
        activityImplCache = new ConcurrentHashMap<String, Map<String, ActivityImpl>>();
    }
    
    /**
     * @param deployName
     * @param resourceName
     * @param inputStream
     * @return
     */
    @Override
    public ProcessDefinition deployToActiviti(String deployName,
            String resourceName, InputStream inputStream) {
        ProcessDefinition proDef = super.deployToActiviti(deployName,
                resourceName,
                inputStream);
        //通过非id获取的ProcessDefinition转型为ProcessDefinitionEntity后获取transitions或activiti时会出现问题
        //这里通过调用统一的byId进行缓存
        proDef = getProcessDefinitionById(proDef.getId());
        return proDef;
    }
    
    /**
     * @param processDefinitionId
     * @return
     */
    @Override
    public ProcessDefinition getProcessDefinitionById(String processDefinitionId) {
        //如果缓存中存在直接从缓存中进行获取
        ProcessDefinitionEntity proDef = null;
        if (this.processDefCache.containsKey(processDefinitionId)) {
            proDef = this.processDefCache.get(processDefinitionId);
        }
        if (proDef != null) {
            return proDef;
        }
        
        proDef = (ProcessDefinitionEntity) super.getProcessDefinitionById(processDefinitionId);
        if (proDef != null) {
            this.processDefCache.put(proDef.getId(), proDef);
            
            Map<String, ActivityImpl> actMap = activityImplCache.get(processDefinitionId);
            for (ActivityImpl activityTemp : proDef.getActivities()) {
                actMap.put(activityTemp.getId(), activityTemp);
            }
        }
        
        return proDef;
    }
    
    /**
     * @param processDefKey
     * @return
     */
    @Override
    public ProcessDefinition getLastVersionProcessDefinitionByKey(
            String processDefKey) {
        ProcessDefinition proDef = super.getLastVersionProcessDefinitionByKey(processDefKey);
        //通过非id获取的ProcessDefinition转型为ProcessDefinitionEntity后获取transitions或activiti时会出现问题
        //这里通过调用统一的byId进行缓存
        proDef = getProcessDefinitionById(proDef.getId());
        return proDef;
    }
    
    /**
     * @param processDefinitionId
     * @param activityId
     * @return
     */
    @Override
    public ActivityImpl getActivityById(String processDefinitionId,
            String activityId) {
        //如果存在缓存，直接提取缓存中的数据
        if (activityImplCache.containsKey(processDefinitionId)) {
            if (activityImplCache.get(processDefinitionId)
                    .containsKey(activityId)) {
                return activityImplCache.get(processDefinitionId)
                        .get(activityId);
            }
        } else {
            activityImplCache.put(processDefinitionId,
                    new HashMap<String, ActivityImpl>());
        }
        
        //获取流程定义
        ProcessDefinitionEntity proDef = (ProcessDefinitionEntity) getProcessDefinitionById(processDefinitionId);
        for (ActivityImpl activityTemp : proDef.getActivities()) {
            if (activityId.equals(activityTemp.getId())) {
                return activityTemp;
            }
        }
        
        throw new WorkflowAccessException("id为:{}流程对应的activityKey不存在",
                processDefinitionId);
    }
    
    /**
     * @param processDefinitionId
     * @return
     */
    @Override
    public ProcessDiagramResource getProcessDiagramResource(
            String processDefinitionId) {
        ProcessDefinition proDef = getProcessDefinitionById(processDefinitionId);
        String resourceName = proDef.getDiagramResourceName();
        String key = proDef.getKey();
        int version = proDef.getVersion();
        
        InputStream resourceAsStream = null;
        byte[] inputStreamBytes = null;
        try {
            resourceAsStream = repositoryService.getResourceAsStream(proDef.getDeploymentId(),
                    resourceName);
            inputStreamBytes = IOUtils.toByteArray(resourceAsStream);
        } catch (IOException e) {
            IOUtils.closeQuietly(resourceAsStream);
        }
        
        ProcessDiagramResource res = new ProcessDiagramResource();
        res.setInputStreamBytes(inputStreamBytes);
        res.setProcessDefinitionId(processDefinitionId);
        res.setProcessDefinitionKey(key);
        res.setResourceName(resourceName);
        res.setVersion(String.valueOf(version));
        
        return res;
    }
    
    /**
     * @return
     */
    @Override
    public Map<String, TaskDefinition> getTaskDefinitions(
            String processDefinitionId) {
        ProcessDefinitionEntity proDef = (ProcessDefinitionEntity) getProcessDefinitionById(processDefinitionId);
        return proDef.getTaskDefinitions();
    }
    
    /**
     * @return
     */
    @Override
    public List<ActivityImpl> getServiceTaskDefinitionsByType(
            String processDefinitionId) {
        ProcessDefinitionEntity proDef = (ProcessDefinitionEntity) getProcessDefinitionById(processDefinitionId);
        return proDef.getActivities();
    }
    
}
