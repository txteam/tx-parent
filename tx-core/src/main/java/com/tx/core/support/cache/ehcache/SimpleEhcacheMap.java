/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-25
 * <修改描述:>
 */
package com.tx.core.support.cache.ehcache;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.cxf.common.util.StringUtils;

import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * Ehcache的Map实现<br/>
 *   1、适用于缓存Map内部值并不是很多的情况<br/>
 *   2、由于实现其中频繁使用到了对象序列化以及反序列化的过程,如果Map过大会造成性能下降<br/>
 *   3、从该map中get对象实际是get了一个Object的copy
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleEhcacheMap<K extends Serializable, V extends Serializable>
        implements Map<K, V> {
    
    private static Set<String> needPutMethodName = new HashSet<String>();
    
    static {
        needPutMethodName.add("put");
        needPutMethodName.add("putAll");
        needPutMethodName.add("clear");
        needPutMethodName.add("remove");
    }
    
    /** 缓存的id */
    private final String id;
    
    /** 缓存实体本身 */
    private final Ehcache ehcache;
    
    /** map的动态代理类 */
    private Map<K, V> mapDelegate;
    
    /**
     * <默认构造函数>
     */
    public SimpleEhcacheMap(String id, Ehcache ehcache) {
        this(id, ehcache, new ConcurrentHashMap<K, V>());
    }
    
    /**
     * <默认构造函数>
     */
    @SuppressWarnings("unchecked")
    public SimpleEhcacheMap(String id, Ehcache ehcache, Map<K, V> realMap) {
        super();
        if (StringUtils.isEmpty(id) || ehcache == null || realMap == null) {
            throw new ParameterIsEmptyException(
                    "EhcacheMap initialize fail.id cache or realMap is empty");
        }
        if (realMap.size() > 0) {
            throw new ParameterIsEmptyException(
                    "EhcacheMap initialize fail.realMap must is empty.");
        }
        this.ehcache = ehcache;
        this.id = id;
        realMap = realMap == null ? new ConcurrentHashMap<K, V>() : realMap;
        this.mapDelegate = (Map<K, V>) Proxy.newProxyInstance(this.getClass()
                .getClassLoader(),
                new Class[] { Map.class },
                new MapInvocationHandler(realMap));
    }
    
    /**
      * Map的动态代理类，用以动态代理到handle中
      * <功能详细描述>
      * 
      * @author  brady
      * @version  [版本号, 2013-2-25]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    private class MapInvocationHandler implements InvocationHandler {
        
        private MapInvocationHandler(Map<K, V> realMap) {
            Element cacheElement = ehcache.get(id);
            if (cacheElement == null) {
                cacheElement = new Element(id, realMap);
                ehcache.put(cacheElement);
            }
        }
        
        /**
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            Element cacheElement = ehcache.get(id);
            if (cacheElement == null) {
                throw new ParameterIsEmptyException(
                        "EhcacheMap MapInvocationHandler.invoke id:{}:map is empty.");
            }
            Object cacheValue = cacheElement.getValue();
            if (cacheValue == null || !(cacheValue instanceof Map)) {
                throw new ParameterIsEmptyException(
                        "EhcacheMap MapInvocationHandler.invoke id:{}:map is not Map.");
            }
            @SuppressWarnings("unchecked")
            Map<K, V> realMap = (Map<K, V>) cacheValue;
            Object res = method.invoke(realMap, args);
            
            String methodName = method.getName();
            if (needPutMethodName.contains(methodName)) {
                ehcache.put(cacheElement);
            }
            return res;
        }
        
    }
    
    /**
     * @return
     */
    @Override
    public int size() {
        return mapDelegate.size();
    }
    
    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return mapDelegate.isEmpty();
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(Object key) {
        return mapDelegate.containsKey(key);
    }
    
    /**
     * @param value
     * @return
     */
    @Override
    public boolean containsValue(Object value) {
        return mapDelegate.containsValue(value);
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public V get(Object key) {
        return mapDelegate.get(key);
    }
    
    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value) {
        return mapDelegate.put(key, value);
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public V remove(Object key) {
        return mapDelegate.remove(key);
    }
    
    /**
     * @param m
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        mapDelegate.putAll(m);
    }
    
    /**
     * 
     */
    @Override
    public void clear() {
        mapDelegate.clear();
    }
    
    /**
     * @return
     */
    @Override
    public Set<K> keySet() {
        return mapDelegate.keySet();
    }
    
    /**
     * @return
     */
    @Override
    public Collection<V> values() {
        return mapDelegate.values();
    }
    
    /**
     * @return
     */
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return mapDelegate.entrySet();
    }
    
}
