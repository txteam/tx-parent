/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月24日
 * <修改描述:>
 */
package com.tx.core.method.util;

import java.util.Map;

import com.tx.core.method.HandlerMethod;
import com.tx.core.method.InvocableHandlerMethod;
import com.tx.core.method.request.InvokeRequest;
import com.tx.core.method.request.impl.MapInvokeRequest;

/**
  * 方法调用工具类<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月24日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public abstract class HandlerMethodInvokeUtils {
    
    /**
     * 注入HandlerMethod调用
     * <功能详细描述>
     * @param handlerMethod
     * @param params
     * @param providedArgs
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Object invokeHandlerMethod(HandlerMethod handlerMethod,
            Map<String, Object> params, Object... providedArgs) {
        InvocableHandlerMethod ihm = new InvocableHandlerMethod(handlerMethod);
        
        InvokeRequest request = new MapInvokeRequest(params);
        Object res = ihm.invokeForRequest(request, providedArgs);
        return res;
    }
}
