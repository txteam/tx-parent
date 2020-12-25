/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2020年12月24日
 * <修改描述:>
 */
package com.tx.component.security.starter;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.util.ReflectionUtils;

import com.tx.component.security.filter.SecurityThreadLocalResourceSupportFilter;

/**
 * Security类现成变量资源你支持过滤器<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2020年12月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@ConditionalOnClass({ SecurityThreadLocalResourceSupportFilter.class })
public class SecurityContextSupportConfiguration
        implements BeanPostProcessor, ApplicationContextAware {
    
    protected ApplicationContext applicationContext;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
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
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
    
    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (WebSecurityConfigurerAdapter.class
                .isAssignableFrom(bean.getClass())) {
            ProxyFactory factory = new ProxyFactory();
            factory.setTarget(bean);
            factory.addAdvice(
                    new SecurityContextSupportAdapter(this.applicationContext));
            bean = factory.getProxy();
        }
        return bean;
    }
    
    /**
     * 线程变量代理适配器<br/>
     * <功能详细描述>
     * 
     * @author  PengQingyang
     * @version  [版本号, 2020年12月24日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    static class SecurityContextSupportAdapter implements MethodInterceptor {
        
        private SecurityContextSupportConfigurer configurer;
        
        /** <默认构造函数> */
        SecurityContextSupportAdapter(ApplicationContext applicationContext) {
            this.configurer = new SecurityContextSupportConfigurer(
                    applicationContext);
        }
        
        /**
         * 注入init方法，在init执行期间获取getHttp，注入指定的Filter
         * @param invocation
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            if (invocation.getMethod().getName().equals("init")) {
                Method method = ReflectionUtils.findMethod(
                        WebSecurityConfigurerAdapter.class,
                        "getHttp");
                ReflectionUtils.makeAccessible(method);
                HttpSecurity http = (HttpSecurity) ReflectionUtils
                        .invokeMethod(method, invocation.getThis());
                this.configurer.configure(http);
            }
            return invocation.proceed();
        }
    }
    
    /**
     * 线程变量资源配置器<br/>
     * <功能详细描述>
     * @author  PengQingyang
     * @version  [版本号, 2020年12月24日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    static class SecurityContextSupportConfigurer {
        
        /** spring容器 */
        protected ApplicationContext applicationContext;
        
        /** 安全线程变量资源配置器 */
        SecurityContextSupportConfigurer(
                ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }
        
        /** 配置http */
        public void configure(HttpSecurity http) throws Exception {
            http.apply(new SecurityThreadLocalResourceSupportConfigurer(
                    securityThreadLoccalResourceFilter()));
        }
        
        private SecurityThreadLocalResourceSupportFilter securityThreadLoccalResourceFilter() {
            SecurityThreadLocalResourceSupportFilter filter = new SecurityThreadLocalResourceSupportFilter();
            return filter;
        }
        
        /**
         * 安全线程变量资源支持配置<br/>
         * <功能详细描述>
         * 
         * @author  PengQingyang
         * @version  [版本号, 2020年12月24日]
         * @see  [相关类/方法]
         * @since  [产品/模块版本]
         */
        private static class SecurityThreadLocalResourceSupportConfigurer
                extends
                SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
            
            private SecurityThreadLocalResourceSupportFilter filter;
            
            SecurityThreadLocalResourceSupportConfigurer(
                    SecurityThreadLocalResourceSupportFilter filter) {
                this.filter = filter;
            }
            
            @Override
            public void configure(HttpSecurity builder) throws Exception {
                SecurityThreadLocalResourceSupportFilter threadLocalResourceSupportFilter = this.filter;
                //ssoFilter.setSessionAuthenticationStrategy(builder
                //        .getSharedObject(SessionAuthenticationStrategy.class));
                builder.addFilterAfter(threadLocalResourceSupportFilter,
                        SecurityContextHolderAwareRequestFilter.class);
            }
        }
    }
    
}
