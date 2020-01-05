/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.auth.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tx.core.exceptions.util.AssertUtils;

import io.swagger.annotations.ApiModel;

/**
 * 权限项类型<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "sec_auth_type")
@ApiModel("权限类型")
public class AuthTypeItem implements AuthType {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7942093110803351685L;
    
    /** 权限类型  */
    private String id;
    
    /** 权限类型名  */
    private String name;
    
    /** 权限类型描述 */
    private String remark;
    
    /** <默认构造函数> */
    public AuthTypeItem() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthTypeItem(AuthType authType) {
        super();
        AssertUtils.notNull(authType, "authType is null.");
        this.id = authType.getId();
        this.name = authType.getName();
        this.remark = authType.getRemark();
    }
    
    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return 返回 remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
