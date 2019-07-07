/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.auth.context.AuthManager;
import com.tx.component.auth.dao.AuthItemDao;
import com.tx.component.auth.model.Auth;
import com.tx.component.auth.model.AuthItem;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Filter;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QuerierBuilder;

/**
 * 权限项的业务层[AuthItemService]
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuthItemService implements AuthManager {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(AuthItemService.class);
    
    private AuthItemDao authItemDao;
    
    /** <默认构造函数> */
    public AuthItemService() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthItemService(AuthItemDao authItemDao) {
        super();
        this.authItemDao = authItemDao;
    }
    
    /**
     * @param authId
     * @return
     */
    @Override
    public Auth findAuthById(String authId) {
        AssertUtils.notEmpty(authId, "authId is empty.");
        
        Auth auth = findById(authId);
        return auth;
    }
    
    /**
     * @param authTypeIds
     * @return
     */
    @Override
    public List<Auth> queryAuthList(String... authTypeIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("authTypeIds", authTypeIds);
        
        List<Auth> resList = queryList(params).stream()
                .collect(Collectors.toList());
        return resList;
    }
    
    /**
     * @param parentId
     * @param authTypeIds
     * @return
     */
    @Override
    public List<Auth> queryChildrenAuthByParentId(String parentId,
            String... authTypeIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("authTypeIds", authTypeIds);
        
        List<Auth> resList = queryChildrenByParentId(parentId, params).stream()
                .collect(Collectors.toList());
        return resList;
    }
    
    /**
     * @param parentId
     * @param authTypeIds
     * @return
     */
    @Override
    public List<Auth> queryDescendantsAuthByParentId(String parentId,
            String... authTypeIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("authTypeIds", authTypeIds);
        
        List<Auth> resList = queryDescendantsByParentId(parentId, params)
                .stream().collect(Collectors.toList());
        return resList;
    }
    
    /**
     * 新增权限项实例<br/>
     * 将authItem插入数据库中保存
     * 1、如果authItem 为空时抛出参数为空异常
     * 2、如果authItem 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param authItem [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(AuthItem authItem) {
        //验证参数是否合法
        AssertUtils.notNull(authItem, "authItem is null.");
        AssertUtils.notEmpty(authItem.getAuthTypeId(),
                "auth.authTypeId is empty.");
        AssertUtils.notEmpty(authItem.getName(), "auth.name is empty.");
        
        //调用数据持久层对实例进行持久化操作
        this.authItemDao.insert(authItem);
    }
    
    /**
     * 根据id删除权限项实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param id
     * @return boolean 删除的条数>0则为true [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        AuthItem condition = new AuthItem();
        condition.setId(id);
        
        int resInt = this.authItemDao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据id查询权限项实例
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return AuthItem [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public AuthItem findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        AuthItem condition = new AuthItem();
        condition.setId(id);
        
        AuthItem res = this.authItemDao.find(condition);
        return res;
    }
    
    /**
     * 查询权限项实例列表
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItem> queryList(Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<AuthItem> resList = this.authItemDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 查询权限项实例列表
     * <功能详细描述>
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItem> queryList(Querier querier) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<AuthItem> resList = this.authItemDao.queryList(querier);
        
        return resList;
    }
    
    /**
     * 分页查询权限项实例列表
     * <功能详细描述>
     * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<AuthItem> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<AuthItem> resPagedList = this.authItemDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 分页查询权限项实例列表
     * <功能详细描述>
     * @param querier    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<AuthItem> queryPagedList(Querier querier, int pageIndex,
            int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<AuthItem> resPagedList = this.authItemDao
                .queryPagedList(querier, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 查询权限项实例数量<br/>
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.authItemDao.count(params);
        
        return res;
    }
    
    /**
     * 查询权限项实例数量<br/>
     * <功能详细描述>
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Querier querier) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.authItemDao.count(querier);
        
        return res;
    }
    
    /**
     * 判断权限项实例是否已经存在<br/>
     * <功能详细描述>
     * @param key2valueMap
     * @param excludeId
     * @return
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("excludeId", excludeId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.authItemDao.count(params);
        
        return res > 0;
    }
    
    /**
     * 判断权限项实例是否已经存在<br/>
     * <功能详细描述>
     * @param key2valueMap
     * @param excludeId
     * @return
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Querier querier, String excludeId) {
        AssertUtils.notNull(querier, "querier is null.");
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.authItemDao.count(querier, excludeId);
        
        return res > 0;
    }
    
    /**
     * 根据id更新权限项实例<br/>
     * <功能详细描述>
     * @param authItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(String id, AuthItem authItem) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(authItem, "authItem is null.");
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        
        //需要更新的字段
        updateRowMap.put("refId", authItem.getRefId());
        updateRowMap.put("refType", authItem.getRefType());
        updateRowMap.put("attributes", authItem.getAttributes());
        updateRowMap.put("authTypeId", authItem.getAuthTypeId());
        updateRowMap.put("name", authItem.getName());
        updateRowMap.put("parentId", authItem.getParentId());
        updateRowMap.put("remark", authItem.getRemark());
        
        boolean flag = this.authItemDao.update(id, updateRowMap);
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
    
    /**
     * 根据id更新权限项实例<br/>
     * <功能详细描述>
     * @param authItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(AuthItem authItem) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(authItem, "authItem is null.");
        AssertUtils.notEmpty(authItem.getId(), "authItem.id is empty.");
        
        boolean flag = updateById(authItem.getId(), authItem);
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
    
    /**
     * 根据parentId查询权限项子级实例列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param params
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItem> queryChildrenByParentId(String parentId,
            Map<String, Object> params) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("parentId", parentId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<AuthItem> resList = this.authItemDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 根据parentId查询权限项子级实例列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItem> queryChildrenByParentId(String parentId,
            Querier querier) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        querier.getFilters().add(Filter.eq("parentId", parentId));
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<AuthItem> resList = this.authItemDao.queryList(querier);
        
        return resList;
    }
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param params
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItem> queryDescendantsByParentId(String parentId,
            Map<String, Object> params) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        Set<String> ids = new HashSet<>();
        Set<String> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<AuthItem> resList = doNestedQueryChildren(ids, parentIds, params);
        return resList;
    }
    
    /**
     * 查询嵌套列表<br/>
     * <功能详细描述>
     * @param ids
     * @param parentIds
     * @param params
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<AuthItem> doNestedQueryChildren(Set<String> ids,
            Set<String> parentIds, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<AuthItem>();
        }
        
        //ids避免数据出错时导致无限循环
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(params);
        queryParams.put("parentIds", parentIds);
        List<AuthItem> resList = queryList(queryParams);
        
        Set<String> newParentIds = new HashSet<>();
        for (AuthItem bdTemp : resList) {
            if (!ids.contains(bdTemp.getId())) {
                newParentIds.add(bdTemp.getId());
            }
            ids.add(bdTemp.getId());
        }
        //嵌套查询下一层级
        resList.addAll(doNestedQueryChildren(ids, newParentIds, params));
        return resList;
    }
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItem> queryDescendantsByParentId(String parentId,
            Querier querier) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        Set<String> ids = new HashSet<>();
        Set<String> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<AuthItem> resList = doNestedQueryChildren(ids, parentIds, querier);
        return resList;
    }
    
    /**
     * 嵌套查询列表<br/>
     * <功能详细描述>
     * @param ids
     * @param parentIds
     * @param querier
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<AuthItem> doNestedQueryChildren(Set<String> ids,
            Set<String> parentIds, Querier querier) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<AuthItem>();
        }
        
        //ids避免数据出错时导致无限循环
        Querier querierClone = (Querier) querier.clone();
        querierClone.getFilters().add(Filter.in("parentId", parentIds));
        List<AuthItem> resList = queryList(querierClone);
        
        Set<String> newParentIds = new HashSet<>();
        for (AuthItem bdTemp : resList) {
            if (!ids.contains(bdTemp.getId())) {
                newParentIds.add(bdTemp.getId());
            }
            ids.add(bdTemp.getId());
        }
        //嵌套查询下一层级
        resList.addAll(doNestedQueryChildren(ids, newParentIds, querier));
        return resList;
    }
}
