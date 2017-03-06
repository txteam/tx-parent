/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.core.exceptions.ErrorCode;

/**
 * 规则访问异常
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleAccessException extends RuleContextException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3113970360855922652L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return RuleContextErrorCodeEnum.RULL_ACCESS_ERROR;
    }
    
    /** <默认构造函数> */
    public RuleAccessException() {
        super();
    }
    
    /** <默认构造函数> */
    public RuleAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public RuleAccessException(String message) {
        super(message);
    }
}
