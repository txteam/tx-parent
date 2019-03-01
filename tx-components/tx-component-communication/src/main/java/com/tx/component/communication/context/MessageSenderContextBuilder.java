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
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.OrderComparator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

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
        MessageSenderContextConfigurator implements BeanNameAware {
    
    /** bean名 */
    protected static String beanName;
    
    /** send处理器映射 */
    private MultiValueMap<String, MessageSendHandler> sendHandlerMap = new LinkedMultiValueMap<>();
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        MessageSenderContextBuilder.beanName = name;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected final void doBuild() throws Exception {
        logger.info("消息路由服务 配置容器启动...");
        Collection<MessageSendHandler> senders = applicationContext.getBeansOfType(MessageSendHandler.class)
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
        AssertUtils.notEmpty(message.getType(), "sendMessage.type is empty.");
        
        String messageType = message.getType().toUpperCase();
        SendResult result = null;
        MessageSendHandler sendHandler = null;
        for (MessageSendHandler sendHandlerTemp : this.sendHandlerMap.get(messageType)) {
            message.setType(messageType);//强制转换为大写字符
            if (sendHandlerTemp.supports(message)) {
                sendHandler = sendHandlerTemp;
                break;
            }
        }
        if (sendHandler == null) {
            result = new SendResult();
            result.setSuccess(false);
            result.setErrorMessage("没有匹配的消息发送处理器.");
            result.setErrorCode("404");
        } else {
            result = sendHandler.send(message);
        }
        return result;
    }
}
