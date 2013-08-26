/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.component.auth.context.loader.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.component.auth.context.AuthTypeItemContext;
import com.tx.component.auth.context.loader.AuthLoader;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.component.auth.service.AuthItemImplService;


 /**
  * 权限加载器，从DB中进行加载<br/>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-4-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Component("dbAuthLoad")
public class DBAuthLoader implements AuthLoader{
    
    @Resource(name = "authItemImplService")
    private AuthItemImplService authItemService;
    
    /** 加载顺序 */
    private int order = Integer.MAX_VALUE;

    /**
     * 从数据库中加载权限项
     * @return
     */
    @Override
    public Set<AuthItem> loadAuthItems() {
        Set<AuthItem> resSet = new HashSet<AuthItem>();
        List<AuthItemImpl> authItemImplList = this.authItemService.queryAllAuthItemList();
        if(authItemImplList == null){
            return resSet;
        }
        
        for(AuthItemImpl authItemTemp : authItemImplList){
            AuthTypeItemContext.getContext().registeAuthTypeItem(authItemTemp.getAuthType());
            resSet.add(authItemTemp);
        }
        return resSet;
    }

    /**
     * @return
     */
    @Override
    public int getOrder() {
        return order;
    }
    
}
