/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月15日
 * <修改描述:>
 */
package com.tx.component.security.zuulfilter;

import com.netflix.zuul.ZuulFilter;

/**
 * Zuul的客户端前置处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ZuulClientPreFilter extends ZuulFilter{
    
    /**
     * @return
     */
    @Override
    public int filterOrder() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @return
     */
    @Override
    public Object run() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean shouldFilter() {
        // TODO Auto-generated method stub
        return false;
    }

    

    /**
     * @return
     */
    @Override
    public String filterType() {
        // TODO Auto-generated method stub
        return null;
    }
}
