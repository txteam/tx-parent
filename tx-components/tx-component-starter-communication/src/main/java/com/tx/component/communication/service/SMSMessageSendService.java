/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月21日
 * <修改描述:>
 */
package com.tx.component.communication.service;

import com.tx.component.communication.context.MessageSenderContext;
import com.tx.component.communication.model.MessageSendRecord;
import com.tx.component.communication.model.MessageSendStatusEnum;
import com.tx.component.communication.model.MessageTypeEnum;
import com.tx.component.communication.model.SendResult;
import com.tx.core.exceptions.util.AssertUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 发送短信<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2016年2月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component("smsMessageSendService")
public class SMSMessageSendService {

    private Logger logger = LoggerFactory.getLogger(SMSMessageSendService.class);

    /**
     * 消息发送记录
     */
    @Resource(name = "messageSendRecordService")
    private MessageSendRecordService messageSendRecordService;

    /**
     * 发送短信<br/>
     * <功能详细描述>
     *
     * @param receiver
     * @param title
     * @param templateType
     * @param params
     * @return boolean [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean sendSMS(String receiver, String title,
                           String smsTemplateContent, String smsTemplateCode, String requestId,
                           Map<String, String> params) {
        AssertUtils.notEmpty(receiver, "receiver is empty.");
        AssertUtils.notNull(title, "title is null.");
        AssertUtils.notNull(smsTemplateCode, "smsTemplateCode is null.");
//        AssertUtils.notNull(templateType, "templateType is null.");

        String transferContent = transferContent(smsTemplateContent,
                params);
        logger.info("发送的短信内容为：" + transferContent);
        SendResult sendResult = null;
        try {
            sendResult = MessageSenderContext.getContext().send("SMS_CODE",
                    receiver,
                    title,
                    smsTemplateCode,
                    params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sendResult = new SendResult();
            sendResult.setSuccess(false);
            sendResult.setErrorCode("SIL_EXCEPTION_001");
            sendResult.setErrorMessage(e.getMessage());
        } finally {
            //构建并持久消息发送记录
            buildAndpersistMessageSendRecord(receiver,
                    title,
                    smsTemplateCode,
                    transferContent,
                    requestId,
                    sendResult);
        }


        boolean flag = sendResult.isSuccess();
        return flag;
    }

    /**
     * 发送短信<br/>
     * <功能详细描述>
     *
     * @param receiver
     * @param title
     * @param templateType
     * @param params
     * @return boolean [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean sendSMS(String receiver, String title,
                           String smsTemplateContent, String smsTemplateCode, Map<String, String> params) {
        if (com.tx.core.util.StringUtils.isEmpty(receiver)) {
            logger.info("接收人为空，短信不发送");
            return false;
        }
        return sendSMS(receiver, title, smsTemplateContent, smsTemplateCode, null, params);
    }

    /**
     * 构建短信发送记录<br/>
     * <功能详细描述>
     *
     * @param receiver
     * @param title
     * @param contentTemplateCode
     * @param content
     * @param sendResult
     * @return MessageSendRecord [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public MessageSendRecord buildAndpersistMessageSendRecord(String receiver,
                                                              String title, String contentTemplateCode, String content,
                                                              String requestIp, SendResult sendResult) {
        MessageSendRecord msr = new MessageSendRecord();
        msr.setContent(content);
        msr.setContentTemplateCode(contentTemplateCode);
        msr.setStatus(sendResult.isSuccess() ? MessageSendStatusEnum.SUCCESS
                : MessageSendStatusEnum.FAIL);
        msr.setTitle(title);

        msr.setSuccess(sendResult.isSuccess());
        msr.setErrorCode(sendResult.getErrorCode());
        msr.setErrorMessage(sendResult.getErrorMessage());

        msr.setReceiver(receiver);

        msr.setType(MessageTypeEnum.SMS);

        msr.setRequestIp(requestIp);

        this.messageSendRecordService.insertMessageSendRecord(msr);
        return null;
    }

    /**
     * 转换为对应的发送内容<br/>
     * <功能详细描述>
     *
     * @param contentTemplate
     * @param params
     * @return String [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String transferContent(String contentTemplate,
                                   Map<String, String> params) {
        if (!MapUtils.isEmpty(params)) {
            for (Entry<String, String> entryTemp : params.entrySet()) {
                String key = entryTemp.getKey();
                String value = entryTemp.getValue();
                contentTemplate = StringUtils.replace(contentTemplate, "#"
                        + key + "#", value);
            }
        }
        return contentTemplate;
    }

    /**
     * 发送短信<br/>
     * <功能详细描述>
     *
     * @param receiver
     * @param title
     * @param templateType
     * @param params
     * @return boolean [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean reSendSMS(String messageSendRecordId) {
        MessageSendRecord messageSendRecord = messageSendRecordService.findMessageSendRecordById(messageSendRecordId);
        AssertUtils.notNull(messageSendRecord, "messageSendRecord not find.");
        AssertUtils.isTrue(messageSendRecord.getType()
                        .equals(MessageTypeEnum.SMS),
                "messageSendRecord type not sms.");

        SendResult sendResult = null;
        try {
            sendResult = MessageSenderContext.getContext()
                    .send(messageSendRecord.getType().getKey(),
                            messageSendRecord.getReceiver(),
                            messageSendRecord.getTitle(),
                            messageSendRecord.getContent());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sendResult = new SendResult();
            sendResult.setSuccess(false);
            sendResult.setErrorCode("SIL_EXCEPTION_001");
            sendResult.setErrorMessage(e.getMessage());
        }

        //构建并持久消息发送记录
        messageSendRecord.setLastSendDate(new Date());
        messageSendRecord.setStatus(sendResult.isSuccess() ? MessageSendStatusEnum.SUCCESS
                : MessageSendStatusEnum.FAIL);
        messageSendRecord.setSuccess(sendResult.isSuccess());
        messageSendRecord.setErrorCode(sendResult.getErrorCode());
        messageSendRecord.setErrorMessage(sendResult.getErrorMessage());
        messageSendRecord.setFailCount(messageSendRecord.getFailCount() + 1);
        messageSendRecord.setRemark(messageSendRecord.getRemark()
                + ";重发");

        messageSendRecordService.updateById(messageSendRecord);

        boolean flag = sendResult.isSuccess();
        return flag;
    }
}
