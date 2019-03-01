/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.communication.senddialect.sms;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;
import com.tx.component.communication.senddialect.MessageSendDialect;

/**
 * 阿里大鱼短信发送方言<br/>
 * 
 * @author rain
 * @version [版本号, 2015年11月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class AbstractSMSCodeSendDialect implements MessageSendDialect,
        InitializingBean {
    
    protected Logger logger = LoggerFactory.getLogger(AbstractSMSCodeSendDialect.class);
    
    /** 短信模板code 以及 短信模板内容的映射 */
    private Set<String> smsCodeSet = new HashSet<String>();
    
    /** 短信签名 */
    private Set<String> signNameSet = new HashSet<String>();
    
    /** 默认的短信签名 */
    private String defaultSMSSignName;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化短信模板相关配置
    }
    
    /**
     * @param message
     * @return
     */
    @Override
    public final boolean supports(SendMessage message) {
        if (CollectionUtils.isEmpty(this.smsCodeSet)) {
            return true;
        } else {
            return this.smsCodeSet.contains(message.getContent());
        }
    }
    
    /**
     * @param sendMessage
     */
    @Override
    public final SendResult send(SendMessage message) {
        //校验发送消息请求的合法性
        validateMessage(message);
        
        //发送消息
        SendResult result = doSend(message);
        
        //返回结果
        return result;
    }
    
    protected abstract void validateMessage(SendMessage message);
    
    protected abstract SendResult doSend(SendMessage message);
    
    /**
     * @param 对smsCodeSet进行赋值
     */
    public void setSmsCodeSet(Set<String> smsCodeSet) {
        this.smsCodeSet = smsCodeSet;
    }
    
    /**
     * @param 对signNameSet进行赋值
     */
    public void setSignNameSet(Set<String> signNameSet) {
        this.signNameSet = signNameSet;
    }
    
    /**
     * 返回短信签名
     * <功能详细描述>
     * @param message
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected String getSMSSignName(SendMessage message) {
        String title = message.getTitle();
        if (this.signNameSet.contains(title)) {
            return title;
        } else if (!StringUtils.isEmpty(this.defaultSMSSignName)) {
            return defaultSMSSignName;
        } else {
            return title;
        }
    }
    
    /**
     * 
     * 把 Map 类型的模板参数转成阿里大渔参数格式字符串<br />
     * 如果参数为空,则返回 null
     *
     * @param smsParams 模板参数
     *            
     * @return String 参数 json 字符串
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月20日]
     * @author rain
     */
    protected String buildParamString(Map<String, String> smsParams) {
        if (MapUtils.isEmpty(smsParams)) {
            return null;
        }
        StringBuilder json = new StringBuilder();
        json.append('{');
        for (Map.Entry<String, String> entry : smsParams.entrySet()) {
            String key = entry.getKey().replace("'", "\\'");
            String value = entry.getValue().replace("'", "\\'");
            json.append('\'')
                    .append(key)
                    .append("':'")
                    .append(value)
                    .append("', ");
        }
        json.deleteCharAt(json.length() - 2).append('}');
        return json.toString();
    }
}
