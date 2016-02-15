/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年1月4日
 * <修改描述:>
 */
package com.tx.core.support.methodinvoke.support;


/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年1月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface InvokeBindingInitializer {
    
    /**
     * Initialize the given DataBinder for the given request.
     * @param binder the DataBinder to initialize
     * @param request the web request that the data binding happens within
     */
    void initBinder(InvokeDataBinder binder, InvokeRequest request);
}
