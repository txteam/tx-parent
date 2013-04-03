/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.tx.component.auth.context.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.store.chm.ConcurrentHashMap;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.cxf.common.util.StringUtils;

import com.tx.component.auth.context.AuthTypeFactory;
import com.tx.component.auth.model.AuthTypeItem;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * 权限类型工厂
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-4-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultAuthTypeFactory implements AuthTypeFactory {
    
    private static AuthTypeFactory factory = new DefaultAuthTypeFactory();
    
    /** 权限类型映射 */
    private static Map<String, AuthTypeItem> authTypeItemMapping = new ConcurrentHashMap<String, AuthTypeItem>();
    
    /** <默认构造函数> */
    private DefaultAuthTypeFactory() {
        super();
    }
    
    public static AuthTypeFactory newInstance() {
        return factory;
    }
    
    /**
     * @param authType
     * @param name
     * @param description
     * @param isViewAble
     * @param isConfigAble
     * @return
     */
    @Override
    public synchronized AuthTypeItem registeNewOrGetAuthTypeItem(
            String authType, String name, String description,
            boolean isViewAble, boolean isConfigAble) {
        if (StringUtils.isEmpty(authType)) {
            throw new ParameterIsEmptyException("authType is empty");
        }
        
        AuthTypeItem res = null;
        if (authTypeItemMapping.containsKey(authType)) {
            res = authTypeItemMapping.get(authType);
            if (!StringUtils.isEmpty(name)) {
                res.setName(name);
            }
            if (StringUtils.isEmpty(description)) {
                res.setDescription(description);
            }
            //如果其中有一个与默认值不同，则认为不同的该值将生效
            //如果其中有任意一个设置为不可见，则认为该类型可见
            if (!isViewAble) {
                res.setViewAble(isViewAble);
            }
            //如果其中有任意一个设置为不可编辑，则认为不可编辑
            if (!isConfigAble) {
                res.setConfigAble(isConfigAble);
            }
        } else {
            res = new AuthTypeItem(authType, name, description, isViewAble,
                    isConfigAble);
            authTypeItemMapping.put(authType, res);
        }
        return res;
    }
    
    /**
     * @param authType
     * @return
     */
    @Override
    public synchronized AuthTypeItem registeNewOrGetAuthTypeItem(String authType) {
        if (StringUtils.isEmpty(authType)) {
            throw new ParameterIsEmptyException("authType is empty");
        }
        
        AuthTypeItem res = null;
        if (authTypeItemMapping.containsKey(authType)) {
            res = authTypeItemMapping.get(authType);
        } else {
            res = new AuthTypeItem(authType);
            authTypeItemMapping.put(authType, res);
        }
        return res;
    }
    
    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AuthTypeItem> getAllRegistedAuthTypeItemList() {
        List<AuthTypeItem> authTypeList = new ArrayList<AuthTypeItem>(
                authTypeItemMapping.values());
        return ListUtils.unmodifiableList(authTypeList);
    }
    
    /**
     * @return
     */
    @Override
    public Map<String, AuthTypeItem> getAllRegistedAuthTypeItemMap() {
        @SuppressWarnings("unchecked")
        Map<String, AuthTypeItem> res = MapUtils.unmodifiableMap(authTypeItemMapping);
        return res;
    }
    
}
