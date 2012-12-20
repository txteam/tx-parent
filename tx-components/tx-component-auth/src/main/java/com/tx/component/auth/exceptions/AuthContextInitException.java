/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-14
 * <修改描述:>
 */
package com.tx.component.auth.exceptions;


 /**
  * 权限容器初始化异常<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class AuthContextInitException extends AuthContextException {

    /** 注释内容 */
    private static final long serialVersionUID = 7273081031541085969L;
    
    /**
     * <默认构造函数>
     */
    public AuthContextInitException(String errorMessage, String... parameters) {
        super(errorMessage, parameters);
    }
    
    /**
     * <默认构造函数>
     */
    public AuthContextInitException(String errorMessage, Throwable cause,
            String... parameters) {
        super(errorMessage, cause, parameters);
    }
}
