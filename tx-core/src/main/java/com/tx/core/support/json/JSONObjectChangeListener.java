/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年12月21日
 * <修改描述:>
 */
package com.tx.core.support.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.alibaba.fastjson.JSONObject;
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
public abstract class JSONObjectChangeListener implements MethodInterceptor {
    
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
    public static JSONObject newProxy(JSONObjectChangeListener listener) {
        AssertUtils.notNull(listener, "listener is null.");
        
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(JSONObject.class);
        enhancer.setCallback(listener);
        
        JSONObject targetProxy = (JSONObject) enhancer.create();
        
        return targetProxy;
    }
    
    /** 代理对象 */
    protected JSONObject jsonObject;
    
    /** <默认构造函数> */
    public JSONObjectChangeListener() {
        super();
    }
    
    /** <默认构造函数> */
    public JSONObjectChangeListener(JSONObject jsonObject) {
        super();
        this.jsonObject = jsonObject;
        
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
        AssertUtils.notNull(this.jsonObject, "jsonObject is null.");
        
        Object ret;
        try {
            ret = method.invoke(this.jsonObject, args);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new SILException("jsonobject proxy invoke exception.", e);
        }
        if (method.getName().startsWith("put")
                || method.getName().startsWith("remove")
                || method.getName().startsWith("compute")
                || method.getName().startsWith("clear")) {
            callbackOnChange();
        }
        return ret;
    }
    
    public abstract void callbackOnChange();
}
