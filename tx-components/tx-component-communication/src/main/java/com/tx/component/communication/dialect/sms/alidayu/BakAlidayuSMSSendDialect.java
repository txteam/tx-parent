package com.tx.component.communication.dialect.sms.alidayu;
///*
// * 描述： <描述>
// * 修改人： rain
// * 修改时间： 2015年11月12日
// * 项目： com.tx.router
// */
//package com.tx.component.messagesender.dialect.sms.alidayu;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
//import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
//import com.tx.component.messagesender.dialect.MessageSendDialect;
//import com.tx.component.messagesender.exception.MessageSenderContextInitException;
//import com.tx.component.messagesender.model.SendMessage;
//import com.tx.component.messagesender.model.SendResult;
//import com.tx.component.servicelog.context.ServiceLoggerContext;
//import com.tx.component.servicelog.logger.TxLoaclFileServiceLog;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.TxCollectionUtils;
//
///**
// * 阿里大鱼短信发送方言<br/>
// * 
// * @author rain
// * @version [版本号, 2015年11月12日]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//public class CopyOfAlidayuSMSSendDialect implements MessageSendDialect {
//    
//    /** 返回格式为 json */
//    private static final String FORMAT = "json";
//    
//    /** 请求地址 */
//    private static final String HTTP_URL = "http://gw.api.taobao.com/router/rest";
//    
//    /** 一次调用发送最大手机号码数量 */
//    private static final int SMS_REC_NUMS_MAX = 200;
//    
//    /** 消息类型(短信类型，传入值请填写normal) */
//    private static final String SMS_TYPE = "normal";
//    
//    /** 应用 key */
//    private String appKey = "23266302";
//    
//    /** 应用 秘钥 */
//    private String appSecret = "e543ded3f5470016217fc20859be11c7";
//    
//    /**
//     * @param sendMessage
//     */
//    @Override
//    public SendResult send(SendMessage message) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//    
//    @Override
//    private SendResult doSend(SendMessage message) {
//        String extend = request.getId();
//        String smsFreeSignName = request.getSmsFreeSignName();
//        String smsTemplateCode = request.getSmsTemplateCode();
//        String smsParam = toSmsParam(request.getSmsParams());
//        String smsRecNum = null;
//        Set<String> set = new HashSet<String>(request.getSmsRecNums()); // 去重
//        List<Collection<String>> splitList = TxCollectionUtils.splitSize(set,
//                SMS_REC_NUMS_MAX);
//        for (Collection<String> collection : splitList) {
//            smsRecNum = StringUtils.join(collection, ',');
//        }
//        
//        TaobaoClient client = new DefaultTaobaoClient(httpUrl, appKey,
//                appSecret, format);
//        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//        req.setExtend(extend);
//        req.setSmsType(smsType);
//        req.setSmsFreeSignName(smsFreeSignName);
//        req.setSmsParam(smsParam);
//        req.setRecNum(smsRecNum);
//        req.setSmsTemplateCode(smsTemplateCode);
//        
//        response.put("_smsParam", smsParam);
//        response.put("_smsRecNum", smsRecNum);
//        response.put("_req", req);
//        try {
//            response.setBody(client.execute(req));
//        } catch (ApiException e) {
//            throw new MessageSenderContextInitException(e, "阿里大鱼-短信 api 调用失败 : {}",
//                    request.toString());
//        }
//    }
//    
//    /**
//     * 
//     * 把 Map 类型的模板参数转成阿里大渔参数格式字符串<br />
//     * 如果参数为空,则返回 null
//     *
//     * @param smsParams 模板参数
//     *            
//     * @return String 参数 json 字符串
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     * @version [版本号, 2015年11月20日]
//     * @author rain
//     */
//    private String toSmsParam(Map<String, String> smsParams) {
//        if (MapUtils.isEmpty(smsParams)) {
//            return null;
//        }
//        StringBuilder json = new StringBuilder();
//        json.append('{');
//        for (Map.Entry<String, String> entry : smsParams.entrySet()) {
//            String key = entry.getKey().replace("'", "\\'");
//            String value = entry.getValue().replace("'", "\\'");
//            json.append('\'')
//                    .append(key)
//                    .append("':'")
//                    .append(value)
//                    .append("', ");
//        }
//        json.deleteCharAt(json.length() - 2).append('}');
//        return json.toString();
//    }
//    
//    @Override
//    protected void checkRequest(AlidayuSMSRequest request) {
//        AssertUtils.notEmpty(appKey, "appKey[应用key] is empty!");
//        AssertUtils.notEmpty(appSecret, "appSecret[应用秘钥] is empty!");
//        AssertUtils.notEmpty(request.getSmsFreeSignName(),
//                "smsFreeSignName[短信签名] is empty!");
//        AssertUtils.notEmpty(request.getSmsRecNums(),
//                "smsRecNum[短信接收号码] is empty!");
//        AssertUtils.notEmpty(request.getSmsTemplateCode(),
//                "smsTemplateCode[短信模板ID] is empty!");
//    }
//    
//    @Override
//    protected void logHandle(AlidayuSMSRequest request,
//            DefaultResponse<AlibabaAliqinFcSmsNumSendResponse> response,
//            Throwable throwable) {
//        AlibabaAliqinFcSmsNumSendResponse body = response.getBody();
//        AlibabaAliqinFcSmsNumSendRequest req = response.getValue("_req",
//                AlibabaAliqinFcSmsNumSendRequest.class);
//        
//        TxLoaclFileServiceLog log = new TxLoaclFileServiceLog();
//        log.setMessageid(req.getExtend());
//        log.setModule(beanName);
//        log.setResponseBody(response.getBody().getBody());
//        log.setResponseCode(body.getErrorCode());
//        log.setResponseCodeMessage(body.getMsg());
//        log.setUseTime(response.getValueOfNumber(super.RESPONSE_VALUE_USE_TIME)
//                + "/毫秒");
//        log.putOtherParam("SmsTemplateCode", request.getSmsTemplateCode());
//        log.putOtherParam("RequestSourceName", request.getRequestSource()
//                .name());
//        log.putOtherParam("RequestTypeName", request.getRequestType().name());
//        log.putOtherParam("_smsParam", response.getValueOfString("_smsParam"));
//        log.putOtherParam("_smsRecNum", response.getValueOfString("_smsRecNum"));
//        if (throwable != null) { // 正常日志
//            log.setRemark(throwable.toString());
//        }
//        ServiceLoggerContext.logWithNotInitContext(log,
//                TxLoaclFileServiceLog.class);
//    }
//}
