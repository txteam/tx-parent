/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月9日
 * <修改描述:>
 */
package com.tx.component.security.script;

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
public class JwtTableInitializer extends AbstractTableInitializer
        implements InitializingBean {
    
    /** 表DDL执行器 */
    private TableDDLExecutor tableDDLExecutor;
    
    /** 表是否自动初始化 */
    private boolean tableAutoInitialize = false;
    
    /** <默认构造函数> */
    public JwtTableInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public JwtTableInitializer(TableDDLExecutor tableDDLExecutor) {
        super();
        this.tableDDLExecutor = tableDDLExecutor;
    }
    
    /** <默认构造函数> */
    public JwtTableInitializer(TableDDLExecutor tableDDLExecutor,
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
                .append("----------table:sec_jwt_signing_key----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        table_sec_jwt_signing_key(tableDDLExecutor, tableAutoInitialize);
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
    private String table_sec_jwt_signing_key(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "sec_jwt_signing_key";
        
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
        
        sec_jwt_signing_key(ddlBuilder);//写入表结构
        
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
    public void sec_jwt_signing_key(DDLBuilder<?> ddlBuilder) {
        /*
        create table sec_jwt_signing_key
        (
            id varchar(64) not null,                        -- 唯一键
            type varchar(64) not null default 'DEFAULT',    -- 签名类型
            signingKeyCode varchar(64) not null,            -- 
            signingKey varchar(64) not null,                --
            
            key varchar(64) not null,               --权限类型
            signKey varchar(64) not null,           --系统唯一键module
            createDate
            
            
            parentId varchar(64),                 --父级权限id
            refId varchar(64),   
            refType varchar(64),
            name varchar(256),                    --权限项名 
            remark varchar(1024),                 --权限项目描述
            viewAble bit not null default 1,      --是否可见
            modifyAble bit not null default 1,    --是否可编辑
            valid bit not null default 1,         --是否有效
            configAble bit not null default 1,    --是否可配置
            
            
            primary key(id)
        );
        create index idx_parentId on auth_authitem(parentId);
        create index idx_module on auth_authitem(module);
        */
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("authType", 64, true, null)
                .newColumnOfVarchar("module", 64, true, null)
                .newColumnOfInteger("version", 10, true, null)
                .newColumnOfVarchar("parentId", 64, false, null)
                .newColumnOfVarchar("refType", 64, false, null)
                .newColumnOfVarchar("refId", 64, false, null)
                .newColumnOfVarchar("name", 255, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfBoolean("modifyAble", true, false)
                .newColumnOfBoolean("valid", true, true)
                .newColumnOfBoolean("configAble", true, true);
        ddlBuilder.newIndex(false, "idx_parentId", "parentId");
        ddlBuilder.newIndex(false, "idx_module", "module");
        //ddlBuilder.newIndex(true, "idx_unique_auth_01", "");
    }
    
}
