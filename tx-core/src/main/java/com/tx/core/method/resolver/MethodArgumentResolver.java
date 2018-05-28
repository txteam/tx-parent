/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月15日
 * <修改描述:>
 */
package com.tx.core.method.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;

import com.tx.core.method.request.InvokeRequest;

/**
 * 注入参数解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年2月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MethodArgumentResolver extends Ordered {
    
    /**
     * 方法参数解析器<br/> 
     * <功能详细描述>
     * @param parameter
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    boolean supportsParameter(MethodParameter parameter);
    
    /**
     * 返回参数值<br/>
     * <功能详细描述>
     * @param methodParameter
     * @param invokeRequest
     * @return
     * @throws Exception [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    Object resolveArgument(MethodParameter methodParameter,
            InvokeRequest invokeRequest) throws Exception;
    
}
