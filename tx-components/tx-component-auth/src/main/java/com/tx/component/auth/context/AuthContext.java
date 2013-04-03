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
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.component.auth.context.checker.DefaultAuthChecker;
import com.tx.component.auth.context.factory.DefaultAuthTypeFactory;
import com.tx.component.auth.exceptions.AuthContextInitException;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.component.auth.model.AuthTypeItem;
import com.tx.component.auth.service.AuthItemRefService;
import com.tx.component.auth.service.AuthItemService;
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
public class AuthContext implements FactoryBean<AuthContext>,
        ApplicationContextAware, InitializingBean {
    
    /** 日志记录器 */
    private static final Logger logger = LoggerFactory.getLogger(AuthContext.class);
    
    /** 业务日志记录器：默认使用logback日志记录器  */
    @SuppressWarnings("unused")
    private static Logger serviceLogger = LoggerFactory.getLogger(AuthContext.class);
    
    /** 懒汉模式工厂实例  */
    private static AuthContext context;
    
    /** 当前spring容器 */
    private ApplicationContext applicationContext;
    
    /** 默认的权限检查器 */
    private AuthChecker defaultAuthChecker;
    
    /** 权限类型工厂 */
    public AuthTypeFactory authTypeFactory;
    
    /**
     * 权限检查器映射，以权限
     * 权限类型检查器默认会添加几个检查器 用户自定义添加的权限检查器会覆盖该检查器
     */
    private Map<String, AuthChecker> authCheckerMapping;
    
    /**
     * 系统的权限项集合<br/>
     * key为权限项唯一键（key,id）<br/>
     * value为具体的权限项
     */
    private Map<String, AuthItem> authItemMapping;
    
    /** 权限项业务层 */
    @Resource(name = "authItemService")
    private AuthItemService authItemService;
    
    /** 权限引用项业务层 */
    @Resource(name = "authItemRefService")
    private AuthItemRefService authItemRefService;
    
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
     * @return
     * @throws Exception
     */
    @Override
    public AuthContext getObject() throws Exception {
        return this;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return AuthContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
    /**
      * 获取权限容器唯一容器
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return AuthContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static AuthContext getContext() {
        return AuthContext.context;
    }
    
    /**
     * InitializingBean接口的实现，用以在容器参数设置完成后加载相关权限
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("初始化权限容器start...");
        if (this.authTypeFactory == null) {
            this.authTypeFactory = DefaultAuthTypeFactory.newInstance();
        }
        //如果没有设置默认的权限检查器
        if (this.defaultAuthChecker == null) {
            this.defaultAuthChecker = new DefaultAuthChecker();
        }
        
        //读取系统中注册的加载器
        Collection<AuthLoader> authLoader = this.applicationContext.getBeansOfType(AuthLoader.class)
                .values();
        //读取系统中注册的权限检查器
        Collection<AuthChecker> authCheckers = this.applicationContext.getBeansOfType(AuthChecker.class)
                .values();
        
        //加载权限检查器
        logger.info("      加载权限检查器...");
        loadAuthChecker(authCheckers);
        //向容器中注册加载器
        logger.info("      加载权限项...");
        loadAuthItems(new ArrayList<AuthLoader>(authLoader));
        
        //使系统context指向实体本身
        context = this;
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
        if (authLoaders == null || authLoaders.size() == 0) {
            logger.warn("AuthContext init.AuthLoader is empty.");
            return;
        }
        
        //一句加载器order值进行排序，根据优先级进行加载
        Collections.sort(authLoaders, new OrderComparator());
        
        //权限项映射
        Map<String, AuthItem> tempAuthItemMapping = new HashMap<String, AuthItem>();
        
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
        if (authCheckers == null || authCheckers.size() == 0) {
            return;
        }
        
        Map<String, AuthChecker> tempAuthCheckerMapping = new HashMap<String, AuthChecker>();
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
    public Map<String, AuthItem> getAuthItemMapping() {
        return authItemMapping;
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
    public boolean isHasAuth(String authKey, Object... objects) {
        //检查对应权限的权限类型是否正确
        AuthItem authItem = getAuthItemMapping().get(authKey);
        if (authItem == null || !authItem.isValid()) {
            return false;
        }
        if (authCheckerMapping.get(authItem.getAuthType()) == null) {
            throw new AuthContextInitException(
                    "The authType:{} authChecker is not exists!",
                    authItem.getAuthType());
        }
        return authCheckerMapping.get(authItem.getAuthType())
                .isHasAuth(authItem, objects);
    }
    
    /**
      * 装载注册权限项<br/>
      *     动态注入容器<br/>
      *     该注入过程，调用注入的authRegister进行，
      *     如果找不到对应权限类型，则调用
      * <功能详细描述>
      * @param authItem
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String registeAuth(AuthItem authItem) {
        //构建注册实体
        AuthItemImpl newAuthItemImpl = new AuthItemImpl();
        newAuthItemImpl.setAuthType(authItem.getAuthType());
        newAuthItemImpl.setDescription(authItem.getDescription());
        newAuthItemImpl.setEditAble(authItem.isEditAble());
        newAuthItemImpl.setName(authItem.getName());
        newAuthItemImpl.setConfigAble(authItem.isConfigAble());
        newAuthItemImpl.setValid(authItem.isValid());
        newAuthItemImpl.setViewAble(authItem.isViewAble());
        
        return "11";
    }
    
    public String registeAuth(String authType, String name, String description,
            boolean isEditAble) {
        AuthItemImpl newAuthItemImpl = new AuthItemImpl();
        newAuthItemImpl.setAuthType(authType);
        newAuthItemImpl.setDescription(description);
        newAuthItemImpl.setEditAble(isEditAble);
        newAuthItemImpl.setName(name);
        
        newAuthItemImpl.setConfigAble(true);
        newAuthItemImpl.setValid(true);
        newAuthItemImpl.setViewAble(true);
        
        return "11";
    }
    
    /**
      * 向容器中注册权限项<br/>
      * <功能详细描述>
      * @param authItemImpl
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String doRegisteAuth(final AuthItemImpl authItemImpl){
        //参数合法性验证
        AssertUtils.notNull(authItemImpl, "authItemImpl is null");
        
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
        }
        else {
            //如果在非事务中执行
            authItemMapping.put(authItemImpl.getId(), authItemImpl);
        }
        
        return null;
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
    public boolean unRegisteAuth(AuthItem authItem) {
        
        return true;
    }
    
    /**
     * 注册新的权限项（如果已存在，则返回原已存在的权限类型项）
     * @param authType
     * @param name
     * @param description
     * @param isViewAble
     * @param isEditAble
     * @return [参数说明]
     * 
     * @return AuthTypeItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public AuthTypeItem registeNewOrGetAuthTypeItem(String authType,
            String name, String description, boolean isViewAble,
            boolean isEditAble) {
        return this.authTypeFactory.registeNewOrGetAuthTypeItem(authType,
                name,
                description,
                isViewAble,
                isEditAble);
    }
    
    /** 
      * 注册新的权限项（如果已存在，则返回原已存在的权限类型项）
      *<功能详细描述>
      * @param authType
      * @return [参数说明]
      * 
      * @return AuthTypeItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public AuthTypeItem registeNewOrGetAuthTypeItem(String authType) {
        return this.authTypeFactory.registeNewOrGetAuthTypeItem(authType);
    }
}
