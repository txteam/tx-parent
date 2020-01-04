/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月3日
 * <修改描述:>
 */
package com.tx.core.support.json;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.TypeDescriptor;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 基础数据json序列化器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseEnumJsonSerializer extends JsonSerializer<BaseEnum> {
    
    /**
     * @param value
     * @param jgen
     * @param provider
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public void serialize(BaseEnum value, JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {
        if (value == null) {
            return;
        }
        
        generator.writeStartObject();
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(value);
        Map<String, Object> objMap = new HashMap<String, Object>();
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            //判断是否忽略该节点
            String pdName = pd.getName();
            if ("class".equals(pdName) || "declaringClass".equals(pdName)
                    || pd.getReadMethod() == null) {
                continue;
            }
            //判断是否忽略该节点
            TypeDescriptor td = bw.getPropertyTypeDescriptor(pdName);
            boolean isIgnore = isIgnore(td, bw);
            if (isIgnore) {
                continue;
            }
            objMap.put(pdName, bw.getPropertyValue(pdName));
            //generator.writeFieldName(pdName);
            //try {
            //    Object object = pd.getReadMethod().invoke(value);
            //    if (object != null) {
            //        generator.writeString(object.toString());
            //    }
            //} catch (IllegalAccessException | IllegalArgumentException
            //        | InvocationTargetException e) {
            //    throw new SILException(e.getMessage(), e);
            //}
        }
        if (MapUtils.isEmpty(objMap)) {
            generator.writeObject(value);
        } else {
            generator.writeObject(objMap);
        }
        generator.writeEndObject();
    }
    
    /**
     * 判断是否忽略对应的节点<br/>
     * <功能详细描述>
     *
     * @param value
     * @param pd
     * @param pdName
     * @return boolean [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean isIgnore(TypeDescriptor td, BeanWrapper bw) {
        try {
            if (td.hasAnnotation(JsonIgnore.class)) {
                return true;
            }
            if (td.hasAnnotation(JSONField.class)) {
                JSONField a = td.getAnnotation(JSONField.class);
                if (!a.serialize()) {
                    return true;
                }
            }
        } catch (Exception e1) {
            return true;
        }
        return false;
    }
}
