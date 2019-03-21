/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月25日
 * <修改描述:>
 */
package com.tx.component.communication.model;

/**
 * 消息内容类型<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2016年2月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public enum MessageTypeEnum {
    SMS("SMS", "短信"),
    EMAIL("EMAIL", "邮件");

    private final String key;

    private final String name;

    /**
     * <默认构造函数>
     */
    private MessageTypeEnum(String key, String name) {
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
