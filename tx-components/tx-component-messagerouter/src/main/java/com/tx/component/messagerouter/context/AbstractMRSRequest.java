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
import com.tx.core.util.UUIDUtils;

/**
 * 消息路由服务请求器默认实现
 * 
 * @author rain
 * @version [版本号, 2015年11月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class AbstractMRSRequest implements MRSRequest {
    
    /** 消息ID */
    private String id;
    
    /** 父消息ID */
    private String parentId;
    
    /** 消息路由服务请求时间 */
    private Date requestTime;
    
    /** 消息路由服务请求来源 */
    private MRSRequestSourceEnum requestSource;
    
    /** 消息路由服务请求类型 */
    private MRSRequestTypeEnum requestType;
    
    /** 消息路由服务拦截器 */
    private MRSInterceptor interceptor;
    
    /**
     * 构造来源和类型请求器<br>
     * 自动调用 uuid 生成器生成 id
     * 
     * @param requestSource 获取操作请求来源
     * @param requestType 获取操作请求类型
     *            
     * @version [版本号, 2015年11月19日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public AbstractMRSRequest(MRSRequestSourceEnum requestSource, MRSRequestTypeEnum requestType) {
        this(UUIDUtils.generateUUID(), null, new Date(), requestSource, requestType, null);
    }
    
    /**
     * 构造来源和类型请求器<br>
     * 自动调用 uuid 生成器生成 id
     * 
     * @param requestSource 获取操作请求来源
     * @param requestType 获取操作请求类型
     * @param interceptor 拦截器
     *            
     * @version [版本号, 2015年11月19日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public AbstractMRSRequest(MRSRequestSourceEnum requestSource, MRSRequestTypeEnum requestType, MRSInterceptor interceptor) {
        this(UUIDUtils.generateUUID(), null, new Date(), requestSource, requestType, interceptor);
    }
    
    /**
     * 构造普通请求器
     * 
     * @param id 消息ID
     * @param requestSource 获取操作请求来源
     * @param requestType 获取操作请求类型
     *            
     * @version [版本号, 2015年11月19日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public AbstractMRSRequest(String id, MRSRequestSourceEnum requestSource, MRSRequestTypeEnum requestType) {
        this(id, null, new Date(), requestSource, requestType, null);
    }
    
    /**
     * 构造有父ID的请求器
     * 
     * @param id 消息ID
     * @param parentId 父消息ID
     * @param requestTime 请求时间
     * @param requestSource 获取操作请求来源
     * @param requestType 获取操作请求类型
     * @param interceptor 拦截器
     *            
     * @version [版本号, 2015年11月19日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public AbstractMRSRequest(String id, String parentId, Date requestTime, MRSRequestSourceEnum requestSource, MRSRequestTypeEnum requestType, MRSInterceptor interceptor) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.requestSource = requestSource;
        this.requestType = requestType;
        this.interceptor = interceptor;
    }
    
    @Override
    public MRSResponse buildEmptyResponse() {
        return new DefaultResponse<Object>();
    }
    
    /** 消息ID */
    @Override
    public String getId() {
        return id;
    }
    
    /** 消息路由服务拦截器 */
    @Override
    public MRSInterceptor getInterceptor() {
        return interceptor;
    }
    
    /** 父消息ID */
    @Override
    public String getParentId() {
        return parentId;
    }
    
    /** 获取操作请求来源 */
    @Override
    public MRSRequestSourceEnum getRequestSource() {
        return requestSource;
    }
    
    /** 消息路由服务请求时间 */
    @Override
    public Date getRequestTime() {
        return requestTime;
    }
    
    /** 获取操作请求类型 */
    @Override
    public MRSRequestTypeEnum getRequestType() {
        return requestType;
    }
    
    /** 消息ID */
    public void setId(String id) {
        this.id = id;
    }
    
    /** 消息路由服务拦截器 */
    public void setInterceptor(MRSInterceptor interceptor) {
        this.interceptor = interceptor;
    }
    
    /** 父消息ID */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    /** 获取操作请求来源 */
    public void setRequestSource(MRSRequestSourceEnum requestSource) {
        this.requestSource = requestSource;
    }
    
    /** 消息路由服务请求时间 */
    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
    
    /** 获取操作请求类型 */
    public void setRequestType(MRSRequestTypeEnum requestType) {
        this.requestType = requestType;
    }
}
