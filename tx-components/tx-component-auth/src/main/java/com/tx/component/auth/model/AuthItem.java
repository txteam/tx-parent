/*
x * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

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
@Entity
@Table(name = "auth_authitem")
public class AuthItem extends AbstractAuthItem {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5205952448154970380L;
    
    /** <默认构造函数> */
    public AuthItem() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthItem(Auth otherAuthItem) {
        super(otherAuthItem);
    }

    /** <默认构造函数> */
    public AuthItem(Map<String, Object> authItemRowMap) {
        super(authItemRowMap);
    }

    /** <默认构造函数> */
    public AuthItem(String id, String module, String authType) {
        super(id, module, authType);
    }
}
