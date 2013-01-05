/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-5
 * <修改描述:>
 */
package com.tx.core.taglib.exceptions;

import com.tx.core.exceptions.ErrorCodeConstant;
import com.tx.core.exceptions.SILException;


 /**
  * 自定义标签异常
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TxTagException extends SILException {
    
    
    /** 注释内容 */
    private static final long serialVersionUID = 2624745011183986205L;

    /**
     * <默认构造函数>
     */
    public TxTagException(String errorMessage, Throwable cause,
            String... parameters) {
        super(ErrorCodeConstant.PARAMETER_IS_EMPTY, errorMessage, cause,
                parameters);
    }
    
    /**
     * <默认构造函数>
     */
    public TxTagException(String errorMessage, String... parameters) {
        super(ErrorCodeConstant.PARAMETER_IS_EMPTY, errorMessage, parameters);
    }
}
