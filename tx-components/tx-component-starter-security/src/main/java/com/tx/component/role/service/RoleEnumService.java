/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月22日
 * <修改描述:>
 */
package com.tx.component.role.service;

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

import com.tx.component.role.context.RoleManager;
import com.tx.component.role.model.Role;
import com.tx.component.role.model.RoleItem;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;

/**
 * 角色类型枚举业务层<br/>
 * <功能详细描述>
 * 1、修改权限容器：通过auth,role ..EnumService获取值时不再获取枚举实例，而是根据枚举实例构造的item对象考虑到缓存以后需要反序列化可能出的异常做出的调整
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleEnumService implements RoleManager, InitializingBean, Ordered {
    
    /** 角色类型映射 */
    private Map<String, Role> roleMap = new HashMap<>();
    
    /** 角色类型映射 */
    private MultiValueMap<String, Role> type2roleMap = new LinkedMultiValueMap<>();
    
    /** 角色类型映射 */
    private MultiValueMap<String, Role> parent2roleMap = new LinkedMultiValueMap<>();
    
    /** 角色类型映射 */
    private Map<String, Class<? extends Role>> roleClassMap = new HashMap<>();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Class<? extends Role>> roleClazzSet = ClassScanUtils
                .scanByParentClass(Role.class, "com.tx");
        
        for (Class<? extends Role> roleClazzTemp : roleClazzSet) {
            if (!roleClazzTemp.isEnum()) {
                continue;
            }
            Role[] roles = roleClazzTemp.getEnumConstants();
            for (Role roleTemp : roles) {
                AssertUtils.notEmpty(roleTemp.getId(), "role.id is empty.");
                AssertUtils.notEmpty(roleTemp.getName(), "role.name is empty.");
                AssertUtils.notEmpty(roleTemp.getRoleTypeId(),
                        "role.roleTypeId is empty.");
                
                AssertUtils.isTrue(!roleClassMap.containsKey(roleTemp.getId()),
                        "roleTypeId is duplicate.roleId:{},class1:{},class2:{}",
                        new Object[] { roleTemp.getId(),
                                roleClassMap.get(roleTemp.getId()),
                                roleClazzTemp });
                roleClassMap.put(roleTemp.getId(), roleClazzTemp);
                
                RoleItem roleWrapper = new RoleItem(roleTemp);
                roleMap.put(roleWrapper.getId(), roleWrapper);
                type2roleMap.add(roleWrapper.getRoleTypeId(), roleWrapper);
                parent2roleMap.add(roleWrapper.getParentId(), roleWrapper);
            }
        }
    }
    
    /**
     * @param roleId
     * @return
     */
    @Override
    public Role findRoleById(String roleId) {
        if (!roleMap.containsKey(roleId)) {
            return null;
        }
        return roleMap.get(roleId);
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    @Override
    public List<Role> queryRoleList(String... roleTypeIds) {
        List<Role> resList = new ArrayList<>(roleMap.values());
        
        if (!ArrayUtils.isEmpty(roleTypeIds)) {
            List<String> roleTypeIdList = Arrays.asList(roleTypeIds);
            resList = resList.stream().filter(role -> {
                return roleTypeIdList.contains(role.getRoleTypeId());
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
    public List<Role> queryChildrenRoleByParentId(String parentId) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<Role> resList = parent2roleMap.get(parentId);
        return resList;
    }
    
    /**
     * @param parentId
     * @param roleTypeId
     * @return
     */
    @Override
    public List<Role> queryDescendantsRoleByParentId(String parentId) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        //生成查询条件
        Set<String> ids = new HashSet<>();
        Set<String> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<Role> resList = doNestedQueryChildren(ids, parentIds);
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
    private List<Role> doNestedQueryChildren(Set<String> ids,
            Set<String> parentIds) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<Role>();
        }
        
        //ids避免数据出错时导致无限循环
        List<Role> resList = new ArrayList<>();
        for (String parentId : parentIds) {
            resList.addAll(parent2roleMap.get(parentId));
        }
        
        Set<String> newParentIds = new HashSet<>();
        for (Role bdTemp : resList) {
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
