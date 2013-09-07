/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-9-7
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import junit.framework.Assert;

import net.sf.ehcache.CacheManager;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.basicdata.testmodel.AuthItemImpl;
import com.tx.core.util.UUIDUtils;

/**
 * 默认的数据执行器测试
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-9-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/beans-aop.xml",
        "classpath:spring/beans-cache.xml", "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml" })
public class DefaultBasicDataExecutorTest {
    
    @Resource(name = "ehcacheManager")
    private CacheManager cacheManager;
    
    @Resource(name = "dataSource")
    private DataSource dataSource;
    
    private Dialect dialect = new H2Dialect();
    
    private DefaultBasicDataExecutor<AuthItemImpl> executor;
    
    @Before
    public void before() {
        executor = new DefaultBasicDataExecutor<AuthItemImpl>(
                AuthItemImpl.class, true, dialect, dataSource, cacheManager);
    }
    
    @Test
    public void testInsert() {
        String id = UUIDUtils.generateUUID();
        
        AuthItemImpl newAuth = new AuthItemImpl();
        newAuth.setAuthType("test");
        newAuth.setConfigAble(true);
        newAuth.setDescription("desc");
        newAuth.setEditAble(true);
        newAuth.setId(id);
        newAuth.setName("authName");
        newAuth.setSystemId("system");
        
        executor.insert(newAuth);
        
        Assert.assertTrue(executor.find(id) != null);
    }
    
    @Test
    public void testDelete() {
        
        AuthItemImpl newAuth1 = new AuthItemImpl();
        newAuth1.setAuthType("test");
        newAuth1.setConfigAble(true);
        newAuth1.setDescription("desc");
        newAuth1.setEditAble(true);
        newAuth1.setId("test_auth");
        newAuth1.setName("authName");
        newAuth1.setSystemId("system");
        
        executor.insert(newAuth1);
        
        executor.delete("test_auth");
        
        Assert.assertTrue(executor.find("test_auth") == null);
    }
    
    @Test
    public void testUpdate() {
        AuthItemImpl newAuth1 = new AuthItemImpl();
        newAuth1.setAuthType("test");
        newAuth1.setConfigAble(true);
        newAuth1.setDescription("desc");
        newAuth1.setEditAble(true);
        newAuth1.setId("test_auth_forupdate");
        newAuth1.setName("test_auth_forupdate");
        newAuth1.setSystemId("system");
        
        executor.insert(newAuth1);
        
        Map<String, Object> updateParams = new HashMap<String, Object>();
        updateParams.put("id", "test_auth_forupdate");
        updateParams.put("name", "newName");
        
        executor.update(updateParams);
        
        Assert.assertTrue("newName".equals(executor.find("test_auth_forupdate").getName()));
    }
    
    private AuthItemImpl createAuth(String id){
        AuthItemImpl newAuth1 = new AuthItemImpl();
        newAuth1.setAuthType("test");
        newAuth1.setConfigAble(true);
        newAuth1.setDescription("desc");
        newAuth1.setEditAble(true);
        newAuth1.setId(id);
        newAuth1.setName("authName_" + id);
        newAuth1.setSystemId("system");
        
        return newAuth1;
    }
    
    @Test
    public void testQuery() {
        for(int i = 0 ; i < 500 ; i++){
            try {
                AuthItemImpl authTemp = createAuth(String.valueOf((int)(Math.random() * 10000000)));
                executor.insert(authTemp);
            } catch (Exception e) {
                continue;
            }
        }
        
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("name", "authName_");
        queryParams.put("viewAble", true);
        queryParams.put("editAble", true);
        
        Assert.assertTrue(executor.query(queryParams).size() > 0);
    }
    
    @Test
    public void testQueryPaged() {
        for(int i = 0 ; i < 500 ; i++){
            try {
                AuthItemImpl authTemp = createAuth(String.valueOf((int)(Math.random() * 10000000)));
                executor.insert(authTemp);
            } catch (Exception e) {
                continue;
            }
        }
        
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("name", "authName_");
        queryParams.put("viewAble", true);
        queryParams.put("editAble", true);
        
        Assert.assertTrue(executor.queryPagedList(queryParams, 1, 10).getList().size() > 0);
    }
    
}
