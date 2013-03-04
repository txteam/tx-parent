/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-3
 * <修改描述:>
 */
package com.tx.component.workflow.model;

import java.io.Serializable;



 /**
  * 流程定义实例具有的接口方法
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-3]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ProcessDef extends Serializable{
    
    /**
      * 流程定义id
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getId();

    /**
      * 流程定义目录类似namespace的作用
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getCategory();
    
    /**
      * 流程定义名
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getName();
    
    /**
      * 流程定义key关键字
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getKey();
    
    /**
      * 流程定义详细描述
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getDescription();
    
    /**
      * 流程定义版本
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    int getVersion();

    /**
      * 流程定义资源名称
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getResourceName();
    
    /**
      * 当前流程定义是否挂起
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean isSuspended();
}
