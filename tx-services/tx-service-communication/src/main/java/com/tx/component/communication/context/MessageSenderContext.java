/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.communication.context;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 消息路由服务容器<br/>
 *    容器的封装理念：将消息发送，邮件，TTS，简单的email等的发送变为简单化<br/>
 *    用户可以:     send("{type:sms,receiver:1898337xxxx,1898377xxxx,message:xyz}");这样的格式实现内容发送
 *         或：         send("sms","1898337xxxx","xyz");
 *    容器中主要提供两种方法调用
 *                 send: 发送消息并返回发送错误代码，适用于发送错误后需要感知发送返回结果
 *                 sendWithodResult: 发送消息，如果错误则抛出预定异常，从异常中获取发送错误消息
 *    在使用过程中能够直接拷贝原存在的代码快速方便的实现消息发送
 * @author Rain.he
 * @version [版本号, 2015年11月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageSenderContext extends MessageSenderContextBuilder {
    
    /** 对自身的引用 */
    protected static MessageSenderContext context;
    
    /**
     * 返回自身唯一引用
     *
     * @return MRSContext 消息路由服务容器
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月12日]
     * @author rain
     */
    public static MessageSenderContext getContext() {
        if (MessageSenderContext.context != null) {
            return MessageSenderContext.context;
        }
        synchronized (MessageSenderContext.class) {
            MessageSenderContext.context = applicationContext.getBean(beanName,
                    MessageSenderContext.class);
        }
        AssertUtils.notNull(MessageSenderContext.context, "context is null.");
        
        return MessageSenderContext.context;
    }
    
    /**
      * 发送消息<br/>
      * <功能详细描述>
      * @param type
      * @param receivers
      * @param messageJsonString
      * @return [参数说明]
      * 
      * @return SendResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public SendResult send(String type, String receivers, String title,
            String content) {
        SendMessage message = new SendMessage(type, receivers, title, content);
        SendResult result = doSend(message);
        return result;
    }
    
    /**
     * 发送消息<br/>
     * <功能详细描述>
     * @param type
     * @param receivers
     * @param messageJsonString
     * @return [参数说明]
     * 
     * @return SendResult [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public SendResult send(String type, String receivers, String title,
            String content, Map<String, String> attributes) {
        SendMessage message = new SendMessage(type, receivers, title, content);
        if (!MapUtils.isEmpty(attributes)) {
            message.getAttributes().putAll(attributes);
        }
        SendResult result = doSend(message);
        return result;
    }
    
    /**
      * 消息发送<br/>
      * <功能详细描述>
      * @param message
      * @return [参数说明]
      * 
      * @return SendResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public SendResult send(SendMessage message) {
        SendResult result = doSend(message);
        return result;
    }
    
}
