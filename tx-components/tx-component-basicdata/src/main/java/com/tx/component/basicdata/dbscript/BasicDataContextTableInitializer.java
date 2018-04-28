/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年10月15日
 * <修改描述:>
 */
package com.tx.component.basicdata.dbscript;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

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
public class BasicDataContextTableInitializer implements InitializingBean {
    
    /** 表DDL执行器 */
    @Resource(name = "taskContext.tableDDLExecutor")
    protected TableDDLExecutor tableDDLExecutor;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化表定义
        table_td_task_def();
        table_td_task_status();
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
    private void table_td_task_def() {
        String tableName = "task_def";
        
        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        DDLBuilder<?> ddlBuilder = null;
        
        if (this.tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = this.tableDDLExecutor.generateAlterTableDDLBuilder(tableName);
            ddlBuilder = alterDDLBuilder;
        } else {
            createDDLBuilder = this.tableDDLExecutor.generateCreateTableDDLBuilder(tableName);
            ddlBuilder = createDDLBuilder;
        }
        
        td_task_def(ddlBuilder);//写入表结构
        ddlBuilder.newIndex(true, "idx_task_def_00", "code");
        ddlBuilder.newIndex(false, "idx_task_def_01", "createDate");
        
        if (alterDDLBuilder != null && alterDDLBuilder.isNeedAlter(false, false)) {
            this.tableDDLExecutor.alter(alterDDLBuilder, false, false);
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
    private void td_task_def(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("code", 64, true, null)
                .newColumnOfVarchar("parentCode", 64, false, null)
                .newColumnOfVarchar("className", 256, true, null)
                .newColumnOfVarchar("beanName", 128, true, null)
                .newColumnOfVarchar("methodName", 128, true, null)
                .newColumnOfVarchar("factory", 128, false, "DEFAULT")
                .newColumnOfVarchar("attributes", 1024, false, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfBoolean("valid", true, true)
                .newColumnOfBoolean("executable", true, false)
                .newColumnOfInteger("orderPriority", 16, true, 0)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void table_td_task_status() {
        String tableName = "task_status";
        
        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        DDLBuilder<?> ddlBuilder = null;
        
        if (this.tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = this.tableDDLExecutor.generateAlterTableDDLBuilder(tableName);
            ddlBuilder = alterDDLBuilder;
        } else {
            createDDLBuilder = this.tableDDLExecutor.generateCreateTableDDLBuilder(tableName);
            ddlBuilder = createDDLBuilder;
        }
        
        td_task_status(ddlBuilder);//写入表结构
        ddlBuilder.newIndex(true, "idx_task_status_00", "taskId");
        ddlBuilder.newIndex(false, "idx_task_status_01", "createDate");
        
        if (alterDDLBuilder != null && alterDDLBuilder.isNeedAlter(false, false)) {
            this.tableDDLExecutor.alter(alterDDLBuilder, false, false);
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
    private void td_task_status(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("taskId", 64, true, null)
                .newColumnOfVarchar("status", 64, true, null)
                .newColumnOfVarchar("result", 64, false, null)
                .newColumnOfDate("startDate", false, true)
                .newColumnOfDate("endDate", false, true)
                .newColumnOfBigDecimal("consuming", 8, 0, false, BigDecimal.ZERO)//size 原值 32
                .newColumnOfVarchar("signature", 128, false, null)
                .newColumnOfVarchar("attributes", 1024, false, null)
                .newColumnOfDate("nextFireDate", false, false)
                .newColumnOfBigDecimal("executeCount", 8, 0, false, BigDecimal.ZERO)
                .newColumnOfDate("successStartDate", false, false)
                .newColumnOfDate("successEndDate", false, false)
                .newColumnOfBigDecimal("successConsuming", 8, 0, false, BigDecimal.ZERO)//size 原值 32
                .newColumnOfBigDecimal("successCount", 8, 0, false, BigDecimal.ZERO)
                .newColumnOfDate("failStartDate", false, false)
                .newColumnOfDate("failEndDate", false, false)
                .newColumnOfBigDecimal("failConsuming", 8, 0, false, BigDecimal.ZERO)//size 原值 32
                .newColumnOfBigDecimal("failCount", 8, 0, false, BigDecimal.ZERO)
                .newColumnOfDate("lastUpdateDate", true, true)
                .newColumnOfDate("createDate", true, true);
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void table_task_execute_log() {
        String tableName = "task_execute_log";
        
        CreateTableDDLBuilder createDDLBuilder = null;
        AlterTableDDLBuilder alterDDLBuilder = null;
        DDLBuilder<?> ddlBuilder = null;
        
        if (this.tableDDLExecutor.exists(tableName)) {
            alterDDLBuilder = this.tableDDLExecutor.generateAlterTableDDLBuilder(tableName);
            ddlBuilder = alterDDLBuilder;
        } else {
            createDDLBuilder = this.tableDDLExecutor.generateCreateTableDDLBuilder(tableName);
            ddlBuilder = createDDLBuilder;
        }
        
        task_execute_log(ddlBuilder);//写入表结构
        ddlBuilder.newIndex(false, "idx_task_execute_log_00", "taskId");
        ddlBuilder.newIndex(false, "idx_task_execute_log_01", "startDate");
        
        if (alterDDLBuilder != null && alterDDLBuilder.isNeedAlter(false, false)) {
            this.tableDDLExecutor.alter(alterDDLBuilder, false, false);
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
    private void task_execute_log(DDLBuilder<?> ddlBuilder) {
        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
                .newColumnOfVarchar("operatorId", 64, false, null)
                .newColumnOfVarchar("vcid", 64, false, null)
                .newColumnOfVarchar("taskId", 64, true, null)
                .newColumnOfVarchar("code", 64, true, null)
                .newColumnOfVarchar("name", 64, true, null)
                .newColumnOfVarchar("remark", 512, false, null)
                .newColumnOfVarchar("result", 64, false, null)
                .newColumnOfDate("startDate", false, true)
                .newColumnOfDate("endDate", false, true)
                .newColumnOfBigDecimal("consuming", 32, 0, false, BigDecimal.ZERO)
                .newColumnOfVarchar("signature", 128, false, null)
                .newColumnOfVarchar("attributes", 512, false, null);
    }
}
