/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

/**
 * 对应规则不存在
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleNotExsitException extends RuleAccessException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 6558310784986527643L;
    
    public RuleNotExsitException(String ruleName, String message,
            Throwable cause, String... parameters) {
        super(ruleName, null, null, message, cause, parameters);
    }
    
    public RuleNotExsitException(String ruleName, String message,
            String... parameters) {
        super(ruleName, null, null, message, parameters);
    }
}
