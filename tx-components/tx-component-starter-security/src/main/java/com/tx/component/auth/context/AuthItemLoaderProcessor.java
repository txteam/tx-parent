/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月7日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.Map;
import java.util.Set;

import org.springframework.core.Ordered;

import com.tx.component.auth.exception.AuthContextInitException;
import com.tx.component.auth.model.Auth;

/**
 * 权限生成生命周期中的处理器接口逻辑定义<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthItemLoaderProcessor extends Ordered {
    
    /**
      * 加载后置处理方法<br/>
      * <功能详细描述>
      * @param authContext
      * @param beforeLoadAuthItemMapping
      * @param authItemSet
      * @return
      * @throws AuthContextInitException [参数说明]
      * 
      * @return Set<AuthItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Set<Auth> postProcessAfterLoad(
            Map<String, Auth> beforeLoadAuthItemMapping,
            Set<Auth> authItemSet) throws AuthContextInitException;
    
}
