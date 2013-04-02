/*
g * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.service.impl;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.auth.dao.AuthDao;
import com.tx.component.auth.model.AuthItemRef;
import com.tx.component.auth.service.AuthService;
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
public class AuthServiceImpl implements AuthService{
    
    /** 日志记录器 */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

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
