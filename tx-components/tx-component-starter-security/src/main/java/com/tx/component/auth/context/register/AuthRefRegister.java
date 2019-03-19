/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-5
 * <修改描述:>
 */
package com.tx.component.auth.context.register;

import com.tx.component.auth.model.AuthRef;

/**
 * 权限引用项注册器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-4-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//没想清楚，暂时木有啥用
public interface AuthRefRegister {
    
    /**
     * 权限注册器对应权限类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public String authRefType();
    
    /**
     * 注册权限<br/>
     * <功能详细描述>
     * @param authItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void registeAuthRef(AuthRef authItemRef);
}
