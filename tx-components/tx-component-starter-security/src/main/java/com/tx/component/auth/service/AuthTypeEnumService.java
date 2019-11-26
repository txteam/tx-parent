/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月22日
 * <修改描述:>
 */
package com.tx.component.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import com.tx.component.auth.context.AuthTypeManager;
import com.tx.component.auth.model.AuthType;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;

/**
 * 权限类型枚举业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthTypeEnumService
        implements AuthTypeManager, InitializingBean, Ordered {
    
    /** 权限类型映射 */
    private final Map<String, AuthType> authTypeMap = new HashMap<>();
    
    /** 权限类型映射 */
    private final Map<String, Class<? extends AuthType>> authTypeClassMap = new HashMap<>();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Class<? extends AuthType>> authTypeClazzSet = ClassScanUtils
                .scanByParentClass(AuthType.class, "com.tx");
        
        for (Class<? extends AuthType> authTypeClazzTemp : authTypeClazzSet) {
            if (!authTypeClazzTemp.isEnum()) {
                continue;
            }
            AuthType[] authTypes = authTypeClazzTemp.getEnumConstants();
            for (AuthType authTypeTemp : authTypes) {
                AssertUtils.notEmpty(authTypeTemp.getId(),
                        "authType.id is empty.");
                AssertUtils.notEmpty(authTypeTemp.getName(),
                        "authType.name is empty.");
                
                AssertUtils.isTrue(
                        !authTypeClassMap.containsKey(authTypeTemp.getId()),
                        "authTypeTemp is duplicate.authId:{},class1:{},class2:{}",
                        new Object[] { authTypeTemp.getId(),
                                authTypeClassMap.get(authTypeTemp.getId()),
                                authTypeTemp });
                
                authTypeClassMap.put(authTypeTemp.getId(), authTypeClazzTemp);
                authTypeMap.put(authTypeTemp.getId(), authTypeTemp);
            }
        }
    }
    
    /**
     * @param authTypeId
     * @return
     */
    @Override
    public AuthType findAuthTypeById(String authTypeId) {
        AssertUtils.notEmpty(authTypeId, "authTypeId is empty.");
        if (!authTypeMap.containsKey(authTypeId)) {
            return null;
        }
        AuthType authType = authTypeMap.get(authTypeId);
        return authType;
    }
    
    /**
     * @return
     */
    @Override
    public List<AuthType> queryAuthTypeList() {
        List<AuthType> resList = new ArrayList<AuthType>(authTypeMap.values());
        return resList;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
}
