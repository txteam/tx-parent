/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelogger.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

/**
 * 业务日志Session容器<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerSession {
    
    
    /** 线程变量属性 */
    private Map<String, Object> attributes;
    
    /** <默认构造函数> */
    public ServiceLoggerSession() {
        super();
        this.attributes = new HashMap<>();
    }
    
    /**
     * 获取线程变量中的属性值<br/>
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
     * 设置属性值到线程变量中<br/>
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
    
    /**
     * 返回当前日志会话中所有属性<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Map<String, Object> getAttributes() {
        Map<String, Object> attMap = MapUtils.unmodifiableMap(this.attributes);
        return attMap;
    }
}
