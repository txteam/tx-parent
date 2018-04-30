/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月19日
 * 项目： com.tx.tsc
 */
package com.tx.component.communication.context;

import java.util.Map;

import com.tx.component.communication.model.SendResult;

/**
 * 消息路由服务拦截器
 * 
 * @author rain
 * @version [版本号, 2015年11月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface MessageSenderInterceptor{
    
    /**
      * 处理器的前置处理方法<br/> 
      *<功能详细描述>
      * @param params
      * @param handler
      * @return
      * @throws Exception [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean preHandle(Map<String, Object> params, Object handler)
            throws Exception;
    
    /**
      * 消息发送处理器<br/>
      *     调用发生于Send调用之后<br/>
      * <功能详细描述>
      * @param params
      * @param handler
      * @param sendResult
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void postHandle(Map<String, Object> params, Object handler,
            SendResult sendResult) throws Exception;
    
    /**
      * 消息后置处理器<br/>
      * <功能详细描述>
      * @param params
      * @param handler
      * @param sendResult
      * @param ex
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void afterCompletion(Map<String, Object> params, Object handler,
            SendResult sendResult, Exception ex) throws Exception;
    
}
