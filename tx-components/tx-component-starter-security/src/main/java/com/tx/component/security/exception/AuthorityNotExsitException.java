/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.security.exception;

import com.tx.core.exceptions.ErrorCode;

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
public class AuthorityNotExsitException extends SecurityContextException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2814953773924819958L;
    
    /** 权限项key */
    private String authority;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SecurityContextErrorCodeEnum.AUTH_ITEM_NOT_EXIST_ERROR;
    }
    
    /** <默认构造函数> */
    public AuthorityNotExsitException(String authority) {
        super(authority + "权限项未定义");
        this.authority = authority;
    }
    
    /**
     * @return 返回 authority
     */
    public String getAuthority() {
        return authority;
    }
    
    /**
     * @param 对authority进行赋值
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
