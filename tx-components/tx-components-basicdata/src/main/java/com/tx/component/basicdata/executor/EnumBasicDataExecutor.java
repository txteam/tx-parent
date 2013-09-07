/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.ehcache.CacheManager;

import org.apache.commons.lang3.EnumUtils;
import org.hibernate.dialect.Dialect;

import com.tx.core.exceptions.logic.UnExpectCallException;
import com.tx.core.paged.model.PagedList;

/**
 * 枚举类型基础数据执行器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EnumBasicDataExecutor<T extends Enum<T>> extends
        BaseBasicDataExecutor<T> {
    
    /**
     * @param method
     * @param params
     * @return
     */
    @Override
    protected String generateCacheKey(String method, Object... params) {
        throw new UnExpectCallException("枚举类基础数据不可能调用到的业务逻辑");
    }

    /**
     * <默认构造函数>
     */
    public EnumBasicDataExecutor(Class<T> type, boolean cacheEnable,
            Dialect dialect, DataSource dataSource, CacheManager cacheManager) {
        super(type, false, null, null, null);
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    protected String getKeyValue(T obj) {
        return obj.toString();
    }
    
    /**
     * 重写基类中的get方法,实现不依赖list实现<br/>
     * @param pk
     * @return
     */
    @Override
    public T get(String pk) {
        T res = EnumUtils.getEnum(getType(), pk);
        return res;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    protected T doFind(String pk) {
        T resObj = EnumUtils.getEnum(getType(), pk);
        return resObj;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected List<T> doQuery(Map<String, Object> params) {
        List<T> resList = EnumUtils.getEnumList(getType());
        return resList;
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    protected PagedList<T> doQueryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        List<T> resList = EnumUtils.getEnumList(getType());
        
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        limit = limit < resList.size() ? limit : resList.size();
        
        PagedList<T> res = new PagedList<T>();
        res.setCount(resList.size());
        res.setPageIndex(pageIndex);
        res.setPageSize(pageSize);
        
        List<T> list = new ArrayList<T>();
        for (int i = offset; i < limit; i++) {
            list.add(resList.get(i));
        }
        res.setList(list);
        
        return res;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected int doCount(Map<String, Object> params) {
        List<T> resList = EnumUtils.getEnumList(getType());
        return resList.size();
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected int doUpdate(Map<String, Object> params) {
        throw new UnExpectCallException("枚举类基础数据不可能调用到的业务逻辑");
    }
    
    /**
     * @param obj
     */
    @Override
    protected void doInsert(T obj) {
        throw new UnExpectCallException("枚举类基础数据不可能调用到的业务逻辑");
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected int doDelete(String pk) {
        throw new UnExpectCallException("枚举类基础数据不可能调用到的业务逻辑");
    }
}
