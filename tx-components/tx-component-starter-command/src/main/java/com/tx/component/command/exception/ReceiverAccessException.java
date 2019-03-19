/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月14日
 * <修改描述:>
 */
package com.tx.component.command.exception;

import com.tx.core.exceptions.SILException;

/**
 * 处理器访问异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年1月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReceiverAccessException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 156339780664372635L;
    
    /** <默认构造函数> */
    public ReceiverAccessException() {
        super();
    }
    
    /** <默认构造函数> */
    public ReceiverAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public ReceiverAccessException(String message) {
        super(message);
    }
}
