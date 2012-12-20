/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-3
 * <修改描述:>
 */
package com.tx.core.exceptions.resource;

import com.tx.core.exceptions.ErrorCodeConstant;
import com.tx.core.exceptions.SILException;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-3]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ResourceLoadException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -1943248379116091306L;

    /**
     * <默认构造函数>
     */
    public ResourceLoadException(String errorMessage, Throwable cause,
            String... parameters) {
        super(ErrorCodeConstant.RESOURCE_LOAD_EXCEPTION, errorMessage, cause);
    }
    
    /**
     * <默认构造函数>
     */
    public ResourceLoadException(String errorMessage, String... parameters) {
        super(ErrorCodeConstant.RESOURCE_LOAD_EXCEPTION, errorMessage);
    }
    
}
