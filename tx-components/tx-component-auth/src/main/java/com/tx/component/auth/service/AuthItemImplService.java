/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.auth.dao.AuthItemImplDao;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.core.exceptions.argument.NullArgException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * AuthItemImpl的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("authItemImplService")
public class AuthItemImplService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(AuthItemImplService.class);
    
    @SuppressWarnings("unused")
    //@Resource(name = "serviceLogger")
    private Logger serviceLogger;
    
    @Resource(name = "authItemImplDao")
    private AuthItemImplDao authItemDao;
    
    @Resource(name = "authItemRefImplService")
    private AuthItemRefImplService authItemRefService;
    
    /**
      * 查找权限项目实例
      * <功能详细描述>
      * @param authItemId
      * @return [参数说明]
      * 
      * @return AuthItemImpl [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public AuthItemImpl findAuthItemImplById(String authItemId){
        AssertUtils.notEmpty(authItemId,"authItemId is empty.");
        
        AuthItemImpl condition = new AuthItemImpl();
        condition.setId(authItemId);
        
        AuthItemImpl res = this.authItemDao.findAuthItemImpl(condition);
        return res;
    }
    
    /**
      * 查询数据库中存储的所有权限项<br/>
      * <功能详细描述>
      * 
      * @return [参数说明]
      * 
      * @return List<AuthItemImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemImpl> queryAllAuthItemList() {
        //查询权限项集合
        List<AuthItemImpl> resList = this.authItemDao.queryAuthItemImplList(null);
        
        return resList;
    }

    /**
      * 查询AuthItem实体列表<br/>
      * <功能详细描述>
      * 
      * @return [参数说明]
      * 
      * @return List<AuthItemImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemImpl> queryAuthItemListByAuthType(String authType) {
        AssertUtils.notEmpty(authType,"authType is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        if(!StringUtils.isEmpty(authType)){
            params.put("authType", authType);
        }
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<AuthItemImpl> resList = this.authItemDao.queryAuthItemImplList(params);
        
        return resList;
    }
    
    /**
      * 将authItemImpl实例插入数据库中保存
      * 1、如果authItemImpl为空时抛出参数为空异常
      * 2、如果authItemImpl中部分必要参数为非法值时抛出参数不合法异常
      * <功能详细描述>
      * @param authItemImpl [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insertAuthItemImpl(AuthItemImpl authItemImpl) {
        AssertUtils.notNull(authItemImpl, "authItem is null.");
        AssertUtils.notEmpty(authItemImpl.getAuthType(),"authItem.authType is empty.");
        
        this.authItemDao.insertAuthItemImpl(authItemImpl);
    }
    
    /**
      * 根据id删除authItemImpl实例
      * 1、如果入参数为空，则抛出异常
      * 2、执行删除后，将返回数据库中被影响的条数
      * @param id
      * @return 返回删除的数据条数，<br/>
      * 有些业务场景，如果已经被别人删除同样也可以认为是成功的
      * 这里讲通用生成的业务层代码定义为返回影响的条数
      * @return int [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void deleteById(String authItemId) {
        if (StringUtils.isEmpty(authItemId)) {
            throw new NullArgException(
                    "AuthItemImplService.deleteById id isEmpty.");
        }
        
        AuthItemImpl condition = new AuthItemImpl();
        condition.setId(authItemId);
        this.authItemDao.deleteAuthItemImpl(condition);
        this.authItemRefService.deleteByAuthItemId(authItemId);
    }
    
    /**
      * 根据id更新对象
      * <功能详细描述>
      * @param authItemImpl
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(AuthItemImpl authItemImpl) {
        AssertUtils.notNull(authItemImpl,"authItem is null");
        AssertUtils.notEmpty(authItemImpl.getId(),"authItem.id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", authItemImpl.getId());
        
        //需要更新的字段
		updateRowMap.put("valid", authItemImpl.isValid());	
		updateRowMap.put("parentId", authItemImpl.getParentId());	
		updateRowMap.put("description", authItemImpl.getDescription());	
		updateRowMap.put("editAble", authItemImpl.isEditAble());	
		updateRowMap.put("viewAble", authItemImpl.isViewAble());	
		updateRowMap.put("name", authItemImpl.getName());	
		updateRowMap.put("authType", authItemImpl.getAuthType());	
        
        int updateRowCount = this.authItemDao.updateAuthItemImpl(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
    /**
      * 更新权限项
      *<功能详细描述>
      * @param authItemRowMap
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public AuthItemImpl saveAuthItemImplByAuthItemRowMap(Map<String, Object> authItemRowMap) {
        AssertUtils.notNull(authItemRowMap,"authItemRowMap is null");
        AssertUtils.notEmpty((String)authItemRowMap.get("id"),"authItemRowMap.id is empty.");   
        String authItemId = (String)authItemRowMap.get("id");
        AuthItemImpl authItem = findAuthItemImplById(authItemId);
        if(authItem != null){
            this.authItemDao.updateAuthItemImpl(authItemRowMap);
        }else{
            authItem = new AuthItemImpl(authItemRowMap);
            insertAuthItemImpl(authItem);
        }
        
        return authItem;
    }
}
