/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月19日
 * 项目： com.tx.router
 */
package com.tx.component.messagerouter.context;

/**
 * 
 * 消息路由服务返回器
 * 
 * @author rain
 * @version [版本号, 2015年11月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface MRSResponse {
    
    /**
     * 
     * 添加返回值
     *
     * @param key 主键
     * @param value 值
     *            
     * @return MRSResponse 返回器本身
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public void put(String key, Object value);
    
    /**
     * 
     * 获取消息返回的主体(一般是报文或者接口返回的信息(一般都是文本,json,xml))
     *
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public Object getBody();
    
    /**
     * 
     * 获取返回值
     *
     * @param key 主键
     *            
     * @return Object 返回值
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public Object getValue(String key);
    
    /**
     * 
     * 移除指定主键的值
     *
     * @param key 主键
     *            
     * @return MRSResponse 返回器本身
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月19日]
     * @author rain
     */
    public void remove(String key);
}
