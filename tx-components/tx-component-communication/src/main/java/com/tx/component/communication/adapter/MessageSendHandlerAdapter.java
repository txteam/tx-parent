/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月18日
 * <修改描述:>
 */
package com.tx.component.communication.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tx.component.communication.model.SendResult;


 /**
  * 消息发送处理适配器<br/>
  *     利用装饰器模式进行实现<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2015年12月18日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface MessageSendHandlerAdapter {
    
    boolean supports(Object handler);
    
    SendResult handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
