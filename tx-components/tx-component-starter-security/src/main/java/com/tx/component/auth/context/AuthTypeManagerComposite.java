/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.Cache;
import org.springframework.core.OrderComparator;

import com.tx.component.auth.model.AuthType;
import com.tx.core.exceptions.util.AssertUtils;

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
        
        if (!CollectionUtils.isEmpty(authTypeManagers)) {
            Collections.sort(authTypeManagers, OrderComparator.INSTANCE);
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
     * 查询权限类型列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<AuthType> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthType> queryList() {
        List<AuthType> resList = new ArrayList<>();
        
        Set<String> authTypeIdSet = new HashSet<>();
        for (AuthTypeManager rm : delegates) {
            List<AuthType> tempList = rm.queryAuthTypeList();
            if(CollectionUtils.isEmpty(tempList)){
                continue;
            }
            //过滤重复权限类型
            tempList.stream().forEach(atTemp -> {
                if (!authTypeIdSet.contains(atTemp.getId())) {
                    resList.add(atTemp);
                    authTypeIdSet.add(atTemp.getId());
                }
            });
        }
        return resList;
    }
    
}
