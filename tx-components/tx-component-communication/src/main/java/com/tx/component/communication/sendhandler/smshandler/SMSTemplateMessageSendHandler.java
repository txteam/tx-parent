/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月31日
 * <修改描述:>
 */
package com.tx.component.communication.sendhandler.smshandler;

import com.tx.component.communication.MessageSenderConstants;
import com.tx.component.communication.sendhandler.AbstractMessageSendHandler;

/**
 * 抽象消息发送处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年12月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SMSTemplateMessageSendHandler extends AbstractMessageSendHandler {
    
    /**
     * @return
     */
    @Override
    public String type() {
        return MessageSenderConstants.MESSAGE_TYPE_SMS;
    }
}
