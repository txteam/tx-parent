/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-20
 * <修改描述:>
 */
package com.tx.core.exceptions.argument;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;
import com.tx.core.exceptions.SILException;

/**
 * 参数非法异常<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ArgIllegalException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 533782652240275430L;
    
    /** 参数名 */
    private String argName;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.ARG_ILLEGAL_ERROR;
    }
    
    /**
     * @return
     */
    @Override
    protected final Integer errorCode() {
        return super.errorCode();
    }
    
    /**
     * @return
     */
    @Override
    protected final String errorMessage() {
        return super.errorMessage();
    }
    
    /** <默认构造函数> */
    public ArgIllegalException() {
        super();
    }
    
    /** <默认构造函数> */
    public ArgIllegalException(String message) {
        super(message);
    }
    
    /** <默认构造函数> */
    public ArgIllegalException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * @return 返回 argName
     */
    public String getArgName() {
        return argName;
    }
    
    /**
     * @param 对argName进行赋值
     */
    public void setArgName(String argName) {
        this.argName = argName;
    }
}
