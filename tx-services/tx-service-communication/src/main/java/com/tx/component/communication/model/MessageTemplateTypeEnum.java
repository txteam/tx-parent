/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月17日
 * <修改描述:>
 */
package com.tx.component.communication.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tx.core.support.json.BaseEnum;
import com.tx.core.support.json.BaseEnumJsonSerializer;

/**
 * 消息模板类型<br/>
 * <功能详细描述>
 *
 * @author  Administrator
 * @version  [版本号, 2016年2月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@JsonSerialize(using = BaseEnumJsonSerializer.class)
public enum MessageTemplateTypeEnum implements BaseEnum {
    
    NOTICE_MESSAGE("NOTICE_MESSAGE", "站内消息"),
    
    SMS("SMS", "短信"),
    
    EMAIL("EMAIL", "邮件");
    
    private final String key;
    
    private final String name;
    
    /** <默认构造函数> */
    private MessageTemplateTypeEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }
    
    /**
    * @return 返回 key
    */
    public String getKey() {
        return key;
    }
    
    /**
    * @return 返回 name
    */
    public String getName() {
        return name;
    }
}
