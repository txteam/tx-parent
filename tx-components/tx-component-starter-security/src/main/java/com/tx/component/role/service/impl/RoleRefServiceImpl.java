/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月19日
 * <修改描述:>
 */
package com.tx.component.role.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.role.context.RoleRegistry;
import com.tx.component.role.model.Role;
import com.tx.component.role.model.RoleRef;
import com.tx.component.role.model.RoleRefItem;
import com.tx.component.role.service.RoleRefItemService;
import com.tx.component.role.service.RoleRefService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * 角色引用业务层实现类<br>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleRefServiceImpl implements RoleRefService {
    
    /** 角色引用项持久层 */
    private RoleRefItemService roleRefItemService;
    
    /** <默认构造函数> */
    public RoleRefServiceImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public RoleRefServiceImpl(RoleRefItemService roleRefItemService) {
        super();
        this.roleRefItemService = roleRefItemService;
    }
    
    /**
     * @param valid
     * @param querier
     * @return
     */
    @Override
    public List<RoleRef> queryList(Boolean effective, Querier querier) {
        querier = querier == null ? new Querier() : querier;
        querier.getParams().put("effective", effective);
        
        List<RoleRef> roleRefList = roleRefItemService.queryList(querier)
                .stream()
                .collect(Collectors.toList());
        return roleRefList;
    }
    
    /**
     * @param valid
     * @param params
     * @return
     */
    @Override
    public List<RoleRef> queryList(Boolean effective, Map<String, Object> params) {
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("effective", effective);
        
        List<RoleRef> roleRefList = roleRefItemService.queryList(params)
                .stream()
                .collect(Collectors.toList());
        return roleRefList;
    }
    
    /**
     * @param refType
     * @param refId
     * @param roleIds
     * @param effictiveDate
     * @param duration
     */
    @Override
    @Transactional
    public void batchInsertForRoleIds(String refType, String refId,
            List<String> roleIds, Date effectiveDate, Duration duration) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }
        AssertUtils.notEmpty(refType, "refType is empty.");
        AssertUtils.notEmpty(refId, "refId is empty.");
        if (effectiveDate == null) {
            effectiveDate = new Date();
        }
        Date expiryDate = null;
        if (duration != null) {
            expiryDate = com.tx.core.util.DateUtils.add(effectiveDate,
                    duration);
        }
        
        Date now = new Date();
        String createOperatorId = null;
        List<RoleRefItem> roleRefList = new ArrayList<>();
        RoleRegistry roleRegistry = RoleRegistry.getInstance();
        for (String roleIdTemp : roleIds) {
            Role role = roleRegistry.findById(roleIdTemp);
            if (role == null) {
                continue;
            }
            RoleRefItem roleRef = new RoleRefItem();
            roleRef.setCreateDate(now);
            roleRef.setCreateOperatorId(createOperatorId);
            roleRef.setEffectiveDate(effectiveDate);
            roleRef.setExpiryDate(expiryDate);
            roleRef.setRefId(refId);
            roleRef.setRefType(refType);
            roleRef.setRoleId(roleIdTemp);
            
            roleRefList.add(roleRef);
        }
        
        this.roleRefItemService.batchInsert(roleRefList);
    }
    
    /**
     * @param roleId
     * @param refType
     * @param refIds
     * @param effectiveDate
     * @param duration
     */
    @Override
    @Transactional
    public void batchInsertForRefIds(String roleId, String refType,
            List<String> refIds, Date effectiveDate, Duration duration) {
        if (CollectionUtils.isEmpty(refIds)) {
            return;
        }
        AssertUtils.notEmpty(roleId, "roleId is empty.");
        AssertUtils.notEmpty(refType, "refType is empty.");
        if (effectiveDate == null) {
            effectiveDate = new Date();
        }
        Date expiryDate = null;
        if (duration != null) {
            expiryDate = com.tx.core.util.DateUtils.add(effectiveDate,
                    duration);
        }
        
        Date now = new Date();
        String createOperatorId = null;
        List<RoleRefItem> roleRefList = new ArrayList<>();
        RoleRegistry roleRegistry = RoleRegistry.getInstance();
        for (String refIdTemp : refIds) {
            Role role = roleRegistry.findById(roleId);
            if (role == null) {
                continue;
            }
            RoleRefItem roleRef = new RoleRefItem();
            roleRef.setCreateDate(now);
            roleRef.setCreateOperatorId(createOperatorId);
            roleRef.setEffectiveDate(effectiveDate);
            roleRef.setExpiryDate(expiryDate);
            roleRef.setRefId(refIdTemp);
            roleRef.setRefType(refType);
            roleRef.setRoleId(roleId);
            
            roleRefList.add(roleRef);
        }
        
        this.roleRefItemService.batchInsert(roleRefList);
    }
    
    /**
     * @param roleRefs
     */
    @Override
    @Transactional
    public void batchMoveToHis(List<RoleRef> roleRefs) {
        if (CollectionUtils.isEmpty(roleRefs)) {
            return;
        }
        
        List<RoleRefItem> items = new ArrayList<>();
        roleRefs.stream().forEach(item -> {
            if (item instanceof RoleRefItem) {
                items.add((RoleRefItem) item);
            } else {
                RoleRefItem newitem = new RoleRefItem();
                BeanUtils.copyProperties(item, newitem);
                items.add(newitem);
            }
        });
        this.roleRefItemService.batchInsertToHis(items);
        this.roleRefItemService.batchDelete(items);
    }
    
}
