package com.tx.component.communication.controller;

import com.github.pagehelper.PageInfo;
import com.tx.component.communication.model.MessageSendRecord;
import com.tx.component.communication.model.MessageSendStatusEnum;
import com.tx.component.communication.model.MessageTypeEnum;
import com.tx.component.communication.service.MessageSendRecordService;
import com.tx.component.communication.service.SMSMessageSendService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2018/05/07]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@Controller
@RequestMapping("smsSender")
public class SMSSenderController {
    
    @Resource
    private SMSMessageSendService smsMessageSendService;

    @Resource
    private MessageSendRecordService messageSendRecordService;

    /**
     * 消息发送
     *
     * @param receiver
     * @param title
     * @param smsTemplateContent
     * @param smsTemplateCode
     * @param requestIp
     * @param params
     * @param request
     * @return
     */
    @RequestMapping("/sendSMS")
    @ResponseBody
    @ApiOperation(value = "短信发送", notes = "根据定义的短信模板，发送短信")
    public Map<String, String> sendSms(String receiver,
                                       String title,
                                       String smsTemplateContent,
                                       String smsTemplateCode,
                                       String requestIp,
//                           @PathVariable String dialect,
                                       @RequestParam Map<String, String> params,
                                       @RequestParam MultiValueMap<String, String> request) {

        try {
            smsMessageSendService.sendSMS(receiver, title, smsTemplateContent, smsTemplateCode, requestIp, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @RequestMapping("/toQueryMessageSendRecordPagedList")
//    public String toQueryMessageSendRecordPagedList() {
//        return "queryMessageSendRecordPagedList";
//    }


    /**
     * 查询消息
     *
     * @param request
     * @param type
     * @param receiver
     * @param maxCreateDate
     * @param status
     * @param minCreateDate
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryMessageSendRecordPagedList")
    @ApiOperation(value = "消息发送查询", notes = "消息发送分页查询")
    public PageInfo<MessageSendRecord> queryMessageSendRecordPagedList(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "type", required = false) MessageTypeEnum type,
            @RequestParam(value = "receiver", required = false) String receiver,
            @RequestParam(value = "maxCreateDate", required = false) Date maxCreateDate,
            @RequestParam(value = "status", required = false) MessageSendStatusEnum status,
            @RequestParam(value = "minCreateDate", required = false) Date minCreateDate,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageInfo<MessageSendRecord> resPagedList = this.messageSendRecordService.queryMessageSendRecordPagedList(type,
                receiver,
                maxCreateDate,
                status,
                minCreateDate, pageIndex, pageSize);
        return resPagedList;
    }
}
