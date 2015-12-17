/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月12日
 * 项目： com.tx.router
 */
package com.tx.component.messagerouter.context;

import java.util.Date;

import com.tx.component.messagerouter.enums.MRSRequestSourceEnum;
import com.tx.component.messagerouter.enums.MRSRequestTypeEnum;

/**
 * 消息路由服务请求器
 * 
 * @author rain
 * @version [版本号, 2015年11月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface MRSRequest {
    
    /**
     * 
     * 创建一个空的返回器实例<br />
     * 默认返回空值的{@link com.tx.router.mrs.context.DefaultResponse DefaultResponse}实例<br />
     * 需要根据需要自己创建自己的返回器
     *
     * @return MRSResponse 返回器实例
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public MRSResponse buildEmptyResponse();
    
    /**
     * 
     * 消息ID(消息请求返回唯一标识符)<br />
     * 会在 Response 中返回,同时使用此值记录来往报文等<br />
     * 如果返回空值,则自动创建一个 ID
     *
     * @return String 消息 id
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public String getId();
    
    /**
     * 
     * 父消息ID<br />
     * 链式处理消息时,返回上一条消息ID
     *
     * @return String 父消息ID
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public String getParentId();
    
    /**
     * 
     * 获取操作请求来源
     *
     * @return MRSRequestSourceTypeEnum 消息路由服务请求来源
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public MRSRequestSourceEnum getRequestSource();
    
    /**
     * 
     * 消息路由服务请求时间<br />
     * 如果此方法没有被重载,则返回当前实体创建时间
     *
     * @return Date [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public Date getRequestTime();
    
    /**
     * 
     * 获取操作请求类型
     *
     * @return MRSRequestTypeEnum [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public MRSRequestTypeEnum getRequestType();
    
    /**
     * 
     * 获取消息路由服务拦截器
     *
     * @return MRSInterceptor [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年12月17日]
     * @author rain
     */
    public MRSInterceptor getInterceptor();
}
