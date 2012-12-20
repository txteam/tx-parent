/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-13
 * <修改描述:>
 */
package com.tx.component.auth.exceptions;

import com.tx.core.exceptions.ErrorCodeConstant;
import com.tx.core.exceptions.SILException;


 /**
  * 权限容器异常基类<br/>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-13]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class AuthContextException extends SILException{
    
    /** 注释内容 */
    private static final long serialVersionUID = -6954158210377121688L;

    /**
     * <默认构造函数>
     */
    public AuthContextException(String errorMessage, Throwable cause,
            String... parameters) {
        super(ErrorCodeConstant.AUTH_CONTEXT_EXCEPTION, errorMessage, cause,
                parameters);
    }
    
    /**
     * <默认构造函数>
     */
    public AuthContextException(String errorMessage, String... parameters) {
        super(ErrorCodeConstant.AUTH_CONTEXT_EXCEPTION, errorMessage, parameters);
    }
}
