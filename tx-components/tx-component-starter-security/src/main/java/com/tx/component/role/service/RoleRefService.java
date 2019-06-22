/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.role.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.tx.component.role.model.RoleRef;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * 角色引用业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RoleRefService {
    
    /**
     * 查询角色关联集合<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<RoleRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RoleRef> queryList(Boolean valid, Querier querier);
    
    /**
     * 查询角色关联集合<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<RoleRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RoleRef> queryList(Boolean valid, Map<String, Object> params);
    
    /**
     * 根据关联类型映射查询角色关联<br/>
     * <功能详细描述>
     * @param refMap
     * @return [参数说明]
     * 
     * @return List<RoleRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default List<RoleRef> queryListByRefMap(
            MultiValueMap<String, String> refMap) {
        AssertUtils.notEmpty(refMap, "refMap is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refMap", refMap);
        
        List<RoleRef> resList = queryList(true, params);
        return resList;
    }
    
    /**
     * 根据关联类型以及关联id查询角色关联<br/>
     * <功能详细描述>
     * @param refType
     * @param refId
     * @return [参数说明]
     * 
     * @return List<RoleRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default List<RoleRef> queryListByRef(Boolean valid, String refType,
            String refId, Map<String, Object> params) {
        AssertUtils.notEmpty(refType, "refType is empty.");
        AssertUtils.notEmpty(refId, "refId is empty.");
        
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("refType", refType);
        params.put("refId", refId);
        
        List<RoleRef> resList = queryList(valid, params);
        return resList;
    }
    
    /**
     * 根据关联类型以及角色id查询角色关联实例<br/>
     * <功能详细描述>
     * @param roleId
     * @param refType(允许为空)
     * @return [参数说明]
     * 
     * @return List<RoleRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default List<RoleRef> queryListByRoleId(Boolean valid, String roleId,
            String refType, Map<String, Object> params) {
        AssertUtils.notEmpty(roleId, "roleId is empty.");
        
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("roleId", roleId);
        params.put("refType", refType);
        
        List<RoleRef> resList = queryList(valid, params);
        return resList;
    }
    
    /**
     * 为指定角色新增关联引用： 应用于针对某角色赋予多个[人员]的场景<br/>
     *    在引用唯一键方面系统不依赖于数据库唯一键去限定，由代码进行控制，允许多个临时权限存在.
     *    add方法一般服务于新增临时权限场景
     * <功能详细描述>
     * @param roleId
     * @param refType
     * @param refIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    default void addForRefIds(String roleId, String refType,
            List<String> refIds, Date effictiveDate, Duration duration) {
        if (CollectionUtils.isEmpty(refIds)) {
            return;
        }
        AssertUtils.notEmpty(roleId, "roleId is empty.");
        AssertUtils.notEmpty(refType, "refType is empty.");
        AssertUtils.notNull(effictiveDate, "effictiveDate is null.");
        AssertUtils.notNull(duration, "duration is null.");
        
        //查询原关联角色
        Map<String, Object> params = new HashMap<>();
        params.put("hasExpiryDate", false);
        List<RoleRef> sourceRefList = queryListByRoleId(true,
                roleId,
                refType,
                params);
        Stream<RoleRef> sourceStream = sourceRefList.stream();
        
        //识别需要添加的角色列表
        List<String> sourceRefIds = new ArrayList<>();
        sourceStream.forEach(roleRefTemp -> {
            sourceRefIds.add(roleRefTemp.getRefId());
        });
        List<String> needAddRefIds = refIds.stream().filter(refIdTemp -> {
            return !sourceRefIds.contains(refIdTemp);
        }).collect(Collectors.toList());
        batchInsertForRefIds(roleId,
                refType,
                needAddRefIds,
                effictiveDate,
                duration);
    }
    
    /**
     * 为指定[人员]添加角色关联：应用于针对某人员一次性赋予多个角色的场景<br/>
     * <功能详细描述>
     * @param refType
     * @param refId
     * @param roleIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    default void addForRoleIds(String refType, String refId,
            List<String> roleIds, Date effictiveDate, Duration duration) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }
        AssertUtils.notEmpty(refId, "refId is empty.");
        AssertUtils.notEmpty(refType, "refType is empty.");
        AssertUtils.notNull(effictiveDate, "effictiveDate is null.");
        AssertUtils.notNull(duration, "duration is null.");
        
        //查询原关联角色
        Map<String, Object> params = new HashMap<>();
        params.put("hasExpiryDate", false);
        List<RoleRef> sourceRefList = queryListByRef(true,
                refType,
                refId,
                params);
        Stream<RoleRef> sourceStream = sourceRefList.stream();
        
        //识别需要添加的角色列表
        List<String> sourceRoleIds = new ArrayList<>();
        sourceStream.forEach(roleRefTemp -> {
            sourceRoleIds.add(roleRefTemp.getRoleId());
        });
        List<String> needAddRoleIds = roleIds.stream().filter(roleIdTemp -> {
            return !sourceRoleIds.contains(roleIdTemp);
        }).collect(Collectors.toList());
        batchInsertForRoleIds(refType,
                refId,
                needAddRoleIds,
                effictiveDate,
                duration);
    }
    
    /**
     * 
     * <功能详细描述>
     * @param roleId
     * @param refType
     * @param refIds
     * @param filterRefIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    default void saveForRefIds(String roleId, String refType,
            List<String> refIds, List<String> filterRefIds) {
        AssertUtils.notEmpty(roleId, "roleId is empty.");
        AssertUtils.notNull(refIds, "refIds is null.");
        
        //查询原关联角色
        Map<String, Object> params = new HashMap<>();
        params.put("hasExpiryDate", false);
        List<RoleRef> sourceRefList = queryListByRoleId(true,
                roleId,
                refType,
                params);
        if (!CollectionUtils.isEmpty(filterRefIds)) {
            sourceRefList = sourceRefList.stream().filter(roleRefTemp -> {
                return filterRefIds.contains(roleRefTemp.getRefId());
            }).collect(Collectors.toList());
        }
        Stream<RoleRef> sourceStream = sourceRefList.stream();
        
        //识别需要删除的角色
        List<String> sourceRefIds = new ArrayList<>();
        sourceStream.forEach(roleRefTemp -> {
            sourceRefIds.add(roleRefTemp.getRefId());
        });
        List<RoleRef> needDeleteRefs = sourceStream.filter(roleRefTemp -> {
            return !refIds.contains(roleRefTemp.getRefId());
        }).collect(Collectors.toList());
        //移除到历史表
        batchMoveToHis(needDeleteRefs);
        
        //识别需要添加的角色列表
        List<String> needAddRefIds = refIds.stream().filter(refIdTemp -> {
            return !sourceRefIds.contains(refIdTemp);
        }).collect(Collectors.toList());
        batchInsertForRefIds(roleId, refType, needAddRefIds, new Date(), null);
    }
    
    /**
     * 
     * <功能详细描述>
     * @param refType
     * @param refId
     * @param roleIds
     * @param filterRoleIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    default void saveForRoleIds(String refType, String refId,
            List<String> roleIds, List<String> filterRoleIds) {
        AssertUtils.notEmpty(refType, "refType is empty.");
        AssertUtils.notEmpty(refId, "refId is empty.");
        AssertUtils.notNull(roleIds, "roleIds is null.");
        
        //查询原关联角色
        Map<String, Object> params = new HashMap<>();
        params.put("hasExpiryDate", false);
        List<RoleRef> sourceRefList = queryListByRef(true,
                refType,
                refId,
                params);
        if (!CollectionUtils.isEmpty(filterRoleIds)) {
            sourceRefList = sourceRefList.stream().filter(roleRefTemp -> {
                return filterRoleIds.contains(roleRefTemp.getRoleId());
            }).collect(Collectors.toList());
        }
        Stream<RoleRef> sourceStream = sourceRefList.stream();
        
        //识别需要删除的角色
        List<String> sourceRoleIds = new ArrayList<>();
        sourceStream.forEach(roleRefTemp -> {
            sourceRoleIds.add(roleRefTemp.getRoleId());
        });
        List<RoleRef> needDeleteRefs = sourceStream.filter(roleRefTemp -> {
            return !roleIds.contains(roleRefTemp.getRoleId());
        }).collect(Collectors.toList());
        //移除到历史表
        batchMoveToHis(needDeleteRefs);
        
        //识别需要添加的角色列表
        List<String> needAddRoleIds = roleIds.stream().filter(roleIdTemp -> {
            return !sourceRoleIds.contains(roleIdTemp);
        }).collect(Collectors.toList());
        batchInsertForRoleIds(refType, refId, needAddRoleIds, new Date(), null);
    }
    
    /**
     * 服务于给人员配置多个角色的场景<br/>
     * <功能详细描述>
     * @param refType
     * @param refId
     * @param roleIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchInsertForRoleIds(String refType, String refId,
            List<String> roleIds, Date effictiveDate, Duration duration);
    
    /**
     * 根据关联id集合进行批量插入<br/>
     *    服务于将角色配置给多个人员的场景<br/>
     * <功能详细描述>
     * @param roleId
     * @param refType
     * @param refIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchInsertForRefIds(String roleId, String refType,
            List<String> refIds, Date effictiveDate, Duration duration);
    
    /**
     * 批量插入历史表<br/>
     *    用于修改配置后，将历史的存储于历史表中，便于回溯<br/>
     * <功能详细描述>
     * @param roleRefs [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchMoveToHis(List<RoleRef> roleRefs);
}
