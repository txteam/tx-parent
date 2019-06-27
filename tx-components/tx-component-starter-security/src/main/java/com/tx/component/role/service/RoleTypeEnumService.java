/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月22日
 * <修改描述:>
 */
package com.tx.component.role.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import com.tx.component.role.context.RoleTypeManager;
import com.tx.component.role.model.RoleType;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;

/**
 * 角色类型枚举业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleTypeEnumService
        implements RoleTypeManager, InitializingBean, Ordered {
    
    /** 角色类型映射 */
    private final Map<String, RoleType> roleTypeMap = new HashMap<>();
    
    /** 角色类型映射 */
    private final Map<String, Class<? extends RoleType>> roleTypeClassMap = new HashMap<>();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Class<? extends RoleType>> roleTypeClazzSet = ClassScanUtils
                .scanByParentClass(RoleType.class, "com.tx");
        
        for (Class<? extends RoleType> roleTypeClazzTemp : roleTypeClazzSet) {
            if (!roleTypeClazzTemp.isEnum()) {
                continue;
            }
            RoleType[] roleTypes = roleTypeClazzTemp.getEnumConstants();
            for (RoleType roleTypeTemp : roleTypes) {
                AssertUtils.notEmpty(roleTypeTemp.getId(),
                        "roleType.id is empty.");
                AssertUtils.notEmpty(roleTypeTemp.getName(),
                        "roleType.name is empty.");
                
                AssertUtils.isTrue(
                        !roleTypeClassMap.containsKey(roleTypeTemp.getId()),
                        "roleTypeId is duplicate.roleId:{},class1:{},class2:{}",
                        new Object[] { roleTypeTemp.getId(),
                                roleTypeClassMap.get(roleTypeTemp.getId()),
                                roleTypeTemp });
                
                roleTypeClassMap.put(roleTypeTemp.getId(), roleTypeClazzTemp);
                roleTypeMap.put(roleTypeTemp.getId(), roleTypeTemp);
            }
        }
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    @Override
    public RoleType findRoleTypeById(String roleTypeId) {
        AssertUtils.notEmpty(roleTypeId, "roleTypeId is empty.");
        if (!roleTypeMap.containsKey(roleTypeId)) {
            return null;
        }
        RoleType roleType = roleTypeMap.get(roleTypeId);
        return roleType;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
}
