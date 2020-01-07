/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月23日
 * <修改描述:>
 */
package com.tx.component.servicelogger.starter;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.servicelogger.support.LogArgumentHandler;
import com.tx.component.servicelogger.support.ServiceLoggerAop;
import com.tx.component.servicelogger.support.ServiceLoggerRegistry;
import com.tx.component.servicelogger.support.handler.ClientIpAddressLogArgHandler;
import com.tx.component.servicelogger.support.handler.CreateDateLogArgHandler;
import com.tx.component.servicelogger.support.handler.RealIpAddressLogArgHandler;
import com.tx.component.servicelogger.support.handler.RemoteIpAddressLogArgHandler;
import com.tx.component.servicelogger.support.handler.SessionAttributesLogArgHandler;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.starter.component.ComponentConstants;

/**
 * 权限容器自动配置项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@EnableAspectJAutoProxy
@Configuration
public class ServiceLoggerPersisterConfiguration {
    
    @Bean(name = "clientIpAddressLogArgHandler")
    public ClientIpAddressLogArgHandler clientIpAddressLogArgHandler() {
        ClientIpAddressLogArgHandler handler = new ClientIpAddressLogArgHandler();
        return handler;
    }
    
    @Bean(name = "realIpAddressLogArgHandler")
    public RealIpAddressLogArgHandler realIpAddressLogArgHandler() {
        RealIpAddressLogArgHandler handler = new RealIpAddressLogArgHandler();
        return handler;
    }
    
    @Bean(name = "remoteIpAddressLogArgHandler")
    public RemoteIpAddressLogArgHandler remoteIpAddressLogArgHandler() {
        RemoteIpAddressLogArgHandler handler = new RemoteIpAddressLogArgHandler();
        return handler;
    }
    
    @Bean(name = "createDateLogArgHandler")
    public CreateDateLogArgHandler createDateLogArgHandler() {
        CreateDateLogArgHandler handler = new CreateDateLogArgHandler();
        return handler;
    }
    
    @Bean(name = "sessionAttributesLogArgHandler")
    public SessionAttributesLogArgHandler sessionAttributesLogArgHandler() {
        SessionAttributesLogArgHandler handler = new SessionAttributesLogArgHandler();
        return handler;
    }
    
    /**
     * 注入aspect切面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return ServiceLoggerAop [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "serviceLoggerAop")
    public ServiceLoggerAop serviceLoggerAop(
            List<LogArgumentHandler> handlers) {
        ServiceLoggerAop aop = new ServiceLoggerAop(handlers);
        return aop;
    }
    
    /**
     * mybatis持久层逻辑实现<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年5月2日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @ConditionalOnProperty(prefix = ComponentConstants.PERSISTER_PROPERTIES_PREFIX, value = "type", havingValue = "mybatis")
    @ConditionalOnBean(name = { "tx.component.myBatisDaoSupport" })
    public static class MybatisAuthContextPersisterConfiguration
            implements InitializingBean {
        
        /** mybatis属性 */
        @Resource(name = "tx.component.myBatisDaoSupport")
        private MyBatisDaoSupport myBatisDaoSupport;
        
        /** 事务管理器 */
        private TransactionTemplate transactionTemplate;
        
        /** 扫描业务日志的路径 */
        private String basePackages;
        
        /** 权限容器属性 */
        private ServiceLoggerContextProperties properties;
        
        /** <默认构造函数> */
        public MybatisAuthContextPersisterConfiguration(
                ServiceLoggerContextProperties properties,
                PlatformTransactionManager transactionManager) {
            this.properties = properties;
            this.transactionTemplate = new TransactionTemplate(
                    transactionManager);
        }
        
        /**
         * @throws Exception
         */
        @Override
        public void afterPropertiesSet() throws Exception {
            AssertUtils.notNull(this.myBatisDaoSupport,
                    "myBatisDaoSupport is null.");
            this.basePackages = StringUtils
                    .isEmpty(properties.getBasePackages()) ? "com.tx"
                            : properties.getBasePackages();
        }
        
        /**
         * 业务日志注册表<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return ServiceLoggerRegistry [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(name = "serviceLoggerRegistry")
        public ServiceLoggerRegistry serviceLoggerRegistry() {
            ServiceLoggerRegistry registry = new ServiceLoggerRegistry(
                    this.basePackages, this.myBatisDaoSupport,
                    this.transactionTemplate);
            return registry;
        }
    }
}
