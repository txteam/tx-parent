/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.basicdata.testmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.tx.component.basicdata.annotation.BasicData;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLikeAfter;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;
import com.tx.core.util.ObjectUtils;

/**
 * 权限项
 * 如果两个权限项的 id与authType相同，则被认为是同一个authitem
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@BasicData()
@Entity
@Table(name = "auth_authitem")
public class AuthItemImpl implements AuthItem {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5205952448154970380L;
    
    /** 
     * 权限项唯一键key 
     * 约定权限项目分割符为"_"
     * 如权限为"wd_"
     * 不同的权限项,id也不能重复
     */
    @Id
    private String id;
    
    /** 父级权限id */
    private String parentId;
    
    /** 系统唯一id */
    private String systemId;
    
    /** 权限项名 */
    @UpdateAble
    @QueryConditionLikeAfter
    private String name;
    
    /** 权限项目描述 */
    @UpdateAble
    private String description;
    
    /** 
     * 权限类型
     * 后续根据需要可以扩展相应的权限大类
     * 比如 
     *      产品权限
     *      流程环节权限
     *      通过多个纬度的权限交叉可以达到多纬度的授权体系
     */
    @UpdateAble
    private String authType;
    
    /** 子权限列表 */
    @OneToMany(fetch = FetchType.LAZY)
    private List<AuthItem> childs = new ArrayList<AuthItem>();
    
    /** 是否有效，默认为true,权限可停用 */
    @UpdateAble
    @QueryConditionEqual
    private boolean valid = true;
    
    /** 是否可配置 */
    @Transient
    private boolean configAble = true;
    
    /** 是否可见 */
    @UpdateAble
    @QueryConditionEqual
    private boolean viewAble = true;
    
    /** 是否可编辑 */
    @UpdateAble
    @QueryConditionEqual
    private boolean editAble = false;
    
    /** <默认构造函数> */
    public AuthItemImpl() {
        super();
    }
    
    /**
     * <默认构造函数>
     */
    public AuthItemImpl(Map<String, Object> authItemRowMap) {
        super();
        if (authItemRowMap.containsKey("id")) {
            this.id = (String) authItemRowMap.get("id");
        }
        if (authItemRowMap.containsKey("parentId")) {
            this.parentId = (String) authItemRowMap.get("parentId");
        }
        if (authItemRowMap.containsKey("systemId")) {
            this.systemId = (String) authItemRowMap.get("systemId");
        }
        if (authItemRowMap.containsKey("name")) {
            this.name = (String) authItemRowMap.get("name");
        }
        if (authItemRowMap.containsKey("description")) {
            this.description = (String) authItemRowMap.get("description");
        }
        if (authItemRowMap.containsKey("authType")) {
            this.authType = (String) authItemRowMap.get("authType");
        }
        if (authItemRowMap.containsKey(valid)) {
            this.valid = (boolean) authItemRowMap.containsKey("valid");
        }
        if (authItemRowMap.containsKey(configAble)) {
            this.configAble = (boolean) authItemRowMap.containsKey("configAble");
        }
        if (authItemRowMap.containsKey(viewAble)) {
            this.viewAble = (boolean) authItemRowMap.containsKey("viewAble");
        }
        if (authItemRowMap.containsKey(editAble)) {
            this.editAble = (boolean) authItemRowMap.containsKey("editAble");
        }
    }
    
    /**
     * <默认构造函数>
     */
    public AuthItemImpl(AuthItem otherAuthItem) {
        super();
        this.id = otherAuthItem.getId();
        this.parentId = otherAuthItem.getParentId();
        this.systemId = otherAuthItem.getSystemId();
        this.name = otherAuthItem.getName();
        this.description = otherAuthItem.getDescription();
        this.authType = otherAuthItem.getAuthType();
        this.valid = otherAuthItem.isValid();
        this.configAble = otherAuthItem.isConfigAble();
        this.viewAble = otherAuthItem.isViewAble();
        this.editAble = otherAuthItem.isEditAble();
    }
    
    /** <默认构造函数> */
    public AuthItemImpl(String id, String sytemId) {
        super();
        this.id = id;
        this.systemId = sytemId;
    }
    
    /** <默认构造函数> */
    public AuthItemImpl(String id, String systemId, String authType) {
        super();
        this.id = id;
        this.systemId = systemId;
        this.authType = authType;
    }
    
//    /** <默认构造函数> */
//    public AuthItemImpl(String id, String systemId, String parentId,
//            String name, String description, String authType, boolean valid,
//            boolean configAble, boolean viewAble) {
//        super();
//        this.id = id;
//        this.parentId = parentId;
//        this.systemId = systemId;
//        this.name = name;
//        this.description = description;
//        this.authType = authType;
//        this.valid = valid;
//        this.configAble = configAble;
//        this.viewAble = viewAble;
//    }
//    
//    /** <默认构造函数> */
//    public AuthItemImpl(String id, String systemId, String parentId,
//            String name, String description, String authType,
//            List<AuthItem> childs, boolean valid, boolean configAble,
//            boolean viewAble, boolean editAble) {
//        super();
//        this.id = id;
//        this.systemId = systemId;
//        this.parentId = parentId;
//        this.name = name;
//        this.description = description;
//        this.authType = authType;
//        this.childs = childs;
//        this.valid = valid;
//        this.configAble = configAble;
//        this.viewAble = viewAble;
//        this.editAble = editAble;
//    }
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return id;
    }
    
    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return
     */
    @Override
    public String getParentId() {
        return parentId;
    }
    
    /**
     * @return 返回 systemId
     */
    public String getSystemId() {
        return systemId;
    }
    
    /**
     * @param 对systemId进行赋值
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        if (StringUtils.isEmpty(name)) {
            return id;
        }
        return name;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return
     */
    @Override
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return
     */
    @Override
    public List<AuthItem> getChilds() {
        return childs;
    }
    
    /**
     * @param childs
     */
    @Override
    public void setChilds(List<AuthItem> childs) {
        this.childs = childs;
    }
    
    /**
     * @return
     */
    @Override
    public String getAuthType() {
        return authType;
    }
    
    /**
     * @param authType
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    
    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    /**
     * @return 返回 valid
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     * @param 对valid进行赋值
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    /**
     * @return 返回 configAble
     */
    public boolean isConfigAble() {
        return configAble;
    }
    
    /**
     * @param 对configAble进行赋值
     */
    public void setConfigAble(boolean configAble) {
        this.configAble = configAble;
    }
    
    /**
     * @return 返回 viewAble
     */
    public boolean isViewAble() {
        return viewAble;
    }
    
    /**
     * @param 对viewAble进行赋值
     */
    public void setViewAble(boolean viewAble) {
        this.viewAble = viewAble;
    }
    
    /**
     * @return 返回 editAble
     */
    public boolean isEditAble() {
        return editAble;
    }
    
    /**
     * @param 对editAble进行赋值
     */
    public void setEditAble(boolean editAble) {
        this.editAble = editAble;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return ObjectUtils.equals(this, obj, "systemId", "id", "authType");
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int resHashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "systemId",
                "id",
                "authType");
        return resHashCode;
    }
}