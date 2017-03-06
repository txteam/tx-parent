/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.core.exceptions.argument;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;

/**
 * 参数为空异常
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ArgNullException extends ArgIllegalException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7356506779676885246L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.ARG_NULL_ERROR;
    }
    
    /** <默认构造函数> */
    public ArgNullException() {
        super();
    }
    
    /** <默认构造函数> */
    public ArgNullException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public ArgNullException(String message) {
        super(message);
    }
}
