/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import net.sf.ehcache.CacheManager;

import org.apache.ibatis.reflection.MetaObject;
import org.hibernate.dialect.Dialect;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

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
    
    private SimpleSqlSource<T> simpleSqlSource;
    
    private SimpleSqlSourceBuilder simpleSqlSourceBuilder = new SimpleSqlSourceBuilder();
    
    /** <默认构造函数> */
    public DefaultBasicDataExecutor(Class<T> type, boolean cacheEnable,
            Dialect dialect, DataSource dataSource, CacheManager cacheManager) {
        super(type, cacheEnable, dialect, dataSource, cacheManager);
        this.simpleSqlSource = simpleSqlSourceBuilder.build(type, dialect);
    }
    
    /**
     * @param method
     * @param params
     * @return
     */
    @Override
    protected String generateCacheKey(String method, Object... params) {
        if ("get".equals(method)) {
            return "cachekey_for_get";
        } else if ("find".equals(method)) {
            return "cachekey_for_find_" + params[0].hashCode();
        } else if ("query".equals(method)) {
            int hashCode = "query".hashCode();
            if (params[0] != null) {
                LinkedHashMap<String, Object> paramMap = simpleSqlSource.getQueryCondtionParamMaps(params[0]);
                for (Entry<String, Object> entryTemp : paramMap.entrySet()) {
                    hashCode += entryTemp.getKey().hashCode()
                            + entryTemp.getValue().hashCode();
                }
            }
            String resKey = "cachekey_for_query_" + hashCode;
            return resKey;
        } else if ("count".equals(method)) {
            int hashCode = "count".hashCode();
            if (params[0] != null) {
                LinkedHashMap<String, Object> paramMap = simpleSqlSource.getQueryCondtionParamMaps(params[0]);
                for (Entry<String, Object> entryTemp : paramMap.entrySet()) {
                    hashCode += entryTemp.getKey().hashCode()
                            + entryTemp.getValue().hashCode();
                }
            }
            String resKey = "cachekey_for_count_" + hashCode;
            return resKey;
        } else if ("queryPagedList".equals(method)) {
            int hashCode = "query".hashCode();
            if (params[0] != null) {
                LinkedHashMap<String, Object> paramMap = simpleSqlSource.getQueryCondtionParamMaps(params[0]);
                for (Entry<String, Object> entryTemp : paramMap.entrySet()) {
                    hashCode += entryTemp.getKey().hashCode();
                    hashCode += entryTemp.getValue().hashCode();
                }
            }
            hashCode += (Integer) params[1];
            hashCode += (Integer) params[2];
            String resKey = "cachekey_for_queryPaged_" + hashCode;
            return resKey;
        } else {
            int hashCode = method.hashCode();
            if (params != null) {
                for (Object obj : params) {
                    if (obj != null) {
                        hashCode += obj.hashCode();
                    }
                }
            }
            String resKey = method + hashCode;
            return resKey;
        }
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    protected String getKeyValue(T obj) {
        MetaObject metaObject = MetaObject.forObject(obj);
        String value = (String) metaObject.getValue(simpleSqlSource.getPkName());
        return value;
    }
    
    /**
     * @param pk
     * @return
     */
    @Override
    protected T doFind(String pk) {
        List<T> resList = getJdbcTemplate().query(simpleSqlSource.findSql(),
                new Object[] { pk },
                simpleSqlSource.getSelectRowMapper());
        return DataAccessUtils.singleResult(resList);
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
        int count = count(params);
        
        PagedList<T> result = new PagedList<T>();
        result.setPageIndex(pageIndex);
        result.setPageSize(pageSize);
        result.setCount(count);
        
        /* 如果count得到的结果为0则不再继续查询具体的哪几条 */
        if (count == 0) {
            return result;
        }
        
        List<T> resList = getJdbcTemplate().query(simpleSqlSource.queryPagedSql(params,
                pageIndex,
                pageSize),
                simpleSqlSource.getPagedQueryCondtionSetter(params,
                        pageIndex,
                        pageSize),
                simpleSqlSource.getSelectRowMapper());
        result.setList(resList);
        
        return result;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected List<T> doQuery(Map<String, Object> params) {
        List<T> resList = getJdbcTemplate().query(simpleSqlSource.countSql(params),
                simpleSqlSource.getQueryCondtionSetter(params),
                simpleSqlSource.getSelectRowMapper());
        return resList;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected int doCount(Map<String, Object> params) {
        RowMapper<Integer> integerRowMapper = new SingleColumnRowMapper<Integer>(
                Integer.class);
        List<Integer> resList = getJdbcTemplate().query(simpleSqlSource.countSql(params),
                simpleSqlSource.getQueryCondtionSetter(params),
                integerRowMapper);
        int res = DataAccessUtils.singleResult(resList);
        return res;
    }
    
    /**
     * @param obj
     */
    @Override
    protected void doInsert(T obj) {
        getJdbcTemplate().update(simpleSqlSource.insertSql(),
                simpleSqlSource.getInsertSetter(obj));
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected int doUpdate(Map<String, Object> params) {
        int res = getJdbcTemplate().update(simpleSqlSource.updateSql(params),
                simpleSqlSource.getUpdateSetter(params));
        return res;
    }
    
    /**
     * @param pk
     * @return
     */
    @Override
    protected int doDelete(String pk) {
        int res = getJdbcTemplate().update(simpleSqlSource.deleteSql(), pk);
        return res;
    }
}
