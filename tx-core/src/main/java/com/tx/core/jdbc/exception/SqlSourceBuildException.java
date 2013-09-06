/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc.exception;

import com.tx.core.exceptions.SILException;


 /**
  * sqlSource构建异常<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-6]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SqlSourceBuildException extends SILException{
    
    /** 注释内容 */
    private static final long serialVersionUID = -276409573213268060L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "BUILD_SQLSOURCE_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "sqlsource构建错误";
    }

    /** <默认构造函数> */
    public SqlSourceBuildException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public SqlSourceBuildException(String message) {
        super(message);
    }

    /** <默认构造函数> */
    public SqlSourceBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
