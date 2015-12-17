/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月19日
 * 项目： com.tx.tsc
 */
package com.tx.component.messagerouter.context;

/**
 * 消息路由服务拦截器
 * 
 * @author rain
 * @version [版本号, 2015年11月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface MRSInterceptor {
    
    /**
     * 
     * 后置处理<br/>
     * 在该逻辑中触发相关事件<br/>
     * 
     * @param request 操作请求器
     * @param response 操作返回对象
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public void logAfterHandle(MRSRequest request, MRSResponse response);
    
    /**
     * 
     * 前置处理<br/>
     * 可做有一些校验请求合法性的功能等<br/>
     * 如果返回 false 则停止调用命令链
     * 
     * @param request 操作请求器
     * @param response 操作返回对象
     *            
     * @return boolean true: 继续执行| false: 停止执行
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public boolean logBeforeHandle(MRSRequest request, MRSResponse response);
    
}
