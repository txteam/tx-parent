/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月29日
 * <修改描述:>
 */
package com.tx.core.method.exceptions;

import org.springframework.validation.BindingResult;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;

/**
 * 方法注入：方法调用访问异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodArgResolveBindException extends MethodArgResolveException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4005784286230689683L;
    
    private final BindingResult bindingResult;

    /** <默认构造函数> */
    public MethodArgResolveBindException() {
        super();
        this.bindingResult = null;
    }
    
    /** <默认构造函数> */
    public MethodArgResolveBindException(BindingResult bindingResult) {
        super();
        this.bindingResult = bindingResult;
    }
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.METHOD_INVOKE_ARG_RESOLVE_BIND_ERROR;
    }

    /**
     * @return 返回 bindingResult
     */
    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
