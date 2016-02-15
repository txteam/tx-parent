/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月15日
 * <修改描述:>
 */
package com.tx.core.support.methodinvoke.support;

import org.springframework.core.MethodParameter;

/**
 * 注入参数解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年2月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface InvokeArgumentResolver {
    
    /**
     * 在解析器无法知道如何去处理对应的方法参数时返回的对象
     */
    Object UNRESOLVED = new Object();
    
    /**
     * 使用InvokeRequest中的参数值解析对应的methodParameter的取值
     * @param methodParameter the handler method parameter to resolve
     * @param webRequest the current web request, allowing access to the native request as well
     * @return the argument value, or {@code UNRESOLVED} if not resolvable
     * @throws Exception in case of resolution failure
     */
    Object resolveArgument(MethodParameter methodParameter,
            InvokeRequest invokeRequest) throws Exception;
    
}
