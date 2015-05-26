/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-4
 * <修改描述:>
 */
package com.tx.core.support.ehcache;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试ehcache相关特性
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/beans-cache.xml" })
public class EhcacheTest {
    
    @Resource(name = "cache")
    private Ehcache cache;
    
    /**
     * 单例CacheManager 创建
     */
    @Test
    public void testCreateSingleCacheManager() {
        // Create a singleton CacheManager using defaults   
        System.out.println("Create a singleton CacheManager using defaults");
        // CacheManager.create();   
        System.out.println("CacheManager.create()     :="
                + CacheManager.getInstance());
        System.out.println("cacheNames length := "
                + CacheManager.getInstance().getCacheNames().length);
        CacheManager.getInstance().shutdown();
        System.out.println("=======================================");
        
        // Create a singleton CacheManager using a configuration file   
        System.out.println("Create a singleton CacheManager using a configuration file");
        CacheManager singletonManager2 = CacheManager.create("src/main/resources/ehcache.xml");
        System.out.println("CacheManager.create(file) :=" + singletonManager2);
        System.out.println("cacheNames length := "
                + singletonManager2.getCacheNames().length);
        System.out.println("CacheManager.getInstance() == singletonManager2 :: "
                + (CacheManager.getInstance() == singletonManager2));
        singletonManager2.shutdown();
        // CacheManager.getInstance().shutdown();   
        
        System.out.println("=======================================");
        
        // Create a singleton CacheManager from a configuration resource in the   
        // classpath.   
        URL configurl = Thread.currentThread()
                .getContextClassLoader()
                .getResource("ehcache.xml");
        CacheManager singletonManager3 = CacheManager.create(configurl);
        System.out.println("CacheManager.create(url)  :=" + singletonManager3);
        
        String[] cacheNames = singletonManager3.getCacheNames();
        System.out.println("cacheNames length := " + cacheNames.length);
        for (String name : cacheNames) {
            System.out.println("name := " + name);
        }
        System.out.println("CacheManager.getInstance() == singletonManager3 :: "
                + (CacheManager.getInstance() == singletonManager3));
        singletonManager3.shutdown();
        // CacheManager.getInstance().shutdown();   
        System.out.println("=======================================");
    }
    
    /**
      * 从这个例子中可以看出ehcache中缓存对象，仅仅是对原对象的引用<br/>
      * 持久化的情况主要发生在后续
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
	@Test
    public void testCacheWriteAfterUpdate() {
        String key = "testKey1";
        Map<String, String> value = new HashMap<String, String>();
        value.put("1", "1");
        value.put("11", "11");
        
        cache.put(new Element(key, value));
        
        //put after update srcValue
        value.put("111", "111");
        value.put("1", "2");
        
        System.out.println(cache.get(key));
        Map<String, String> cacheMap = (Map<String, String>) cache.get(key)
                .getValue();
        System.out.println(value == cacheMap);
        System.out.println(cacheMap.size());
        System.out.println(cacheMap.get("1"));
        //Assert.assertEquals(value.get("1"), actual)
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
    public void testCacheWriteAfterUpdateWithListener() {
        String key = "testKey1";
        Map<String, String> value = new HashMap<String, String>();
        value.put("1", "1");
        value.put("11", "11");
        
        cache.put(new Element(key, value));
        cache.addPropertyChangeListener(new PropertyChangeListener() {
            
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("PropertyChangeEvent:" + evt);
            }
        });
        
        //put after update srcValue
        value.put("111", "111");
        value.put("1", "2");
        
        System.out.println(cache.get(key));
        Map<String, String> cacheMap = (Map<String, String>) cache.get(key)
                .getValue();
        System.out.println(value == cacheMap);
        System.out.println(cacheMap.size());
        System.out.println(cacheMap.get("1"));
        //Assert.assertEquals(value.get("1"), actual)
        
        cache.put(new Element(key, value));
        cache.put(new Element(key, new ArrayList()));
        
        System.out.println("testEnd");
    }
    
    public void testSimpleEhcacheImpl() {
        CacheManager cm = CacheManager.getInstance();
        
        cm.addCache("test");
    }
}
