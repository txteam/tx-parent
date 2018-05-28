/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月24日
 * <修改描述:>
 */
package com.tx.core.method.resolver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.Assert;

import com.tx.core.TxConstants;
import com.tx.core.method.request.InvokeRequest;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月24日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class MethodArgumentResolverComposite implements MethodArgumentResolver {
    
    /** 日志记录器 */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /** 参数解析器的装饰器模式 */
    private final List<MethodArgumentResolver> argumentResolvers = new LinkedList<MethodArgumentResolver>();
    
    /** 参数名解析器<br/> */
    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    
    /** 通过该功能能提高方法实际调用时参数匹配时的效率 */
    private final Map<MethodParameter, MethodArgumentResolver> argumentResolverCache = new ConcurrentHashMap<MethodParameter, MethodArgumentResolver>(
            TxConstants.INITIAL_MAP_SIZE);
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
    /**
     * 根据方法参数对象获取对应的方法参数解析器对象<br/> 
     * <功能详细描述>
     * @param parameter
     * @return [参数说明]
     * 
     * @return HandlerMethodArgumentResolver [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private MethodArgumentResolver getArgumentResolver(
            MethodParameter parameter) {
        MethodArgumentResolver result = this.argumentResolverCache
                .get(parameter);
        if (result == null) {
            //初始化参数名获取方式<br/>
            parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
            
            //查找方法参数对应的参数解析器<br/>
            for (MethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Testing if argument resolver ["
                            + methodArgumentResolver + "] supports ["
                            + parameter.getGenericParameterType() + "]");
                }
                
                if (methodArgumentResolver.supportsParameter(parameter)) {
                    result = methodArgumentResolver;
                    this.argumentResolverCache.put(parameter, result);
                    break;
                }
            }
        }
        return result;
    }
    
    /**
     * 是否支持对应的参数解析<br/>
     * @param parameter
     * @return
     */
    public boolean supportsParameter(MethodParameter parameter) {
        return getArgumentResolver(parameter) != null;
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
        MethodArgumentResolver resolver = getArgumentResolver(parameter);
        
        //参数解析器不能为空
        Assert.notNull(resolver,
                "Unknown parameter type ["
                        + parameter.getParameterType().getName() + "]");
        
        return resolver.resolveArgument(parameter, invokeRequest);
    }
    
    /**
     * Add the given {@link EventListenerMethodArgumentResolver}.
     */
    public MethodArgumentResolverComposite addResolver(
            MethodArgumentResolver argumentResolver) {
        this.argumentResolvers.add(argumentResolver);
        Collections.sort(this.argumentResolvers, OrderComparator.INSTANCE);
        
        return this;
    }
    
    /**
     * Add the given {@link EventListenerMethodArgumentResolver}s.
     */
    public MethodArgumentResolverComposite addResolvers(
            List<? extends MethodArgumentResolver> argumentResolvers) {
        if (argumentResolvers != null) {
            for (MethodArgumentResolver resolver : argumentResolvers) {
                this.argumentResolvers.add(resolver);
            }
        }
        Collections.sort(this.argumentResolvers, OrderComparator.INSTANCE);
        
        return this;
    }
}
