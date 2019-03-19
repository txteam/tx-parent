/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年7月5日
 * <修改描述:>
 */
package com.tx.component.security.interceptor;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 在请求链中需要传递的Header参数集<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年7月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FeignTokenInterceptor implements RequestInterceptor {
    
    /** 日志记录器 */
    private Logger logger = LoggerFactory
            .getLogger(FeignTokenInterceptor.class);
    
    /**
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取请求实例
        HttpServletRequest request = getHttpServletRequest();
        
        //token值过滤
        requestTemplate.header("token",
                getHeaders(request).get("token"));
    }
    
    /**
     * 取得HttpServletRequest实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return HttpServletRequest [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
        } catch (Exception e) {
            logger.error("getHttpServletRequest error.", e);
            throw e;
        }
    }
    
    /**
     * 取得请求中的Header值<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return Map<String,String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            
            map.put(key, value);
        }
        return map;
    }
}
