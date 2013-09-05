/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.exceptions.logic;

import com.tx.core.exceptions.SILException;


 /**
  * 不期望的调用<br/>
  *     常用于不期望调用到的业务逻辑，如果一旦调用到则抛出该异常<br/>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-9-6]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class UnExpectCallException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 1854863970816482414L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "UN_EXPECT_CALL_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "系统内部错误";
    }

    /** <默认构造函数> */
    public UnExpectCallException(String message) {
        super(message);
    }
    
    /** <默认构造函数> */
    public UnExpectCallException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
}
