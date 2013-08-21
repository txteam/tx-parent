/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-21
 * <修改描述:>
 */
package com.tx.core.dbscript.exception;

import org.apache.commons.lang.StringUtils;

import com.tx.core.exceptions.SILException;

/**
 * 表升级异常
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TableUpdateException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -1581923617845550399L;
    
    private String tableName;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "TABLE_UPDATE_ERROR";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return StringUtils.isEmpty(tableName) ? "表升级异常" : this.tableName
                + "表升级异常";
    }
    
    /** <默认构造函数> */
    public TableUpdateException(String tableName, String message) {
        super(message);
        this.tableName = tableName;
    }
    
    /** <默认构造函数> */
    public TableUpdateException(String tableName, String message,
            Throwable cause) {
        super(message, cause);
        this.tableName = tableName;
    }
    
    /** <默认构造函数> */
    public TableUpdateException(String tableName, String message,
            Object[] parameters) {
        super(message, parameters);
        this.tableName = tableName;
    }
    
}
