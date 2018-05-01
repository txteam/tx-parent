/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月10日
 * <修改描述:>
 */
package com.tx.component.task.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.tx.component.task.annotations.Task;
import com.tx.core.TxConstants;

/**
 * 动态表业务层缓存创建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AnnotationTaskInterceptorProxyCreator extends AbstractAutoProxyCreator {
    
    /** 注释内容 */
    private static final long serialVersionUID = 5717953769982845279L;
    
    /** 没有注解的类 */
    private final Map<Class<?>, Boolean> nonAnnotatedClasses = new ConcurrentHashMap<Class<?>, Boolean>(64);
    
    @Resource(name = "taskContext.taskExecuteInterceptorFactory")
    private TaskExecuteInterceptorFactory taskExecuteInterceptorFactory;
    
    /** <默认构造函数> */
    public AnnotationTaskInterceptorProxyCreator() {
        super();
    }
    
    /**
     * @param beanClass
     * @param beanName
     * @param customTargetSource
     * @return
     * @throws BeansException
     */
    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName,
            TargetSource customTargetSource) throws BeansException {
        if (AopUtils.isAopProxy(beanClass) || this.nonAnnotatedClasses.containsKey(beanClass)) {
            return DO_NOT_PROXY;
        }
        
        final Set<Method> annotatedMethods = new LinkedHashSet<Method>(TxConstants.INITIAL_CONLLECTION_SIZE);
        ReflectionUtils.doWithMethods(beanClass, new MethodCallback() {
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                if (!Modifier.isPublic(method.getModifiers())) {
                    //如果非public方法则直接返回
                    return;
                }
                Task taskAnnotation = AnnotationUtils.getAnnotation(method, Task.class);
                if (taskAnnotation != null) {
                    annotatedMethods.add(method);
                }
            }
        });
        if (annotatedMethods.isEmpty()) {
            //如果不含有Task注解的的class无需进行代理
            this.nonAnnotatedClasses.put(beanClass, Boolean.TRUE);
            return DO_NOT_PROXY;
        } else {
            TaskExecuteInterceptor interceptor = this.taskExecuteInterceptorFactory.newInterceptor(beanName,
                    beanClass.getName(),
                    annotatedMethods);
            Object[] interceptors = new Object[] { interceptor };
            return interceptors;
        }
    }
    
    /**
     * @param proxyFactory
     */
    @Override
    protected void customizeProxyFactory(ProxyFactory proxyFactory) {
        proxyFactory.setProxyTargetClass(true);
    }
}
