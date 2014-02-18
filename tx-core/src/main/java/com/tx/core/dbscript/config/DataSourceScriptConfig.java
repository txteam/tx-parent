/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-19
 * <修改描述:>
 */
package com.tx.core.dbscript.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.tx.core.dbscript.model.DataSourceTypeEnum;


 /**
  * 数据源配置<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-12-19]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("dataSourceScript")
public class DataSourceScriptConfig {
    
    @XStreamAsAttribute
    private DataSourceTypeEnum dataSourceType;
    
    /** 创建表脚本 */
    private String createTableScript;
    
    /** 默认更新表脚本 */
    private String defaultUpdateTableScript;
    
    @XStreamImplicit(itemFieldName = "updateTableScript")
    private List<UpdateTableScriptConfig> updateTableScripts;

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

    /**
     * @return 返回 createTableScript
     */
    public String getCreateTableScript() {
        return createTableScript;
    }

    /**
     * @param 对createTableScript进行赋值
     */
    public void setCreateTableScript(String createTableScript) {
        this.createTableScript = createTableScript;
    }

    /**
     * @return 返回 defaultUpdateTableScript
     */
    public String getDefaultUpdateTableScript() {
        return defaultUpdateTableScript;
    }

    /**
     * @param 对defaultUpdateTableScript进行赋值
     */
    public void setDefaultUpdateTableScript(String defaultUpdateTableScript) {
        this.defaultUpdateTableScript = defaultUpdateTableScript;
    }

    /**
     * @return 返回 updateTableScripts
     */
    public List<UpdateTableScriptConfig> getUpdateTableScripts() {
        return updateTableScripts;
    }

    /**
     * @param 对updateTableScripts进行赋值
     */
    public void setUpdateTableScripts(
            List<UpdateTableScriptConfig> updateTableScripts) {
        this.updateTableScripts = updateTableScripts;
    }
}
