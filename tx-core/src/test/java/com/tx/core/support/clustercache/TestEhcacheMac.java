///*
// * 描          述:  <描述>
// * 修  改   人:  brady
// * 修改时间:  2013-9-3
// * <修改描述:>
// */
//package com.tx.core.support.clustercache;
//
//import java.net.URL;
//import java.util.Scanner;
//import java.util.WeakHashMap;
//
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;
//
//
// /**
//  * <功能简述>
//  * <功能详细描述>
//  * 
//  * @author  brady
//  * @version  [版本号, 2013-9-3]
//  * @see  [相关类/方法]
//  * @since  [产品/模块版本]
//  */
//public class TestEhcacheMac {
//    
//    public static  void test(String ehcacheCfgPath) throws Exception{
//        Scanner scan = new Scanner(System.in);
//        
//        //"com/tx/core/support/clustercache/ehcache_mac1.xml"
//        URL url = Mac1.class.getClassLoader().getResource(ehcacheCfgPath);
//        CacheManager cacheManager = CacheManager.create(url);
//        if(!cacheManager.cacheExists("testCache")){
//            System.out.println("cache create.");
//            cacheManager.addCache("testCache");
//        }
//        Cache cache = cacheManager.getCache("testCache");
//        WeakHashMap<String, Element> map = new WeakHashMap<String, Element>();
//        
//        boolean scanFlag = true;
//        while(scanFlag){
//            System.out.print("entry command(end,put,get):");
//            String in = scan.next();
//            if("end".equals(in)){
//                break;
//            }else if("put".equals(in)){
//                System.out.print("entry key:");
//                String key = scan.next();
//                System.out.print("entry value:");
//                String value = scan.next();
//                
//                Element element = new Element(key, "null".equals(value) ? null : value);
//                cache.put(element);
//                
//                map.put(key, element);
//            }else if("get".equals(in)){
//                System.out.print("entry key:");
//                String key = scan.next();
//                
//                System.out.println("cache current size:" + cache.getSize());
//                
//                Element element = cache.get(key);
//                
//                if(element != null){
//                    System.out.println("element: key :" + key + "value:" + element == null ? "" : element.getValue() );
//                }else{
//                    System.out.println("element not exist.");
//                }
//                
//            }else if("remove".equals(in)){
//                System.out.print("entry key:");
//                String key = scan.next();
//                
//                cache.remove(key);
//            }else{
//                System.out.println("error command.");
//            }
//        }
//        
//        scan.close();
//    }
//}
