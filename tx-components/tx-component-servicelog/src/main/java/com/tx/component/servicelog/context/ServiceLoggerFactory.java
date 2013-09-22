/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import javax.sql.DataSource;

import com.tx.component.servicelog.logger.ServiceLogger;
import com.tx.core.dbscript.model.DataSourceTypeEnum;

/**
 * 业务日志对象工厂<br/>
 *     用于组装系统中使用到的业务日志数据<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ServiceLoggerFactory {
    
    /**
     * 构建需持久的日志实例 
     *<功能详细描述>
     * @param srcObj
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public Object buildLogInstance(Object srcObj);
    
    /**
     * 是否支持对应对象的日志记录<br/>
     *<功能详细描述>
     * @param srcObjType
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public boolean isSupport(Class<?> srcObjType);
    
    /**
      * 构建业务日志记录器<br/>  
      *<功能详细描述>
      * @param dataSourceType
      * @param dataSource
      * @return [参数说明]
      * 
      * @return ServiceLogger<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ServiceLogger<?> buildServiceLogger(Class<?> srcObjType,
            DataSourceTypeEnum dataSourceType, DataSource dataSource);
}
