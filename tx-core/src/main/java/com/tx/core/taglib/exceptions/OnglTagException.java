/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-5
 * <修改描述:>
 */
package com.tx.core.taglib.exceptions;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class OnglTagException extends TxTagException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2546447397281801588L;

    /** <默认构造函数> */
    public OnglTagException(String errorMessage, Throwable cause,
            String... parameters) {
        super(errorMessage, cause, parameters);
        // TODO Auto-generated constructor stub
    }
    
    /** <默认构造函数> */
    public OnglTagException(String errorMessage, String... parameters) {
        super(errorMessage, parameters);
        // TODO Auto-generated constructor stub
    }
    
}
