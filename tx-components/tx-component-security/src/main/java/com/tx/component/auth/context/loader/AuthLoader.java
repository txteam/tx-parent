/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-13
 * <修改描述:>
 */
package com.tx.component.auth.context.loader;

import java.util.Map;
import java.util.Set;

import org.springframework.core.Ordered;

import com.tx.component.auth.model.Auth;

/**
 * 权限加载器<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthLoader extends Ordered {
    
    /**
      * 加载系统的所有权限项
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<AuthItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Set<Auth> loadAuthItems(Map<String, Auth> authItemMapping);
}
