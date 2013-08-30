/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.core.exceptions.SILException;

/**
 * 注册规则异常
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleRegisteException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7388892404144552007L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "RULE_REGISTE_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "规则容器注册规则异常";
    }

    public RuleRegisteException(String message, Object[] parameters) {
        super(message, parameters);
    }

    public RuleRegisteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleRegisteException(String message) {
        super(message);
    }
}
