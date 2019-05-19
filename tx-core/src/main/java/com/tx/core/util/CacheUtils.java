/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月17日
 * <修改描述:>
 */
package com.tx.core.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.spring.cache.CacheKey;
import com.tx.core.springmvc.support.StringToDateConverter;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CacheUtils {
    
    /**
     * 生成缓存Key
     * <功能详细描述>
     * @param beanClass
     * @param methodName
     * @param parameterTypes
     * @param args
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String generateStringCacheKey(Class<?> beanClass,
            String methodName, Class<?>[] parameterTypes, Object[] args) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append(beanClass.getSimpleName())
                .append(".")
                .append(methodName)
                .append("_");
        if (ArrayUtils.isEmpty(parameterTypes)) {
            sb.append(0).append("_");
        } else {
            sb.append(parameterTypes.length).append("_");
        }
        sb.append(Math.abs(beanClass.getName().hashCode())).append("_");
        
        //参数类型
        int hashcode1 = 0;
        if (!ArrayUtils.isEmpty(parameterTypes)) {
            int i = 1;
            for (Class<?> pt : parameterTypes) {
                hashcode1 += (Objects.hashCode(pt) * i++);
            }
        }
        sb.append(Math.abs(hashcode1)).append("_");
        
        int hashcode2 = 0;
        if (!ArrayUtils.isEmpty(args)) {
            int i = 1;
            for (Object obj : args) {
                hashcode2 += (ObjectUtils.hashCode(obj) * i++);
            }
        }
        sb.append(Math.abs(hashcode2));
        
        String cacheKey = sb.toString();
        return cacheKey;
    }
    
    /**
     * 根据方法以及实际参数生成缓存Key
     * <功能详细描述>
     * @param method
     * @param args
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String generateStringCacheKey(Method method, Object[] args) {
        AssertUtils.notNull(method, "method is null.");
        
        Class<?>[] parameterTypes = method.getParameterTypes();
        String methodName = method.getName();
        Class<?> beanClass = method.getDeclaringClass();
        
        String cacheKey = generateStringCacheKey(beanClass,
                methodName,
                parameterTypes,
                args);
        return cacheKey;
    }
    
    public static CacheKey generateCacheKey(Class<?> beanClass,
            String methodName, Class<?>[] parameterTypes, Object[] args) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(beanClass);
        cacheKey.update(methodName);
        cacheKey.updateAll(parameterTypes);
        cacheKey.updateAll(args);
        return cacheKey;
    }
    
    /**
     * 根据方法以及实际参数生成缓存Key
     * <功能详细描述>
     * @param method
     * @param args
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static CacheKey generateCacheKey(Method method, Object[] args) {
        AssertUtils.notNull(method, "method is null.");
        
        Class<?>[] parameterTypes = method.getParameterTypes();
        String methodName = method.getName();
        
        CacheKey cacheKey = generateCacheKey(method.getDeclaringClass(),
                methodName,
                parameterTypes,
                args);
        
        return cacheKey;
    }
    
    public static void main(String[] args) {
        Method m = MethodUtils.getAccessibleMethod(
                StringToDateConverter.class, "convert", String.class);
        System.out.println(m.getDeclaringClass().getName());
        System.out.println(m.getReturnType());
        System.out.println(void.class.equals(m.getReturnType()));
        
        int test71[] = new int[] { 1, 2, 3, 4, 5, 6, 7 };
        int test72[] = new int[] { 1, 2, 3, 4, 5, 6, 7 };
        
        String test81[] = new String[] { "1", "2", "3", "4", "5" };
        String test82[] = new String[] { "1", "2", "3", "4", "5" };
        
        System.out.println("insert".startsWith("insert"));
        Map<String, String> test1 = new TreeMap<String, String>();
        test1.put("test2", "test2");
        test1.put("test5", "test5");
        test1.put("test4", "test4");
        test1.put("test1", "test1");
        test1.put("test3", "test3");
        
        System.out.println(test1.toString());
        
        Map<String, String> test2 = new TreeMap<String, String>();
        test2.put("test1", "test1");
        test2.put("test3", "test3");
        test2.put("test2", "test2");
        test2.put("test5", "test5");
        test2.put("test4", "test4");
        
        System.out.println(test2.toString());
        
        Map<String, String> test3 = new HashMap<String, String>();
        test3.put("test1", "test1");
        test3.put("test3", "test3");
        test3.put("test2", "test2");
        test3.put("test5", "test5");
        test3.put("test4", "test4");
        
        System.out.println(test3.toString());
        
        for (Entry<String, String> entry : test1.entrySet()) {
            System.out.println(entry.getKey());
        }
        for (Entry<String, String> entry : test2.entrySet()) {
            System.out.println(entry.getKey());
        }
        
        Class<?>[] types = new Class<?>[] { boolean.class, int.class,
                String.class, Map.class, String.class, Integer.class,
                int[].class, String[].class };
        String cacheKey1 = generateStringCacheKey(
                StringToDateConverter.class,
                "convert",
                types,
                new Object[] { true, 1, 't', test1, "test123456",
                        Integer.valueOf(333333), test71, test81 });
        System.out.println(cacheKey1);
        
        String cacheKey2 = generateStringCacheKey(
                StringToDateConverter.class,
                "convert",
                types,
                new Object[] { true, 1, 't', test2, "test123456",
                        Integer.valueOf(333333), test72, test82 });
        System.out.println(cacheKey2);
        
        String cacheKey3 = generateStringCacheKey(
                StringToDateConverter.class,
                "convert",
                types,
                new Object[] { true, 1, 't', test3, "test123456",
                        Integer.valueOf(333333), test71, test82 });
        System.out.println(cacheKey3);
        
        CacheKey cacheKey11 = generateCacheKey(
                StringToDateConverter.class,
                "convert",
                types,
                new Object[] { true, 1, 't', test1, "test123456",
                        Integer.valueOf(333333), test71, test81 });
        System.out.println(cacheKey11.toString());
        
        CacheKey cacheKey12 = generateCacheKey(
                StringToDateConverter.class,
                "convert",
                types,
                new Object[] { true, 1, 't', test2, "test123456",
                        Integer.valueOf(333333), test72, test82 });
        System.out.println(cacheKey12.toString());
        
        CacheKey cacheKey13 = generateCacheKey(
                StringToDateConverter.class,
                "convert",
                types,
                new Object[] { true, 1, 't', test3, "test123456",
                        Integer.valueOf(333333), test71, test82 });
        System.out.println(cacheKey13.toString());
    }
}
