/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-27
 * <修改描述:>
 */
package com.tx.core.exceptions.reflection;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;

/**
 * 非法的Set方法
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class InvalidSetterMethodException extends ReflectionException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -4601581578924296318L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.INVALID_SETTER_METHOD_ERROR;
    }
    
    /** <默认构造函数> */
    public InvalidSetterMethodException() {
        super();
    }
    
    /** <默认构造函数> */
    public InvalidSetterMethodException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public InvalidSetterMethodException(String message) {
        super(message);
    }
    
}
