/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-10-1
 * <修改描述:>
 */
package com.tx.core.exceptions.logic;

import com.tx.core.exceptions.SILException;


 /**
  * 不支持的操作异常
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-10-1]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class UnsupportedOperationException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6999147615584234159L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "UNSUPPORTED_OPERATION_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "Unsupported Operation";
    }

    /** <默认构造函数> */
    public UnsupportedOperationException(String message) {
        super(message);
    }
    
    /** <默认构造函数> */
    public UnsupportedOperationException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
}
