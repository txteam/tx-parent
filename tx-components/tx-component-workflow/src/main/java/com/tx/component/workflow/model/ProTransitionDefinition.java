/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-1
 * <修改描述:>
 */
package com.tx.component.workflow.model;



 /**
  * 流程流转定义
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-2-1]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ProTransitionDefinition {
    
    /**
      * 获取流程环节流转操作id
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getId();
    
    /**
      * 获取流程流转环节操作名
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getName();
    
    /**
      * 获取流程流转环节源节点定义
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return ProTaskDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ProTaskDefinition getSource();
    
    /**
      * 获取流程流转目标节点定义
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return ProTaskDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ProTaskDefinition getDestination();
}
