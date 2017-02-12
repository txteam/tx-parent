/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.core.exceptions;

import com.tx.core.TxConstants;

/**
 * 系统内部逻辑异常:System Inner Logic Exception <br/>
 *      系统内部错误 errorMessage用于异常抛送到页面后，为用户显示错误 因具体的系统使用者不用太过关心message(详细错误信息) <br/>
 *      如果非简体中文系统,则可以通过errorCode得到一个errorCode到errorMessage的映射 <br/>
 *      以达到系统兼容未来多语言或多显示型式的方式 errorMessage<br/>
 * 系统内部逻辑异常封装理念： <br/>
 *      1、告诉使用者的错误消息尽量精炼。 <br/>
 *      2、最终使用者不期望看到一堆错误堆栈，堆栈应当打到后台日志中，显示在前台的应该很简单的语言能够描述。 <br/>
 *      3、合法性的提示应该在客户提交以前，就以js的形式进行提示，纠正 <br/>
 *      4、如果遇到提交到后台才发现错误，错误反馈到前端，应该能容忍说明不清楚，这个应该算作BUG进行修正。 <br/>
 *      5、不应当客户提交信息后，才发现错误存在<br/>
 * @author PengQingyang
 * @version [版本号, 2012-10-14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SILException extends RuntimeException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4629630103815146373L;
    
    /** 注册错误编码 */
    protected static final void registeErrorCode(int errorCode,
            String errorMessage) {
        ErrorCodeRegistry.INSTANCE.registeErrorCode(errorCode, errorMessage);
    }
    
    /** 错误 */
    protected ErrorCode error() {
        return null;
    }
    
    /** 错误编码 */
    protected Integer errorCode() {
        return error() == null ? null : error().getCode();
    }
    
    /** 错误消息 */
    protected String errorMessage() {
        return error() == null ? null : error().getMessage();
    }
    
    /** 错误编码: errorCode */
    private int errorCode = -1;
    
    /**
      * 设置错误编码<br/>
      * <功能详细描述>
      * @param errorCode [参数说明]
      * 
      * @return void [返回类型说明]
      * 
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public final void setErrorCode(int errorCode) {
        if (this.error() != null || this.errorCode() < 0) {
            //当子类覆写了error()方法，或errorCode()方法时.setErrorCode方法将会失效<br/>
            return;
        }
        this.errorCode = errorCode;
    }
    
    /**
     * 获取系统错误编码<br/>
     *      errorMessage用于异常抛送到页面后
     *      为用户显示错误 因具体的系统使用者不用太过关心message(详细错误信息) 如果非简体中文系统
     *      则可以通过errorCode得到一个errorCode到errorMessage的映射 以达到系统兼容未来多语言或多显示型式的方式 <功能详细描述>
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public final int getErrorCode() {
        if (this.error() != null) {
            return this.error().getCode();
        } else if (this.errorCode() >= 0) {
            return this.errorCode();
        } else {
            return this.errorCode;
        }
    }
    
    /**
     * 获取系统错误编码<br/>
     *      errorMessage用于异常抛送到页面后<br/>
     *      为用户显示错误 因具体的系统使用者不用太过关心message(详细错误信息) 如果非简体中文系统<br/>
     *      则可以通过errorCode得到一个errorCode到errorMessage的映射 以达到系统兼容未来多语言或多显示型式的方式 <功能详细描述><br/>
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public final String getErrorMessage() {
        String errorMessage = ErrorCodeRegistry.INSTANCE.getErrorMessage(this.getErrorCode());
        return errorMessage;
    }
    
    /** <默认构造函数> */
    public SILException() {
        super();
    }
    
    /** SILException构造函数 */
    public SILException(String message) {
        super(message);
    }
    
    /** SILException构造函数 */
    public SILException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * toString方法 
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("SILException: ");
        sb.append(this.getClass().getName());
        sb.append("\n");
        sb.append("\t errorCode: ").append(this.getErrorCode()).append("\n");
        sb.append("\t errorMessage: ")
                .append(this.getErrorMessage())
                .append("\n");
        sb.append("\t message: ").append(super.getMessage()).append("\n");
        sb.append("\t cause: ").append(super.getCause() == null ? ""
                : super.getCause().toString());
        return sb.toString();
    }
}
