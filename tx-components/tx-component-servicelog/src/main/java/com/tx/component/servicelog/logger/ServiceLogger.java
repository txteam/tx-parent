/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-16
 * <修改描述:>
 */
package com.tx.component.servicelog.logger;

import java.util.List;

import com.tx.core.paged.model.PagedList;

/**
 * 业务日志记录器<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ServiceLogger<T> {
    
    /**
      * 获取业务日志记录器对应的日志类型<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public T getServiceLogType();
    
    /**
      * 记录业务日志<br/>
      *<功能详细描述>
      * @param serviceLog [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void log(T serviceLog);
    
    /**
      * 分页查询业务日志<br/> 
      *<功能详细描述>
      * @param minCreateDate
      * @param maxCreateDate
      * @return [参数说明]
      * 
      * @return List<PagedList<T>> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<PagedList<T>> queryPagedList(String minCreateDate,
            String maxCreateDate);
}
