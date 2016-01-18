/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月31日
 * <修改描述:>
 */
package com.tx.core.support.methodinvoke.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.tx.core.support.methodinvoke.annotation.MISInitBinder;
import com.tx.core.support.methodinvoke.annotation.MISModelAttribute;
import com.tx.core.support.methodinvoke.annotation.MISSessionAttributes;


 /**
  * 处理器方法解析器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2015年12月31日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class HandlerMethodResolver<A extends Annotation,M extends Annotation> {
    
    /** 类型的注解类型 */
    private Class<A> typeAnnotationType;
    
    /** 方法的注解类型 */
    private Class<M> methodAnnotationType;
    
    /** 类型上的注解实例 */
    private A typeLevelMapping;
    
    /** 处理方法方法实例集合 */
    private final Set<Method> handlerMethods = new LinkedHashSet<Method>();

    /** 初始化的参数转换类方法实例集合 */
    private final Set<Method> initBinderMethods = new LinkedHashSet<Method>();

    /** modelAttribute处理方法实例集合 */
    private final Set<Method> modelAttributeMethods = new LinkedHashSet<Method>();

    /** 是否含有会话属性 */
    private boolean sessionAttributesFound;

    /** 会话属性名称集合 */
    private final Set<String> sessionAttributeNames = new HashSet<String>();

    /** 会话属性类型集合 */
    private final Set<Class<?>> sessionAttributeTypes = new HashSet<Class<?>>();

    // using a ConcurrentHashMap as a Set
    private final Map<String, Boolean> actualSessionAttributeNames = new ConcurrentHashMap<String, Boolean>(4);

    /**
     * Initialize a new HandlerMethodResolver for the specified handler type.
     * @param handlerType the handler class to introspect
     */
    public void init(final Class<?> handlerType) {
        Set<Class<?>> handlerTypes = new LinkedHashSet<Class<?>>();
        Class<?> specificHandlerType = null;
        //如果不是代理类方法
        if (!Proxy.isProxyClass(handlerType)) {
            handlerTypes.add(handlerType);
            specificHandlerType = handlerType;
        }
        //获取代理类方法的所有接口
        handlerTypes.addAll(Arrays.asList(handlerType.getInterfaces()));
        for (Class<?> currentHandlerType : handlerTypes) {
            final Class<?> targetClass = (specificHandlerType != null ? specificHandlerType : currentHandlerType);
            ReflectionUtils.doWithMethods(currentHandlerType, new ReflectionUtils.MethodCallback() {
                public void doWith(Method method) {
                    Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
                    Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
                    if (isHandlerMethod(specificMethod) &&
                            (bridgedMethod == specificMethod || !isHandlerMethod(bridgedMethod))) {
                        handlerMethods.add(specificMethod);
                    }
                    else if (isInitBinderMethod(specificMethod) &&
                            (bridgedMethod == specificMethod || !isInitBinderMethod(bridgedMethod))) {
                        initBinderMethods.add(specificMethod);
                    }
                    else if (isModelAttributeMethod(specificMethod) &&
                            (bridgedMethod == specificMethod || !isModelAttributeMethod(bridgedMethod))) {
                        modelAttributeMethods.add(specificMethod);
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
        this.typeLevelMapping = AnnotationUtils.findAnnotation(handlerType, typeAnnotationType);
        MISSessionAttributes sessionAttributes = AnnotationUtils.findAnnotation(handlerType, MISSessionAttributes.class);
        this.sessionAttributesFound = (sessionAttributes != null);
        if (this.sessionAttributesFound) {
            this.sessionAttributeNames.addAll(Arrays.asList(sessionAttributes.value()));
            this.sessionAttributeTypes.addAll(Arrays.asList(sessionAttributes.types()));
        }
    }

    /**
      * 是否是含有指定注解的方法<br/>
      * <功能详细描述>
      * @param method
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected boolean isHandlerMethod(Method method) {
        return AnnotationUtils.findAnnotation(method, methodAnnotationType) != null;
    }

    /**
      * 是否初始化参数方法<br/>
      * <功能详细描述>
      * @param method
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected boolean isInitBinderMethod(Method method) {
        return AnnotationUtils.findAnnotation(method, MISInitBinder.class) != null;
    }

    /**
      * 是否含有ModelAtrribute的注解<br/>
      * <功能详细描述>
      * @param method
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected boolean isModelAttributeMethod(Method method) {
        return AnnotationUtils.findAnnotation(method, MISModelAttribute.class) != null;
    }

    /**
      * 是否含有处理器方法<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public final boolean hasHandlerMethods() {
        return !this.handlerMethods.isEmpty();
    }

    /**
      * 获取其中含有的处理器方法<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<Method> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public final Set<Method> getHandlerMethods() {
        return this.handlerMethods;
    }

    /**
      * 参数初始化绑定方法<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<Method> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public final Set<Method> getInitBinderMethods() {
        return this.initBinderMethods;
    }

    /**
      * 获取ModelAttribute方法集合
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<Method> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public final Set<Method> getModelAttributeMethods() {
        return this.modelAttributeMethods;
    }

    /**
      * 是否含有会话属性<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean hasSessionAttributes() {
        return this.sessionAttributesFound;
    }

    /**
      * 是否含有会话属性<br/>
      * <功能详细描述>
      * @param attrName
      * @param attrType
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isSessionAttribute(String attrName, Class<?> attrType) {
        if (this.sessionAttributeNames.contains(attrName) || this.sessionAttributeTypes.contains(attrType)) {
            this.actualSessionAttributeNames.put(attrName, Boolean.TRUE);
            return true;
        }
        else {
            return false;
        }
    }

    /**
      * 获取所有的会话属性Key值集合
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Set<String> getActualSessionAttributeNames() {
        return this.actualSessionAttributeNames.keySet();
    }
    
    /**
      * 类上是否含有类型映射<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean hasTypeLevelMapping() {
        return (this.typeLevelMapping != null);
    }

    /**
     * 获取类型映射注解实例<br/> 
     */
    public A getTypeLevelMapping() {
        return this.typeLevelMapping;
    }
}
