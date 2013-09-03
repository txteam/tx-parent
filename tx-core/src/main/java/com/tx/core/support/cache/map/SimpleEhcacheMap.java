/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-3
 * <修改描述:>
 */
package com.tx.core.support.cache.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.collections.MapUtils;

/**
 * 基于缓存的Map实现<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleEhcacheMap<K extends Serializable, V extends Serializable>
        implements Map<K, V> {
    
    /** 缓存的id */
    private final String id;
    
    /** 缓存实体本身 */
    private final Ehcache ehcache;
    
    /** <默认构造函数> */
    public SimpleEhcacheMap(CacheManager cacheManager, String systemName,
            String id) {
        super();
        //初始化缓存实现，如果已经存在，则认为系统中使用同key的缓存非法
        //如果需要两处共用一个simplEhcache需要自行解决共享问题
        this.id = "simpleEhcacheMap_" + systemName + id;
        cacheManager.cacheExists(this.id);
        cacheManager.addCache(this.id);
        this.ehcache = cacheManager.getEhcache(this.id);
    }
    
    /**
     * 
     */
    @Override
    public void clear() {
        this.ehcache.removeAll();
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(Object key) {
        Element element = this.ehcache.get(key);
        return element != null;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        int size = this.ehcache.getSize();
        return size == 0;
    }
    
    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        Element oldElement = this.ehcache.get(key);
        this.ehcache.put(new Element(key, value));
        if (oldElement == null) {
            return null;
        } else {
            return (V) oldElement.getValue();
        }
    }
    
    /**
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        Element element = this.ehcache.get(key);
        if (element == null) {
            return null;
        } else {
            return (V) element.getValue();
        }
    }
    
    /**
     * @return
     */
    @Override
    public Set<K> keySet() {
        @SuppressWarnings("unchecked")
        List<K> keyList = this.ehcache.getKeys();
        return new HashSet<K>(keyList);
    }
    
    /**
     * @param m
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (MapUtils.isEmpty(m)) {
            return;
        }
        for (@SuppressWarnings("rawtypes")
        Entry entryTemp : m.entrySet()) {
            this.ehcache.put(new Element(entryTemp.getKey(),
                    entryTemp.getValue()));
        }
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        Element el = this.ehcache.get(key);
        this.ehcache.remove(key);
        if (el == null) {
            return null;
        } else {
            return (V) el.getValue();
        }
    }
    
    /**
     * @return
     */
    @Override
    public int size() {
        int cacheSize = this.ehcache.getSize();
        return cacheSize;
    }
    
    /**
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<V> values() {
        List keys = this.ehcache.getKeys();
        Map<Object, Element> allMap = this.ehcache.getAll(keys);
        Map<K, V> tempMap = new HashMap<K, V>();
        for(Entry<Object, Element> entryTemp : allMap.entrySet()){
            if(entryTemp.getValue() != null){
                tempMap.put((K)entryTemp.getKey(), (V)(entryTemp.getValue().getValue()));
            }
        }
        return tempMap.values();
    }
    
    /**
     * @param value
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean containsValue(Object value) {
        List keys = this.ehcache.getKeys();
        Map<Object, Element> allMap = this.ehcache.getAll(keys);
        Map<K, V> tempMap = new HashMap<K, V>();
        for(Entry<Object, Element> entryTemp : allMap.entrySet()){
            if(entryTemp.getValue() != null){
                tempMap.put((K)entryTemp.getKey(), (V)(entryTemp.getValue().getValue()));
            }
        }
        boolean resFlag = tempMap.containsValue(value);
        return resFlag;
    }
    

    
    /**
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        List keys = this.ehcache.getKeys();
        Map<Object, Element> allMap = this.ehcache.getAll(keys);
        Map<K, V> tempMap = new HashMap<K, V>();
        for(Entry<Object, Element> entryTemp : allMap.entrySet()){
            if(entryTemp.getValue() != null){
                tempMap.put((K)entryTemp.getKey(), (V)(entryTemp.getValue().getValue()));
            }
        }
        return tempMap.entrySet();
    }
    
}
