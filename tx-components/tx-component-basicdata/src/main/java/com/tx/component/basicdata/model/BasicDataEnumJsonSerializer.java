/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月14日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;

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
 * @author Administrator
 * @version [版本号, 2017年1月14日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BasicDataEnumJsonSerializer extends JsonSerializer<BasicDataEnum> {

    /**
     * @param value
     * @param jgen
     * @param provider
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public void serialize(BasicDataEnum value, JsonGenerator generator,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {
        if (value == null) {
            return;
        }

        generator.writeStartObject();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(value.getClass());
        for (PropertyDescriptor pd : pds) {
            String pdName = pd.getName();
            if ("class".equals(pdName) || "declaringClass".equals(pdName)) {
                continue;
            }
            //判断是否忽略该节点
            boolean isIgnore = isIgnore(value, pd);
            if (isIgnore) {
                continue;
            }
            generator.writeFieldName(pdName);
            try {
                Object object = pd.getReadMethod().invoke(value);
                if (object != null) {
                    generator.writeString(object.toString());
                }
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new SILException(e.getMessage(), e);
            }
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
    private boolean isIgnore(BasicDataEnum value, PropertyDescriptor pd) {
        try {
            Method method = pd.getReadMethod();
            if (method == null || method.isAnnotationPresent(JsonIgnore.class)) {
                return true;
            }
            String pdName = pd.getName();
            Field field = FieldUtils.getField(value.getClass(), pdName, true);
            if (field != null && field.isAnnotationPresent(JsonIgnore.class)) {
                return true;
            }
        } catch (Exception e1) {
            return true;
        }
        return false;
    }
}
