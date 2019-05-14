/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月7日
 * <修改描述:>
 */
package com.tx.core.support.json;

import com.alibaba.fastjson.JSONObject;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * attributesAble Listener<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
class AttributesAbleJSONObjectChangeListener extends JSONObjectChangeListener {
    
    /** 菜单对象 */
    private JSONAttributesSupport object;
    
    /** <默认构造函数> */
    public AttributesAbleJSONObjectChangeListener(
            JSONAttributesSupport object) {
        AssertUtils.notNull(object, "object is null.");
        
        this.jsonObject = JSONObject.parseObject(object.getAttributes());
        if (this.jsonObject == null) {
            this.jsonObject = new JSONObject();
            object.setAttributes(this.jsonObject.toJSONString());
        }
        this.object = object;
    }
    
    /**
     * JSONObject值变化时的回调方法<brf/>
     */
    @Override
    public void callbackOnChange() {
        object.setAttributes(this.jsonObject.toJSONString());
    }
    
    //    public static void main(String[] args) {
    //        JSONObject jsonObject = JSONObject.parseObject(null);
    //        if (jsonObject == null) {
    //            jsonObject = new JSONObject();
    //            System.out.println(jsonObject.toJSONString());
    //        }
    //        jsonObject = JSONObject.parseObject(jsonObject.toJSONString());
    //        System.out.println(jsonObject == null);
    //        //this.object = object;
    //    }
}
