/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月9日
 * <修改描述:>
 */
package com.tx.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;

import com.tx.core.exceptions.reflection.ReflectionException;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * Aop代理对象工具类<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年6月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AopTargetUtils {
    
    /**
     * 获取 目标对象
     * @param proxy 代理对象
     * @return 
     * @throws Exception
     */
    public static boolean isProxy(Object proxy) {
        if (AopUtils.isAopProxy(proxy)) {
            return true;//不是代理对象
        }
        
        if (Proxy.isProxyClass(proxy.getClass())
                || ClassUtils.isCglibProxy(proxy)) {
            return true;
        }
        return false;
    }
    
    /**
     * 获取 目标对象
     * @param proxy 代理对象
     * @return 
     * @throws Exception
     */
    public static Object getTarget(Object proxy) {        
        try {
            if(Proxy.isProxyClass(proxy.getClass())){
                return getJdkDynamicProxyTargetObject(proxy);
            }else if(ClassUtils.isCglibProxy(proxy)){
                return getCglibProxyTargetObject(proxy);
            }else{
                return proxy;
            }
        } catch (Exception e) {
            throw ExceptionWrapperUtils.wrapperSILException(ReflectionException.class,
                    e.getMessage());
        }
    }
    
    /**
      * 获取cglib代理对象实例
      * <功能详细描述>
      * @param proxy
      * @return
      * @throws Exception [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static Object getCglibProxyTargetObject(Object proxy)
            throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        
        Field advised = dynamicAdvisedInterceptor.getClass()
                .getDeclaredField("advised");
        advised.setAccessible(true);
        
        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource()
                .getTarget();
        
        return target;
    }
    
    /**
      * 获取jdk动态代理目标对象
      * <功能详细描述>
      * @param proxy
      * @return
      * @throws Exception [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy)
            throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        
        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource()
                .getTarget();
        
        return target;
    }
}
