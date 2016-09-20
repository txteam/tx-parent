/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.core.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import com.tx.core.TxConstants;

/**
 * 系统内部逻辑异常:System Inner Logic Exception 系统内部错误 errorMessage用于异常抛送到页面后，为用户显示错误 因具体的系统使用者不用太过关心message(详细错误信息) 如果非简体中文系统，则可以通过errorCode得到一个errorCode到errorMessage的映射 以达到系统兼容未来多语言或多显示型式的方式 errorMessage在某种型式上可以看为defaultErrorCodeMessage即当前错误码对应的错误信息<br/>
 * 系统内部逻辑异常封装理念： 1、告诉使用者的错误消息尽量精炼。 2、最终使用者不期望看到一堆错误堆栈，堆栈应当打到后台日志中，显示在前台的应该很简单的语言能够描述。 3、合法性的提示应该在客户提交以前，就以js的形式进行提示，纠正 4、如果遇到提交到后台才发现错误，错误反馈到前端，应该能容忍说明不清楚，这个应该算作BUG进行修正。 5、不应当客户提交信息后，才发现错误存在
 * 
 * @author PengQingyang
 * @version [版本号, 2012-10-14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SILException extends RuntimeException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4629630103815146373L;
    
    /** 错误编码 */
    private String errorCode = "";
    
    /** 展示错误信息：如果子实现没有覆写对应的get方法.那么getErrorMessage() = getMessage() */
    private String errorMessage = "";
    
    /**
     * 获取系统错误编码<br/>
     * errorMessage用于异常抛送到页面后，为用户显示错误 因具体的系统使用者不用太过关心message(详细错误信息) 如果非简体中文系统，则可以通过errorCode得到一个errorCode到errorMessage的映射 以达到系统兼容未来多语言或多显示型式的方式 <功能详细描述>
     * 
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
     * errorMessage用于异常抛送到页面后，为用户显示错误 因具体的系统使用者不用太过关心message(详细错误信息) 如果非简体中文系统，则可以通过errorCode得到一个errorCode到errorMessage的映射 以达到系统兼容未来多语言或多显示型式的方式
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String doGetErrorCode() {
        return "SYSTEM_INNER_LOGIC_ERROR";
    }
    
    /**
     * 获取错误信息<br/>
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getErrorMessage() {
        String errorMessage = this.errorMessage;
        return errorMessage;
    }
    
    /**
     * 获取错误描述（展示）信息<br/>
     * 不需要太过详细，用户不用太关注系统内部的错误<br/>
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String doGetErrorMessage() {
        return "";
    }
    
    public SILException(String message) {
        this(message, new Object[0]);
        this.errorCode = getErrorCode();
        this.errorMessage = !StringUtils.isEmpty(doGetErrorMessage()) ? doGetErrorMessage()
                : message;
    }
    
    public SILException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = getErrorCode();
        this.errorMessage = !StringUtils.isEmpty(doGetErrorMessage()) ? doGetErrorMessage()
                : message;
    }
    
    public SILException(String message, Object[] parameters) {
        super((parameters == null || parameters.length == 0) ? message
                : MessageFormatter.arrayFormat(message, parameters)
                        .getMessage());
        this.errorCode = getErrorCode();
        this.errorMessage = !StringUtils.isEmpty(doGetErrorMessage()) ? doGetErrorMessage()
                : message;
    }
    
    public SILException(String message, String... parameters) {
        this(message, (Object[]) parameters);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("SILException: ");
        sb.append(this.getClass().getName());
        sb.append("\n");
        sb.append("   errorCode: ").append(this.errorCode).append("\n");
        sb.append("   errorMessage: ").append(this.errorMessage).append("\n");
        sb.append("   message: ").append(super.getMessage()).append("\n");
        sb.append("   Exception toString: ").append(super.toString());
        return sb.toString();
    }
}
