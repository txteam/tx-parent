/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年7月3日
 * <修改描述:>
 */
package com.tx.component.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 服务提供者微服务提供系统的前置请求Filter<br/>
 *    在接收到请求后，根据传递的jwt消息中消息头中的AuthenticationToken策略找到对应的实现<br/>
 *    根据对应的策略进行认证信息的处理<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年7月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter{

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        
    }
}
