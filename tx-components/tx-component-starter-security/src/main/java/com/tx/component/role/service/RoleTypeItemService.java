/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.role.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.role.context.RoleTypeManager;
import com.tx.component.role.dao.RoleTypeItemDao;
import com.tx.component.role.model.RoleType;
import com.tx.component.role.model.RoleTypeItem;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QuerierBuilder;

/**
 * RoleTypeItem的业务层[RoleTypeItemService]
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RoleTypeItemService implements RoleTypeManager {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(RoleTypeItemService.class);
    
    private RoleTypeItemDao roleTypeItemDao;
    
    /** <默认构造函数> */
    public RoleTypeItemService() {
        super();
    }
    
    /** <默认构造函数> */
    public RoleTypeItemService(RoleTypeItemDao roleTypeItemDao) {
        super();
        this.roleTypeItemDao = roleTypeItemDao;
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    @Override
    public RoleType findRoleTypeById(String roleTypeId) {
        AssertUtils.notEmpty(roleTypeId, "roleTypeId is empty.");
        
        RoleType roleType = findById(roleTypeId);
        return roleType;
    }
    
    /**
     * 新增RoleTypeItem实例<br/>
     * 将roleTypeItem插入数据库中保存
     * 1、如果roleTypeItem 为空时抛出参数为空异常
     * 2、如果roleTypeItem 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param roleTypeItem [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(RoleTypeItem roleTypeItem) {
        //验证参数是否合法
        AssertUtils.notNull(roleTypeItem, "roleTypeItem is null.");
        AssertUtils.notEmpty(roleTypeItem.getName(),
                "roleTypeItem.name is empty.");
        
        //调用数据持久层对实例进行持久化操作
        this.roleTypeItemDao.insert(roleTypeItem);
    }
    
    /**
     * 根据id删除RoleTypeItem实例
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
        
        RoleTypeItem condition = new RoleTypeItem();
        condition.setId(id);
        
        int resInt = this.roleTypeItemDao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据id查询RoleTypeItem实例
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return RoleTypeItem [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public RoleTypeItem findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        RoleTypeItem condition = new RoleTypeItem();
        condition.setId(id);
        
        RoleTypeItem res = this.roleTypeItemDao.find(condition);
        return res;
    }
    
    /**
     * 查询RoleTypeItem实例列表
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RoleTypeItem> queryList(Map<String, Object> params) {
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<RoleTypeItem> resList = this.roleTypeItemDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 查询RoleTypeItem实例列表
     * <功能详细描述>
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RoleTypeItem> queryList(Querier querier) {
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<RoleTypeItem> resList = this.roleTypeItemDao.queryList(querier);
        
        return resList;
    }
    
    /**
     * 分页查询RoleTypeItem实例列表
     * <功能详细描述>
     * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<RoleTypeItem> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<RoleTypeItem> resPagedList = this.roleTypeItemDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 分页查询RoleTypeItem实例列表
     * <功能详细描述>
     * @param querier    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<RoleTypeItem> queryPagedList(Querier querier,
            int pageIndex, int pageSize) {
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<RoleTypeItem> resPagedList = this.roleTypeItemDao
                .queryPagedList(querier, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 查询RoleTypeItem实例数量<br/>
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Map<String, Object> params) {
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.roleTypeItemDao.count(params);
        
        return res;
    }
    
    /**
     * 查询RoleTypeItem实例数量<br/>
     * <功能详细描述>
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Querier querier) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.roleTypeItemDao.count(querier);
        
        return res;
    }
    
    /**
     * 判断RoleTypeItem实例是否已经存在<br/>
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
        int res = this.roleTypeItemDao.count(params);
        
        return res > 0;
    }
    
    /**
     * 判断RoleTypeItem实例是否已经存在<br/>
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
        int res = this.roleTypeItemDao.count(querier, excludeId);
        
        return res > 0;
    }
    
    /**
     * 根据id更新RoleTypeItem实例<br/>
     * <功能详细描述>
     * @param roleTypeItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(String id, RoleTypeItem roleTypeItem) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(roleTypeItem, "roleTypeItem is null.");
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        
        //
        updateRowMap.put("name", roleTypeItem.getName());
        updateRowMap.put("remark", roleTypeItem.getRemark());
        
        boolean flag = this.roleTypeItemDao.update(id, updateRowMap);
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
    
    /**
     * 根据id更新RoleTypeItem实例<br/>
     * <功能详细描述>
     * @param roleTypeItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(RoleTypeItem roleTypeItem) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(roleTypeItem, "roleTypeItem is null.");
        AssertUtils.notEmpty(roleTypeItem.getId(), "roleTypeItem.id is empty.");
        
        boolean flag = updateById(roleTypeItem.getId(), roleTypeItem);
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
}
