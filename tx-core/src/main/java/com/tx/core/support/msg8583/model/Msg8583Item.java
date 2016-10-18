/*
 * 描             述:  <描述>
 * 修     改    人:  Administrator
 * 修  改  时  间:  2016年9月6日
 * <修改描述:>
 */
package com.tx.core.support.msg8583.model;

/**
 * 报文消息项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Msg8583Item<T> {
    
    /** 报文项值 */
    private T value;
    
    /** 报文项类型 */
    private Class<T> type;
    
    /** 对应字段名 */
    private String propertyName;
    
    /**
     * @return 返回 value
     */
    public T getValue() {
        return value;
    }
    
    /**
     * @param 对value进行赋值
     */
    public void setValue(T value) {
        this.value = value;
    }
    
    /**
     * @return 返回 type
     */
    public Class<T> getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(Class<T> type) {
        this.type = type;
    }
    
    /**
     * @return 返回 propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }
    
    /**
     * @param 对propertyName进行赋值
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
