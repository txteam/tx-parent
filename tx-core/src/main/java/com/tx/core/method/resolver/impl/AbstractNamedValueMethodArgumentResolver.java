/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.core.method.resolver.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.validation.DataBinder;
import org.springframework.web.context.request.NativeWebRequest;

import com.tx.core.method.exceptions.MethodArgResolveException;
import com.tx.core.method.request.InvokeRequest;
import com.tx.core.method.resolver.MethodArgumentResolver;
import com.tx.core.util.MessageUtils;

/**
 * 参数具@EventListenerParam注解的方法参数解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractNamedValueMethodArgumentResolver
        implements MethodArgumentResolver {
    
    private final Map<MethodParameter, NamedValueInfo> namedValueInfoCache = new ConcurrentHashMap<MethodParameter, NamedValueInfo>(
            256);
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
    
    /**
     * @param methodParameter
     * @param invokeRequest
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
            InvokeRequest invokeRequest) throws Exception {
        //获取参数名
        NamedValueInfo namedValueInfo = getNamedValueInfo(parameter);
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        
        Object arg = resolveName(namedValueInfo.name,
                nestedParameter,
                invokeRequest);
        
        if (arg == null) {
            if (namedValueInfo.required && !nestedParameter.isOptional()) {
                handleMissingValue(namedValueInfo.name,
                        nestedParameter,
                        invokeRequest);
            }
            arg = handleNullValue(namedValueInfo.name,
                    arg,
                    nestedParameter.getNestedParameterType());
        }
        
        DataBinder binder = new DataBinder(null, namedValueInfo.name);
        try {
            arg = binder.convertIfNecessary(arg,
                    parameter.getParameterType(),
                    parameter);
        } catch (ConversionNotSupportedException ex) {
            throw new MethodArgResolveException(MessageUtils.format(
                    "ConversionNotSupported: argumentName {} for methodParameter type {}",
                    new Object[] { namedValueInfo.name, parameter
                            .getNestedParameterType().getSimpleName() }),
                    ex);
        } catch (TypeMismatchException ex) {
            throw new MethodArgResolveException(MessageUtils.format(
                    "TypeMismatch: argumentName {} for methodParameter type {}",
                    new Object[] { namedValueInfo.name, parameter
                            .getNestedParameterType().getSimpleName() }),
                    ex);
        }
        
        return arg;
    }
    
    /**
     * Resolve the given parameter type and value name into an argument value.
     * @param name the name of the value being resolved
     * @param parameter the method parameter to resolve to an argument value
     * (pre-nested in case of a {@link java.util.Optional} declaration)
     * @param request the current request
     * @return the resolved argument (may be {@code null})
     * @throws Exception in case of errors
     */
    protected abstract Object resolveName(String name,
            MethodParameter parameter, InvokeRequest request);
    
    /**
     * A {@code null} results in a {@code false} value for {@code boolean}s or an exception for other primitives.
     */
    private Object handleNullValue(String name, Object value,
            Class<?> paramType) {
        if (value == null) {
            if (Boolean.TYPE.equals(paramType)) {
                return Boolean.FALSE;
            } else if (paramType.isPrimitive()) {
                throw new MethodArgResolveException("Optional "
                        + paramType.getSimpleName() + " parameter '" + name
                        + "' is present but cannot be translated into a null value due to being declared as a "
                        + "primitive type. Consider declaring it as object wrapper for the corresponding primitive type.");
            }
        }
        return value;
    }
    
    /**
     * Invoked when a named value is required, but {@link #resolveName(String, MethodParameter, NativeWebRequest)}
     * returned {@code null} and there is no default value. Subclasses typically throw an exception in this case.
     * @param name the name for the value
     * @param parameter the method parameter
     * @param request the current request
     * @since 4.3
     */
    protected void handleMissingValue(String name, MethodParameter parameter,
            InvokeRequest request) throws Exception {
        handleMissingValue(name, parameter);
    }
    
    /**
     * Invoked when a named value is required, but {@link #resolveName(String, MethodParameter, NativeWebRequest)}
     * returned {@code null} and there is no default value. Subclasses typically throw an exception in this case.
     * @param name the name for the value
     * @param parameter the method parameter
     */
    protected void handleMissingValue(String name, MethodParameter parameter)
            throws ServletException {
        throw new MethodArgResolveException(
                "Missing argument '" + name + "' for method parameter of type "
                        + parameter.getNestedParameterType().getSimpleName());
    }
    
    /**
     * Obtain the named value for the given method parameter.
     */
    private NamedValueInfo getNamedValueInfo(MethodParameter parameter) {
        NamedValueInfo namedValueInfo = this.namedValueInfoCache.get(parameter);
        if (namedValueInfo == null) {
            namedValueInfo = createNamedValueInfo(parameter);
            namedValueInfo = updateNamedValueInfo(parameter, namedValueInfo);
            this.namedValueInfoCache.put(parameter, namedValueInfo);
        }
        return namedValueInfo;
    }
    
    /**
     * Create the {@link NamedValueInfo} object for the given method parameter. Implementations typically
     * retrieve the method annotation by means of {@link MethodParameter#getParameterAnnotation(Class)}.
     * 
     * @param parameter the method parameter
     * @return the named value information
     */
    protected abstract NamedValueInfo createNamedValueInfo(
            MethodParameter parameter);
    
    /**
     * Create a new NamedValueInfo based on the given NamedValueInfo with sanitized values.
     */
    private NamedValueInfo updateNamedValueInfo(MethodParameter parameter,
            NamedValueInfo info) {
        String name = info.name;
        if (org.apache.commons.lang3.StringUtils.isEmpty(info.name)) {
            name = parameter.getParameterName();
            
            if (name == null) {
                throw new MethodArgResolveException("Name for argument type ["
                        + parameter.getNestedParameterType().getName()
                        + "] not available, and parameter name information not found in class file either.");
            }
        }
        return new NamedValueInfo(name, info.required);
    }
    
    /**
     * Represents the information about a named value, including name, whether it's required
     */
    protected static class NamedValueInfo {
        
        private final String name;
        
        private final boolean required;
        
        public NamedValueInfo(String name, boolean required) {
            this.name = name;
            this.required = required;
        }
    }
}
