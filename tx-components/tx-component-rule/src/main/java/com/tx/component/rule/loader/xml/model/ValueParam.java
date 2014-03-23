/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-18
 * <修改描述:>
 */
package com.tx.component.rule.loader.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * Value类型参数<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamConverter(ValueParamConverter.class)
@XStreamAlias("value")
public class ValueParam {
    
    /** byte参数key */
    @XStreamAsAttribute
    private String key;
    
    private String value;
    
    /**
     * @return 返回 key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * @param 对key进行赋值
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * @return 返回 value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * @param 对value进行赋值
     */
    public void setValue(String value) {
        this.value = value;
    }
}
