/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月18日
 * <修改描述:>
 */
package com.tx.component.communication.dialect;

import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;

/**
 * 消息发送方言类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年12月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MessageSendDialect {
    
    /**
      * 消息发送器<br/>
      * <功能详细描述>
      * @param sendMessage [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public SendResult send(SendMessage message);
}