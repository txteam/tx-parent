/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月10日
 * <修改描述:>
 */
package com.tx.core.exceptions.resource;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;



 /**
  * 资源解析异常
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年6月10日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ResourceParseException extends ResourceAccessException{
    
    /** 注释内容 */
    private static final long serialVersionUID = -6957893285239093580L;

    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.RESOURCE_NOT_EXSIT_ERROR;
    }

    /** <默认构造函数> */
    public ResourceParseException() {
        super();
    }

    /** <默认构造函数> */
    public ResourceParseException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public ResourceParseException(String message) {
        super(message);
    }
}
