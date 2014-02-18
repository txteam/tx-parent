/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.component.configuration.exception;

import com.tx.core.exceptions.SILException;


 /**
  * 不可编辑的异常 <br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-12-20]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class UnModifyAbleException extends SILException{

    /** 注释内容 */
    private static final long serialVersionUID = -6556434116335191367L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "UNSUPPORT_OPERATE_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "不支持的操作类型";
    }

    /** <默认构造函数> */
    public UnModifyAbleException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public UnModifyAbleException(String message) {
        super(message);
    }
    
    
    
}
