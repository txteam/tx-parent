/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年10月15日
 * <修改描述:>
 */
package com.tx.component.plugin.script;

import com.tx.core.TxConstants;
import com.tx.core.dbscript.initializer.AbstractTableInitializer;
import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;

/**
  * 任务容器表初始器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年10月15日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class PluginContextTableInitializer extends AbstractTableInitializer {
    
    /** <默认构造函数> */
    public PluginContextTableInitializer() {
        super();
    }
    
    /**
     * 
     */
    @Override
    public String tables(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        //初始化表定义
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        
        sb.append(COMMENT_PREFIX)
                .append("----------table:bd_plugin_instance----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(table_bd_plugin_instance(tableDDLExecutor,
                tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String table_bd_plugin_instance(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "bd_plugin_instance";
        
        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        DDLBuilder<?> ddlBuilder = null;
        
        if (tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = tableDDLExecutor
                    .generateAlterTableDDLBuilder(tableName);
            ddlBuilder = alterDDLBuilder;
        } else {
            createDDLBuilder = tableDDLExecutor
                    .generateCreateTableDDLBuilder(tableName);
            ddlBuilder = createDDLBuilder;
        }
        
        bd_plugin_instance(ddlBuilder);//写入表结构
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            if (tableAutoInitialize) {
                tableDDLExecutor.alter(alterDDLBuilder);
            }
            return alterDDLBuilder.alterSql();
        } else if (createDDLBuilder != null) {
            if (tableAutoInitialize) {
                tableDDLExecutor.create(createDDLBuilder);
            }
            return createDDLBuilder.createSql();
        }
        return "";
    }
    
    /**
     * td_task_status的构建器<br/>
     * <功能详细描述>
     * @param ddlBuilder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void bd_plugin_instance(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("catalog", 64, true, null)
                .newColumnOfVarchar("prefix", 64, true, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("version", 64, true, null)
                .newColumnOfVarchar("author", 64, true, null)
                .newColumnOfBoolean("installed", true, false)
                .newColumnOfBoolean("valid", true, false)
                .newColumnOfVarchar("describeUrl", 255, false, null)
                .newColumnOfVarchar("installUrl", 255, false, null)
                .newColumnOfVarchar("uninstallUrl", 255, false, null)
                .newColumnOfVarchar("settingUrl", 255, false, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfInteger("priority", true, 0)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
        ddlBuilder.newIndex(false, "idx_plugin_instance_0", "createDate");
    }
}
