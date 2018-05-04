/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月25日
 * <修改描述:>
 */
package com.tx.component.command.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.util.typereference.ParameterizedTypeReference;

/**
  * 操作请求支持器支持类<br/>
  * <功能详细描述>
  * 
  * @author  bobby
  * @version  [版本号, 2015年1月6日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public abstract class RequestSupport<PR extends CommandRequest, RECEIVER extends CommandReceiver<PR>>
        extends ParameterizedTypeReference<PR> {
    
    /** 日志记录器 */
    protected static final Logger logger = LoggerFactory.getLogger(RequestSupport.class);
    
    /** 请求处理器 */
    protected final RECEIVER receiver;
    
    /** <默认构造函数> */
    protected RequestSupport() {
        super();
        this.receiver = null;
    }
    
    /** <默认构造函数> */
    public RequestSupport(RECEIVER receiver) {
        super();
        this.receiver = receiver;
    }
    
    /**
      * 获取交易类型<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<? extends TradingRequest> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Class<? extends CommandRequest> getRequestType() {
        @SuppressWarnings("unchecked")
        Class<? extends CommandRequest> rawType = (Class<? extends CommandRequest>) getRawType();
        return rawType;
    }
    
    /**
      * 请求响应支撑类<br/>
      * <功能详细描述>
      * @param request
      * @return [参数说明]
      * 
      * @return ProcessResponse [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract void handle(PR request, CommandResponse response);
    
    /**
     * @return 返回 receiver
     */
    public CommandReceiver<PR> getReceiver() {
        return receiver;
    }
}
