/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月31日
 * <修改描述:>
 */
package com.tx.test.method;

import java.util.HashMap;
import java.util.Map;

import com.tx.core.method.HandlerMethod;
import com.tx.core.method.util.HandlerMethodInvokeUtils;
import com.tx.test.method.service.MethodTestService;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodInvokeUtilsTest {
    
    public static void main(String[] args) throws NoSuchMethodException {
        MethodTestService service = new MethodTestService();
        
        HandlerMethod hm1 = new HandlerMethod(service, "test1");
        
        HandlerMethodInvokeUtils.invokeHandlerMethod(hm1, null, null);
        
        HandlerMethod hm2 = new HandlerMethod(service, "test2", String.class,
                String.class);
        
        System.out.println("result:" + HandlerMethodInvokeUtils
                .invokeHandlerMethod(hm2, null, null, "test2_1"));
        System.out.println("result:" + HandlerMethodInvokeUtils
                .invokeHandlerMethod(hm2, null, "test2_2"));
        
        Map<String, Object> params = new HashMap<>();
        params.put("test1", "test2-3-1");
        params.put("test2", "test2-3-2");
        System.out.println("result:" + HandlerMethodInvokeUtils
                .invokeHandlerMethod(hm2, params));
    }
}
