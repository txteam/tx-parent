/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-17
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.util.HashMap;
import java.util.Map;

import com.tx.component.workflow.model.ProTaskDefinition;
import com.tx.component.workflow.model.ProcessDefinition;

/**
 * 流程定义业务层<br/>
 * 1、用以封闭底层引擎实现接口，为未来可能的流程变更以及扩展提供可能<br/>
 * 2、主要包含流程文件的解析，以及流程定义自定义扩展的服务<br/>
 *     a、提供通过导入的流程定义文件，解析生成流程<br/>
 *     b、提供启动时检查是否需要加载新的流程定义，通过版本<br/>
 *     c、通过该定义决定启动时加载哪些流程<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ProcessDefinitionService {
    
    /**
     * 流程定义流程定义缓存
     */
    private Map<String, ProcessDefinition> processDefCache = new HashMap<String, ProcessDefinition>();
    
    /**
     * 流程环节定义缓存
     */
    private Map<String, Map<String, ProTaskDefinition>> processTaskDefCache = new HashMap<String, Map<String, ProTaskDefinition>>();
    
    public ProcessDefinition getProcessDefinition(String key){
        return null;
    }
    
    public ProcessDefinition getProcessDefinition(String key,String version){
        return null;
    }
    
    public ProTaskDefinition getProTaskDefinition(String taskDefinitionId){
        return null;
    }
    
    public ProTaskDefinition getProTaskDefinition(String id,String process){
        return null;
    }
    
    public ProTaskDefinition getProTaskDefinition(String id,String processKey,String xx){
        return null;
    }
}
