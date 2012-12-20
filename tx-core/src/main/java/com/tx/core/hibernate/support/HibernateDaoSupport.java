/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-5
 * <修改描述:>
 */
package com.tx.core.hibernate.support;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.tx.core.paged.model.PagedList;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-11-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HibernateDaoSupport {
    
    private HibernateTemplate hibernateTemplate;
    
    /**
      *<持久化数据对象>
      *<功能详细描述>
      * @param entity
      * @param entityName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void persist(Object entity, String... entityName) {
        if (ArrayUtils.isEmpty(entityName)) {
            this.hibernateTemplate.persist(entity);
        } else {
            this.hibernateTemplate.persist(entityName[0], entity);
        }
    }
    
    /**
      *<insert or update插入或保存对象>
      *<功能详细描述>
      * @param entity
      * @param entityName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void save(Object entity, String... entityName) {
        if (ArrayUtils.isEmpty(entityName)) {
            this.hibernateTemplate.saveOrUpdate(entity);
        } else {
            this.hibernateTemplate.saveOrUpdate(entityName[0], entity);
        }
    }
    
    /**
      *<批量保存对象>
      *<功能详细描述>
      * @param collection [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void saveAll(Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return;
        }
        this.hibernateTemplate.saveOrUpdateAll(collection);
    }
    
    /**
      *<插入对象>
      *<功能详细描述>
      * @param obj
      * @param entityName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insert(Object entity, String... entityName) {
        if (ArrayUtils.isEmpty(entityName)) {
            this.hibernateTemplate.save(entity);
        } else {
            this.hibernateTemplate.save(entityName[0], entity);
        }
    }
    
    /**
      *<批量插入>
      *<功能详细描述>
      * @param collection [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchInsert(Collection<?> collection) {
        if (collection == null) {
            return;
        }
        for (Object objTemp : collection) {
            this.hibernateTemplate.save(objTemp);
        }
    }
    
    /**
     *<批量插入>
     *<功能详细描述>
     * @param collection [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void insertAll(Collection<?> collection) {
        this.hibernateTemplate.saveOrUpdateAll(collection);
    }
    
    /**
      *<调用删除功能>
      *<功能详细描述>
      * @param entityName
      * @param entity [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void delete(Object entity, String... entityName) {
        if (ArrayUtils.isEmpty(entityName)) {
            this.hibernateTemplate.delete(entity);
        } else {
            this.hibernateTemplate.delete(entityName[0], entityName);
        }
    }
    
    /**
      *<调用删除功能>
      *<功能详细描述>
      * @param entity
      * @param lockMode
      * @param entityName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void delete(Object entity, LockMode lockMode, String... entityName) {
        if (ArrayUtils.isEmpty(entityName)) {
            this.hibernateTemplate.delete(entity, lockMode);
        } else {
            this.hibernateTemplate.delete(entityName[0], entity, lockMode);
        }
    }
    
    /**
      *<批量删除对象>
      *<功能详细描述>
      * @param collection [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void deleteAll(Collection<?> collection) {
        if (collection == null) {
            return;
        }
        this.hibernateTemplate.deleteAll(collection);
    }
    
    /**
      *<批量删除>
      *<功能详细描述>
      * @param collection [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchDelete(Collection<?> collection) {
        if (collection == null) {
            return;
        }
        for (Object objTemp : collection) {
            this.hibernateTemplate.save(objTemp);
        }
    }
    
    /**
     *<查询实体对象>
     *<功能详细描述>
     * @param queryClass
     * @param id
     * @param lockMode 可以为空
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public <T> T find(Class<T> queryClass, String id) {
        return this.hibernateTemplate.get(queryClass, id);
    }
    
    /**
      *<查询实体对象>
      *<功能详细描述>
      * @param entityName
      * @param id
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object find(String entityName, String id) {
        return this.hibernateTemplate.get(entityName, id);
    }
    
    /**
      *<查询实体对象>
      *<功能详细描述>
      * @param queryClass
      * @param id
      * @param lockMode 可以为空
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object find(Class<?> queryClass, String id, LockMode lockMode) {
        if (lockMode == null) {
            return this.hibernateTemplate.get(queryClass, id);
        } else {
            return this.hibernateTemplate.get(queryClass, id, lockMode);
        }
    }
    
    /**
     *<查询实体对象>
     *<功能详细描述>
     * @param queryClass
     * @param id
     * @param lockMode
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public Object find(String entityName, String id, LockMode lockMode) {
        if (lockMode == null) {
            return this.hibernateTemplate.get(entityName, id);
        } else {
            return this.hibernateTemplate.get(entityName, id, lockMode);
        }
    }
    
    /**
      *<根据hql查询列表>
      *<功能详细描述>
      * @param queryString
      * @param values [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<?> queryListByHql(String queryHql, Object... values) {
        if (ArrayUtils.isEmpty(values)) {
            return this.hibernateTemplate.find(queryHql);
        } else {
            return this.hibernateTemplate.find(queryHql, values);
        }
    }
    
    /**
      *<根据hql查询列表>
      *<功能详细描述>
      * @param queryHql
      * @param params
      * @return [参数说明]
      * 
      * @return List<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<?> queryListByNamedHql(String queryHql,
            Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return this.hibernateTemplate.find(queryHql);
        } else {
            int paramsSize = params.size();
            String[] paramNames = new String[paramsSize];
            Object[] values = new Object[paramsSize];
            
            int index = 0;
            for (Entry<String, Object> entryTemp : params.entrySet()) {
                paramNames[index] = entryTemp.getKey();
                values[index] = entryTemp.getValue();
                index++;
            }
            
            if (paramsSize > 1) {
                return this.hibernateTemplate.findByNamedParam(queryHql,
                        paramNames,
                        values);
            } else {
                return this.hibernateTemplate.findByNamedParam(queryHql,
                        paramNames[0],
                        values[0]);
            }
        }
    }
    
    /**
      *<根据sql查询结果>
      *<功能详细描述>
      * @param sql
      * @param values
      * @return [参数说明]
      * 
      * @return List<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<?> queryListBySql(String sql, Object... values){
        final String finalSql = sql;
        final Object[] finalValues = values;
        List<?> resList = this.hibernateTemplate.execute(new HibernateCallback<List<?>>() {

            /**
             * @param session
             * @return
             * @throws HibernateException
             * @throws SQLException
             */
            @Override
            public List<?> doInHibernate(Session session)
                    throws HibernateException, SQLException {
                SQLQuery sqlQuery = session.createSQLQuery(finalSql);
                if(finalValues != null && finalValues.length > 0){
                    for (int i = 0 ; i < finalValues.length ; i++){
                        sqlQuery.setParameter(i, finalValues[i]);
                    }
                }
                List<?> res = sqlQuery.list();
                return res;
            }
            
        });
        return resList;
    }
    
    /**
      *<根据sql查询列表>
      *<功能详细描述>
      * @param sql
      * @param params
      * @return [参数说明]
      * 
      * @return List<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<?> queryListByNamedSql(String sql,Map<String, Object> params){
        final String finalSql = sql;
        final Map<String, Object> finalParams = params;
        List<?> resList = this.hibernateTemplate.execute(new HibernateCallback<List<?>>() {

            /**
             * @param session
             * @return
             * @throws HibernateException
             * @throws SQLException
             */
            @Override
            public List<?> doInHibernate(Session session)
                    throws HibernateException, SQLException {
                SQLQuery sqlQuery = session.createSQLQuery(finalSql);
                if(finalParams != null && finalParams.size() > 0){
                    for (Entry<String, Object> entryTemp : finalParams.entrySet()){
                        sqlQuery.setParameter(entryTemp.getKey(), entryTemp.getValue());
                    }
                }
                List<?> res = sqlQuery.list();
                return res;
            }
            
        });
        return resList;
    }
    
    /**
      *<查询分页列表>
      *<功能详细描述>
      * @param querySql
      * @param pageIndex
      * @param pageSize
      * @param values
      * @return [参数说明]
      * 
      * @return PagedList<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public PagedList<?> queryPagedListBySql(String querySql,int pageIndex,int pageSize,Object... values){
        final String finalQuerySql = querySql;
        final Object[] finalValues = values;
        final int finalPageIndex = pageIndex >= 1 ? pageIndex : 1;
        final int finalPageSize = pageSize > 0 ? pageSize : 1;
        PagedList<?> result = this.hibernateTemplate.execute(new HibernateCallback<PagedList<?>>() {

            /**
             * @param session
             * @return
             * @throws HibernateException
             * @throws SQLException
             */
            @Override
            public PagedList<?> doInHibernate(Session session)
                    throws HibernateException, SQLException {
                PagedList<Object> res = new PagedList<Object>();
                res.setPageIndex(finalPageIndex);
                res.setPageSize(finalPageSize);
                
                SQLQuery query = session.createSQLQuery(finalQuerySql);
                if (!ArrayUtils.isEmpty(finalValues)) {
                    for (int i = 0; i < finalValues.length; i++) {
                        query.setParameter(i, finalValues[i]);
                    }
                }
                List<?> tempList = query.list();
                int count = tempList.size();
                res.setCount(count);
                
                int firstResult = (finalPageIndex - 1) * finalPageSize;
                int maxResults = firstResult + finalPageSize;
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
                
                res.setList(query.list());
                return res;
            }
        });
        
        return result;
    }
    
    /**
      *<根据hql查询分页对象>
      *<功能详细描述>
      * @param queryHql
      * @param pageIndex
      * @param pageSize
      * @param values
      * @return [参数说明]
      * 
      * @return PagedList<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public PagedList<?> queryPagedListByHql(String queryHql, int pageIndex,
            int pageSize, Object... values) {
        
        final String finalQueryHql = queryHql;
        final Object[] finalValues = values;
        final int finalPageIndex = pageIndex >= 1 ? pageIndex : 1;
        final int finalPageSize = pageSize > 0 ? pageSize : 1;
        PagedList<?> result = this.hibernateTemplate.execute(new HibernateCallback<PagedList<?>>() {
            /**
             * @param session
             * @return
             * @throws HibernateException
             * @throws SQLException
             */
            @Override
            public PagedList<?> doInHibernate(Session session)
                    throws HibernateException, SQLException {
                PagedList<Object> res = new PagedList<Object>();
                res.setPageIndex(finalPageIndex);
                res.setPageSize(finalPageSize);
                
                Query query = session.createQuery(finalQueryHql);
                if (!ArrayUtils.isEmpty(finalValues)) {
                    for (int i = 0; i < finalValues.length; i++) {
                        query.setParameter(i, finalValues[i]);
                    }
                }
                //TODO:这里需要验证一下，size是否是触发了count方法
                List<?> tempList = query.list();
                int count = tempList.size();
                res.setCount(count);
                
                if (count == 0) {
                    return res;
                }
                
                int firstResult = (finalPageIndex - 1) * finalPageSize;
                int maxResults = firstResult + finalPageSize;
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
                res.setList(query.list());
                
                return res;
            }
        });
        
        return result;
    }
    
    /**
     *<根据hql查询分页对象>
     *<功能详细描述>
     * @param queryHql
     * @param pageIndex
     * @param pageSize
     * @param values
     * @return [参数说明]
     * 
     * @return PagedList<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<?> queryPagedListByNamedHql(String queryHql,
            int pageIndex, int pageSize, Map<String, Object> params) {
        
        final String finalQueryHql = queryHql;
        final Map<String, Object> finalParams = params;
        final int finalPageIndex = pageIndex >= 1 ? pageIndex : 1;
        final int finalPageSize = pageSize > 0 ? pageSize : 1;
        PagedList<?> result = this.hibernateTemplate.execute(new HibernateCallback<PagedList<?>>() {
            /**
             * @param session
             * @return
             * @throws HibernateException
             * @throws SQLException
             */
            @Override
            public PagedList<?> doInHibernate(Session session)
                    throws HibernateException, SQLException {
                PagedList<Object> res = new PagedList<Object>();
                res.setPageIndex(finalPageIndex);
                res.setPageSize(finalPageSize);
                
                Query query = session.createQuery(finalQueryHql);
                if (finalParams != null && finalParams.size() > 0) {
                    for (Entry<String, Object> entryTemp : finalParams.entrySet()) {
                        query.setParameter(entryTemp.getKey(),
                                entryTemp.getValue());
                    }
                }
                //TODO:这里需要验证一下，size是否是触发了count方法
                List<?> tempList = query.list();
                int count = tempList.size();
                res.setCount(count);
                
                if (count == 0) {
                    return res;
                }
                
                int firstResult = (finalPageIndex - 1) * finalPageSize;
                int maxResults = firstResult + finalPageSize;
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
                res.setList(query.list());
                
                return res;
            }
        });
        
        return result;
    }
    
    /**
      *<更新对象>
      *<功能详细描述>
      * @param entity
      * @param entityName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void update(Object entity, String... entityName) {
        if (ArrayUtils.isEmpty(entityName)) {
            this.hibernateTemplate.update(entity);
        } else {
            this.hibernateTemplate.update(entityName[0], entityName);
        }
    }
    
    /**
      *<更新字段，可对对象枷锁>
      *<功能详细描述>
      * @param entity
      * @param lockMode
      * @param entityName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void update(Object entity, LockMode lockMode, String... entityName) {
        if (ArrayUtils.isEmpty(entityName)) {
            this.hibernateTemplate.update(entity, lockMode);
        } else {
            this.hibernateTemplate.update(entityName[0], entity, lockMode);
        }
    }
    
    /**
      *<更新所有>
      *<功能详细描述>
      * @param collection [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void updateAll(Collection<?> collection) {
        if (collection == null) {
            return;
        }
        this.hibernateTemplate.saveOrUpdateAll(collection);
    }
    
    /**
      *<批量更新对象>
      *<功能详细描述>
      * @param collection [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchUpdate(Collection<?> collection) {
        if (collection == null) {
            return;
        }
        for (Object objTemp : collection) {
            this.hibernateTemplate.update(objTemp);
        }
    }
    
    /**
      *<将hibernateCallback暴露出去>
      *<功能详细描述>
      * @param action
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> T execute(HibernateCallback<T> action) {
        return this.hibernateTemplate.execute(action);
    }
    
}
