/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.store.chm.ConcurrentHashMap;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.cxf.common.util.StringUtils;

import com.tx.component.auth.model.AuthTypeItem;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * 权限类型容器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-4-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthTypeItemContext{
    
    private static AuthTypeItemContext context = new AuthTypeItemContext();
    
    /** 权限类型映射 */
    private static Map<String, AuthTypeItem> authTypeItemMapping = new ConcurrentHashMap<String, AuthTypeItem>();
    
    /** <默认构造函数> */
    private AuthTypeItemContext() {
        super();
    }
    
    /**
      * 取得权限类型容器实例
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return AuthTypeContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static AuthTypeItemContext getContext() {
        return context;
    }
    
    /**
     * 创建权限类型实例<br/>
     * @param authType
     * @param name
     * @param description
     * @param isViewAble
     * @param isConfigAble
     * @return
     */
    public synchronized AuthTypeItem registeAuthTypeItem(
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
      * 获取权限类型实例<br/>
      *     如果权限类型容器中不存在对应权限类型项实例则生成并注入容器<br/>
      * <功能详细描述>
      * @param authType
      * @return [参数说明]
      * 
      * @return AuthTypeItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public synchronized AuthTypeItem registeAuthTypeItem(String authType) {
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
      * 获取所有注册过的权限类型<br/>
      *     如果权限类型容器中不存在对应权限类型项实例则生成并注入容器<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<AuthTypeItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthTypeItem> getAllAuthTypeItemList() {
        List<AuthTypeItem> authTypeList = new ArrayList<AuthTypeItem>(
                authTypeItemMapping.values());
        
        @SuppressWarnings("unchecked")
        List<AuthTypeItem> authItemList = ListUtils.unmodifiableList(authTypeList);
        return authItemList;
    }
    
    /**
      * 获取系统中已经注册过的权限项映射<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,AuthTypeItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, AuthTypeItem> getAllAuthTypeItemMap() {
        @SuppressWarnings("unchecked")
        Map<String, AuthTypeItem> res = MapUtils.unmodifiableMap(authTypeItemMapping);
        return res;
    }
}
