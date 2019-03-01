/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月21日
 * <修改描述:>
 */
package com.tx.component.communication.model;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionGreaterOrEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLess;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 短信发送记录<br/>
 * <功能详细描述>
 *
 * @author  Administrator
 * @version  [版本号, 2016年2月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "comm_message_send_record")
public class MessageSendRecord {

   /** 记录id */
   @Id
   private String id;

   /** 消息类型 */
   @UpdateAble
   @QueryConditionEqual
   private MessageTypeEnum type;

   /** 状态 */
   @UpdateAble
   @QueryConditionEqual
   private MessageSendStatusEnum status;

   /** 消息接收人 */
   private String receiver;

   /** title */
   private String title;

   /** 内容 */
   private String content;

   /** 内容文件id */
   private String contentFileId;

   /** 短信内容模板id */
   private String contentTemplateId;

   /** 短信内容模板code */
   private String contentTemplateCode;

   /** 内容模板文件id */
   private String contentTemplateFileId;

   /** 短信是否发送成功 */
   @UpdateAble
   private boolean success;

   /** 错误编码 */
   @UpdateAble
   private String errorCode;

   /** 错误消息 */
   @UpdateAble
   private String errorMessage;

   /** 备注 */
   private String remark;

   /** 调用ip */
   private String requestIp;

   public String getRequestIp() {
       return requestIp;
   }

   public void setRequestIp(String requestIp) {
       this.requestIp = requestIp;
   }

   /** 失败后是否重发 */
   private boolean sendAgainWhenFail = false;

   /** 失败次数 */
   private int failCount;

   /** 最后发布日期 */
   private Date lastSendDate;

   /** 创建时间 */
   @QueryConditionGreaterOrEqual(key="minCreateDate")
   @QueryConditionLess(key="maxCreateDate")
   private Date createDate;

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
    * @return 返回 type
    */
   public MessageTypeEnum getType() {
       return type;
   }

   /**
    * @param 对type进行赋值
    */
   public void setType(MessageTypeEnum type) {
       this.type = type;
   }

   /**
    * @return 返回 status
    */
   public MessageSendStatusEnum getStatus() {
       return status;
   }

   /**
    * @param 对status进行赋值
    */
   public void setStatus(MessageSendStatusEnum status) {
       this.status = status;
   }

   /**
    * @return 返回 receiver
    */
   public String getReceiver() {
       return receiver;
   }

   /**
    * @param 对receiver进行赋值
    */
   public void setReceiver(String receiver) {
       this.receiver = receiver;
   }

   /**
    * @return 返回 title
    */
   public String getTitle() {
       return title;
   }

   /**
    * @param 对title进行赋值
    */
   public void setTitle(String title) {
       this.title = title;
   }

   /**
    * @return 返回 content
    */
   public String getContent() {
       return content;
   }

   /**
    * @param 对content进行赋值
    */
   public void setContent(String content) {
       this.content = content;
   }

   /**
    * @return 返回 contentFileId
    */
   public String getContentFileId() {
       return contentFileId;
   }

   /**
    * @param 对contentFileId进行赋值
    */
   public void setContentFileId(String contentFileId) {
       this.contentFileId = contentFileId;
   }

   /**
    * @return 返回 contentTemplateId
    */
   public String getContentTemplateId() {
       return contentTemplateId;
   }

   /**
    * @param 对contentTemplateId进行赋值
    */
   public void setContentTemplateId(String contentTemplateId) {
       this.contentTemplateId = contentTemplateId;
   }

   /**
    * @return 返回 contentTemplateCode
    */
   public String getContentTemplateCode() {
       return contentTemplateCode;
   }

   /**
    * @param 对contentTemplateCode进行赋值
    */
   public void setContentTemplateCode(String contentTemplateCode) {
       this.contentTemplateCode = contentTemplateCode;
   }

   /**
    * @return 返回 contentTemplateFileId
    */
   public String getContentTemplateFileId() {
       return contentTemplateFileId;
   }

   /**
    * @param 对contentTemplateFileId进行赋值
    */
   public void setContentTemplateFileId(String contentTemplateFileId) {
       this.contentTemplateFileId = contentTemplateFileId;
   }

   /**
    * @return 返回 success
    */
   public boolean isSuccess() {
       return success;
   }

   /**
    * @param 对success进行赋值
    */
   public void setSuccess(boolean success) {
       this.success = success;
   }

   /**
    * @return 返回 errorCode
    */
   public String getErrorCode() {
       return errorCode;
   }

   /**
    * @param 对errorCode进行赋值
    */
   public void setErrorCode(String errorCode) {
       this.errorCode = errorCode;
   }

   /**
    * @return 返回 errorMessage
    */
   public String getErrorMessage() {
       return errorMessage;
   }

   /**
    * @param 对errorMessage进行赋值
    */
   public void setErrorMessage(String errorMessage) {
       this.errorMessage = errorMessage;
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
    * @return 返回 sendAgainWhenFail
    */
   public boolean isSendAgainWhenFail() {
       return sendAgainWhenFail;
   }

   /**
    * @param 对sendAgainWhenFail进行赋值
    */
   public void setSendAgainWhenFail(boolean sendAgainWhenFail) {
       this.sendAgainWhenFail = sendAgainWhenFail;
   }

   /**
    * @return 返回 failCount
    */
   public int getFailCount() {
       return failCount;
   }

   /**
    * @param 对failCount进行赋值
    */
   public void setFailCount(int failCount) {
       this.failCount = failCount;
   }

   /**
    * @return 返回 lastSendDate
    */
   public Date getLastSendDate() {
       return lastSendDate;
   }

   /**
    * @param 对lastSendDate进行赋值
    */
   public void setLastSendDate(Date lastSendDate) {
       this.lastSendDate = lastSendDate;
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
}
