/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.component.event.listener.resolver;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.Assert;

import com.tx.component.event.event.Event;
import com.tx.core.TxConstants;

/**
 * 处理方法参数解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventListenerMethodArgumentResolverComposite implements
        EventListenerMethodArgumentResolver {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    /** 参数名解析器<br/> */
    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    
    /** 参数解析器的装饰器模式 */
    private final List<EventListenerMethodArgumentResolver> argumentResolvers = new LinkedList<EventListenerMethodArgumentResolver>();
    
    /** 通过该功能能提高方法实际调用时参数匹配时的效率 */
    private final Map<MethodParameter, EventListenerMethodArgumentResolver> argumentResolverCache = new ConcurrentHashMap<MethodParameter, EventListenerMethodArgumentResolver>(
            TxConstants.INITIAL_MAP_SIZE);
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
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
      * 根据方法参数对象获取对应的方法参数解析器对象<br/> 
      *<功能详细描述>
      * @param parameter
      * @return [参数说明]
      * 
      * @return HandlerMethodArgumentResolver [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private EventListenerMethodArgumentResolver getArgumentResolver(
            MethodParameter parameter) {
        EventListenerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
        if (result == null) {
            //初始化参数名获取方式<br/>
            parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
            //查找方法参数对应的参数解析器<br/>
            for (EventListenerMethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
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
     * @param parameter
     * @param event
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, Event event,
            Map<String, Object> params) throws Exception {
        EventListenerMethodArgumentResolver resolver = getArgumentResolver(parameter);
        Assert.notNull(resolver, "Unknown parameter type ["
                + parameter.getParameterType().getName() + "]");
        
        return resolver.resolveArgument(parameter, event, params);
    }
    
    /**
     * Add the given {@link EventListenerMethodArgumentResolver}.
     */
    public EventListenerMethodArgumentResolverComposite addResolver(
            EventListenerMethodArgumentResolver argumentResolver) {
        this.argumentResolvers.add(argumentResolver);
        return this;
    }
    
    /**
     * Add the given {@link EventListenerMethodArgumentResolver}s.
     */
    public EventListenerMethodArgumentResolverComposite addResolvers(
            List<? extends EventListenerMethodArgumentResolver> argumentResolvers) {
        if (argumentResolvers != null) {
            for (EventListenerMethodArgumentResolver resolver : argumentResolvers) {
                this.argumentResolvers.add(resolver);
            }
        }
        return this;
    }
}
