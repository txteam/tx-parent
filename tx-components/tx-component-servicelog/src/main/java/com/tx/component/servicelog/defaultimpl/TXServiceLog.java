/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-16
 * <修改描述:>
 */
package com.tx.component.servicelog.defaultimpl;

import java.util.Date;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionGreaterOrEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLess;

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
public interface TXServiceLog {
    
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
      * 设置业务日志唯一键<br/>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setId(String id);
    
    /**
      * 当前操作员id,可以为空
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getOperatorId();
    
    /**
      * 设置操作员id
      *<功能详细描述>
      * @param operatorId [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setOperatorId(String operatorId);
    
    /**
      * 获取当前虚中心id
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getVcid();
    
    /**
      * 设置虚中心id
      *<功能详细描述>
      * @param vcid [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setVcid(String vcid);
    
    /**
      * 获取当前组织id 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getOrganizationId();
    
    /**
      * 设置组织id
      *<功能详细描述>
      * @param organizationId [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setOrganizationId(String organizationId);
    
    /**
      * 业务日志记录时间<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Date [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @QueryConditionGreaterOrEqual(key="minCreateDate")
    @QueryConditionLess(key="maxCreateDate")
    public Date getCreateDate();
    
    /**
      * 设置业务日志记录时间<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Date [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setCreateDate(Date createDate);
    
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
    
    /**
      * 设置业务日志信息<br/> 
      *<功能详细描述>
      * @param message [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setMessage(String message);
    
    /**
      * 获取客户端ip地址
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getClientIpAddress();
    
    /**
      * 设置客户端ip地址<br/>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setClientIpAddress(String clientIpAddress);
    
    /**
     * @return 返回 operatorLoginName
     */
    public String getOperatorLoginName();

    /**
     * @param 对operatorLoginName进行赋值
     */
    public void setOperatorLoginName(String operatorLoginName);

    /**
     * @return 返回 operatorName
     */
    public String getOperatorName();

    /**
     * @param 对operatorName进行赋值
     */
    public void setOperatorName(String operatorName);
}
