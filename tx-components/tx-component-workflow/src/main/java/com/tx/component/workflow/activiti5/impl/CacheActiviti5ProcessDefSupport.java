/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-5
 * <修改描述:>
 */
package com.tx.component.workflow.activiti5.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskDelegateExpressionActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.tx.component.workflow.exceptions.WorkflowAccessException;
import com.tx.component.workflow.model.ProcessDiagramResource;
import com.tx.core.exceptions.argument.NullArgException;

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
        DefaultActiviti5ProcessDefSupport implements InitializingBean,
        ApplicationContextAware {
    
    private Logger logger = LoggerFactory.getLogger(CacheActiviti5ProcessDefSupport.class);
    
    /**
     * 流程定义缓存
     */
    private Map<String, ProcessDefinitionEntity> processDefCache;
    
    /**
     * 流程环节缓存
     */
    private Map<String, Map<String, ActivityImpl>> activityImplCache;
    
    /** spring容器引用 */
    private ApplicationContext applicationContext;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
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
        if(StringUtils.isEmpty(resourceName) || inputStream  == null){
            throw new NullArgException("resourceName is empty or inputStream is null.");
        }
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
        if(StringUtils.isEmpty(processDefinitionId)){
            throw new NullArgException("processDefinitionId is empty.");
        }
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
            
            Map<String, ActivityImpl> actMap = new HashMap<String, ActivityImpl>();
            for(ActivityImpl actImplTemp : proDef.getActivities()){
                actMap.put(actImplTemp.getId(), actImplTemp);
            }
            activityImplCache.put(processDefinitionId, actMap);
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
        if(StringUtils.isEmpty(processDefKey)){
            throw new NullArgException("processDefinitionId is empty.");
        }
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
        if(StringUtils.isEmpty(processDefinitionId) ||
                StringUtils.isEmpty(activityId)){
            throw new NullArgException("processDefinitionId or activityId is empty.");
        }
        //如果存在缓存，直接提取缓存中的数据
        if (activityImplCache.containsKey(processDefinitionId)) {
            if (activityImplCache.get(processDefinitionId)
                    .containsKey(activityId)) {
                return activityImplCache.get(processDefinitionId)
                        .get(activityId);
            }
        }
        
        //获取流程定义
        ProcessDefinitionEntity proDef = (ProcessDefinitionEntity) getProcessDefinitionById(processDefinitionId);
        for (ActivityImpl activityTemp : proDef.getActivities()) {
            if (activityId.equals(activityTemp.getId())) {
                return activityTemp;
            }
        }
        
        throw new WorkflowAccessException("id为:{}流程对应的activityKey不存在",
                new Object[]{processDefinitionId});
    }
    
    /**
     * @param processDefinitionId
     * @return
     */
    @Override
    public ProcessDiagramResource getProcessDiagramResource(
            String processDefinitionId) {
        if(StringUtils.isEmpty(processDefinitionId)){
            throw new NullArgException("processDefinitionId is empty.");
        }
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
        if(StringUtils.isEmpty(processDefinitionId)){
            throw new NullArgException("processDefinitionId is empty.");
        }
        ProcessDefinitionEntity proDef = (ProcessDefinitionEntity) getProcessDefinitionById(processDefinitionId);
        return proDef.getTaskDefinitions();
    }
    
    /**
     * @return
     */
    @Override
    public List<ActivityImpl> getServiceTaskDefinitionsByType(
            String processDefinitionId, Class<?> classType) {
        if(StringUtils.isEmpty(processDefinitionId) ||
                classType == null){
            throw new NullArgException("processDefinitionId is empty. or classType is null");
        }
        ProcessDefinitionEntity proDef = (ProcessDefinitionEntity) getProcessDefinitionById(processDefinitionId);
        
        //结果列表
        List<ActivityImpl> resList = new ArrayList<ActivityImpl>();
        
        List<ActivityImpl> allActivityImpl = proDef.getActivities();
        for (ActivityImpl acitivityImplTemp : allActivityImpl) {
            ActivityBehavior aiAB = acitivityImplTemp.getActivityBehavior();
            //如果是ServiceTaskDelegateExpressionActivityBehavior的实现
            if (aiAB instanceof ServiceTaskDelegateExpressionActivityBehavior) {
                ServiceTaskDelegateExpressionActivityBehavior stTemp = (ServiceTaskDelegateExpressionActivityBehavior) aiAB;
                MetaObject mo = MetaObject.forObject(stTemp);
                Expression beanExpression = (Expression) mo.getValue("expression");
                
                Object beanImpl = this.applicationContext.getBean(beanExpression.getExpressionText());
                if (beanImpl == null) {
                    logger.warn("processDef:{} activityImpl:{} serviceClass:{} is not exist.",
                            new Object[]{processDefinitionId, acitivityImplTemp.getId(),
                            beanExpression});
                }
                if(beanImpl != null && classType.isInstance(beanImpl)){
                    //如果是classType的一个实现
                    resList.add(acitivityImplTemp);
                }
            }else if(aiAB instanceof ClassDelegate){
                ClassDelegate cdTemp = (ClassDelegate) aiAB;
                String className = cdTemp.getClassName();
                if(StringUtils.isEmpty(className)){
                    logger.warn("processDef:{} activityImpl:{} className:{} is empty.",
                            new Object[]{processDefinitionId, acitivityImplTemp.getId()});
                }else{
                    try {
                        @SuppressWarnings("rawtypes")
                        Class type = Class.forName(className);
                        if(classType.isAssignableFrom(type)){
                            //如果是classType的一个实现
                            resList.add(acitivityImplTemp);
                        }
                    } catch (ClassNotFoundException e) {
                        logger.warn("processDef:{} activityImpl:{} className:{} is not exist.",
                                new Object[]{processDefinitionId, acitivityImplTemp.getId(),
                                className});
                    }
                }

            }
        }
        
        return resList;
    }

    /**
     * @param processDefinitionId
     * @param activityId
     * @return
     */
    @Override
    public List<PvmTransition> getOutTransitions(String processDefinitionId,
            String activityId) {
        ActivityImpl activityImpl = getActivityById(processDefinitionId, activityId);
        return activityImpl.getOutgoingTransitions();
    }
}
