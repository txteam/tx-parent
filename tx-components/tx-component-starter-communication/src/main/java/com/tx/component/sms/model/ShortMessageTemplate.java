/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月19日
 * <修改描述:>
 */
package com.tx.component.sms.model;


/**
 * 短信模板<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ShortMessageTemplate {
    
    /** 模板id */
    private String id;
    
    /** 发送渠道 */
    private String channel;
    
    /** 模板编码 */
    private String code;
    
    /** 模板内容 */
    private String content;

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
     * @return 返回 channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param 对channel进行赋值
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return 返回 code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param 对code进行赋值
     */
    public void setCode(String code) {
        this.code = code;
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
