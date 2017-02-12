/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月13日
 * <修改描述:>
 */
package com.tx.core.exceptions.resource;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;


 /**
  * 写入Excel信息错误<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年5月13日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ResourceWriteException extends ResourceAccessException{

    /** 注释内容 */
    private static final long serialVersionUID = 4713438423383887241L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.RESOURCE_WRITE_ERROR;
    }

    /** <默认构造函数> */
    public ResourceWriteException() {
        super();
    }

    /** <默认构造函数> */
    public ResourceWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public ResourceWriteException(String message) {
        super(message);
    }
}
