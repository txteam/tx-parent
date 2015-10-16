/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-12
 * <修改描述:>
 */
package com.tx.component.template.context;

import javax.sql.DataSource;

import com.tx.core.dbscript.model.DataSourceTypeEnum;


 /**
  * 模板引擎配置器<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-12]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TempalteEngineConfigurator {
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 数据源类型 */
    private DataSourceTypeEnum dataSourceType;
    
    /** 表前缀:具有表前缀的功能，就无需systemId的支持了，这里的容器不同于权限容器，权限容器有时候，既需要表前缀又需要系统id去支撑功能 */
    private String tablePrefix;
    
    /**
     * @return 返回 tablePrefix
     */
    public String getTablePrefix() {
        return tablePrefix;
    }

    /**
     * @param 对tablePrefix进行赋值
     */
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    /**
     * @return 返回 dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return 返回 dataSourceType
     */
    public DataSourceTypeEnum getDataSourceType() {
        return dataSourceType;
    }

    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
}
