/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月18日
 * <修改描述:>
 */
package com.tx.component.messagerouter.context;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;

/**
 * 阿里云短信发送方言类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AliyunMessageSendTest{
    
    public static void main(String[] args) {
        //sendMsg("18983379637");
        
        sendEmail("240638006@qq.com");
    }
    
    private static void sendEmail(String email) {
        IClientProfile profile = DefaultProfile.getProfile("cn-shenzhen",
                "LTAItTQj5hMN5eD1",
                "8L8MXtQUb0wNUOlNNan0o9GrNpTCRc");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setFromAlias("企帐通");//发件名称
            request.setAccountName("test1@e.cnvex.cn");//发件地址
            
            request.setAddressType(1);
            request.setTagName("test1");//阿里云配置tag
            request.setReplyToAddress(true);
            request.setToAddress(email);
            
            request.setSubject("给你打款800万，请及时验收！xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            request.setHtmlBody("测试邮件 别当真.xxxxxxxxxxxxxxxxxxxxxxxxx");
            
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            String rid = httpResponse.getRequestId();
            System.out.println(rid);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    
    private static void sendMsg(String tel) {
        IClientProfile profile = DefaultProfile.getProfile("cn-shenzhen",
                "LTAItTQj5hMN5eD1",
                "8L8MXtQUb0wNUOlNNan0o9GrNpTCRc");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendSmsRequest request = new SingleSendSmsRequest();
        try {
            request.setActionName("SingleSendSms");
            request.setSignName("汽摩交易所");
            request.setTemplateCode("SMS_14246689");
            request.setRecNum(tel);
            //request.setMethod(MethodType.POST);
            request.setParamString("{\"code\":\"亚美蝶\"}");
            // request.AcceptFormat = "xml";
            // request.Version = "2016-03-18";
            
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
            System.out.println(httpResponse.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
