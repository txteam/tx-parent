/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.util.HashMap;
import java.util.Map;

/**
 * 规则会话容器<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodRuleSessionContext {
    
    /**
     * 全局容器绑定
     */
    private static ThreadLocal<Map<String, Object>> global = new ThreadLocal<Map<String, Object>>() {
        
        /**
         * @return
         */
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };
    
    /**
      * 打开方法规则会话容器<br/>
      *     1、初始化容器<br/>
      *     
      * @param globals [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void open(Map<String, Object> globals){
        global.set(globals);
    }
    
    /**
      * 关闭方法规则会话容器
      *     1、移除方法线程变量 [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void close(){
        global.remove();
    }
    
    /**
      * 设置全局变量<br/>
      * 
      * @param key
      * @param object [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void setGlobal(String key, Object object) {
        global.get().put(key, object);
    }
    
    /**
     * 设置全局变量<br/>
     * 
     * @param globals [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void setGlobals(Map<String, Object> globals) {
        global.get().putAll(globals);
    }
    
    /**
      * 获取全局变量<br/>
      * <功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static Object getGlobal(String key) {
        return global.get().get(key);
    }
    
    /**
      * 获取所有的全局变量
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static Map<String, Object> getGlobals() {
        return global.get();
    }
}
