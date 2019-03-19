/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月16日
 * <修改描述:>
 */
package com.tx.component.command.context.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.command.context.CommandContext;
import com.tx.component.command.context.CommandReceiver;
import com.tx.component.command.context.CommandRequest;
import com.tx.component.command.context.CommandResponse;
import com.tx.component.command.context.request.AbstractRequest;
import com.tx.core.util.typereference.ParameterizedTypeReference;

/**
 * 客户账户交易处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年1月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractReceiver<PR extends AbstractRequest> extends
        ParameterizedTypeReference<PR> implements CommandReceiver<PR> {
    
    protected Logger logger = LoggerFactory.getLogger(CommandContext.class);
    
    /**
     * 请求类型<br/>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends CommandRequest> getRequestType() {
        Class<? extends CommandRequest> requestType = (Class<? extends CommandRequest>) getRawType();
        return requestType;
    }
    
    /**
     * @param request
     * @return
     */
    @Override
    public final boolean supports(CommandRequest request) {
        if (!getRequestType().isInstance(request)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        boolean flag = isMatch((PR) request);
        return flag;
    }
    
    /**
      * 判断是否匹配
      * <功能详细描述>
      * @param request
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected boolean isMatch(PR request) {
        return true;
    }
    
    /**
     * 前置处理
     * @param request
     * @param response
     * @return
     */
    @Override
    public boolean preHandle(PR request, CommandResponse response) {
        return true;
    }
    
    /**
      * 前置处理：验证<br/>
      * <功能详细描述>
      * @param request
      * @param response [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void validate(PR request, CommandResponse response) {
        return;
    }
    
    /**
     * @param request
     * @param response
     * @param e
     */
    @Override
    public void afterCompletion(PR request, CommandResponse response,
            Throwable e) {
    }
}
