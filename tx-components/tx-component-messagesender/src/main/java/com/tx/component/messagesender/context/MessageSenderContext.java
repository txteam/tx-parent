/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.messagesender.context;

import org.springframework.beans.factory.InitializingBean;

import com.taobao.api.internal.tmc.Message;
import com.tx.component.messagesender.model.SendResult;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 消息路由服务容器
 * 
 * @author Rain.he
 * @version [版本号, 2015年11月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageSenderContext extends MessageSenderConfigurator implements
        InitializingBean {
    
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
            return context;
        }
        synchronized (MessageSenderContext.class) {
            MessageSenderContext.context = (MessageSenderContext) applicationContext.getBean(beanName);
        }
        AssertUtils.notNull(context, "MRSContext is null. maybe not inited!");
        return MessageSenderContext.context;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("消息路由服务容器启动...");
        super.afterPropertiesSet();
        context = this;
        logger.info("消息路由服务容器启动完毕...");
    }
    
    public SendResult send(Message message) {
        return null;
    }
    
    public SendResult send(String type, String receivers,
            String messageJsonString) {
        
        return null;
    }
    
    //    /**
    //     * 
    //     * 发送消息数据
    //     *
    //     * @param data 消息数据
    //     *            
    //     * @return void [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     * @version [版本号, 2015年11月12日]
    //     * @author rain
    //     */
    //    @Transactional
    //    public MRSResponse post(MRSRequest request) {
    //        AssertUtils.notNull(request, "request is null!");
    //        Class<? extends MRSRequest> clazz = request.getClass();
    //        AssertUtils.isTrue(request2Receiver.containsKey(clazz), "消息路由服务请求器对应的接收器不存在! : {}", clazz.getName());
    //        
    //        MRSReceiver<? extends MRSRequest, ? extends MRSResponse> receiver = request2Receiver.get(request.getClass());
    //        MRSResponse response = request.buildEmptyResponse();
    //        if (response == null) {
    //            throw new MRSException("response is null!");
    //        }
    //        
    //        receiver.handle(request, response);
    //        return response;
    //    }
}
