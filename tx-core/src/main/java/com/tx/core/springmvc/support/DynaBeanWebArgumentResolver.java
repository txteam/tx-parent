/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-4
 * <修改描述:>
 */
package com.tx.core.springmvc.support;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;


 /**
  * 让springMVC支持用动态bean接收参数
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-4]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DynaBeanWebArgumentResolver implements WebArgumentResolver{

    /**
     * @param methodParameter
     * @param webRequest
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
            NativeWebRequest webRequest) throws Exception {
        if(methodParameter == null){
            
        }
        return null;
    }
}
