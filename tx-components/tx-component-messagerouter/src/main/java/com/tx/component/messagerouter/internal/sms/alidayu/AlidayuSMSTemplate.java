/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月19日
 * 项目： com.tx.router
 */
package com.tx.component.messagerouter.internal.sms.alidayu;

import com.tx.component.messagerouter.enums.MRSRequestTypeEnum;

/**
 * 阿里大鱼短信模板
 * 
 * @author rain
 * @version [版本号, 2015年11月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public enum AlidayuSMSTemplate {
    
    测试(MRSRequestTypeEnum.测试, "SMS_2130726"),
    
    登陆验证码(MRSRequestTypeEnum.验证码, "SMS_2130726"),;
    
    /** 消息路由服务请求类型 */
    private MRSRequestTypeEnum requestType;
    
    /** 短信模板ID，传入的模板必须是在阿里大鱼阿里大鱼“管理中心-短信模板管理”中的可用模板 */
    private String smsTemplateCode;
    
    AlidayuSMSTemplate(MRSRequestTypeEnum requestType, String smsTemplateCode) {
        this.requestType = requestType;
        this.smsTemplateCode = smsTemplateCode;
    }
    
    /** 消息路由服务请求类型 */
    public MRSRequestTypeEnum getRequestType() {
        return requestType;
    }
    
    /** 短信模板ID，传入的模板必须是在阿里大鱼阿里大鱼“管理中心-短信模板管理”中的可用模板 */
    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }
}
