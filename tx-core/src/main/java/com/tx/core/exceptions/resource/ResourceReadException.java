/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月13日
 * <修改描述:>
 */
package com.tx.core.exceptions.resource;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;
import com.tx.core.exceptions.SILException;


 /**
  * excel解析异常
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年5月13日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ResourceReadException extends ResourceAccessException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7506398580147411100L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.RESOURCE_READ_ERROR;
    }

    /** <默认构造函数> */
    public ResourceReadException() {
        super();
    }

    /** <默认构造函数> */
    public ResourceReadException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public ResourceReadException(String message) {
        super(message);
    }
}
