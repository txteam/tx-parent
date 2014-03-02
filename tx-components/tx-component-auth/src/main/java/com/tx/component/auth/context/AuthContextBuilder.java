/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月26日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.tx.component.auth.context.adminchecker.AdminChecker;
import com.tx.component.auth.context.authchecker.AuthChecker;
import com.tx.component.auth.context.authchecker.impl.DefaultAuthChecker;
import com.tx.component.auth.context.loader.AuthLoader;
import com.tx.component.auth.context.loader.impl.DBAuthLoader;
import com.tx.component.auth.context.loader.impl.XmlAuthLoader;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.component.auth.service.AuthItemImplService;
import com.tx.component.auth.service.AuthItemRefImplService;
import com.tx.component.auth.service.NotTempAuthItemRefImplService;
import com.tx.core.dbscript.TableDefinition;
import com.tx.core.dbscript.XMLTableDefinition;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.cache.map.EhcacheMap;

/**
 * 权限容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年2月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthContextBuilder extends AuthContextConfigurator implements
        InitializingBean, ApplicationContextAware {
    
    /** 权限表定义文件路径 */
    private static final String authItemTableDefinitionLocation = "classpath:/com/tx/component/auth/script/auth_authitem_table.xml";
    
    /** 权限引用表定义文件路径 */
    private static final String authRefDefinitionLocation = "classpath:/com/tx/component/auth/script/auth_authref_table.xml";
    
    /** 权限引用表定义文件路径 */
    private static final String authRefHisDefinitionLocation = "classpath:/com/tx/component/auth/script/auth_authref_his_table.xml";
    
    /** 权限加载器 */
    protected List<AuthLoader> authLoaderList;
    
    /**
     * 权限检查器映射，以权限
     * 权限类型检查器默认会添加几个检查器 用户自定义添加的权限检查器会覆盖该检查器
     */
    protected Map<String, AuthChecker> authCheckerMapping;
    
    /** 超级管理员认证器 */
    protected Map<String, AdminChecker> adminCheckerMapping;
    
    /**
     * 系统的权限项集合<br/>
     * key为权限项唯一键（key,id）<br/>
     * value为具体的权限项
     */
    protected Map<String, AuthItem> authItemMapping;
    
    /** 权限项业务层 */
    protected AuthItemImplService authItemService;
    
    /** 权限引用项业务层 */
    protected AuthItemRefImplService authItemRefService;
    
    /** 非临时权限引用处理业务层 */
    protected NotTempAuthItemRefImplService notTempAuthItemRefImplService;
    
    /**
     * InitializingBean接口的实现，用以在容器参数设置完成后加载相关权限
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("初始化权限容器start...");
        if (this.dataSource == null) {
            AssertUtils.notNull(this.jdbcTemplate,
                    "dataSource or jdbcTemplate is null");
            AssertUtils.notNull(this.platformTransactionManager,
                    "platformTransactionManager or jdbcTemplate is null");
        } else {
            if (this.jdbcTemplate == null) {
                this.jdbcTemplate = new JdbcTemplate(this.dataSource);
            }
            if (this.platformTransactionManager == null) {
                this.platformTransactionManager = new DataSourceTransactionManager(
                        this.dataSource);
            }
        }
        
        this.authItemRefService = new AuthItemRefImplService(
                this.platformTransactionManager, this.jdbcTemplate);
        this.authItemService = new AuthItemImplService(
                this.platformTransactionManager, this.jdbcTemplate,
                this.authItemRefService);
        this.notTempAuthItemRefImplService = new NotTempAuthItemRefImplService(
                this.platformTransactionManager, this.jdbcTemplate);
        this.authLoaderList = new ArrayList<AuthLoader>();
        this.authLoaderList.add(new DBAuthLoader(this.tableSuffix,
                this.systemId, this.authItemService));
        this.authLoaderList.add(new XmlAuthLoader(this.applicationContext,
                this.authConfigLocaions));
        
        //如果没有设置默认的权限检查器
        if (this.defaultAuthChecker == null) {
            this.defaultAuthChecker = new DefaultAuthChecker();
        }
        
        logger.info("初始化权限容器表结构.表后缀名为：{}...", this.tableSuffix);
        if (databaseSchemaUpdate && dbScriptExecutorContext != null) {
            Map<String, String> replaceDataMap = new HashMap<String, String>();
            replaceDataMap.put("tableSuffix", this.tableSuffix);
            TableDefinition authItemTableDefinition = new XMLTableDefinition(
                    authItemTableDefinitionLocation, replaceDataMap);
            TableDefinition authRefTableDefinition = new XMLTableDefinition(
                    authRefDefinitionLocation, replaceDataMap);
            TableDefinition authRefHisTableDefinition = new XMLTableDefinition(
                    authRefHisDefinitionLocation, replaceDataMap);
            
            this.dbScriptExecutorContext.createOrUpdateTable(authItemTableDefinition);
            this.dbScriptExecutorContext.createOrUpdateTable(authRefTableDefinition);
            this.dbScriptExecutorContext.createOrUpdateTable(authRefHisTableDefinition);
            
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
        Collection<AuthLoader> authLoaders = this.applicationContext.getBeansOfType(AuthLoader.class)
                .values();
        this.authLoaderList.addAll(authLoaders);
        if (this.cache != null) {
            this.authItemMapping = new EhcacheMap<String, AuthItem>(this.cache);
        } else {
            this.authItemMapping = new HashMap<String, AuthItem>();
        }
        loadAuthItems(this.authLoaderList);
        
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
    protected void loadAuthItems(List<AuthLoader> authLoaders) {
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
                AssertUtils.notNull(authItem, "authItem is null.");
                AssertUtils.notEmpty(authItem.getId(), "authItem.id is empty.");
                AssertUtils.notTrue(authItem.getId()
                        .equals(authItem.getParentId()),
                        "authItem.id equals authItem.parentId.authItem.id:{},parentId:{}",
                        authItem.getId(),authItem.getParentId());
                
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
    
}
