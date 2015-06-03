/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-15
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

/**
 * 消息处理工具类<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-11-15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageUtils {
    
    /**
     * 组装消息 将对象toString放入消息占位符{}中
     * 
     * @param message
     * @param objArr
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String createMessage(String message, Object... objArr) {
        return MessageFormatter.arrayFormat(message, objArr).getMessage();
    }
    
    /**
     * 
     * 组装消息,将mapArr放入占位符中<br/>
     * 
     * <pre>
     * false : key:a->value:1   key:12->value:3  => "{{a}2}" = {12}
     * </pre>
     * 
     * @param message 消息字符串
     * @param mapArr 参数
     * 
     * @return String 替换后的消息
     * @exception [异常类型] [异常说明]
     * @see com.tx.core.util.MessageUtils#createMessageByMap(String, Map, boolean)
     */
    public static String createMessageByMap(String message,
            Map<String, Object> mapArr) {
        return createMessageByMap(message, mapArr, false);
    }
    
    /**
     * 
     * 组装消息,将mapArr放入占位符中<br/>
     * repeat如果为true.则替换占位符时,会重复替换.
     * 
     * <pre>
     * true :  key:a->value:1   key:12->value:3  => "{{a}2}" = {12} = 3 
     * false : key:a->value:1   key:12->value:3  => "{{a}2}" = {12}
     * </pre>
     * 
     * @param message 消息字符串
     * @param mapArr 参数
     * @param repeat 是否重复替换
     * 
     * @return String 替换后的消息
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String createMessageByMap(String message,
            Map<String, Object> mapArr, boolean repeat) {
        if (MapUtils.isEmpty(mapArr)) {
            return message;
        }
        if (message == null) {
            return null;
        }
        
        List<String> keys = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : mapArr.entrySet()) {
            keys.add("{" + entry.getKey() + "}");
            values.add(String.valueOf(entry.getValue()));
        }
        if (repeat) {
            return StringUtils.replaceEachRepeatedly(message,
                    keys.toArray(new String[] {}),
                    values.toArray(new String[] {}));
        } else {
            return StringUtils.replaceEach(message,
                    keys.toArray(new String[] {}),
                    values.toArray(new String[] {}));
        }
    }
    
    public static void main(String[] args) {
        String str = "{0},{0} 天天{{向上}你可以吗?}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("0", "零");
        map.put("向上", "xiangshang");
        map.put("xiangshang你可以吗?", "去掉了");
        String createMessage = MessageUtils.createMessageByMap(str, map);
        System.out.println(createMessage);
    }
}
