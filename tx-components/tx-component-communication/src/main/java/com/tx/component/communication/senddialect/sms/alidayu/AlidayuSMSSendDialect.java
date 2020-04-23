/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.communication.senddialect.sms.alidayu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;
import com.tx.component.communication.senddialect.sms.AbstractSMSMessageSendDialect;
import com.tx.core.exceptions.util.AssertUtils;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * 阿里大鱼短信发送方言<br/>
 * 
 * @author rain
 * @version [版本号, 2015年11月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AlidayuSMSSendDialect extends AbstractSMSMessageSendDialect {
    private static  final  String VERSION = "2017-05-25";
    private static  final  String DOMAIN = "dysmsapi.aliyuncs.com";
    private static  final  String RegionId = "cn-hangzhou";

    /** 请求地址 */
    private static final String HTTP_URL = "http://gw.api.taobao.com/router/rest";
    
    /** 消息类型(短信类型，传入值请填写normal) */
    private static final String DEFAULT_SMS_TYPE = "normal";
    
    /** 一次调用发送最大手机号码数量 */
    @SuppressWarnings("unused")
    private static final int SMS_REC_NUMS_MAX = 200;
    
    /** 返回格式为 json */
    private static final String SEND_MESSAGE_FORMAT_TYPE = "json";
    
    /** 链接超时时间 */
    private int connectTimeout = 3000;
    
    /** 读取超时时间 */
    private int readTimeout = 15000;
    
    /** 应用 key */
    private String appKey;
    
    /** 应用 秘钥 */
    private String appSecret;
    
    /** 短信发送客户端 */
    private IAcsClient sendSMSClient = null;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(appKey, "appKey[应用key] is empty!");
        AssertUtils.notEmpty(appSecret, "appSecret[应用秘钥] is empty!");

        logger.info("appKey[应用key]"+this.appKey);
        logger.info("appSecret[应用秘钥]"+this.appSecret);

        DefaultProfile profile =  DefaultProfile.getProfile(RegionId, this.appKey, this.appSecret);
        this.sendSMSClient =new DefaultAcsClient(profile);
        super.afterPropertiesSet();
    }
    
    /**
     * @param message
     */
    @Override
    protected void validateMessage(SendMessage message) {
        
    }
    
    /**
     * @param message
     * @return
     */
    @Override
    protected SendResult doSend(SendMessage message) {
        SendResult result = new SendResult();
        CommonRequest request = buildSendSMSRequest(message);
        try {
            org.apache.commons.io.IOUtils ioUtils;
            CommonResponse response = this.sendSMSClient.getCommonResponse(request);

            //{"data":"{\"Message\":\"OK\",\"RequestId\":\"7A00071D-C605-42B9-B343-09A13C9C8871\",\"BizId\":\"967722487630864925^0\",\"Code\":\"OK\"}","httpResponse":{"encoding":"UTF-8","headers":{"Access-Control-Allow-Headers":"X-Requested-With, X-Sequence, _aop_secret, _aop_signature","Access-Control-Allow-Methods":"POST, GET, OPTIONS","Access-Control-Allow-Origin":"*","Access-Control-Max-Age":"172800","Connection":"keep-alive","Content-Length":"110","Content-Type":"application/json;charset=utf-8","Date":"Thu, 23 Apr 2020 08:34:25 GMT"},"httpContent":"eyJNZXNzYWdlIjoiT0siLCJSZXF1ZXN0SWQiOiI3QTAwMDcxRC1DNjA1LTQyQjktQjM0My0wOUExM0M5Qzg4NzEiLCJCaXpJZCI6Ijk2NzcyMjQ4NzYzMDg2NDkyNV4wIiwiQ29kZSI6Ik9LIn0=","httpContentString":"{\"Message\":\"OK\",\"RequestId\":\"7A00071D-C605-42B9-B343-09A13C9C8871\",\"BizId\":\"967722487630864925^0\",\"Code\":\"OK\"}","httpContentType":"JSON","status":200,"success":true,"url":"http://dysmsapi.aliyuncs.com/"},"httpStatus":200}
            String data = response.getData();
            JSONObject jsonObject = JSON.parseObject(data);

            if("OK".equalsIgnoreCase(jsonObject.getString("Code"))){
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setErrorCode(jsonObject.getString("Code"));
                result.setErrorMessage(jsonObject.getString("Message"));
            }


        } catch (ServerException e) {
            logger.warn("调用阿里云短信接口发送短信失败.ServerException", e);
            
            result.setSuccess(false);
            result.setErrorCode(e.getErrCode());
            result.setErrorMessage(e.getErrMsg());
            result.getAttributes()
                    .put("localizedMessage", e.getLocalizedMessage());
            result.getAttributes().put("message", e.getMessage());
        }  catch (ClientException e) {
            logger.warn("调用阿里云短信接口发送短信失败.ServerException", e);

            result.setSuccess(false);
            result.setErrorCode(e.getErrCode());
            result.setErrorMessage(e.getErrMsg());
            result.getAttributes()
                    .put("localizedMessage", e.getLocalizedMessage());
            result.getAttributes().put("message", e.getMessage());
        }
        return result;
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
    private CommonRequest buildSendSMSRequest(
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
        String smsFreeSignName = getSMSSignName(message);
        
        //根据短信内容结息短信发送模板信息
        SMSContentInfo smsContentInfo = parseSendMessageContent(message);
        //短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
        //短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
        String smsTemplateCode = smsContentInfo.getTemplateCode();
        //示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
        String smsParam = toSmsParam(smsContentInfo.getParams());//message.getAttributes();

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setAction("SendSms");
        request.setDomain(DOMAIN);
        request.setVersion(VERSION);

        request.putQueryParameter("RegionId", RegionId);

        
        request.putQueryParameter("PhoneNumbers", recNum);
        request.putQueryParameter("SignName", smsFreeSignName);
        request.putQueryParameter("TemplateCode", smsTemplateCode);
        request.putQueryParameter("TemplateParam", smsParam);

        
        return request;
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

//    public static void main(String[] args) {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G73qFBWrdfveWVK6rxi", "MU8xtB00FfPfzeBTxk8P7Sec4uXbOa");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setAction("SendSms");
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", "17383152159");
//        request.putQueryParameter("SignName", "贵州酱香酒交易中心");
//        request.putQueryParameter("TemplateCode", "SMS_188490116");
//        request.putQueryParameter("TemplateParam", "{\"code\":\"4455\"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }
}
