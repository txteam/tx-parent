/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-4
 * <修改描述:>
 */
package com.tx.core.exceptions.parameter;

import com.tx.core.exceptions.ErrorCodeConstant;
import com.tx.core.exceptions.SILException;

/**
 * <参数无效异常>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ParameterIsInvalidException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5076881251815083398L;
    
    /** <默认构造函数> */
    public ParameterIsInvalidException(String errorMessage,
            String... parameters) {
        super(ErrorCodeConstant.PARAMETER_IS_INVALID, errorMessage, parameters);
    }
    
    /** <默认构造函数> */
    public ParameterIsInvalidException(String errorMessage, Throwable cause,
            String... parameters) {
        super(ErrorCodeConstant.PARAMETER_IS_INVALID, errorMessage, cause,
                parameters);
    }
    
}
