/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月11日
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

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
public enum RuleContextErrorCodeEnum implements ErrorCode {
    
    /* -------　权限容器异常 start ------- */
    RULL_CONTEXT_ERROR(250000, "权限容器异常"),
    
    RULL_CONTEXT_INIT_ERROR(251000, "权限容器初始化异常"),
    
    RULL_ITEM_NOT_EXIST_ERROR(252000, "权限项不存在"),
    
    RULL_ACCESS_ERROR(253000, "规则访问异常"),
    
    RULL_REGISTE_ERROR(253100, "规则注册异常"),
    
    RULL_NOT_EXSIT_ERROR(253200, "规则不存在"),
    
    RULL_STATE_ERROR(253300, "规则状态异常")
    /* -------　权限容器异常 end   ------- */
    ;
    
    /** 编码 */
    private final int code;
    
    /** 内容 */
    private final String message;
    
    /** <默认构造函数> */
    private RuleContextErrorCodeEnum(int code, String message) {
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
