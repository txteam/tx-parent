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
public class Msg8583ItemConfig {
    
    /** 报文索引位：0开始编码 */
    private int index;
    
    /** 报文项类型 */
    private Class<?> type;
    
    /** 报文项类型 */
    private Class<?> propertyType;
    
    /** 对应字段名 */
    private String propertyName;
    
    /** 起始值 */
    private int start;
    
    /** 字长 当length < 0时表示不定长 */
    private int length;
    
    /** 是否固定长度 */
    private boolean fixedLength;
    
    /** 最大长度 */
    private int maxLength;
    
    /**
     * @return 返回 index
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * @param 对index进行赋值
     */
    public void setIndex(int index) {
        this.index = index;
    }
    
    /**
     * @return 返回 type
     */
    public Class<?> getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(Class<?> type) {
        this.type = type;
    }
    
    /**
     * @return 返回 propertyType
     */
    public Class<?> getPropertyType() {
        return propertyType;
    }
    
    /**
     * @param 对propertyType进行赋值
     */
    public void setPropertyType(Class<?> propertyType) {
        this.propertyType = propertyType;
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
    
    /**
     * @return 返回 start
     */
    public int getStart() {
        return start;
    }
    
    /**
     * @param 对start进行赋值
     */
    public void setStart(int start) {
        this.start = start;
    }
    
    /**
     * @return 返回 length
     */
    public int getLength() {
        return length;
    }
    
    /**
     * @param 对length进行赋值
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     * @return 返回 fixedLength
     */
    public boolean isFixedLength() {
        return fixedLength;
    }
    
    /**
     * @param 对fixedLength进行赋值
     */
    public void setFixedLength(boolean fixedLength) {
        this.fixedLength = fixedLength;
    }
    
    /**
     * @return 返回 maxLength
     */
    public int getMaxLength() {
        return maxLength;
    }
    
    /**
     * @param 对maxLength进行赋值
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
