/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.ehcache.CacheManager;

import org.hibernate.dialect.Dialect;

import com.tx.core.jdbc.sqlsource.SimpleSqlSource;
import com.tx.core.jdbc.sqlsource.SimpleSqlSourceBuilder;
import com.tx.core.paged.model.PagedList;


/**
 * 基础数据容器默认执行器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultBasicDataExecutor<T> extends BaseBasicDataExecutor<T> {
    
    private SimpleSqlSource simpleSqlSource;
    
    /** <默认构造函数> */
    public DefaultBasicDataExecutor(Class<T> type, boolean cacheEnable,
            Dialect dialect, DataSource dataSource, CacheManager cacheManager) {
        super(type, cacheEnable, dialect, dataSource, cacheManager);
        this.simpleSqlSource = SimpleSqlSourceBuilder.build(type, dialect);
    }

    /**
     * @param method
     * @param params
     * @return
     */
    @Override
    protected String generateCacheKey(String method, Object... params) {
        if("get".equals(method)){
            return "cachekey_for_get";
        }else if("find".equals(method)){
            return "cachekey_for_find_" + params[0].hashCode();
        }else if("query".equals(method)){
            return "cachekey_for_query_";//TODO:...
        }
        return null;
    }

    /**
     * @param obj
     * @return
     */
    @Override
    protected String getValue(T obj) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param pk
     * @return
     */
    @Override
    protected T doFind(String pk) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param params
     * @return
     */
    @Override
    protected List<T> doQuery(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param params
     * @return
     */
    @Override
    protected int doCount(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @param obj
     */
    @Override
    protected void doInsert(T obj) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param params
     * @return
     */
    @Override
    protected int doUpdate(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @param pk
     * @return
     */
    @Override
    protected int doDelete(String pk) {
        // TODO Auto-generated method stub
        return 0;
    }
}
