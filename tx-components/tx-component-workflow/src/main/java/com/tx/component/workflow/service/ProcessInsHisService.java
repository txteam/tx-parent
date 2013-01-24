/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-17
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.util.Map;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-17]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ProcessInsHisService {
    /**
     * 根据流程定义名开启一条对应流程名的最新流程版本的流程实例<br/>
     * 1、统一流程定义名，可能对应多个流程定义版本，这里讲获取对应的最新版本的流程定义，从而生成最新流程定义的流程<br/>
     * 2、开始流程后返回新生成的流程实例
     * @param processDefinitionKey 流程定义名，建议对应使用模块中，将该流程定义名使用常量或枚举型数据进行管理<br/>
     * @return [参数说明]
     * 
     * @return ProcessInstance [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public String start(String processDefinitionKey);
   
   /**
     * 根据流程定义名开启一条对应流程名的最新流程版本的流程实例<br/>
     * 1、统一流程定义名，可能对应多个流程定义版本，这里讲获取对应的最新版本的流程定义，从而生成最新流程定义的流程<br/>
     * 2、开始流程后返回新生成的流程实例
     * @param processDefinitionKey 流程定义名，建议对应使用模块中，将该流程定义名使用常量或枚举型数据进行管理<br/>
     * @param paramMap
     * @return [参数说明]
     * 
     * @return ProcessInstance [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public String start(String processDefinitionKey,
           Map<String, Object> paramMap);
   
   /**
     * 设置流程变量<br/>
     * 1、利用该流程变量功能设置流程变量<br/>
     * 2、设置流程变量后，原已经设置的流程变量，并不会被删除，会增量添加<br/>
     * 3、如果流程变量同名，将被覆盖
     * @param processInsId
     * @param paramMap [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public void setProcessVaribals(String processInsId,
           Map<String, Object> paramMap);
   
   /** 
     * 获取对应流程实例的流程所有变量map映射
     * @param processInsId
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public Map<String, Object> getProcessVaribals(String processInsId);
   
   /** 
     * 获取流程变量<br/>
     * 1、逐个获取流程变量
     * @param processInsId
     * @param key
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public Object getProcessVaribal(String processInsId, String key);
   
   /** 
     * 设置流程变量<br/>
     * <功能详细描述>
     * @param processInsId
     * @param key
     * @param value [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public void setProcessVaribal(String processInsId, String key, Object value);
}
