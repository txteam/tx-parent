///*
// * 描          述:  <描述>
// * 修  改   人:  brady
// * 修改时间:  2012-11-30
// * <修改描述:>
// */
//package com.tx.component.auth.context;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.ListUtils;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.MultiValueMap;
//
//import com.tx.component.auth.context.authchecker.AuthChecker;
//import com.tx.component.auth.exception.AuthContextInitException;
//import com.tx.component.auth.model.Auth;
//import com.tx.component.auth.model.AuthItem;
//import com.tx.component.auth.model.AuthRef;
//import com.tx.component.auth.model.AuthRefItem;
//import com.tx.core.TxConstants;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.MessageUtils;
//
///**
// * 权限容器<br/>
// * 功能详细描述<br/>
// *     下一版本，将把权限注册的相关逻辑抽取接口AuthRegister从AuthContext中移出<br/>
// * 
// * @author PengQingyang
// * @version [版本号, 2012-12-1]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//public class AuthContext extends AuthContextBuilder {
//    /* 不需要进行注入部分属性 */
//    
//    /** 单子模式权限容器唯一实例 */
//    private static AuthContext authContext;
//    
//    /**
//     * <默认构造函数>
//     * 构造函数级别为子类可见<br/>
//     */
//    protected AuthContext() {
//    }
//    
//    /**
//      * 获取权限容器实例
//      *<功能详细描述>
//      * @return [参数说明]
//      * 
//      * @return AuthContext [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static AuthContext getContext() {
//        AssertUtils.notNull(AuthContext.authContext,
//                "context is null.please call it after init.");
//        return authContext;
//    }
//    
//    /**
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //使系统context指向实体本身
//        authContext = this;
//        
//        super.afterPropertiesSet();
//    }
//    
//    /**
//      * 获取当前权限项映射
//      * <功能详细描述>
//      * @return [参数说明]
//      * 
//      * @return Map<String,AuthItem> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public Map<String, Auth> getAllAuthItemMapping() {
//        Map<String, Auth> resMap = new HashMap<>(
//                TxConstants.INITIAL_MAP_SIZE);
//        
//        for (Entry<String, Auth> entryTemp : authItemMapping.entrySet()) {
//            resMap.put(entryTemp.getKey(), entryTemp.getValue());
//        }
//        return resMap;
//    }
//    
//    /**
//      * 根据权限类型获取权限列表<br/>
//      *<功能详细描述>
//      * @param authType
//      * @return [参数说明]
//      * 
//      * @return List<AuthItem> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @SuppressWarnings("unchecked")
//    public List<Auth> getAuthItemListByAuthType(String authType) {
//        AssertUtils.notEmpty(authType, "authType is empty.");
//        List<Auth> resList = new ArrayList<>(
//                TxConstants.INITIAL_CONLLECTION_SIZE);
//        
//        for (Auth authItemTemp : authItemMapping.values()) {
//            if (authType.equals(authItemTemp.getAuthType())) {
//                resList.add(authItemTemp);
//            }
//        }
//        return (List<Auth>) ListUtils.unmodifiableList(resList);
//    }
//    
//    public List<Auth> getAuthItemListByAuthTypeAndParentId(String authType,
//            String parentId) {
//        throw new RuntimeException("xx");
//    }
//    
//    /**
//      * 根据权限类型获取权限映射<br/>
//      * <功能详细描述>
//      * @param authType
//      * @return [参数说明]
//      * 
//      * @return Map<String,AuthItem> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public Map<String, Auth> getAuthItemMapByAuthType(String authType) {
//        AssertUtils.notEmpty(authType, "authType is empty.");
//        Map<String, Auth> resMap = new HashMap<>(
//                TxConstants.INITIAL_MAP_SIZE);
//        
//        for (Entry<String, Auth> entryTemp : authItemMapping.entrySet()) {
//            if (authType.equals(entryTemp.getValue().getAuthType())) {
//                resMap.put(entryTemp.getKey(), entryTemp.getValue());
//            }
//        }
//        return resMap;
//    }
//    
//    /**
//      * 根据权限类型及引用id映射查询权限引用集合<br/>
//      * <功能详细描述>
//      * @param refType2RefIdMapping [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public void login(MultiValueMap<String, String> refType2RefIdMapping) {
//        AssertUtils.notEmpty(refType2RefIdMapping,
//                "refType2RefIdMapping is empty");
//        
//        List<AuthRef> authItemRefList = null;
//        
//        boolean isSuperAdmin = false;
//        String adminRefType = null;
//        String adminRefId = null;
//        if (!MapUtils.isEmpty(adminCheckerMapping)) {
//            for (Entry<String, List<String>> refType2RefIdEntry : refType2RefIdMapping.entrySet()) {
//                if (adminCheckerMapping.get(refType2RefIdEntry.getKey()) != null
//                        && adminCheckerMapping.get(refType2RefIdEntry.getKey())
//                                .isSuperAdmin(refType2RefIdEntry.getValue()
//                                        .get(0))) {
//                    isSuperAdmin = true;
//                }
//            }
//        }
//        
//        if (isSuperAdmin) {
//            //如果是超级管理员则拥有所有权限项的引用
//            authItemRefList = new ArrayList<AuthRef>();
//            for (Auth authItemTemp : authItemMapping.values()) {
//                AuthRefItem newAuthItemRefTemp = new AuthRefItem();
//                newAuthItemRefTemp.setAuthItem(authItemTemp);
//                newAuthItemRefTemp.setAuthRefType(adminRefType);
//                newAuthItemRefTemp.setRefId(adminRefId);
//                //是否是临时权限
//                newAuthItemRefTemp.setTemp(false);
//                
//                //构建的超级管理员的权限引用
//                authItemRefList.add(newAuthItemRefTemp);
//            }
//        } else {
//            //如果不是超级管理员，根据引用表查询得到相关的权限引用
//            authItemRefList = new ArrayList<AuthRef>();
//            List<AuthRefItem> refImplList = this.authItemRefImplService.queryAuthItemRefListByRefType2RefIdMapping(refType2RefIdMapping,
//                    this.systemId,
//                    this.tableSuffix);
//            if (refImplList != null) {
//                for (AuthRefItem refImplTemp : refImplList) {
//                    loadAuthItemRef(refImplTemp);
//                }
//            }
//            authItemRefList.addAll(refImplList);
//        }
//        
//        //将权限引用写入容器
//        AuthSessionContext.putAuthRefToSession(authItemRefList);
//    }
//    
//    /**
//      * 加载权限引用项
//      * <功能详细描述>
//      * @param refImpl [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private void loadAuthItemRef(AuthRefItem refImpl) {
//        if (refImpl == null || refImpl.getAuthItem() == null) {
//            return;
//        }
//        
//        Auth realAuthItem = authItemMapping.get(refImpl.getAuthItem()
//                .getId());
//        if (realAuthItem != null) {
//            refImpl.setAuthItem(realAuthItem);
//        }
//    }
//    
//    /**
//      * 从权限容器中，根据权限项id,获取对应的权限项
//      * <功能详细描述>
//      * @param authItemId
//      * @return [参数说明]
//      * 
//      * @return AuthItem [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public Auth getAuthItemFromContextById(String authItemId) {
//        return authItemMapping.get(authItemId);
//    }
//    
//    /**
//     * 判断是否具有某权限<br/>
//     * authType:除定制的几类权限特性以外， 可以为 业务权限 产品权限
//     * 这里权限验证会根据当前会话以及对应的权限验证器判断是否具有对应权限
//     * 
//     * @param authKey
//     * @param authType
//     * @param objects
//     *            判断权限传入的参数，可以为业务ID，可以为等等。。。。 该参数会直接传入对应的authChecker中
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    public boolean hasAuth(String authKey) {
//        return hasAuth(authKey, (Object[]) null);
//    }
//    
//    /**
//     * 判断是否具有某权限<br/>
//     * authType:除定制的几类权限特性以外， 可以为 业务权限 产品权限
//     * 这里权限验证会根据当前会话以及对应的权限验证器判断是否具有对应权限
//     * 
//     * @param authKey
//     * @param authType
//     * @param objects
//     *            判断权限传入的参数，可以为业务ID，可以为等等。。。。 该参数会直接传入对应的authChecker中
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    public boolean hasAuth(String authKey, Object... objects) {
//        if (!authItemMapping.containsKey(authKey)) {
//            return true;
//        }
//        //检查对应权限的权限类型是否正确
//        Auth authItem = authItemMapping.get(authKey);
//        if (authItem == null) {
//            throw new AuthContextInitException(
//                    MessageUtils.format("The authKey:{} AuthItem is not exists.",
//                            new Object[] { authKey }));
//        }
//        
//        //如果对应权限项已经无效，则认为拥有权限
//        if (!authItem.isValid()) {
//            return true;
//        }
//        
//        //如果当前权限引用依赖有效时间，则判断该权限引用是否还有效
//        List<AuthRef> authItemRefList = AuthSessionContext.getAuthRefListFromSession(authItem.getId());
//        if (!CollectionUtils.isEmpty(authItemRefList)) {
//            //根据权限类型获取对应的权限检查器映射
//            AuthChecker authChecker = null;
//            if (!authCheckerMapping.containsKey(authItem.getAuthType())) {
//                authChecker = this.defaultAuthChecker;
//            }
//            return authChecker.isHasAuth(authItemRefList, objects);
//        } else {
//            return false;
//        }
//    }
//    
//    /**
//      * 装载注册权限项<br/>
//      *     如果对应权限项已经存在，则进行更新<br/>
//      *     如果对应权限项不存在，则进行增加<br/>
//      * <功能详细描述>
//      * @param authItem
//      * @return [参数说明]
//      * 
//      * @return String [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @Transactional
//    public Auth registerAuth(Auth authItem) {
//        AssertUtils.notNull(authItem, "authItem is null");
//        //参数合法性验证
//        //参数合法性验证
//        boolean isNeedNew = false;
//        if (StringUtils.isEmpty(authItem.getId())
//                || !authItemMapping.containsKey(authItem.getId())) {
//            isNeedNew = true;
//        }
//        
//        AuthItem res = null;
//        if (isNeedNew) {
//            AssertUtils.notNull(authItem, "authItemImpl is null");
//            AssertUtils.notEmpty(authItem.getAuthType(), "authType is empty.");
//            
//            //构建注册实体
//            AuthItem newAuthItemImpl = new AuthItem();
//            newAuthItemImpl.setId(authItem.getId());
//            newAuthItemImpl.setParentId(authItem.getParentId());
//            newAuthItemImpl.setAuthType(authItem.getAuthType());
//            newAuthItemImpl.setDescription(authItem.getDescription());
//            newAuthItemImpl.setEditAble(authItem.isEditAble());
//            newAuthItemImpl.setName(authItem.getName());
//            newAuthItemImpl.setConfigAble(authItem.isConfigAble());
//            newAuthItemImpl.setValid(authItem.isValid());
//            newAuthItemImpl.setViewAble(authItem.isViewAble());
//            newAuthItemImpl.setRefId(authItem.getRefId());
//            newAuthItemImpl.setRefType(authItem.getRefType());
//            
//            //持久化权限项
//            res = doRegisteNewAuth(newAuthItemImpl);
//        } else {
//            Map<String, Object> authItemRowMap = new HashMap<String, Object>();
//            authItemRowMap.put("id", authItem.getId());
//            
//            authItemRowMap.put("parentId", authItem.getParentId());
//            authItemRowMap.put("description", authItem.getDescription());
//            authItemRowMap.put("editAble", authItem.isEditAble());
//            authItemRowMap.put("name", authItem.getName());
//            authItemRowMap.put("configAble", authItem.isConfigAble());
//            authItemRowMap.put("valid", authItem.isValid());
//            authItemRowMap.put("viewAble", authItem.isViewAble());
//            authItemRowMap.put("refId", authItem.getRefId());
//            authItemRowMap.put("refType", authItem.getRefType());
//            
//            res = doRegisteSaveAuth(authItemRowMap);
//        }
//        return res;
//    }
//    
//    /**
//      * 装载注册权限项<br/>
//      *     动态注入容器<br/>
//      *     该注入过程，调用注入的authRegister进行，
//      *     如果找不到对应权限类型，则调用
//      *     如果对应权限项已经存在，则根据传入的信息进行更新，如果不存在则插入<br/>
//      *<功能详细描述>
//      * @param authType
//      * @param name
//      * @param description
//      * @param isEditAble
//      * @return [参数说明]
//      * 
//      * @return AuthItem [返回类型说明]
//      * @exception throws [异常类型] [rr异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @Transactional
//    public Auth registerAuth(String id, String parentId, String name,
//            String description, String authType, boolean valid,
//            boolean configAble, boolean viewAble, boolean editAble) {
//        //参数合法性验证
//        boolean isNeedNew = false;
//        if (StringUtils.isEmpty(id) || !authItemMapping.containsKey(id)) {
//            isNeedNew = true;
//        }
//        
//        AuthItem res = null;
//        if (isNeedNew) {
//            AuthItem newAuthItemImpl = new AuthItem();
//            newAuthItemImpl.setId(id);
//            newAuthItemImpl.setParentId(parentId);
//            newAuthItemImpl.setName(name);
//            newAuthItemImpl.setAuthType(authType);
//            newAuthItemImpl.setDescription(description);
//            newAuthItemImpl.setEditAble(editAble);
//            newAuthItemImpl.setConfigAble(configAble);
//            newAuthItemImpl.setValid(valid);
//            newAuthItemImpl.setViewAble(viewAble);
//            
//            //持久化权限项
//            res = doRegisteNewAuth(newAuthItemImpl);
//        } else {
//            Map<String, Object> authItemRowMap = new HashMap<String, Object>();
//            authItemRowMap.put("id", id);
//            
//            authItemRowMap.put("parentId", parentId);
//            authItemRowMap.put("description", description);
//            authItemRowMap.put("editAble", editAble);
//            authItemRowMap.put("name", name);
//            authItemRowMap.put("configAble", configAble);
//            authItemRowMap.put("valid", valid);
//            authItemRowMap.put("viewAble", viewAble);
//            
//            res = doRegisteSaveAuth(authItemRowMap);
//        }
//        
//        return res;
//    }
//    
//    /**
//      * 装载注册权限项<br/>
//      *     动态注入容器<br/>
//      *     该注入过程，调用注入的authRegister进行，
//      *     如果找不到对应权限类型，则调用
//      *     如果对应权限项已经存在，则根据传入的信息进行更新，如果不存在则插入<br/>
//      *<功能详细描述>
//      * @param id
//      * @param name
//      * @param authType
//      * @param isValid
//      * @return [参数说明]
//      * 
//      * @return AuthItem [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @Transactional
//    public Auth registerAuth(String id, String name, String description,
//            String authType, boolean valid) {
//        //参数合法性验证
//        boolean isNeedNew = false;
//        if (StringUtils.isEmpty(id) || !authItemMapping.containsKey(id)) {
//            isNeedNew = true;
//        }
//        
//        AuthItem res = null;
//        if (isNeedNew) {
//            AuthItem newAuthItemImpl = new AuthItem();
//            newAuthItemImpl.setId(id);
//            newAuthItemImpl.setName(name);
//            newAuthItemImpl.setAuthType(authType);
//            newAuthItemImpl.setValid(valid);
//            
//            newAuthItemImpl.setEditAble(true);
//            newAuthItemImpl.setConfigAble(true);
//            newAuthItemImpl.setViewAble(true);
//            
//            //持久化权限项
//            res = doRegisteNewAuth(newAuthItemImpl);
//        } else {
//            Map<String, Object> authItemRowMap = new HashMap<String, Object>();
//            authItemRowMap.put("id", id);
//            
//            authItemRowMap.put("name", name);
//            authItemRowMap.put("description", description);
//            authItemRowMap.put("valid", valid);
//            
//            res = doRegisteSaveAuth(authItemRowMap);
//        }
//        return res;
//    }
//    
//    /**
//      * 装载注册权限项<br/>
//      *     动态注入容器<br/>
//      *     该注入过程，调用注入的authRegister进行
//      *<功能详细描述>
//      * @param name
//      * @param description
//      * @param authType
//      * @return [参数说明]
//      * 
//      * @return AuthItem [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @Transactional
//    public Auth registerAuth(String name, String description,
//            String authType) {
//        //参数合法性验证
//        AssertUtils.notEmpty(authType, "authType is empty.");
//        
//        AuthItem newAuthItemImpl = new AuthItem();
//        newAuthItemImpl.setAuthType(authType);
//        newAuthItemImpl.setDescription(description);
//        
//        newAuthItemImpl.setName(name);
//        
//        newAuthItemImpl.setConfigAble(true);
//        newAuthItemImpl.setEditAble(true);
//        newAuthItemImpl.setValid(true);
//        newAuthItemImpl.setViewAble(true);
//        
//        //持久化权限项
//        return doRegisteNewAuth(newAuthItemImpl);
//    }
//    
//    /**
//     * 装载注册权限项<br/>
//     *     动态注入容器<br/>
//     *     该注入过程，调用注入的authRegister进行
//     *<功能详细描述>
//     * @param name
//     * @param description
//     * @param authType
//     * @return [参数说明]
//     * 
//     * @return AuthItem [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @Transactional
//    public Auth registerAuth(String name, String description,
//            String authType, boolean isValid, boolean isConfigAble,
//            boolean isEditAble, boolean isViewAble) {
//        //参数合法性验证
//        AssertUtils.notEmpty(authType, "authType is empty.");
//        
//        AuthItem newAuthItemImpl = new AuthItem();
//        newAuthItemImpl.setAuthType(authType);
//        newAuthItemImpl.setDescription(description);
//        newAuthItemImpl.setName(name);
//        
//        newAuthItemImpl.setConfigAble(isViewAble);
//        newAuthItemImpl.setEditAble(isEditAble);
//        newAuthItemImpl.setValid(isValid);
//        newAuthItemImpl.setViewAble(isConfigAble);
//        
//        //持久化权限项
//        return doRegisteNewAuth(newAuthItemImpl);
//    }
//    
//    /**
//      * 向容器中注册新权限项<br/>
//      * <功能详细描述>
//      * @param authItemImpl
//      * @return [参数说明]
//      * 
//      * @return String [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private AuthItem doRegisteNewAuth(final AuthItem authItemImpl) {
//        AssertUtils.notNull(authItemImpl, "authItemImpl is null");
//        AssertUtils.notEmpty(authItemImpl.getAuthType(), "authType is empty.");
//        
//        //获取权限类型项（如果权限类型项不存在，则注册）
//        //向权限容器中注册权类型
//        AuthTypeItemContext.getContext()
//                .registeAuthTypeItem(authItemImpl.getAuthType());
//        
//        //持久化对应的权限项到数据库中
//        this.authItemImplService.insertAuthItemImpl(authItemImpl,
//                this.systemId,
//                this.tableSuffix);
//        
//        authItemMapping.put(authItemImpl.getId(), authItemImpl);
//        
//        return authItemImpl;
//    }
//    
//    /**
//      * 更新容器中的权限项
//      * <功能详细描述>
//      * @param authItemRowMap
//      * @return [参数说明]
//      * 
//      * @return String [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private AuthItem doRegisteSaveAuth(
//            final Map<String, Object> authItemRowMap) {
//        AssertUtils.notNull(authItemRowMap, "authItemRowMap is null");
//        AssertUtils.notEmpty((String) authItemRowMap.get("id"),
//                "authItemRowMap.id is empty.");
//        
//        final AuthItem authItemImpl = this.authItemImplService.saveAuthItemImplByAuthItemRowMap(authItemRowMap,
//                this.systemId,
//                this.tableSuffix);
//        
//        authItemMapping.put(authItemImpl.getId(), authItemImpl);
//        
//        return authItemImpl;
//    }
//    
//    /**
//     * 增加某一权限 的：对应权限引用类型的新的权限引用id集合
//     *     系统会自动过滤掉原已经存在的权限引用，仅对新增的权限引用进行增加
//     * <功能详细描述>
//     * @param authRefType
//     * @param authItemId
//     * @param refIdList [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public void addAuthItemOfAuthRefIdList(String authRefType,
//            String authItemId, List<String> addRefIdList) {
//        this.notTempAuthItemRefImplService.addAuthItemOfAuthRefList(authRefType,
//                authItemId,
//                addRefIdList,
//                this.systemId,
//                this.tableSuffix);
//    }
//    
//    /**
//     * 增加某一权限 的：对应权限引用类型的新的权限引用id集合
//     *     系统会自动过滤掉原已经存在的权限引用，仅对新增的权限引用进行增加
//     * <功能详细描述>
//     * @param authRefType
//     * @param authItemId
//     * @param refIdList [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public void deleteAuthItemOfAuthRefIdList(String authRefType,
//            String authItemId, List<String> deleteRefIdList) {
//        this.notTempAuthItemRefImplService.deleteAuthItemOfAuthRefList(authRefType,
//                authItemId,
//                deleteRefIdList,
//                this.systemId,
//                this.tableSuffix);
//    }
//    
//    /**
//     * 增加某一权限 的：对应权限引用类型的新的权限引用id集合
//     *     系统会自动过滤掉原已经存在的权限引用，仅对新增的权限引用进行增加
//     * <功能详细描述>
//     * @param authRefType
//     * @param authItemId
//     * @param refIdList [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public void saveAuthItemOfAuthRefIdList(String authRefType,
//            String authItemId, List<String> addRefIdList,
//            List<String> deleteRefIdList) {
//        this.notTempAuthItemRefImplService.saveAuthItemOfAuthRefList(authRefType,
//                authItemId,
//                addRefIdList,
//                deleteRefIdList,
//                this.systemId,
//                this.tableSuffix);
//    }
//    
//    /**
//      * 保存某一权限 的：对应权限引用类型的新的权限引用id集合
//      * <功能详细描述>
//      * @param authRefType
//      * @param authItemId
//      * @param refIdList [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public void saveAuthItemOfAuthRefIdList(String authRefType,
//            String authItemId, List<String> refIdList) {
//        this.notTempAuthItemRefImplService.saveAuthItemOfAuthRefList(authRefType,
//                authItemId,
//                refIdList,
//                this.systemId,
//                this.tableSuffix);
//    }
//    
//    //    /**
//    //     * 保存某一权限 的：对应权限引用类型的新的权限引用id集合
//    //     * <功能详细描述>
//    //     * @param authRefType
//    //     * @param authItemId
//    //     * @param refIdList [参数说明]
//    //     * 
//    //     * @return void [返回类型说明]
//    //     * @exception throws [异常类型] [异常说明]
//    //     * @see [类、类#方法、类#成员]
//    //    */
//    //    public void saveAuthItemOfAuthRefIdList(String authType,
//    //            String authRefType, String authItemId, List<String> refIdList) {
//    //        this.authItemRefService.saveAuthItemOfAuthRefList(authType,
//    //                authRefType,
//    //                authItemId,
//    //                refIdList,
//    //                this.systemId,
//    //                this.tableSuffix);
//    //    }
//    
//    /**
//      * 保存指定引用类型引用id的：引用到的权限id的集合
//      * <功能详细描述>
//      * @param authRefType
//      * @param refId
//      * @param authItemIdList [参数说明]
//      * 
//      * @return void [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public void saveAuthRefOfAuthItemIdList(String authRefType, String refId,
//            List<String> authItemIdList) {
//        this.notTempAuthItemRefImplService.saveAuthRefOfAuthItemList(authRefType,
//                refId,
//                authItemIdList,
//                this.systemId,
//                this.tableSuffix);
//    }
//    
//    /**
//     * 保存指定引用类型引用id的：引用到的权限id的集合
//     * <功能详细描述>
//     * @param authRefType
//     * @param refId
//     * @param authItemIdList [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public void saveAuthRefOfAuthItemIdList(String authType,
//            String authRefType, String refId, List<String> authItemIdList) {
//        this.notTempAuthItemRefImplService.saveAuthRefOfAuthItemList(authType,
//                authRefType,
//                refId,
//                authItemIdList,
//                this.systemId,
//                this.tableSuffix);
//    }
//    
//    /**
//      * 根据引用id以及权限引用类型查询权限引用集合<br/>
//      *<功能详细描述>
//      * @param authRefType
//      * @param refId
//      * @return [参数说明]
//      * 
//      * @return List<AuthItemRef> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public List<AuthRef> queryAuthItemRefListByAuthRefTypeAndRefId(
//            String authRefType, String refId) {
//        List<AuthRefItem> authItemRefImplList = this.notTempAuthItemRefImplService.queryAuthItemRefListByRefTypeAndRefId(authRefType,
//                refId,
//                this.systemId,
//                this.tableSuffix);
//        
//        List<AuthRef> resList = changeAuthItemRefImplListToAuthItemRefList(authItemRefImplList);
//        return resList;
//    }
//    
//    /**
//      * 根据引用类型以及权限项id获取，对应的引用类型中有哪些引用实体id引用了该权限<br/>
//      *<功能详细描述>
//      * @param authRefType
//      * @param authItemId
//      * @return [参数说明]
//      * 
//      * @return List<AuthItemRef> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public List<AuthRef> queryAuthItemRefListByAuthRefTypeAndAuthItemId(
//            String authRefType, String authItemId) {
//        List<AuthRefItem> authItemRefImplList = this.notTempAuthItemRefImplService.queryAuthItemRefListByRefTypeAndAuthItemId(authRefType,
//                authItemId,
//                this.systemId,
//                this.tableSuffix);
//        
//        List<AuthRef> resList = changeAuthItemRefImplListToAuthItemRefList(authItemRefImplList);
//        return resList;
//    }
//    
//    /**
//      * 根据具体的权限引用类型以及引用id查询权限引用集合<br/>
//      *     系统登录需要调用该方法，以获知当前人员拥有的权限引用集合<br/>
//      *<功能详细描述>
//      * @param refType2RefIdMapping
//      * @return [参数说明]
//      * 
//      * @return List<AuthItemRef> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public List<AuthRef> queryAuthItemRefListByRefType2RefIdMapping(
//            MultiValueMap<String, String> refType2RefIdMapping) {
//        List<AuthRefItem> authItemRefImplList = this.authItemRefImplService.queryAuthItemRefListByRefType2RefIdMapping(refType2RefIdMapping,
//                this.systemId,
//                this.tableSuffix);
//        
//        List<AuthRef> resList = changeAuthItemRefImplListToAuthItemRefList(authItemRefImplList);
//        return resList;
//    }
//    
//    /**
//      * 将authItemRefImpl列表转换为authItemRef列表<br/>
//      * 并在转换过程中将实际的authItem实例设置进去<br/>
//      * <功能详细描述>
//      * @param authItemRefImplList
//      * @return [参数说明]
//      * 
//      * @return List<AuthItemRef> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    private List<AuthRef> changeAuthItemRefImplListToAuthItemRefList(
//            List<AuthRefItem> authItemRefImplList) {
//        List<AuthRef> resList = new ArrayList<AuthRef>();
//        if (CollectionUtils.isEmpty(authItemRefImplList)) {
//            return resList;
//        }
//        
//        for (AuthRefItem authItemRefTemp : authItemRefImplList) {
//            if (authItemMapping.containsKey(authItemRefTemp.getAuthItem()
//                    .getId())) {
//                authItemRefTemp.setAuthItem(authItemMapping.get(authItemRefTemp.getAuthItem()
//                        .getId()));
//                resList.add(authItemRefTemp);
//            }
//        }
//        return resList;
//    }
//    
//    /**
//      * 判断是否拥有某权限<br/>
//      * <功能详细描述>
//      * @param authKey
//      * @param objects
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static boolean isHasAuth(String authKey, Object... objects) {
//        AssertUtils.notNull(authContext, "AuthContext init fail.");
//        return authContext.hasAuth(authKey, objects);
//    }
//    
//}
