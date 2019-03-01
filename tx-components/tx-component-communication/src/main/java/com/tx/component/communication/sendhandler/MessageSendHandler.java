/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月18日
 * <修改描述:>
 */
package com.tx.component.communication.sendhandler;

import org.springframework.core.Ordered;

import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;

/**
 * 消息发送器执行器<br/>
 *     该消息发送器执行器，去调用消息发送方言，进行消息发送<br/>
 *     该执行器中，主要根据传入版本，组装发送报送的内容<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年12月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MessageSendHandler extends Ordered {
    
    /**
      * 消息发送处理器对应的消息类型<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String type();
    
    /**
      * 是否匹配消息发送的处理器<br/>
      * <功能详细描述>
      * @param message
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean supports(SendMessage message);
    
    /**
      * 消息发送处理器<br/>
      * <功能详细描述>
      * @param message
      * @return [参数说明]
      * 
      * @return SendResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public SendResult send(SendMessage message);
}
