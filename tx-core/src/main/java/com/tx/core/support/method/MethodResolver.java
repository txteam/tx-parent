/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-25
 * <修改描述:>
 */
package com.tx.core.support.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 方法解析器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodResolver {
    
    /** 方法解析器缓存 */
    private static Map<Method, MethodResolver> methodResolverCache = new HashMap<Method, MethodResolver>();
    
    private LinkedList<ParameterResolver> parameterResolvers;
    
    private Method method;
    
    private int parametersLength;
    
    private Class<?> returnType;
    
    /**
     * 方法解析器
     */
    private MethodResolver() {
        parameterResolvers = new LinkedList<ParameterResolver>();
    }
    
    /**
      * 解析方法解析器
      * 
      * @param method
      * @return [参数说明]
      * 
      * @return MethodResolver [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public synchronized static MethodResolver resolveMethodResolver(Method method) {
        if (methodResolverCache.containsKey(method)) {
            return methodResolverCache.get(method);
        }
        
        MethodResolver methodResolver = new MethodResolver();
        methodResolver.setMethod(method);
        method.getTypeParameters();
        Class<?>[] methodTypes = method.getParameterTypes();
        Annotation[][] annotationArr = method.getParameterAnnotations();
        for (int i = 0; i < methodTypes.length; i++) {
            methodResolver.getParameterResolvers().add(new ParameterResolver(
                    methodTypes[i], annotationArr[i]));
        }
        methodResolver.setParametersLength(methodTypes.length);
        methodResolver.setReturnType(method.getReturnType());
        
        methodResolverCache.put(method, methodResolver);
        
        return methodResolver;
    }
    
    /**
     * @return 返回 methodResolverCache
     */
    public static Map<Method, MethodResolver> getMethodResolverCache() {
        return methodResolverCache;
    }
    
    /**
     * @param 对methodResolverCache进行赋值
     */
    public static void setMethodResolverCache(
            Map<Method, MethodResolver> methodResolverCache) {
        MethodResolver.methodResolverCache = methodResolverCache;
    }
    
    /**
     * @return 返回 parameterResolvers
     */
    public LinkedList<ParameterResolver> getParameterResolvers() {
        return parameterResolvers;
    }
    
    /**
     * @param 对parameterResolvers进行赋值
     */
    public void setParameterResolvers(
            LinkedList<ParameterResolver> parameterResolvers) {
        this.parameterResolvers = parameterResolvers;
    }
    
    /**
     * @return 返回 method
     */
    public Method getMethod() {
        return method;
    }
    
    /**
     * @param 对method进行赋值
     */
    public void setMethod(Method method) {
        this.method = method;
    }
    
    /**
     * @return 返回 parametersLength
     */
    public int getParametersLength() {
        return parametersLength;
    }

    /**
     * @param 对parametersLength进行赋值
     */
    public void setParametersLength(int parametersLength) {
        this.parametersLength = parametersLength;
    }

    /**
     * @return 返回 returnType
     */
    public Class<?> getReturnType() {
        return returnType;
    }

    /**
     * @param 对returnType进行赋值
     */
    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }
}
