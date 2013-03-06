/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-4
 * <修改描述:>
 */
package com.tx.component.workflow.model;


 /**
  * 流程实例接口<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-4]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ProcessInstance {
    
    /**
      * 流程实例id
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getId();
    
    /**
      * 流程实例对应的流程定义id
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getProcessDefinitionId();
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getBusinessKey();
    
    /**
      * 流程实例是否被挂起
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean isSuspended();
}
