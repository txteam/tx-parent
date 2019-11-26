/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.Cache;
import org.springframework.core.OrderComparator;

import com.tx.component.auth.model.Auth;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 权限业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthManagerComposite {
    
    /** 权限管理器实现 */
    private List<CachingAuthManager> delegates;
    
    /** <默认构造函数> */
    public AuthManagerComposite(List<AuthManager> roleManagers, Cache cache) {
        super();
        this.delegates = new ArrayList<>();
        AssertUtils.notNull(cache, "cache is null.");
        
        Collections.sort(roleManagers,OrderComparator.INSTANCE);
        if (!CollectionUtils.isEmpty(roleManagers)) {
            roleManagers.stream().forEach(rmTemp -> {
                if (rmTemp instanceof CachingAuthManager) {
                    this.delegates.add((CachingAuthManager) rmTemp);
                } else {
                    this.delegates.add(new CachingAuthManager(rmTemp, cache));
                }
            });
        }
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    public Auth findById(String authId) {
        AssertUtils.notEmpty(authId, "authId is empty.");
        
        Auth roleTemp = null;
        for (AuthManager rm : delegates) {
            roleTemp = rm.findAuthById(authId);
            if (roleTemp != null) {
                return roleTemp;
            }
        }
        return roleTemp;
    }
    
    /**
     * @param authTypeId
     * @return
     */
    public List<Auth> queryList(String... authTypeIds) {
        List<Auth> resList = new ArrayList<>();
        
        Set<String> authIdSet = new HashSet<>();
        for (AuthManager rm : delegates) {
            List<Auth> tempList = rm.queryAuthList(authTypeIds);
            if (CollectionUtils.isEmpty(tempList)) {
                continue;
            }
            tempList.forEach(auth -> {
                if (!authIdSet.contains(auth.getId())) {
                    resList.add(auth);
                    authIdSet.add(auth.getId());
                }
            });
        }
        return resList;
    }
    
    /**
     * @param authTypeId
     * @return
     */
    public List<Auth> queryChildrenByParentId(String parentId,
            String... authTypeIds) {
        List<Auth> resList = new ArrayList<>();
        
        Set<String> authIdSet = new HashSet<>();
        for (AuthManager rm : delegates) {
            List<Auth> tempList = rm.queryChildrenAuthByParentId(parentId,
                    authTypeIds);
            if (CollectionUtils.isEmpty(tempList)) {
                continue;
            }
            tempList.forEach(auth -> {
                if (!authIdSet.contains(auth.getId())) {
                    resList.add(auth);
                    authIdSet.add(auth.getId());
                }
            });
        }
        return resList;
    }
    
    /**
     * @param authTypeId
     * @return
     */
    public List<Auth> queryDescendantsByParentId(String parentId,
            String... authTypeIds) {
        List<Auth> resList = new ArrayList<>();
        
        Set<String> authIdSet = new HashSet<>();
        for (AuthManager rm : delegates) {
            List<Auth> tempList = rm.queryDescendantsAuthByParentId(parentId,
                    authTypeIds);
            if (CollectionUtils.isEmpty(tempList)) {
                continue;
                
            }
            tempList.forEach(auth -> {
                if (!authIdSet.contains(auth.getId())) {
                    resList.add(auth);
                    authIdSet.add(auth.getId());
                }
            });
        }
        return resList;
    }
    
}
