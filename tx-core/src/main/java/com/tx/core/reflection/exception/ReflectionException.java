/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.core.reflection.exception;

import com.tx.core.exceptions.SILException;


 /**
  *  类反射异常
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ReflectionException extends SILException{

    /** 注释内容 */
    private static final long serialVersionUID = 7007961386385237056L;

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "REFLECTION_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "系统内部错误";
    }

    /** <默认构造函数> */
    public ReflectionException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public ReflectionException(String message) {
        super(message);
    }
}
