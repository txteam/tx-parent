/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-21
 * <修改描述:>
 */
package com.tx.core.dbscript.exception;

/**
 * 表不支持升级异常<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UnsupportAutoUpdateException extends TableUpdateException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7544636527204118709L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "UNSUPPORT_AUTO_UPDATE_ERROR";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "表不支持自动升级或尚未实现";
    }
    
    /** <默认构造函数> */
    public UnsupportAutoUpdateException(String tableName, String message,
            Object[] parameters) {
        super(tableName, message, parameters);
    }
    
    /** <默认构造函数> */
    public UnsupportAutoUpdateException(String tableName, String message,
            Throwable cause) {
        super(tableName, message, cause);
    }
    
    /** <默认构造函数> */
    public UnsupportAutoUpdateException(String tableName, String message) {
        super(tableName, message);
    }
}
