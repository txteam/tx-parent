/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-20
 * <修改描述:>
 */
package com.tx.core.exceptions.resource;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;

/**
 * 访问资源部存在异常<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResourceIsExistException extends ResourceAccessException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 1553364691606122178L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.RESOURCE_IS_EXSIT_ERROR;
    }
    
    /** <默认构造函数> */
    public ResourceIsExistException() {
        super();
    }
    
    /** <默认构造函数> */
    public ResourceIsExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public ResourceIsExistException(String message) {
        super(message);
    }
    
}
