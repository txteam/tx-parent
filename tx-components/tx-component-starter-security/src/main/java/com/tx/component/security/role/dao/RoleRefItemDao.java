/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.security.role.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.security.auth.model.AuthRefItem;
import com.tx.component.security.role.model.RoleRefItem;

/**
 * AuthItemRefImpl持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RoleRefItemDao {
    
    /**
     * 插入AuthItemRefImpl对象实体
     * 1、auto generate
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insertToHis(RoleRefItem roleRef);
    
    /**
     * 批量插入权限项引用
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsertToHis(List<RoleRefItem> roleRefs);
    
    /**
     * 插入AuthItemRefImpl对象实体
     * 1、auto generate
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insert(RoleRefItem roleRef);
    
    /**
     * 批量插入权限项引用
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsert(List<RoleRefItem> roleRefs);
    
    /**
     * 删除AuthItemRefImpl对象
     * 1、auto generate
     * 2、根据入参条件进行删除
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public int delete(RoleRefItem roleRef);
    
    /**
     * 批量删除权限项目引用
     * <功能详细描述>
     * @param authItemRefImplList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchDelete(List<RoleRefItem> roleRefs);
    
    /**
     * 根据条件查询AuthItemRefImpl列表
     * auto generate
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<AuthItemRefImpl> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<AuthRefItem> queryList(Map<String, Object> params);
    
    /**
     * 更新AuthItemRefImpl实体，
     * auto generate
     * 1、传入AuthItemRefImpl中主键不能为空
     * <功能详细描述>
     * @param updateAuthItemRefImplRowMap
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int update(Map<String, Object> updateRowMap);
}
