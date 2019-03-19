/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年6月2日
 * <修改描述:>
 */
package com.tx.component.command.context;

import java.util.List;

import org.springframework.core.Ordered;

/**
  * 请求处理选择器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年6月2日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ReceiverSelector extends Ordered {
    
    /**
     * 获取支持的操作请求类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<? extends CommandRequest> getRequestType();
    
    /**
     * 前置处理<br/>
     *     可做有一些比如校验请求合法性的功能<br/>
     * <功能详细描述>
     * @param request
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public <PR extends CommandRequest> CommandReceiver<PR> select(CommandRequest request,
            List<CommandReceiver<? extends CommandRequest>> receivers);
    
}
