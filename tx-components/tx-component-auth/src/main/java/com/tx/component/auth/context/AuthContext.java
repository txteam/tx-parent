/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;

import net.sf.ehcache.Cache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
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
import com.tx.component.auth.dbscript.DataSourceType;
import com.tx.component.auth.exceptions.AuthContextInitException;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.component.auth.model.AuthItemRef;
import com.tx.component.auth.model.AuthItemRefImpl;
import com.tx.component.auth.service.AuthItemImplService;
import com.tx.component.auth.service.AuthItemRefImplService;
import com.tx.core.dbscript.executor.DBScriptAutoExecutor;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.support.cache.map.EhcacheMap;

/**
 * 权限容器<br/>
 * 功能详细描述<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-12-1]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuthContext implements ApplicationContextAware, InitializingBean {
    
    /** 日志记录器 */
    private static final Logger logger = LoggerFactory.getLogger(AuthContext.class);
    
    /* 不需要进行注入部分属性 */
    
    /** 单子模式权限容器唯一实例 */
    private static AuthContext authContext;
    
    /** 当前spring容器 */
    private ApplicationContext applicationContext;
    
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
    
    /* 可注入部分属性 */
    
    /** 默认的权限检查器 */
    private AuthChecker defaultAuthChecker;
    
    /** 权限项缓存对应的缓存生成器 */
    private Cache cache;
    
    /** 系统id 64，用以与其他系统区分 */
    private String systemId;
    
    /** 表后缀名 */
    private String tableSuffix;
    
    /** 数据源类型 */
    private DataSourceType dataSourceType = DataSourceType.H2;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 数据库脚本是否自动执行 */
    private boolean databaseSchemaUpdate = false;
    
    /* 自动注入部分属性 */
    
    /** 权限项业务层 */
    @Resource(name = "authItemImplService")
    private AuthItemImplService authItemService;
    
    /** 权限引用项业务层 */
    @Resource(name = "authItemRefImplService")
    private AuthItemRefImplService authItemRefService;
    
    /**
     * <默认构造函数>
     * 构造函数级别为子类可见<br/>
     */
    protected AuthContext() {
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
        return authContext;
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
        
        logger.info("初始化权限容器表结构.表后缀名为：{}...", this.tableSuffix);
        if (databaseSchemaUpdate) {
            String dbScriptContext = loadDBScript();
            dbScriptContext = StringUtils.replace(dbScriptContext,
                    "${tableSuffix}",
                    this.tableSuffix);
            logger.debug(" 自动初始化权限容器表结构,dbScriptContext：\n{}", dbScriptContext);
            DBScriptAutoExecutor dbExecutor = new DBScriptAutoExecutor(
                    dataSource, dbScriptContext, databaseSchemaUpdate);
            dbExecutor.execute();
            logger.info(" 自动初始化权限容器表结构完成.表后缀名为：{}...", this.tableSuffix);
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
        if (this.cache != null) {
            this.authItemMapping = new EhcacheMap<String, AuthItem>(this.cache);
        } else {
            this.authItemMapping = new HashMap<String, AuthItem>();
        }
        loadAuthItems(this.authLoaderList);
        
        //使系统context指向实体本身
        authContext = this;
        logger.info("初始化权限容器end...");
    }
    
    /** 
     * 加载脚本
     *<功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
    * @throws IOException 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String loadDBScript() {
        String dbScriptBasePath = dataSourceType.getBasePath();
        String dbScriptPath = org.springframework.util.StringUtils.cleanPath("classpath:"
                + dbScriptBasePath + "auth_base_1.0.0.sql");
        logger.info("load authcontext init dbscript from path:{}", dbScriptPath);
        org.springframework.core.io.Resource dbScriptResource = this.applicationContext.getResource(dbScriptPath);
        if (!dbScriptResource.exists()) {
            dbScriptPath = org.springframework.util.StringUtils.cleanPath("classpath*:"
                    + dbScriptBasePath + "auth_base_1.0.0.sql");
            logger.info("load authcontext init dbscript from path:{}",
                    dbScriptPath);
            dbScriptResource = this.applicationContext.getResource(dbScriptPath);
        }
        
        //        URL dbScriptURL = DataSourceType.class.getResource(dataSourceType.getBasePath() + "auth_base_1.0.0.sql");
        //        org.springframework.core.io.Resource dbScriptResource = new UrlResource(
        //                dbScriptURL);
        AssertUtils.isExist(dbScriptResource,
                "dbScriptResource is not exist.path:{}",
                dbScriptPath);
        InputStream in = null;
        
        try {
            in = dbScriptResource.getInputStream();
            return IOUtils.toString(dbScriptResource.getInputStream());
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "read dbScriptResource error.");
        } finally {
            IOUtils.closeQuietly(in);
        }
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
        if (authLoaders == null || authLoaders.size() == 0) {
            logger.warn("AuthContext init.AuthLoader is empty.");
            return;
        }
        
        Map<String, AuthItem> tempAuthItemMapping = new HashMap<String, AuthItem>();
        //一句加载器order值进行排序，根据优先级进行加载
        Collections.sort(authLoaders, OrderComparator.INSTANCE);
        for (AuthLoader authLoaderTemp : authLoaders) {
            //加载权限项
            Set<AuthItem> authItemSet = authLoaderTemp.loadAuthItems();
            
            for (AuthItem authItem : authItemSet) {
                //加载权限并设置加载的权限项目的系统id
                authItem.setSystemId(this.systemId);
                
                //高优先级的加载器加载权限有效，低优先级权限加载器，加载的权限项目，将被忽略
                if (!tempAuthItemMapping.containsKey(authItem.getId())) {
                    tempAuthItemMapping.put(authItem.getId(), authItem);
                } else {
                    //如果低优先级加载器加载的权限项目name,parentId指定了值，而先前的未指定的情况时
                    //用新的覆盖旧的，如果旧值存在，则忽略新值
                    AuthItem realItemTemp = tempAuthItemMapping.get(authItem.getId());
                    if (realItemTemp instanceof AuthItemImpl) {
                        AuthItemImpl realItemImplTemp = (AuthItemImpl) realItemTemp;
                        if (StringUtils.isEmpty(realItemImplTemp.getName())
                                || realItemImplTemp.getId()
                                        .equals(realItemImplTemp.getName())) {
                            realItemImplTemp.setName(authItem.getName());
                        }
                        if (StringUtils.isEmpty(realItemImplTemp.getParentId())) {
                            realItemImplTemp.setParentId(authItem.getParentId());
                        }
                    }
                }
            }
        }
        
        //移除原存在，现在不存在的
        Set<String> needRemoveKeySet = new HashSet<String>();
        for (String authItemKeyTemp : authItemMapping.keySet()) {
            if (!tempAuthItemMapping.containsKey(authItemKeyTemp)) {
                needRemoveKeySet.add(authItemKeyTemp);
            }
        }
        
        //进行添加或更新
        authItemMapping.putAll(tempAuthItemMapping);
        //进行实际的移除
        for (String needRemoveKey : needRemoveKeySet) {
            authItemMapping.remove(needRemoveKey);
        }
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
            List<AuthItemRefImpl> refImplList = this.authItemRefService.queryAuthItemRefListByRefType2RefIdMapping(refType2RefIdMapping,
                    this.systemId,
                    this.tableSuffix);
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
                    "The authKey:{} AuthItem is not exists.",
                    new Object[] { authKey });
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
    
    //    /**
    //      * 注册权限项到容器<br/>
    //      *     如果容器中不存在对应id的权限项会抛出异常<br/>
    //      * <功能详细描述>
    //      * @param id
    //      * @param valid
    //      * @return [参数说明]
    //      * 
    //      * @return AuthItem [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    
    //    public AuthItem registeAuth(String id, boolean valid) {
    //        //参数合法性验证
    //        AssertUtils.notEmpty(id, "id is empty.");
    //        AssertUtils.isTrue(authItemMapping.containsKey(id),
    //                "id:{} not exist in current authContext.",
    //                id);
    //        
    //        Map<String, Object> authItemRowMap = new HashMap<String, Object>();
    //        authItemRowMap.put("id", id);
    //        authItemRowMap.put("valid", valid);
    //        
    //        AuthItem res = doRegisteSaveAuth(authItemRowMap);
    //        return res;
    //    }
    
    //    /**
    //     * 注册权限项到容器<br/>
    //      *     如果容器中不存在对应id的权限项会抛出异常<br/>
    //      * @param id
    //      * @param name
    //      * @param isValid
    //      * @return [参数说明]
    //      * 
    //      * @return AuthItem [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    public AuthItem registeAuth(String id, String name, boolean valid) {
    //        //参数合法性验证
    //        AssertUtils.notEmpty(id, "id is empty.");
    //        AssertUtils.isTrue(authItemMapping.containsKey(id),
    //                "id:{} not exist in current authContext.",
    //                id);
    //        
    //        Map<String, Object> authItemRowMap = new HashMap<String, Object>();
    //        authItemRowMap.put("id", id);
    //        authItemRowMap.put("name", name);
    //        authItemRowMap.put("valid", valid);
    //        
    //        AuthItem res = doRegisteSaveAuth(authItemRowMap);
    //        return res;
    //    }
    
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
            authItemRowMap.put("editAble", authItem.isEditAble());
            authItemRowMap.put("name", authItem.getName());
            authItemRowMap.put("configAble", authItem.isConfigAble());
            authItemRowMap.put("valid", authItem.isValid());
            authItemRowMap.put("viewAble", authItem.isViewAble());
            
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
            String description, String authType, boolean valid,
            boolean configAble, boolean viewAble, boolean editAble) {
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
            newAuthItemImpl.setEditAble(editAble);
            newAuthItemImpl.setConfigAble(configAble);
            newAuthItemImpl.setValid(valid);
            newAuthItemImpl.setViewAble(viewAble);
            
            //持久化权限项
            res = doRegisteNewAuth(newAuthItemImpl);
        } else {
            Map<String, Object> authItemRowMap = new HashMap<String, Object>();
            authItemRowMap.put("id", id);
            
            authItemRowMap.put("parentId", parentId);
            authItemRowMap.put("description", description);
            authItemRowMap.put("editAble", editAble);
            authItemRowMap.put("name", name);
            authItemRowMap.put("configAble", configAble);
            authItemRowMap.put("valid", valid);
            authItemRowMap.put("viewAble", viewAble);
            
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
            String authType, boolean valid) {
        //参数合法性验证
        boolean isNeedNew = false;
        if (StringUtils.isEmpty(id) || !authItemMapping.containsKey(id)) {
            isNeedNew = true;
        }
        
        AuthItemImpl res = null;
        if (isNeedNew) {
            AuthItemImpl newAuthItemImpl = new AuthItemImpl();
            newAuthItemImpl.setId(id);
            newAuthItemImpl.setName(name);
            newAuthItemImpl.setAuthType(authType);
            newAuthItemImpl.setValid(valid);
            
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
            authItemRowMap.put("valid", valid);
            
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
        this.authItemService.insertAuthItemImpl(authItemImpl,
                this.systemId,
                this.tableSuffix);
        
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
        
        final AuthItemImpl authItemImpl = this.authItemService.saveAuthItemImplByAuthItemRowMap(authItemRowMap,
                this.systemId,
                this.tableSuffix);
        
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
     * 增加某一权限 的：对应权限引用类型的新的权限引用id集合
     *     系统会自动过滤掉原已经存在的权限引用，仅对新增的权限引用进行增加
     * <功能详细描述>
     * @param authRefType
     * @param authItemId
     * @param refIdList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void addAuthItemOfAuthRefIdList(String authRefType,
            String authItemId, List<String> addRefIdList) {
        this.authItemRefService.addAuthItemOfAuthRefList(authRefType,
                authItemId,
                addRefIdList,
                this.systemId,
                this.tableSuffix);
    }
    
    /**
      *  删除某一权限 的：对应权限引用类型的新的权限引用id集合
      *      系统会自动过滤掉原不村子啊的权限引用，仅对减少的权限引用进行减少
      *<功能详细描述>
      * @param authRefType
      * @param authItemId
      * @param deleteRefIdList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void deleteAuthItemOfAuthRefIdList(String authRefType,
            String authItemId, List<String> deleteRefIdList) {
        this.authItemRefService.deleteAuthItemOfAuthRefList(authRefType,
                authItemId,
                deleteRefIdList,
                this.systemId,
                this.tableSuffix);
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
                refIdList,
                this.systemId,
                this.tableSuffix);
    }
    
    //    /**
    //     * 保存某一权限 的：对应权限引用类型的新的权限引用id集合
    //     * <功能详细描述>
    //     * @param authRefType
    //     * @param authItemId
    //     * @param refIdList [参数说明]
    //     * 
    //     * @return void [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //    */
    //    public void saveAuthItemOfAuthRefIdList(String authType,
    //            String authRefType, String authItemId, List<String> refIdList) {
    //        this.authItemRefService.saveAuthItemOfAuthRefList(authType,
    //                authRefType,
    //                authItemId,
    //                refIdList,
    //                this.systemId,
    //                this.tableSuffix);
    //    }
    
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
                authItemIdList,
                this.systemId,
                this.tableSuffix);
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
                authItemIdList,
                this.systemId,
                this.tableSuffix);
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
                refId,
                this.systemId,
                this.tableSuffix);
        
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
                authItemId,
                this.systemId,
                this.tableSuffix);
        
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
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefService.queryAuthItemRefListByRefType2RefIdMapping(refType2RefIdMapping,
                this.systemId,
                this.tableSuffix);
        
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
        this.authItemService.deleteById(authItemId,
                this.systemId,
                this.tableSuffix);
        
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
     * @param 对defaultAuthChecker进行赋值
     */
    public void setDefaultAuthChecker(AuthChecker defaultAuthChecker) {
        this.defaultAuthChecker = defaultAuthChecker;
    }
    
    /**
     * @param 对cache进行赋值
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }
    
    /**
     * @param 对systemId进行赋值
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    /**
     * @param 对tableSuffix进行赋值
     */
    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param 对databaseSchemaUpdate进行赋值
     */
    public void setDatabaseSchemaUpdate(boolean databaseSchemaUpdate) {
        this.databaseSchemaUpdate = databaseSchemaUpdate;
    }
    
    public static void main(String[] args) {
        System.out.println(DataSourceType.class.getResource("./h2/auth_base_1.0.0.sql"));
    }
}
