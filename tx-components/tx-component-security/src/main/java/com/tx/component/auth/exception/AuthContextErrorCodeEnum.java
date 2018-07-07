/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月11日
 * <修改描述:>
 */
package com.tx.component.auth.exception;

import com.tx.core.exceptions.ErrorCode;

/**
 * 权限容器错误编码<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum AuthContextErrorCodeEnum implements ErrorCode {
    
    /* -------　权限容器异常 start ------- */
    AUTH_CONTEXT_ERROR(220000, "权限容器异常"),
    
    AUTH_CONTEXT_INIT_ERROR(221000, "权限容器初始化异常"),
    
    AUTH_ITEM_NOT_EXIST_ERROR(222000, "权限项不存在")
    /* -------　权限容器异常 end   ------- */
    ;
    
    /** 编码 */
    private final int code;
    
    /** 内容 */
    private final String message;
    
    /** <默认构造函数> */
    private AuthContextErrorCodeEnum(int code, String message) {
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
