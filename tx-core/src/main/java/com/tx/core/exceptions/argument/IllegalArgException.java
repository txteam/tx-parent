/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-20
 * <修改描述:>
 */
package com.tx.core.exceptions.argument;

import com.tx.core.exceptions.SILException;


 /**
  * 参数非法异常<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-8-20]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class IllegalArgException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 533782652240275430L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "ARGUMENT_ILLEGAL_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "参数非法错误";
    }

    /** <默认构造函数> */
    public IllegalArgException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public IllegalArgException(String message) {
        super(message);
    }

    /** <默认构造函数> */
    public IllegalArgException(String message, Throwable cause) {
        super(message, cause);
    }
}
