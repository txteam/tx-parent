/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.core.exceptions.parameter;

import com.tx.core.exceptions.ErrorCodeConstant;
import com.tx.core.exceptions.SILException;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ParameterIsEmptyException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7356506779676885246L;
    
    /**
     * <默认构造函数>
     */
    public ParameterIsEmptyException(String errorMessage, Throwable cause,
            String... parameters) {
        super(ErrorCodeConstant.PARAMETER_IS_EMPTY, errorMessage, cause,
                parameters);
    }
    
    /**
     * <默认构造函数>
     */
    public ParameterIsEmptyException(String errorMessage, String... parameters) {
        super(ErrorCodeConstant.PARAMETER_IS_EMPTY, errorMessage, parameters);
    }
}
