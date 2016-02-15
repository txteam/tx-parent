/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月17日
 * <修改描述:>
 */
package com.tx.component.communication.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 待发送的消息<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年12月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SendMessage {
    
    /** 发送消息的唯一键 */
    private String id;
    
    /** 发送消息的流水号 */
    private String serialNumber;
    
    /** 消息类型 */
    private String type;
    
    /** 接收消息 */
    private String receivers;
    
    /** 发送时间：如果支持定时发送时间，则使用该时间 */
    private Date sendDate;
    
    /** 消息内容 */
    private String content;
    
    /** 消息内容模板Id */
    private String contentTemplateId;
    
    /** 消息内容模板消息：可在初始化时不进行写入，在Dialect中进行注入即可，便于日志记录 */
    private String contentTemplateMessage;
    
    /** 消息对象的其他属性 */
    public final Map<String, Object> attributes = new HashMap<String, Object>();

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
     * @return 返回 serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param 对serialNumber进行赋值
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return 返回 type
     */
    public String getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 返回 receivers
     */
    public String getReceivers() {
        return receivers;
    }

    /**
     * @param 对receivers进行赋值
     */
    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    /**
     * @return 返回 sendDate
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * @param 对sendDate进行赋值
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
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
     * @return 返回 contentTemplateMessage
     */
    public String getContentTemplateMessage() {
        return contentTemplateMessage;
    }

    /**
     * @param 对contentTemplateMessage进行赋值
     */
    public void setContentTemplateMessage(String contentTemplateMessage) {
        this.contentTemplateMessage = contentTemplateMessage;
    }

    /**
     * @return 返回 attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
