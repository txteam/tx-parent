/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月11日
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILException;

/**
 * 规则容器异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class RuleContextException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 8057301704561184079L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return RuleContextErrorCodeEnum.RULL_CONTEXT_ERROR;
    }
    
    /** <默认构造函数> */
    public RuleContextException() {
        super();
    }
    
    /** <默认构造函数> */
    public RuleContextException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public RuleContextException(String message) {
        super(message);
    }
    
}
