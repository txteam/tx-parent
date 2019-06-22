/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.jpa.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

/**
 * 实体持久层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface JPABaseDao<T, ID extends Serializable> {
    
    /**
     * 保存实体对象<br/>
     * <功能详细描述>
     * @param entity [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default T save(T entity) {
        return merge(entity);
    }
    
    /**
     * 插入实体对象<br/>
     * <功能详细描述>
     * @param entity [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default void insert(T entity) {
        persist(entity);
    }
    
    /**
     * 持久化实体对象<br/>    
     * <persist是直接保存>
     * persist 方法可以将实例转换为 managed( 托管 ) 状态。在调用 flush() 方法或提交事物后，实例将会被插入到数据库中<br/>
     * 对不同状态下的实例 A ， persist 会产生以下操作 :<br/>
     *  如果 A 是一个 new 状态的实体，它将会转为 managed 状态；<br/>
     *  如果 A 是一个 managed 状态的实体，它的状态不会发生任何改变。但是系统仍会在数据库执行 INSERT 操作；<br/>
     *  如果 A 是一个 removed( 删除 ) 状态的实体，它将会转换为受控状态；<br/>
     *  果 A 是一个 detached( 分离 ) 状态的实体，该方法会抛出 IllegalArgumentException 异常，具体异常根据不同的 JPA 实现有关。<br/>
     * @param entity [实体对象]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void persist(T entity);
    
    /**
     * 合并实体对象<br/>
     * <merge是根据id是否存在来判断是保存还是修改（id存在，则修改； id不存在，则添加）>
     * merge 方法的主要作用是将用户对一个 detached 状态实体的修改进行归档，归档后将产生一个新的 managed 状态对象。<br/>
     * 对不同状态下的实例 A ， merge 会产生以下操作 :<br/>
     *  如果 A 是一个 detached 状态的实体，该方法会将 A 的修改提交到数据库，并返回一个新的 managed 状态的实例 A2 <br/>
     *  如果 A 是一个 new 状态的实体，该方法会产生一个根据 A 产生的 managed 状态实体 A2 ;<br/>
     *  如果 A 是一个 managed 状态的实体，它的状态不会发生任何改变。但是系统仍会在数据库执行 UPDATE 操作；<br/>
     *  如果 A 是一个 removed 状态的实体，该方法会抛出 IllegalArgumentException 异常。<br/>
     * @param entity
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    T merge(T entity);
    
    /**
     * 刷新实体对象<br/>
     * <refresh 方法可以保证当前的实例与数据库中的实例的内容一致。<br/>
     * 对不同状态下的实例 A ， refresh 会产生以下操作 :<br/>
     *  如果 A 是一个 new 状态的实例，不会发生任何操作，但有可能会抛出异常，具体情况根据不同 JPA 实现有关；<br/>
     *  如果 A 是一个 managed 状态的实例，它的属性将会和数据库中的数据同步；<br/>
     *  如果 A 是一个 removed 状态的实例，不会发生任何操作 ;<br/>
     *  如果 A 是一个 detached 状态的实体，该方法将会抛出异常。<br/>
     * @param entity [实体对象]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default void refresh(T entity) {
        refresh(entity, null);
    }
    
    /**
     * 刷新实体对象<br/>
     * <refresh 方法可以保证当前的实例与数据库中的实例的内容一致。<br/>
     * 对不同状态下的实例 A ， refresh 会产生以下操作 :<br/>
     *  如果 A 是一个 new 状态的实例，不会发生任何操作，但有可能会抛出异常，具体情况根据不同 JPA 实现有关；<br/>
     *  如果 A 是一个 managed 状态的实例，它的属性将会和数据库中的数据同步；<br/>
     *  如果 A 是一个 removed 状态的实例，不会发生任何操作 ;<br/>
     *  如果 A 是一个 detached 状态的实体，该方法将会抛出异常。<br/>
     * @param entity [实体对象]
     * @param lockModeType [锁定方式]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void refresh(T entity, LockModeType lockModeType);
    
    /**
     * 移除实体对象
     * 
     * @param entity
     *            实体对象
     */
    void remove(T entity);
    
    /**
     * 批量插入对象实体
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsert(List<T> entityList);
    
    /**
     * 批量插入对象<br/>
     * <功能详细描述>
     * @param objectList
     * @param doFlushSize
     * @return [参数说明]
     * 
     * @return BatchResult [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsert(List<T> entityList, int doFlushSize);
    
    /**
     * 删除对象实体<br/>
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return int [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public int delete(T entity);
    
    /**
     * 批量删除对象<br/>
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchDelete(List<T> entityList);
    
    /**
     * 批量删除<br/>
     * <功能详细描述>
     * @param objectList
     * @param doFlushSize
     * @return [参数说明]
     * 
     * @return BatchResult [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchDelete(List<T> objectList, int doFlushSize);
    
    /**
     * 更新T实体，
     * auto generate
     * 1、传入T中主键不能为空
     * <功能详细描述>
     * @param updateTRowMap
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int update(Map<String, Object> updateRowMap);
    
    /**
     * 批量更新对象实体<br/>
     *    1、传入T中主键不能为空
     * 
     * <功能详细描述>
     * @param updateTRowMap
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchUpdate(List<Map<String, Object>> updateRowMapList);
    
    /**
     * 根据实体对象主键查找实体对象<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default T find(ID id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        T res = find(id, null);
        return res;
    }
    
    /**
     * 查找实体对象
     * <功能详细描述>
     * @param id [ID]
     * @param lockModeType [锁定方式 ]
     * @return [参数说明]
     * 
     * @return T [实体对象，若不存在则返回null]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    T find(ID id, LockModeType lockModeType);
    
    /**
     * 查询对象实体<br/>
     * <功能详细描述>
     * @param condition
     * 
     * @return T [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    default T find(T condition) {
        return find(condition, null);
    }
    
    /**
     * 查询对象实体<br/>
     * <功能详细描述>
     * @param condition
     * 
     * @return T [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    T find(T condition, LockModeType lockModeType);
    
    /**
     * 根据条件查询T列表
     * auto generate
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
     * 分页查询T列表
     * auto generate
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
    
    
    
    public void lock(Object entity, LockModeType lockMode);
    
    public boolean contains(Object entity);
    
    public FlushModeType getFlushMode();
    
    public LockModeType getLockMode(Object entity);
    
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
     * 设置为游离状态
     * 
     * @param entity
     *            实体对象
     */
    void detach(T entity);
    
    /**
     * 清除缓存<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void clear();
    
    /**
     * 刷新数据<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void flush();
    
    /**
     * 获取实体对应的持久层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return EntityManager [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    EntityManager getEntityManager();
}
