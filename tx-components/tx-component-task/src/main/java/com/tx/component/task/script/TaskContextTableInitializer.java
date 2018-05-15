/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年10月15日
 * <修改描述:>
 */
package com.tx.component.task.script;

import org.springframework.beans.factory.InitializingBean;

import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.initializer.AbstractTableInitializer;

/**
  * 任务容器表初始器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年10月15日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TaskContextTableInitializer extends AbstractTableInitializer
        implements InitializingBean {
    
    /** 表DDL执行器 */
    protected TableDDLExecutor tableDDLExecutor;
    
    /** <默认构造函数> */
    public TaskContextTableInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskContextTableInitializer(TableDDLExecutor tableDDLExecutor) {
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
    public void tables() {
        //初始化表定义
        table_task_def();
        table_task_status();
        table_task_execute_log();
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void table_task_def() {
        String tableName = "task_def";
        
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
        
        task_def(ddlBuilder);//写入表结构
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
    public static void task_def(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("code", 64, true, null)
                .newColumnOfVarchar("parentCode", 64, false, null)
                .newColumnOfVarchar("className", 256, true, null)
                .newColumnOfVarchar("beanName", 128, true, null)
                .newColumnOfVarchar("methodName", 128, true, null)
                .newColumnOfVarchar("module", 64, true, null)
                .newColumnOfVarchar("attributes", 1024, false, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfBoolean("valid", true, true)
                .newColumnOfBoolean("executable", true, false)
                .newColumnOfInteger("orderPriority", 16, true, 0)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
        
        ddlBuilder.newIndex(true, "idx_code", "code");
        ddlBuilder.newIndex(false, "idx_parentCode", "parentCode");
        ddlBuilder.newIndex(false, "idx_module", "module");
        ddlBuilder.newIndex(false, "idx_createDate", "createDate");
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void table_task_status() {
        String tableName = "task_status";
        
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
        
        task_status(ddlBuilder);//写入表结构
        
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
    public static void task_status(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("taskId", 64, true, null)
                .newColumnOfVarchar("status", 64, true, null)
                .newColumnOfVarchar("result", 64, false, null)
                .newColumnOfDate("startDate", false, true)
                .newColumnOfDate("endDate", false, true)
                .newColumnOfInteger("consuming", 19, false, 0)//size 原值 32
                .newColumnOfVarchar("signature", 128, false, null)
                .newColumnOfVarchar("attributes", 1024, false, null)
                .newColumnOfDate("nextFireDate", false, false)
                .newColumnOfInteger("executeCount", 8, false, 0)
                .newColumnOfDate("successStartDate", false, false)
                .newColumnOfDate("successEndDate", false, false)
                .newColumnOfInteger("successConsuming", 19, false, 0)//size 原值 32
                .newColumnOfInteger("successCount", 8, false, 0)
                .newColumnOfDate("failStartDate", false, false)
                .newColumnOfDate("failEndDate", false, false)
                .newColumnOfInteger("failConsuming", 19, false, 0)//size 原值 32
                .newColumnOfInteger("failCount", 8, false, 0)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
        ddlBuilder.newIndex(true, "idx_taskId", "taskId");
        ddlBuilder.newIndex(false, "idx_createDate", "createDate");
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void table_task_execute_log() {
        String tableName = "task_execute_log";
        
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
        
        task_execute_log(ddlBuilder);//写入表结构
        
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
    public static void task_execute_log(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("operatorId", 64, false, null)
                .newColumnOfVarchar("vcid", 64, false, null)
                .newColumnOfVarchar("taskId", 64, true, null)
                .newColumnOfVarchar("code", 64, true, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfVarchar("result", 64, true, null)
                .newColumnOfDate("startDate", true, true)
                .newColumnOfDate("endDate", true, true)
                .newColumnOfInteger("consuming", 19, true, 0)
                .newColumnOfVarchar("signature", 128, false, null)
                .newColumnOfVarchar("attributes", 512, false, null);
        ddlBuilder.newIndex(false, "idx_taskId", "taskId");
        ddlBuilder.newIndex(false, "idx_startDate", "startDate");
    }
}
