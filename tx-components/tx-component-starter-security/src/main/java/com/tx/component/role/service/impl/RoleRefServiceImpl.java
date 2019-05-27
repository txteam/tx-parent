/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月19日
 * <修改描述:>
 */
package com.tx.component.role.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.role.context.RoleRegistry;
import com.tx.component.role.dao.RoleRefItemDao;
import com.tx.component.role.model.Role;
import com.tx.component.role.model.RoleRef;
import com.tx.component.role.model.RoleRefItem;
import com.tx.component.role.service.RoleRefService;
import com.tx.core.exceptions.util.AssertUtils;

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
    private RoleRefItemDao roleRefItemDao;
    
    /**
     * @param valid
     * @param params
     * @return
     */
    @Override
    public List<RoleRef> queryList(Boolean valid, Map<String, Object> params) {
        List<RoleRef> roleRefList = roleRefItemDao.queryList(params)
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
        }
        this.roleRefItemDao.batchInsert(roleRefList);
    }
    
    /**
     * @param roleId
     * @param refType
     * @param refIds
     * @param effictiveDate
     * @param duration
     */
    @Override
    @Transactional
    public void batchInsertForRefIds(String roleId, String refType,
            List<String> refIds, Date effictiveDate, Duration duration) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param roleRefs
     */
    @Override
    @Transactional
    public void batchMoveToHis(List<RoleRef> roleRefs) {
        // TODO Auto-generated method stub
        
    }
    
}