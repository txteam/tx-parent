/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.defaultimpl;

import javax.sql.DataSource;

import com.tx.component.servicelog.context.BaseServiceLoggerContextBuilder;
import com.tx.component.servicelog.logger.ServiceLogDecorate;
import com.tx.component.servicelog.logger.ServiceLogPersister;
import com.tx.component.servicelog.logger.ServiceLogQuerier;
import com.tx.core.dbscript.model.DataSourceTypeEnum;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-23]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TXServiceLoggerContextBuilder extends
        BaseServiceLoggerContextBuilder {
    
    /**
     * @param srcObjType
     * @return
     */
    @Override
    protected boolean isSupport(Class<?> srcObjType) {
        
        return false;
    }
    
    /**
     * @param srcObjType
     * @param dataSourceType
     * @param dataSource
     * @return
     */
    @Override
    protected ServiceLogPersister buildServiceLogPersister(Class<?> srcObjType,
            DataSourceTypeEnum dataSourceType, DataSource dataSource) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param srcObjType
     * @return
     */
    @Override
    protected ServiceLogDecorate buildServiceLogDecorate(Class<?> srcObjType) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param srcObjType
     * @return
     */
    @Override
    protected <T> ServiceLogQuerier<T> buildServiceLogQuerier(
            Class<T> srcObjType) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
