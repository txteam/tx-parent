/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.core.exceptions.logic;

import com.tx.core.exceptions.SILException;


 /**
  * 克隆对象异常
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-10]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class CloneException extends SILException {

    /** 注释内容 */
    private static final long serialVersionUID = 4325246483458715882L;

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "CLONE_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "克隆对象异常.";
    }

    /** <默认构造函数> */
    public CloneException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public CloneException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public CloneException(String message) {
        super(message);
    }
    
}
