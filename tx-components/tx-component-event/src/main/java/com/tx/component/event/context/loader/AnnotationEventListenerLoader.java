/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月19日
 * <修改描述:>
 */
package com.tx.component.event.context.loader;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.method.HandlerMethod;

import com.tx.component.event.annotation.EventListeners;
import com.tx.component.event.context.EventListenerLoader;
import com.tx.component.event.listener.EventListener;
import com.tx.component.event.listener.EventListenerScopeEnum;
import com.tx.component.event.listener.impl.HandlerMethodEventListener;
import com.tx.component.event.listener.resolver.EventListenerMethodArgumentResolver;
import com.tx.core.exceptions.SILException;
import com.tx.core.util.AopTargetUtils;

/**
 * 注解类型的事件监听器加载器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AnnotationEventListenerLoader implements EventListenerLoader,
        ApplicationContextAware {
    
    /** 参数解析器 */
    private List<EventListenerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
    
    /** spring容器句柄 */
    private ApplicationContext applicationContext;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @return
     */
    @Override
    public List<EventListener> load() {
        //添加事件监听器方法参数解析器
        argumentResolvers.addAll(this.applicationContext.getBeansOfType(EventListenerMethodArgumentResolver.class)
                .values());
        
        //springBean上存在EventListeners注解
        Map<String, Object> beansMap = this.applicationContext.getBeansWithAnnotation(EventListeners.class);
        
        List<EventListener> resList = new ArrayList<>();
        if (MapUtils.isEmpty(beansMap)) {
            return resList;
        }
        
        for (Object beanTemp : beansMap.values()) {
            Object target = beanTemp;
            try {
                target = AopTargetUtils.getTarget(beanTemp);
            } catch (Exception e) {
                //TODO: XX
                throw new SILException("", e);
            }
            Method[] methods = target.getClass().getMethods();
            if (ArrayUtils.isEmpty(methods)) {
                continue;
            }
            for (Method methodTemp : methods) {
                //检查是否存在EventListener注解
                if (!methodTemp.isAnnotationPresent(com.tx.component.event.annotation.EventListener.class)) {
                    continue;
                }
                
                com.tx.component.event.annotation.EventListener eventListenerAnno = methodTemp.getAnnotation(com.tx.component.event.annotation.EventListener.class);
                String eventType = eventListenerAnno.value();
                //如果value为空
                if (StringUtils.isBlank(eventType)) {
                    //将指定的事件类型名当做eventType进行处理
                    eventType = eventListenerAnno.eventType().getName();
                }
                EventListenerScopeEnum scope = eventListenerAnno.scope();
                HandlerMethod handlerMethod = new HandlerMethod(beanTemp,
                        methodTemp);
                int order = eventListenerAnno.order();
                
                EventListener eventListenerTemp = new HandlerMethodEventListener(
                        eventType, scope, handlerMethod, argumentResolvers,
                        order);
                resList.add(eventListenerTemp);
            }
        }
        
        return resList;
    }
}
