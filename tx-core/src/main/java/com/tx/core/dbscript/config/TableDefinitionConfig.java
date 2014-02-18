/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-18
 * <修改描述:>
 */
package com.tx.core.dbscript.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 表定义配置<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("tableDefinition")
public class TableDefinitionConfig {
    
    /** 表名 */
    private String tableName;
    
    /** 表版本号 */
    private String tableVersion;
    
    /** 数据源脚本配置 */
    @XStreamImplicit(itemFieldName="dataSourceScript")
    private List<DataSourceScriptConfig> dataSourceScriptConfigs;
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * @param 对tableName进行赋值
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * @return 返回 tableVersion
     */
    public String getTableVersion() {
        return tableVersion;
    }
    
    /**
     * @param 对tableVersion进行赋值
     */
    public void setTableVersion(String tableVersion) {
        this.tableVersion = tableVersion;
    }

    /**
     * @return 返回 dataSourceScriptConfigs
     */
    public List<DataSourceScriptConfig> getDataSourceScriptConfigs() {
        return dataSourceScriptConfigs;
    }

    /**
     * @param 对dataSourceScriptConfigs进行赋值
     */
    public void setDataSourceScriptConfigs(
            List<DataSourceScriptConfig> dataSourceScriptConfigs) {
        this.dataSourceScriptConfigs = dataSourceScriptConfigs;
    }
}
