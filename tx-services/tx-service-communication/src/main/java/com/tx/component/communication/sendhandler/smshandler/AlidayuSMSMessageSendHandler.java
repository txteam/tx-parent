///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2015年12月31日
// * <修改描述:>
// */
//package com.tx.component.communication.sendhandler.smshandler;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.core.Ordered;
//
//import com.tx.component.communication.model.SendMessage;
//import com.tx.component.communication.model.SendResult;
//import com.tx.component.communication.senddialect.MessageSendDialect;
//import com.tx.component.communication.sendhandler.MessageSendHandler;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * 抽象消息发送处理器<br/>
// *     //TODO:需重构逻辑，将短信发送模板匹配的逻辑移到SendHandler中，间接支持support的逻辑
// * <功能详细描述>
// *
// * @author  Administrator
// * @version  [版本号, 2015年12月31日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Deprecated
//public class AlidayuSMSMessageSendHandler implements MessageSendHandler,
//        InitializingBean {
//
//    /** 消息类型 */
//    private final String messageType = "SMS";
//
//    /** 消息对应的方言类 */
//    private MessageSendDialect dialect;
//
//    /**
//     * @return
//     */
//    @Override
//    public String type() {
//        return messageType;
//    }
//
//    /**
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        AssertUtils.notNull(dialect, "dialect is null.");
//        AssertUtils.notEmpty(messageType, "messageType is null.");
//    }
//
//    /**
//     * @param message
//     * @return
//     */
//    @Override
//    public final boolean supports(SendMessage message) {
//        if (messageType.equals(message.getType())) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * @param message
//     * @return
//     */
//    @Override
//    public SendResult send(SendMessage message) {
//        SendResult result = this.dialect.send(message);
//        return result;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE;
//    }
//
//    /**
//     * @param 对dialect进行赋值
//     */
//    public void setDialect(MessageSendDialect dialect) {
//        this.dialect = dialect;
//    }
//}
