/*
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2015年7月9日
 * <修改描述:>
 */
package com.tx.core.support.bean;

/**
 * 键值对
 * 
 * @author  rain
 * @version  [版本号, 2015年7月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class KeyValue<Key, Value> {
    
    private Key key;
    
    private Value value;
    
    public KeyValue() {
        
    }
    
    public KeyValue(Key key, Value value) {
        this.key = key;
        this.value = value;
    }
    
    /** @return 返回 key */
    public Key getKey() {
        return key;
    }
    
    /** @param 对key进行赋值 */
    public void setKey(Key key) {
        this.key = key;
    }
    
    /** @return 返回 value */
    public Value getValue() {
        return value;
    }
    
    /** @param 对value进行赋值 */
    public void setValue(Value value) {
        this.value = value;
    }
    
}
