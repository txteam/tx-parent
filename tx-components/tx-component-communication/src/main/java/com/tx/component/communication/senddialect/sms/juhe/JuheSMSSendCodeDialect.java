/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月18日
 * <修改描述:>
 */
package com.tx.component.communication.senddialect.sms.juhe;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;
import com.tx.component.communication.senddialect.sms.AbstractSMSCodeSendDialect;
import com.tx.core.exceptions.SILException;
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
public class JuheSMSSendCodeDialect extends AbstractSMSCodeSendDialect {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(JuheSMSSendCodeDialect.class);
    
    /** 默认的字符集 */
    public static final String DEF_CHATSET = "UTF-8";
    
    /** 默认链接的超时时间 */
    public static final int DEF_CONN_TIMEOUT = 30000;
    
    /** 默认的读取超时时间 */
    public static final int DEF_READ_TIMEOUT = 30000;
    
    /** userAgent */
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    
    /** 聚合发送短信接口 */
    public static final String SERVER_URL = "http://v.juhe.cn/sms/send";
    
    /** 配置您申请的KEY 036a43cf37e9ffd5aff0e22e6e775862 */
    public String appKey = "";
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(appKey, "appKey is empty.");
        
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
        
        Map<String, String> params = buildSendSMSRequestParams(message);
        try {
            String resultMessage = net(SERVER_URL, params, "GET");
            JSONObject object = JSONObject.parseObject(resultMessage);
            if (object.getInteger("error_code") == 0) {
                result.setSuccess(true);
                result.getAttributes().put("result", object.get("result"));
            } else {
                result.setSuccess(false);
                result.setErrorCode(String.valueOf(object.get("reason")));
                result.setErrorMessage(String.valueOf(object.get("reason")));
            }
        } catch (Exception e) {
            logger.warn("调用阿里云短信接口发送短信失败.ServerException", e);
            
            result.setSuccess(false);
            result.setErrorCode("504");
            result.setErrorMessage(e.getMessage());
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
    private Map<String, String> buildSendSMSRequestParams(SendMessage message) {
        
        //公共回传参数，在“消息返回”中会透传回该参数；
        //举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
        //String extend = message.getSerialNumber();//短信发送流水
        //短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。
        //群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。
        //示例：18600000000,13911111111,13322222222
        String recNum = message.getReceivers();
        //短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
        //短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
        String smsTemplateCode = message.getContent();
        //示例：针对模板“验证码#code#，您正在进行#product#身份验证，打死不要告诉别人哦！”，传参时需传入 #code#=1234&#product#=alidayu
        String paramString = buildParamString(message.getAttributes());//message.getAttributes();
        
        //       AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        //       req.setExtend(extend);
        //       req.setRecNum(recNum);
        //       req.setSmsType(smsType);
        //       
        //       req.setSmsFreeSignName(smsFreeSignName);
        //       req.setSmsParam(smsParam);
        //       req.setSmsTemplateCode(smsTemplateCode);
        
        Map<String, String> requestParams = new HashMap<String, String>();//请求参数
        requestParams.put("mobile", recNum);//接收短信的手机号码
        requestParams.put("tpl_id", smsTemplateCode);//短信模板ID，请参考个人中心短信模板设置
        requestParams.put("tpl_value", paramString);//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
        requestParams.put("key", this.appKey);//应用APPKEY(应用详细页查询)
        requestParams.put("dtype", "");//返回数据的格式,xml或json，默认json
        
        //request.setMethod(MethodType.POST);
        return requestParams;
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
            return "";
        }
        StringBuilder paramSb = new StringBuilder();
        for (Entry<String, String> entryTemp : smsParams.entrySet()) {
            String key = entryTemp.getKey();
            if (StringUtils.isEmpty(key)) {
                continue;
            }
            if (!key.startsWith("#")) {
                paramSb.append("#");
            }
            paramSb.append(key);
            if (!key.endsWith("#")) {
                paramSb.append("#");
            }
            paramSb.append("=").append(entryTemp.getValue());
            paramSb.append("&");
        }
        paramSb.deleteCharAt(paramSb.length() - 1);
        return paramSb.toString();
    }
    
    /**
     * @param 对appKey进行赋值
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
    
    /**
     * 发送请求<br/>
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    private static String net(String strUrl, Map<String, String> params,
            String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            System.out.println(strUrl);
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(
                            conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    throw new SILException(e.getMessage(), e);
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            throw new SILException(e.getMessage(), e);
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
    
    //将map型转为请求参数型
    public static String urlencode(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> i : data.entrySet()) {
            try {
                sb.append(i.getKey())
                        .append("=")
                        .append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                throw new SILException(e.getMessage(), e);
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
        JuheSMSSendCodeDialect dialect = new JuheSMSSendCodeDialect();
        dialect.setAppKey("036a43cf37e9ffd5aff0e22e6e775862");
        
        //Map<String, String> smsTemplateMap = new HashMap<String, String>();
        //smsTemplateMap.put("29669", "【添馨网络科技】验证码#code#,您正在注册成为#app#用户,感谢您的支持!");
        //dialect.setSmsTemplateMap(smsTemplateMap);
        dialect.afterPropertiesSet();
        
        SendMessage message = new SendMessage();
        message.setReceivers("18983379637");
        message.setContent("29669");
        message.getAttributes().put("code", "1122");
        message.getAttributes().put("app", "添馨网络科技有限公司");
        SendResult result = dialect.send(message);
        
        if (result.isSuccess()) {
            System.out.println("success.");
        } else {
            System.out.println("errorCode:" + result.getErrorCode()
                    + " errorMessage:" + result.getErrorMessage());
        }
    }
}
