/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.auth.service;

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

import com.tx.component.auth.model.AuthRef;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * 权限引用业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthRefService {
    
    /**
     * 查询权限关联集合<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<AuthRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthRef> queryList(Boolean effective, Querier querier);
    
    /**
     * 查询权限关联集合<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<AuthRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthRef> queryList(Boolean effective,
            Map<String, Object> params);
    
    /**
     * 根据关联类型映射查询权限关联<br/>
     * <功能详细描述>
     * @param refMap
     * @return [参数说明]
     * 
     * @return List<AuthRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default List<AuthRef> queryListByRefMap(Boolean effective,
            MultiValueMap<String, String> refMap) {
        AssertUtils.notEmpty(refMap, "refMap is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refMap", refMap);
        
        List<AuthRef> resList = queryList(true, params);
        return resList;
    }
    
    /**
     * 根据关联类型以及关联id查询权限关联<br/>
     * <功能详细描述>
     * @param refType
     * @param refId
     * @return [参数说明]
     * 
     * @return List<AuthRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default List<AuthRef> queryListByRef(Boolean effective, String refType,
            String refId, Map<String, Object> params) {
        AssertUtils.notEmpty(refType, "refType is empty.");
        AssertUtils.notEmpty(refId, "refId is empty.");
        
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("refType", refType);
        params.put("refId", refId);
        
        List<AuthRef> resList = queryList(effective, params);
        return resList;
    }
    
    /**
     * 根据关联类型以及权限id查询权限关联实例<br/>
     * <功能详细描述>
     * @param authId
     * @param refType(允许为空)
     * @return [参数说明]
     * 
     * @return List<AuthRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default List<AuthRef> queryListByAuthId(Boolean effective, String authId,
            String refType, Map<String, Object> params) {
        AssertUtils.notEmpty(authId, "authId is empty.");
        
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("authId", authId);
        params.put("refType", refType);
        
        List<AuthRef> resList = queryList(effective, params);
        return resList;
    }
    
    /**
     * 为指定权限新增关联引用： 应用于针对某权限赋予多个[人员]的场景<br/>
     *    在引用唯一键方面系统不依赖于数据库唯一键去限定，由代码进行控制，允许多个临时权限存在.
     *    add方法一般服务于新增临时权限场景
     * <功能详细描述>
     * @param authId
     * @param refType
     * @param refIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    default void addForRefIds(String authId, String refType,
            List<String> refIds, Date effictiveDate, Duration duration) {
        if (CollectionUtils.isEmpty(refIds)) {
            return;
        }
        AssertUtils.notEmpty(authId, "authId is empty.");
        AssertUtils.notEmpty(refType, "refType is empty.");
        AssertUtils.notNull(effictiveDate, "effictiveDate is null.");
        AssertUtils.notNull(duration, "duration is null.");
        
        //查询原关联权限
        Map<String, Object> params = new HashMap<>();
        params.put("hasExpiryDate", false);
        List<AuthRef> sourceRefList = queryListByAuthId(true,
                authId,
                refType,
                params);
        Stream<AuthRef> sourceStream = sourceRefList.stream();
        
        //识别需要添加的权限列表
        List<String> sourceRefIds = new ArrayList<>();
        sourceStream.forEach(authRefTemp -> {
            sourceRefIds.add(authRefTemp.getRefId());
        });
        List<String> needAddRefIds = refIds.stream().filter(refIdTemp -> {
            return !sourceRefIds.contains(refIdTemp);
        }).collect(Collectors.toList());
        batchInsertForRefIds(authId,
                refType,
                needAddRefIds,
                effictiveDate,
                duration);
    }
    
    /**
     * 为指定[人员]添加权限关联：应用于针对某人员一次性赋予多个权限的场景<br/>
     * <功能详细描述>
     * @param refType
     * @param refId
     * @param authIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    default void addForAuthIds(String refType, String refId,
            List<String> authIds, Date effictiveDate, Duration duration) {
        if (CollectionUtils.isEmpty(authIds)) {
            return;
        }
        AssertUtils.notEmpty(refId, "refId is empty.");
        AssertUtils.notEmpty(refType, "refType is empty.");
        AssertUtils.notNull(effictiveDate, "effictiveDate is null.");
        AssertUtils.notNull(duration, "duration is null.");
        
        //查询原关联权限
        Map<String, Object> params = new HashMap<>();
        params.put("hasExpiryDate", false);
        List<AuthRef> sourceRefList = queryListByRef(true,
                refType,
                refId,
                params);
        Stream<AuthRef> sourceStream = sourceRefList.stream();
        
        //识别需要添加的权限列表
        List<String> sourceAuthIds = new ArrayList<>();
        sourceStream.forEach(authRefTemp -> {
            sourceAuthIds.add(authRefTemp.getAuthId());
        });
        List<String> needAddAuthIds = authIds.stream().filter(authIdTemp -> {
            return !sourceAuthIds.contains(authIdTemp);
        }).collect(Collectors.toList());
        batchInsertForAuthIds(refType,
                refId,
                needAddAuthIds,
                effictiveDate,
                duration);
    }
    
    /**
     * 
     * <功能详细描述>
     * @param authId
     * @param refType
     * @param refIds
     * @param filterRefIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    default void saveForRefIds(String authId, String refType,
            List<String> refIds, List<String> filterRefIds) {
        AssertUtils.notEmpty(authId, "rolauthIdeId is empty.");
        AssertUtils.notNull(refIds, "refIds is null.");
        
        //查询原关联权限
        Map<String, Object> params = new HashMap<>();
        params.put("hasExpiryDate", false);
        List<AuthRef> sourceRefList = queryListByAuthId(true,
                authId,
                refType,
                params);
        if (!CollectionUtils.isEmpty(filterRefIds)) {
            sourceRefList = sourceRefList.stream().filter(authRefTemp -> {
                return filterRefIds.contains(authRefTemp.getRefId());
            }).collect(Collectors.toList());
        }
        Stream<AuthRef> sourceStream = sourceRefList.stream();
        
        //识别需要删除的权限
        List<String> sourceRefIds = new ArrayList<>();
        sourceStream.forEach(authRefTemp -> {
            sourceRefIds.add(authRefTemp.getRefId());
        });
        List<AuthRef> needDeleteRefs = sourceStream.filter(authRefTemp -> {
            return !refIds.contains(authRefTemp.getRefId());
        }).collect(Collectors.toList());
        //移除到历史表
        batchMoveToHis(needDeleteRefs);
        
        //识别需要添加的权限列表
        List<String> needAddRefIds = refIds.stream().filter(refIdTemp -> {
            return !sourceRefIds.contains(refIdTemp);
        }).collect(Collectors.toList());
        batchInsertForRefIds(authId, refType, needAddRefIds, new Date(), null);
    }
    
    /**
     * 
     * <功能详细描述>
     * @param refType
     * @param refId
     * @param authIds
     * @param filterAuthIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    default void saveForAuthIds(String refType, String refId,
            List<String> authIds, List<String> filterAuthIds) {
        AssertUtils.notEmpty(refType, "refType is empty.");
        AssertUtils.notEmpty(refId, "refId is empty.");
        AssertUtils.notNull(authIds, "authIds is null.");
        
        //查询原关联权限
        Map<String, Object> params = new HashMap<>();
        params.put("hasExpiryDate", false);
        List<AuthRef> sourceRefList = queryListByRef(true,
                refType,
                refId,
                params);
        if (!CollectionUtils.isEmpty(filterAuthIds)) {
            sourceRefList = sourceRefList.stream().filter(authRefTemp -> {
                return filterAuthIds.contains(authRefTemp.getAuthId());
            }).collect(Collectors.toList());
        }
        Stream<AuthRef> sourceStream = sourceRefList.stream();
        
        //识别需要删除的权限
        List<String> sourceAuthIds = new ArrayList<>();
        sourceStream.forEach(authRefTemp -> {
            sourceAuthIds.add(authRefTemp.getAuthId());
        });
        List<AuthRef> needDeleteRefs = sourceStream.filter(authRefTemp -> {
            return !authIds.contains(authRefTemp.getAuthId());
        }).collect(Collectors.toList());
        //移除到历史表
        batchMoveToHis(needDeleteRefs);
        
        //识别需要添加的权限列表
        List<String> needAddAuthIds = authIds.stream().filter(authIdTemp -> {
            return !sourceAuthIds.contains(authIdTemp);
        }).collect(Collectors.toList());
        batchInsertForAuthIds(refType, refId, needAddAuthIds, new Date(), null);
    }
    
    /**
     * 服务于给人员配置多个权限的场景<br/>
     * <功能详细描述>
     * @param refType
     * @param refId
     * @param authIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchInsertForAuthIds(String refType, String refId,
            List<String> authIds, Date effictiveDate, Duration duration);
    
    /**
     * 根据关联id集合进行批量插入<br/>
     *    服务于将权限配置给多个人员的场景<br/>
     * <功能详细描述>
     * @param authId
     * @param refType
     * @param refIds [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchInsertForRefIds(String authId, String refType,
            List<String> refIds, Date effictiveDate, Duration duration);
    
    /**
     * 批量插入历史表<br/>
     *    用于修改配置后，将历史的存储于历史表中，便于回溯<br/>
     * <功能详细描述>
     * @param authRefs [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void batchMoveToHis(List<AuthRef> authRefs);
}
