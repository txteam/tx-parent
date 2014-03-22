/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.transation;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 规则会话容器<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleSessionContext {
    
    private static RuleSessionContext context = new RuleSessionContext();
    
    private static Logger logger = LoggerFactory.getLogger(RuleSessionContext.class);
    
    /**
     * 全局容器绑定
     */
    private static ThreadLocal<Map<String, Object>> global = new ThreadLocal<Map<String, Object>>() {
        
        /**
         * @return
         */
        @Override
        protected Map<String, Object> initialValue() {
            return null;
        }
    };
    
    /** 私有化构造函数 */
    private RuleSessionContext(){
    }
    
    /**
      * 返回context实例
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return RuleSessionContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static RuleSessionContext getContext(){
        return context;
    }
    
    /**
     *  包可见方法
      * 规则回话事务是否已经开启
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    static boolean isOpen(){
        return global.get() != null;
    }
    
    /**
      *  包可见方法
      * 打开方法规则会话容器<br/>
      *     1、初始化容器<br/>
      *     
      * @param globals [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    static void open(){
        logger.debug("rule session tranaction open.");
        global.set(new HashMap<String, Object>());
    }
    
    /**
      * 包可见方法
      * 关闭方法规则会话容器
      *     1、移除方法线程变量 [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    static void close(){
        logger.debug("rule session tranaction close.");
        global.set(null);//正常来说这个是不必要的
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
    public void setGlobal(String key, Object object) {
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
    public void setGlobals(Map<? extends String,? extends Object> globals) {
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
    public Object getGlobal(String key) {
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
    public Map<String, Object> getGlobals() {
        return global.get();
    }
}
