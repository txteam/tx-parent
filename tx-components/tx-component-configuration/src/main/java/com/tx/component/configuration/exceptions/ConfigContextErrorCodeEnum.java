/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月12日
 * <修改描述:>
 */
package com.tx.component.configuration.exceptions;

import com.tx.core.exceptions.ErrorCode;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum ConfigContextErrorCodeEnum implements ErrorCode {
    
    /* -------　配置容器异常 start ------- */
    CONFIG_CONTEXT_ERROR(240000, "配置容器异常"),
    
    CONFIG_CONTEXT_INIT_ERROR(241000, "配置容器初始化异常"),
    
    CONFIG_ITEM_ACCESS_ERROR(242000, "配置项访问异常"),
    
    CONFIG_ITEM_NOT_EXIST_ERROR(242001, "配置项不存在"),
    
    UNMODIFYABLE_ERROR(242002, "配置项目不可编辑")
    /* -------　配置容器异常 end   ------- */
    ;
    
    /** 编码 */
    private final int code;
    
    /** 内容 */
    private final String message;
    
    /** <默认构造函数> */
    private ConfigContextErrorCodeEnum(int code, String message) {
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
