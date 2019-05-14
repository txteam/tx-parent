/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月17日
 * <修改描述:>
 */
package com.tx.core.support.json;

import com.alibaba.fastjson.JSONObject;

/**
 * 支持json_attributes的接口实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface JSONAttributesSupport {
    
    /**
     * 获取额外属性<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getAttributes();
    
    /**
     * 设置额外属性<br/>
     * <功能详细描述>
     * @param attributes [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setAttributes(String attributes);
    
    /**
     * 获取属性对应的JSONObject对象<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return JSONObject [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default JSONObject getAttributeJSONObject() {
        AttributesAbleJSONObjectChangeListener listener = new AttributesAbleJSONObjectChangeListener(
                this);
        JSONObject json = JSONObjectChangeListener.newProxy(listener);
        
        return json;
    }
}
