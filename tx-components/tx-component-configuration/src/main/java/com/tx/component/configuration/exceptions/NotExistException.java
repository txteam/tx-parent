/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-17
 * <修改描述:>
 */
package com.tx.component.configuration.exceptions;

import com.tx.core.exceptions.ErrorCode;

/**
 * 配置属性不存在抛出的异常<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-2-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NotExistException extends ConfigAccessException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4273026101999032246L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return ConfigContextErrorCodeEnum.CONFIG_ITEM_NOT_EXIST_ERROR;
    }
    
    /** <默认构造函数> */
    public NotExistException() {
        super();
    }
    
    /** <默认构造函数> */
    public NotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public NotExistException(String message) {
        super(message);
    }
    
}
