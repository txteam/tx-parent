/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月18日
 * <修改描述:>
 */
package com.tx.component.messagesender.dialect;

import java.util.Map;

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
    
    public Map<String, String> getErrorCode2MessageMap();
    
    public String getErrorMessageFromCode(String errorCode);
}
