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

import com.tx.component.auth.dao.AuthItemRefImplDao;
import com.tx.component.auth.model.AuthItemRefImpl;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * AuthItemRefImpl的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("authItemRefService")
public class AuthItemRefService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(AuthItemRefService.class);
    
    @SuppressWarnings("unused")
    private Logger serviceLogger;
    
    @Resource(name = "authItemRefImplDao")
    private AuthItemRefImplDao authItemRefImplDao;
    
    /**
      * 根据AuthItemRefImpl实体列表
      * TODO:补充说明
      * 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRefImpl> queryAuthItemRefImplList(/*TODO:自己定义条件*/) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<AuthItemRefImpl> resList = this.authItemRefImplDao.queryAuthItemRefImplList(params);
        
        return resList;
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
    public int deleteByRefId(String refId) {
        if (StringUtils.isEmpty(refId)) {
            throw new ParameterIsEmptyException(
                    "AuthItemRefImplService.deleteByRefId refId isEmpty.");
        }
        
        AuthItemRefImpl condition = new AuthItemRefImpl();
        condition.setRefId(refId);
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
    public boolean updateByRefId(AuthItemRefImpl authItemRefImpl) {
        //TODO:验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalrefIdException
        if (authItemRefImpl == null || StringUtils.isEmpty(authItemRefImpl.getRefId())) {
            throw new ParameterIsEmptyException(
                    "AuthItemRefImplService.updateByRefId authItemRefImpl or authItemRefImpl.refId is empty.");
        }
        
        //TODO:生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("refId", authItemRefImpl.getRefId());
        
        //TODO:需要更新的字段
		updateRowMap.put("authRefType", authItemRefImpl.getAuthRefType());	
		//type:java.lang.String
		updateRowMap.put("authItem", authItemRefImpl.getAuthItem());
		updateRowMap.put("validDependEndDate", authItemRefImpl.isValidDependEndDate());	
		updateRowMap.put("createOperId", authItemRefImpl.getCreateOperId());	
		updateRowMap.put("endDate", authItemRefImpl.getEndDate());	
		updateRowMap.put("createDate", authItemRefImpl.getCreateDate());	
        
        int updateRowCount = this.authItemRefImplDao.updateAuthItemRefImpl(updateRowMap);
        
        //TODO:如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
