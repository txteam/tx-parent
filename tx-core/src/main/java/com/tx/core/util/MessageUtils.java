/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-15
 * <修改描述:>
 */
package com.tx.core.util;

import org.slf4j.helpers.MessageFormatter;

/**
 * 消息处理工具类
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MessageUtils {
    
    /**
      * 组装消息
      * 将对象toString放入消息占位符{}中
      * @param message
      * @param objArr
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String createMessage(String message, Object... objArr) {
        return MessageFormatter.arrayFormat(message,objArr).getMessage();
    }
    
}
