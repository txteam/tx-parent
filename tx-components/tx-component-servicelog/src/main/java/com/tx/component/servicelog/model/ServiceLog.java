/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-16
 * <修改描述:>
 */
package com.tx.component.servicelog.model;

import java.util.Date;


 /**
  * 业务日志模型<br/>
  *     业务日志主要目的为
  *     记录：什么人在什么时候做了什么事情<br/>
  *           所以在这里抽取其中核心要素定义业务日志基类<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-16]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ServiceLog {
    
    /**
      * 业务日志唯一id
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getId();
    
    /**
      * 当前操作员id,可以为空
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getCurrentOperatorId();
    
    /**
      * 业务日志记录时间<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Date [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Date getCreateDate();
    
    /**
      * 业务日志记录信息<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getMessage();
}
