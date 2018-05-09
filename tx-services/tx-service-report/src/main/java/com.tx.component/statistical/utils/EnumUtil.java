package com.tx.component.statistical.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SEELE on 2016/7/1.
 */
public class EnumUtil {
    public static <T> Map<String, Enum<?>> parseEnum(String className)
            throws ClassNotFoundException {
        Class cls = Class.forName(className);
        return parseEnum(cls);
    }

    public static <T> Map<String, Enum<?>> parseEnum(Class<T> ref) {
        Map<String, Enum<?>> map = new LinkedHashMap<String, Enum<?>>();
        if (ref.isEnum()) {
            T[] ts = ref.getEnumConstants();
            for (T t : ts) {
                Enum<?> tempEnum = (Enum<?>) t;
                tempEnum.name();
                map.put(tempEnum.name(), tempEnum);
            }
        }
        return map;
    }

    public static <T> Enum<?>[] parseEnum2Arr(String className)
            throws ClassNotFoundException {
        Class cls = Class.forName(className);
        return parseEnum2Arr(cls);
    }

    public static <T> Enum<?>[] parseEnum2Arr(Class<T> ref) {
        Map<String, Enum<?>> map = new LinkedHashMap<String, Enum<?>>();
        if (ref.isEnum()) {
            T[] ts = ref.getEnumConstants();
            return (Enum<?>[]) ts;
        }
        return null;
    }

    public static String getFieldValue(Enum tempEnum, String fieldName) {

        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(tempEnum);
        if(bw.isReadableProperty(fieldName) ){
            return (String)bw.getPropertyValue(fieldName);
        }
//        Class cls = tempEnum.getClass();
//        Field[] fields = cls.getDeclaredFields();
//        for (Field field : fields) {
//            if (field.getName().equalsIgnoreCase(fieldName)) {
//                field.setAccessible(true);
//                return (String) field.get(tempEnum);
//            }
//        }
        return null;
    }
    
}
