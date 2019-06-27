/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月25日
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.thoughtworks.xstream.XStream;
import com.tx.component.role.config.RoleConfig;
import com.tx.component.role.config.RoleContextConfig;
import com.tx.component.role.config.RoleTypeConfig;
import com.tx.component.role.context.RoleManager;
import com.tx.component.role.context.RoleTypeManager;
import com.tx.component.role.model.Role;
import com.tx.component.role.model.RoleItem;
import com.tx.component.role.model.RoleType;
import com.tx.component.role.model.RoleTypeItem;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.XstreamUtils;

/**
 * 角色配置业务业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleConfigService
        implements RoleManager, RoleTypeManager, InitializingBean, Ordered {
    
    //日志记录句柄
    private Logger logger = LoggerFactory.getLogger(RoleConfigService.class);
    
    /** resourceResolver */
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    
    private XStream roleConfigXstream = XstreamUtils
            .getXstream(RoleContextConfig.class);
    
    private String configLocation;
    
    /** 角色类型映射 */
    private final Map<String, RoleType> roleTypeMap = new HashMap<>();
    
    /** 角色映射 */
    private final Map<String, Role> roleMap = new HashMap<>();
    
    /** 角色类型映射 */
    private final MultiValueMap<String, Role> type2roleMap = new LinkedMultiValueMap<>();
    
    /** 角色类型映射 */
    private final MultiValueMap<String, Role> parent2roleMap = new LinkedMultiValueMap<>();
    
    /** <默认构造函数> */
    public RoleConfigService() {
        super();
    }
    
    /** <默认构造函数> */
    public RoleConfigService(String configLocation) {
        super();
        this.configLocation = configLocation;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(this.configLocation)) {
            return;
        }
        String[] locations = StringUtils
                .splitByWholeSeparator(this.configLocation, ",");
        if (ArrayUtils.isEmpty(locations)) {
            return;
        }
        
        Set<org.springframework.core.io.Resource> configResources = new HashSet<>();
        for (String locationTemp : locations) {
            if (StringUtils.isBlank(locationTemp)) {
                continue;
            }
            configResources.addAll(
                    Arrays.asList(resourceResolver.getResources(locationTemp)));
        }
        
        for (org.springframework.core.io.Resource configResource : configResources) {
            if (!configResource.exists()) {
                logger.info("configLocation resource is not exist.");
                continue;
            }
            
            //解析角色配置文件
            RoleContextConfig parser = (RoleContextConfig) roleConfigXstream
                    .fromXML(configResource.getInputStream());
            //初始化角色类型配置
            initRoleType(parser.getTypes());
        }
    }
    
    /**
     * 初始化配置属性<br/>
     * <功能详细描述>
     * @param parserList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void initRoleType(List<RoleTypeConfig> types) {
        if (CollectionUtils.isEmpty(types)) {
            return;
        }
        for (RoleTypeConfig typeConfig : types) {
            AssertUtils.notEmpty(typeConfig.getId(), "typeConfig.id is empty.");
            AssertUtils.notEmpty(typeConfig.getName(),
                    "typeConfig.name is empty.");
            
            RoleTypeItem type = new RoleTypeItem();
            type.setId(typeConfig.getId());
            type.setName(typeConfig.getName());
            type.setRemark(typeConfig.getRemark());
            
            //嵌套初始化配置属性
            initRole(type, null, typeConfig.getRoles());
            
            //类型配置
            roleTypeMap.put(typeConfig.getId(), type);
        }
    }
    
    /**
     * 初始化配置属性<br/>
     * <功能详细描述>
     * @param parserList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<Role> initRole(RoleTypeItem type, RoleItem parent,
            List<RoleConfig> roleConfigList) {
        List<Role> roleList = new ArrayList<>();
        if (CollectionUtils.isEmpty(roleList)) {
            return roleList;
        }
        for (RoleConfig configParserTemp : roleConfigList) {
            AssertUtils.notNull(type, "type is null.");
            AssertUtils.notEmpty(type.getId(), "type.id is empty.");
            AssertUtils.notEmpty(type.getName(), "type.name is empty.");
            
            RoleItem role = new RoleItem();
            role.setParentId(parent == null ? null : parent.getId());
            role.setRoleTypeId(type.getId());
            role.setName(type.getName());
            role.setRemark(type.getRemark());
            //嵌套初始化配置属性
            List<Role> children = initRole(type,
                    role,
                    configParserTemp.getChildren());
            role.setChildren(children);
            
            type2roleMap.add(type.getId(), role);
            roleMap.put(role.getId(), role);
            parent2roleMap.add(StringUtils.isEmpty(role.getParentId()) ? null
                    : role.getParentId(), role);
            roleList.add(role);
        }
        return roleList;
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    @Override
    public RoleType findRoleTypeById(String roleTypeId) {
        if (!roleTypeMap.containsKey(roleTypeId)) {
            return null;
        }
        
        RoleType type = roleTypeMap.get(roleTypeId);
        return type;
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
        
        Role role = roleMap.get(roleId);
        return role;
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
    public List<Role> queryChildrenRoleByParentId(String parentId,
            String... roleTypeIds) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<Role> resList = parent2roleMap.get(parentId);
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
    public List<Role> queryDescendantsRoleByParentId(String parentId,
            String... roleTypeIds) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        //生成查询条件
        Set<String> ids = new HashSet<>();
        Set<String> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<Role> resList = doNestedQueryChildren(ids, parentIds, roleTypeIds);
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
            Set<String> parentIds, String... roleTypeIds) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<Role>();
        }
        
        //ids避免数据出错时导致无限循环
        List<Role> resList = new ArrayList<>();
        for (String parentId : parentIds) {
            resList.addAll(parent2roleMap.get(parentId));
        }
        if (!ArrayUtils.isEmpty(roleTypeIds)) {
            List<String> roleTypeIdList = Arrays.asList(roleTypeIds);
            resList = resList.stream().filter(role -> {
                return roleTypeIdList.contains(role.getRoleTypeId());
            }).collect(Collectors.toList());
        }
        
        Set<String> newParentIds = new HashSet<>();
        for (Role bdTemp : resList) {
            if (!ids.contains(bdTemp.getId())) {
                newParentIds.add(bdTemp.getId());
            }
            ids.add(bdTemp.getId());
        }
        //嵌套查询下一层级
        resList.addAll(doNestedQueryChildren(ids, newParentIds, roleTypeIds));
        return resList;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
