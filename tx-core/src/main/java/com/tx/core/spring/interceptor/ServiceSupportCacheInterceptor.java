/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月29日
 * <修改描述:>
 */
package com.tx.core.spring.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ObjectUtils;

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
    public static String generateCacheKey(Method method, Object[] args) {
        AssertUtils.notNull(method, "method is null.");
        
        Class<?>[] parameterTypes = method.getParameterTypes();
        String methodName = method.getName();
        
        String cacheKey = doGenerateCacheKey(method.getDeclaringClass(),
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
    private static String doGenerateCacheKey(Class<?> beanClass,
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
     * @param 对cache进行赋值
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }
    
    //        public static void main(String[] args) {
    //            int test1[] = new int[] { 1, 2, 3, 4, 5, 6, 7 };
    //            int test2[] = new int[] { 1, 2, 3, 4, 5, 6, 7 };
    //            int test3[] = new int[] { 1, 2, 3, 4, 5, 6, 7 };
    //            
    //            System.out.println(Objects.hashCode(test1));
    //            System.out.println(Objects.hashCode(test2));
    //            System.out.println(Objects.hashCode(test3));
    //            
    //            System.out.println(Arrays.hashCode(test1));
    //            System.out.println(Arrays.hashCode(test2));
    //            System.out.println(Arrays.hashCode(test3));
    //            
    //            String test21[] = new String[] { "1", "2", "3", "4", "5" };
    //            String test22[] = new String[] { "1", "2", "3", "4", "5" };
    //            String test23[] = new String[] { "1", "2", "3", "4", "5" };
    //            
    //            System.out.println(Objects.hashCode(test21));
    //            System.out.println(Objects.hashCode(test22));
    //            System.out.println(Objects.hashCode(test23));
    //            
    //            System.out.println(Arrays.hashCode(test21));
    //            System.out.println(Arrays.hashCode(test22));
    //            System.out.println(Arrays.hashCode(test23));
    //            
    //            List<String> test11 = Arrays.asList("1", "2", "3", "4", "5");
    //            List<String> test12 = Arrays.asList("1", "2", "3", "4", "5");
    //            List<String> test13 = Arrays.asList("1", "2", "3", "4", "5");
    //            
    //            System.out.println(Objects.hashCode(test11));
    //            System.out.println(Objects.hashCode(test12));
    //            System.out.println(Objects.hashCode(test13));
    //            
    //            System.out.println("---------------------");
    //            System.out.println(ObjectUtils.hashCode(test1));
    //            System.out.println(ObjectUtils.hashCode(test2));
    //            System.out.println(ObjectUtils.hashCode(test3));
    //            
    //            System.out.println(ObjectUtils.hashCode(test21));
    //            System.out.println(ObjectUtils.hashCode(test22));
    //            System.out.println(ObjectUtils.hashCode(test23));
    //            
    //            System.out.println(ObjectUtils.hashCode(test11));
    //            System.out.println(ObjectUtils.hashCode(test12));
    //            System.out.println(ObjectUtils.hashCode(test13));
    //        }
    //
    public static void main(String[] args) {
        
        Method m = MethodUtils.getAccessibleMethod(
                ServiceSupportCacheInterceptor.class, "setCache", Cache.class);
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
        ServiceSupportCacheInterceptor t = new ServiceSupportCacheInterceptor();
        String cacheKey1 = doGenerateCacheKey(
                ServiceSupportCacheInterceptor.class,
                "queryList",
                types,
                new Object[] { true, 1, 't', test1, "test123456",
                        Integer.valueOf(333333), test71, test81 });
        System.out.println(cacheKey1);
        
        String cacheKey2 = doGenerateCacheKey(
                ServiceSupportCacheInterceptor.class,
                "queryList",
                types,
                new Object[] { true, 1, 't', test2, "test123456",
                        Integer.valueOf(333333), test72, test82 });
        System.out.println(cacheKey2);
        
        String cacheKey3 = doGenerateCacheKey(
                ServiceSupportCacheInterceptor.class,
                "queryList",
                types,
                new Object[] { true, 1, 't', test3, "test123456",
                        Integer.valueOf(333333), test71, test82 });
        System.out.println(cacheKey3);
    }
}
