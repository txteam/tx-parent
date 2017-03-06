/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月12日
 * <修改描述:>
 */
package com.tx.component.communication.exceptions;

import com.tx.core.exceptions.ErrorCode;

/**
 * 消息容器错误编码<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum CommunicationContextErrorCodeEnum implements ErrorCode {
    
    /* -------　消息容器异常 start ------- */
    COMMUNICATION_CONTEXT_ERROR(230000, "消息容器异常"),
    
    COMMUNICATION_CONTEXT_INIT_ERROR(231000, "消息容器初始化异常"),
    
    SEND_MESSAGE_ERROR(232000, "消息发送异常")
    /* -------　消息容器异常 end   ------- */
    ;
    
    /** 编码 */
    private final int code;
    
    /** 内容 */
    private final String message;
    
    /** <默认构造函数> */
    private CommunicationContextErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    /**
     * @return 返回 code
     */
    public int getCode() {
        return code;
    }
    
    /**
     * @return 返回 message
     */
    public String getMessage() {
        return message;
    }
}
