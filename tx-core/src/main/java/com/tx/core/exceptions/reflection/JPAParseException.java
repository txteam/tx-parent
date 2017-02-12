/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-29
 * <修改描述:>
 */
package com.tx.core.exceptions.reflection;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;

/**
 * 自定义JPA注解解析异常<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JPAParseException extends ReflectionException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4824999872795616238L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.JPA_META_CLASS_NEW_INSTANCE_ERROR;
    }
    
    /** <默认构造函数> */
    public JPAParseException() {
        super();
    }
    
    /** <默认构造函数> */
    public JPAParseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public JPAParseException(String message) {
        super(message);
    }
}
