/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月22日
 * <修改描述:>
 */
package com.tx.component.role.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.role.context.RoleManager;
import com.tx.component.role.context.RoleTypeManager;
import com.tx.component.role.model.Role;
import com.tx.component.role.model.RoleType;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;
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
public class RoleConfigService
        implements RoleTypeManager, RoleManager, InitializingBean {
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
    }
    
    /**
     * @param roleId
     * @return
     */
    @Override
    public Role findRoleById(String roleId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param querier
     * @return
     */
    @Override
    public List<Role> queryRoleList(Querier querier) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<Role> queryChildrenRoleByParentId(String parentId,
            Querier querier) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<Role> queryDescendantsRoleByParentId(String parentId,
            Querier querier) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    @Override
    public RoleType findRoleTypeById(String roleTypeId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param querier
     * @return
     */
    @Override
    public List<RoleType> queryRoleTypeList(Querier querier) {
        // TODO Auto-generated method stub
        return null;
    }
}
