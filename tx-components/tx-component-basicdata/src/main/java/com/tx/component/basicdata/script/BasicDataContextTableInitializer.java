/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年10月15日
 * <修改描述:>
 */
package com.tx.component.basicdata.script;

import org.springframework.beans.factory.InitializingBean;

import com.tx.core.ddlutil.TableInitializer;
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
public class BasicDataContextTableInitializer
        implements InitializingBean, TableInitializer {
    
    /** 表DDL执行器 */
    protected TableDDLExecutor tableDDLExecutor;
    
    /** <默认构造函数> */
    public BasicDataContextTableInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public BasicDataContextTableInitializer(TableDDLExecutor tableDDLExecutor) {
        super();
        this.tableDDLExecutor = tableDDLExecutor;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化表定义
        initialize();
    }
    
    /**
     * 
     */
    @Override
    public void initialize() {
        //初始化表定义
        table_bd_basic_data_type();
        table_bd_data_dict();
        table_bd_data_dict_entry();
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void table_bd_basic_data_type() {
        String tableName = "bd_basic_data_type";
        
        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        DDLBuilder<?> ddlBuilder = null;
        
        if (this.tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = this.tableDDLExecutor
                    .generateAlterTableDDLBuilder(tableName);
            ddlBuilder = alterDDLBuilder;
        } else {
            createDDLBuilder = this.tableDDLExecutor
                    .generateCreateTableDDLBuilder(tableName);
            ddlBuilder = createDDLBuilder;
        }
        
        bd_basic_data_type(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            this.tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            this.tableDDLExecutor.create(createDDLBuilder);
        }
    }
    
    /**
     * td_task_def的构建器<br/>
     * <功能详细描述>
     * @param ddlBuilder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void bd_basic_data_type(DDLBuilder<?> ddlBuilder) {
        /*
        drop table if exists bd_basic_data_type;
        create table bd_basic_data_type(
            id varchar(64) not null,
            type varchar(128) not null,
            code varchar(64) not null,
            module varchar(64) not null,
            name varchar(64) not null,
            tableName varchar(64) not null,
            modifyAble bit not null default 0,
            valid bit not null default 1,
            common bit not null default 1,
            viewType varchar(64) not null,
            remark varchar(512),
            createDate datetime not null default now(),
            lastUpdateDate datetime not null default now(),
            primary key(id)
        );
        create unique index idx_bd_basic_data_type_00 on bd_basic_data_type(type,module);
        create index idx_bd_basic_data_type_01 on bd_basic_data_type(type,type);
        create index idx_bd_basic_data_type_02 on bd_basic_data_type(module);
        */
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("type", 128, true, null)
                .newColumnOfVarchar("code", 64, true, null)
                .newColumnOfVarchar("module", 64, true, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("tableName", 256, true, null)
                .newColumnOfBoolean("modifyAble", true, true)
                .newColumnOfBoolean("valid", true, true)
                .newColumnOfBoolean("common", true, true)
                .newColumnOfVarchar("viewType", 64, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
        ddlBuilder.newIndex(true, "idx_code", "code,type");
        ddlBuilder.newIndex(true, "idx_type", "type");
        ddlBuilder.newIndex(false, "idx_module", "module");
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void table_bd_data_dict() {
        String tableName = "bd_data_dict";
        
        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        DDLBuilder<?> ddlBuilder = null;
        
        if (this.tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = this.tableDDLExecutor
                    .generateAlterTableDDLBuilder(tableName);
            ddlBuilder = alterDDLBuilder;
        } else {
            createDDLBuilder = this.tableDDLExecutor
                    .generateCreateTableDDLBuilder(tableName);
            ddlBuilder = createDDLBuilder;
        }
        
        bd_data_dict(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            this.tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            this.tableDDLExecutor.create(createDDLBuilder);
        }
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
    public static void bd_data_dict(DDLBuilder<?> ddlBuilder) {
        /*
        drop table if exists bd_data_dict;
        create table bd_data_dict(
            id varchar(64) not null,
            basicDataTypeCode varchar(64) not null,
            parentId varchar(64),
            code varchar(64) not null,
            valid bit not null default 1,
            modifyAble bit not null default 1,
            name varchar(64) not null,
            remark varchar(256),
            lastUpdateDate datetime not null default now(),
            createDate datetime not null default now(),
            primary key(id)
        );
        create unique index idx_bd_data_dict_00 on bd_data_dict(code,basicDataTypeCode);
        create index idx_bd_data_dict_01 on bd_data_dict(basicDataTypeCode);
        create index idx_bd_data_dict_02 on bd_data_dict(parentId);
        */
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("parentId", 64, false, null)
                .newColumnOfVarchar("basicDataTypeCode", 64, true, null)
                .newColumnOfVarchar("code", 64, true, null)
                .newColumnOfBoolean("modifyAble", true, true)
                .newColumnOfBoolean("valid", true, true)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
        ddlBuilder.newIndex(true, "idx_data_dict_00", "code,basicDataTypeCode");
        ddlBuilder.newIndex(false,
                "idx_basicDataTypeCode",
                "basicDataTypeCode");
        ddlBuilder.newIndex(false, "idx_parentId", "parentId");
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void table_bd_data_dict_entry() {
        String tableName = "bd_data_dict_entry";
        
        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        DDLBuilder<?> ddlBuilder = null;
        
        if (this.tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = this.tableDDLExecutor
                    .generateAlterTableDDLBuilder(tableName);
            ddlBuilder = alterDDLBuilder;
        } else {
            createDDLBuilder = this.tableDDLExecutor
                    .generateCreateTableDDLBuilder(tableName);
            ddlBuilder = createDDLBuilder;
        }
        
        bd_data_dict_entry(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            this.tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            this.tableDDLExecutor.create(createDDLBuilder);
        }
    }
    
    /**
     * task_execute_log的构建器<br/>
     * <功能详细描述>
     * @param ddlBuilder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void bd_data_dict_entry(DDLBuilder<?> ddlBuilder) {
        /*
        drop table if exists bd_data_dict_entry;
        CREATE TABLE bd_data_dict_entry(
            id varchar(64) not null,
            entityId varchar(64) not null,
            entryKey varchar(64) not null,
            entryValue varchar(255),
            primary key(id)
        );
        create unique index idx_bd_data_dict_entry_00 ON bd_data_dict_entry(entityId,entryKey);
         */
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("entityId", 64, true, null)
                .newColumnOfVarchar("entryKey", 64, true, null)
                .newColumnOfVarchar("entryValue", 255, false, null);
        ddlBuilder.newIndex(true, "idx_dict_entry_00", "entityId,entryKey");
    }
}
