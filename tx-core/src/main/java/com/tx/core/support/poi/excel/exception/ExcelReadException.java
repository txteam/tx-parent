/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月13日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.exception;

import com.tx.core.exceptions.SILException;


 /**
  * excel解析异常
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年5月13日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ExcelReadException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7506398580147411100L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "EXCEL_PARSE_ERROR";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "excel解析错误";
    }
    
    /** <默认构造函数> */
    public ExcelReadException(String message) {
        super(message);
    }

    /** <默认构造函数> */
    public ExcelReadException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public ExcelReadException(String message, Object[] parameters) {
        super(message, parameters);
    }
}
