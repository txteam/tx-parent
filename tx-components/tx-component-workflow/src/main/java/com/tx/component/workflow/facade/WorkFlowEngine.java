/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-16
 * <修改描述:>
 */
package com.tx.component.workflow.facade;

import com.tx.component.workflow.service.ProcessDefService;
import com.tx.component.workflow.service.ProcessInstanceService;

/**
 * 工作流引擎门面类用以对外提供服务<br/>
 * 封装的意义：隔离底层工作流引擎实现（与jbpm,activiti,cutomize隔离）<br/>
 * 隔离工作流引擎特殊实现，隔离底层引擎的不同性，提供项目组统一的工作流引擎接口<br/>
 * 隔离底层实现差异性<br/>
 * 1、封装基于activiti针对业务情况进行二次封装得到<br/>
 * <功能详细描述>
 * 在系统中，暂考虑只将流程引擎当做状态机使用<br/>
 *      后续可扩展流程引擎当做，流程处理人的引擎支持，暂不考虑该部分功能<br/>
 *      人员部分实现，可考虑后续扩展插件进行支持<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface WorkFlowEngine extends 
        ProcessDefService, ProcessInstanceService {
    
    
}
