/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年12月21日
 * <修改描述:>
 */
package com.tx.component.command.context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 对象代理器<br/>
 *      ObjectChangeListenProxy
 * 
 * @author  Administrator
 * @version  [版本号, 2016年12月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ChangeListener<T> implements MethodInterceptor {
    
    /** 代理对象类型 */
    private final Class<T> targetType;
    
    /** 代理对象 */
    private final T targetObject;
    
    /** 对象是否发生过改变 */
    private boolean changed = false;
    
    /**  */
    private Collection<String> ignoreMethodNames;
    
    /** <默认构造函数> */
    ChangeListener(T targetObject, Class<T> targetType,
            Collection<String> ignoreMethodNames) {
        super();
        AssertUtils.notNull(targetType, "targetType is null");
        AssertUtils.notNull(targetObject, "targetObject is null");
        
        this.targetType = targetType;
        this.targetObject = targetObject;
        
        this.ignoreMethodNames = ignoreMethodNames;
    }
    
    /** <默认构造函数> */
    ChangeListener(T targetObject, Class<T> targetType) {
        super();
        AssertUtils.notNull(targetType, "targetType is null");
        AssertUtils.notNull(targetObject, "targetObject is null");
        
        this.targetType = targetType;
        this.targetObject = targetObject;
    }
    
    /**
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args,
            MethodProxy methodProxy) {
        if (method.getName().startsWith("set")) {
            if (!CollectionUtils.isEmpty(this.ignoreMethodNames)) {
                if (!this.ignoreMethodNames.contains(method.getName())) {
                    this.changed = true;
                }
            } else {
                this.changed = true;
            }
        }
        Object ret = null;
        try {
            if ("getTargetType".equals(method.getName())
                    || "getTargetObject".equals(method.getName())
                    || "isChanged".equals(method.getName())) {
                ret = method.invoke(this, args);
            } else {
                ret = method.invoke(this.targetObject, args);
            }
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new SILException("object proxy invoke exception.", e);
        }
        return ret;
    }
    
    /**
     * @return 返回 targetType
     */
    public Class<T> getTargetType() {
        return targetType;
    }
    
    /**
     * @return 返回 targetObject
     */
    public T getTargetObject() {
        return targetObject;
    }
    
    /**
     * @return
     */
    public boolean isChanged() {
        return this.changed;
    }
}
