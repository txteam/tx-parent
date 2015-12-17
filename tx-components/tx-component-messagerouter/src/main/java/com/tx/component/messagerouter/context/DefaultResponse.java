/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月19日
 * 项目： com.tx.router
 */
package com.tx.component.messagerouter.context;

import java.util.HashMap;
import java.util.Map;

import com.tx.core.util.ObjectUtils;

/**
 * 消息路由服务-默认实现的返回器
 * 
 * @author rain
 * @version [版本号, 2015年11月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DefaultResponse<T> implements MRSResponse {
    
    /** 获取消息返回的主体(一般是报文或者接口返回的信息(一般都是文本,json,xml)) */
    protected T body;
    
    /** 返回值 */
    protected Map<String, Object> responseValues = new HashMap<String, Object>();
    
    /**
     * 
     * 判断返回值是否存在
     *
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public boolean containsKey(String key) {
        return responseValues.containsKey(key);
    }
    
    @Override
    public T getBody() {
        return body;
    }
    
    @Override
    public Object getValue(String key) {
        return responseValues.get(key);
    }
    
    /**
     * 
     * 获取返回值
     *
     * @param key 主键
     * @param clazz 返回值类型
     *            
     * @return T 返回值
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    @SuppressWarnings("unchecked")
    public <V> V getValue(String key, Class<V> clazz) {
        return (V) getValue(key);
    }
    
    /**
     * 
     * 获取返回值
     *
     * @param key
     * @return
     * 
     * @return Boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    public Boolean getValueOfBoolean(String key) {
        return ObjectUtils.toBoolean(getValue(key));
    }
    
    /**
     * 
     * 获取返回值
     *
     * @param key 主键
     *            
     * @return Integer 返回值
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public Number getValueOfNumber(String key) {
        return ObjectUtils.toNumber(getValue(key));
    }
    
    /**
     * 
     * 获取返回值
     *
     * @param key 主键
     *            
     * @return String 返回值
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public String getValueOfString(String key) {
        Object value = getValue(key);
        return value == null ? null : String.valueOf(value);
    }
    
    @Override
    public void put(String key, Object value) {
        responseValues.put(key, value);
    }
    
    @Override
    public void remove(String key) {
        responseValues.remove(key);
    }
    
    /** 获取消息返回的主体(一般是报文或者接口返回的信息(一般都是文本,json,xml)) */
    public void setBody(T body) {
        this.body = body;
    }
}
