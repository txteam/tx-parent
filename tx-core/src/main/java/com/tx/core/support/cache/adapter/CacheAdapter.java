/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-19
 * <修改描述:>
 */
package com.tx.core.support.cache.adapter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tx.core.paged.model.PagedList;

/**
 * 缓存适配器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface CacheAdapter<T,PK extends Serializable> {
    
    public T find(T findCondition);
    
    public T findByPk(PK pk);
    
    public List<T> query(Map<String, Object> queryParams);
    
    public int count(Map<String, Object> queryParams);
    
    public PagedList<T> queryPagedList(Map<String, Object> queryParams);
    
    public void put(PK pk,T object);
    
    public void putAll(List<T> objList);
    
}
