/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月21日
 * <修改描述:>
 */
package com.tx.core.method.resolver.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver.NamedValueInfo;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.tx.core.TxConstants;
import com.tx.core.method.request.InvokeRequest;
import com.tx.core.method.resolver.MethodArgumentResolver;

/**
 * 抽象的事件监听器方法参数解析器:主要根据name对参数的解析<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractNamedValueMethodArgumentResolver
        implements MethodArgumentResolver {
    
    private final ConfigurableBeanFactory configurableBeanFactory;

    private final BeanExpressionContext expressionContext;
    
    /** 参数信息 */
    private final Map<MethodParameter, NamedValueInfo> namedValueInfoCache = new ConcurrentHashMap<MethodParameter, NamedValueInfo>(
            TxConstants.INITIAL_MAP_SIZE);
    
    /** 构造函数 */
    public AbstractNamedValueMethodArgumentResolver() {
        this.configurableBeanFactory = null;
        this.expressionContext = null;
    }

    /**
     * @param beanFactory a bean factory to use for resolving ${...} placeholder
     * and #{...} SpEL expressions in default values, or {@code null} if default
     * values are not expected to contain expressions
     */
    public AbstractNamedValueMethodArgumentResolver(ConfigurableBeanFactory beanFactory) {
        this.configurableBeanFactory = beanFactory;
        this.expressionContext =
                (beanFactory != null ? new BeanExpressionContext(beanFactory, new RequestScope()) : null);
    }
    
    /**
     * @param parameter
     * @param event
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
            InvokeRequest invokeRequest) throws Exception {
        //获取参数对应的NamedValueInfo
        NamedValueInfo namedValueInfo = getNamedValueInfo(parameter);
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        
        Object resolvedName = resolveStringValue(namedValueInfo.name);
        if (resolvedName == null) {
            throw new IllegalArgumentException(
                    "Specified name must not resolve to null: [" + namedValueInfo.name + "]");
        }
        
        //根据名获取对应参数值<br/>
        Object arg = resolveName(namedValueInfo.name, parameter, invokeRequest);
        
        //如果对应参数值获取出来为空
        if (arg == null) {
            if (namedValueInfo.required) {
                handleMissingValue(namedValueInfo.name, parameter);
            }
        }
        
        return arg;
    }
    
    /**
     * Invoked when a named value is required, but {@link #resolveName(String, MethodParameter, NativeWebRequest)}
     * returned {@code null} and there is no default value. Subclasses typically throw an exception in this case.
     * @param name the name for the value
     * @param parameter the method parameter
     */
    protected abstract void handleMissingValue(String name,
            MethodParameter parameter) throws ServletException;
    
    /**
      * 基恩系参数值<br/> 
      * <功能详细描述>
      * @param name
      * @param parameter
      * @param invokeRequest
      * @return
      * @throws Exception [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract Object resolveName(String name,
            MethodParameter parameter, InvokeRequest invokeRequest)
            throws Exception;
    
    /**
     * 根据方法参数检错对应的NameValueInfo
     * <功能详细描述>
     * @param parameter
     * @return [参数说明]
     * 
     * @return NamedValueInfo [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
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
      * 根据参数对象生成其对应的NamedValueInfo<br/>
      * <功能详细描述>
      * @param parameter
      * @return [参数说明]
      * 
      * @return NamedValueInfo [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract NamedValueInfo createNamedValueInfo(
            MethodParameter parameter);
    
    /**
      * 将子类创建的NamedValueInfo进行加工处理方法<br/>
      *<功能详细描述>
      * @param parameter
      * @param info
      * @return [参数说明]
      * 
      * @return NamedValueInfo [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private NamedValueInfo updateNamedValueInfo(MethodParameter parameter,
            NamedValueInfo info) {
        String name = info.name;
        if (StringUtils.isBlank(info.name)) {
            name = parameter.getParameterName();
            Assert.notNull(name,
                    "Name for argument type ["
                            + parameter.getParameterType().getName()
                            + "] not available, and parameter name information not found in class file either.");
        }
        return new NamedValueInfo(name, info.required);
    }
    
    protected void handleMissingValue(String name, MethodParameter parameter, NativeWebRequest request)
            throws Exception {

        handleMissingValue(name, parameter);
    }
    
    protected void handleMissingValue(String name, MethodParameter parameter) throws ServletException {
        throw new ServletRequestBindingException("Missing argument '" + name +
                "' for method parameter of type " + parameter.getNestedParameterType().getSimpleName());
    }
    
    /**
     * A {@code null} results in a {@code false} value for {@code boolean}s or an exception for other primitives.
     */
    private Object handleNullValue(String name, Object value, Class<?> paramType) {
        if (value == null) {
            if (Boolean.TYPE.equals(paramType)) {
                return Boolean.FALSE;
            }
            else if (paramType.isPrimitive()) {
                throw new IllegalStateException("Optional " + paramType.getSimpleName() + " parameter '" + name +
                        "' is present but cannot be translated into a null value due to being declared as a " +
                        "primitive type. Consider declaring it as object wrapper for the corresponding primitive type.");
            }
        }
        return value;
    }
    
    /**
     * Invoked after a value is resolved.
     * 
     * @param arg the resolved argument value
     * @param name the argument name
     * @param parameter the argument parameter type
     * @param mavContainer the {@link ModelAndViewContainer} (may be {@code null})
     * @param webRequest the current request
     */
    protected void handleResolvedValue(Object arg, String name,
            MethodParameter parameter, InvokeRequest invokeRequest) {
    }
    
    /**
      * 参数名及参数值之间的关联信息<br/>
      * <功能详细描述>
      * 
      * @author  Administrator
      * @version  [版本号, 2014年4月21日]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    protected static class NamedValueInfo {
        
        private final String name;
        
        private final boolean required;
        
        private final String defaultValue;
        
        public NamedValueInfo(String name, boolean required,
                String defaultValue) {
            this.name = name;
            this.required = required;
            this.defaultValue = defaultValue;
        }
    }
    
}
