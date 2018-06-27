/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.auth.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.auth.model.AuthItem;

/**
 * AuthItemImpl持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthItemDao {
    
    /**
     * 插入AuthItemImpl对象实体
     * 1、auto generate
     * <功能详细描述>
     * @param condition
     * @param tableSuffix [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insert(AuthItem condition);
    
    /**
     * 删除AuthItemImpl对象
     * 1、auto generate
     * 2、根据入参条件进行删除
     * <功能详细描述>
     * @param condition [参数说明]
     * @param tableSuffix [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public int delete(AuthItem condition);
    
    /**
     * 查询AuthItemImpl实体
     * auto generate
     * <功能详细描述>
     * @param condition
     * @param tableSuffix
     * @return [参数说明]
     * 
     * @return AuthItemImpl [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public AuthItem find(AuthItem condition);
    
    /**
     * 根据条件查询AuthItemImpl列表
     * auto generate
     *<功能详细描述>
     * @param params
     * @param tableSuffix
     * @return [参数说明]
     * 
     * @return List<AuthItemImpl> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<AuthItem> queryList(Map<String, Object> params);
    
    /**
     * 更新AuthItemImpl实体，
     * auto generate
     * 1、传入AuthItemImpl中主键不能为空
     * <功能详细描述>
     * @param updateRowMap
     * @param tableSuffix
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int update(Map<String, Object> updateRowMap);
}
