/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-3
 * <修改描述:>
 */
package com.tx.core.exceptions.resource;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;
import com.tx.core.exceptions.SILException;

/**
 * 资源访问异常<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResourceAccessException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -1943248379116091306L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.RESOURCE_ACCESS_ERROR;
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
    public ResourceAccessException() {
        super();
    }
    
    /** <默认构造函数> */
    public ResourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public ResourceAccessException(String message) {
        super(message);
    }
}
