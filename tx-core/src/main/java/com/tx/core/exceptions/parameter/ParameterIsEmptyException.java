/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.core.exceptions.parameter;

import org.slf4j.helpers.MessageFormatter;

import com.tx.core.exceptions.SILException;

/**
 * 参数为空异常
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Deprecated
public class ParameterIsEmptyException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7356506779676885246L;
    
    /**
     * <默认构造函数>
     */
    public ParameterIsEmptyException(String errorMessage, Throwable cause,
            String... parameters) {
        super(MessageFormatter.arrayFormat(errorMessage, parameters)
                .getMessage(), cause);
    }
    
    /**
     * <默认构造函数>
     */
    public ParameterIsEmptyException(String errorMessage, String... parameters) {
        super(errorMessage, parameters);
    }
    
    /**
     * <默认构造函数>
     */
    public ParameterIsEmptyException(String errorMessage, Throwable cause,
            Object[] parameters) {
        super(MessageFormatter.arrayFormat(errorMessage, parameters)
                .getMessage(), cause);
    }
    
    /**
     * <默认构造函数>
     */
    public ParameterIsEmptyException(String errorMessage, Object[] parameters) {
        super(errorMessage, parameters);
    }
    
}
