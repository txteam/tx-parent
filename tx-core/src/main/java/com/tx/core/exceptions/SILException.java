/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.core.exceptions;

import org.slf4j.helpers.MessageFormatter;

import com.tx.core.TxConstants;

/**
 * <系统内部逻辑异常:System Inner Logic Exception>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class SILException extends RuntimeException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4629630103815146373L;
    
    /** 错误编码 */
    private String errorCode = "";
    
    private String errorMessage = "";
    
    public abstract String getErrorCode();
    
    public abstract String getErrorMessage();
    
    /** <默认构造函数> */
    public SILException(String message,
            String... parameters) {
        super((parameters == null || parameters.length == 0) ? message
                : MessageFormatter.arrayFormat(message, parameters)
                        .getMessage());
        this.errorCode = getErrorCode();
        this.errorMessage = getErrorMessage();
    }
    
    /** <默认构造函数> */
    public SILException(String message, Throwable cause,
            String... parameters) {
        super((parameters == null || parameters.length == 0) ? message
                : MessageFormatter.arrayFormat(message, parameters)
                        .getMessage(), cause);
        this.errorCode = getErrorCode();
        this.errorMessage = getErrorMessage();
    }
    
    /**
     * <默认构造函数>
     */
    public SILException(String message,
            Object[] parameters) {
        super((parameters == null || parameters.length == 0) ? message
                : MessageFormatter.arrayFormat(message, parameters)
                        .getMessage());
        this.errorCode = getErrorCode();
        this.errorMessage = getErrorMessage();
    }
    
    /** <默认构造函数> */
    public SILException(String message, Throwable cause,
            Object[] parameters) {
        super((parameters == null || parameters.length == 0) ? message
                : MessageFormatter.arrayFormat(message, parameters)
                        .getMessage(), cause);
        this.errorCode = getErrorCode();
        this.errorMessage = getErrorMessage();
    }
    
    /**
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("SILException: ").append(this.getClass().getName());
        sb.append("   errorCode: ").append(this.errorCode).append("/n");
        sb.append("   errorMessage: ").append(this.errorMessage).append(this.errorCode).append("/n");
        sb.append("   Exception toString: ").append(super.toString());
        return sb.toString();
    }
}
