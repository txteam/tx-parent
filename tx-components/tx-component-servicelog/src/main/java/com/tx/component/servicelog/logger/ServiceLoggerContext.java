package com.tx.component.servicelog.logger;

import java.util.Date;

import com.tx.core.paged.model.PagedList;

/**
  *  业务日志容器接口
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-23]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface ServiceLoggerContext<T> {
    
    /**
      * 向容器中设置属性值<br/>
      *<功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract void setAttribute(String key, Object value);
    
    /**
      * 获取写入的业务日志属性值<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract Object getAttribute(String key);
    
    /**
      * 记录业务日志<br/> 
      *<功能详细描述>
      * @param serviceLogInstance [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract void log(Object serviceLogInstance);
    
    /**
      * 业务日志查询器<br/>
      *<功能详细描述>
      * @param minCreateDate
      * @param maxCreateDate
      * @return [参数说明]
      * 
      * @return PagedList<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract PagedList<T> query(Date minCreateDate, Date maxCreateDate);
    
}