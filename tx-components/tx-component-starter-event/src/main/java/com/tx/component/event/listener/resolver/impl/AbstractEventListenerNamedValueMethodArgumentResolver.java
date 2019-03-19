/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月21日
 * <修改描述:>
 */
package com.tx.component.event.listener.resolver.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;

import com.tx.component.event.event.Event;
import com.tx.component.event.listener.resolver.EventListenerMethodArgumentResolver;
import com.tx.core.TxConstants;

/**
 * 抽象的事件监听器方法参数解析器:主要根据name对参数的解析<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractEventListenerNamedValueMethodArgumentResolver
        implements EventListenerMethodArgumentResolver {
    
    /** 参数信息 */
    private Map<MethodParameter, NamedValueInfo> namedValueInfoCache = new ConcurrentHashMap<MethodParameter, NamedValueInfo>(
            TxConstants.INITIAL_MAP_SIZE);
    
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
        //获取参数对应的NamedValueInfo
        NamedValueInfo namedValueInfo = getNamedValueInfo(parameter);
        
        //根据名获取对应参数值<br/>
        Object arg = resolveName(namedValueInfo.name, parameter, event, params);
        
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
      *<功能详细描述>
      * @param name
      * @param parameter
      * @param event
      * @param params
      * @return
      * @throws Exception [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract Object resolveName(String name,
            MethodParameter parameter, Event event, Map<String, Object> params)
            throws Exception;
    
    /**
     * 根据方法参数检错对应的NameValueInfo
     *<功能详细描述>
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
        
        /** 参数名 */
        private final String name;
        
        /** 参数是否必填 */
        private final boolean required;
        
        /** 命名值信息 */
        protected NamedValueInfo(String name, boolean required) {
            this.name = name;
            this.required = required;
        }
    }
    
}
