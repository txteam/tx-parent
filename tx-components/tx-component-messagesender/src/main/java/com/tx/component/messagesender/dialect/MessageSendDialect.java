/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月18日
 * <修改描述:>
 */
package com.tx.component.messagesender.dialect;

import java.util.Locale;
import java.util.Map;

import com.tx.component.messagesender.model.SendMessage;

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
      * 获取错误消息与错误编码之间的映射<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, String> getErrorCode2MessageMap();
    
    /**
      * 根据错误编码获取对应的错误消息<br/>
      * <功能详细描述>
      * @param errorCode
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getErrorMessageFromCode(String errorCode);
    
    /** 
     * 获取错误消息与错误编码之间的映射<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public Map<String, String> getErrorCode2MessageMap(Locale locale);
    
    /**
     * 根据错误编码获取对应的错误消息<br/>
     * <功能详细描述>
     * @param errorCode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public String getErrorMessageFromCode(Locale locale, String errorCode);
    
    /**
      * 消息发送器<br/>
      * <功能详细描述>
      * @param sendMessage [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void send(SendMessage message);
}
