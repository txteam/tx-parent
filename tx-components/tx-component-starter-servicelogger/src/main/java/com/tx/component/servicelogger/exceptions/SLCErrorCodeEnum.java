/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月11日
 * <修改描述:>
 */
package com.tx.component.servicelogger.exceptions;

import com.tx.core.exceptions.ErrorCode;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum SLCErrorCodeEnum implements ErrorCode {
    
    /* -------　业务日志异常 start ------- */
    SERVICE_LOGGER_ERROR(211000, "业务日志容器"),
    
    SERVICE_LOGGER_CONTEXT_INIT_ERROR(211100, "业务日志容器初始化异常"),
    
    SERVICE_LOGGER_BUILD_ERROR(211200, "不支持的业务日志类型"),
    
    SERVICE_LOGGER_DATA_ACCESS_ERROR(211300, "业务日志持久化异常")
    /* -------　业务日志异常 end   ------- */
    ;
    
    /** 编码 */
    private final int code;
    
    /** 内容 */
    private final String message;
    
    /** <默认构造函数> */
    private SLCErrorCodeEnum(int code, String message) {
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
