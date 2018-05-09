/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月7日
 * <修改描述:>
 */
package com.tx.component.auth.context.loaderprocessor.childauth;

import java.util.Set;

import com.tx.component.auth.model.Auth;

/**
 * 子数据权限注册器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ChildAuthRegister {
    
    /**
     * 加权限项
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Set<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public Set<Auth> loadAuthItems(Auth authItem);
}
