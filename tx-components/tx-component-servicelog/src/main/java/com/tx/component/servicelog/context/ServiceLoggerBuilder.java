/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import org.springframework.jdbc.core.JdbcTemplate;

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
public interface ServiceLoggerBuilder {
    
    /**
      * 构造业务日志容器<br/>
      *<功能详细描述>
      * @param srcObjType
      * @param dataSourceType
      * @param dataSource
      * @return [参数说明]
      * 
      * @return ServiceLoggerContext<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    <T> ServiceLogger<T> build(Class<T> srcObjType,
            DataSourceTypeEnum dataSourceType, JdbcTemplate jdbcTemplate);
    
}
