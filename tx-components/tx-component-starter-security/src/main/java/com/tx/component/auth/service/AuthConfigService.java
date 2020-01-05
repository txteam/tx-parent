/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月25日
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
import com.tx.component.auth.config.AuthConfig;
import com.tx.component.auth.config.AuthContextConfig;
import com.tx.component.auth.config.AuthTypeConfig;
import com.tx.component.auth.context.AuthManager;
import com.tx.component.auth.context.AuthTypeManager;
import com.tx.component.auth.model.Auth;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthType;
import com.tx.component.auth.model.AuthTypeItem;
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
public class AuthConfigService
        implements AuthManager, AuthTypeManager, InitializingBean, Ordered {
    
    //日志记录句柄
    private Logger logger = LoggerFactory.getLogger(AuthConfigService.class);
    
    /** resourceResolver */
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    
    private XStream authConfigXstream = XstreamUtils
            .getXstream(AuthContextConfig.class);
    
    private String configLocation;
    
    /** 角色类型映射 */
    private Map<String, AuthType> authTypeMap = new HashMap<>();
    
    /** 角色映射 */
    private Map<String, Auth> authMap = new HashMap<>();
    
    /** 角色类型映射 */
    private MultiValueMap<String, Auth> type2authMap = new LinkedMultiValueMap<>();
    
    /** 角色类型映射 */
    private MultiValueMap<String, Auth> parent2authMap = new LinkedMultiValueMap<>();
    
    /** <默认构造函数> */
    public AuthConfigService() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthConfigService(String configLocation) {
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
            AuthContextConfig parser = (AuthContextConfig) authConfigXstream
                    .fromXML(configResource.getInputStream());
            //初始化角色类型配置
            initAuthType(parser.getTypes());
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
    private void initAuthType(List<AuthTypeConfig> types) {
        if (CollectionUtils.isEmpty(types)) {
            return;
        }
        for (AuthTypeConfig typeConfig : types) {
            AssertUtils.notEmpty(typeConfig.getId(), "typeConfig.id is empty.");
            
            AuthTypeItem type = new AuthTypeItem();
            type.setId(typeConfig.getId());
            type.setName(typeConfig.getName());
            type.setRemark(typeConfig.getRemark());
            
            //嵌套初始化配置属性
            initAuth(type, null, typeConfig.getAuths());
            
            //类型配置
            authTypeMap.put(typeConfig.getId(), type);
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
    private List<Auth> initAuth(AuthTypeItem type, AuthItem parent,
            List<AuthConfig> authConfigList) {
        List<Auth> authList = new ArrayList<>();
        if (CollectionUtils.isEmpty(authConfigList)) {
            return authList;
        }
        for (AuthConfig configParserTemp : authConfigList) {
            AssertUtils.notNull(type, "type is null.");
            AssertUtils.notEmpty(type.getId(), "type.id is empty.");
            
            AuthItem auth = new AuthItem();
            auth.setParentId(parent == null ? null : parent.getId());
            auth.setAuthTypeId(type.getId());
            auth.setId(configParserTemp.getId());
            auth.setName(configParserTemp.getName());
            auth.setRemark(configParserTemp.getRemark());
            auth.setConfigAble(configParserTemp.isConfigAble());
            
            //嵌套初始化配置属性
            List<Auth> children = initAuth(type,
                    auth,
                    configParserTemp.getChildren());
            auth.setChildren(children);
            
            type2authMap.add(type.getId(), auth);
            authMap.put(auth.getId(), auth);
            parent2authMap.add(StringUtils.isEmpty(auth.getParentId()) ? null
                    : auth.getParentId(), auth);
            authList.add(auth);
        }
        return authList;
    }
    
    /**
     * @param authTypeId
     * @return
     */
    @Override
    public AuthType findAuthTypeById(String authTypeId) {
        if (!authTypeMap.containsKey(authTypeId)) {
            return null;
        }
        
        AuthType type = authTypeMap.get(authTypeId);
        return type;
    }
    
    /**
     * @return
     */
    @Override
    public List<AuthType> queryAuthTypeList() {
        List<AuthType> resList = new ArrayList<>(authTypeMap.values());
        return resList;
    }
    
    /**
     * @param authId
     * @return
     */
    @Override
    public Auth findAuthById(String authId) {
        if (!authMap.containsKey(authId)) {
            return null;
        }
        
        Auth auth = authMap.get(authId);
        return auth;
    }
    
    /**
     * @param authTypeId
     * @return
     */
    @Override
    public List<Auth> queryAuthList(String... authTypeIds) {
        List<Auth> resList = new ArrayList<>(authMap.values());
        
        if (!ArrayUtils.isEmpty(authTypeIds)) {
            List<String> authTypeIdList = Arrays.asList(authTypeIds);
            
            resList = authMap.values().stream().filter(auth -> {
                return authTypeIdList.contains(auth.getAuthTypeId());
            }).collect(Collectors.toList());
        }
        return resList;
    }
    
    /**
     * @param parentId
     * @param authTypeId
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
     * @param authTypeId
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
     * @return List<Auth> [返回类型说明]
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
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
