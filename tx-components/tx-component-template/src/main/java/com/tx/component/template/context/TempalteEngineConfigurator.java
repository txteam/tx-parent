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
    
    private DataSource dataSource;
    
    private DataSourceTypeEnum dataSourceType;
    
    private String systemId;

    /**
     * @return 返回 systemId
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * @param 对systemId进行赋值
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
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
