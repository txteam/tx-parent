/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-29
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.core.exceptions.SILException;

/**
 * 规则会话事务异常
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleSessionTransactionException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4501605360800406598L;

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "rule_session_transation_error";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "规则会话事务异常";
    }

    public RuleSessionTransactionException(String message, Object[] parameters) {
        super(message, parameters);
    }

    public RuleSessionTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleSessionTransactionException(String message) {
        super(message);
    }
}
