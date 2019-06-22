/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.Cache;

import com.tx.component.auth.model.AuthType;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * 权限类型业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthTypeManagerComposite {
    
    private List<AuthTypeManager> delegates;
    
    /** <默认构造函数> */
    public AuthTypeManagerComposite(List<AuthTypeManager> authTypeManagers,
            Cache cache) {
        super();
        this.delegates = new ArrayList<>();
        AssertUtils.notNull(cache, "cache is null.");
        
        if (CollectionUtils.isEmpty(authTypeManagers)) {
            authTypeManagers.stream().forEach(atTemp -> {
                if (atTemp instanceof CachingAuthTypeManager) {
                    this.delegates.add((CachingAuthTypeManager) atTemp);
                } else {
                    this.delegates
                            .add(new CachingAuthTypeManager(atTemp, cache));
                }
            });
        }
    }
    
    /**
     * @param authTypeId
     * @return
     */
    public AuthType findById(String authTypeId) {
        AssertUtils.notEmpty(authTypeId, "authTypeId is empty.");
        
        AuthType authTypeTemp = null;
        for (AuthTypeManager rm : delegates) {
            authTypeTemp = rm.findAuthTypeById(authTypeId);
            if (authTypeTemp != null) {
                return authTypeTemp;
            }
        }
        return authTypeTemp;
    }
    
    /**
     * @param params
     * @return
     */
    public List<AuthType> queryList(Querier querier) {
        List<AuthType> resList = new ArrayList<>();
        for (AuthTypeManager rm : delegates) {
            List<AuthType> tempList = rm.queryAuthTypeList(querier);
            if (!CollectionUtils.isEmpty(tempList)) {
                resList.addAll(tempList);
            }
        }
        return resList;
    }
    
}
