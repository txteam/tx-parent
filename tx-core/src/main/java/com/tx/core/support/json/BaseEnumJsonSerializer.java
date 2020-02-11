/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月3日
 * <修改描述:>
 */
package com.tx.core.support.json;

import java.beans.PropertyDescriptor;
import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.TypeDescriptor;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tx.core.exceptions.SILException;

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
            generator.writeFieldName(pdName);
            Object obj = bw.getPropertyValue(pdName);
            Class<?> clazz = bw.getPropertyType(pdName);
            if (BeanUtils.isSimpleValueType(clazz)) {
                if (obj == null) {
                    generator.writeNull();
                } else {
                    if (CharSequence.class.isInstance(obj)) {
                        generator.writeString(obj.toString());
                    } else if (Number.class.isInstance(obj)) {
                        generator.writeNumber(obj.toString());
                    } else if (Boolean.class.isInstance(obj)) {
                        generator.writeBoolean((Boolean)obj);
                    }
                }
                continue;
            }
            throw new SILException("枚举对象属性为复杂对象，不能使用该json序列化转换器.");
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
    
    //public static void main(String[] args) {
    //    System.out.println(Number.class.isAssignableFrom(int.class));
    //    System.out.println(JsonUtils.toJson(1));
    //    System.out.println(JsonUtils.toJson("12312asdfasdf"));
    //    System.out.println(JsonUtils.toJson(111111111111111l));
    //    System.out.println(JsonUtils.toJson(true));
    //}
}
