/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-9-9
 * <修改描述:>
 */
package com.tx.core.exceptions.logic;

import com.tx.core.exceptions.SILException;


 /**
  * 不允许的操作异常<br/>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-9-9]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class NotAllowedOperateException extends SILException{

    /** 注释内容 */
    private static final long serialVersionUID = -6052707737840254112L;

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "NOT_ALLOWED_OPERATE_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "不被允许的操作";
    }

    /** <默认构造函数> */
    public NotAllowedOperateException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public NotAllowedOperateException(String message) {
        super(message);
    }
}
