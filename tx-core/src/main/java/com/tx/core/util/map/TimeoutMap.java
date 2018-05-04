/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年4月8日
 * <修改描述:>
 */
package com.tx.core.util.map;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <p/>
 * 超时Map<br/>
 * <p/>
 * 此Map每个键值对都有一个超时时间,只要键值对超时,则会自动移除掉此键值对<br/>
 * <p/>
 * <strong>注意，此实现不是同步的</strong>,如果需要同步,请使用{@link Collections#synchronizedMap Collections.synchronizedMap}方法进行包装
 * <p/>
 * 此实现中,集合方法非常影响性能,因为需要去遍历每一个键值对来确认是否超时,然后再返回
 * 
 * @author Rain.he
 * @version [版本号, 2015年4月8日]
 * @param <K> 键
 * @param <V> 值
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TimeoutMap<K, V> extends HashMap<K, V> implements Map<K, V> {
    private static final long serialVersionUID = -5822572358103365035L;
    
    /** 超时map */
    private Map<K, Long> timeoutMap = new HashMap<K, Long>();
    
    /** 超时时间,单位:毫秒 */
    private long timeout;
    
    /**
     * 构造一个拥有超时时间的HashMap
     * 
     * @param timeout 超时时间,单位:毫秒
     *            
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public TimeoutMap(long timeout) {
        super();
        this.timeout = timeout;
    }
    
    /**
     * @return 返回 timeout,单位:毫秒
     */
    public long getTimeout() {
        return timeout;
    }
    
    /**
     * @param 对timeout进行赋值,单位:毫秒
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
    
    /**
     * 返回Map条数<br/>
     * 注意:集合方法非常影响性能
     */
    @Override
    public int size() {
        refresh();
        return super.size();
    }
    
    /**
     * 判断Map是否为空<br/>
     * 注意:集合方法非常影响性能
     * 
     * @return boolean 是否为空
     */
    @Override
    public boolean isEmpty() {
        refresh();
        return super.isEmpty();
    }
    
    /**
     * 根据键获取值
     * 
     * @param key 键
     * @return 值
     */
    @Override
    public V get(Object key) {
        refresh(key);
        return super.get(key);
    }
    
    /**
     * 根据键确认键值对是否存在<br/>
     * 
     * @param key 键
     * @return boolean 此键值对是否存在
     */
    @Override
    public boolean containsKey(Object key) {
        refresh(key);
        return super.containsKey(key);
    }
    
    /**
     * 设置键值对
     * 
     * @param key 键
     * @param value 值
     */
    @Override
    public V put(K key, V value) {
        this.timeoutMap.put(key, Long.valueOf(new Date().getTime()));
        return super.put(key, value);
    }
    
    /**
     * 设置键值对
     * 
     * @param 键值对Map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Long now = Long.valueOf(new Date().getTime());
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            this.timeoutMap.put(entry.getKey(), now);
        }
        super.putAll(m);
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public V remove(Object key) {
        this.timeoutMap.remove(key);
        return super.remove(key);
    }
    
    /**
     * 清空Map<br/>
     */
    @Override
    public void clear() {
        this.timeoutMap.clear();
        super.clear();
    }
    
    /**
     * 判断是否包含此值
     * 
     * @param value 值
     * @return boolean 是否包含此值
     */
    @Override
    public boolean containsValue(Object value) {
        long now = new Date().getTime();
        Iterator<Entry<K, V>> iterator = super.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (value.equals(entry.getValue())) { // 找到值,确定此值是否已经超时
                K key = entry.getKey();
                long time = this.timeoutMap.get(key).longValue();
                if ((time + this.timeout) < now) { // 超时
                    this.timeoutMap.remove(key);
                    iterator.remove();
                } else {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 返回Map键Set<br/>
     * 在遍历返回的Set的同时,进行put和remove操作,遍历结果会不可预知<br/>
     * 注意:集合方法非常影响性能
     * 
     * @return Set 键Set
     */
    @Override
    public Set<K> keySet() {
        refresh();
        return super.keySet();
    }
    
    /**
     * 返回Map值Collection<br/>
     * 在遍历返回的Collection的同时,进行put和remove操作,遍历结果会不可预知<br/>
     * 注意:集合方法非常影响性能
     * 
     * @return Collection 值Collection
     */
    @Override
    public Collection<V> values() {
        refresh();
        return super.values();
    }
    
    /**
     * 返回Map键值Set<Entry<K,V>><br/>
     * 在遍历返回的Set<Entry<K,V>>的同时,进行put和remove操作,遍历结果会不可预知<br/>
     * 注意:集合方法非常影响性能
     * 
     * @return Set<Entry<K, V>> 键值Set
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        refresh();
        return super.entrySet();
    }
    
    /**
     * 刷新键值对超时状态,如果超时则移除
     * 
     * @return void [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void refresh() {
        long now = new Date().getTime(); // 当前时间
        Set<Entry<K, Long>> entry = this.timeoutMap.entrySet();
        Iterator<Entry<K, Long>> iterator = entry.iterator();
        while (iterator.hasNext()) {
            Entry<K, Long> e = iterator.next();
            if ((e.getValue().longValue() + this.timeout) < now) { // 键值对创建时间+超时时间 < 当前时间
                iterator.remove();
                super.remove(e.getKey());
            }
        }
    }
    
    /**
     * 刷新键值对,如果超时则移除
     * 
     * @param key 键
     *            
     * @return void [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void refresh(Object key) {
        long now = new Date().getTime();
        long time = this.timeoutMap.get(key).longValue();
        if ((time + this.timeout) < now) {
            this.timeoutMap.remove(key);
            super.remove(key);
        }
    }
}
