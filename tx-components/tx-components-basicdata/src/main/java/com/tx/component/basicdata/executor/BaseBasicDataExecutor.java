/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
    
    private Class<T> type;
    
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    
    private JdbcTemplate jdbcTemplate;
    
    private Cache cache;
    
    private String cacheName;
    
    private boolean cacheEnable;
    
    /** <默认构造函数> */
    public BaseBasicDataExecutor(DataSource dataSource,CacheManager cacheManager,Class<T> type) {
        this.type = type;
        this.cacheName = "basicdata_cache_" + type.getName();
        if(!type.isEnum()){
            if(dataSource != null){
                this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
                this.jdbcTemplate = new JdbcTemplate(dataSource);
            }
            if(cacheEnable){
                if(cacheManager == null){
                    cacheManager = CacheManager.create();
                }
                if(!cacheManager.cacheExists(this.cacheName)){
                    cacheManager.addCache(this.cacheName);
                }
                this.cache = cacheManager.getCache(this.cacheName);
            }
        }
    }
    
    /** <默认构造函数> */
    public BaseBasicDataExecutor(Class<T> type) {
        this(null,null,type);
    }
    
    /** <默认构造函数> */
    public BaseBasicDataExecutor(CacheManager cacheManager,Class<T> type) {
        this(null,cacheManager,type);
    }
    
    /**
      * 根据主键获取对应对象<br/>
      *     如果不存在则返回空对象<br/>
      *<功能详细描述>
      * @param pk
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract T doGet(String pk);
    
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
    protected abstract T doFind(T obj);
    
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
    protected abstract PagedList<T> doQueryPagedList(Map<String, Object> params,int pageIndex,int pageSize);
    
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
    protected abstract int doDelete(Map<String, Object> params);
    
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
    @Override
    public T get(String pk) {
        //TODO: find from cache
        
        T t = doGet(pk);
        return t;
    }
    
    /**
     * @param findCondition
     * @return
     */
    @Override
    public T find(T findCondition) {
        //TODO: find from cache
        
        T res = doFind(findCondition);
        return null;
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
    public int delete(Map<String, Object> params) {
        int resInt = doDelete(params);
        clearCache();
        return resInt;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int update(Map<String, Object> params) {
        int resInt = doUpdate(params);
        clearCache();
        return resInt;
    }
    
    /**
      * 清空缓存<br/>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void clearCache(){
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
     * @param 对cacheEnable进行赋值
     */
    protected void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }
    
    /**
     * @return 返回 type
     */
    protected Class<T> getType() {
        return type;
    }

    /**
     * @return 返回 namedJdbcTemplate
     */
    protected NamedParameterJdbcTemplate getNamedJdbcTemplate() {
        return namedJdbcTemplate;
    }

    /**
     * @return 返回 jdbcTemplate
     */
    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
