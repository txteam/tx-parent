/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-19
 * <修改描述:>
 */
package com.tx.core.support.cache;

import java.util.Map;

import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 能够感知事务，在事务提交时进行执行的缓存Map实现
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TransactionAwareLazyEhCacheMap<V> extends LazyEhCacheMap<V> {
    
    /** <默认构造函数> */
    public TransactionAwareLazyEhCacheMap(EhCacheCache cache,
            LazyCacheValueFactory<String, V> factory, boolean flushWhenUpdate) {
        super(cache, factory, flushWhenUpdate);
    }
    
    /** <默认构造函数> */
    public TransactionAwareLazyEhCacheMap(Map<String, V> unModifyAbleLocalMap,
            EhCacheCache cache, LazyCacheValueFactory<String, V> factory,
            boolean flushWhenUpdate) {
        super(unModifyAbleLocalMap, cache, factory, flushWhenUpdate);
    }
    
    /**
     * @param key
     * @param value
     */
    @Override
    protected void doPut(final String key, final V value) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    putInCache(key, value);
                }
            });
        } else {
            putInCache(key, value);
        }
    }
    
    /**
     * @param m
     */
    @Override
    protected void doPutAll(final Map<? extends String, ? extends V> m) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    putAllInCache(m);
                }
            });
        } else {
            putAllInCache(m);
        }
    }
    
    /**
     * @param key
     */
    @Override
    protected void doRemove(final String key) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    removeFromCache(key);
                }
            });
        } else {
            removeFromCache(key);
        }
    }
    
    /**
     * 
     */
    @Override
    protected void doClear() {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    clearCache();
                }
            });
        } else {
            clearCache();
        }
    }
}
