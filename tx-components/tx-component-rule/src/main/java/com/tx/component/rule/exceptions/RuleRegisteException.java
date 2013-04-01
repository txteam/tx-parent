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
    
    /** <默认构造函数> */
    public RuleRegisteException(String errorMessage, String... parameters) {
        super(RuleExceptionTranslator.RULE_ERROR_CODE, errorMessage, parameters);
    }
    
    /** <默认构造函数> */
    public RuleRegisteException(String errorMessage, Throwable cause,
            String... parameters) {
        super(RuleExceptionTranslator.RULE_ERROR_CODE, errorMessage, cause,
                parameters);
    }
    
    /** <默认构造函数> */
    public RuleRegisteException(String errorMessage, Object[] parameters) {
        super(RuleExceptionTranslator.RULE_ERROR_CODE, errorMessage, parameters);
    }
    
    /** <默认构造函数> */
    public RuleRegisteException(String errorMessage, Throwable cause,
            Object[] parameters) {
        super(RuleExceptionTranslator.RULE_ERROR_CODE, errorMessage, cause,
                parameters);
    }
    
}
