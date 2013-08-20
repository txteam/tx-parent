/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.auth.exceptions;

import com.tx.core.exceptions.ErrorCodeConstant;
import com.tx.core.exceptions.SILException;

/**
 * 权限定义不存在异常<br/>
 *     调用权限容器，检测权限引用是否存在时<br/>
 *     如果对应的权限定义不存在，则抛出该异常<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthDefinitionNotExsitException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2814953773924819958L;
    
    private String ruleId;
    
    /** <默认构造函数> */
    public AuthDefinitionNotExsitException(String message, Object[] parameters) {
        super(message, parameters);
        // TODO Auto-generated constructor stub
    }

    /** <默认构造函数> */
    public AuthDefinitionNotExsitException(String message, String... parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public AuthDefinitionNotExsitException(String message, Throwable cause,
            Object[] parameters) {
        super(message, cause, parameters);
    }

    /** <默认构造函数> */
    public AuthDefinitionNotExsitException(String message, Throwable cause,
            String... parameters) {
        super(message, cause, parameters);
    }

    /** <默认构造函数> */
    public AuthDefinitionNotExsitException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * @return 返回 ruleId
     */
    public String getRuleId() {
        return ruleId;
    }

    /**
     * @param 对ruleId进行赋值
     */
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }
}
