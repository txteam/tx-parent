/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.component.auth.context.impl;

import java.util.Set;

import com.tx.component.auth.context.AuthLoader;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.service.AuthItemImplService;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-4-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DBAuthLoad implements AuthLoader{
    
    private AuthItemImplService authItemImplService;

    /**
     * @return
     */
    @Override
    public Set<AuthItem> loadAuthItems() {
        authItemImplService.queryAuthItemImplList();
        return null;
    }
}
