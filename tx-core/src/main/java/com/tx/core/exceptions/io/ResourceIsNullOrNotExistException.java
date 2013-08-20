/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-20
 * <修改描述:>
 */
package com.tx.core.exceptions.io;

import org.springframework.core.io.Resource;


 /**
  * 访问资源部存在异常<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-8-20]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ResourceIsNullOrNotExistException extends ResourceAccessException{

    /** 注释内容 */
    private static final long serialVersionUID = 1553364691606122178L;

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "RESOURCE_NULL_OR_NOTEXIST_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "资源为空或不存在";
    }

    /** <默认构造函数> */
    public ResourceIsNullOrNotExistException(Resource resource, String message,
            Object[] parameters, Throwable cause) {
        super(resource, message, parameters, cause);
    }

    /** <默认构造函数> */
    public ResourceIsNullOrNotExistException(Resource resource, String message,
            Object[] parameters) {
        super(resource, message, parameters);
    }

    /** <默认构造函数> */
    public ResourceIsNullOrNotExistException(Resource resource, String errorMessage,
            Throwable cause) {
        super(resource, errorMessage, cause);
    }

    /** <默认构造函数> */
    public ResourceIsNullOrNotExistException(Resource resource, String errorMessage) {
        super(resource, errorMessage);
    }

    /** <默认构造函数> */
    public ResourceIsNullOrNotExistException(String message, Object[] parameters,
            Throwable cause) {
        super(message, parameters, cause);
    }

    /** <默认构造函数> */
    public ResourceIsNullOrNotExistException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public ResourceIsNullOrNotExistException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    /** <默认构造函数> */
    public ResourceIsNullOrNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
