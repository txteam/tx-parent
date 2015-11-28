/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.logger;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.OrderBy;

import org.slf4j.helpers.MessageFormatter;

/**
 * 基础业务日志类
 * 
 * @author brady
 * @version [版本号, 2013-9-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class TXBaseServiceLog implements TXServiceLog {
    
    /** 业务日志id */
    @Id
    private String id;
    
    /** 当前操作员虚中心id,允许为空 */
    private String vcid;
    
    /** 当前组织id,允许为空 */
    private String organizationId;
    
    /** 当前操作人员,允许为空 */
    private String operatorId;
    
    /** 访问客户端ip地址 */
    private String clientIpAddress;
    
    /** 业务日志记录时间 */
    @OrderBy("createDate desc")
    private Date createDate;
    
    /** 业务日志消息体 */
    private String message;
    
    /** 操作员登录名 */
    private String operatorLoginName;
    
    /** 操作员名 */
    private String operatorName;
    
    public TXBaseServiceLog() {
    }
    
    public TXBaseServiceLog(String message) {
        super();
        this.message = message;
    }
    
    public TXBaseServiceLog(String message, Object[] messageParams) {
        super();
        this.message = MessageFormatter.arrayFormat(message, messageParams).getMessage();
    }
    
    /** 访问客户端ip地址 */
    public String getClientIpAddress() {
        return clientIpAddress;
    }
    
    /** 业务日志记录时间 */
    public Date getCreateDate() {
        return createDate;
    }
    
    /** 业务日志id */
    public String getId() {
        return id;
    }
    
    /** 业务日志消息体 */
    public String getMessage() {
        return message;
    }
    
    /** 当前操作人员,允许为空 */
    public String getOperatorId() {
        return operatorId;
    }
    
    /** 操作员登录名 */
    public String getOperatorLoginName() {
        return operatorLoginName;
    }
    
    /** 操作员名 */
    public String getOperatorName() {
        return operatorName;
    }
    
    /** 当前组织id,允许为空 */
    public String getOrganizationId() {
        return organizationId;
    }
    
    /** 当前操作员虚中心id,允许为空 */
    public String getVcid() {
        return vcid;
    }
    
    /** 访问客户端ip地址 */
    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }
    
    /** 业务日志记录时间 */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /** 业务日志id */
    public void setId(String id) {
        this.id = id;
    }
    
    /** 业务日志消息体 */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /** 当前操作人员,允许为空 */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
    
    /** 操作员登录名 */
    public void setOperatorLoginName(String operatorLoginName) {
        this.operatorLoginName = operatorLoginName;
    }
    
    /** 操作员名 */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    
    /** 当前组织id,允许为空 */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
    
    /** 当前操作员虚中心id,允许为空 */
    public void setVcid(String vcid) {
        this.vcid = vcid;
    }
}
