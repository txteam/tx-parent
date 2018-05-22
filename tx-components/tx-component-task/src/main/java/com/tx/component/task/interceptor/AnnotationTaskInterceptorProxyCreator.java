/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月10日
 * <修改描述:>
 */
package com.tx.component.task.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.tx.component.task.annotations.Task;
import com.tx.component.task.interfaces.TaskExecutor;
import com.tx.component.task.model.TaskDef;
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
public class AnnotationTaskInterceptorProxyCreator
        extends AbstractAutoProxyCreator {
    
    /** 注释内容 */
    private static final long serialVersionUID = 5717953769982845279L;
    
    private final Set<String> targetSourcedBeans =
            Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>(16));
    
    private final Map<Object, Boolean> advisedBeans = new ConcurrentHashMap<Object, Boolean>(256);
    
    @Resource(name = "taskContext.taskExecuteInterceptorFactory")
    private TaskExecuteInterceptorFactory taskExecuteInterceptorFactory;
    
    /** <默认构造函数> */
    public AnnotationTaskInterceptorProxyCreator() {
        super();
    }
    
    /** 为对象生成代理对象 */
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        if (beanName != null && this.targetSourcedBeans.contains(beanName)) {
            return bean;
        }
        if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
            return bean;
        }
        if (isInfrastructureClass(bean.getClass()) || shouldSkip(bean.getClass(), beanName)) {
            this.advisedBeans.put(cacheKey, Boolean.FALSE);
            return bean;
        }

        // Create proxy if we have advice.
        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
        if (specificInterceptors != DO_NOT_PROXY) {
            this.advisedBeans.put(cacheKey, Boolean.TRUE);
            Object proxy = createProxy(
                    bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
            this.proxyTypes.put(cacheKey, proxy.getClass());
            return proxy;
        }

        this.advisedBeans.put(cacheKey, Boolean.FALSE);
        return bean;
    }
    
    /**
     * @param beanClass
     * @param beanName
     * @param customTargetSource
     * @return
     * @throws BeansException
     */
    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass,
            String beanName, TargetSource customTargetSource)
            throws BeansException {
        
        if (AopUtils.isAopProxy(beanClass)
                || this.nonAnnotatedClasses.containsKey(beanClass)) {
            return DO_NOT_PROXY;
        }
        
        Map<Method, TaskDef> method2taskMap = new HashMap<>();
        if(TaskExecutor.class.isAssignableFrom(beanClass)){
            customTargetSource.getTarget();
        }else{
            //如果非Executor的实现
            ReflectionUtils.doWithMethods(beanClass, new MethodCallback() {
                public void doWith(Method method)
                        throws IllegalArgumentException, IllegalAccessException {
                    if (!Modifier.isPublic(method.getModifiers())) {
                        //如果非public方法则直接返回
                        return;
                    }
                    
                    Task taskAnnotation = AnnotationUtils.getAnnotation(method,
                            Task.class);
                    if (taskAnnotation != null) {
                        TaskDef taskTemp = new TaskDef();
                        
                        taskTemp.setBeanName(beanName);
                        taskTemp.setClassName(beanClass.getName());
                        taskTemp.setMethodName(method.getName());
                        
                        taskTemp.setCode(taskAnnotation.code());
                        taskTemp.setParentCode(taskAnnotation.parentCode());
                        taskTemp.setName(taskAnnotation.name());
                        taskTemp.setRemark(taskAnnotation.remark());
                        taskTemp.setOrderPriority(taskAnnotation.order());
                        
                        //如果为无参构造函数，并且没有父级任务，则可执行
                        //如果有parentCode，则不能执行，//如果参数数量 == 0，则可执行，//如果参数数量 > 0,则不可执行
                        taskTemp.setValid(true);
                        taskTemp.setExecutable(StringUtils.isEmpty(taskAnnotation.parentCode())
                                && ArrayUtils.isEmpty(method.getParameterTypes()));
                        
                        method2taskMap.put(method, taskTemp);
                    }
                }
            });
        }
        
        final Set<Method> annotatedMethods = new LinkedHashSet<Method>(
                TxConstants.INITIAL_CONLLECTION_SIZE);
        
        
        
        
        if (!annotatedMethods.isEmpty()) {
            TaskExecuteInterceptor interceptor = this.taskExecuteInterceptorFactory
                    .newInterceptor(beanName,
                            beanClass.getName(),
                            annotatedMethods);
            Object[] interceptors = new Object[] { interceptor };
            return interceptors;
        }
        //如果不含有Task注解的的class无需进行代理
        this.nonAnnotatedClasses.put(beanClass, Boolean.TRUE);
        return DO_NOT_PROXY;
    }
    
    /**
     * @param proxyFactory
     */
    @Override
    protected void customizeProxyFactory(ProxyFactory proxyFactory) {
        proxyFactory.setProxyTargetClass(true);
    }
}
