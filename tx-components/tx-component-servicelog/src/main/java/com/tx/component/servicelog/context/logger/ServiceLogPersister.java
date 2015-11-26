/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-16
 * <修改描述:>
 */
package com.tx.component.servicelog.context.logger;

/**
 * 业务日志记录器<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-12-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ServiceLogPersister<T> {
    
    /**
     * 记录业务日志<br/>
     * 
     * @param serviceLog [参数说明]
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void persist(T logInstance);
}
