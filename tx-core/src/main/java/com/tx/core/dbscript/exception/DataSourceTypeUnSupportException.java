/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-19
 * <修改描述:>
 */
package com.tx.core.dbscript.exception;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.SILException;

/**
 * 不支持的数据源类型异常
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataSourceTypeUnSupportException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 5983718256413464840L;
    
    /**
     * 指定的数据源类型<br/>
     */
    private DataSourceTypeEnum dataSourceType;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "DATASOURCETYPE_UNSUPPORT_ERROR";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "不支持的数据源类型";
    }
    
    /** <默认构造函数> */
    public DataSourceTypeUnSupportException(DataSourceTypeEnum dataSourceType) {
        this("不支持的数据源类型:" + dataSourceType);
        this.dataSourceType = dataSourceType;
    }
    
    /** <默认构造函数> */
    public DataSourceTypeUnSupportException(DataSourceTypeEnum dataSourceType,
            String message, Object[] parameters) {
        this(message, parameters);
        this.dataSourceType = dataSourceType;
    }
    
    /** <默认构造函数> */
    public DataSourceTypeUnSupportException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public DataSourceTypeUnSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public DataSourceTypeUnSupportException(String message) {
        super(message);
    }

    /**
     * @return 返回 dataSourceType
     */
    public DataSourceTypeEnum getDataSourceType() {
        return dataSourceType;
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
}
