/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.communication.senddialect.sms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
public abstract class AbstractSMSMessageSendDialect implements
        MessageSendDialect, InitializingBean {
    
    protected Logger logger = LoggerFactory.getLogger(AbstractSMSMessageSendDialect.class);
    
    private String placeholder = "\\$\\{(.+?)\\}";
    
    private Pattern placeholderPattern = null;
    
    /** 短信模板code 以及 短信模板内容的映射 */
    private Map<String, String> smsTemplateMap = new HashMap<String, String>();
    
    /** 短信签名 */
    private Map<String, String> signNameMap = new HashMap<String, String>();
    
    /** 默认的短信签名 */
    private String defaultSMSSignName;
    
    /** 短信模板正则表达式 */
    private final Map<String, Pattern> smsTemplatePatternMap = new HashMap<>();
    
    /** 短信模板code 以及 短信模板关键字之间的映射 */
    private final MultiValueMap<String, String> smsTempalteParamsMap = new LinkedMultiValueMap<String, String>();
    
    /**
      * 初始化短信模板<br/>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void initSMSTemplateConfig() {
        //如果默认的短信签名为空，则获取配置的签名映射中的第一个
        if (StringUtils.isEmpty(defaultSMSSignName)) {
            if (!MapUtils.isEmpty(this.signNameMap)) {
                //如果默认签名为空，则将第一个短信签名当作默认签名
                defaultSMSSignName = (String) this.signNameMap.values()
                        .toArray()[0];
            }
        }
        
        //替换字符串
        this.placeholderPattern = Pattern.compile(this.placeholder);
        //迭代处理
        if (MapUtils.isEmpty(this.smsTemplateMap)) {
            return;
        }
        for (Entry<String, String> entryTemp : smsTemplateMap.entrySet()) {
            String templateCode = entryTemp.getKey();
            String templateContent = entryTemp.getValue();
            String patternStringTemp = "^.*?"
                    + templateContent.replaceAll(this.placeholder, "(.+?)")
                    + "$";
            Pattern pTemp = Pattern.compile(patternStringTemp);
            
            this.smsTemplatePatternMap.put(templateCode, pTemp);
            
            Matcher m = placeholderPattern.matcher(templateContent);
            Set<String> keySet = new HashSet<String>();
            while (m.find()) {
                keySet.add(m.group(1));
                this.smsTempalteParamsMap.add(templateCode, m.group(1));
            }
        }
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化短信模板相关配置
        initSMSTemplateConfig();
    }
    
    /**
     * @param message
     * @return
     */
    @Override
    public final boolean supports(SendMessage message) {
        boolean support = false;
        for (Pattern pTemp : this.smsTemplatePatternMap.values()) {
            if (pTemp.matcher(message.getContent()).matches()) {
                support = true;
                break;
            }
        }
        return support;
    }
    
    /**
      * 根据发送的消息解析短信发送模板<br/>
      * <功能详细描述>
      * @param sendMessage
      * @return [参数说明]
      * 
      * @return SMSContentInfo [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected SMSContentInfo parseSendMessageContent(SendMessage sendMessage) {
        String content = sendMessage.getContent();
        String templateCode = null;
        Map<String, String> params = new HashMap<>();
        for (Entry<String, Pattern> entryTemp : this.smsTemplatePatternMap.entrySet()) {
            Pattern p = entryTemp.getValue();
            Matcher m = p.matcher(content);
            if (!m.matches()) {
                continue;
            }
            templateCode = entryTemp.getKey();
            int i = 1;
            Iterator<String> paramKeyIte = this.smsTempalteParamsMap.get(templateCode)
                    .iterator();
            while (i <= m.groupCount() && paramKeyIte.hasNext()) {
                String paramKeyTemp = paramKeyIte.next();
                params.put(paramKeyTemp, m.group(i));
                i++;
            }
        }
        SMSContentInfo res = new SMSContentInfo(templateCode, params);
        return res;
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
     * @param 对placeholderPattern进行赋值
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
    
    /**
     * @param 对smsTemplateMap进行赋值
     */
    public void setSmsTemplateMap(Map<String, String> smsTemplateMap) {
        this.smsTemplateMap = smsTemplateMap;
    }
    
    /**
     * @param 对signNameMap进行赋值
     */
    public void setSignNameMap(Map<String, String> signNameMap) {
        this.signNameMap = signNameMap;
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
        if (this.signNameMap.containsKey(title)) {
            return this.signNameMap.get(title);
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
    
    /**
      * 短信模板信息<br/>
      * <功能详细描述>
      * 
      * @author  Administrator
      * @version  [版本号, 2016年9月18日]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    public static class SMSContentInfo {
        
        /** 短信模板信息 */
        private final String templateCode;
        
        /** 短信模板参数 */
        private final Map<String, String> params;
        
        /** <默认构造函数> */
        public SMSContentInfo(String templateCode, Map<String, String> params) {
            super();
            this.templateCode = templateCode;
            this.params = params;
        }
        
        /**
         * @return 返回 templateCode
         */
        public String getTemplateCode() {
            return templateCode;
        }
        
        /**
         * @return 返回 params
         */
        public Map<String, String> getParams() {
            return params;
        }
    }
}
