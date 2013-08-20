/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.core.exceptions.argument;

import org.apache.commons.lang.StringUtils;
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
public class NullArgException extends IllegalArgException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7356506779676885246L;
    
    /**
     * 参数名数组
     */
    private String argumentName;
    
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
        return StringUtils.isBlank(argumentName) ? "参数不能为空"
                : MessageFormatter.arrayFormat("参数：{} 不能为空",
                        new Object[] { argumentName }).getMessage();
    }
    
    /** <默认构造函数> */
    public NullArgException(String argumentName) {
        super(argumentName + "不能为空");
        this.argumentName = argumentName;
    }

    /**
     * @return 返回 argumentName
     */
    public String getArgumentName() {
        return argumentName;
    }

    /**
     * @param 对argumentName进行赋值
     */
    public void setArgumentName(String argumentName) {
        this.argumentName = argumentName;
    }
}
