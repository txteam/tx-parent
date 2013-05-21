/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-21
 * <修改描述:>
 */
package com.tx.core.support.rmi.interceptor;

import java.rmi.server.RemoteServer;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * rmi服务限制调用客户端ip
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecurityInterceptor implements MethodInterceptor {
    
    private Set<String> allowed;
    
    /**
     * @param arg0
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String clientHost = RemoteServer.getClientHost();
        if (allowed != null && allowed.contains(clientHost)) {
            return methodInvocation.proceed();
        } else {
            throw new SecurityException("非法访问.");
        }
    }
    
    public void setAllowed(Set<String> allowed) {
        this.allowed = allowed;
    }
}
