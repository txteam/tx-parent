/*
g * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.auth.dao.AuthDao;
import com.tx.component.auth.model.AuthItemRef;
import com.tx.component.auth.model.AuthItemRefImpl;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * 权限处理业务层<br/>
 * <功能详细描述>
 * 
 * @author brady
 * @version [版本号, 2012-11-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuthService {
    
    /** 日志记录器 */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    /** 业务日志记录器，默认使用logback日志，可注入业务日志记录器记录业务日志 */
    private Logger serviceLogger = LoggerFactory.getLogger(AuthService.class);
    
    private AuthDao authDao;
    
    /**
     * <根据登录人id查询权限项列表> 后续通过重载该方法可以针对具体的项目让authContext修改性提出去
     * 
     * @param operatorId
     * @return [参数说明]
     * 
     * @return List<AuthItemRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryAuthItemRefSetByOperatorId(String operatorId) {
        if (StringUtils.isEmpty(operatorId)) {
            throw new ParameterIsEmptyException(
                    "queryAuthItemRefSetByOperatorId(operatorId) operatorId is empty.");
        }
        
        List<AuthItemRef> authItemRefList = this.authDao.queryItemAuthRefListByOperId(operatorId);
        return authItemRefList;
    }
    
    /**
     * 更新权限引用项，并记录相应的业务日志<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void updateAuthItemRef(String operId, String refId,
            String authRefType, List<String> newAuthIds) {
        if (StringUtils.isEmpty(operId) || StringUtils.isEmpty(refId)
                || StringUtils.isEmpty(authRefType)) {
            throw new ParameterIsEmptyException(
                    "updateAuthItemRef({}) operatorId is empty.", refId);
        }
        
    }
    
    /**
     * <根据操作员以及权限引用类型查询当前操作人员具有哪些权限> <功能详细描述>
     * 
     * @param operatorId
     * @param authRefType
     * @return [参数说明]
     * 
     * @return List<AuthItemRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryAuthItemRefListByAuthRefType(
            String userId, String authRefType) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(authRefType)) {
            throw new ParameterIsEmptyException(
                    "queryAuthItemRefListByAuthRefType({},{}) operatorId is empty.",
                    userId, authRefType);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("operatorId", userId);
        params.put("authRefType", authRefType);
        List<AuthItemRef> authItemRefList = this.authDao.queryItemAuthRefList(params);
        return authItemRefList;
    }
    
    /**
     * <删除日志项> <功能详细描述>
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
    public void delAuthIds(String userId, String refId, String authRefType,
            List<String> newAuthIds) {
        List<AuthItemRef> authItemRefList = new ArrayList<AuthItemRef>();
        for (String newAuthId : newAuthIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setCreateDate(new Date());
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refId);
            authItemRef.setCreateOperId(userId);
            authItemRef.setAuthId(newAuthId);
            authItemRefList.add(authItemRef);
        }
        this.authDao.delAuthItemRefList(authItemRefList);
        serviceLogger.info(" {}于 {} 删除类型为{}的日志引用{}.", new String[] { userId,
                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                authRefType, ArrayUtils.toString(newAuthIds) });
    }
    
    /**
     * <新增日志项> <功能详细描述>
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
    public void addAuthIds(String userId, String refId,
            List<String> newAuthIds, String authRefType) {
        List<AuthItemRef> authItemRefList = new ArrayList<AuthItemRef>();
        for (String newAuthId : newAuthIds) {
            AuthItemRefImpl authItemRef = new AuthItemRefImpl();
            authItemRef.setCreateDate(new Date());
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refId);
            authItemRef.setCreateOperId(userId);
            authItemRef.setAuthId(newAuthId);
            authItemRefList.add(authItemRef);
        }
        this.authDao.addAuthItemRefList(authItemRefList);
        serviceLogger.info(" {}于 {} 新增类型为{}的日志引用{}.", new String[] { userId,
                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                authRefType, ArrayUtils.toString(newAuthIds) });
    }
    
    /**
     * @return 返回 serviceLogger
     */
    public Logger getServiceLogger() {
        return serviceLogger;
    }
    
    /**
     * @param 对serviceLogger进行赋值
     */
    public void setServiceLogger(Logger serviceLogger) {
        this.serviceLogger = serviceLogger;
    }

    /**
     * @return 返回 authDao
     */
    public AuthDao getAuthDao() {
        return authDao;
    }

    /**
     * @param 对authDao进行赋值
     */
    public void setAuthDao(AuthDao authDao) {
        this.authDao = authDao;
    }
}
