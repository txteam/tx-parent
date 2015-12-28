/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月17日
 * <修改描述:>
 */
package com.tx.component.messagesender.model;

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
    
    /** 消息类型 */
    private String type;
    
    /** 接收消息 */
    private String receivers;
    
    /** 发送时间：如果支持定时发送时间，则使用该时间 */
    private Date sendDate;
    
    /** 消息内容 */
    private String content;
    
    /** 消息对象的其他属性 */
    public final Map<String, Object> attributes = new HashMap<String, Object>();
    
    /**
      * 获取消息属性<br/>
      *     支持 get("type")
      *         get("message")
      *         此类的数据提取
      * <功能详细描述>
      * @param keyPath
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getMessageAttribute(String keyPath) {
        
        return null;
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
}
