/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.messagerouter.context;

import org.apache.ibatis.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.messagerouter.exception.MRSException;

/**
 * 消息路由服务接收器默认实现
 * 
 * @author rain
 * @version [版本号, 2015年11月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class AbstractMRSReceiver<Request extends MRSRequest, Response extends MRSResponse>
        extends TypeReference<Request> implements MRSReceiver<Request, Response> {
        
    /** 日志 */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    /** 返回值_请求花费时间(单位:毫秒) */
    protected final String RESPONSE_VALUE_USE_TIME = "response_value:use_time";
    
    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Request> getRequestType() {
        return (Class<? extends Request>) getRawType();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void handle(MRSRequest request, MRSResponse response) {
        Request rq = (Request) request;
        Response rp = (Response) response;
        MRSInterceptor interceptor = request.getInterceptor();
        
        long starttime = System.currentTimeMillis();
        
        // 校验数据
        checkRequest(rq);
        Exception exception = null;
        try {
            beforeHandle(rq, rp);// 前置处理
            if (interceptor != null) {
                interceptor.logBeforeHandle(rq, rp);
            }
            doHandle(rq, rp);// 消息处理
            afterHandle(rq, rp);// 后置处理
            if (interceptor != null) {
                interceptor.logAfterHandle(rq, rp);
            }
        } catch (Exception e) {
            exception = e;
            logger.error("消息处理异常 : " + e.getMessage(), e);
            throw new MRSException("消息处理异常 : " + e.getMessage(), e);
        } finally {
            response.put(RESPONSE_VALUE_USE_TIME, System.currentTimeMillis() - starttime);
            // 记录日志
            logHandle(rq, rp, exception);
        }
    }
    
    /**
     * 后置处理<br />
     * 在消息发送后执行
     * 
     * @param request 操作请求器
     * @param response 操作返回对象
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void afterHandle(Request request, Response response) {
    }
    
    /**
     * 前置处理<br />
     * 在消息发送前执行
     * 
     * @param request 操作请求器
     * @param response 操作返回对象
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void beforeHandle(Request request, Response response) {
    }
    
    /**
     * 
     * 请求数据校验<br />
     * 发现数据异常,抛出异常
     *
     * @param request 请求器
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    protected abstract void checkRequest(Request request);
    
    /**
     * 
     * 消息处理
     *
     * @param request 操作请求器
     * @param response 操作返回对象
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    protected abstract void doHandle(Request request, Response response);
    
    /**
     * 
     * 日志处理
     *
     * @param request 操作请求器
     * @param response 操作返回对象
     * @param throwable 异常信息
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    protected void logHandle(Request request, Response response, Throwable throwable) {
    }
}
