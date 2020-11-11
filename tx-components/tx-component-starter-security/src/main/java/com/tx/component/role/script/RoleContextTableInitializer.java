/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月22日
 * <修改描述:>
 */
package com.tx.component.role.script;

import com.tx.core.TxConstants;
import com.tx.core.dbscript.initializer.AbstractTableInitializer;
import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;

/**
 * 安全容器表初始化器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleContextTableInitializer extends AbstractTableInitializer {
    
    /** <默认构造函数> */
    public RoleContextTableInitializer() {
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
        
        //初始化表定义
        sb.append(COMMENT_PREFIX)
                .append("----------table:table_sec_role_type----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        String secRoleType = table_sec_role_type(tableDDLExecutor,
                tableAutoInitialize);
        sb.append(secRoleType);
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------table:table_sec_role----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        String secRole = table_sec_role(tableDDLExecutor, tableAutoInitialize);
        sb.append(secRole);
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------table:table_sec_roleref----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        String secRoleRef = table_sec_roleref(tableDDLExecutor,
                tableAutoInitialize);
        sb.append(secRoleRef);
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------table:table_sec_roleref_his----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        String secRoleRefHis = table_sec_roleref_his(tableDDLExecutor,
                tableAutoInitialize);
        sb.append(secRoleRefHis);
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
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
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("refType", 64, true, null)
                .newColumnOfVarchar("refId", 64, true, null)
                .newColumnOfVarchar("authTypeId", 64, true, null)
                .newColumnOfVarchar("authId", 128, true, null)
                .newColumnOfVarchar("parentId", 64, false, null)
                .newColumnOfVarchar("name", 255, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfBoolean("configAble", true, true);
        
    }
    
    /**
     * 权限项<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String table_sec_role_type(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "sec_role_type";
        
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
        
        sec_role_type(ddlBuilder);//写入表结构
        
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
    public void sec_role_type(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 128, true, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("remark", 512, false, null);
    }
    
    /**
     * 权限项<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String table_sec_role(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "sec_role";
        
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
        
        sec_role(ddlBuilder);//写入表结构
        
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
    public void sec_role(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 128, true, null)
                .newColumnOfVarchar("parentId", 64, false, null)
                .newColumnOfVarchar("roleTypeId", 64, true, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("remark", 512, false, null);
    }
    
    /**
     * 权限引用<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String table_sec_roleref(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "sec_roleref";
        
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
        
        sec_roleref(ddlBuilder);//写入表结构
        ddlBuilder.newIndex(false, "idx_roleId", "roleId");
        ddlBuilder.newIndex(false, "idx_ref", "refId,refType");
        
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
     * 权限引用<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String table_sec_roleref_his(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        String tableName = "sec_roleref_his";
        
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
        
        sec_roleref(ddlBuilder);//写入表结构
        
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
    public static void sec_roleref(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("refType", 64, true, null)
                .newColumnOfVarchar("refId", 64, true, null)
                .newColumnOfVarchar("roleId", 128, true, null)
                .newColumnOfDate("effectiveDate", true, true)
                .newColumnOfDate("expiryDate", false, false)
                .newColumnOfDate("createDate", true, true)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfVarchar("createOperatorId", 64, false, null)
                .newColumnOfVarchar("lastUpdateOperatorId", 64, false, null);
    }
}
