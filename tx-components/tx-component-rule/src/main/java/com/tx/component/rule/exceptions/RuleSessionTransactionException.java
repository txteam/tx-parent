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
    
    /** <默认构造函数> */
    public RuleSessionTransactionException(String errorMessage,
            String... parameters) {
        super(RuleExceptionTranslator.RULE_ERROR_CODE, errorMessage, parameters);
    }
    
    /** <默认构造函数> */
    public RuleSessionTransactionException(String errorMessage,
            Throwable cause, String... parameters) {
        super(RuleExceptionTranslator.RULE_ERROR_CODE, errorMessage, cause,
                parameters);
    }
    
}
