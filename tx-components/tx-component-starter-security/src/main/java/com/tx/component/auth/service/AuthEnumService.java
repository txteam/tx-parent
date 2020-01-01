/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月22日
 * <修改描述:>
 */
package com.tx.component.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.auth.context.AuthManager;
import com.tx.component.auth.model.Auth;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;

/**
 * 权限枚举业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthEnumService implements AuthManager, InitializingBean, Ordered {
    
    /** 权限映射 */
    private Map<String, Auth> authMap = new HashMap<>();
    
    /** 权限映射 */
    private MultiValueMap<String, Auth> type2authMap = new LinkedMultiValueMap<>();
    
    /** 权限映射 */
    private MultiValueMap<String, Auth> parent2authMap = new LinkedMultiValueMap<>();
    
    /** 权限映射 */
    private Map<String, Class<? extends Auth>> authClassMap = new HashMap<>();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Class<? extends Auth>> authClazzSet = ClassScanUtils
                .scanByParentClass(Auth.class, "com.tx");
        
        for (Class<? extends Auth> authClazzTemp : authClazzSet) {
            if (!authClazzTemp.isEnum()) {
                continue;
            }
            Auth[] auths = authClazzTemp.getEnumConstants();
            for (Auth authTemp : auths) {
                AssertUtils.notEmpty(authTemp.getId(), "auth.id is empty.");
                AssertUtils.notEmpty(authTemp.getName(), "auth.name is empty.");
                AssertUtils.notEmpty(authTemp.getAuthTypeId(),
                        "auth.authTypeId is empty.");
                
                AssertUtils.isTrue(!authClassMap.containsKey(authTemp.getId()),
                        "roleTypeId is duplicate.roleId:{},class1:{},class2:{}",
                        new Object[] { authTemp.getId(),
                                authClassMap.get(authTemp.getId()),
                                authClazzTemp });
                authClassMap.put(authTemp.getId(), authClazzTemp);
                
                authMap.put(authTemp.getId(), authTemp);
                
                type2authMap.add(authTemp.getAuthTypeId(), authTemp);
                parent2authMap.add(authTemp.getParentId(), authTemp);
            }
        }
    }
    
    /**
     * @param roleId
     * @return
     */
    @Override
    public Auth findAuthById(String authId) {
        if (!authMap.containsKey(authId)) {
            return null;
        }
        return authMap.get(authId);
    }
    
    /**
     * @param authTypeId
     * @return
     */
    @Override
    public List<Auth> queryAuthList(String... authTypeIds) {
        List<Auth> resList = new ArrayList<>(authMap.values());
        
        if (!ArrayUtils.isEmpty(authTypeIds)) {
            List<String> roleTypeIdList = Arrays.asList(authTypeIds);
            resList = resList.stream().filter(role -> {
                return roleTypeIdList.contains(role.getAuthTypeId());
            }).collect(Collectors.toList());
        }
        return resList;
    }
    
    /**
     * @param parentId
     * @param roleTypeId
     * @return
     */
    @Override
    public List<Auth> queryChildrenAuthByParentId(String parentId) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<Auth> resList = parent2authMap.get(parentId);
        return resList;
    }
    
    /**
     * @param parentId
     * @param roleTypeId
     * @return
     */
    @Override
    public List<Auth> queryDescendantsAuthByParentId(String parentId) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        //生成查询条件
        Set<String> ids = new HashSet<>();
        Set<String> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<Auth> resList = doNestedQueryChildren(ids, parentIds);
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
     * @return List<RoleItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<Auth> doNestedQueryChildren(Set<String> ids,
            Set<String> parentIds) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<Auth>();
        }
        
        //ids避免数据出错时导致无限循环
        List<Auth> resList = new ArrayList<>();
        for (String parentId : parentIds) {
            resList.addAll(parent2authMap.get(parentId));
        }
        
        Set<String> newParentIds = new HashSet<>();
        for (Auth bdTemp : resList) {
            if (!ids.contains(bdTemp.getId())) {
                newParentIds.add(bdTemp.getId());
            }
            ids.add(bdTemp.getId());
        }
        //嵌套查询下一层级
        resList.addAll(doNestedQueryChildren(ids, newParentIds));
        return resList;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
}
