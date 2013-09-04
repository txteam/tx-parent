/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.auth.context.AuthContext;
import com.tx.component.auth.context.AuthSessionContext;
import com.tx.component.auth.dao.AuthItemRefImplDao;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.component.auth.model.AuthItemRefImpl;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * AuthItemRefImpl的业务层<br/>
 *
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//TODO: 业务日志功能添加后，权限变更需要加入权限变更业务日志
@Component("authItemRefImplService")
public class AuthItemRefImplService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(AuthItemRefImplService.class);
    
    @Resource(name = "authItemRefImplDao")
    private AuthItemRefImplDao authItemRefImplDao;
    
    /**
      * 根据具体的权限引用类型以及引用id查询权限引用集合<br/>
      *     系统登录需要调用该方法，以获知当前人员拥有的权限引用集合<br/>
      * 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRefImpl> queryAuthItemRefListByRefType2RefIdMapping(
            Map<String, String> refType2RefIdMapping, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(refType2RefIdMapping,
                "refType2RefIdMapping is empty.");
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refType2RefIdMapping", refType2RefIdMapping);
        params.put("systemId", systemId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
                tableSuffix);
        
        return authItemRefImplList;
    }
    
    /**
     * 根据引用id以及权限引用类型查询权限引用集合<br/>
     * <功能详细描述>
     * 
     * @param operatorId
     * @param authRefType
     * @return [参数说明]
     * 
     * @return List<AuthItemRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRefImpl> queryAuthItemRefListByRefTypeAndRefId(
            String authRefType, String refId, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(refId, "refId is empty.");
        AssertUtils.notEmpty(authRefType, "authRefType is empty.");
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refId", refId);
        params.put("authRefType", authRefType);
        params.put("systemId", systemId);
        
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
                tableSuffix);
        
        return authItemRefImplList;
    }
    
    /**
      * 根据权限类型，权限引用类型，以及引用id查询权限引用列表<br/>
      * <功能详细描述>
      * @param authType
      * @param authRefType
      * @param refId
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRefImpl> queryAuthItemRefListByAuthTypeAndRefTypeAndRefId(
            String authType, String authRefType, String refId, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(refId, "refId is empty.");
        AssertUtils.notEmpty(authRefType, "authRefType is empty.");
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("authType", authType);
        params.put("refId", refId);
        params.put("authRefType", authRefType);
        params.put("systemId", systemId);
        
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
                tableSuffix);
        
        return authItemRefImplList;
    }
    
    /**
      * 根据引用类型以及权限项id获取，对应的引用类型中有哪些引用实体id引用了该权限<br/>
      * <功能详细描述>
      * @param authRefType
      * @param refId
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRefImpl> queryAuthItemRefListByRefTypeAndAuthItemId(
            String authRefType, String authItemId, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty.");
        AssertUtils.notEmpty(authItemId, "refId is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("authRefType", authRefType);
        params.put("authItemId", authItemId);
        params.put("systemId", systemId);
        
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
                tableSuffix);
        
        return authItemRefImplList;
    }
    
    /**
      * 根据权限类型，权限引用类型，以及权限id差选权限引用集合
      * <功能详细描述>
      * @param authType
      * @param authRefType
      * @param authItemId
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRefImpl> queryAuthItemRefListByAuthTypeAndRefTypeAndAuthItemId(
            String authType, String authRefType, String authItemId,
            String systemId, String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty.");
        AssertUtils.notEmpty(authItemId, "refId is empty.");
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("authType", authType);
        params.put("authRefType", authRefType);
        params.put("authItemId", authItemId);
        params.put("systemId", systemId);
        
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
                tableSuffix);
        
        return authItemRefImplList;
    }
    
    /**
      * 保存权限项目引用
      *     需要传入，引用类型，权限项ID，引用id集合
      * <功能详细描述>
      * @param authRefType
      * @param authId
      * @param refIdList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void saveAuthItemOfAuthRefList(String authRefType,
            String authItemId, List<String> refIdList, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(authItemId, "authItemId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        if (refIdList == null) {
            refIdList = new ArrayList<String>();
        }
        
        //这里应该先判断一下当前人员是否拥有对应的权限
        //TODO:
        List<String> srcAuthRefIds = new ArrayList<String>();
        List<AuthItemRefImpl> authItemRefImplList = queryAuthItemRefListByRefTypeAndAuthItemId(authRefType,
                authItemId,
                systemId,
                tableSuffix);
        if (authItemRefImplList != null) {
            for (AuthItemRefImpl refTemp : authItemRefImplList) {
                srcAuthRefIds.add(refTemp.getRefId());
            }
        }
        
        @SuppressWarnings("unchecked")
        List<String> needDeleteRefIds = ListUtils.subtract(srcAuthRefIds,
                refIdList);
        @SuppressWarnings("unchecked")
        List<String> needInsertRefIds = ListUtils.subtract(refIdList,
                srcAuthRefIds);
        
        batchDeleteAuthItemRefByRefIds(authRefType,
                authItemId,
                needDeleteRefIds,
                systemId,
                tableSuffix);
        batchInsertAuthItemRefByRefIds(authRefType,
                authItemId,
                needInsertRefIds,
                systemId,
                tableSuffix);
    }
    
    /**
      * 保存权限项目
      *     需要传入，权限类型，引用类型，权限项ID，引用id集合
      * @param authType
      * @param authRefType
      * @param authItemId
      * @param refIdList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void saveAuthItemOfAuthRefList(String authType, String authRefType,
            String authItemId, List<String> refIdList, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(authItemId, "authItemId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        if (refIdList == null) {
            refIdList = new ArrayList<String>();
        }
        
        //这里应该先判断一下当前人员是否拥有对应的权限
        //TODO:
        List<String> srcAuthRefIds = new ArrayList<String>();
        List<AuthItemRefImpl> authItemRefImplList = queryAuthItemRefListByRefTypeAndAuthItemId(authRefType,
                authItemId,
                systemId,
                tableSuffix);
        if (authItemRefImplList != null) {
            for (AuthItemRefImpl refTemp : authItemRefImplList) {
                srcAuthRefIds.add(refTemp.getRefId());
            }
        }
        
        @SuppressWarnings("unchecked")
        List<String> needDeleteRefIds = ListUtils.subtract(srcAuthRefIds,
                refIdList);
        @SuppressWarnings("unchecked")
        List<String> needInsertRefIds = ListUtils.subtract(refIdList,
                srcAuthRefIds);
        
        batchDeleteAuthItemRefByRefIds(authType,
                authRefType,
                authItemId,
                needDeleteRefIds,
                systemId,
                tableSuffix);
        batchInsertAuthItemRefByRefIds(authRefType,
                authItemId,
                needInsertRefIds,
                systemId,
                tableSuffix);
    }
    
    /**
      * 根据存入的权限项目id集合，以及权限引用类型，引用id<br/>
      *     更新对应应用的权限集<br/>
      * <功能详细描述>
      * @param authRefType
      * @param refId
      * @param authItemIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void saveAuthRefOfAuthItemList(String authRefType, String refId,
            List<String> authItemIds, String systemId, String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(refId, "refId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        if (authItemIds == null) {
            authItemIds = new ArrayList<String>();
        }
        
        //为了安全应该先过滤掉当前人员不存在，或仅仅是临时权限的权限项
        //TODO:XXX
        
        //存储前,获取原有的权限引用
        List<String> srcAuthItemIds = new ArrayList<String>();
        List<AuthItemRefImpl> authItemRefImplList = queryAuthItemRefListByRefTypeAndRefId(authRefType,
                refId,
                systemId,
                tableSuffix);
        if (authItemRefImplList != null) {
            for (AuthItemRefImpl refTemp : authItemRefImplList) {
                srcAuthItemIds.add(refTemp.getAuthItem().getId());
            }
        }
        
        //求差集，得到需要删除，以及需要增加的权限id集合
        @SuppressWarnings("unchecked")
        List<String> needDeleteAuthItemIds = ListUtils.subtract(srcAuthItemIds,
                authItemIds);
        @SuppressWarnings("unchecked")
        List<String> needInsertAuthItemIds = ListUtils.subtract(authItemIds,
                srcAuthItemIds);
        
        batchDeleteAuthItemRefByAuthItemIds(authRefType,
                refId,
                needDeleteAuthItemIds,
                systemId,
                tableSuffix);
        batchInsertAuthItemRefByAuthItemIds(authRefType,
                refId,
                needInsertAuthItemIds,
                systemId,
                tableSuffix);
    }
    
    /**
      * 根据存入的权限项目id集合，以及权限引用类型，引用id，权限类型<br/>
      *     更新对应应用的权限集<br/>
      * <功能详细描述>
      * @param authType
      * @param authRefType
      * @param refId
      * @param authItemIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void saveAuthRefOfAuthItemList(String authType, String authRefType,
            String refId, List<String> authItemIds, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(refId, "refId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        if (authItemIds == null) {
            authItemIds = new ArrayList<String>();
        }
        
        //为了安全应该先过滤掉当前人员不存在，或仅仅是临时权限的权限项
        //TODO:XXX
        
        //存储前,获取原有的权限引用
        List<String> srcAuthItemIds = new ArrayList<String>();
        List<AuthItemRefImpl> authItemRefImplList = queryAuthItemRefListByRefTypeAndRefId(authRefType,
                refId,
                systemId,
                tableSuffix);
        if (authItemRefImplList != null) {
            for (AuthItemRefImpl refTemp : authItemRefImplList) {
                srcAuthItemIds.add(refTemp.getAuthItem().getId());
            }
        }
        
        //求差集，得到需要删除，以及需要增加的权限id集合
        @SuppressWarnings("unchecked")
        List<String> needDeleteAuthItemIds = ListUtils.subtract(srcAuthItemIds,
                authItemIds);
        @SuppressWarnings("unchecked")
        List<String> needInsertAuthItemIds = ListUtils.subtract(authItemIds,
                srcAuthItemIds);
        
        batchDeleteAuthItemRefByAuthItemIds(authType,
                authRefType,
                refId,
                needDeleteAuthItemIds,
                systemId,
                tableSuffix);
        batchInsertAuthItemRefByAuthItemIds(authRefType,
                refId,
                needInsertAuthItemIds,
                systemId,
                tableSuffix);
    }
    
    /**
     * 删除权限引用项<br/>
     * <功能详细描述>
     * 
     * @param operatorId 当前操作员
     * @param refId
     * @param newAuthIds
     * @param authRefType
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void batchDeleteAuthItemRefByAuthItemIds(String authRefType,
            String refId, List<String> needDeleteAuthItemIds, String systemId,
            String tableSuffix) {
        if (CollectionUtils.isEmpty(needDeleteAuthItemIds)) {
            return;
        }
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        
        //如果存在需要删除的权限引用项
        List<AuthItemRefImpl> authItemRefList = new ArrayList<AuthItemRefImpl>();
        for (String authItemIdTemp : needDeleteAuthItemIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refId);
            
            authItemRef.setAuthItem(new AuthItemImpl(authItemIdTemp, systemId));
            authItemRefList.add(authItemRef);
        }
        this.authItemRefImplDao.batchDeleteAuthItemRefImpl(authItemRefList,
                tableSuffix);
        
        //TODO:记录相关业务日志
        //        serviceLogger.info(" {}于 {} 删除类型为{}的日志引用{}.", new String[] { userId,
        //                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
        //                authRefType, ArrayUtils.toString(newAuthIds) });
    }
    
    /**
      * 删除权限引用项<br/>
      *<功能详细描述>
      * @param authType
      * @param authRefType
      * @param refId
      * @param needDeleteAuthItemIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void batchDeleteAuthItemRefByAuthItemIds(String authType,
            String authRefType, String refId,
            List<String> needDeleteAuthItemIds, String systemId,
            String tableSuffix) {
        if (CollectionUtils.isEmpty(needDeleteAuthItemIds)) {
            return;
        }
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        //如果存在需要删除的权限引用项
        List<AuthItemRefImpl> authItemRefList = new ArrayList<AuthItemRefImpl>();
        for (String authItemIdTemp : needDeleteAuthItemIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refId);
            
            authItemRef.setAuthItem(new AuthItemImpl(authItemIdTemp, systemId,
                    authType));
            authItemRefList.add(authItemRef);
        }
        this.authItemRefImplDao.batchDeleteAuthItemRefImpl(authItemRefList,
                tableSuffix);
        
        //TODO:记录相关业务日志
        //        serviceLogger.info(" {}于 {} 删除类型为{}的日志引用{}.", new String[] { userId,
        //                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
        //                authRefType, ArrayUtils.toString(newAuthIds) });
    }
    
    /**
      * 批量删除权限项的多个权限项目引用 
      * <功能详细描述>
      * @param authRefType
      * @param authItemId
      * @param needDeleteRefIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void batchDeleteAuthItemRefByRefIds(String authRefType,
            String authItemId, List<String> needDeleteRefIds, String systemId,
            String tableSuffix) {
        if (CollectionUtils.isEmpty(needDeleteRefIds)) {
            return;
        }
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        //如果存在需要删除的权限引用项
        List<AuthItemRefImpl> authItemRefList = new ArrayList<AuthItemRefImpl>();
        for (String refIdTemp : needDeleteRefIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setAuthItem(new AuthItemImpl(authItemId, systemId));
            
            authItemRef.setRefId(refIdTemp);
            authItemRefList.add(authItemRef);
        }
        this.authItemRefImplDao.batchDeleteAuthItemRefImpl(authItemRefList,
                tableSuffix);
        
        //TODO:记录相关业务日志
        //        serviceLogger.info(" {}于 {} 删除类型为{}的日志引用{}.", new String[] { userId,
        //                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
        //                authRefType, ArrayUtils.toString(newAuthIds) });
    }
    
    /**
      * 批量删除权限项的多个权限项目引用 
      * <功能详细描述>
      * @param authType
      * @param authRefType
      * @param authItemId
      * @param needDeleteRefIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void batchDeleteAuthItemRefByRefIds(String authType,
            String authRefType, String authItemId,
            List<String> needDeleteRefIds, String systemId, String tableSuffix) {
        if (CollectionUtils.isEmpty(needDeleteRefIds)) {
            return;
        }
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        //如果存在需要删除的权限引用项
        List<AuthItemRefImpl> authItemRefList = new ArrayList<AuthItemRefImpl>();
        for (String refIdTemp : needDeleteRefIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setAuthItem(new AuthItemImpl(authItemId, systemId,
                    authType));
            
            authItemRef.setRefId(refIdTemp);
            authItemRefList.add(authItemRef);
        }
        this.authItemRefImplDao.batchDeleteAuthItemRefImpl(authItemRefList,
                tableSuffix);
        
        //TODO:记录相关业务日志
        //        serviceLogger.info(" {}于 {} 删除类型为{}的日志引用{}.", new String[] { userId,
        //                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
        //                authRefType, ArrayUtils.toString(newAuthIds) });
    }
    
    /**
     * 批量添加对权限引用的权限
     * 
     * @param userId
     * @param refId
     * @param newAuthIds
     * @param authRefType
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void batchInsertAuthItemRefByAuthItemIds(String authRefType,
            String refId, List<String> needInsertAuthItemIds, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        List<AuthItemRefImpl> authItemRefList = new ArrayList<AuthItemRefImpl>();
        
        //取得当前登录人员id
        String currentOperatorId = AuthSessionContext.getOperatorIdFromSession();
        
        for (String authItemIdTemp : needInsertAuthItemIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setCreateDate(new Date());
            authItemRef.setCreateOperId(currentOperatorId);
            
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refId);
            
            AuthItem authItemTemp = AuthContext.getContext()
                    .getAuthItemFromContextById(authItemIdTemp);
            AssertUtils.notNull(authItemTemp,
                    "authItemTemp is null.authItemIdTemp:{}",
                    authItemIdTemp);
            AssertUtils.isTrue(systemId.equals(authItemTemp.getSystemId()),
                    "authItem.systemId:{} is not equal systemId:{}",
                    authItemTemp.getSystemId(),
                    systemId);
            authItemRef.setAuthItem(authItemTemp);
            
            authItemRef.setValidDependEndDate(false);
            
            authItemRefList.add(authItemRef);
        }
        
        this.authItemRefImplDao.batchInsertAuthItemRefImpl(authItemRefList,
                tableSuffix);
        //        serviceLogger.info(" {}于 {} 新增类型为{}的日志引用{}.", new String[] { userId,
        //                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
        //                authRefType, ArrayUtils.toString(newAuthIds) });
    }
    
    /**
      * 批量插入某一权限引用类型的，一组引用id
      * <功能详细描述>
      * @param authRefType
      * @param authItemId
      * @param needInsertRefIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void batchInsertAuthItemRefByRefIds(String authRefType,
            String authItemId, List<String> needInsertRefIds, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(systemId, "systemId is empty");
        List<AuthItemRefImpl> authItemRefList = new ArrayList<AuthItemRefImpl>();
        
        //取得当前登录人员id
        AuthItem authItem = AuthContext.getContext()
                .getAuthItemFromContextById(authItemId);
        AssertUtils.notNull(authItem,
                "authItem is null.authItemId:{}",
                authItemId);
        
        AssertUtils.isTrue(systemId.equals(authItem.getSystemId()),
                "authItem.systemId:{} is not equal systemId:{}",
                authItem.getSystemId(),
                systemId);
        
        String currentOperatorId = AuthSessionContext.getOperatorIdFromSession();
        for (String refIdTemp : needInsertRefIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setCreateDate(new Date());
            authItemRef.setCreateOperId(currentOperatorId);
            authItemRef.setAuthItem(authItem);
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setValidDependEndDate(false);
            
            authItemRef.setRefId(refIdTemp);
            
            //添加进批量处理列表
            authItemRefList.add(authItemRef);
        }
        
        this.authItemRefImplDao.batchInsertAuthItemRefImpl(authItemRefList,
                tableSuffix);
        //        serviceLogger.info(" {}于 {} 新增类型为{}的日志引用{}.", new String[] { userId,
        //                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
        //                authRefType, ArrayUtils.toString(newAuthIds) });
    }
    
    /**
      * 根据refId删除authItemRefImpl实例
      * 1、如果入参数为空，则抛出异常
      * 2、执行删除后，将返回数据库中被影响的条数
      * @param refId
      * @return 返回删除的数据条数，<br/>
      * 有些业务场景，如果已经被别人删除同样也可以认为是成功的
      * 这里讲通用生成的业务层代码定义为返回影响的条数
      * @return int [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public int deleteByAuthItemId(String authItemId, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(authItemId, "authItemId is empty.");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        AuthItemRefImpl condition = new AuthItemRefImpl();
        condition.setAuthItem(new AuthItemImpl(authItemId,systemId));
        return this.authItemRefImplDao.deleteAuthItemRefImpl(condition,tableSuffix);
    }
    
//    /**
//      * 根据refId更新对象
//      * <功能详细描述>
//      * @param authItemRefImpl
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @Transactional
//    public boolean updateByRefId(AuthItemRef authItemRefImpl, String systemId,
//            String tableSuffix) {
//        AssertUtils.notNull(authItemRefImpl, "");
//        AssertUtils.notEmpty(systemId, "systemId is empty");
//        
//        //生成需要更新字段的hashMap
//        Map<String, Object> updateRowMap = new HashMap<String, Object>();
//        updateRowMap.put("refId", authItemRefImpl.getRefId());
//        updateRowMap.put("authRefType", authItemRefImpl.getAuthRefType());
//        updateRowMap.put("authItem", authItemRefImpl.getAuthItem());
//        
//        //type:java.lang.String
//        updateRowMap.put("validDependEndDate",
//                authItemRefImpl.isValidDependEndDate());
//        updateRowMap.put("endDate", authItemRefImpl.getEndDate());
//        
//        int updateRowCount = this.authItemRefImplDao.updateAuthItemRefImpl(updateRowMap);
//        
//        //如果需要大于1时，抛出异常并回滚，需要在这里修改
//        return updateRowCount >= 1;
//    }
}
