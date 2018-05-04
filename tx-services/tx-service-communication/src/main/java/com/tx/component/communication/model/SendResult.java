/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月18日
 * <修改描述:>
 */
package com.tx.component.communication.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 发送结果<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年12月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SendResult {
    
    /** 发送是否成功 */
    private boolean success;
    
    /** 错误编码 */
    private String errorCode;
    
    /** 错误消息 */
    private String errorMessage;
    
    /** 其他消息 */
    private final Map<String, Object> attributes = new HashMap<>();
    
    /**
     * @return 返回 success
     */
    public boolean isSuccess() {
        return success;
    }
    
    /**
     * @param 对success进行赋值
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /**
     * @return 返回 errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * @param 对errorCode进行赋值
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    /**
     * @return 返回 errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * @param 对errorMessage进行赋值
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    /**
      * 获取属性集合<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    /**
      * 获取属性key集合<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Set<String> getAttribateKeySet() {
        return attributes.keySet();
    }
    
    /**
      * 设置key值
      * <功能详细描述>
      * @param key
      * @param value
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object setAttribute(String key, Object value) {
        Object oldValue = this.attributes.put(key, value);
        return oldValue;
    }
    
    /**
      * 获取key对应的值<br/>
      * <功能详细描述>
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
      * 获取容器中是否含有某key 
      * <功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean containsKey(String key) {
        boolean res = this.attributes.containsKey(key);
        return res;
    }
}
