/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-3
 * <修改描述:>
 */
package com.tx.core.exceptions.io;

import org.springframework.core.io.Resource;

import com.tx.core.exceptions.SILException;

/**
 * 资源访问异常<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResourceAccessException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -1943248379116091306L;
    
    private Resource resource;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "RESOURCE_ACCESS_ERROR";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "资源访问错误";
    }
    
    /** <默认构造函数> */
    public ResourceAccessException(Resource resource, String message) {
        this(message);
        this.resource = resource;
    }
    
    /** <默认构造函数> */
    public ResourceAccessException(Resource resource, String message,
            Object[] parameters) {
        this(message, parameters);
        this.resource = resource;
    }
    
    /** <默认构造函数> */
    public ResourceAccessException(Resource resource, String message,Throwable cause) {
        this(message);
        this.resource = resource;
    }

    /** <默认构造函数> */
    public ResourceAccessException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public ResourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public ResourceAccessException(String message) {
        super(message);
    }

    /**
     * @return 返回 resource
     */
    public Resource getResource() {
        return resource;
    }
    
    /**
     * @param 对resource进行赋值
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
