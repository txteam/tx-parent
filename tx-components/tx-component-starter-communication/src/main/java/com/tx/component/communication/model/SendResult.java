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

import io.swagger.annotations.ApiModelProperty;

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
    /** 返回码，0-成功，1-通用错误， 其他-详见错误码定义 */
    //之所以只放错误码，如果多一个boolean字段，感觉和错误码重复了，明明可以只用一个字段表示，没有必要引入更多的字段表示
    @ApiModelProperty(value = "返回码，0-成功，非0-失败", required = true, example = "0")
    private int code = -1;
    
    /** 提示消息 */
    @ApiModelProperty(value = "返回消息", required = true, example = "成功")
    private String message;
    
    /** 返回的数据 */
    @ApiModelProperty(value = "返回数据", required = true)
    private final Map<String, Object> attributes = new HashMap<>();
    
    /**
     * @return 返回 code
     */
    public int getCode() {
        return code;
    }
    
    /**
     * @param 对code进行赋值
     */
    public void setCode(int code) {
        this.code = code;
    }
    
    /**
     * @return 返回 message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * @param 对message进行赋值
     */
    public void setMessage(String message) {
        this.message = message;
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
