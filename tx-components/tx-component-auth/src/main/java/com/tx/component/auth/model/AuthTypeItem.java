/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.store.chm.ConcurrentHashMap;

import org.apache.cxf.common.util.StringUtils;

import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * 权限项类型<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthTypeItem implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7942093110803351685L;
    
    /** 权限类型  */
    private String authType;
    
    /** 权限类型名  */
    private String name;
    
    /** 权限类型描述 */
    private String description;
    
    /** 是否可见 */
    private boolean isViewAble;
    
    /** 是否可在统一权限管理界面进行编辑  */
    private boolean isConfigAble;
    
    /** 权限项列表 */
    private List<AuthItem> authItemList;
    
    /**
     * 使AuthType构造函数为包内可见，使外部不能通过new去创建AuthTypeItem
     * <默认构造函数>
     */
    private AuthTypeItem(String authType, String name, String description,
            boolean isViewAble, boolean isConfigAble) {
        super();
        this.authType = authType;
        this.name = name;
        this.description = description;
        this.isViewAble = isViewAble;
        this.isConfigAble = isConfigAble;
    }
    
    /**
     * @return 返回 authType
     */
    public String getAuthType() {
        return authType;
    }
    
    /**
     * @param 对authType进行赋值
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    
    /**
     * @return 返回 isViewAble
     */
    public boolean isViewAble() {
        return isViewAble;
    }
    
    /**
     * @param 对isViewAble进行赋值
     */
    public void setViewAble(boolean isViewAble) {
        this.isViewAble = isViewAble;
    }
    
    /**
     * @return 返回 isConfigAble
     */
    public boolean isConfigAble() {
        return isConfigAble;
    }

    /**
     * @param 对isConfigAble进行赋值
     */
    public void setConfigAble(boolean isConfigAble) {
        this.isConfigAble = isConfigAble;
    }

    /**
     * @return 返回 authItemList
     */
    public List<AuthItem> getAuthItemList() {
        return authItemList;
    }
    
    /**
     * @param 对authItemList进行赋值
     */
    public void setAuthItemList(List<AuthItem> authItemList) {
        this.authItemList = authItemList;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        if (StringUtils.isEmpty(name)) {
            return authType;
        } else {
            return name;
        }
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return 返回 description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AuthTypeItem)) {
            return false;
        } else {
            AuthTypeItem other = (AuthTypeItem) obj;
            if (this.authType == null) {
                return this == other;
            } else {
                return this.authType.equals(other.getAuthType());
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        if (this.authType == null) {
            return super.hashCode();
        }
        return this.authType.hashCode();
    }
    
    /**
      * 权限类型工厂<br/>
      * <功能详细描述>
      * 
      * @author  brady
      * @version  [版本号, 2013-4-2]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    public static abstract class AuthTypeFactory {
        
        /** 权限类型映射 */
        private static Map<String, AuthTypeItem> authTypeItemMapping = new ConcurrentHashMap<String, AuthTypeItem>();
        
        /**
          * 创建权限类型实例<br/>
          * <功能详细描述>
          * @param authType
          * @param name
          * @param description
          * @param isViewAble
          * @param isConfigAble
          * @return [参数说明]
          * 
          * @return AuthTypeItem [返回类型说明]
          * @exception throws [异常类型] [异常说明]
          * @see [类、类#方法、类#成员]
         */
        public synchronized static AuthTypeItem newAuthTypeInstance(String authType, String name, String description,
                boolean isViewAble, boolean isConfigAble) {
            if(StringUtils.isEmpty(authType)){
                throw new ParameterIsEmptyException("authType is empty");
            }
            
            AuthTypeItem res = null;
            if(authTypeItemMapping.containsKey(authType)){
                res = authTypeItemMapping.get(authType);
                if(!StringUtils.isEmpty(name)){
                    res.setName(name);
                }
                if(StringUtils.isEmpty(description)){
                    res.setDescription(description);
                }
                //如果其中有一个与默认值不同，则认为不同的该值将生效
                //如果其中有任意一个设置为不可见，则认为该类型可见
                if(!isViewAble){
                    res.setViewAble(isViewAble);
                }
                //如果其中有任意一个设置为不可编辑，则认为不可编辑
                if(!isConfigAble){
                    res.setConfigAble(isConfigAble);
                }
               
            }else{
                res = new AuthTypeItem(authType,name,description,isViewAble,isConfigAble);
                authTypeItemMapping.put(authType, res);
            }
            return res;
        }
    }
}
