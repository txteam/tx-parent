/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-1
 * <修改描述:>
 */
package com.tx.component.auth.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限引用项
 *     可以是： 角色权限
 *             职位权限
 *             ...可以赋予权限的主体
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "auth_authref")
public class AuthItemRef extends AbstractAuthItemRef {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7928952142014599323L;
    
    /** <默认构造函数> */
    public AuthItemRef() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthItemRef(Auth auth) {
        super(auth);
    }
    
    /** <默认构造函数> */
    public AuthItemRef(String authId) {
        super(authId);
    }
    
}
