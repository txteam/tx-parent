/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月9日
 * <修改描述:>
 */
package com.tx.component.auth.script;

import org.springframework.beans.factory.InitializingBean;

import com.tx.core.TxConstants;
import com.tx.core.dbscript.initializer.AbstractTableInitializer;
import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;

/**
 * 权限容器表初始<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthContextTableInitializer extends AbstractTableInitializer
        implements InitializingBean {
    
    /** 表DDL执行器 */
    private TableDDLExecutor tableDDLExecutor;
    
    /** 表是否自动初始化 */
    private boolean tableAutoInitialize = false;
    
    /** <默认构造函数> */
    public AuthContextTableInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthContextTableInitializer(TableDDLExecutor tableDDLExecutor) {
        super();
        this.tableDDLExecutor = tableDDLExecutor;
    }
    
    /** <默认构造函数> */
    public AuthContextTableInitializer(TableDDLExecutor tableDDLExecutor,
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
            initialize(tableDDLExecutor, tableAutoInitialize);
        }
    }
    
    /**
     * 
     */
    @Override
    public String tables(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        //初始化表定义
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        
        //初始化表定义
        sb.append(COMMENT_PREFIX)
                .append("----------table:sec_authitem----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        table_sec_authitem(tableDDLExecutor, tableAutoInitialize);
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------table:auth_authref----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        table_sec_authref(tableDDLExecutor, tableAutoInitialize);
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------table:table_auth_authref_his----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        table_sec_authref_his(tableDDLExecutor, tableAutoInitialize);
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * 权限项<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String table_sec_authitem(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "sec_authitem";
        
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
        
        sec_authitem(ddlBuilder);//写入表结构
        
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
     * 权限项<br/>
     * <功能详细描述>
     * @param ddlBuilder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void sec_authitem(DDLBuilder<?> ddlBuilder) {
        /*
        create table sec_authitem
        (
            id varchar(128) not null,             --权限项唯一键key
            module varchar(64) not null,          --系统唯一键module
            version integer not null,             --版本
            authType varchar(64) not null,        --权限类型
            parentId varchar(64),                 --父级权限id
            refType varchar(64),
            refId varchar(64),
            name varchar(256) not null,           --权限项名 
            remark varchar(512),                  --权限项目描述
            attributes varchar(1024),             --权限项目描述
            modifyAble bit not null default 1,    --是否可编辑
            valid bit not null default 1,         --是否有效
            configAble bit not null default 1,    --是否可配置
            primary key(id)
        );
        create unique index idx_un_authitem_00 on sec_authitem(id,module,version);
        create index idx_parentId on sec_authitem(parentId);
        create index idx_module on sec_authitem(module);
        */
        ddlBuilder.newColumnOfVarchar(true, "id", 128, true, null)
                .newColumnOfVarchar("module", 64, true, null)
                .newColumnOfInteger("version", true, null)
                .newColumnOfVarchar("authType", 64, true, null)
                .newColumnOfVarchar("parentId", 64, false, null)
                .newColumnOfVarchar("refType", 64, false, null)
                .newColumnOfVarchar("refId", 64, false, null)
                .newColumnOfVarchar("name", 255, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfVarchar("attributes", 1024, false, null)
                .newColumnOfBoolean("modifyAble", true, false)
                .newColumnOfBoolean("valid", true, true)
                .newColumnOfBoolean("configAble", true, true);
        ddlBuilder.newIndex(true, "idx_un_authitem_00", "id,module,version");
        ddlBuilder.newIndex(false, "idx_parentId", "parentId");
        ddlBuilder.newIndex(false, "idx_module", "module");
        //ddlBuilder.newIndex(true, "idx_unique_auth_01", "");
    }
    
    /**
     * 权限引用<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String table_sec_authref(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "sec_authref";
        
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
        
        sec_authref(ddlBuilder);//写入表结构
        
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
     * 权限引用建表语句<br/>
     * <功能详细描述>
     * @param ddlBuilder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sec_authref(DDLBuilder<?> ddlBuilder) {
        /*
        create table sec_authref(
            id varchar2(64) not null,
            authId varchar2(128) not null,
            refType varchar2(64) not null,
            refId  varchar2(64) not null,
            createOperatorId varchar2(64),
            createDate date default now() not null,
            effectiveDate date not null,
            expiryDate date,
            primary key(id)
        );
        create unique index idx_sec_authref_00 on sec_authref(refid,authreftype,authid,systemid,authType,temp);
        create index idx_auth_authref_01 on auth_authref${tableSuffix}(systemid,refid);
        create index idx_auth_authref_02 on auth_authref${tableSuffix}(systemid,refid,authreftype);
        create index idx_auth_authref_03 on auth_authref${tableSuffix}(systemid,authid);
        create index idx_auth_authref_04 on auth_authref${tableSuffix}(systemid,authid,authreftype);
        create index idx_auth_authref_05 on auth_authref${tableSuffix}(systemid,authreftype);
        create index idx_auth_authref_06 on auth_authref${tableSuffix}(effectiveDate);
        create index idx_auth_authref_07 on auth_authref${tableSuffix}(invalidDate);
        */
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("refType", 64, true, null)
                .newColumnOfVarchar("refId", 64, true, null)
                .newColumnOfVarchar("authId", 128, true, null)
        
                
                .newColumnOfVarchar("authType", 64, true, null)
                .newColumnOfVarchar("parentId", 64, false, null)
                .newColumnOfVarchar("name", 255, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfBoolean("modifyAble", true, true)
                .newColumnOfBoolean("valid", true, true)
                .newColumnOfBoolean("viewAble", true, true)
                .newColumnOfBoolean("configAble", true, true);
        ddlBuilder.newIndex(false, "idx_parentId", "parentId");
        ddlBuilder.newIndex(false, "idx_module", "module");
    }
    
    /**
     * 权限引用历史表<br/>
     * <功能详细描述>
     * @param tableDDLExecutor
     * @param tableAutoInitialize
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String table_sec_authref_his(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "sec_authref_his";
        
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
        
        auth_authref_his(ddlBuilder);//写入表结构
        
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
    
    public static void auth_authref_his(DDLBuilder<?> ddlBuilder) {
        /*
        create table auth_authref_his${tableSuffix}(
            id varchar2(64) not null,
            refId varchar2(255) not null,
            authreftype varchar2(64) not null,
            authid varchar2(255) not null,
            systemid varchar2(64) not null,
            authType varchar2(64) not null,
            createdate date default sysdate not null,
            enddate date not null,
            effectiveDate date,
            invalidDate date,
            createoperid varchar2(64),
            temp number(1) not null default 0
        );
        create index idx_auth_ref_his01 on auth_authref_his${tableSuffix}(systemid,refid);
        create index idx_auth_ref_his02 on auth_authref_his${tableSuffix}(systemid,refid,authreftype);
        create index idx_auth_ref_his03 on auth_authref_his${tableSuffix}(systemid,authid);
        create index idx_auth_ref_his04 on auth_authref_his${tableSuffix}(systemid,authid,authreftype);
        create index idx_auth_ref_his05 on auth_authref_his${tableSuffix}(systemid,authreftype);
        create index idx_auth_ref_his06 on auth_authref_his${tableSuffix}(effectiveDate);
        create index idx_auth_ref_his07 on auth_authref_his${tableSuffix}(invalidDate);
        */
    }
}
