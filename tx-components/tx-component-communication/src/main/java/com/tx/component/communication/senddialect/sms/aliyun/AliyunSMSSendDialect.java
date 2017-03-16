/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月18日
 * <修改描述:>
 */
package com.tx.component.communication.senddialect.sms.aliyun;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;
import com.tx.component.communication.senddialect.sms.AbstractSMSMessageSendDialect;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 阿里云短信发送方言类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AliyunSMSSendDialect extends AbstractSMSMessageSendDialect {
    
    private String regionId = "cn-shenzhen";
    
    private String accessKeyId;
    
    private String secret;
    
    private IClientProfile profile;
    
    private IAcsClient sendSMSClient;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(regionId, "regionId is empty.");
        AssertUtils.notEmpty(accessKeyId, "accessKeyId is empty.");
        AssertUtils.notEmpty(secret, "secret is empty.");
        
        this.profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        this.sendSMSClient = new DefaultAcsClient(this.profile);
        
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
        SingleSendSmsRequest req = buildSendSMSRequest(message);
        try {
            SingleSendSmsResponse response = this.sendSMSClient.getAcsResponse(req);
            
            result.setSuccess(true);
            result.getAttributes().put("requestId", response.getRequestId());
            result.getAttributes().put("code", req.getTemplateCode());//
        } catch (ServerException e) {
            logger.warn("调用阿里云短信接口发送短信失败.ServerException", e);
            
            result.setSuccess(false);
            result.setErrorCode(e.getErrCode());
            result.setErrorMessage(e.getErrMsg());
            
            result.getAttributes().put("requestId", e.getRequestId());
            result.getAttributes()
                    .put("errorType", e.getErrorType().toString());
            result.getAttributes().put("message", e.getMessage());
        } catch (ClientException e) {
            logger.warn("调用阿里云短信接口发送短信失败.ClientException", e);
            
            result.setSuccess(false);
            result.setErrorCode(e.getErrCode());
            result.setErrorMessage(e.getErrMsg());
            
            result.getAttributes().put("requestId", e.getRequestId());
            result.getAttributes()
                    .put("errorType", e.getErrorType().toString());
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
    private SingleSendSmsRequest buildSendSMSRequest(SendMessage message) {
        
        //公共回传参数，在“消息返回”中会透传回该参数；
        //举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
        //String extend = message.getSerialNumber();//短信发送流水
        //短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。
        //群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。
        //示例：18600000000,13911111111,13322222222
        String recNum = message.getReceivers();
        //短信类型，传入值请填写normal
        //String smsType = DEFAULT_SMS_TYPE;
        //短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。
        //如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。
        //短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
        String smsSignName = getSMSSignName(message);
        //根据短信内容结息短信发送模板信息
        SMSContentInfo smsContentInfo = parseSendMessageContent(message);
        //短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
        //短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
        String smsTemplateCode = smsContentInfo.getTemplateCode();
        //示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
        String paramString = buildParamString(smsContentInfo.getParams());//message.getAttributes();
        
        //       AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        //       req.setExtend(extend);
        //       req.setRecNum(recNum);
        //       req.setSmsType(smsType);
        //       
        //       req.setSmsFreeSignName(smsFreeSignName);
        //       req.setSmsParam(smsParam);
        //       req.setSmsTemplateCode(smsTemplateCode);
        
        SingleSendSmsRequest request = new SingleSendSmsRequest();
        request.setActionName("SingleSendSms");
        
        request.setSignName(smsSignName);
        request.setTemplateCode(smsTemplateCode);
        request.setRecNum(recNum);
        request.setParamString(paramString);
        
        //request.setMethod(MethodType.POST);
        
        return request;
    }
    
    /**
     * @param 对regionId进行赋值
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
    
    /**
     * @param 对accessKeyId进行赋值
     */
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    
    /**
     * @param 对secret进行赋值
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    public static void main(String[] args) throws Exception {
        sendMsg("18983379637");
    }
    
    private static void sendMsg(String tel) throws Exception {
        AliyunSMSSendDialect d = new AliyunSMSSendDialect();
        
        d.setAccessKeyId("LTAItTQj5hMN5eD1");
        d.setSecret("8L8MXtQUb0wNUOlNNan0o9GrNpTCRc");
        Map<String, String> signNameMap = new HashMap<String, String>();
        signNameMap.put("汽摩交易所", "汽摩交易所");
        signNameMap.put("企账通", "企账通");
        Map<String, String> smsTemplateMap = new HashMap<String, String>();
        smsTemplateMap.put("SMS_14246689", "您的验证码是${code}。");
        d.setSignNameMap(signNameMap);
        d.setSmsTemplateMap(smsTemplateMap);
        
        d.afterPropertiesSet();
        
        SendMessage message = new SendMessage("SMS", "18983379637", "汽摩交易所",
                "您的验证码是0018。");
        
        SendResult result = d.send(message);
        if (result.isSuccess()) {
            System.out.println("success.");
        } else {
            System.out.println("errorCode:" + result.getErrorCode()
                    + " errorMessage:" + result.getErrorMessage());
        }
    }
}
