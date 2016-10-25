/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月11日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.list.TreeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

import com.tx.core.TxConstants;

/**
 * 基础数据业务层拦截器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataServiceInterceptor implements MethodInterceptor {
    
    /** 日志记录器 */
    private Logger logger = LoggerFactory.getLogger(BasicDataServiceInterceptor.class);
    
    private Cache cache;
    
    /** <默认构造函数> */
    public BasicDataServiceInterceptor() {
        super();
    }
    
    /** <默认构造函数> */
    public BasicDataServiceInterceptor(Cache cache) {
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
        String methodName = invocation.getMethod().getName();
        Object[] args = invocation.getArguments();
        
        Object res = null;
        if (methodName.startsWith("query") || methodName.startsWith("find")
                || methodName.startsWith("list")) {
            //生成对应的缓存值
            String cacheKey = generateCacheKey(methodName, args);
            ValueWrapper vw = cache.get(cacheKey);
            if (vw == null || vw.get() == null) {
                //如果缓存里值不存在，则调用方法进行获取<br/>
                logger.info("未命中缓存：缓存:{} 缓存key:{}",
                        this.cache.getName(),
                        cacheKey);
                res = invocation.proceed();
                if (res != null) {
                    cache.put(cacheKey, res);
                }
            } else {
                //如果有值则获取缓存值
                logger.info("从缓存中获取: 命中缓存：缓存:{} 缓存key:{}",
                        this.cache.getName(),
                        cacheKey);
                res = vw.get();
            }
        } else if (methodName.startsWith("save")
                || methodName.startsWith("insert")
                || methodName.startsWith("move")
                || methodName.startsWith("add")
                || methodName.startsWith("update")
                || methodName.startsWith("change")
                || methodName.startsWith("del")) {
            cache.clear();
            res = invocation.proceed();
        } else {
            res = invocation.proceed();
        }
        return res;
    }
    
    /**
     * 生成缓存Key值<br/>
     * <功能详细描述>
     * @param methodName
     * @param args
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private String generateCacheKey(String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append(methodName).append("_");
        for (Object arg : args) {
            int hashCode = generateCacheKey(arg, new HashSet<>());
            sb.append(hashCode).append("_");
        }
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
    private int generateCacheKey(Object arg, Set<Object> cachedSet) {
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
    private int generateMapCache(Map<?, ?> argMap, Set<Object> cachedSet) {
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
    private int generateSetCache(Set<?> set, Set<Object> cachedSet) {
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
    private int generateListCache(List<?> set, Set<Object> cachedSet) {
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
    
    public static void main(String[] args) {
        Map<String, String> test1 = new TreeMap<String, String>();
        test1.put("test2", "test2");
        test1.put("test5", "test5");
        test1.put("test4", "test4");
        test1.put("test1", "test1");
        test1.put("test3", "test3");
        
        Map<String, String> test2 = new TreeMap<String, String>();
        test2.put("test1", "test1");
        test2.put("test3", "test3");
        test2.put("test2", "test2");
        test2.put("test5", "test5");
        test2.put("test4", "test4");
        
        for (Entry<String, String> entry : test1.entrySet()) {
            System.out.println(entry.getKey());
        }
        for (Entry<String, String> entry : test2.entrySet()) {
            System.out.println(entry.getKey());
        }
        
        BasicDataServiceInterceptor t = new BasicDataServiceInterceptor();
        String cacheKey1 = t.generateCacheKey("queryList", new Object[] { true,
                test1 });
        System.out.println(cacheKey1);
        
        String cacheKey2 = t.generateCacheKey("queryList", new Object[] { true,
                test2 });
        System.out.println(cacheKey2);
    }
}
