/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年7月2日
 * <修改描述:>
 */
package com.tx.core.exceptions.remote;

/**
 * http请求发送前异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年7月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AfterHttpExcuteException extends HttpExecuteException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7590087272631996759L;
    
    /** <默认构造函数> */
    public AfterHttpExcuteException(String message, Throwable cause) {
        super(true, message, cause);
    }
}
