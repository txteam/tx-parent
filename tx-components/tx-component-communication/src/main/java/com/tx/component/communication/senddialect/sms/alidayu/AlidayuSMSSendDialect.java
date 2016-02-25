/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.communication.senddialect.sms.alidayu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.tx.component.communication.exception.SendMessageException;
import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;
import com.tx.component.communication.senddialect.MessageSendDialect;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 阿里大鱼短信发送方言<br/>
 * 
 * @author rain
 * @version [版本号, 2015年11月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AlidayuSMSSendDialect implements MessageSendDialect,
        InitializingBean {
    
    private static Pattern placeholderPattern = Pattern.compile("\\$\\{(.+?)\\}");
    
    /** 返回格式为 json */
    private static final String SEND_MESSAGE_FORMAT_TYPE = "json";
    
    /** 请求地址 */
    private static final String HTTP_URL = "http://gw.api.taobao.com/router/rest";
    
    /** 消息类型(短信类型，传入值请填写normal) */
    private static final String DEFAULT_SMS_TYPE = "normal";
    
    /** 一次调用发送最大手机号码数量 */
    @SuppressWarnings("unused")
    private static final int SMS_REC_NUMS_MAX = 200;
    
    /** 链接超时时间 */
    private int connectTimeout = 3000;
    
    /** 读取超时时间 */
    private int readTimeout = 15000;
    
    /** 应用 key */
    private String appKey = "23313081";
    
    /** 应用 秘钥 */
    private String appSecret = "2a339ffb0394b074888dc8d05b552388";
    
    /** 短信发送客户端 */
    private TaobaoClient sendSMSClient = null;
    
    /** 默认的短信签名 */
    private String defaultSMSFreeSignName = null;
    
    /** 短信模板code 以及 短信模板内容的映射 */
    private Map<String, String> smsTemplateMap = new HashMap<String, String>();
    
    /** 短信签名 */
    private Map<String, String> signNameMap = new HashMap<String, String>();
    
    /** 短信模板正则表达式 */
    private final Map<String, Pattern> smsTemplatePatternMap = new HashMap<>();
    
    /** 短信模板code 以及 短信模板关键字之间的映射 */
    private final MultiValueMap<String, String> smsTempalteParamsMap = new LinkedMultiValueMap<String, String>();
    
    private void setDefaultInfo() {
        signNameMap.put("身份验证", "身份验证");
        signNameMap.put("注册验证", "注册验证");
        signNameMap.put("登录验证", "登录验证");
        signNameMap.put("变更验证", "变更验证");
        signNameMap.put("活动验证", "活动验证");
        
        //身份验证验证码
        smsTemplateMap.put("SMS_5053678",
                "验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！");
        //登录确认验证码
        smsTemplateMap.put("SMS_5053677",
                "验证码${code}，您正在登录${product}，若非本人操作，请勿泄露。");
        //登录异常验证码
        smsTemplateMap.put("SMS_5053676",
                "验证码${code}，您正尝试异地登录${product}，若非本人操作，请勿泄露。");
        //用户注册验证码
        smsTemplateMap.put("SMS_5053675",
                "验证码${code}，您正在注册成为${product}用户，感谢您的支持！");
        //活动确认验证码
        smsTemplateMap.put("SMS_5053674",
                "验证码${code}，您正在参加${product}的${item}活动，请确认系本人申请。");
        //修改密码验证码
        smsTemplateMap.put("SMS_5053673",
                "验证码${code}，您正在尝试修改${product}登录密码，请妥善保管账户信息。");
        //信息变更验证码
        smsTemplateMap.put("SMS_5053672",
                "验证码${code}，您正在尝试变更${product}重要信息，请妥善保管账户信息。");
    }
    
    //    public static void main(String[] args) {
    //        String test = "验证码asdfasdf，您正在参加asdf的asdfad活动，请确认系本人申请。";
    //        String testTemplate = "验证码${code}，您正在参加${product}的${item}活动，请确认系本人申请。";
    //        String testPatternStr = "^.*?"
    //                + testTemplate.replaceAll("\\$\\{.+?\\}", "(.+?)") + "$";
    //        System.out.println(testPatternStr);
    //        Pattern testP = Pattern.compile(testPatternStr);
    //        Matcher testM = testP.matcher(test);
    //        System.out.println("生成的字符串是否匹配：" + testM.matches());
    //        System.out.println(testM.groupCount());
    //        for (int i = 1; i <= testM.groupCount(); i++) {
    //            System.out.println(testM.group(i));
    //        }
    //        
    //        Matcher m = placeholderPattern.matcher("验证码${code}，您正在参加${product}的${item}活动，请确认系本人申请。");
    //        while(m.find()){
    //            System.out.println(m.group(1) + "-" + m.start());
    //        }
    //    }
    
    private void initSMSTemplateConfig() {
        if (MapUtils.isEmpty(smsTemplateMap)) {
            return;
        }
        //迭代处理
        for (Entry<String, String> entryTemp : smsTemplateMap.entrySet()) {
            String templateCode = entryTemp.getKey();
            String templateContent = entryTemp.getValue();
            String patternStringTemp = "^.*?"
                    + templateContent.replaceAll("\\$\\{.+?\\}", "(.+?)") + "$";
            Pattern pTemp = Pattern.compile(patternStringTemp);
            
            this.smsTemplatePatternMap.put(templateCode, pTemp);
            
            Matcher m = placeholderPattern.matcher(templateContent);
            while (m.find()) {
                this.smsTempalteParamsMap.add(templateCode, m.group(1));
            }
        }
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(appKey, "appKey[应用key] is empty!");
        AssertUtils.notEmpty(appSecret, "appSecret[应用秘钥] is empty!");
        
        this.sendSMSClient = new DefaultTaobaoClient(HTTP_URL, this.appKey,
                this.appSecret, SEND_MESSAGE_FORMAT_TYPE, this.connectTimeout,
                this.readTimeout);
        
        if (MapUtils.isEmpty(this.smsTemplateMap)) {
            //如果配置为空，则读取默认值
            setDefaultInfo();
        }
        
        //初始化短信模板相关配置
        initSMSTemplateConfig();
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
    public SendResult send(SendMessage message) {
        //校验发送消息请求的合法性
        validateMessage(message);
        //发送消息
        SendResult result = doSend(message);
        //返回结果
        return result;
    }
    
    private void validateMessage(SendMessage message) {
        //        AssertUtils.notEmpty(request.getSmsFreeSignName(),
        //                "smsFreeSignName[短信签名] is empty!");
        //        AssertUtils.notEmpty(request.getSmsRecNums(),
        //                "smsRecNum[短信接收号码] is empty!");
        //        AssertUtils.notEmpty(request.getSmsTemplateCode(),
        //                "smsTemplateCode[短信模板ID] is empty!");
    }
    
    private SendResult doSend(SendMessage message) {
        SendResult result  = null;
        AlibabaAliqinFcSmsNumSendRequest req = buildAlidayuSendSMSRequest(message);
        try {
            AlibabaAliqinFcSmsNumSendResponse response = this.sendSMSClient.execute(req);
            
            result = new SendResult();
            if(response.isSuccess()){
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setErrorCode(response.getErrorCode());
                result.setErrorMessage(response.getMsg());
                if(!MapUtils.isEmpty(response.getParams())){
                    result.getAttributes().putAll(response.getParams());
                }
            }
        } catch (ApiException e) {
            throw new SendMessageException(
                    "调用阿里大鱼发送短信失败:ErrorCode:{} ErrorMessage:{}");
        }
        return result;
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
    private String getSMSFreeSignName(SendMessage message) {
        String title = message.getTitle();
        if (this.signNameMap.containsKey(title)) {
            return this.signNameMap.get(title);
        } else {
            return defaultSMSFreeSignName;
        }
    }
    
    /**
      * 构建阿里大鱼短信发送的发送请求<br/>
      * <功能详细描述>
      * @param message
      * @return [参数说明]
      * 
      * @return AlibabaAliqinFcSmsNumSendRequest [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private AlibabaAliqinFcSmsNumSendRequest buildAlidayuSendSMSRequest(
            SendMessage message) {
        //公共回传参数，在“消息返回”中会透传回该参数；
        //举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
        String extend = message.getSerialNumber();//短信发送流水
        //短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。
        //群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。
        //示例：18600000000,13911111111,13322222222
        String recNum = message.getReceivers();
        //短信类型，传入值请填写normal
        String smsType = DEFAULT_SMS_TYPE;
        //短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。
        //如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。
        //短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
        String smsFreeSignName = getSMSFreeSignName(message);
        //根据短信内容结息短信发送模板信息
        SMSContentInfo smsContentInfo = parseSendMessageContent(message);
        //短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
        //短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
        String smsTemplateCode = smsContentInfo.getTemplateCode();
        //示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
        String smsParam = toSmsParam(smsContentInfo.getParams());//message.getAttributes();
        
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend(extend);
        req.setRecNum(recNum);
        req.setSmsType(smsType);
        
        req.setSmsFreeSignName(smsFreeSignName);
        req.setSmsParam(smsParam);
        req.setSmsTemplateCode(smsTemplateCode);
        
        return req;
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
    private String toSmsParam(Map<String, String> smsParams) {
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
     * @param 对connectTimeout进行赋值
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    
    /**
     * @param 对readTimeout进行赋值
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
    
    /**
     * @param 对appKey进行赋值
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
    
    /**
     * @param 对appSecret进行赋值
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
    
    /**
     * @param 对placeholderPattern进行赋值
     */
    public static void setPlaceholderPattern(Pattern placeholderPattern) {
        AlidayuSMSSendDialect.placeholderPattern = placeholderPattern;
    }

    /**
     * @param 对defaultSMSFreeSignName进行赋值
     */
    public void setDefaultSMSFreeSignName(String defaultSMSFreeSignName) {
        this.defaultSMSFreeSignName = defaultSMSFreeSignName;
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
    
    private static class SMSContentInfo {
        
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
    
    
    
    public static void main(String[] args) {
        try {
            AlidayuSMSSendDialect sender = new AlidayuSMSSendDialect();
            sender.afterPropertiesSet();
            //身份验证|注册验证
            //用户注册验证码: SMS_2105920: 
            SendMessage sm = new SendMessage();
            sm.setSerialNumber(UUID.randomUUID().toString());
            sm.setContent("验证码123321qQ，您正在注册成为渝金所用户，感谢您的支持！");
            sm.setReceivers("18983379637");
            sm.getAttributes().put("code", "123321qQ");
            sm.getAttributes().put("product", "测试公司");
            sm.setTitle("注册验证");
            sender.send(sm);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    
    
}
