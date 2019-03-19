///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2014年2月26日
// * <修改描述:>
// */
//package com.tx.component.auth.context;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.core.OrderComparator;
//
//import com.tx.component.auth.context.adminchecker.AdminChecker;
//import com.tx.component.auth.context.authchecker.AuthChecker;
//import com.tx.component.auth.context.authchecker.impl.DefaultAuthChecker;
//import com.tx.component.auth.context.loader.AuthLoader;
//import com.tx.component.auth.model.Auth;
//import com.tx.component.auth.model.AuthItem;
//import com.tx.component.auth.persister.AuthItemPersister;
//import com.tx.component.auth.service.AuthItemRefImplService;
//import com.tx.component.auth.service.AuthItemService;
//import com.tx.component.auth.service.NotTempAuthItemRefImplService;
//import com.tx.core.dbscript.TableDefinition;
//import com.tx.core.dbscript.XMLTableDefinition;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * 权限容器构建器<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2014年2月26日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class AuthContextBuilder extends AuthContextConfigurator {
//    
//    /** 权限表定义文件路径 */
//    private static final String authItemTableDefinitionLocation = "classpath:/com/tx/component/auth/script/auth_authitem_table.xml";
//    
//    /** 权限引用表定义文件路径 */
//    private static final String authRefDefinitionLocation = "classpath:/com/tx/component/auth/script/auth_authref_table.xml";
//    
//    /** 权限引用表定义文件路径 */
//    private static final String authRefHisDefinitionLocation = "classpath:/com/tx/component/auth/script/auth_authref_his_table.xml";
//    
//    /** 权限加载器 */
//    private List<AuthLoader> authLoaderList;
//    
//    /** 权限加载器处理器列表 */
//    private List<AuthItemLoaderProcessor> authLoaderProcessorList;
//    
//    /**
//     * 权限检查器映射，以权限
//     * 权限类型检查器默认会添加几个检查器 用户自定义添加的权限检查器会覆盖该检查器
//     */
//    protected Map<String, AuthChecker> authCheckerMapping;
//    
//    /** 超级管理员认证器 */
//    protected Map<String, AdminChecker> adminCheckerMapping;
//    
////    /**
////     * 系统的权限项集合<br/>
////     * key为权限项唯一键（key,id）<br/>
////     * value为具体的权限项
////     */
////    protected Map<String, Auth> authItemMapping;
////    
////    @Resource(name = "authItemPersister")
////    private AuthItemPersister authItemPersister;
////    
////    /** 权限项业务层 */
////    @Resource(name = "authItemImplService")
////    protected AuthItemService authItemImplService;
////    
////    /** 权限引用项业务层 */
////    @Resource(name = "authItemRefImplService")
////    protected AuthItemRefImplService authItemRefImplService;
////    
////    /** 非临时权限引用处理业务层 */
////    @Resource(name = "notTempAuthItemRefImplService")
////    protected NotTempAuthItemRefImplService notTempAuthItemRefImplService;
//    
//    /**
//     * InitializingBean接口的实现，用以在容器参数设置完成后加载相关权限
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        logger.info("初始化权限容器start...");
//        logger.info("初始化权限容器表结构.表后缀名为：{}...", this.tableSuffix);
//        databaseSchemaUpdate();
//        
//        super.afterPropertiesSet();
//        //如果没有设置默认的权限检查器
//        if (this.defaultAuthChecker == null) {
//            this.defaultAuthChecker = new DefaultAuthChecker();
//        }
//        
//        //加载超级管理员认证器
//        Collection<AdminChecker> adminCheckers = this.applicationContext
//                .getBeansOfType(AdminChecker.class).values();
//        loadAdminChecker(adminCheckers);
//        //加载权限检查器
//        logger.info("      加载权限检查器...");
//        //读取系统中注册的权限检查器
//        Collection<AuthChecker> authCheckers = this.applicationContext
//                .getBeansOfType(AuthChecker.class).values();
//        loadAuthChecker(authCheckers);
//        
//        //向容器中注册加载器
//        logger.info("      加载权限项加载器...");
//        //读取系统中注册的加载器
//        Collection<AuthLoader> authLoaders = this.applicationContext
//                .getBeansOfType(AuthLoader.class).values();
//        loadAuthLoader(authLoaders);
//        
//        //权限加载器处理器，处理权限加载器加载权限完成后的一些后置处理逻辑
//        Collection<AuthItemLoaderProcessor> loaderProcessors = this.applicationContext
//                .getBeansOfType(AuthItemLoaderProcessor.class).values();
//        this.authLoaderProcessorList = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(loaderProcessors)) {
//            for (AuthItemLoaderProcessor processorTemp : loaderProcessors) {
//                this.authLoaderProcessorList.add(processorTemp);
//            }
//        }
//        Collections.sort(this.authLoaderProcessorList,
//                OrderComparator.INSTANCE);
//        
//        logger.info("      加载权限项...");
//        this.authItemMapping = loadAuthItems(this.authLoaderList,
//                this.authLoaderProcessorList);
//        
//        logger.info("初始化权限容器end...");
//    }
//    
//    /**
//     * 加载系统的权限项
//     * 1、加载同时，检查对应的权限检查器是否存在，如果不存在，则抛出异常提示，对应权限检查器不存在
//     * <功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private Map<String, Auth> loadAuthItems(List<AuthLoader> authLoaders,
//            List<AuthItemLoaderProcessor> loaderProcessors) {
//        Map<String, Auth> resMap = null;
//        
//        Map<String, Auth> tempAuthItemMapping = new HashMap<String, Auth>();
//        //一句加载器order值进行排序，根据优先级进行加载
//        Collections.sort(authLoaders, OrderComparator.INSTANCE);
//        for (AuthLoader authLoaderTemp : authLoaders) {
//            //加载权限项
//            @SuppressWarnings("unchecked")
//            Set<Auth> authItemSet = authLoaderTemp
//                    .loadAuthItems((Map<String, Auth>) MapUtils
//                            .unmodifiableMap(tempAuthItemMapping));
//            if (CollectionUtils.isEmpty(authItemSet)) {
//                continue;
//            }
//            for (AuthItemLoaderProcessor processor : loaderProcessors) {
//                //将处理完成的权限集合一并压入
//                authItemSet.addAll(processor.postProcessAfterLoad(
//                        tempAuthItemMapping, authItemSet));
//            }
//            
//            for (Auth authItem : authItemSet) {
//                AssertUtils.notNull(authItem, "authItem is null.");
//                AssertUtils.notEmpty(authItem.getId(), "authItem.id is empty.");
//                AssertUtils.notTrue(
//                        authItem.getId().equals(authItem.getParentId()),
//                        "authItem.id equals authItem.parentId.authItem.id:{},parentId:{}",
//                        authItem.getId(),
//                        authItem.getParentId());
//                
//                //加载权限并设置加载的权限项目的系统id
//                authItem.setSystemId(this.systemId);
//                //高优先级的加载器加载权限有效，低优先级权限加载器，加载的权限项目，将被忽略
//                if (!tempAuthItemMapping.containsKey(authItem.getId())) {
//                    tempAuthItemMapping.put(authItem.getId(), authItem);
//                } else {
//                    //如果低优先级加载器加载的权限项目name,parentId指定了值，而先前的未指定的情况时
//                    //用新的覆盖旧的，如果旧值存在，则忽略新值
//                    Auth realItemTemp = tempAuthItemMapping
//                            .get(authItem.getId());
//                    if (realItemTemp instanceof AuthItem) {
//                        AuthItem realItemImplTemp = (AuthItem) realItemTemp;
//                        if (StringUtils.isEmpty(realItemImplTemp.getName())
//                                || realItemImplTemp.getId()
//                                        .equals(realItemImplTemp.getName())) {
//                            realItemImplTemp.setName(authItem.getName());
//                        }
//                        if (StringUtils
//                                .isEmpty(realItemImplTemp.getParentId())) {
//                            realItemImplTemp
//                                    .setParentId(authItem.getParentId());
//                        }
//                    }
//                    realItemTemp.getData().putAll(authItem.getData());
//                }
//            }
//        }
//        
//        //        AssertUtils.notNull(ehcache, "ehcache is null.");
//        //        final AuthItemPersister finalauthItemPersister = this.authItemPersister;
//        //        resMap = new TransactionAwareLazyEhCacheMap<AuthItem>(
//        //                tempAuthItemMapping, new EhCacheCache(ehcache),
//        //                new LazyCacheValueFactory<String, AuthItem>() {
//        //                    
//        //                    @Override
//        //                    public AuthItem find(String authItemId) {
//        //                        AuthItem authItemTemp = finalauthItemPersister.findAuthItem(authItemId);
//        //                        if (authItemTemp != null) {
//        //                            return authItemTemp;
//        //                        } else {
//        //                            return null;
//        //                        }
//        //                    }
//        //                    
//        //                    @Override
//        //                    public Map<String, AuthItem> listMap() {
//        //                        Map<String, AuthItem> resMap = new HashMap<String, AuthItem>();
//        //                        Set<AuthItem> authItemSetTemp = finalauthItemPersister.listAuthItem();
//        //                        if (!CollectionUtils.isEmpty(authItemSetTemp)) {
//        //                            for (AuthItem authItemTemp : authItemSetTemp) {
//        //                                resMap.put(authItemTemp.getId(), authItemTemp);
//        //                            }
//        //                        }
//        //                        return resMap;
//        //                    }
//        //                    
//        //                }, false);
//        //强制将lazy中list加载一次
//        resMap.entrySet();
//        
//        return resMap;
//    }
//    
//    /**
//      * 加载权限项加载器<br/>
//      *<功能详细描述>
//      * @param authLoaders [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private void loadAuthLoader(Collection<AuthLoader> authLoaders) {
//        this.authLoaderList = new ArrayList<AuthLoader>();
//        
//        if (CollectionUtils.isEmpty(authLoaders)) {
//            return;
//        }
//        for (AuthLoader authLoaderTemp : authLoaders) {
//            this.authLoaderList.add(authLoaderTemp);
//        }
//        Collections.sort(this.authLoaderList, OrderComparator.INSTANCE);
//    }
//    
//    /** 
//     * 执行脚本自动升级
//     *<功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private void databaseSchemaUpdate() {
//        if (databaseSchemaUpdate && dbScriptExecutorContext != null) {
//            Map<String, String> replaceDataMap = new HashMap<String, String>();
//            replaceDataMap.put("tableSuffix", this.tableSuffix);
//            TableDefinition authItemTableDefinition = new XMLTableDefinition(
//                    authItemTableDefinitionLocation, replaceDataMap);
//            TableDefinition authRefTableDefinition = new XMLTableDefinition(
//                    authRefDefinitionLocation, replaceDataMap);
//            TableDefinition authRefHisTableDefinition = new XMLTableDefinition(
//                    authRefHisDefinitionLocation, replaceDataMap);
//            
//            this.dbScriptExecutorContext
//                    .createOrUpdateTable(authItemTableDefinition);
//            this.dbScriptExecutorContext
//                    .createOrUpdateTable(authRefTableDefinition);
//            this.dbScriptExecutorContext
//                    .createOrUpdateTable(authRefHisTableDefinition);
//            
//            logger.info(" 自动初始化权限容器表结构完成.表后缀名为：{}...", this.tableSuffix);
//        }
//    }
//    
//    /**
//     * 加载权限检查器<br/>
//     *  <功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private void loadAuthChecker(Collection<AuthChecker> authCheckers) {
//        //加载系统中存在的权限检查器
//        Map<String, AuthChecker> tempAuthCheckerMapping = new HashMap<String, AuthChecker>();
//        if (authCheckers == null || authCheckers.size() == 0) {
//            authCheckerMapping = tempAuthCheckerMapping;
//            return;
//        }
//        
//        for (AuthChecker authCheckerTemp : authCheckers) {
//            logger.info("加载权限检查器：权限类型:'{}':检查器类：'{}'",
//                    authCheckerTemp.getCheckAuthType(),
//                    authCheckerTemp.getClass().getName());
//            tempAuthCheckerMapping.put(authCheckerTemp.getCheckAuthType(),
//                    authCheckerTemp);
//        }
//        
//        authCheckerMapping = tempAuthCheckerMapping;
//    }
//    
//    /**
//     * 加载权限检查器<br/>
//     *  <功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private void loadAdminChecker(Collection<AdminChecker> adminCheckers) {
//        Map<String, AdminChecker> tempAuthCheckerMapping = new HashMap<String, AdminChecker>();
//        //加载系统中存在的权限检查器
//        if (adminCheckers == null || adminCheckers.size() == 0) {
//            adminCheckerMapping = tempAuthCheckerMapping;
//            return;
//        }
//        
//        for (AdminChecker authCheckerTemp : adminCheckers) {
//            logger.info("加载超级管理员认证器：超级管理员引用类型:'{}':认证器类：'{}'",
//                    authCheckerTemp.refType(),
//                    authCheckerTemp.getClass().getName());
//            tempAuthCheckerMapping.put(authCheckerTemp.refType(),
//                    authCheckerTemp);
//        }
//        adminCheckerMapping = tempAuthCheckerMapping;
//    }
//    
//}
