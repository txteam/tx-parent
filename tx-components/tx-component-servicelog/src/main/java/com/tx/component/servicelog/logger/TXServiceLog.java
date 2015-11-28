/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-16
 * <修改描述:>
 */
package com.tx.component.servicelog.logger;

import java.util.Date;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionGreaterOrEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLess;

/**
 * 业务日志模型<br/>
 * 业务日志主要目的为 记录：什么人在什么时候做了什么事情<br/>
 * 所以在这里抽取其中核心要素定义业务日志基类<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface TXServiceLog {
    
    /** 业务日志唯一id */
    public String getId();
    
    /** 设置业务日志唯一键 */
    public void setId(String id);
    
    /** 当前操作员id,可以为空 */
    public String getOperatorId();
    
    /** 设置操作员id */
    public void setOperatorId(String operatorId);
    
    /** 获取当前虚中心id */
    public String getVcid();
    
    /** 设置虚中心id */
    public void setVcid(String vcid);
    
    /** 获取当前组织id */
    public String getOrganizationId();
    
    /** 设置组织id */
    public void setOrganizationId(String organizationId);
    
    /** 业务日志记录时间 */
    @QueryConditionGreaterOrEqual(key = "minCreateDate")
    @QueryConditionLess(key = "maxCreateDate")
    public Date getCreateDate();
    
    /** 设置业务日志记录时间 */
    public void setCreateDate(Date createDate);
    
    /** 业务日志记录信息 */
    public String getMessage();
    
    /** 设置业务日志信息 */
    public void setMessage(String message);
    
    /** 获取客户端ip地址 */
    public String getClientIpAddress();
    
    /** 设置客户端ip地址 */
    public void setClientIpAddress(String clientIpAddress);
    
    /** 登陆名 */
    public String getOperatorLoginName();
    
    /** 登陆名 */
    public void setOperatorLoginName(String operatorLoginName);
    
    /** 操作员名称 */
    public String getOperatorName();
    
    /** 操作员名称 */
    public void setOperatorName(String operatorName);
}
