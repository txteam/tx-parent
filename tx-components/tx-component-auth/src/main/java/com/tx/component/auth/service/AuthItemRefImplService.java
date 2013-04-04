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

import com.tx.component.auth.context.AuthSessionContext;
import com.tx.component.auth.dao.AuthItemRefImplDao;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.component.auth.model.AuthItemRef;
import com.tx.component.auth.model.AuthItemRefImpl;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * AuthItemRefImpl的业务层<br/>
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("authItemRefImplService")
public class AuthItemRefImplService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(AuthItemRefImplService.class);
    
    @SuppressWarnings("unused")
    private Logger serviceLogger;
    
    @Resource(name = "authItemRefImplDao")
    private AuthItemRefImplDao authItemRefImplDao;
    
    @Resource(name = "authSessionContext")
    private AuthSessionContext authSessionContext;
    
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
            Map<String, String> refType2RefIdMapping) {
        AssertUtils.notEmpty(refType2RefIdMapping,
                "refType2RefIdMapping is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refType2RefIdMapping", refType2RefIdMapping);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params);
        
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
            String authRefType, String refId) {
        AssertUtils.notEmpty(refId, "refId is empty.");
        AssertUtils.notEmpty(authRefType, "authRefType is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refId", refId);
        params.put("authRefType", authRefType);
        
        List<AuthItemRefImpl> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params);
        
        return authItemRefImplList;
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
    public void saveNewAuthItemRef(String authRefType, String refId,
            List<String> authItemIds) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(refId, "refId is empty");
        
        //存储前,获取原有的权限引用
        List<String> srcIds = new ArrayList<String>();
        List<AuthItemRefImpl> authItemRefImpl = queryAuthItemRefListByRefTypeAndRefId(authRefType,
                refId);
        if (authItemRefImpl != null) {
            for (AuthItemRefImpl refTemp : authItemRefImpl) {
                srcIds.add(refTemp.getAuthItem().getId());
            }
        }
        
        //求差集，得到需要删除，以及需要增加的权限id集合
        @SuppressWarnings("unchecked")
        List<String> needDeleteAuthItemIds = ListUtils.subtract(srcIds,
                authItemIds);
        @SuppressWarnings("unchecked")
        List<String> needInsertAuthItemIds = ListUtils.subtract(authItemIds,
                srcIds);
        
        batchDeleteAuthItemRef(authRefType, refId, needDeleteAuthItemIds);
        batchInsertAuthItemRef(authRefType, refId, needInsertAuthItemIds);
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
    private void batchDeleteAuthItemRef(String authRefType, String refId,
            List<String> needDeleteAuthItemIds) {
        if (CollectionUtils.isEmpty(needDeleteAuthItemIds)) {
            return;
        }
        
        //如果存在需要删除的权限引用项
        List<AuthItemRefImpl> authItemRefList = new ArrayList<AuthItemRefImpl>();
        for (String newAuthId : needDeleteAuthItemIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refId);
            authItemRef.setAuthItem(new AuthItemImpl(newAuthId));
            
            authItemRefList.add(authItemRef);
        }
        this.authItemRefImplDao.batchDeleteAuthItemRefImpl(authItemRefList);
        
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
    private void batchInsertAuthItemRef(String authRefType, String refId,
            List<String> needInsertAuthItemIds) {
        List<AuthItemRefImpl> authItemRefList = new ArrayList<AuthItemRefImpl>();
        String currentOperatorId = authSessionContext.getOperatorIdFromSession();
        for (String newAuthId : needInsertAuthItemIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setCreateDate(new Date());
            authItemRef.setCreateOperId(currentOperatorId);
            
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refId);
            authItemRef.setAuthItem(new AuthItemImpl(newAuthId));
            authItemRef.setValidDependEndDate(false);
            
            authItemRefList.add(authItemRef);
        }
        
        this.authItemRefImplDao.batchInsertAuthItemRefImpl(authItemRefList);
        //        serviceLogger.info(" {}于 {} 新增类型为{}的日志引用{}.", new String[] { userId,
        //                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
        //                authRefType, ArrayUtils.toString(newAuthIds) });
    }
    
    /**
      * 将authItemRefImpl实例插入数据库中保存
      * 1、如果authItemRefImpl为空时抛出参数为空异常
      * 2、如果authItemRefImpl中部分必要参数为非法值时抛出参数不合法异常
      * <功能详细描述>
      * @param authItemRefImpl [参数说明]
      * 
      * @return vorefId [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insertAuthItemRefImpl(AuthItemRefImpl authItemRefImpl) {
        //TODO:验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalrefIdException
        if (authItemRefImpl == null /*TODO:|| 其他参数验证*/) {
            throw new ParameterIsEmptyException(
                    "AuthItemRefImplService.insertAuthItemRefImpl authItemRefImpl isNull.");
        }
        
        this.authItemRefImplDao.insertAuthItemRefImpl(authItemRefImpl);
    }
    
    /**
      * 根据权限引用项主键删除对应的权限引用项
      * 
      * <功能详细描述>
      * @param authItemId
      * @param refType
      * @param refId
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unused")
    private void deleteByPk(String authItemId, String refType, String refId) {
        //AssertUtils.isem
        
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
    public int deleteByAuthItemId(String authItemId) {
        AssertUtils.notEmpty(authItemId, "authItemId is empty.");
        
        AuthItemRefImpl condition = new AuthItemRefImpl();
        condition.setAuthItem(new AuthItemImpl(authItemId));
        return this.authItemRefImplDao.deleteAuthItemRefImpl(condition);
    }
    
    /**
      * 根据refId更新对象
      * <功能详细描述>
      * @param authItemRefImpl
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateByRefId(AuthItemRef authItemRefImpl) {
        AssertUtils.notNull(authItemRefImpl, "");
        AssertUtils.notEmpty("", "");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("refId", authItemRefImpl.getRefId());
        updateRowMap.put("authRefType", authItemRefImpl.getAuthRefType());
        updateRowMap.put("authItem", authItemRefImpl.getAuthItem());
        
        //type:java.lang.String
        updateRowMap.put("validDependEndDate",
                authItemRefImpl.isValidDependEndDate());
        updateRowMap.put("endDate", authItemRefImpl.getEndDate());
        
        int updateRowCount = this.authItemRefImplDao.updateAuthItemRefImpl(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
