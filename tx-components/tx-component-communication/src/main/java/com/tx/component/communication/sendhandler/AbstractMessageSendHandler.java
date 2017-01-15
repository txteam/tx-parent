/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月31日
 * <修改描述:>
 */
package com.tx.component.communication.sendhandler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import com.tx.component.communication.model.SendMessage;
import com.tx.component.communication.model.SendResult;
import com.tx.component.communication.senddialect.MessageSendDialect;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 抽象消息发送处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年12月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractMessageSendHandler implements MessageSendHandler,
        InitializingBean {
    
    /** 消息对应的方言类 */
    protected MessageSendDialect dialect;
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(this.dialect, "dialect is null.");
        AssertUtils.notEmpty(type(), "type() is empty.");
    }
    
    /**
     * @param message
     * @return
     */
    @Override
    public final boolean supports(SendMessage message) {
        if (message.getType() != null
                && type().equals(message.getType().toUpperCase())) {
            if (this.dialect.supports(message)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    
    /**
     * @param message
     * @return
     */
    @Override
    public SendResult send(SendMessage message) {
        SendResult result = this.dialect.send(message);
        return result;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
    /**
     * @param 对dialect进行赋值
     */
    public void setDialect(MessageSendDialect dialect) {
        this.dialect = dialect;
    }
}
