/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.role.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.role.dao.RoleRefItemDao;
import com.tx.component.role.model.RoleRefItem;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QuerierBuilder;

/**
 * 角色引用的业务层[RoleRefItemService]
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RoleRefItemService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(RoleRefItemService.class);
    
    private RoleRefItemDao roleRefItemDao;
    
    /** <默认构造函数> */
    public RoleRefItemService() {
        super();
    }
    
    /** <默认构造函数> */
    public RoleRefItemService(RoleRefItemDao roleRefItemDao) {
        super();
        this.roleRefItemDao = roleRefItemDao;
    }
    
    /**
     * 新增角色引用实例<br/>
     * 将roleRefItem插入数据库中保存
     * 1、如果roleRefItem 为空时抛出参数为空异常
     * 2、如果roleRefItem 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param roleRefItem [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insertToHis(RoleRefItem roleRefItem) {
        //验证参数是否合法
        AssertUtils.notNull(roleRefItem, "roleRefItem is null.");
        AssertUtils.notEmpty(roleRefItem.getId(), "roleRef.id is empty.");
        AssertUtils.notEmpty(roleRefItem.getRoleId(),
                "roleRef.roleId is empty.");
        AssertUtils.notEmpty(roleRefItem.getRefType(),
                "roleRef.refType is empty.");
        AssertUtils.notEmpty(roleRefItem.getRefId(), "roleRef.refId is empty.");
        
        //为添加的数据需要填入默认值的字段填入默认值
        Date now = new Date();
        roleRefItem.setLastUpdateDate(now);
        
        //调用数据持久层对实例进行持久化操作
        this.roleRefItemDao.insertToHis(roleRefItem);
    }
    
    /**
     * 新增角色引用实例<br/>
     * 将roleRefItem插入数据库中保存
     * 1、如果roleRefItem 为空时抛出参数为空异常
     * 2、如果roleRefItem 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param roleRefItem [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchInsertToHis(List<RoleRefItem> roleRefItems) {
        if (CollectionUtils.isEmpty(roleRefItems)) {
            return;
        }
        
        Date now = new Date();
        roleRefItems.stream().forEach(roleRef -> {
            roleRef.setLastUpdateDate(now);
            
            AssertUtils.notEmpty(roleRef.getId(), "roleRef.id is empty.");
        });
        
        //调用数据持久层对实例进行持久化操作
        this.roleRefItemDao.batchInsertToHis(roleRefItems);
    }
    
    /**
     * 新增角色引用实例<br/>
     * 将roleRefItem插入数据库中保存
     * 1、如果roleRefItem 为空时抛出参数为空异常
     * 2、如果roleRefItem 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param roleRefItem [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(RoleRefItem roleRefItem) {
        //验证参数是否合法
        AssertUtils.notNull(roleRefItem, "roleRefItem is null.");
        AssertUtils.notEmpty(roleRefItem.getRoleId(),
                "roleRef.roleId is empty.");
        AssertUtils.notEmpty(roleRefItem.getRefType(),
                "roleRef.refType is empty.");
        AssertUtils.notEmpty(roleRefItem.getRefId(), "roleRef.refId is empty.");
        
        //为添加的数据需要填入默认值的字段填入默认值
        Date now = new Date();
        roleRefItem.setCreateDate(now);
        roleRefItem.setLastUpdateDate(now);
        if (roleRefItem.getEffectiveDate() == null) {
            roleRefItem.setEffectiveDate(now);
        }
        
        //调用数据持久层对实例进行持久化操作
        this.roleRefItemDao.insert(roleRefItem);
    }
    
    /**
     * 新增角色引用实例<br/>
     * 将roleRefItem插入数据库中保存
     * 1、如果roleRefItem 为空时抛出参数为空异常
     * 2、如果roleRefItem 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param roleRefItem [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchInsert(List<RoleRefItem> roleRefItems) {
        if (CollectionUtils.isEmpty(roleRefItems)) {
            return;
        }
        
        Date now = new Date();
        roleRefItems.stream().forEach(roleRef -> {
            roleRef.setCreateDate(now);
            roleRef.setLastUpdateDate(now);
            if (roleRef.getEffectiveDate() == null) {
                roleRef.setEffectiveDate(now);
            }
            
            AssertUtils.notEmpty(roleRef.getRoleId(),
                    "roleRef.roleId is empty.");
            AssertUtils.notEmpty(roleRef.getRefType(),
                    "roleRef.refType is empty.");
            AssertUtils.notEmpty(roleRef.getRefId(), "roleRef.refId is empty.");
        });
        
        //调用数据持久层对实例进行持久化操作
        this.roleRefItemDao.batchInsert(roleRefItems);
    }
    
    /**
     * 根据id删除角色引用实例
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
        
        RoleRefItem condition = new RoleRefItem();
        condition.setId(id);
        
        int resInt = this.roleRefItemDao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 新增角色引用实例<br/>
     * 将roleRefItem插入数据库中保存
     * 1、如果roleRefItem 为空时抛出参数为空异常
     * 2、如果roleRefItem 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param roleRefItem [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchDelete(List<RoleRefItem> roleRefItems) {
        if (CollectionUtils.isEmpty(roleRefItems)) {
            return;
        }
        
        roleRefItems.stream().forEach(roleRef -> {
            AssertUtils.notEmpty(roleRef.getId(), "roleRef.id is empty.");
        });
        
        //调用数据持久层对实例进行持久化操作
        this.roleRefItemDao.batchDelete(roleRefItems);
    }
    
    /**
     * 根据id查询角色引用实例
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return RoleRefItem [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public RoleRefItem findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        RoleRefItem condition = new RoleRefItem();
        condition.setId(id);
        
        RoleRefItem res = this.roleRefItemDao.find(condition);
        return res;
    }
    
    /**
     * 查询角色引用实例列表
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<RoleRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RoleRefItem> queryList(Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<RoleRefItem> resList = this.roleRefItemDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 查询角色引用实例列表
     * <功能详细描述>
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<RoleRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RoleRefItem> queryList(Querier querier) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<RoleRefItem> resList = this.roleRefItemDao.queryList(querier);
        
        return resList;
    }
    
    /**
     * 分页查询角色引用实例列表
     * <功能详细描述>
     * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<RoleRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<RoleRefItem> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<RoleRefItem> resPagedList = this.roleRefItemDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 分页查询角色引用实例列表
     * <功能详细描述>
     * @param querier    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<RoleRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<RoleRefItem> queryPagedList(Querier querier, int pageIndex,
            int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<RoleRefItem> resPagedList = this.roleRefItemDao
                .queryPagedList(querier, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 查询角色引用实例数量<br/>
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<RoleRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.roleRefItemDao.count(params);
        
        return res;
    }
    
    /**
     * 查询角色引用实例数量<br/>
     * <功能详细描述>
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<RoleRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Querier querier) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.roleRefItemDao.count(querier);
        
        return res;
    }
    
    /**
     * 判断角色引用实例是否已经存在<br/>
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
        int res = this.roleRefItemDao.count(params);
        
        return res > 0;
    }
    
    /**
     * 判断角色引用实例是否已经存在<br/>
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
        int res = this.roleRefItemDao.count(querier, excludeId);
        
        return res > 0;
    }
    
    /**
     * 根据id更新角色引用实例<br/>
     * <功能详细描述>
     * @param roleRefItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(String id, RoleRefItem roleRefItem) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(roleRefItem, "roleRefItem is null.");
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        //需要更新的字段
        updateRowMap.put("refId", roleRefItem.getRefId());
        updateRowMap.put("refType", roleRefItem.getRefType());
        updateRowMap.put("roleId", roleRefItem.getRoleId());
        updateRowMap.put("expiryDate", roleRefItem.getExpiryDate());
        updateRowMap.put("effectiveDate", roleRefItem.getEffectiveDate());
        updateRowMap.put("lastUpdateDate", new Date());
        
        boolean flag = this.roleRefItemDao.update(id, updateRowMap);
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
    
    /**
     * 根据id更新角色引用实例<br/>
     * <功能详细描述>
     * @param roleRefItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(RoleRefItem roleRefItem) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(roleRefItem, "roleRefItem is null.");
        AssertUtils.notEmpty(roleRefItem.getId(), "roleRefItem.id is empty.");
        
        boolean flag = updateById(roleRefItem.getId(), roleRefItem);
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
}
