/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.core.exceptions.argument;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.helpers.MessageFormatter;

/**
 * 参数为空异常
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NullArgumentException extends IllegalArgumentException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7356506779676885246L;
    
    /**
     * 参数名数组
     */
    private String[] argumensNames;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "ARGUMENT_SHOULD_NOT_NULL_ERROR";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return ArrayUtils.isEmpty(argumensNames) ? "参数不能为空"
                : MessageFormatter.arrayFormat("参数：{}不能为空",
                        new Object[] { argumensNames }).getMessage();
    }
    
    /** <默认构造函数> */
    public NullArgumentException(String[] argumensNames,String message, 
            Object[] parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public NullArgumentException(String[] argumensNames,String message, 
            String... parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public NullArgumentException(String message, Object[] parameters,
            Throwable cause) {
        super(message, parameters, cause);
    }

    /** <默认构造函数> */
    public NullArgumentException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public NullArgumentException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    /** <默认构造函数> */
    public NullArgumentException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * @return 返回 argumensNames
     */
    public String[] getArgumensNames() {
        return argumensNames;
    }
    
    /**
     * @param 对argumensNames进行赋值
     */
    public void setArgumensNames(String[] argumensNames) {
        this.argumensNames = argumensNames;
    }
}
