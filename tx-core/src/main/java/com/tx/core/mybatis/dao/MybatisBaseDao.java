/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月8日
 * <修改描述:>
 */
package com.tx.core.mybatis.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

/**
 * 持久层接口<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MybatisBaseDao<T, ID extends Serializable> {
    
    /**
     * 插入对象实例<br/>
     * <功能详细描述>
     * @param entity [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(T entity);
    
    /**
     * 批量插入对象实例<br/>
     * <功能详细描述>
     * @param entityList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsert(List<T> entityList);
    
    /**
     * 删除对象实例<br/>
     * <功能详细描述>
     * @param entity
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean delete(ID pk);
    
    /**
     * 删除对象实例<br/>
     * <功能详细描述>
     * @param entity
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int delete(T entity);
    
    /**
     * 批量删除对象实例<br/>
     * <功能详细描述>
     * @param entityList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchDelete(List<T> entityList);
    
    /**
     * 根据主键修改对象实例<br/>
     * <功能详细描述>
     * @param pk
     * @param updateEntityMap
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean update(ID pk, Map<String, Object> updateEntityMap);
    
    /**
     * 更新对象实例<br/>
     * <功能详细描述>
     * @param updateRowMap
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int update(Map<String, Object> updateEntityMap);
    
    /**
     * 批量更新对象实例<br/>
     * <功能详细描述>
     * @param updateEntityMapList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchUpdate(List<Map<String, Object>> updateEntityMapList);
    
    /**
     * 根据主键查询对象实例<br/>
     * <功能详细描述>
     * @param entityCondition
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public T find(ID pk);
    
    /**
     * 查询对象实例<br/>
     * <功能详细描述>
     * @param entity
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public T find(T entity);
    
    /**
     * 根据条件查询对象实例列表<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<T> queryList(Map<String, Object> params);
    
    /**
     * 根据条件查询对象实例列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<T> queryList(Querier querier);
    
    /**
     * 根据条件查询对象实例分页列表<br/>
     * <功能详细描述>
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
    /**
     * 根据条件查询对象实例分页列表<br/>
     * <功能详细描述>
     * @param querier
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<T> queryPagedList(Querier querier, int pageIndex,
            int pageSize);
    
    /**
     * 根据条件查询对象实例分页列表<br/>
     * <功能详细描述>
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param count
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, int count);
    
    /**
     * 根据条件查询对象实例分页列表<br/>
     * <功能详细描述>
     * @param querier
     * @param pageIndex
     * @param pageSize
     * @param count
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<T> queryPagedList(Querier querier, int pageIndex,
            int pageSize, int count);
    
    /**
     * 根据条件查询对象实例数量<br/>
     * <功能详细描述>
     * @param params
     * @param exclude
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Map<String, Object> params, ID exclude);
    
    /**
     * 根据条件查询对象实例数量<br/>
     * <功能详细描述>
     * @param querier
     * @param exclude
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Querier querier, ID exclude);
    
    /**
     * 根据条件查询对象实例数量<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default int count(Map<String, Object> params) {
        int res = count(params, null);
        return res;
    }
    
    /**
     * 根据条件查询对象实例数量<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default int count(Querier querier) {
        int res = count(querier, null);
        return res;
    }
    
    /**
     * 判断指定条件的对象实例是否存在<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default boolean exists(Map<String, Object> params) {
        int count = count(params, null);
        boolean flag = count > 0;
        return flag;
    }
    
    /**
     * 判断指定条件的对象实例是否存在<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default boolean exists(Querier querier) {
        int count = count(querier, null);
        boolean flag = count > 0;
        return flag;
    }
    
    /**
     * 判断指定条件的对象实例是否存在<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default boolean exists(Map<String, Object> params, ID exclude) {
        int count = count(params, exclude);
        boolean flag = count > 0;
        return flag;
    }
    
    /**
     * 判断指定条件的对象实例是否存在<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default boolean exists(Querier querier, ID exclude) {
        int count = count(querier, exclude);
        boolean flag = count > 0;
        return flag;
    }
    
    /**
     * 判断指定条件的对象实例是否存在<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default boolean unique(Map<String, Object> params) {
        int count = count(params);
        boolean flag = count == 1;
        return flag;
    }
    
    /**
     * 判断指定条件的对象实例是否存在<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default boolean unique(Querier querier) {
        int count = count(querier);
        boolean flag = count == 1;
        return flag;
    }
}
