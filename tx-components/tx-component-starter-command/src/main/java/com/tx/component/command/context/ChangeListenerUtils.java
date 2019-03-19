/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年12月21日
 * <修改描述:>
 */
package com.tx.component.command.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 对象代理工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年12月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ChangeListenerUtils {
    
    /**
     * 构建监听器<br/>
     * <功能详细描述>
     * @param object
     * @param type
     * @return [参数说明]
     * 
     * @return ChangeListener<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <T> ChangeListener<T> buildListener(T object, Class<T> type,
            Collection<String> ignoreMethodNames) {
        if (object == null) {
            return null;
        }
        AssertUtils.notNull(type, "type is null.");
        
        ChangeListener<T> cl = new ChangeListener<T>(object, type,
                ignoreMethodNames);
        return cl;
    }
    
    /**
    * 构建监听器<br/>
    * <功能详细描述>
    * @param object
    * @param type
    * @return [参数说明]
    * 
    * @return ChangeListener<T> [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    public static <T> List<ChangeListener<T>> buildListenerList(
            List<T> objectList, Class<T> type,
            Collection<String> ignoreMethodNames) {
        if (objectList == null) {
            return null;
        }
        List<ChangeListener<T>> clList = new ArrayList<>();
        if (objectList.size() == 0) {
            return clList;
        }
        AssertUtils.notNull(type, "type is null.");
        
        for (T object : objectList) {
            ChangeListener<T> cl = new ChangeListener<T>(object, type,
                    ignoreMethodNames);
            clList.add(cl);
        }
        return clList;
    }
    
    /**
      * 构建监听器<br/>
      * <功能详细描述>
      * @param object
      * @param type
      * @return [参数说明]
      * 
      * @return ChangeListener<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> ChangeListener<T> buildListener(T object, Class<T> type) {
        if (object == null) {
            return null;
        }
        AssertUtils.notNull(type, "type is null.");
        
        ChangeListener<T> cl = new ChangeListener<T>(object, type);
        return cl;
    }
    
    /**
     * 构建监听器<br/>
     * <功能详细描述>
     * @param object
     * @param type
     * @return [参数说明]
     * 
     * @return ChangeListener<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <T> List<ChangeListener<T>> buildListenerList(
            List<T> objectList, Class<T> type) {
        if (objectList == null) {
            return null;
        }
        List<ChangeListener<T>> clList = new ArrayList<>();
        if (objectList.size() == 0) {
            return clList;
        }
        AssertUtils.notNull(type, "type is null.");
        
        for (T object : objectList) {
            ChangeListener<T> cl = new ChangeListener<T>(object, type);
            clList.add(cl);
        }
        return clList;
    }
    
    /**
      * 新生成对象代理<br/>
      * <功能详细描述>
      * @param type
      * @param target
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> T newProxy(ChangeListener<T> listener) {
        if (listener == null) {
            return null;
        }
        AssertUtils.notNull(listener, "proxy is null.");
        AssertUtils.notNull(listener.getTargetType(),
                "proxy.targetType is null.");
        AssertUtils.notNull(listener.getTargetType(),
                "proxy.targetType is null.");
        
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(listener.getTargetType());
        enhancer.setCallback(listener);
        @SuppressWarnings("unchecked")
        T targetProxy = (T) enhancer.create();
        
        return targetProxy;
    }
    
    /**
     * 新生成对象代理<br/>
     * <功能详细描述>
     * @param type
     * @param target
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <T> List<T> newProxyList(
            List<ChangeListener<T>> listenerList) {
        if (listenerList == null) {
            return null;
        }
        List<T> objectProxyList = new ArrayList<>();
        if (listenerList.size() == 0) {
            return objectProxyList;
        }
        
        for (ChangeListener<T> listener : listenerList) {
            T proxy = newProxy(listener);
            objectProxyList.add(proxy);
        }
        return objectProxyList;
    }
    
    /**
      * 获取改变了的列表<br/>
      * <功能详细描述>
      * @param listenerList
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> List<T> getChangedList(
            List<ChangeListener<T>> listenerList) {
        if (listenerList == null) {
            return null;
        }
        List<T> objectList = new ArrayList<>();
        if (listenerList.size() == 0) {
            return objectList;
        }
        
        for (ChangeListener<T> listener : listenerList) {
            if (listener.isChanged()) {
                objectList.add(listener.getTargetObject());
            }
        }
        return objectList;
    }
    
}
