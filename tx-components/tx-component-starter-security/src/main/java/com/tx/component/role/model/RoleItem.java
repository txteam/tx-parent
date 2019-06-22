/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月22日
 * <修改描述:>
 */
package com.tx.component.role.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tx.component.auth.model.Auth;

import io.swagger.annotations.ApiModel;

/**
 * 角色项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "sec_role")
@ApiModel("角色")
public class RoleItem implements Role{
    
    /** 权限项唯一键key : 约定权限项目分割符为"_" 如权限为"wd_" 不同的权限项,id也不能重复 */
    @Id
    private String id;
    
    /** 父级权限id */
    private String parentId;
    
    /** 角色类型 后续根据需要可以扩展相应的权限大类   比如: 产品权限\流程环节权限\通过多个纬度的权限交叉可以达到多纬度的授权体系*/
    private String roleTypeId;
    
    /** 权限项所属模块 */
    private String module;
    
    /** 引用id */
    private String refId;
    
    /** 引用类型 */
    private String refType;
    
    /** 权限项名 */
    private String name;
    
    /** 权限项目描述 */
    private String remark;
    
    /** 是否可配置给 */
    private boolean configAble = true;
    
    /** 子权限列表 */
    @OneToMany(fetch = FetchType.LAZY)
    private List<Role> children = new ArrayList<Role>();
    
    
}
