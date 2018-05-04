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

import org.apache.commons.collections4.MapUtils;
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
     * 格式化组装消息<br/>
     * <功能详细描述>
     * @param messagePattern
     * @param argArray
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static String format(String messagePattern, String... argArray) {
        String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
        return message;
    }
    
    /**
      * 格式化组装消息<br/>
      * <功能详细描述>
      * @param messagePattern
      * @param argArray
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String format(String messagePattern, Object[] argArray) {
        String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
        return message;
    }
    
    /**
     * 格式化组装消息<br/>
     * <功能详细描述>
     * false : key:a->value:1   key:12->value:3  => "{{a}2}" = {12}
     * </pre>
     * 
     * @param message 消息字符串
     * @param params 参数
     * 
     * @return String 替换后的消息
     * @exception [异常类型] [异常说明]
     * @see com.tx.core.util.MessageUtils#format(String, Map, boolean)
     */
    public static String format(String message, Map<String, Object> params) {
        return format(message, params, false);
    }
    
    /**
     * 格式化组装消息<br/>
     * repeat如果为true.则替换占位符时,会重复替换.
     * <功能详细描述>
     * <pre>
     * true :  key:a->value:1   key:12->value:3  => "{{a}2}" = {12} = 3 
     * false : key:a->value:1   key:12->value:3  => "{{a}2}" = {12}
     * </pre>
     * 
     * @param message 消息字符串
     * @param params 参数
     * @param repeat 是否重复替换
     * 
     * @return String 替换后的消息
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String format(String message, Map<String, Object> params, boolean repeat) {
        if (MapUtils.isEmpty(params)) {
            return message;
        }
        if (StringUtils.isEmpty(message)) {
            return message;
        }
        
        List<String> keys = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            keys.add("{" + entry.getKey() + "}");
            values.add(String.valueOf(entry.getValue()));
        }
        if (repeat) {
            return StringUtils.replaceEachRepeatedly(message,
                    keys.toArray(new String[] {}),
                    values.toArray(new String[] {}));
        } else {
            return StringUtils.replaceEach(message, keys.toArray(new String[] {}), values.toArray(new String[] {}));
        }
    }
    
    public static void main(String[] args) {
        String str = "{0},{0} 天天{{向上}你可以吗?}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("0", "零");
        map.put("向上", "xiangshang");
        map.put("xiangshang你可以吗?", "去掉了");
        System.out.println(MessageUtils.format(str, map));
        System.out.println(MessageUtils.format(str, map, true));
    }
}
