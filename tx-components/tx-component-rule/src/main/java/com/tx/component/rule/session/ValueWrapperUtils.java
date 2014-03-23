/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月23日
 * <修改描述:>
 */
package com.tx.component.rule.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 值工具<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ValueWrapperUtils {
    
    /**
     * 向valueWrapper中写入结果值<br/>
     *<功能详细描述>
     * @param valueWrapper
     * @param value [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <T> T getValue(ValueWrapper<T> valueWrapper, T defaultValue) {
        if (valueWrapper == null) {
            return null;
        }
        if (valueWrapper.getValue() == null) {
            valueWrapper.setValue(defaultValue);
        }
        return valueWrapper.getValue();
    }
    
    /**
      * 向valueWrapper中写入结果值<br/>
      *<功能详细描述>
      * @param valueWrapper
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> void setValue(ValueWrapper<T> valueWrapper, T value) {
        if (valueWrapper == null) {
            return;
        }
        valueWrapper.setValue(value);
    }
    
    /**
      * 向valueWrapper添加值<br/>
      *<功能详细描述>
      * @param valueWrapper
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> void addValue(ValueWrapper<List<T>> valueWrapper, T value) {
        if (valueWrapper == null) {
            return;
        }
        if (valueWrapper.getValue() == null) {
            valueWrapper.setValue(new ArrayList<T>());
        }
        valueWrapper.getValue().add(value);
    }
    
    /**
      * 向map类结果中写入值<br/>
      *<功能详细描述>
      * @param valueWrapper
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <K, V> void putValue(ValueWrapper<Map<K, V>> valueWrapper,
            K key, V value) {
        if (valueWrapper == null) {
            return;
        }
        if (valueWrapper.getValue() == null) {
            valueWrapper.setValue(new HashMap<K, V>());
        }
        valueWrapper.getValue().put(key, value);
    }
}
