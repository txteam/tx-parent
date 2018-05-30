/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月27日
 * <修改描述:>
 */
package com.tx.core.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.method.exceptions.MethodArgResolveException;
import com.tx.core.method.exceptions.MethodInvokeAccessException;
import com.tx.core.method.exceptions.MethodInvokeArgIllegalException;
import com.tx.core.method.exceptions.MethodInvokeTargetException;
import com.tx.core.method.request.InvokeRequest;
import com.tx.core.method.resolver.MethodArgumentResolverComposite;

/**
  * 可调用的HandlerMethod<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月27日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class InvocableHandlerMethod extends HandlerMethod {
    
    /** 参数解析器 */
    private MethodArgumentResolverComposite argumentResolvers = MethodArgumentResolverComposite.DEFAULT_INSTANCE;
    
    /** 参数名发现器 */
    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    
    /** <默认构造函数> */
    public InvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }
    
    /** <默认构造函数> */
    public InvocableHandlerMethod(Object bean, Method method) {
        super(bean, method);
    }
    
    /** <默认构造函数> */
    public InvocableHandlerMethod(Object bean, String methodName,
            Class<?>... parameterTypes) throws NoSuchMethodException {
        super(bean, methodName, parameterTypes);
    }
    
    /**
     * @param 对argumentResolvers进行赋值
     */
    public void setArgumentResolvers(
            MethodArgumentResolverComposite argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }
    
    /**
     * @param 对parameterNameDiscoverer进行赋值
     */
    public void setParameterNameDiscoverer(
            ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }
    
    /**
     * 在给定请求的上下文中解析其参数值之后调用该方法。
     * 参数值通常通过MethodArgumentResolver来解决。
     * 参数可以提供直接使用的参数值，
     * 即没有参数解析。提供的参数值的示例包括，或抛出异常实例。
     * 在参数解析器之前检查提供的参数值。
     * 
     * @param request the current request
     * @param providedArgs "given" arguments matched by type, not resolved
     * 
     * @return the raw value returned by the invoked method
     * @throws Exception raised if no suitable argument resolver can be found,
     * or if the method raised an exception
     */
    public Object invokeForRequest(InvokeRequest request,
            Object... providedArgs) {
        
        //获取方法参数
        Object[] args = getMethodArgumentValues(request, providedArgs);
        
        if (logger.isTraceEnabled()) {
            logger.trace("Invoking '"
                    + ClassUtils.getQualifiedMethodName(getMethod(),
                            getBeanType())
                    + "' with arguments " + Arrays.toString(args));
        }
        
        Object returnValue = doInvoke(args);
        
        if (logger.isTraceEnabled()) {
            logger.trace("Method ["
                    + ClassUtils.getQualifiedMethodName(getMethod(),
                            getBeanType())
                    + "] returned [" + returnValue + "]");
        }
        
        return returnValue;
    }
    
    /**
     * Get the method argument values for the current request.
     */
    private Object[] getMethodArgumentValues(InvokeRequest request,
            Object... providedArgs) {
        
        MethodParameter[] parameters = getMethodParameters();
        Object[] args = new Object[parameters.length];
        
        for (int index = 0; index < parameters.length; index++) {
            MethodParameter parameter = parameters[index];
            parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
            
            //如果从额外出的参数中检测出位置一致，并且类型一致的参数 则优先适配
            if (!ArrayUtils.isEmpty(providedArgs)) {
                if (parameters.length == providedArgs.length) {
                    args[index] = resolveProvidedArgumentByIndex(index,
                            parameter,
                            providedArgs);
                } else {
                    args[index] = resolveProvidedArgumentByType(parameter,
                            providedArgs);
                }
            }
            
            if (args[index] != null) {
                continue;
            }
            
            //如果没有，则从调用请求中获取值
            if (this.argumentResolvers.supportsParameter(parameter)) {
                try {
                    args[index] = this.argumentResolvers
                            .resolveArgument(parameter, request);
                    continue;
                } catch (MethodArgResolveException ex) {
                    throw ex;
                } catch (Exception ex) {
                    throw new MethodArgResolveException(
                            getArgumentResolutionErrorMessage(
                                    "Failed to resolve", index),
                            ex);
                }
            }
            
            if (args[index] == null) {
                throw new MethodArgResolveException(
                        "Could not resolve method parameter at index "
                                + parameter.getParameterIndex() + " in "
                                + parameter.getMethod().toGenericString() + ": "
                                + getArgumentResolutionErrorMessage(
                                        "No suitable resolver for", index));
            }
        }
        return args;
    }
    
    /**
     * 获取参数结息异常信息<br/>
     * <功能详细描述>
     * @param text
     * @param index
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String getArgumentResolutionErrorMessage(String text, int index) {
        Class<?> paramType = getMethodParameters()[index].getParameterType();
        return text + " argument " + index + " of type '" + paramType.getName()
                + "'";
    }
    
    /**
     * 尝试从提供的参数值列表中解析方法参数<br/.
     *    根据类型优先匹配的原则，获取参数.(适用于参数个数不一致的情况)
     * <功能详细描述>
     * @param parameter
     * @param providedArgs
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private Object resolveProvidedArgumentByType(MethodParameter parameter,
            Object... providedArgs) {
        if (providedArgs == null) {
            return null;
        }
        for (Object providedArg : providedArgs) {
            if (parameter.getParameterType().isInstance(providedArg)) {
                return providedArg;
            }
        }
        return null;
    }
    
    /**
     * 尝试从提供的参数值列表中解析方法参数<br/>
     *  适用于参数个数一致的情况<br/>
     * <功能详细描述>
     * @param parameter
     * @param providedArgs
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Object resolveProvidedArgumentByIndex(int index,
            MethodParameter parameter, Object... providedArgs) {
        AssertUtils.isTrue(index < providedArgs.length,
                "index:{} should less than arg.length:{}",
                new Object[] { index, providedArgs.length });
        if (ArrayUtils.isEmpty(providedArgs)) {
            return null;
        }
        
        Object providedArg = providedArgs[index];
        if (parameter.getParameterType().isInstance(providedArg)) {
            return providedArg;
        }
        return null;
    }
    
    /**
     * Invoke the handler method with the given argument values.
     */
    protected Object doInvoke(Object... args) {
        ReflectionUtils.makeAccessible(getBridgedMethod());
        
        try {
            return getBridgedMethod().invoke(getBean(), args);
        } catch (IllegalAccessException e) {
            throw new MethodInvokeAccessException("invoke access exception.",
                    e);
        } catch (IllegalArgumentException ex) {
            assertTargetBean(getBridgedMethod(), getBean(), args);
            
            String text = (ex.getMessage() != null ? ex.getMessage()
                    : "Illegal argument");
            throw new MethodInvokeArgIllegalException(
                    getInvocationErrorMessage(text, args), ex);
        } catch (InvocationTargetException ex) {
            Throwable targetException = ex.getTargetException();
            
            if (targetException instanceof SILException) {
                throw (SILException) targetException;
            } else {
                String text = getInvocationErrorMessage(
                        "Failed to invoke handler method", args);
                throw new MethodInvokeTargetException(text, ex,
                        targetException);
            }
        }
    }
    
    /**
     * Assert that the target bean class is an instance of the class where the given
     * method is declared. In some cases the actual controller instance at request-
     * processing time may be a JDK dynamic proxy (lazy initialization, prototype
     * beans, and others). {@code @Controller}'s that require proxying should prefer
     * class-based proxy mechanisms.
     */
    private void assertTargetBean(Method method, Object targetBean,
            Object[] args) {
        Class<?> methodDeclaringClass = method.getDeclaringClass();
        Class<?> targetBeanClass = targetBean.getClass();
        if (!methodDeclaringClass.isAssignableFrom(targetBeanClass)) {
            String text = "The mapped handler method class '"
                    + methodDeclaringClass.getName()
                    + "' is not an instance of the actual controller bean class '"
                    + targetBeanClass.getName()
                    + "'. If the controller requires proxying "
                    + "(e.g. due to @Transactional), please use class-based proxying.";
            
            throw new MethodInvokeAccessException(
                    getInvocationErrorMessage(text, args));
        }
    }
    
    /**
     * 获取注入的错误消息<br/>
     * <功能详细描述>
     * @param text
     * @param resolvedArgs
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String getInvocationErrorMessage(String text,
            Object[] resolvedArgs) {
        StringBuilder sb = new StringBuilder(getDetailedErrorMessage(text));
        sb.append("Resolved arguments: \n");
        for (int i = 0; i < resolvedArgs.length; i++) {
            sb.append("[").append(i).append("] ");
            if (resolvedArgs[i] == null) {
                sb.append("[null] \n");
            } else {
                sb.append("[type=")
                        .append(resolvedArgs[i].getClass().getName())
                        .append("] ");
                sb.append("[value=").append(resolvedArgs[i]).append("]\n");
            }
        }
        return sb.toString();
    }
    
    /**
     * Adds HandlerMethod details such as the bean type and method signature to the message.
     * @param text error message to append the HandlerMethod details to
     */
    protected String getDetailedErrorMessage(String text) {
        StringBuilder sb = new StringBuilder(text).append("\n");
        sb.append("HandlerMethod details: \n");
        sb.append("Controller [").append(getBeanType().getName()).append("]\n");
        sb.append("Method [")
                .append(getBridgedMethod().toGenericString())
                .append("]\n");
        
        return sb.toString();
    }
}
