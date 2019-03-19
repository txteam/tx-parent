///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2017年1月2日
// * <修改描述:>
// */
//package com.tx.component.command.model;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
///**
// * 命令容器请求记录<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2017年1月2日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Entity
//@Table(name = "comm_notify_request")
//public class NotifyRequestRecord {
//    
//    /** 获取操作流水 */
//    private String id;
//    
//    /** 请求发起人所在的虚中心/分公司 */
//    private String vcid;
//    
//    /** 请求发起人所在的组织id */
//    private String organizationId;
//    
//    /** 操作员id */
//    private String operatorId;
//    
//    /** 备注信息 */
//    private String remark;
//    
//    /** 获取操作创建时间 */
//    private Date createDate;
//    
//    /** 交易类型 */
//    private String tradingRecordType;
//    
//    /** 所属模块 */
//    private String module;
//    
//    /** 请求类型 */
//    private Class<?> requestType;
//    
//    /** 请求Json数据 */
//    private String requestJson;
//    
//    /** 是否执行成功 */
//    private boolean success = false;
//    
//    /** 执行成功事件 */
//    private Date successDate;
//    
//    /** 错误次数 */
//    private int errorCount = 0;
//    
//    /** 最后一次错误时间 */
//    private Date lastErrorDate;
//    
//    /**
//     * @return 返回 id
//     */
//    public String getId() {
//        return id;
//    }
//    
//    /**
//     * @param 对id进行赋值
//     */
//    public void setId(String id) {
//        this.id = id;
//    }
//    
//    /**
//     * @return 返回 vcid
//     */
//    public String getVcid() {
//        return vcid;
//    }
//    
//    /**
//     * @param 对vcid进行赋值
//     */
//    public void setVcid(String vcid) {
//        this.vcid = vcid;
//    }
//    
//    /**
//     * @return 返回 organizationId
//     */
//    public String getOrganizationId() {
//        return organizationId;
//    }
//    
//    /**
//     * @param 对organizationId进行赋值
//     */
//    public void setOrganizationId(String organizationId) {
//        this.organizationId = organizationId;
//    }
//    
//    /**
//     * @return 返回 operatorId
//     */
//    public String getOperatorId() {
//        return operatorId;
//    }
//    
//    /**
//     * @param 对operatorId进行赋值
//     */
//    public void setOperatorId(String operatorId) {
//        this.operatorId = operatorId;
//    }
//    
//    /**
//     * @return 返回 remark
//     */
//    public String getRemark() {
//        return remark;
//    }
//    
//    /**
//     * @param 对remark进行赋值
//     */
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//    
//    /**
//     * @return 返回 createDate
//     */
//    public Date getCreateDate() {
//        return createDate;
//    }
//    
//    /**
//     * @param 对createDate进行赋值
//     */
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//    
//    /**
//     * @return 返回 tradingRecordType
//     */
//    public String getTradingRecordType() {
//        return tradingRecordType;
//    }
//    
//    /**
//     * @param 对tradingRecordType进行赋值
//     */
//    public void setTradingRecordType(String tradingRecordType) {
//        this.tradingRecordType = tradingRecordType;
//    }
//    
//    /**
//     * @return 返回 module
//     */
//    public String getModule() {
//        return module;
//    }
//    
//    /**
//     * @param 对module进行赋值
//     */
//    public void setModule(String module) {
//        this.module = module;
//    }
//    
//    /**
//     * @return 返回 requestType
//     */
//    public Class<?> getRequestType() {
//        return requestType;
//    }
//    
//    /**
//     * @param 对requestType进行赋值
//     */
//    public void setRequestType(Class<?> requestType) {
//        this.requestType = requestType;
//    }
//    
//    /**
//     * @return 返回 requestJson
//     */
//    public String getRequestJson() {
//        return requestJson;
//    }
//    
//    /**
//     * @param 对requestJson进行赋值
//     */
//    public void setRequestJson(String requestJson) {
//        this.requestJson = requestJson;
//    }
//    
//    /**
//     * @return 返回 success
//     */
//    public boolean isSuccess() {
//        return success;
//    }
//    
//    /**
//     * @param 对success进行赋值
//     */
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//    
//    /**
//     * @return 返回 successDate
//     */
//    public Date getSuccessDate() {
//        return successDate;
//    }
//    
//    /**
//     * @param 对successDate进行赋值
//     */
//    public void setSuccessDate(Date successDate) {
//        this.successDate = successDate;
//    }
//    
//    /**
//     * @return 返回 errorCount
//     */
//    public int getErrorCount() {
//        return errorCount;
//    }
//    
//    /**
//     * @param 对errorCount进行赋值
//     */
//    public void setErrorCount(int errorCount) {
//        this.errorCount = errorCount;
//    }
//    
//    /**
//     * @return 返回 lastErrorDate
//     */
//    public Date getLastErrorDate() {
//        return lastErrorDate;
//    }
//    
//    /**
//     * @param 对lastErrorDate进行赋值
//     */
//    public void setLastErrorDate(Date lastErrorDate) {
//        this.lastErrorDate = lastErrorDate;
//    }
//}
