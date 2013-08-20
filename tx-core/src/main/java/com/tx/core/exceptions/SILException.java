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
 * 系统内部逻辑异常:System Inner Logic Exception
 * 系统内部错误
 *     errorMessage用于异常抛送到页面后，为用户显示错误
 *     因具体的系统使用者不用太过关心message(详细错误信息)
 *     如果非简体中文系统，则可以通过errorCode得到一个errorCode到errorMessage的映射
 *     以达到系统兼容未来多语言或多显示型式的方式
 *     errorMessage在某种型式上可以看为defaultErrorCodeMessage即当前错误码对应的错误信息<br/>
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
    
    /** 展示错误信息 */
    private String errorMessage = "";
    
    /**
      * 获取系统错误编码<br/>
      *     errorMessage用于异常抛送到页面后，为用户显示错误
      *     因具体的系统使用者不用太过关心message(详细错误信息)
      *     如果非简体中文系统，则可以通过errorCode得到一个errorCode到errorMessage的映射
      *     以达到系统兼容未来多语言或多显示型式的方式
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getErrorCode() {
        String errorCode = doGetErrorCode();
        return errorCode;
    }
    
    /**
      * 获取系统错误编码<br/>
      *     errorMessage用于异常抛送到页面后，为用户显示错误
      *     因具体的系统使用者不用太过关心message(详细错误信息)
      *     如果非简体中文系统，则可以通过errorCode得到一个errorCode到errorMessage的映射
      *     以达到系统兼容未来多语言或多显示型式的方式
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract String doGetErrorCode();
    
    /**
      * 获取错误信息<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getErrorMessage() {
        String errorMessage = doGetErrorMessage();
        return errorMessage;
    }
    
    /**
      * 获取错误描述（展示）信息<br/>
      *     不需要太过详细，用户不用太关注系统内部的错误<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract String doGetErrorMessage();
    
    /** <默认构造函数> */
    public SILException(String errorMessage) {
        super(errorMessage);
        this.errorCode = getErrorCode();
        this.errorMessage = errorMessage;
    }
    
    /** <默认构造函数> */
    public SILException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = getErrorCode();
        this.errorMessage = errorMessage;
    }
    
    /**
     * <默认构造函数>
     */
    public SILException(String message, Object[] parameters) {
        super((parameters == null || parameters.length == 0) ? message
                : MessageFormatter.arrayFormat(message, parameters)
                        .getMessage());
        this.errorCode = getErrorCode();
        this.errorMessage = getErrorMessage();
    }
    
    /** <默认构造函数> */
    public SILException(String message, Object[] parameters, Throwable cause) {
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
        sb.append("SILException: ")
                .append(this.getClass().getName())
                .append("/n");
        sb.append("   errorCode: ").append(this.errorCode).append("/n");
        sb.append("   errorMessage: ").append(this.errorMessage).append("/n");
        sb.append("   message: ").append(super.getMessage()).append("/n");
        sb.append("   Exception toString: ").append(super.toString());
        return sb.toString();
    }
}
