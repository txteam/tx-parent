/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.config.exception;

import com.tx.core.exceptions.SILException;


 /**
  * 配置容器初始化异常<br/>
  * <功能详细描述>
  * 
  * @author  wanxin
  * @version  [版本号, 2013-8-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ConfigContextInitException extends SILException {

    /** 注释内容 */
    private static final long serialVersionUID = -995644958455158202L;
    
    /** 配置容器 */
    public static final String ERROR_CODE_CONFIG_CONTEXT_INIT = "configcontext_000_001";

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "CONFIGCONTEXT_INIT_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "权限容器初始化异常";
    }

    /** <默认构造函数> */
    public ConfigContextInitException(String message, Object... parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public ConfigContextInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
