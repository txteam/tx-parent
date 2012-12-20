package com.tx.core.datasource;

import javax.sql.DataSource;

/**
  * <数据源查找生成器>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-10-7]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface DataSourceFinder {
    
    /**
      *<根据数据源名获取数据源>
      *<功能详细描述>
      * @param name
      * @return [参数说明]
      * 
      * @return DataSource [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract DataSource getDataSource(String name);
    
}