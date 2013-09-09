/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-9-9
 * <修改描述:>
 */
package com.tx.core.exceptions.logic;

import com.tx.core.exceptions.SILException;

/**
 * 无权限的访问异常<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-9-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NoAuthorityAccessException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5085628134273024411L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "NO_AUTHORITY_ACCESS_ERROR";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "无权限的访问";
    }
    
    /** <默认构造函数> */
    public NoAuthorityAccessException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public NoAuthorityAccessException(String message) {
        super(message);
    }
}
