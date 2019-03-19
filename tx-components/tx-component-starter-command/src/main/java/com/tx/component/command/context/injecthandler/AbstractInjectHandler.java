/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年12月7日
 * <修改描述:>
 */
package com.tx.component.command.context.injecthandler;

import com.tx.component.command.context.CommandRequest;
import com.tx.component.command.context.InjectHandler;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.typereference.ParameterizedTypeReference;

/**
 * 抽象的注入器实现<br/>
 * <功能详细描述>
 * 
 * @author  PengQY
 * @version  [版本号, 2016年12月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractInjectHandler<PR extends Object> extends
        ParameterizedTypeReference<PR> implements InjectHandler {
    
    /**
     * @param requestType
     * @return
     */
    @Override
    public final boolean supports(Class<?> requestType) {
        if (matches(requestType)) {
            return true;
        }
        return false;
    }
    
    /**
      * 是否匹配<br/>
      * <功能详细描述>
      * @param requestType
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected boolean matches(Class<?> requestType) {
        if (getType().isAssignableFrom(requestType)) {
            return true;
        }
        return false;
    }
    
    /**
     * @param request
     */
    @SuppressWarnings("unchecked")
    @Override
    public void inject(CommandRequest request) {
        AssertUtils.isAssignable(getType(),
                request.getClass(),
                "request.type:{} should be subtype of:{}",
                new Object[] { request.getClass(), getType() });
        
        //注入对象
        doInject((PR) request);
    }
    
    /**
      * 请求注入<br/>
      * <功能详细描述>
      * @param request
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract void doInject(PR request);
}
