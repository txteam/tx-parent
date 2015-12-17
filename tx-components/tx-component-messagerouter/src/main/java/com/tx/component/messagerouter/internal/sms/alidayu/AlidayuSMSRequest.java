/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月19日
 * 项目： com.tx.router
 */
package com.tx.component.messagerouter.internal.sms.alidayu;

import java.util.Collection;
import java.util.Map;

import com.tx.component.messagerouter.context.AbstractMRSRequest;
import com.tx.component.messagerouter.context.MRSRequest;
import com.tx.component.messagerouter.enums.MRSRequestSourceEnum;

/**
 * 消息路由服务-阿里大鱼短信发送
 * 
 * @author rain
 * @version [版本号, 2015年11月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AlidayuSMSRequest extends AbstractMRSRequest implements MRSRequest {
    
    /** 短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。 */
    private String smsFreeSignName;
    
    /** 短信模板变量，传参规则{"key"："value"}，key的名字须和申请模板中的变量名一致。 */
    private Map<String, String> smsParams;
    
    /** 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。 */
    private Collection<String> smsRecNums;
    
    /** 短信模板ID，传入的模板必须是在阿里大鱼阿里大鱼“管理中心-短信模板管理”中的可用模板。 */
    private String smsTemplateCode;
    
    /**
     * 
     * 构建短信请求器
     * 
     * @param smsFreeSignName 短信签名
     * @param smsParams 短信模板变量
     * @param smsRecNums 短信接收号码
     * @param template 阿里大鱼短信模板
     * @param requestSource 消息路由服务请求来源
     *            
     * @version [版本号, 2015年11月20日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public AlidayuSMSRequest(
            String smsFreeSignName,
            Map<String, String> smsParams,
            Collection<String> smsRecNums,
            AlidayuSMSTemplate template,
            MRSRequestSourceEnum requestSource) {
        super(requestSource, template.getRequestType());
        this.smsFreeSignName = smsFreeSignName;
        this.smsParams = smsParams;
        this.smsRecNums = smsRecNums;
        this.smsTemplateCode = template.getSmsTemplateCode();
    }
    
    /** 短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。 */
    public String getSmsFreeSignName() {
        return smsFreeSignName;
    }
    
    /** 短信模板变量，传参规则{"key"："value"}，key的名字须和申请模板中的变量名一致。 */
    public Map<String, String> getSmsParams() {
        return smsParams;
    }
    
    /** 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。 */
    public Collection<String> getSmsRecNums() {
        return smsRecNums;
    }
    
    /** 短信模板ID，传入的模板必须是在阿里大鱼阿里大鱼“管理中心-短信模板管理”中的可用模板。 */
    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }
    
    /** 短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。 */
    public void setSmsFreeSignName(String smsFreeSignName) {
        this.smsFreeSignName = smsFreeSignName;
    }
    
    /** 短信模板变量，传参规则{"key"："value"}，key的名字须和申请模板中的变量名一致。 */
    public void setSmsParams(Map<String, String> smsParams) {
        this.smsParams = smsParams;
    }
    
    /** 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。 */
    public void setSmsRecNums(Collection<String> smsRecNums) {
        this.smsRecNums = smsRecNums;
    }
    
    /** 短信模板ID，传入的模板必须是在阿里大鱼阿里大鱼“管理中心-短信模板管理”中的可用模板。 */
    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }
    
    @Override
    public String toString() {
        return "AlidayuSMSRequest [smsFreeSignName=" + smsFreeSignName + ", " + "smsTemplateCode=" + smsTemplateCode + "]";
    }
}
