/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-27
 * <修改描述:>
 */
package com.tx.core.support.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml", "classpath:spring/beans-cache.xml",
        "classpath:spring/beans.xml" })
@ActiveProfiles("dev")
public class LazyEhCacheMapTest {
    
    @Resource(name = "testCache")
    private Ehcache testCache;
    
    private Map<String, String> testCacheMap;
    
    @Test
    public void testRuleConstants() {
        final Map<String, String> testMap = new HashMap<String, String>();
        testMap.put("wx", "wx");
        testMap.put("pqy", "pqy");
        testMap.put("zlk", "zlk");
        testCacheMap = new LazyEhCacheMap<String>(null, new EhCacheCache(testCache),
                new LazyCacheValueFactory<String, String>() {

                    /**
                     * @param key
                     * @return
                     */
                    @Override
                    public String find(String key) {
                        System.out.println("find from factory.");
                        return testMap.get(key);
                    }

                    /**
                     * @return
                     */
                    @Override
                    public Map<String, String> listMap() {
                        System.out.println("list from factory.");
                        return testMap;
                    }
                }, true);
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("please enter command \t r:remove \t p:put \t g:get \t c:clear \t s:size \t l:list:");
            String in = "";
            in = sc.next();
            String key = null;
            String value = null;
            switch (in) {
                case "r":
                    System.out.print("remove key:");
                    key = sc.next();
                    testCacheMap.remove(key);
                    break;
                case "p":
                    System.out.print("put key:");
                    key = sc.next();
                    System.out.print("put value:");
                    value = sc.next();
                    testCacheMap.put(key,value);
                    break;
                case "l":
                    System.out.println("list map:\n");
                    for(Entry<String, String> entryTemp : testCacheMap.entrySet()){
                        System.out.println("\t" + entryTemp.getKey() + " = " + entryTemp.getValue());
                    }
                    break;
                case "s":
                    System.out.println("list map size: " + testCacheMap.size());
                    break;
                case "c":
                    testCacheMap.clear();
                    break;
                case "g":
                    System.out.print("remove key:");
                    key = sc.next();
                    System.out.println("key:[" + key + "] = " + testCacheMap.get(key));;
                    break;
                default:
                    break;
            }
        }
    }
}
