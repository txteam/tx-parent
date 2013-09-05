/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.hibernate.dialect.Dialect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * 基础基础数据执行器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseBasicDataExecutor<T> implements BasicDataExecutor<T> {
    
    /** jdbcTemplate */
    private JdbcTemplate jdbcTemplate;
    
    /** 缓存名 */
    private String cacheName;
    
    /** 缓存实体 */
    private Cache cache;
    
    /** 执行器对应类型  */
    private Class<T> type;
    
    /** 方言类 */
    private Dialect dialect;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 是否进行缓存 */
    private boolean cacheEnable;
    
    /** <默认构造函数> */
    public BaseBasicDataExecutor(Class<T> type, boolean cacheEnable,
            Dialect dialect, DataSource dataSource, CacheManager cacheManager) {
        AssertUtils.notNull(type, "type is null.");
        
        this.type = type;
        this.cacheEnable = cacheEnable;
        this.cacheName = "basicdata_cache_" + type.getName();
        
        if (!type.isEnum()) {
            
            AssertUtils.notNull(dialect, "dialect is null.");
            AssertUtils.notNull(dataSource, "dataSource is null.");
            
            this.dataSource = dataSource;
            if (dataSource != null) {
                this.jdbcTemplate = new JdbcTemplate(this.dataSource);
            }
            if (cacheEnable) {
                AssertUtils.notNull(cacheManager, "cacheManager is null.");
                if (cacheManager == null) {
                    cacheManager = CacheManager.create();
                }
                if (!cacheManager.cacheExists(this.cacheName)) {
                    cacheManager.addCache(this.cacheName);
                }
                this.cache = cacheManager.getCache(this.cacheName);
            }
        } else {
            this.cacheEnable = false;
        }
    }
    
    /**
      * 生成缓存唯一键
      *<功能详细描述>
      * @param method
      * @param params
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract String generateCacheKey(String method, Object... params);
    
    /**
     * 从对象中获取基础数据主键值<br/>
     *<功能详细描述>
     * @param obj
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected abstract String getValue(T obj);
    
    /**
      * 根据对象信息查询对应对象<br/>
      *<功能详细描述>
      * @param obj
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract T doFind(String pk);
    
    /**
      * 查询分页列表<br/>
      *<功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract PagedList<T> doQueryPagedList(
            Map<String, Object> params, int pageIndex, int pageSize);
    
    /**
      * 根据条件查询对象列表<br/>
      *<功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract List<T> doQuery(Map<String, Object> params);
    
    /**
      * 根据条件统计对象数目<br/>
      *<功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract int doCount(Map<String, Object> params);
    
    /**
      * 插入对象实例<br/>
      *<功能详细描述>
      * @param obj [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract void doInsert(T obj);
    
    /**
      * 根据传入映射更新指定对象<br/>
      *<功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract int doUpdate(Map<String, Object> params);
    
    /**
      * 根据传入参数删除指定对象<br/>
      *<功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract int doDelete(String pk);
    
    /**
     * @param pk
     * @return
     */
    @Override
    public boolean contains(String pk) {
        T t = get(pk);
        if (t == null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @param pk
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(String pk) {
        if (this.cacheEnable) {
            Element getEl = this.cache.get(generateCacheKey("get"));
            if (getEl != null) {
                Object obj = getEl.getObjectKey();
                Map<String, T> cacheListMap = (Map<String, T>) obj;
                return cacheListMap.get(pk);
            }
        }
        List<T> listResult = list();
        Map<String, T> cacheListMap = new HashMap<String, T>();
        if (listResult != null) {
            for (T temp : listResult) {
                cacheListMap.put(getValue(temp), temp);
            }
        }
        if (this.cacheEnable) {
            this.cache.put(new Element(generateCacheKey("get"), cacheListMap));
        }
        T res = cacheListMap.get(pk);
        return res;
    }
    
    /**
     * @param findCondition
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public T find(String pk) {
        if (this.cacheEnable) {
            Element getEl = this.cache.get(generateCacheKey("find", pk));
            if (getEl != null) {
                Object obj = getEl.getObjectKey();
                return (T)obj;
            }
        }
        T res = doFind(pk);
        if (this.cacheEnable && res != null) {
            this.cache.put(new Element(generateCacheKey("find", pk), res));
        }
        return res;
    }
    
    /**
     * @return
     */
    @Override
    public List<T> list() {
        List<T> resList = doQuery(null);
        return resList;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<T> query(Map<String, Object> params) {
        //TODO: query from cache
        
        List<T> resList = doQuery(null);
        return resList;
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        //TODO: query from cache
        
        PagedList<T> res = doQueryPagedList(params, pageIndex, pageSize);
        return res;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        //TODO: query from cache
        
        int resCount = doCount(params);
        return resCount;
    }
    
    /**
     * @param instance
     */
    @Override
    public void insert(T instance) {
        doInsert(instance);
        clearCache();
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public boolean delete(String pk) {
        int resInt = doDelete(pk);
        clearCache();
        return resInt > 0;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public boolean update(Map<String, Object> params) {
        int resInt = doUpdate(params);
        clearCache();
        return resInt > 0;
    }
    
    /**
      * 清空缓存<br/>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void clearCache() {
        //如果无需缓存
        if (!this.cacheEnable) {
            return;
        }
        //如果缓存开启
        final Cache finalCache = this.cache;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            //如果在事务逻辑中执行
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    finalCache.removeAll();
                }
            });
        } else {
            //如果在非事务中执行
            finalCache.removeAll();
        }
    }
    
    /**
     * @return 返回 type
     */
    protected Class<T> getType() {
        return type;
    }
    
    /**
     * @return 返回 jdbcTemplate
     */
    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    /**
     * @return 返回 dialect
     */
    protected Dialect getDialect() {
        return dialect;
    }
}
