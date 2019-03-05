/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年12月6日
 * <修改描述:>
 */
package com.tx.component.configuration.script;

import org.springframework.beans.factory.InitializingBean;

import com.tx.core.TxConstants;
import com.tx.core.dbscript.initializer.AbstractTableInitializer;
import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;

/**
 * 配置容器表初始化器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年12月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContextTableInitializer extends AbstractTableInitializer
        implements InitializingBean {
    
    /** 表DDL执行器 */
    private TableDDLExecutor tableDDLExecutor;
    
    /** 是否自动执行 */
    private boolean tableAutoInitialize = false;
    
    /** <默认构造函数> */
    public ConfigContextTableInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public ConfigContextTableInitializer(TableDDLExecutor tableDDLExecutor) {
        super();
        this.tableDDLExecutor = tableDDLExecutor;
    }
    
    /** <默认构造函数> */
    public ConfigContextTableInitializer(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        super();
        this.tableDDLExecutor = tableDDLExecutor;
        this.tableAutoInitialize = tableAutoInitialize;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化表定义
        if (this.tableDDLExecutor != null && this.tableAutoInitialize) {
            initialize(this.tableDDLExecutor, this.tableAutoInitialize);
        }
    }
    
    /**
     * @param tableDDLExecutor
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String tables(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        //初始化表定义
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        
        sb.append(COMMENT_PREFIX)
                .append("----------core_config_context----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(table_core_config_context(tableDDLExecutor,
                tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------core_config_context_his----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(table_core_config_context_his(tableDDLExecutor,
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
    public String table_core_config_context(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "core_config_context";
        
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
        
        core_config_context(ddlBuilder);//写入表结构
        ddlBuilder.newIndex(true,
                "idx_core_config_context_00",
                "keyName,systemId");
        
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
    public String table_core_config_context_his(
            TableDDLExecutor tableDDLExecutor, boolean tableAutoInitialize) {
        String tableName = "core_config_context_his";
        
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
        
        core_config_context(ddlBuilder);//写入表结构
        
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
    public static void core_config_context(DDLBuilder<?> ddlBuilder) {
        /*
        drop table if exists core_config_context;
        create table core_config_context(
            id varchar2(64) not null,
            code varchar2(64) not null,
            value varchar2(64) not null,
            name varchar2(64) not null,
            remark varchar2(512),
            createdate timestamp,
            lastupdatedate timestamp,
            modifyAble bit default 1,
            leaf bit default 0,
            module varchar2(64),
            validateexpression varchar2(128),
            primary key(id)
        );
        create unique index idx_core_conf_cont_00 on core_config_context(keyName,systemId);
        */
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("parentId", 64, false, null)
                .newColumnOfVarchar("code", 64, true, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("value", 64, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfVarchar("validateExpression", 128, false, null)
                .newColumnOfVarchar("module", 64, false, null)
                .newColumnOfVarchar("version", 64, false, null)
                .newColumnOfBoolean("modifyAble", true, true)
                .newColumnOfBoolean("leaf", true, false)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
    }
}
