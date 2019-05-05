/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月29日
 * <修改描述:>
 */
package com.tx.core.spring.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

import com.tx.core.TxConstants;

/**
 * 业务层拦截器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceSupportCacheInterceptor implements MethodInterceptor {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory
            .getLogger(ServiceSupportCacheInterceptor.class);
    
    /** 缓存 */
    private Cache cache;
    
    /** <默认构造函数> */
    public ServiceSupportCacheInterceptor() {
        super();
    }
    
    /** <默认构造函数> */
    public ServiceSupportCacheInterceptor(Cache cache) {
        super();
        this.cache = cache;
    }
    
    /**
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (this.cache == null) {
            return invocation.proceed();
        }
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();
        boolean isReturnVoid = void.class.equals(method.getReturnType());
        
        Object res = null;
        if (isUseCache(method, args) && !isReturnVoid) {
            //生成对应的缓存值
            String cacheKey = generateCacheKey(method, args);
            //生成缓存Key
            ValueWrapper vw = cache.get(cacheKey);
            if (vw == null || vw.get() == null) {
                //如果缓存里值不存在，则调用方法进行获取<br/>
                logger.debug("未命中缓存：缓存:{} 缓存key:{}",
                        this.cache.getName(),
                        cacheKey);
                res = invocation.proceed();
                if (res != null) {
                    cache.put(cacheKey, res);
                }
            } else {
                //如果有值则获取缓存值
                logger.debug("从缓存中获取: 命中缓存：缓存:{} 缓存key:{}",
                        this.cache.getName(),
                        cacheKey);
                res = vw.get();
            }
        } else if (isClearCache(method, args)) {
            cache.clear();
            res = invocation.proceed();
        } else {
            res = invocation.proceed();
        }
        return res;
    }
    
    /**
     * 判断当前方法是否应清空缓存<br/>
     * <功能详细描述>
     * @param method
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected boolean isClearCache(Method method, Object[] args) {
        String methodName = method.getName();
        boolean isNeedClearCache = methodName.startsWith("save")
                || methodName.startsWith("insert")
                || methodName.startsWith("move") || methodName.startsWith("add")
                || methodName.startsWith("update")
                || methodName.startsWith("patch")
                || methodName.startsWith("change")
                || methodName.startsWith("del")
                || methodName.startsWith("delete")
                || methodName.startsWith("disable")
                || methodName.startsWith("enable");
        return isNeedClearCache;
    }
    
    /**
     * 判断是否是查询方法<br/>
     *    如果是查询方法，将进入缓存处理逻辑<br/>
     * <功能详细描述>
     * @param methodName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected boolean isUseCache(Method method, Object[] args) {
        String methodName = method.getName();
        boolean isQueryMethod = methodName.startsWith("query")
                || methodName.startsWith("find")
                || methodName.startsWith("list")
                || methodName.startsWith("exist")
                || methodName.startsWith("is");
        return isQueryMethod;
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
    protected String generateCacheKey(Method method, Object[] args) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String methodName = method.getName();
        
        String cacheKey = generateCacheKey(method.getDeclaringClass(),
                methodName,
                parameterTypes,
                args);
        return cacheKey;
    }
    
    /**
     * 生成缓存Key值<br/>
     * <功能详细描述>
     * @param className
     * @param methodName
     * @param args
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private String generateCacheKey(Class<?> beanClass, String methodName,
            Class<?>[] parameterTypes, Object[] args) {
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
            for (Class<?> pt : parameterTypes) {
                hashcode1 += Objects.hashCode(pt);
            }
        }
        sb.append(Math.abs(hashcode1)).append("_");
        
        int hashcode2 = 0;
        if (!ArrayUtils.isEmpty(args)) {
            for (Object arg : args) {
                hashcode2 += Objects.hashCode(arg);
            }
        }
        sb.append(Math.abs(hashcode2));
        
        String cacheKey = sb.toString();
        return cacheKey;
    }
    
    /**
      * 生成缓存值<br/>
      * <功能详细描述>
      * @param arg
      * @param cachedSet 已经缓存的对象<br/>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    protected int generateCacheKey(Object arg, Set<Object> cachedSet) {
        int hashCode = 0;
        if (arg == null) {
            hashCode = "NULL".hashCode();
        } else if (arg instanceof Map && !cachedSet.contains(arg)) {
            cachedSet.add(arg);//避免循环引用
            hashCode = generateMapCache((Map) arg, cachedSet);
        } else if (arg instanceof List && !cachedSet.contains(arg)) {
            cachedSet.add(arg);//避免循环引用
            hashCode = generateListCache((List) arg, cachedSet);
        } else if (arg instanceof Set && !cachedSet.contains(arg)) {
            cachedSet.add(arg);//避免循环引用
            hashCode = generateSetCache((Set) arg, cachedSet);
        } else if (TypeUtils.isArrayType(arg.getClass())) {
            cachedSet.add(arg);//避免循环引用
            List arrayList = Arrays.asList(arg);
            hashCode = generateListCache(arrayList, cachedSet);
        } else {
            hashCode = arg.hashCode();
        }
        return hashCode;
    }
    
    /**
      * 生成Map的缓存Key值
      * <功能详细描述>
      * @param argMap
      * @param cachedSet
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected int generateMapCache(Map<?, ?> argMap, Set<Object> cachedSet) {
        if (MapUtils.isEmpty(argMap)) {
            return "EMPTY_MAP".hashCode();
        }
        int res = 0;
        TreeMap<?, ?> treeMap = new TreeMap<>(argMap);
        for (Entry<?, ?> entryTemp : treeMap.entrySet()) {
            String val = "" + generateCacheKey(entryTemp.getKey(), cachedSet)
                    + ":" + generateCacheKey(entryTemp.getValue(), cachedSet)
                    + ";";
            res += val.hashCode();
        }
        return res;
    }
    
    /**
      * 生成Set集合的缓存Key值
      * <功能详细描述>
      * @param set
      * @param cachedSet
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected int generateSetCache(Set<?> set, Set<Object> cachedSet) {
        if (CollectionUtils.isEmpty(set)) {
            return "EMPTY_SET".hashCode();
        }
        int res = 0;
        Set<?> treeSet = new TreeSet<>(set);
        for (Object temp : treeSet) {
            res += generateCacheKey(temp, cachedSet);
        }
        return res;
    }
    
    /**
      * 生成List的缓存Key值<br/>
      * <功能详细描述>
      * @param set
      * @param cachedSet
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected int generateListCache(List<?> set, Set<Object> cachedSet) {
        if (CollectionUtils.isEmpty(set)) {
            return "EMPTY_LIST".hashCode();
        }
        int res = 0;
        TreeList treeList = new TreeList(set);
        for (Object temp : treeList) {
            res += generateCacheKey(temp, cachedSet);
        }
        return res;
    }
    
    /**
     * @param 对cache进行赋值
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }
    
    //    public static void main(String[] args) {
    //        
    //        Method m = MethodUtils.getAccessibleMethod(
    //                ServiceSupportCacheInterceptor.class, "setCache", Cache.class);
    //        System.out.println(m.getDeclaringClass().getName());
    //        System.out.println(m.getReturnType());
    //        System.out.println(void.class.equals(m.getReturnType()));
    //        
    //        System.out.println("insert".startsWith("insert"));
    //        Map<String, String> test1 = new TreeMap<String, String>();
    //        test1.put("test2", "test2");
    //        test1.put("test5", "test5");
    //        test1.put("test4", "test4");
    //        test1.put("test1", "test1");
    //        test1.put("test3", "test3");
    //        
    //        System.out.println(test1.toString());
    //        
    //        Map<String, String> test2 = new TreeMap<String, String>();
    //        test2.put("test1", "test1");
    //        test2.put("test3", "test3");
    //        test2.put("test2", "test2");
    //        test2.put("test5", "test5");
    //        test2.put("test4", "test4");
    //        
    //        System.out.println(test2.toString());
    //        
    //        Map<String, String> test3 = new HashMap<String, String>();
    //        test3.put("test1", "test1");
    //        test3.put("test3", "test3");
    //        test3.put("test2", "test2");
    //        test3.put("test5", "test5");
    //        test3.put("test4", "test4");
    //        
    //        System.out.println(test3.toString());
    //        
    //        for (Entry<String, String> entry : test1.entrySet()) {
    //            System.out.println(entry.getKey());
    //        }
    //        for (Entry<String, String> entry : test2.entrySet()) {
    //            System.out.println(entry.getKey());
    //        }
    //        
    //        Class<?>[] types = new Class<?>[] { boolean.class, int.class,
    //                String.class, Map.class, String.class, Integer.class };
    //        ServiceSupportCacheInterceptor t = new ServiceSupportCacheInterceptor();
    //        String cacheKey1 = t.generateCacheKey(
    //                ServiceSupportCacheInterceptor.class,
    //                "queryList",
    //                types,
    //                new Object[] { true, 1, 't', test1, "test123456",
    //                        Integer.valueOf(333333) });
    //        System.out.println(cacheKey1);
    //        
    //        String cacheKey2 = t.generateCacheKey(
    //                ServiceSupportCacheInterceptor.class,
    //                "queryList",
    //                types,
    //                new Object[] { true, 1, 't', test2, "test123456",
    //                        Integer.valueOf(333333) });
    //        System.out.println(cacheKey2);
    //        
    //        String cacheKey3 = t.generateCacheKey(
    //                ServiceSupportCacheInterceptor.class,
    //                "queryList",
    //                types,
    //                new Object[] { true, 1, 't', test3, "test123456",
    //                        Integer.valueOf(333333) });
    //        System.out.println(cacheKey3);
    //    }
}
