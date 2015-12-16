/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月24日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.logger;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

/**
 * 报文日志
 * 
 * @author rain
 * @version [版本号, 2015年11月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TxLoaclFileServiceLog extends TXBaseServiceLog {
    
    /** 报文请求id */
    private String messageid;
    
    /** 所属模块 */
    private String module;
    
    /** 请求数据体 */
    private String requestBody;
    
    /** 响应代码 */
    private String responseCode;
    
    /** 响应代码信息 */
    private String responseCodeMessage;
    
    /** 响应数据体 */
    private String responseBody;
    
    /** 执行时间(可以保存报文请求时间,controller 执行时间等等,随意咯) */
    private String useTime;
    
    /** 版本号 */
    private String version;
    
    /** 备注 */
    private String remark;
    
    /** 其他参数 */
    @Transient
    private Map<String, Object> otherParams = new HashMap<String, Object>();
    
    /**
     * 构造报文日志<br />
     * 
     * @version [版本号, 2015年11月24日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public TxLoaclFileServiceLog() {
        super();
    }
    
    /**
     * 构造报文日志<br />
     * 
     * @param messageid 报文id
     * @param module 报文模块
     * @param requestBody 请求体
     * @param responseCode 响应代码
     * @param responseCodeMessage 响应代码信息
     * @param responseBody 响应体
     *            
     * @version [版本号, 2015年11月24日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public TxLoaclFileServiceLog(
            String messageid,
            String module,
            String requestBody,
            String responseCode,
            String responseCodeMessage,
            String responseBody) {
        super();
        this.messageid = messageid;
        this.module = module;
        this.requestBody = requestBody;
        this.responseCode = responseCode;
        this.responseCodeMessage = responseCodeMessage;
        this.responseBody = responseBody;
    }
    
    /**
     * 构造报文日志<br />
     * 
     * @param messageid 报文id
     * @param module 报文模块
     * @param requestBody 请求体
     * @param responseCode 响应代码
     * @param responseCodeMessage 响应代码信息
     * @param responseBody 响应体
     * @param useTime 使用时间
     * @param remark 备注
     * @param version 版本
     *            
     * @version [版本号, 2015年11月24日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public TxLoaclFileServiceLog(
            String messageid,
            String module,
            String requestBody,
            String responseCode,
            String responseCodeMessage,
            String responseBody,
            String useTime,
            String remark,
            String version) {
        this(messageid, module, requestBody, responseCode, responseCodeMessage, responseBody);
        this.useTime = useTime;
        this.remark = remark;
        this.version = version;
    }
    
    /** @return 返回 messageid */
    public String getMessageid() {
        return messageid;
    }
    
    /** @return 返回 module */
    public String getModule() {
        return module;
    }
    
    /** 其他参数 */
    public final Map<String, Object> getOtherParams() {
        return otherParams;
    }
    
    /** @return 返回 remark */
    public String getRemark() {
        return remark;
    }
    
    /** @return 返回 requestBody */
    public String getRequestBody() {
        return requestBody;
    }
    
    /** @return 返回 responseBody */
    public String getResponseBody() {
        return responseBody;
    }
    
    /** @return 返回 responseCode */
    public String getResponseCode() {
        return responseCode;
    }
    
    /** @return 返回 responseCodeMessage */
    public String getResponseCodeMessage() {
        return responseCodeMessage;
    }
    
    /** @return 返回 useTime */
    public String getUseTime() {
        return useTime;
    }
    
    /** @return 返回 version */
    public String getVersion() {
        return version;
    }
    
    /** 添加其他参数 */
    public final void putOtherParam(String key, Object object) {
        this.otherParams.put(key, object);
    }
    
    /** @param 对 messageid 进行赋值 */
    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }
    
    /** @param 对 module 进行赋值 */
    public void setModule(String module) {
        this.module = module;
    }
    
    /** 其他参数 */
    public final void setOtherParams(Map<String, Object> otherParams) {
        this.otherParams = otherParams;
    }
    
    /** @param 对 remark 进行赋值 */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /** @param 对 requestBody 进行赋值 */
    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
    
    /** @param 对 responseBody 进行赋值 */
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
    
    /** @param 对 responseCode 进行赋值 */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    
    /** @param 对 responseCodeMessage 进行赋值 */
    public void setResponseCodeMessage(String responseCodeMessage) {
        this.responseCodeMessage = responseCodeMessage;
    }
    
    /** @param 对 useTime 进行赋值 */
    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
    
    /** @param 对 version 进行赋值 */
    public void setVersion(String version) {
        this.version = version;
    }
    
}
