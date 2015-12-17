/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月19日
 * 项目： com.tx.router
 */
package com.tx.component.messagerouter.context;

/**
 * 消息路由服务接收处理器
 * 
 * @author rain
 * @version [版本号, 2015年11月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface MRSReceiver<Request extends MRSRequest, Response extends MRSResponse> {
    
    /**
     * 
     * 消息路由服务请求类型<br />
     *
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @version [版本号, 2015年11月12日]
     * @author rain
     */
    public Class<? extends Request> getRequestType();
    
    /**
     * 处理消息<br/>
     *
     * @param request 操作请求器
     * @param response 操作返回对象
     *            
     * @return boolean true:继续执行操作|false:停止执行操作直接返回
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void handle(MRSRequest request, MRSResponse response);
}
