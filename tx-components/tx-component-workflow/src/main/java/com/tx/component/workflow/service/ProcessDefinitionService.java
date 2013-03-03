/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-3
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.io.InputStream;

import com.tx.component.workflow.model.ProcessDefinition;


 /**
  * 流程定义相关业务逻辑层
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-3]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ProcessDefinitionService {
    
    /**
     * 更具指定名，以及输入流部署对应的流程
     * @param key
     * @param inputStream [参数说明]
     * @param serviceType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   ProcessDefinition deploy(String resourceName, InputStream inputStream,
           String serviceType);
}
