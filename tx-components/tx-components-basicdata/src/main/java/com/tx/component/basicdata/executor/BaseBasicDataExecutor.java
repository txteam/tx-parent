/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.basicdata.context.BasicDataContextConfigurator;
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
    
    private Logger logger = LoggerFactory.getLogger(BaseBasicDataExecutor.class);
    
    /** jdbcTemplate */
    private JdbcTemplate jdbcTemplate;
    
    /** 缓存名 */
    private String cacheName;
    
    /** 缓存实体 */
    private Cache cache;
    
    /** 执行器对应类型  */
    private Class<T> type;
    
    /** <默认构造函数> */
    public BaseBasicDataExecutor(Class<T> type,
            BasicDataContextConfigurator configurator) {
        AssertUtils.notNull(type, "type is null.");
        
        this.type = type;
        this.cacheName = "basicdata_cache_" + type.getName();
        
        AssertUtils.notNull(configurator, "configurator is null.");
        AssertUtils.notNull(configurator.getDataSource(),
                "configurator.getDataSource() is null.");
        AssertUtils.notNull(configurator.getDataSourceType(),
                "configurator.getDataSourceType() is null.");
        
        DataSource dataSource = configurator.getDataSource();
        if (dataSource != null) {
            this.jdbcTemplate = new JdbcTemplate(dataSource);
        }
        AssertUtils.notNull(configurator.getCacheManager(),
                "cacheManager is null.");
        
        CacheManager cacheManager = null;
        if (configurator.getCacheManager() == null) {
            cacheManager = CacheManager.create();
        } else {
            cacheManager = configurator.getCacheManager();
        }
        if (!cacheManager.cacheExists(this.cacheName)) {
            cacheManager.addCache(this.cacheName);
        }
        this.cache = cacheManager.getCache(this.cacheName);
    }
    
    /**
      * 是否缓存
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract boolean isCacheEnable();
    
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
    protected Object getValue(T obj, String getterName) {
        MetaObject metaObject = MetaObject.forObject(obj);
        return metaObject.getValue(getterName);
    }
    
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
     * @param getterName
     * @return
     */
    @Override
    public MultiValueMap<Object, T> getMultiValueMap(String getterName) {
        List<String> getterNameList = Arrays.asList(MetaClass.forClass(this.type)
                .getGetterNames());
        AssertUtils.isTrue(getterNameList.contains(getterName),
                "type:{} has not getterName:{}",
                new Object[] { this.type, getterName });
        
        if (isCacheEnable()) {
            String cacheKey = generateCacheKey("getMultiValueMap", getterName);
            
            Element getEl = this.cache.get(cacheKey);
            if (getEl != null) {
                logger.debug("getMultiValueMap cacheEnable:true cacheKey:{}. cache exist.",
                        cacheKey);
                
                Object obj = getEl.getObjectValue();
                @SuppressWarnings("unchecked")
                MultiValueMap<Object, T> cacheListMap = (MultiValueMap<Object, T>) obj;
                return cacheListMap;
            }
            logger.debug("getMultiValueMap cacheEnable:true cacheKey:{}. cache not exist.",
                    cacheKey);
        }
        
        List<T> listResult = list();
        @SuppressWarnings({ "rawtypes", "unchecked" })
        MultiValueMap<Object, T> resMultiValueMap = new LinkedMultiValueMap();
        if (listResult != null) {
            for (T temp : listResult) {
                resMultiValueMap.add(getValue(temp, getterName), temp);
            }
        }
        if (isCacheEnable()) {
            this.cache.put(new Element(generateCacheKey("getMultiValueMap", getterName),
                    resMultiValueMap));
        }
        return resMultiValueMap;
    }
    
    /**
     * @param findCondition
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public T find(String pk) {
        if (isCacheEnable()) {
            String cacheKey = generateCacheKey("find", pk);
            Element getEl = this.cache.get(cacheKey);
            if (getEl != null) {
                logger.debug("find cacheEnable:true cacheKey:{}. cache exist.",
                        cacheKey);
                Object obj = getEl.getObjectValue();
                return (T) obj;
            }
            logger.debug("find cacheEnable:true cacheKey:{}. cache not exist.",
                    cacheKey);
        }
        T res = doFind(pk);
        if (isCacheEnable() && res != null) {
            this.cache.put(new Element(generateCacheKey("find", pk), res));
        }
        return res;
    }
    
    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> list() {
        if (isCacheEnable()) {
            String cacheKey = generateCacheKey("list");
            Element getEl = this.cache.get(cacheKey);
            if (getEl != null) {
                logger.debug("list cacheEnable:true cacheKey:{}. cache exist.",
                        cacheKey);
                Object obj = getEl.getObjectValue();
                return (List<T>) obj;
            }
            logger.debug("list cacheEnable:true cacheKey:{}. cache not exist.",
                    cacheKey);
        }
        List<T> resList = doQuery(null);
        if (isCacheEnable() && resList != null) {
            this.cache.put(new Element(generateCacheKey("list"), resList));
        }
        
        return resList;
    }
    
    /**
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> query(Map<String, Object> params) {
        if (isCacheEnable()) {
            String cacheKey = generateCacheKey("query", params);
            Element getEl = this.cache.get(cacheKey);
            if (getEl != null) {
                logger.debug("query cacheEnable:true cacheKey:{}. cache exist.",
                        cacheKey);
                
                Object obj = getEl.getObjectValue();
                return (List<T>) obj;
            }
            logger.debug("query cacheEnable:true cacheKey:{}. cache not exist.",
                    cacheKey);
        }
        List<T> resList = doQuery(params);
        if (isCacheEnable() && resList != null) {
            this.cache.put(new Element(generateCacheKey("query", params),
                    resList));
        }
        return resList;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        if (isCacheEnable()) {
            String cacheKey = generateCacheKey("count", params);
            Element getEl = this.cache.get(cacheKey);
            if (getEl != null) {
                logger.debug("count cacheEnable:true cacheKey:{}. cache exist.",
                        cacheKey);
                
                Object obj = getEl.getObjectValue();
                return (Integer) obj;
            }
            logger.debug("count cacheEnable:true cacheKey:{}. cache not exist.",
                    cacheKey);
        }
        int resCount = doCount(params);
        if (isCacheEnable()) {
            this.cache.put(new Element(generateCacheKey("count", params),
                    resCount));
        }
        return resCount;
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
        PagedList<T> res = doQueryPagedList(params, pageIndex, pageSize);
        return res;
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
        if (!isCacheEnable()) {
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
                    
                    logger.debug("clearCache cacheName:{}.",
                            finalCache.getName());
                }
            });
        } else {
            //如果在非事务中执行
            finalCache.removeAll();
            logger.debug("clearCache cacheName:{}.", finalCache.getName());
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
}
