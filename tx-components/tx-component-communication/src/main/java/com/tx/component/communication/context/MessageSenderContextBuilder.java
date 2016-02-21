/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月31日
 * <修改描述:>
 */
package com.tx.component.communication.context;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.OrderComparator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;
import com.tx.component.communication.sendhandler.MessageSendHandler;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 消息发送构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年12月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MessageSenderContextBuilder extends
        MessageSenderContextConfigurator {
    
    private MultiValueMap<String, MessageSendHandler> sendHandlerMap = new LinkedMultiValueMap<>();
    
    /**
     * @throws Exception
     */
    @Override
    protected final void initMessageSenderContext() throws Exception {
        logger.info("消息路由服务 配置容器启动...");
        //调用初始化容器
        initContext();
        
        Collection<MessageSendHandler> senders = this.applicationContext.getBeansOfType(MessageSendHandler.class)
                .values();
        if (!CollectionUtils.isEmpty(senders)) {
            for (MessageSendHandler senderTemp : senders) {
                String type = senderTemp.type();
                if (StringUtils.isEmpty(type)) {
                    return;
                }
                this.sendHandlerMap.add(type.toUpperCase(), senderTemp);
            }
        }
        
        //将sender按照优先级进行排序
        for (List<MessageSendHandler> handlerListTemp : this.sendHandlerMap.values()) {
            Collections.sort(handlerListTemp, OrderComparator.INSTANCE);
        }
        logger.info("消息路由服务 配置容器启动完毕...");
    }
    
    /**
      * 提供给子类初始化容器时使用<br/>
      * <功能详细描述>
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void initContext() throws Exception {
    }
    
    /**
      * 消息发送实现类<br/>
      * <功能详细描述>
      * @param sendMessage [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected SendResult doSend(SendMessage message) {
        AssertUtils.notNull(message, "message is null.");
        AssertUtils.notEmpty(message.getType(),
                "sendMessage.type is empty.");
        
        String messageType = message.getType().toUpperCase();
        SendResult result = null;
        for(MessageSendHandler sendHandlerTemp : this.sendHandlerMap.get(messageType)){
            message.setType(messageType);//强制转换为大写字符
            if(sendHandlerTemp.supports(message)){
                result = sendHandlerTemp.send(message);
                break;
            }
        }
        return result;
    }
}
