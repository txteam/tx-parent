/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-23
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tx.component.auth.model.AuthItemImpl;
import com.tx.component.auth.model.AuthItemRefImpl;
import com.tx.component.auth.service.AuthItemImplService;
import com.tx.component.auth.service.AuthItemRefImplService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 权限容器持久化逻辑<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthContextPersister {
    
    /** 权限项业务层 */
    @Resource(name = "authItemImplService")
    private AuthItemImplService authItemService;
    
    /** 权限引用项业务层 */
    @Resource(name = "authItemRefImplService")
    private AuthItemRefImplService authItemRefService;
    
    /**
      * 根据权限类型到引用ID的映射查询对应权限项目引用集合<br/>
      *<功能详细描述>
      * @param refType2RefIdMapping
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected List<AuthItemRefImpl> queryAuthItemRefListByRefType2RefIdMapping(
            Map<String, String> refType2RefIdMapping) {
        AssertUtils.notEmpty(refType2RefIdMapping, "refType2RefIdMapping is empty.");
        
        List<AuthItemRefImpl> refImplList = this.authItemRefService.queryAuthItemRefListByRefType2RefIdMapping(refType2RefIdMapping);
        return refImplList;
    }
    
    /**
      * 持久化权限项<br/>
      *<功能详细描述>
      * @param authItemImpl
      * @return [参数说明]
      * 
      * @return AuthItemImpl [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected AuthItemImpl insertAuthItemImpl(final AuthItemImpl authItemImpl) {
        AssertUtils.notNull(authItemImpl, "authItemImpl is null");
        AssertUtils.notEmpty(authItemImpl.getAuthType(), "authType is empty.");
        
        //持久化对应的权限项到数据库中
        this.authItemService.insertAuthItemImpl(authItemImpl);

        return authItemImpl;
    }
}
