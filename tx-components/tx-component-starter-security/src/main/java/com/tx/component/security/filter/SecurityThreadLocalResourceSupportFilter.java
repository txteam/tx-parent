/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年11月25日
 * <修改描述:>
 */
package com.tx.component.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.tx.component.security.context.SecurityResourceHolderStrategy;

/**
 * 安全容器支持SecurityResource机制的拦截器，用于强制线程变量清空<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年11月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecurityThreadLocalResourceSupportFilter extends GenericFilterBean{

    /**
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try{
            //System.out.println(((HttpServletRequest)request).getRequestURI());
            SecurityResourceHolderStrategy.open();
            //执行链路
            chain.doFilter(request, response);
        }finally {
            SecurityResourceHolderStrategy.close();
        }
    }
}
