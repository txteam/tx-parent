/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-3-4
 * <修改描述:>
 */
package com.tx.component.workflow.model;

/**
 * 流程环节定义模型<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-3-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ProTaskDef {
    
    /**
     * task实例名
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    String getName();
    
    /**
      * 任务实例描叙信息
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getDescription();
    
    /**
     * 任务对应流程定义id
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    String getProcessDefinitionId();
    
    /**
     * 任务对应的任务定义key
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    String getTaskDefinitionKey();
}
