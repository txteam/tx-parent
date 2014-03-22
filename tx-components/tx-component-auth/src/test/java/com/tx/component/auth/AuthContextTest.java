/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-4
 * <修改描述:>
 */
package com.tx.component.auth;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.auth.context.AuthContext;
import com.tx.component.auth.context.AuthSessionContext;
import com.tx.component.auth.model.AuthItemRef;

/**
 * 权限容器测试
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-aop.xml",
        "classpath:spring/beans-cache.xml", 
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml", 
        "classpath:spring/beans-auth.xml" })
public class AuthContextTest {
    
    @Resource(name = "authContext")
    private AuthContext authContext;
    
    @Test
    public void testGetAllAuthItemList() {
        Assert.assertNotNull(authContext.getAllAuthItemList());
    }
    
    @Test
    public void testGetAllAuthItemMapping() {
        Assert.assertNotNull(authContext.getAllAuthItemMapping());
    }
    
    @Test
    public void testGetAuthItemFromContextById() {
        Assert.assertNotNull(authContext.getAuthItemFromContextById("system_config_manange"));
    }
    
    @Test
    public void testGetContext() {
        Assert.assertNotNull(AuthContext.getContext());
    }
    
    @Test
    public void testRegisteAuth() {
        Assert.assertNotNull(authContext.registeAuth("test1",
                "测试权限1",
                "测试权限1",
                AuthConstant.AUTHTYPE_OPERATE,
                true));
        Assert.assertNotNull(authContext.registeAuth("test2",
                "测试权限2",
                "测试权限2",
                AuthConstant.AUTHTYPE_OPERATE,
                true));
    }
    
    @Test
    public void saveAuthItemOfAuthRefIdList1() {
        List<String> refIdList = new ArrayList<String>();
        refIdList.add("111");
        refIdList.add("222");
        refIdList.add("333");
        
        AuthSessionContext.bindCurrentSessionToThread(new MockHttpServletRequest(),
                new MockHttpServletResponse());
        AuthSessionContext.putOperatorIdToSession("123456");
        authContext.saveAuthItemOfAuthRefIdList(AuthConstant.AUTHREFTYPE_OPERATOR,
                "test1",
                refIdList);
        
        List<AuthItemRef> resList1 = authContext.queryAuthItemRefListByAuthRefTypeAndRefId(AuthConstant.AUTHREFTYPE_OPERATOR,
                "111");
        Assert.assertTrue(resList1.size() > 0);
        List<AuthItemRef> resList2 = authContext.queryAuthItemRefListByAuthRefTypeAndAuthItemId(AuthConstant.AUTHREFTYPE_OPERATOR,
                "test1");
        Assert.assertTrue(resList2.size() > 0);
        
        AuthSessionContext.removeCurrentSessionFromThread();
    }
    
    @Test
    public void saveAuthItemOfAuthRefIdList2() {
        List<String> refIdList = new ArrayList<String>();
        refIdList.add("1111");
        refIdList.add("2222");
        refIdList.add("3333");
        
        AuthSessionContext.bindCurrentSessionToThread(new MockHttpServletRequest(),
                new MockHttpServletResponse());
        AuthSessionContext.putOperatorIdToSession("123456");
        
        authContext.saveAuthItemOfAuthRefIdList(AuthConstant.AUTHREFTYPE_OPERATOR,
                "test2",
                refIdList);
        
        List<AuthItemRef> resList1 = authContext.queryAuthItemRefListByAuthRefTypeAndRefId(AuthConstant.AUTHREFTYPE_OPERATOR,
                "1111");
        Assert.assertTrue(resList1.size() > 0);
        List<AuthItemRef> resList2 = authContext.queryAuthItemRefListByAuthRefTypeAndAuthItemId(AuthConstant.AUTHREFTYPE_OPERATOR,
                "test2");
        Assert.assertTrue(resList2.size() > 0);
        
        AuthSessionContext.removeCurrentSessionFromThread();
    }
}
