/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-19
 * <修改描述:>
 */
package com.tx.core.dbscript.tableresource;

import java.util.Map;

import com.tx.core.dbscript.model.DataSourceTypeEnum;

/**
 * 表资源<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TableResource {
    
    /**
      * TableResource表名<br/>
      *<功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getTableName(Map<String, String> params);
    
    /** 
      * 表是否已存在<br/>
      *<功能详细描述>
      * @param dataSourceType
      * @param params
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isExsit(DataSourceTypeEnum dataSourceType,
            Map<String, String> params);
    
    /**
     * 是否需要升级<br/>
      *<功能简述>
      *<功能详细描述>
      * @param dataSourceType
      * @param version
      * @param params
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isNeedUpdate(DataSourceTypeEnum dataSourceType,
            String version, Map<String, String> params);
    
    /**
      * 创建表<br/>
      *<功能详细描述>
      * @param dataSourceType
      * @param parameters [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void createTable(DataSourceTypeEnum dataSourceType,
            Map<String, String> params);
    
    /**
      * 初始化表数据<br/>
      *<功能详细描述>
      * @param dataSourceType
      * @param parameters [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void initTableData(DataSourceTypeEnum dataSourceType,
            Map<String, String> params);
    
    /**
      * 备份表
      *<功能详细描述>
      * @param dataSourceType
      * @param parameters [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void backupTable(DataSourceTypeEnum dataSourceType,
            Map<String, String> params);
    
    /**
      * 升级表<br/>
      *<功能详细描述>
      * @param dataSourceType
      * @param parameters [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void updateTable(DataSourceTypeEnum dataSourceType, String version,
            Map<String, String> params);
    
    /**
      * 更新表数据<br/>
      *<功能详细描述>
      * @param dataSourceType
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void updateTableData(DataSourceTypeEnum dataSourceType,
            String version, Map<String, String> params);
    
}
