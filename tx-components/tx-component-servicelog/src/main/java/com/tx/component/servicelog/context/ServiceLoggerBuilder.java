/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import com.tx.component.servicelog.context.logger.ServiceLogger;

/**
 * 
 * 业务日志对象建造者<br/>
 * 用于组装系统中使用到的业务日志数据<br/>
 * 
 * @author rain
 * @version [版本号, 2015年11月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ServiceLoggerBuilder {
    
    /**
     * 
     * 日志对象工厂排序<br />
     * 数字越小,越先调用isSupport()进行判断
     *
     * @return int 排序
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月24日]
     * @author rain
     */
    public int order();
    
    /**
     * 
     * 判断是否能支持处理此日志对象
     *
     * @param logObjectType 日志对象类型
     *            
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月24日]
     * @author rain
     */
    public <T> boolean isSupport(Class<T> logObjectType);
    
    /**
     * 
     * 构造业务日志
     *
     * @param logObjectType 日志对象类型
     *            
     * @return ServiceLogger<T> 日志对象
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月24日]
     * @author rain
     */
    public <T> ServiceLogger<T> build(Class<T> logObjectType);
    
}
