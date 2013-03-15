/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-14
 * <修改描述:>
 */
package com.tx.core.support.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;


 /**
  * 多值Map适配器<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class MultiValueMapAdapter<K, V> implements MultiValueMap<K, V>, Serializable {

    /** 注释内容 */
    private static final long serialVersionUID = -5735762715198006123L;
    
    private final Map<K, List<V>> map;

    public MultiValueMapAdapter(Map<K, List<V>> map) {
        Assert.notNull(map, "'map' must not be null");
        this.map = map;
    }

    public void add(K key, V value) {
        List<V> values = this.map.get(key);
        if (values == null) {
            values = new LinkedList<V>();
            this.map.put(key, values);
        }
        values.add(value);
    }

    public V getFirst(K key) {
        List<V> values = this.map.get(key);
        return (values != null ? values.get(0) : null);
    }

    public void set(K key, V value) {
        List<V> values = new LinkedList<V>();
        values.add(value);
        this.map.put(key, values);
    }

    public void setAll(Map<K, V> values) {
        for (Entry<K, V> entry : values.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }

    public Map<K, V> toSingleValueMap() {
        LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<K,V>(this.map.size());
        for (Entry<K, List<V>> entry : map.entrySet()) {
            singleValueMap.put(entry.getKey(), entry.getValue().get(0));
        }
        return singleValueMap;
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public List<V> get(Object key) {
        return this.map.get(key);
    }

    public List<V> put(K key, List<V> value) {
        return this.map.put(key, value);
    }

    public List<V> remove(Object key) {
        return this.map.remove(key);
    }

    public void putAll(Map<? extends K, ? extends List<V>> m) {
        this.map.putAll(m);
    }

    public void clear() {
        this.map.clear();
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public Collection<List<V>> values() {
        return this.map.values();
    }

    public Set<Entry<K, List<V>>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return map.equals(other);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}

