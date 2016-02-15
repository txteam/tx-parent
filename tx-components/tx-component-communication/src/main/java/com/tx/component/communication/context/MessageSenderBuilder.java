/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月31日
 * <修改描述:>
 */
package com.tx.component.communication.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.tx.component.communication.exception.MessageSenderContextInitException;


 /**
  * 消息发送构建器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2015年12月31日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class MessageSenderBuilder extends MessageSenderConfigurator {
    
    /** 请求器名称和接收器映射 */
    protected Map<Class<? extends MRSRequest>, MRSReceiver<? extends MRSRequest, ? extends MRSResponse>> request2Receiver = new HashMap<>();
    
    @SuppressWarnings({ "rawtypes" })
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("消息路由服务 配置容器启动...");
        
        // 加载消息路由服务接收器
        Map<String, MRSReceiver> receivers = applicationContext.getBeansOfType(MRSReceiver.class);
        if (MapUtils.isNotEmpty(receivers)) {
            for (MRSReceiver<?, ?> receiver : receivers.values()) {
                Class<? extends MRSRequest> requestType = receiver.getRequestType();
                if (this.request2Receiver.containsKey(requestType)) {
                    throw new MessageSenderContextInitException(
                            "存在相同的消息路由服务 : {}", receiver.getClass().getName());
                }
                this.request2Receiver.put(requestType, receiver);
                logger.info("加载消息路由服务 : {}[{}]",
                        receiver.getClass().getName(),
                        requestType.getName());
            }
        }
        
        logger.info("消息路由服务 配置容器启动完毕...");
    }
}
