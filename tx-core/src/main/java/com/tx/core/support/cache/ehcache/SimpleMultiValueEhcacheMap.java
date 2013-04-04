/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-14
 * <修改描述:>
 */
package com.tx.core.support.cache.ehcache;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.util.MultiValueMap;

import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * 基于ehcache的多值缓存
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleMultiValueEhcacheMap<K extends Serializable, V extends Serializable>
        implements MultiValueMap<K, V> {
    
    private final static Set<String> needPutMethodName = new HashSet<String>();
    
    static {
        needPutMethodName.add("put");
        needPutMethodName.add("putAll");
        needPutMethodName.add("clear");
        needPutMethodName.add("remove");
        
        needPutMethodName.add("add");
        needPutMethodName.add("set");
        needPutMethodName.add("setAll");
    }
    
    /** 缓存的id */
    private final String id;
    
    /** 缓存实体本身 */
    private final Ehcache ehcache;
    
    /** map的动态代理类 */
    private Map<K, List<V>> mapDelegate;
    
    /**
     * <默认构造函数>
     */
    public SimpleMultiValueEhcacheMap(String id, Ehcache ehcache) {
        this(id, ehcache, new ConcurrentHashMap<K, List<V>>());
    }
    
    /**
     * <默认构造函数>
     */
    @SuppressWarnings("unchecked")
    public SimpleMultiValueEhcacheMap(String id, Ehcache ehcache,
            Map<K, List<V>> realMap) {
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
        this.id = "SimpleMultiValueEhcacheMap_" + id;
        realMap = realMap == null ? new ConcurrentHashMap<K, List<V>>()
                : realMap;
        this.mapDelegate = (Map<K, List<V>>) Proxy.newProxyInstance(this.getClass()
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
        
        private MapInvocationHandler(Map<K, List<V>> realMap) {
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
        public synchronized Object invoke(Object proxy, Method method, Object[] args)
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
    public Set<java.util.Map.Entry<K, List<V>>> entrySet() {
        return mapDelegate.entrySet();
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public List<V> get(Object key) {
        return mapDelegate.get(key);
    }
    
    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    public List<V> put(K key, List<V> value) {
        return mapDelegate.put(key, value);
    }
    
    /**
     * @param m
     */
    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        mapDelegate.putAll(m);
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public List<V> remove(Object key) {
        return mapDelegate.remove(key);
    }
    
    /**
     * @return
     */
    @Override
    public Collection<List<V>> values() {
        return mapDelegate.values();
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public V getFirst(K key) {
        List<V> values = this.mapDelegate.get(key);
        return (values != null ? values.get(0) : null);
    }
    
    /**
     * @param key
     * @param value
     */
    @Override
    public void add(K key, V value) {
        List<V> values = this.mapDelegate.get(key);
        if (values == null) {
            values = new LinkedList<V>();
            this.mapDelegate.put(key, values);
        }
        values.add(value);
    }
    
    /**
     * @param key
     * @param value
     */
    @Override
    public void set(K key, V value) {
        List<V> values = new LinkedList<V>();
        values.add(value);
        this.mapDelegate.put(key, values);
    }
    
    /**
     * @param values
     */
    @Override
    public void setAll(Map<K, V> valuesMap) {
        for (Entry<K, V> entry : valuesMap.entrySet()) {
            List<V> values = new LinkedList<V>();
            values.add(entry.getValue());
            this.mapDelegate.put(entry.getKey(), values);
        }
    }
    
    /**
     * @return
     */
    @Override
    public Map<K, V> toSingleValueMap() {
        LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<K, V>(
                this.mapDelegate.size());
        for (Entry<K, List<V>> entry : this.mapDelegate.entrySet()) {
            singleValueMap.put(entry.getKey(), entry.getValue().get(0));
        }
        return singleValueMap;
    }
    
}
