/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月10日
 * <修改描述:>
 */
package com.tx.component.task.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.tx.component.task.annotations.Task;
import com.tx.component.task.context.TaskContextRegistry;
import com.tx.component.task.interfaces.TaskExecutor;
import com.tx.component.task.model.TaskDef;
import com.tx.core.exceptions.util.AssertUtils;

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
    
    /** 模块 */
    private String module;
    
    /** 任务执行拦截器工厂 */
    private TaskExecuteInterceptorFactory taskExecuteInterceptorFactory;
    
    /** 被代理的bean名称集合 */
    private Map<String, Map<Method, TaskDef>> proxyBeanNameMap = new ConcurrentHashMap<>();
    
    /** <默认构造函数> */
    public AnnotationTaskInterceptorProxyCreator() {
        super();
    }
    
    /** <默认构造函数> */
    public AnnotationTaskInterceptorProxyCreator(String module,
            TaskExecuteInterceptorFactory taskExecuteInterceptorFactory) {
        super();
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notNull(taskExecuteInterceptorFactory,
                "taskExecuteInterceptorFactory is null.");
        
        this.module = module;
        this.taskExecuteInterceptorFactory = taskExecuteInterceptorFactory;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        Object afterBean = super.postProcessAfterInitialization(bean, beanName);
        if (!proxyBeanNameMap.containsKey(beanName)) {
            return afterBean;
        }
        
        //如果是被代理的bean的代理后实例
        for (Entry<Method, TaskDef> entryTemp : proxyBeanNameMap.get(beanName)
                .entrySet()) {
            //注册入实体
            TaskContextRegistry.INSTANCE.registeTask(afterBean,
                    entryTemp.getKey(),
                    entryTemp.getValue());
        }
        
        return afterBean;
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
        if (StringUtils.isEmpty(beanName)) {
            //如果beanName is empty时，不会产生代理对象
            return DO_NOT_PROXY;
        }
        Set<Method> taskMethods = new HashSet<>();
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
                    taskMethods.add(method);
                }
            }
        });
        if (taskMethods.isEmpty()) {
            //如果taskMethod is empty时，不会产生代理对象
            return DO_NOT_PROXY;
        }
        
        Object bean = getBeanFactory().getBean(beanName);
        AssertUtils.notNull(bean, "bean is null.beanName:{}", beanName);
        
        //生成方法到任务的映射
        Map<Method, TaskDef> method2taskMap = createMethod2TaskMap(bean,
                beanName,
                taskMethods,
                beanClass);
        
        //标记便于登记
        proxyBeanNameMap.put(beanName, method2taskMap);//标记哪些bean的方法会被代理
        
        //构建拦截器
        TaskExecuteInterceptor interceptor = this.taskExecuteInterceptorFactory
                .newInterceptor(method2taskMap);
        Object[] interceptors = new Object[] { interceptor };
        
        return interceptors;
    }
    
    /**
     * 创建方法到任务的映射<br/>
     * <功能详细描述>
     * @param bean
     * @param beanClass
     * @param taskMethods
     * @return [参数说明]
     * 
     * @return Map<Method,TaskDef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Map<Method, TaskDef> createMethod2TaskMap(Object bean,
            String beanName, Set<Method> taskMethods, Class<?> beanClass) {
        AssertUtils.notNull(bean, "bean is null.");
        AssertUtils.notNull(bean, "bean is null.");
        AssertUtils.notEmpty(taskMethods, "taskMethods is null.");
        
        Map<Method, TaskDef> resMap = null;
        if (bean instanceof TaskExecutor) {
            resMap = doCreateMethod2TaskMapByTaskExecutor((TaskExecutor) bean,
                    beanName,
                    taskMethods,
                    beanClass);
        } else {
            resMap = doCreateMethod2TaskMap(bean,
                    beanName,
                    taskMethods,
                    beanClass);
        }
        return resMap;
    }
    
    /**
     * 创建方法对任务的映射<br/>
     * <功能详细描述>
     * @param bean
     * @param beanName
     * @param taskMethods
     * @param beanClass
     * @return [参数说明]
     * 
     * @return Map<Method,TaskDef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Map<Method, TaskDef> doCreateMethod2TaskMapByTaskExecutor(
            TaskExecutor bean, String beanName, Set<Method> taskMethods,
            Class<?> beanClass) {
        Map<Method, TaskDef> method2taskMap = new HashMap<>();
        for (Method method : taskMethods) {
            Task taskAnnotation = AnnotationUtils.getAnnotation(method,
                    Task.class);
            AssertUtils.notNull(taskAnnotation, "taskAnnotation is null.");
            
            TaskDef taskTemp = new TaskDef();
            
            taskTemp.setMethodName(method.getName());
            
            //默认使用annotation的值
            taskTemp.setParentCode(taskAnnotation.parentCode());
            taskTemp.setName(taskAnnotation.name());
            taskTemp.setRemark(taskAnnotation.remark());
            taskTemp.setOrderPriority(taskAnnotation.order());
            
            AssertUtils.notEmpty(bean.code(), "bean.code() is empty");
            //code,order使用executor实现值
            taskTemp.setCode(bean.code());
            taskTemp.setOrderPriority(bean.order());
            
            //如果executor有对应值，则进行覆盖
            if (!StringUtils.isBlank(bean.className())) {
                taskTemp.setClassName(bean.className());
            }
            if (!StringUtils.isBlank(bean.beanName())) {
                taskTemp.setClassName(bean.beanName());
            }
            if (!StringUtils.isBlank(bean.parentCode())) {
                taskTemp.setClassName(bean.parentCode());
            }
            if (!StringUtils.isBlank(bean.name())) {
                taskTemp.setClassName(bean.name());
            }
            if (!StringUtils.isBlank(bean.remark())) {
                taskTemp.setClassName(bean.remark());
            }
            
            //所属模块
            taskTemp.setModule(this.module);
            
            //如果为无参构造函数，并且没有父级任务，则可执行
            //如果有parentCode，则不能执行，//如果参数数量 == 0，则可执行，//如果参数数量 > 0,则不可执行
            taskTemp.setValid(true);
            taskTemp.setExecutable(
                    StringUtils.isEmpty(taskAnnotation.parentCode())
                            && ArrayUtils.isEmpty(method.getParameterTypes()));
            
            method2taskMap.put(method, taskTemp);
        }
        return method2taskMap;
    }
    
    /**
     * 创建方法对任务的映射<br/>
     * <功能详细描述>
     * @param bean
     * @param beanName
     * @param taskMethods
     * @param beanClass
     * @return [参数说明]
     * 
     * @return Map<Method,TaskDef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Map<Method, TaskDef> doCreateMethod2TaskMap(Object bean,
            String beanName, Set<Method> taskMethods, Class<?> beanClass) {
        Map<Method, TaskDef> method2taskMap = new HashMap<>();
        for (Method method : taskMethods) {
            Task taskAnnotation = AnnotationUtils.getAnnotation(method,
                    Task.class);
            AssertUtils.notNull(taskAnnotation, "taskAnnotation is null.");
            
            TaskDef taskTemp = new TaskDef();
            taskTemp.setBeanName(beanName);
            taskTemp.setMethodName(method.getName());
            
            AssertUtils.notEmpty(taskAnnotation.code(),
                    "taskAnnotation.code() is empty");
            taskTemp.setCode(taskAnnotation.code());
            
            taskTemp.setParentCode(taskAnnotation.parentCode());
            taskTemp.setName(taskAnnotation.name());
            taskTemp.setRemark(taskAnnotation.remark());
            taskTemp.setOrderPriority(taskAnnotation.order());
            
            //所属模块
            taskTemp.setModule(this.module);
            
            //获取被代理的类名
            taskTemp.setClassName(AopUtils.getTargetClass(bean).getName());
            
            //如果为无参构造函数，并且没有父级任务，则可执行
            //如果有parentCode，则不能执行，//如果参数数量 == 0，则可执行，//如果参数数量 > 0,则不可执行
            taskTemp.setValid(true);
            taskTemp.setExecutable(
                    StringUtils.isEmpty(taskAnnotation.parentCode())
                            && ArrayUtils.isEmpty(method.getParameterTypes()));
            
            method2taskMap.put(method, taskTemp);
        }
        return method2taskMap;
    }
}
