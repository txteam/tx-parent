/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelogger.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.support.ResourceHolderSupport;

/**
 * 业务日志Session容器<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerSession extends ResourceHolderSupport{
    
    /** 线程变量属性 */
    private Map<String, Object> attributes;
    
    /**
     * 获取业务日志容器<br/>
     * 
     * @return [参数说明]
     *         
     * @return ServiceLoggerContext [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static ServiceLoggerSession getContext() {
        ServiceLoggerSession res = context.get();
        return res;
    }
    
    /**
     * 初始化业务日志容器
     * 
     * @param request
     * @param response
     * @param session [参数说明]
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void init() {
        context.remove();
        
        ServiceLoggerSession localContext = context.get();
    }
    
    /**
     * 从当前线程中移除当前会话
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void remove() {
        ServiceLoggerSession res = context.get();
        res.clear();
        
        context.remove();
    }
    
    private ServiceLoggerSession() {
        super();
        
        this.attributes = new HashMap<String, Object>();
    }
    
    /**
     * 清空线程变量中对象
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
    
    /**
     * 获取线程变量中的属性值
     * 
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
     * 设置属性值到线程变量中
     * 
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
}
