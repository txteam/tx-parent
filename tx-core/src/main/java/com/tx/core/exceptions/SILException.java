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
public class SILException extends RuntimeException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4629630103815146373L;
    
    /** 错误编码 */
    private String errorCode = "";
    
    /** <默认构造函数> */
    public SILException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }
    
    /** <默认构造函数> */
    public SILException(String errorCode, Throwable cause) {
        super(cause);
    }
    
    /** <默认构造函数> */
    public SILException(String errorCode, String errorMessage,
            String... parameters) {
        super((parameters == null || parameters.length == 0) ? errorMessage
                : MessageFormatter.arrayFormat(errorMessage, parameters)
                        .getMessage());
        this.errorCode = errorCode;
    }
    
    /** <默认构造函数> */
    public SILException(String errorCode, String errorMessage, Throwable cause,
            String... parameters) {
        super((parameters == null || parameters.length == 0) ? errorMessage
                : MessageFormatter.arrayFormat(errorMessage, parameters)
                        .getMessage(), cause);
        this.errorCode = errorCode;
    }
    
    /**
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("SILException errorCode: ").append(this.errorCode);
        sb.append(" . Exception to String: ").append(super.toString());
        return sb.toString();
    }
}
