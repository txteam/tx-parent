/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月19日
 * <修改描述:>
 */
package com.tx.component.auth.service.impl;

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

import com.tx.component.auth.context.AuthRegistry;
import com.tx.component.auth.model.Auth;
import com.tx.component.auth.model.AuthRef;
import com.tx.component.auth.model.AuthRefItem;
import com.tx.component.auth.service.AuthRefItemService;
import com.tx.component.auth.service.AuthRefService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * 权限引用业务层实现类<br>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthRefServiceImpl implements AuthRefService {
    
    /** 权限引用项持久层 */
    private AuthRefItemService authRefItemService;
    
    /** <默认构造函数> */
    public AuthRefServiceImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthRefServiceImpl(AuthRefItemService authRefItemService) {
        super();
        this.authRefItemService = authRefItemService;
    }
    
    /**
     * @param valid
     * @param querier
     * @return
     */
    @Override
    public List<AuthRef> queryList(Boolean valid, Querier querier) {
        querier = querier == null ? new Querier() : querier;
        querier.getParams().put("valid", valid);
        
        List<AuthRef> authRefList = authRefItemService.queryList(querier)
                .stream()
                .collect(Collectors.toList());
        return authRefList;
    }
    
    /**
     * @param valid
     * @param params
     * @return
     */
    @Override
    public List<AuthRef> queryList(Boolean valid, Map<String, Object> params) {
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("valid", valid);
        
        List<AuthRef> authRefList = authRefItemService.queryList(params)
                .stream()
                .collect(Collectors.toList());
        return authRefList;
    }
    
    /**
     * @param refType
     * @param refId
     * @param authIds
     * @param effictiveDate
     * @param duration
     */
    @Override
    @Transactional
    public void batchInsertForAuthIds(String refType, String refId,
            List<String> authIds, Date effectiveDate, Duration duration) {
        if (CollectionUtils.isEmpty(authIds)) {
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
        List<AuthRefItem> authRefList = new ArrayList<>();
        AuthRegistry authRegistry = AuthRegistry.getInstance();
        for (String authIdTemp : authIds) {
            Auth auth = authRegistry.findById(authIdTemp);
            if (auth == null) {
                continue;
            }
            AuthRefItem authRef = new AuthRefItem();
            authRef.setCreateDate(now);
            authRef.setCreateOperatorId(createOperatorId);
            authRef.setEffectiveDate(effectiveDate);
            authRef.setExpiryDate(expiryDate);
            authRef.setRefId(refId);
            authRef.setRefType(refType);
            authRef.setAuthId(authIdTemp);
            
            authRefList.add(authRef);
        }
        
        this.authRefItemService.batchInsert(authRefList);
    }
    
    /**
     * @param authId
     * @param refType
     * @param refIds
     * @param effectiveDate
     * @param duration
     */
    @Override
    @Transactional
    public void batchInsertForRefIds(String authId, String refType,
            List<String> refIds, Date effectiveDate, Duration duration) {
        if (CollectionUtils.isEmpty(refIds)) {
            return;
        }
        AssertUtils.notEmpty(authId, "authId is empty.");
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
        List<AuthRefItem> authRefList = new ArrayList<>();
        AuthRegistry authRegistry = AuthRegistry.getInstance();
        for (String refIdTemp : refIds) {
            Auth auth = authRegistry.findById(authId);
            if (auth == null) {
                continue;
            }
            AuthRefItem authRef = new AuthRefItem();
            authRef.setCreateDate(now);
            authRef.setCreateOperatorId(createOperatorId);
            authRef.setEffectiveDate(effectiveDate);
            authRef.setExpiryDate(expiryDate);
            authRef.setRefId(refIdTemp);
            authRef.setRefType(refType);
            authRef.setAuthId(authId);
            
            authRefList.add(authRef);
        }
        
        this.authRefItemService.batchInsert(authRefList);
    }
    
    /**
     * @param authRefs
     */
    @Override
    @Transactional
    public void batchMoveToHis(List<AuthRef> authRefs) {
        if (CollectionUtils.isEmpty(authRefs)) {
            return;
        }
        
        List<AuthRefItem> items = new ArrayList<>();
        authRefs.stream().forEach(item -> {
            if (item instanceof AuthRefItem) {
                items.add((AuthRefItem) item);
            } else {
                AuthRefItem newitem = new AuthRefItem();
                BeanUtils.copyProperties(item, newitem);
                items.add(newitem);
            }
        });
        this.authRefItemService.batchInsertToHis(items);
        this.authRefItemService.batchDelete(items);
    }
    
}
