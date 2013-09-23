/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 业务日志容器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerSessionContext {
    
    private HttpServletRequest request;
    
    private HttpServletResponse response;
    
    private Map<String, Object> attributes;
    
    /** <默认构造函数> */
    private ServiceLoggerSessionContext() {
        super();
        this.attributes = new HashMap<String, Object>();
    }
    
    /**
     * 线程变量:当前会话容器<br/>
     * 获取到该容器后可以<br/>
     * 获取当前回话的session从而获取到相应的权限列表
     */
    private static ThreadLocal<ServiceLoggerSessionContext> context = new ThreadLocal<ServiceLoggerSessionContext>() {
        
        /**
         * @return
         */
        @Override
        protected ServiceLoggerSessionContext initialValue() {
            ServiceLoggerSessionContext csContext = new ServiceLoggerSessionContext();
            return csContext;
        }
    };
    
    /**
      * 获取业务日志容器<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return ServiceLoggerContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static ServiceLoggerSessionContext getContext() {
        ServiceLoggerSessionContext res = context.get();
        return res;
    }
    
    /**
     * 从当前线程中移除当前会话
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void remove() {
        ServiceLoggerSessionContext res = context.get();
        res.clear();
        context.remove();
    }
    
    /**
      * 初始化业务日志容器
      *<功能详细描述>
      * @param request
      * @param response
      * @param session [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void init(HttpServletRequest request,
            HttpServletResponse response) {
        context.remove();
        ServiceLoggerSessionContext localContext = context.get();
        localContext.setRequest(request);
        localContext.setResponse(response);
    }
    
    /**
     * @return 返回 request
     */
    public HttpServletRequest getRequest() {
        return request;
    }
    
    /**
     * @param 对request进行赋值
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    /**
     * @return 返回 response
     */
    public HttpServletResponse getResponse() {
        return response;
    }
    
    /**
     * @param 对response进行赋值
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    /**
      * 设置属性值到线程变量中
      *<功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }
    
    /**
      * 获取线程变量中的属性值
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object getAttribute(String key) {
        Object res = this.attributes.get(key);
        return res;
    }
    
    /**
      * 清空线程变量中对象
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void clear() {
        if (this.attributes != null) {
            this.attributes.clear();
        }
        this.request = null;
        this.response = null;
        this.attributes = null;
    }
}
