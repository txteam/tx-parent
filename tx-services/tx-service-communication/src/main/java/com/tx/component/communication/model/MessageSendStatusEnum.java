/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月25日
 * <修改描述:>
 */
package com.tx.component.communication.model;


/**
 * 消息发送状态枚举<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2016年2月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public enum MessageSendStatusEnum {

    FAIL("FAIL","失败"), SUCCESS("SUCCESS","成功");

    private String key;
    private String value;

    private MessageSendStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }


    public String getValue() {
        return value;
    }

}
