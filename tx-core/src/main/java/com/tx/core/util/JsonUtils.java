/*
 * Copyright 2005-2017 cqtianxin.com. All rights reserved.
 * Support: http://www.cqtianxin.com
 * License: http://www.cqtianxin.com/license
 */
package com.tx.core.util;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tx.core.exceptions.argument.ArgIllegalException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * Utils - JSON
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class JsonUtils {
    
    /** ObjectMapper */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    public static void main(String[] args) {
        System.out.println(toJson(null));
        System.out.println(toObject(toJson(null), String.class));
        System.out.println(toJson("123212312312"));
    }
    
    /**
     * 不可实例化
     */
    private JsonUtils() {
    }
    
    /**
     * 将对象转换为JSON字符串
     * @param value 对象
     * @return JSON字符串
     */
    public static String toJson(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new ArgIllegalException("对象转换为json时发生异常.", e);
        }
    }
    
    /**
     * 将JSON字符串转换为对象
     * @param json JSON字符串
     * @param valueType 类型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        AssertUtils.notNull(valueType, "valueType is null.");
        try {
            return OBJECT_MAPPER.readValue(json, valueType);
        } catch (IOException e) {
            throw new ArgIllegalException("json换为对象转时发生异常.", e);
        }
    }
    
    /**
     * 将JSON字符串转换为对象
     * 
     * @param json JSON字符串
     * @param typeReference 类型
     * @return 对象
     */
    public static <T> T toObject(String json, TypeReference<?> typeReference) {
        AssertUtils.notNull(typeReference, "typeReference is null.");
        
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new ArgIllegalException("json换为对象转时发生异常.", e);
        }
    }
    
    /**
     * 将JSON字符串转换为对象
     * 
     * @param jsonJSON字符串
     * @param javaType类型
     * @return 对象
     */
    public static <T> T toObject(String json, JavaType javaType) {
        AssertUtils.notNull(javaType, "javaType is null.");
        
        try {
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new ArgIllegalException("json换为对象转时发生异常.", e);
        }
    }
    
    /**
     * 将JSON字符串转换为树
     * 
     * @param json JSON字符串
     * @return 树
     */
    public static JsonNode toTree(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (IOException e) {
            throw new ArgIllegalException("json换为对象转时发生异常.", e);
        }
    }
    
    /**
     * 将对象转换为JSON流
     * 
     * @param writer Writer
     * @param value 对象
     */
    public static void writeValue(Writer writer, Object value) {
        AssertUtils.notNull(writer, "writer is null.");
        
        try {
            OBJECT_MAPPER.writeValue(writer, value);
        } catch (IOException e) {
            throw new ArgIllegalException("json换为对象转时发生异常.", e);
        }
    }
    
    /**
     * 构造类型
     * @param type 类型
     * @return 类型
     */
    public static JavaType constructType(Type type) {
        AssertUtils.notNull(type, "type is null.");
        
        return TypeFactory.defaultInstance().constructType(type);
    }
    
    /**
     * 构造类型
     * @param typeReference 类型
     * @return 类型
     */
    public static JavaType constructType(TypeReference<?> typeReference) {
        AssertUtils.notNull(typeReference, "typeReference is null.");
        
        return TypeFactory.defaultInstance().constructType(typeReference);
    }
    
}