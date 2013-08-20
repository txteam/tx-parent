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
public class DataSourceTypeUnSupportException extends SILException{
    
    /**
     * 指定的数据源类型<br/>
     */
    private DataSourceTypeEnum dataSourceType;

    private DataSourceTypeUnSupportException(String errorCode,
            String errorMessage, Object[] parameters) {
        super(errorCode, errorMessage, parameters);
        // TODO Auto-generated constructor stub
    }

    private DataSourceTypeUnSupportException(String errorCode,
            String errorMessage, String... parameters) {
        super(errorCode, errorMessage, parameters);
        // TODO Auto-generated constructor stub
    }

    private DataSourceTypeUnSupportException(String errorCode,
            String errorMessage, Throwable cause, Object[] parameters) {
        super(errorCode, errorMessage, cause, parameters);
        // TODO Auto-generated constructor stub
    }

    private DataSourceTypeUnSupportException(String errorCode,
            String errorMessage, Throwable cause, String... parameters) {
        super(errorCode, errorMessage, cause, parameters);
        // TODO Auto-generated constructor stub
    }
}
