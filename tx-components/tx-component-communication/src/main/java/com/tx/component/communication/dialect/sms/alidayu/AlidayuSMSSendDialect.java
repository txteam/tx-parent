/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.communication.dialect.sms.alidayu;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.tx.component.communication.dialect.MessageSendDialect;
import com.tx.component.communication.exception.MessageSenderContextInitException;
import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;
import com.tx.component.servicelog.context.ServiceLoggerContext;
import com.tx.component.servicelog.logger.TxLoaclFileServiceLog;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.TxCollectionUtils;

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
    
    /** 返回格式为 json */
    private static final String SEND_MESSAGE_FORMAT_TYPE = "json";
    
    /** 请求地址 */
    private static final String HTTP_URL = "http://gw.api.taobao.com/router/rest";
    
    /** 一次调用发送最大手机号码数量 */
    private static final int SMS_REC_NUMS_MAX = 200;
    
    /** 消息类型(短信类型，传入值请填写normal) */
    private static final String SMS_TYPE = "normal";
    
    /** 链接超时时间 */
    private int connectTimeout = 3000;
    
    /** 读取超时时间 */
    private int readTimeout = 15000;
    
    /** 应用 key */
    private String appKey = "23266302";
    
    /** 应用 秘钥 */
    private String appSecret = "a04c7ae4333d376e55dead62ff41d791";
    
    /** 短信发送客户端 */
    private TaobaoClient sendSMSClient = null;
    
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
        AssertUtils.notEmpty(request.getSmsFreeSignName(),
                "smsFreeSignName[短信签名] is empty!");
        AssertUtils.notEmpty(request.getSmsRecNums(),
                "smsRecNum[短信接收号码] is empty!");
        AssertUtils.notEmpty(request.getSmsTemplateCode(),
                "smsTemplateCode[短信模板ID] is empty!");
    }
    
    @Override
    private SendResult doSend(SendMessage message) {
        
        String smsFreeSignName = request.getSmsFreeSignName();
        String smsTemplateCode = request.getSmsTemplateCode();
        String smsParam = toSmsParam(request.getSmsParams());
        String smsRecNum = null;
        Set<String> set = new HashSet<String>(request.getSmsRecNums()); // 去重
        List<Collection<String>> splitList = TxCollectionUtils.splitSize(set,
                SMS_REC_NUMS_MAX);
        for (Collection<String> collection : splitList) {
            smsRecNum = StringUtils.join(collection, ',');
        }
        
        AlibabaAliqinFcSmsNumSendRequest req = buildAlidayuSendSMSRequest(extend,
                smsFreeSignName,
                smsTemplateCode,
                smsParam,
                smsRecNum);
        
        //response.put("_smsParam", smsParam);
        //response.put("_smsRecNum", smsRecNum);
        //response.put("_req", req);
        try {
            response.setBody(client.execute(req));
        } catch (ApiException e) {
            throw new MessageSenderContextInitException(e,
                    "阿里大鱼-短信 api 调用失败 : {}", request.toString());
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
        String smsType = SMS_TYPE;
        //短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。
        //如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。
        //短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
        String smsFreeSignName = "";
        //短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
        //示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
        String smsParam = "";
        //短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
        String smsTemplateCode = "";
        
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
}
