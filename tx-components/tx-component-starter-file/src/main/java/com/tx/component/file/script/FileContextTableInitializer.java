/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年10月15日
 * <修改描述:>
 */
package com.tx.component.file.script;

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
public class FileContextTableInitializer extends AbstractTableInitializer {
    
    /** <默认构造函数> */
    public FileContextTableInitializer() {
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
        
        //fc_file_definition
        sb.append(COMMENT_PREFIX)
                .append("----------table:fc_file_definition----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(table_fc_file_definition(tableDDLExecutor,
                tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        //fc_file_definition_his
        sb.append(COMMENT_PREFIX)
                .append("----------table:fc_file_definition_his----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(table_fc_file_definition_his(tableDDLExecutor,
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
    public String table_fc_file_definition(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "fc_file_definition";
        
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
        
        fc_file_definition(ddlBuilder);//写入表结构
        ddlBuilder.newIndex(true,
                "idx_fc_file_def_00",
                "relativePath,catalog,module");
        ddlBuilder.newIndex("idx_fc_file_def_01", "refId,refType");
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
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String table_fc_file_definition_his(
            TableDDLExecutor tableDDLExecutor, boolean tableAutoInitialize) {
        String tableName = "fc_file_definition_his";
        
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
        
        fc_file_definition(ddlBuilder);//写入表结构
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
    public static void fc_file_definition(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                //所属目录必填
                .newColumnOfVarchar("module", 64, true, null)
                //所属目录必填
                .newColumnOfVarchar("catalog", 64, true, null)
                //该字段太长的话建不了索引
                .newColumnOfVarchar("relativePath", 128, true, null)
                //该字段太长的话建不了索引
                .newColumnOfVarchar("folderId", 64, false, null)
                //文件一定存在
                .newColumnOfVarchar("filename", 64, false, null)
                //文件后缀非必填，其不一定存在
                .newColumnOfVarchar("filenameExtension", 64, false, null)
                //关联业务类型非必填
                .newColumnOfVarchar("refType", 64, false, null)
                //关联业务id
                .newColumnOfVarchar("refId", 64, false, null)
                //额外属性存放
                .newColumnOfVarchar("attributes", 1000, false, null)
                //是否删除（软删除）
                .newColumnOfBoolean("deleted", true, false)
                //删除时间（软删除）
                .newColumnOfDate("deleteDate", false, false)
                //最后更新时间
                .newColumnOfDate("lastUpdateDate", true, true)
                //创建时间
                .newColumnOfDate("createDate", true, true);
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
    public static void fc_folder_definition(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                //所属目录必填
                .newColumnOfVarchar("module", 64, true, null)
                //所属目录必填
                .newColumnOfVarchar("catalog", 64, true, null)
                //该字段太长的话建不了索引
                .newColumnOfVarchar("relativePath", 128, true, null)
                //文件一定存在
                .newColumnOfVarchar("parentId", 64, false, null)
                //文件一定存在
                .newColumnOfVarchar("filename", 64, false, null)
                //关联业务类型非必填
                .newColumnOfVarchar("refType", 64, false, null)
                //关联业务id
                .newColumnOfVarchar("refId", 64, false, null)
                //额外属性存放
                .newColumnOfVarchar("attributes", 1000, false, null)
                //是否删除（软删除）
                .newColumnOfBoolean("deleted", true, false)
                //删除时间（软删除）
                .newColumnOfDate("deleteDate", false, false)
                //最后更新时间
                .newColumnOfDate("lastUpdateDate", true, true)
                //创建时间
                .newColumnOfDate("createDate", true, true);
    }
}
