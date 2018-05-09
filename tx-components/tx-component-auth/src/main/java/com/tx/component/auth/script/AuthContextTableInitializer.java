/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月9日
 * <修改描述:>
 */
package com.tx.component.auth.script;

import org.springframework.beans.factory.InitializingBean;

import com.tx.core.ddlutil.TableInitializer;
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
public class AuthContextTableInitializer
        implements InitializingBean, TableInitializer {
    
    /** 表DDL执行器 */
    protected TableDDLExecutor tableDDLExecutor;
    
    /** <默认构造函数> */
    public AuthContextTableInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthContextTableInitializer(TableDDLExecutor tableDDLExecutor) {
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
        table_auth_authitem();
        table_auth_authref();
        table_auth_authref_his();
    }
    
    /**
     * 权限项<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void table_auth_authitem() {
        String tableName = "auth_authitem";
        
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
        
        auth_authitem(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            this.tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            this.tableDDLExecutor.create(createDDLBuilder);
        }
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
    public void auth_authitem(DDLBuilder<?> ddlBuilder) {
        /*
        create table auth_authitem
        (
            id varchar(64) not null,              --权限项唯一键key 
            authType varchar(64) not null,        --权限类型
            module varchar(64) not null,          --系统唯一键module
            parentId varchar(64),                 --父级权限id
            refId varchar(64),   
            refType varchar(64),
            name varchar(256),                    --权限项名 
            remark varchar(1024),                 --权限项目描述
            viewAble bit not null default 1,      --是否可见
            modifyAble bit not null default 1,    --是否可编辑
            valid bit not null default 1,         --是否有效
            configAble bit not null default 1,    --是否可配置
            primary key(id,systemid)
        );
        create index idx_parentId on auth_authitem(parentId);
        create index idx_module on auth_authitem(module);
        */
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("module", 64, true, null)
                .newColumnOfVarchar("authType", 64, true, null)
                .newColumnOfVarchar("refId", 64, false, null)
                .newColumnOfVarchar("refType", 64, false, null)
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
     * 权限引用<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void table_auth_authref() {
        String tableName = "auth_authref";
        
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
        
        auth_authref(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            this.tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            this.tableDDLExecutor.create(createDDLBuilder);
        }
    }
    
    public static void auth_authref(DDLBuilder<?> ddlBuilder) {
        
    }
    
    private void table_auth_authref_his() {
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
    
    public static void auth_authref_his(DDLBuilder<?> ddlBuilder) {
        
    }
}
