/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月14日
 * <修改描述:>
 */
package com.tx.component.command.context.request;

import java.util.Date;

import com.tx.component.command.context.CommandRequest;
import com.tx.core.util.UUIDUtils;

/**
 * 抽象交易请求类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年1月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractRequest implements CommandRequest {
    
    /** 获取操作流水 */
    private String id;
    
    /** 请求发起人所在的虚中心/分公司 */
    private String vcid;
    
    /** 请求发起人所在的组织id */
    private String organizationId;
    
    /** 操作员id */
    private String operatorId;
    
    /** 备注信息 */
    private String remark;
    
    /** 获取操作创建时间 */
    private Date createDate;
    
    /** 所属模块 */
    private String module;
    
    /** 交易类型 */
    private String requestType;
    
    /** <默认构造函数> */
    public AbstractRequest() {
        super();
        this.id = UUIDUtils.generateUUID();
        
        //FIXME: 封装统一会话容器
        //this.operatorId = WebContextUtils.getCurrentOperatorId();
        //this.vcid = WebContextUtils.getCurrentVcid();
        //this.organizationId = WebContextUtils.getCurrentOrganizationId();
        
        this.createDate = new Date();
        
        this.module = module();
        this.requestType = requestType();
    }
    
    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return 返回 operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }
    
    /**
     * @param 对operatorId进行赋值
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
    
    /**
     * @return 返回 vcid
     */
    public String getVcid() {
        return vcid;
    }
    
    /**
     * @param 对vcid进行赋值
     */
    public void setVcid(String vcid) {
        this.vcid = vcid;
    }
    
    /**
     * @return 返回 organizationId
     */
    public String getOrganizationId() {
        return organizationId;
    }
    
    /**
     * @param 对organizationId进行赋值
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
    
    /**
     * @return 返回 createDate
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * @param 对createDate进行赋值
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * @return 返回 remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @return 返回 requestType
     */
    public String getRequestType() {
        return requestType;
    }
    
    /**
     * @param 对requestType进行赋值
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    
    /**
      * 获取请求所属模块<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract String module();
    
    /**
      * 获取交易记录类型
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return TradingRecordType [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract String requestType();
}
