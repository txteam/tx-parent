/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.auth.exceptions;

import com.tx.core.exceptions.SILException;

/**
 * 权限定义不存在异常<br/>
 *     调用权限容器，检测权限引用是否存在时<br/>
 *     如果对应的权限定义不存在，则抛出该异常<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthDefinitionNotExsitException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2814953773924819958L;
    
    private String authKey;

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "AUTH_KEY_NOT_EXSIT_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "权限未定义";
    }
    
    /** <默认构造函数> */
    public AuthDefinitionNotExsitException(String authKey) {
        super(authKey + "权限未定义");
        this.authKey = authKey;
    }

    /**
     * @return 返回 authKey
     */
    public String getAuthKey() {
        return authKey;
    }

    /**
     * @param 对authKey进行赋值
     */
    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
