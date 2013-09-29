/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-29
 * <修改描述:>
 */
package com.tx.core.reflection.exception;

import com.tx.core.exceptions.SILException;


 /**
  * 自定义JPA注解解析异常<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-29]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class JpaMetaClassNewInstanceException extends SILException {

    /** 注释内容 */
    private static final long serialVersionUID = 4824999872795616238L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "JPAMETACLASS_NEWINSTANCE_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "JPA PARSE ERROR";
    }

    /** <默认构造函数> */
    public JpaMetaClassNewInstanceException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public JpaMetaClassNewInstanceException(String message) {
        super(message);
    }
}
