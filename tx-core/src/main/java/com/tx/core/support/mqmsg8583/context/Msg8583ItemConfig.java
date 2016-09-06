/*
 * 描             述:  <描述>
 * 修     改    人:  Administrator
 * 修  改  时  间:  2016年9月6日
 * <修改描述:>
 */
package com.tx.core.support.mqmsg8583.context;

/**
 * 报文消息项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Msg8583ItemConfig<T> {
    
    /** 报文项类型 */
    private Class<T> type;
    
    /** 对应字段名 */
    private String fieldName;
    
    /** 起始值 */
    private int start;
    
    /** 字长 */
    private int length;

    /**
     * @return 返回 type
     */
    protected Class<T> getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    protected void setType(Class<T> type) {
        this.type = type;
    }

    /**
     * @return 返回 fieldName
     */
    protected String getFieldName() {
        return fieldName;
    }

    /**
     * @param 对fieldName进行赋值
     */
    protected void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return 返回 start
     */
    protected int getStart() {
        return start;
    }

    /**
     * @param 对start进行赋值
     */
    protected void setStart(int start) {
        this.start = start;
    }

    /**
     * @return 返回 length
     */
    protected int getLength() {
        return length;
    }

    /**
     * @param 对length进行赋值
     */
    protected void setLength(int length) {
        this.length = length;
    }
}
