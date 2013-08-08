/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.component.auth.context.adminchecker.AdminChecker;
import com.tx.component.auth.context.authchecker.AuthChecker;
import com.tx.component.auth.context.authchecker.impl.DefaultAuthChecker;
import com.tx.component.auth.context.loader.AuthLoader;
import com.tx.component.auth.exceptions.AuthContextInitException;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.component.auth.model.AuthItemRef;
import com.tx.component.auth.model.AuthItemRefImpl;
import com.tx.component.auth.service.AuthItemImplService;
import com.tx.component.auth.service.AuthItemRefImplService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 权限容器<br/>
 * 功能详细描述<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-12-1]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuthContext implements ApplicationContextAware, InitializingBean,
        BeanNameAware {
    
    /** 日志记录器 */
    private static final Logger logger = LoggerFactory.getLogger(AuthContext.class);
    
    /** 业务日志记录器：默认使用logback日志记录器  */
    @SuppressWarnings("unused")
    private Logger serviceLogger = LoggerFactory.getLogger(AuthContext.class);
    
    private static AuthContext authContext = null;
    
    /** 当前spring容器 */
    private ApplicationContext applicationContext;
    
    /** spring容器中beanName */
    @SuppressWarnings("unused")
    private String beanName;
    
    /** 默认的权限检查器 */
    private AuthChecker defaultAuthChecker;
    
    /**
     * 权限检查器映射，以权限
     * 权限类型检查器默认会添加几个检查器 用户自定义添加的权限检查器会覆盖该检查器
     */
    private Map<String, AuthChecker> authCheckerMapping;
    
    /** 超级管理员认证器 */
    private Map<String, AdminChecker> adminCheckerMapping;
    
    /** 权限加载器 */
    private List<AuthLoader> authLoaderList;
    
    /**
     * 系统的权限项集合<br/>
     * key为权限项唯一键（key,id）<br/>
     * value为具体的权限项
     */
    private Map<String, AuthItem> authItemMapping;
    
    /** 权限项业务层 */
    @Resource(name = "authItemImplService")
    private AuthItemImplService authItemService;
    
    /** 权限引用项业务层 */
    @Resource(name = "authItemRefImplService")
    private AuthItemRefImplService authItemRefService;
    
    /**
     * <默认构造函数>
     */
    private AuthContext() {
        AssertUtils.isNull(AuthContext.authContext,
                "AuthContext must be singleton.it has be created");
    }
    
    /**
      * 获取权限容器实例
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return AuthContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static AuthContext getContext() {
        AssertUtils.notNull(AuthContext.authContext,
                "AuthContext is not initialize.");
        return authContext;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
    
    /**
     * ApplicationContextAware接口实现<br/>
     * 用以获取spring容器引用
     * @param context
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        this.applicationContext = context;
    }
    
    /**
     * InitializingBean接口的实现，用以在容器参数设置完成后加载相关权限
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("初始化权限容器start...");
        //如果没有设置默认的权限检查器
        if (this.defaultAuthChecker == null) {
            this.defaultAuthChecker = new DefaultAuthChecker();
        }
        
        //加载超级管理员认证器
        Collection<AdminChecker> adminCheckers = this.applicationContext.getBeansOfType(AdminChecker.class)
                .values();
        loadAdminChecker(adminCheckers);
        //加载权限检查器
        logger.info("      加载权限检查器...");
        
        //读取系统中注册的权限检查器
        Collection<AuthChecker> authCheckers = this.applicationContext.getBeansOfType(AuthChecker.class)
                .values();
        loadAuthChecker(authCheckers);
        //向容器中注册加载器
        logger.info("      加载权限项加载器...");
        //读取系统中注册的加载器
        Collection<AuthLoader> authLoader = this.applicationContext.getBeansOfType(AuthLoader.class)
                .values();
        this.authLoaderList = new ArrayList<AuthLoader>(authLoader);
        loadAuthItems(this.authLoaderList);
        
        //使系统context指向实体本身
        authContext = this;
        logger.info("初始化权限容器end...");
    }
    
    /**
     * 加载系统的权限项
     * 1、加载同时，检查对应的权限检查器是否存在，如果不存在，则抛出异常提示，对应权限检查器不存在
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void loadAuthItems(List<AuthLoader> authLoaders) {
        //权限项映射
        Map<String, AuthItem> tempAuthItemMapping = new HashMap<String, AuthItem>();
        if (authLoaders == null || authLoaders.size() == 0) {
            logger.warn("AuthContext init.AuthLoader is empty.");
            authItemMapping = tempAuthItemMapping;
            return;
        }
        
        //一句加载器order值进行排序，根据优先级进行加载
        Collections.sort(authLoaders, new OrderComparator());
        for (AuthLoader authLoaderTemp : authLoaders) {
            //加载权限项
            Set<AuthItem> authItemSet = authLoaderTemp.loadAuthItems();
            
            for (AuthItem authItem : authItemSet) {
                tempAuthItemMapping.put(authItem.getId(), authItem);
            }
        }
        authItemMapping = tempAuthItemMapping;
    }
    
    /**
     * 加载权限检查器<br/>
     *  <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void loadAuthChecker(Collection<AuthChecker> authCheckers) {
        //加载系统中存在的权限检查器
        Map<String, AuthChecker> tempAuthCheckerMapping = new HashMap<String, AuthChecker>();
        if (authCheckers == null || authCheckers.size() == 0) {
            authCheckerMapping = tempAuthCheckerMapping;
            return;
        }
        
        for (AuthChecker authCheckerTemp : authCheckers) {
            logger.info("加载权限检查器：权限类型:'{}':检查器类：'{}'",
                    authCheckerTemp.getCheckAuthType(),
                    authCheckerTemp.getClass().getName());
            tempAuthCheckerMapping.put(authCheckerTemp.getCheckAuthType(),
                    authCheckerTemp);
        }
        
        authCheckerMapping = tempAuthCheckerMapping;
    }
    
    /**
     * 加载权限检查器<br/>
     *  <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void loadAdminChecker(Collection<AdminChecker> adminCheckers) {
        Map<String, AdminChecker> tempAuthCheckerMapping = new HashMap<String, AdminChecker>();
        //加载系统中存在的权限检查器
        if (adminCheckers == null || adminCheckers.size() == 0) {
            adminCheckerMapping = tempAuthCheckerMapping;
            return;
        }
        
        for (AdminChecker authCheckerTemp : adminCheckers) {
            logger.info("加载超级管理员认证器：超级管理员引用类型:'{}':认证器类：'{}'",
                    authCheckerTemp.refType(),
                    authCheckerTemp.getClass().getName());
            tempAuthCheckerMapping.put(authCheckerTemp.refType(),
                    authCheckerTemp);
        }
        adminCheckerMapping = tempAuthCheckerMapping;
    }
    
    /**
     * 重新加载权限配置
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void reLoadAuthItems() {
        Collection<AuthLoader> authLoader = this.applicationContext.getBeansOfType(AuthLoader.class)
                .values();
        
        logger.info("      重新加载权限项...start");
        loadAuthItems(new ArrayList<AuthLoader>(authLoader));
        logger.info("      重新加载权限项...end");
    }
    
    /**
      * 获取当前权限项映射
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,AuthItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, AuthItem> getAllAuthItemMapping() {
        @SuppressWarnings("unchecked")
        Map<String, AuthItem> resMap = MapUtils.unmodifiableMap(authItemMapping);
        return resMap;
    }
    
    /**
      * 获取系统中所有权限项的列表
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<AuthItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItem> getAllAuthItemList() {
        @SuppressWarnings("unchecked")
        List<AuthItem> resList = ListUtils.unmodifiableList(new ArrayList<AuthItem>(
                authItemMapping.values()));
        return resList;
    }
    
    /**
      * 根据权限类型及引用id映射查询权限引用集合<br/>
      * <功能详细描述>
      * @param refType2RefIdMapping [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void login(Map<String, String> refType2RefIdMapping) {
        AssertUtils.notEmpty(refType2RefIdMapping,
                "refType2RefIdMapping is empty");
        
        List<AuthItemRef> authItemRefList = null;
        
        boolean isSuperAdmin = false;
        String adminRefType = null;
        String adminRefId = null;
        if (!MapUtils.isEmpty(adminCheckerMapping)) {
            for (Entry<String, String> refType2RefIdEntry : refType2RefIdMapping.entrySet()) {
                if (adminCheckerMapping.get(refType2RefIdEntry.getKey()) != null
                        && adminCheckerMapping.get(refType2RefIdEntry.getKey())
                                .isSuperAdmin(refType2RefIdEntry.getValue())) {
                    isSuperAdmin = true;
                    adminRefType = refType2RefIdEntry.getKey();
                    adminRefId = refType2RefIdEntry.getValue();
                }
            }
        }
        
        if (isSuperAdmin) {
            //如果是超级管理员则拥有所有权限项的引用
            authItemRefList = new ArrayList<AuthItemRef>();
            for (AuthItem authItemTemp : authItemMapping.values()) {
                AuthItemRefImpl newAuthItemRefTemp = new AuthItemRefImpl();
                newAuthItemRefTemp.setAuthItem(authItemTemp);
                newAuthItemRefTemp.setAuthRefType(adminRefType);
                newAuthItemRefTemp.setRefId(adminRefId);
                //是否是临时权限
                newAuthItemRefTemp.setValidDependEndDate(false);
                
                //构建的超级管理员的权限引用
                authItemRefList.add(newAuthItemRefTemp);
            }
        } else {
            //如果不是超级管理员，根据引用表查询得到相关的权限引用
            authItemRefList = new ArrayList<AuthItemRef>();
            List<AuthItemRefImpl> refImplList = this.authItemRefService.queryAuthItemRefListByRefType2RefIdMapping(refType2RefIdMapping);
            if (refImplList != null) {
                for (AuthItemRefImpl refImplTemp : refImplList) {
                    loadAuthItemRef(refImplTemp);
                }
            }
        }
        
        //将权限引用写入容器
        AuthSessionContext.putAuthRefToSession(authItemRefList);
    }
    
    /**
      * 加载权限引用项
      * <功能详细描述>
      * @param refImpl [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void loadAuthItemRef(AuthItemRefImpl refImpl) {
        if (refImpl == null || refImpl.getAuthItem() == null) {
            return;
        }
        
        AuthItem realAuthItem = authItemMapping.get(refImpl.getAuthItem()
                .getId());
        if (realAuthItem != null) {
            refImpl.setAuthItem(realAuthItem);
        }
    }
    
    /**
      * 从权限容器中，根据权限项id,获取对应的权限项
      * <功能详细描述>
      * @param authItemId
      * @return [参数说明]
      * 
      * @return AuthItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public AuthItem getAuthItemFromContextById(String authItemId) {
        return authItemMapping.get(authItemId);
    }
    
    /**
     * 判断是否具有某权限<br/>
     * authType:除定制的几类权限特性以外， 可以为 业务权限 产品权限
     * 这里权限验证会根据当前会话以及对应的权限验证器判断是否具有对应权限
     * 
     * @param authKey
     * @param authType
     * @param objects
     *            判断权限传入的参数，可以为业务ID，可以为等等。。。。 该参数会直接传入对应的authChecker中
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean hasAuth(String authKey, Object... objects) {
        //检查对应权限的权限类型是否正确
        AuthItem authItem = authItemMapping.get(authKey);
        if (authItem == null) {
            throw new AuthContextInitException(
                    "The authKey:{} AuthItem is not exists.", authKey);
        }
        
        //如果对应权限项已经无效，则认为拥有权限
        if (!authItem.isValid()) {
            return true;
        }
        
        //如果当前权限引用依赖有效时间，则判断该权限引用是否还有效
        List<AuthItemRef> authItemRefList = AuthSessionContext.getAuthRefListFromSession(authItem.getId());
        if (!CollectionUtils.isEmpty(authItemRefList)) {
            //根据权限类型获取对应的权限检查器映射
            AuthChecker authChecker = null;
            if (!authCheckerMapping.containsKey(authItem.getAuthType())) {
                authChecker = this.defaultAuthChecker;
            }
            return authChecker.isHasAuth(authItemRefList, objects);
        } else {
            return false;
        }
    }
    
    /**
      * 注册权限项到容器<br/>
      *     如果容器中不存在对应id的权限项会抛出异常<br/>
      * <功能详细描述>
      * @param id
      * @param isValid
      * @return [参数说明]
      * 
      * @return AuthItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    
    public AuthItem registeAuth(String id, boolean isValid) {
        //参数合法性验证
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.isTrue(authItemMapping.containsKey(id),
                "id:{} not exist in current authContext.",
                id);
        
        Map<String, Object> authItemRowMap = new HashMap<String, Object>();
        authItemRowMap.put("id", id);
        authItemRowMap.put("isValid", isValid);
        
        AuthItem res = doRegisteSaveAuth(authItemRowMap);
        return res;
    }
    
    /**
     * 注册权限项到容器<br/>
      *     如果容器中不存在对应id的权限项会抛出异常<br/>
      * @param id
      * @param name
      * @param isValid
      * @return [参数说明]
      * 
      * @return AuthItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public AuthItem registeAuth(String id, String name, boolean isValid) {
        //参数合法性验证
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.isTrue(authItemMapping.containsKey(id),
                "id:{} not exist in current authContext.",
                id);
        
        Map<String, Object> authItemRowMap = new HashMap<String, Object>();
        authItemRowMap.put("id", id);
        authItemRowMap.put("name", name);
        authItemRowMap.put("isValid", isValid);
        
        AuthItem res = doRegisteSaveAuth(authItemRowMap);
        return res;
    }
    
    /**
      * 装载注册权限项<br/>
      *     如果对应权限项已经存在，则进行更新<br/>
      *     如果对应权限项不存在，则进行增加<br/>
      * <功能详细描述>
      * @param authItem
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public AuthItem registeAuth(AuthItem authItem) {
        AssertUtils.notNull(authItem, "authItem is null");
        //参数合法性验证
        //参数合法性验证
        boolean isNeedNew = false;
        if (StringUtils.isEmpty(authItem.getId())
                || !authItemMapping.containsKey(authItem.getId())) {
            isNeedNew = true;
        }
        
        AuthItemImpl res = null;
        if (isNeedNew) {
            AssertUtils.notNull(authItem, "authItemImpl is null");
            AssertUtils.notEmpty(authItem.getAuthType(), "authType is empty.");
            
            //构建注册实体
            AuthItemImpl newAuthItemImpl = new AuthItemImpl();
            newAuthItemImpl.setId(authItem.getId());
            newAuthItemImpl.setParentId(authItem.getParentId());
            newAuthItemImpl.setAuthType(authItem.getAuthType());
            newAuthItemImpl.setDescription(authItem.getDescription());
            newAuthItemImpl.setEditAble(authItem.isEditAble());
            newAuthItemImpl.setName(authItem.getName());
            newAuthItemImpl.setConfigAble(authItem.isConfigAble());
            newAuthItemImpl.setValid(authItem.isValid());
            newAuthItemImpl.setViewAble(authItem.isViewAble());
            
            //持久化权限项
            res = doRegisteNewAuth(newAuthItemImpl);
        } else {
            Map<String, Object> authItemRowMap = new HashMap<String, Object>();
            authItemRowMap.put("id", authItem.getId());
            
            authItemRowMap.put("parentId", authItem.getParentId());
            authItemRowMap.put("description", authItem.getDescription());
            authItemRowMap.put("isEditAble", authItem.isEditAble());
            authItemRowMap.put("name", authItem.getName());
            authItemRowMap.put("isConfigAble", authItem.isConfigAble());
            authItemRowMap.put("isValid", authItem.isValid());
            authItemRowMap.put("isViewAble", authItem.isViewAble());
            
            res = doRegisteSaveAuth(authItemRowMap);
        }
        return res;
    }
    
    /**
      * 装载注册权限项<br/>
      *     动态注入容器<br/>
      *     该注入过程，调用注入的authRegister进行，
      *     如果找不到对应权限类型，则调用
      *     如果对应权限项已经存在，则根据传入的信息进行更新，如果不存在则插入<br/>
      *<功能详细描述>
      * @param authType
      * @param name
      * @param description
      * @param isEditAble
      * @return [参数说明]
      * 
      * @return AuthItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public AuthItem registeAuth(String id, String parentId, String name,
            String description, String authType, boolean isValid,
            boolean isConfigAble, boolean isViewAble, boolean isEditAble) {
        //参数合法性验证
        boolean isNeedNew = false;
        if (StringUtils.isEmpty(id) || !authItemMapping.containsKey(id)) {
            isNeedNew = true;
        }
        
        AuthItemImpl res = null;
        if (isNeedNew) {
            AuthItemImpl newAuthItemImpl = new AuthItemImpl();
            newAuthItemImpl.setId(id);
            newAuthItemImpl.setParentId(parentId);
            newAuthItemImpl.setName(name);
            newAuthItemImpl.setAuthType(authType);
            newAuthItemImpl.setDescription(description);
            newAuthItemImpl.setEditAble(isEditAble);
            newAuthItemImpl.setConfigAble(isConfigAble);
            newAuthItemImpl.setValid(isValid);
            newAuthItemImpl.setViewAble(isViewAble);
            
            //持久化权限项
            res = doRegisteNewAuth(newAuthItemImpl);
        } else {
            Map<String, Object> authItemRowMap = new HashMap<String, Object>();
            authItemRowMap.put("id", id);
            
            authItemRowMap.put("parentId", parentId);
            authItemRowMap.put("description", description);
            authItemRowMap.put("isEditAble", isEditAble);
            authItemRowMap.put("name", name);
            authItemRowMap.put("isConfigAble", isConfigAble);
            authItemRowMap.put("isValid", isValid);
            authItemRowMap.put("isViewAble", isViewAble);
            
            res = doRegisteSaveAuth(authItemRowMap);
        }
        
        return res;
    }
    
    /**
      * 装载注册权限项<br/>
      *     动态注入容器<br/>
      *     该注入过程，调用注入的authRegister进行，
      *     如果找不到对应权限类型，则调用
      *     如果对应权限项已经存在，则根据传入的信息进行更新，如果不存在则插入<br/>
      *<功能详细描述>
      * @param id
      * @param name
      * @param authType
      * @param isValid
      * @return [参数说明]
      * 
      * @return AuthItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public AuthItem registeAuth(String id, String name, String description,
            String authType, boolean isValid) {
        //参数合法性验证
        boolean isNeedNew = false;
        if (StringUtils.isEmpty(id) || !authItemMapping.containsKey(id)) {
            isNeedNew = true;
        }
        
        AuthItemImpl res = null;
        if (isNeedNew) {
            AuthItemImpl newAuthItemImpl = new AuthItemImpl();
            newAuthItemImpl.setName(name);
            newAuthItemImpl.setAuthType(authType);
            newAuthItemImpl.setValid(isValid);
            
            newAuthItemImpl.setEditAble(true);
            newAuthItemImpl.setConfigAble(true);
            newAuthItemImpl.setViewAble(true);
            
            //持久化权限项
            res = doRegisteNewAuth(newAuthItemImpl);
        } else {
            Map<String, Object> authItemRowMap = new HashMap<String, Object>();
            authItemRowMap.put("id", id);
            
            authItemRowMap.put("name", name);
            authItemRowMap.put("description", description);
            authItemRowMap.put("isValid", isValid);
            
            res = doRegisteSaveAuth(authItemRowMap);
        }
        return res;
    }
    
    /**
      * 装载注册权限项<br/>
      *     动态注入容器<br/>
      *     该注入过程，调用注入的authRegister进行
      *<功能详细描述>
      * @param name
      * @param description
      * @param authType
      * @return [参数说明]
      * 
      * @return AuthItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public AuthItem registeAuth(String name, String description, String authType) {
        //参数合法性验证
        AssertUtils.notEmpty(authType, "authType is empty.");
        
        AuthItemImpl newAuthItemImpl = new AuthItemImpl();
        newAuthItemImpl.setAuthType(authType);
        newAuthItemImpl.setDescription(description);
        
        newAuthItemImpl.setName(name);
        
        newAuthItemImpl.setConfigAble(true);
        newAuthItemImpl.setEditAble(true);
        newAuthItemImpl.setValid(true);
        newAuthItemImpl.setViewAble(true);
        
        //持久化权限项
        return doRegisteNewAuth(newAuthItemImpl);
    }
    
    /**
     * 装载注册权限项<br/>
     *     动态注入容器<br/>
     *     该注入过程，调用注入的authRegister进行
     *<功能详细描述>
     * @param name
     * @param description
     * @param authType
     * @return [参数说明]
     * 
     * @return AuthItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public AuthItem registeAuth(String name, String description,
            String authType, boolean isValid, boolean isConfigAble,
            boolean isEditAble, boolean isViewAble) {
        //参数合法性验证
        AssertUtils.notEmpty(authType, "authType is empty.");
        
        AuthItemImpl newAuthItemImpl = new AuthItemImpl();
        newAuthItemImpl.setAuthType(authType);
        newAuthItemImpl.setDescription(description);
        newAuthItemImpl.setName(name);
        
        newAuthItemImpl.setConfigAble(isViewAble);
        newAuthItemImpl.setEditAble(isEditAble);
        newAuthItemImpl.setValid(isValid);
        newAuthItemImpl.setViewAble(isConfigAble);
        
        //持久化权限项
        return doRegisteNewAuth(newAuthItemImpl);
    }
    
    /**
      * 向容器中注册新权限项<br/>
      * <功能详细描述>
      * @param authItemImpl
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private AuthItemImpl doRegisteNewAuth(final AuthItemImpl authItemImpl) {
        AssertUtils.notNull(authItemImpl, "authItemImpl is null");
        AssertUtils.notEmpty(authItemImpl.getAuthType(), "authType is empty.");
        
        //获取权限类型项（如果权限类型项不存在，则注册）
        //向权限容器中注册权类型
        AuthTypeItemContext.getContext()
                .registeAuthTypeItem(authItemImpl.getAuthType());
        
        //持久化对应的权限项到数据库中
        this.authItemService.insertAuthItemImpl(authItemImpl);
        
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            //如果在事务逻辑中执行
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    authItemMapping.put(authItemImpl.getId(), authItemImpl);
                }
            });
        } else {
            //如果在非事务中执行
            authItemMapping.put(authItemImpl.getId(), authItemImpl);
        }
        
        return authItemImpl;
    }
    
    /**
      * 更新容器中的权限项
      * <功能详细描述>
      * @param authItemRowMap
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private AuthItemImpl doRegisteSaveAuth(
            final Map<String, Object> authItemRowMap) {
        AssertUtils.notNull(authItemRowMap, "authItemRowMap is null");
        AssertUtils.notEmpty((String) authItemRowMap.get("id"),
                "authItemRowMap.id is empty.");
        
        final AuthItemImpl authItemImpl = this.authItemService.saveAuthItemImplByAuthItemRowMap(authItemRowMap);
        
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            //如果在事务逻辑中执行
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    authItemMapping.put(authItemImpl.getId(), authItemImpl);
                }
            });
        } else {
            //如果在非事务中执行
            authItemMapping.put(authItemImpl.getId(), authItemImpl);
        }
        
        return authItemImpl;
    }
    
    /**
      * 保存某一权限 的：对应权限引用类型的新的权限引用id集合
      * <功能详细描述>
      * @param authRefType
      * @param authItemId
      * @param refIdList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void saveAuthItemOfAuthRefIdList(String authRefType,
            String authItemId, List<String> refIdList) {
        this.authItemRefService.saveAuthItemOfAuthRefList(authRefType,
                authItemId,
                refIdList);
    }
    
    /**
     * 保存某一权限 的：对应权限引用类型的新的权限引用id集合
     * <功能详细描述>
     * @param authRefType
     * @param authItemId
     * @param refIdList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void saveAuthItemOfAuthRefIdList(String authType,
            String authRefType, String authItemId, List<String> refIdList) {
        this.authItemRefService.saveAuthItemOfAuthRefList(authType,
                authRefType,
                authItemId,
                refIdList);
    }
    
    /**
      * 保存指定引用类型引用id的：引用到的权限id的集合
      * <功能详细描述>
      * @param authRefType
      * @param refId
      * @param authItemIdList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void saveAuthRefOfAuthItemIdList(String authRefType, String refId,
            List<String> authItemIdList) {
        this.authItemRefService.saveAuthRefOfAuthItemList(authRefType,
                refId,
                authItemIdList);
    }
    
    /**
     * 保存指定引用类型引用id的：引用到的权限id的集合
     * <功能详细描述>
     * @param authRefType
     * @param refId
     * @param authItemIdList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void saveAuthRefOfAuthItemIdList(String authType,
            String authRefType, String refId, List<String> authItemIdList) {
        this.authItemRefService.saveAuthRefOfAuthItemList(authType,
                authRefType,
                refId,
                authItemIdList);
    }
    
    /**
      * 根据引用id以及权限引用类型查询权限引用集合<br/>
      *<功能详细描述>
      * @param authRefType
      * @param refId
      * @return [参数说明]
      * 
      * @return List<AuthItemRef> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryAuthItemRefListByAuthRefTypeAndRefId(
            String authRefType, String refId) {
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefService.queryAuthItemRefListByRefTypeAndRefId(authRefType,
                refId);
        
        List<AuthItemRef> resList = changeAuthItemRefImplListToAuthItemRefList(authItemRefImplList);
        return resList;
    }
    
    /**
      * 根据引用类型以及权限项id获取，对应的引用类型中有哪些引用实体id引用了该权限<br/>
      *<功能详细描述>
      * @param authRefType
      * @param authItemId
      * @return [参数说明]
      * 
      * @return List<AuthItemRef> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryAuthItemRefListByAuthRefTypeAndAuthItemId(
            String authRefType, String authItemId) {
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefService.queryAuthItemRefListByRefTypeAndAuthItemId(authRefType,
                authItemId);
        
        List<AuthItemRef> resList = changeAuthItemRefImplListToAuthItemRefList(authItemRefImplList);
        return resList;
    }
    
    /**
      * 根据具体的权限引用类型以及引用id查询权限引用集合<br/>
      *     系统登录需要调用该方法，以获知当前人员拥有的权限引用集合<br/>
      *<功能详细描述>
      * @param refType2RefIdMapping
      * @return [参数说明]
      * 
      * @return List<AuthItemRef> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryAuthItemRefListByRefType2RefIdMapping(
            Map<String, String> refType2RefIdMapping) {
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefService.queryAuthItemRefListByRefType2RefIdMapping(refType2RefIdMapping);
        
        List<AuthItemRef> resList = changeAuthItemRefImplListToAuthItemRefList(authItemRefImplList);
        return resList;
    }
    
    /**
      * 将authItemRefImpl列表转换为authItemRef列表<br/>
      * 并在转换过程中将实际的authItem实例设置进去<br/>
      * <功能详细描述>
      * @param authItemRefImplList
      * @return [参数说明]
      * 
      * @return List<AuthItemRef> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private List<AuthItemRef> changeAuthItemRefImplListToAuthItemRefList(
            List<AuthItemRefImpl> authItemRefImplList) {
        List<AuthItemRef> resList = new ArrayList<AuthItemRef>();
        if (CollectionUtils.isEmpty(authItemRefImplList)) {
            return resList;
        }
        
        for (AuthItemRefImpl authItemRefTemp : authItemRefImplList) {
            authItemRefTemp.setAuthItem(authItemMapping.get(authItemRefTemp.getAuthItem()
                    .getId()));
            resList.add(authItemRefTemp);
        }
        
        return resList;
    }
    
    /**
      * 卸载权限项
      * <功能详细描述>
      * @param authItemId [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void unRegisteAuth(String authItemId) {
        AssertUtils.notEmpty(authItemId, "authItemId is empty.");
        
        //卸载权限项
        doUnRegisteAuth(authItemId);
    }
    
    /**
      * 卸载权限项<br/>
      * <功能详细描述>
      * @param authItemImpl
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void doUnRegisteAuth(final String authItemId) {
        AssertUtils.notEmpty(authItemId, "authItemId is empty.");
        
        //持久化对应的权限项到数据库中
        this.authItemService.deleteById(authItemId);
        
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            //如果在事务逻辑中执行
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    authItemMapping.remove(authItemId);
                }
            });
        } else {
            //如果在非事务中执行
            authItemMapping.remove(authItemId);
        }
    }
    
    /**
      * 判断是否拥有某权限<br/>
      * <功能详细描述>
      * @param authKey
      * @param objects
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isHasAuth(String authKey, Object... objects) {
        AssertUtils.notNull(authContext, "AuthContext init fail.");
        return authContext.hasAuth(authKey, objects);
    }
    
    /**
     * @return 返回 defaultAuthChecker
     */
    public AuthChecker getDefaultAuthChecker() {
        return defaultAuthChecker;
    }
    
    /**
     * @param 对defaultAuthChecker进行赋值
     */
    public void setDefaultAuthChecker(AuthChecker defaultAuthChecker) {
        this.defaultAuthChecker = defaultAuthChecker;
    }
    
    /**
     * @return 返回 authItemService
     */
    public AuthItemImplService getAuthItemService() {
        return authItemService;
    }
    
    /**
     * @param 对authItemService进行赋值
     */
    public void setAuthItemService(AuthItemImplService authItemService) {
        this.authItemService = authItemService;
    }
    
    /**
     * @return 返回 authItemRefService
     */
    public AuthItemRefImplService getAuthItemRefService() {
        return authItemRefService;
    }
    
    /**
     * @param 对authItemRefService进行赋值
     */
    public void setAuthItemRefService(AuthItemRefImplService authItemRefService) {
        this.authItemRefService = authItemRefService;
    }
}
