/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-17
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.util.Map;


 /**
  * 工作流实例业务层逻辑
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-17]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ProcessInsService {
    
    /**
      * 开始一条流程实例<br/>
      *     创建一个流程实例<br/>
      * @param processKey
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String start(String processKey);
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param processKey
      * @param varibalMap
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String start(String processKey,Map<String, String> varibalMap);
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param processKey
      * @param processInstanceId
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String start(String processKey,String processInstanceId);
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param processKey
      * @param processInstanceId
      * @param varibalMap
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String start(String processKey,String processInstanceId,Map<String, String> varibalMap);
    
    public String process();
}
