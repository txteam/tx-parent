/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-19
 * <修改描述:>
 */
package com.tx.core.support.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.UnmodifiableMap;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCache;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 懒加载缓存Map的实现<br/>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LazyEhCacheMap<V> implements Map<String, V> {
    
    /** 查询全部时的缓存key */
    private static final String LISTMAP_CACHE_KEY = "list_map";
    
    /** 查询单个时的缓存key */
    private static final String FIND_CACHE_KEY_PREFIX = "find_";
    
    /** 在缓存发生变更时，是否整个flush缓存 */
    private boolean flushWhenUpdate;
    
    /** 本地化非缓存类型的Map实现 */
    private Map<String, V> unModifyAbleLocalMap;
    
    /** ehCache在spring中的cache实现 */
    private EhCacheCache cache;
    
    /** 缓存值工厂 */
    private LazyCacheValueFactory<String, V> factory;
    
    /** <默认构造函数> */
    @SuppressWarnings("unchecked")
    public LazyEhCacheMap(EhCacheCache cache,
            LazyCacheValueFactory<String, V> factory, boolean flushWhenUpdate) {
        super();
        if (unModifyAbleLocalMap != null) {
            this.unModifyAbleLocalMap = UnmodifiableMap.decorate(unModifyAbleLocalMap);
        }
        AssertUtils.notNull(cache, "cache is null.");
        AssertUtils.notNull(factory, "lazyCacheValueFactory is null.");
        this.cache = cache;
        this.factory = factory;
        this.flushWhenUpdate = flushWhenUpdate;
    }
    
    /** <默认构造函数> */
    @SuppressWarnings("unchecked")
    public LazyEhCacheMap(Map<String, V> unModifyAbleLocalMap,
            EhCacheCache cache, LazyCacheValueFactory<String, V> factory,
            boolean flushWhenUpdate) {
        super();
        if (unModifyAbleLocalMap != null) {
            this.unModifyAbleLocalMap = UnmodifiableMap.decorate(unModifyAbleLocalMap);
        }
        AssertUtils.notNull(cache, "cache is null.");
        AssertUtils.notNull(factory, "lazyCacheValueFactory is null.");
        this.cache = cache;
        this.factory = factory;
        this.flushWhenUpdate = flushWhenUpdate;
    }
    
    /** 清空缓存 */
    protected void clearCache() {
        this.cache.clear();
    }
    
    /** 将值压入缓存中 */
    protected void putInCache(String key, V value) {
        AssertUtils.notEmpty(key, "key is empty.");
        this.cache.evict(LISTMAP_CACHE_KEY);
        if (flushWhenUpdate) {
            this.cache.clear();
        }
        this.cache.put(FIND_CACHE_KEY_PREFIX + key, value);
    }
    
    /** 将值压入缓存中 */
    protected void putAllInCache(Map<? extends String, ? extends V> map) {
        if (MapUtils.isEmpty(map)) {
            return;
        }
        this.cache.evict(LISTMAP_CACHE_KEY);
        if (flushWhenUpdate) {
            this.cache.clear();
        }
        for (Entry<? extends String, ? extends V> entryTemp : map.entrySet()) {
            this.cache.put(FIND_CACHE_KEY_PREFIX + entryTemp.getKey(),
                    entryTemp.getValue());
        }
    }
    
    /** 将值从缓存中移除 */
    protected void removeFromCache(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        this.cache.evict(FIND_CACHE_KEY_PREFIX + key);
        this.cache.evict(LISTMAP_CACHE_KEY);
        if (flushWhenUpdate) {
            this.cache.clear();
        }
    }
    
    /** 获取缓存中的list结果 */
    private Map<String, V> listMapFromCache() {
        ValueWrapper valueWrapper = this.cache.get(LISTMAP_CACHE_KEY);
        @SuppressWarnings("unchecked")
        Map<String, V> cacheMap = valueWrapper == null ? null
                : (Map<String, V>) valueWrapper.get();
        if (cacheMap == null) {
            Map<String, V> listMap = this.factory.listMap();
            AssertUtils.notNull(listMap, "factory.listMap result is null.");
            this.cache.put(LISTMAP_CACHE_KEY, listMap);
            for (Entry<String, V> entryTemp : listMap.entrySet()) {
                this.cache.put(FIND_CACHE_KEY_PREFIX + entryTemp.getKey(),
                        entryTemp.getValue());
            }
            return listMap;
        } else {
            return cacheMap;
        }
    }
    
    /** 从缓存中获取指定key对应的值 */
    private V getFromCache(String key) {
        ValueWrapper valueWrapper = this.cache.get(FIND_CACHE_KEY_PREFIX + key);
        @SuppressWarnings("unchecked")
        V cacheValue = valueWrapper == null ? null : (V) valueWrapper.get();
        if (cacheValue == null) {
            V valueTemp = this.factory.find(key);
            if (valueTemp != null) {
                this.cache.put(FIND_CACHE_KEY_PREFIX + key, valueTemp);
            }
            return valueTemp;
        } else {
            return cacheValue;
        }
    }
    
    /**
     * @return
     */
    @Override
    public int size() {
        Map<String, V> mapTemp = new HashMap<String, V>();
        if (!MapUtils.isEmpty(unModifyAbleLocalMap)) {
            mapTemp.putAll(unModifyAbleLocalMap);
        }
        Map<String, V> cacheMap = listMapFromCache();
        if (!MapUtils.isEmpty(cacheMap)) {
            mapTemp.putAll(cacheMap);
        }
        return mapTemp.size();
    }
    
    /**
     * @param value
     * @return
     */
    @Override
    public boolean containsValue(Object value) {
        if (!MapUtils.isEmpty(unModifyAbleLocalMap)
                && unModifyAbleLocalMap.containsValue(value)) {
            return true;
        }
        Map<String, V> cacheMap = listMapFromCache();
        if (!MapUtils.isEmpty(cacheMap) && cacheMap.containsValue(value)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        if (!MapUtils.isEmpty(unModifyAbleLocalMap)) {
            return false;
        }
        Map<String, V> cacheMap = listMapFromCache();
        if (!MapUtils.isEmpty(cacheMap)) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Set<String> keySet() {
        Map<String, V> mapTemp = new HashMap<String, V>();
        if (!MapUtils.isEmpty(unModifyAbleLocalMap)) {
            mapTemp.putAll(unModifyAbleLocalMap);
        }
        Map<String, V> cacheMap = listMapFromCache();
        if (!MapUtils.isEmpty(cacheMap)) {
            mapTemp.putAll(cacheMap);
        }
        Set<String> resSet = mapTemp.keySet();
        return resSet;
    }
    
    /**
     * @return
     */
    @Override
    public Collection<V> values() {
        Map<String, V> mapTemp = new HashMap<String, V>();
        if (!MapUtils.isEmpty(unModifyAbleLocalMap)) {
            mapTemp.putAll(unModifyAbleLocalMap);
        }
        Map<String, V> cacheMap = listMapFromCache();
        if (!MapUtils.isEmpty(cacheMap)) {
            mapTemp.putAll(cacheMap);
        }
        Collection<V> resCollection = mapTemp.values();
        return resCollection;
    }
    
    /**
     * @return
     */
    @Override
    public Set<java.util.Map.Entry<String, V>> entrySet() {
        Map<String, V> mapTemp = new HashMap<String, V>();
        if (!MapUtils.isEmpty(unModifyAbleLocalMap)) {
            mapTemp.putAll(unModifyAbleLocalMap);
        }
        Map<String, V> cacheMap = listMapFromCache();
        if (!MapUtils.isEmpty(cacheMap)) {
            mapTemp.putAll(cacheMap);
        }
        Set<java.util.Map.Entry<String, V>> resSet = mapTemp.entrySet();
        return resSet;
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(Object key) {
        if (!MapUtils.isEmpty(unModifyAbleLocalMap)
                && unModifyAbleLocalMap.containsKey(key)) {
            return true;
        }
        V value = getFromCache((String) key);
        if (value != null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public V get(Object key) {
        V value = null;
        value = unModifyAbleLocalMap.get(key);
        if (value != null) {
            return value;
        }
        value = getFromCache((String) key);
        return value;
    }
    
    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(String key, V value) {
        V res = getFromCache(key);
        doPut(key, value);
        return res;
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public V remove(Object key) {
        V res = getFromCache((String) key);
        doRemove((String) key);
        return res;
    }
    
    /**
     * @param m
     */
    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        doPutAll(m);
    }
    
    /**
     * 这里的清空不是真正意义上的清空,unModifyMap中的数据是不会变化的
     */
    @Override
    public void clear() {
        doClear();
    }
    
    /**
      * 将值压入缓存中
      *<功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doPut(String key, V value) {
        putInCache(key, value);
    }
    
    /**
      * 将一组map值压入缓存<br/>
      *<功能详细描述>
      * @param m [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doPutAll(Map<? extends String, ? extends V> m) {
        putAllInCache(m);
    }
    
    /**
      * 从缓存中移除<br/>
      *<功能详细描述>
      * @param key [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doRemove(String key) {
        removeFromCache(key);
    }
    
    /**
      * 对对应的缓存进行清空操作<br/> 
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doClear() {
        clearCache();
    }
}
