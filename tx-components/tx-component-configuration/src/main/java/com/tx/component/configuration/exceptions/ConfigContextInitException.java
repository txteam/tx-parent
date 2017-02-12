/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.configuration.exceptions;

import com.tx.core.exceptions.ErrorCode;

/**
 * 配置容器初始化异常<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-8-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContextInitException extends ConfigContextException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -995644958455158202L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return ConfigContextErrorCodeEnum.CONFIG_CONTEXT_INIT_ERROR;
    }
    
    /** <默认构造函数> */
    public ConfigContextInitException() {
        super();
    }
    
    /** <默认构造函数> */
    public ConfigContextInitException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public ConfigContextInitException(String message) {
        super(message);
    }
}
