/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.test.dbscript;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.component.task.script.TaskContextTableInitializer;
import com.tx.core.datasource.DataSourceFinder;
import com.tx.core.datasource.finder.TomcatDataSourceFinder;
import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月1日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TaskContextTableInitializerTest {
    
    public static void main(String[] args) {
        DataSourceFinder finder = new TomcatDataSourceFinder(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://120.24.75.25:3306/test?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
                "pqy", "pqy");
        DataSource ds = finder.getDataSource();
        JdbcTemplate jt = new JdbcTemplate(ds);
        TableDDLExecutor tableDDLExecutor = new MysqlTableDDLExecutor(jt);
        
        //创建表
        if (tableDDLExecutor.exists("task_def")) {
            tableDDLExecutor.drop("task_def");
        }
        table_td_task_def(tableDDLExecutor);//创建表
        table_td_task_def(tableDDLExecutor);//更新表
        
        if (tableDDLExecutor.exists("task_status")) {
            tableDDLExecutor.drop("task_status");
        }
        table_td_task_status(tableDDLExecutor);//创建表
        table_td_task_status(tableDDLExecutor);//更新表
        
        if (tableDDLExecutor.exists("task_execute_log")) {
            tableDDLExecutor.drop("task_execute_log");
        }
        table_task_execute_log(tableDDLExecutor);//创建表
        table_task_execute_log(tableDDLExecutor);//更新表
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private static void table_td_task_def(TableDDLExecutor tableDDLExecutor) {
        String tableName = "task_def";
        
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
        
        TaskContextTableInitializer.task_def(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            System.out.println("检测到需要升级.task_def");
            tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            System.out.println("创建表成功.task_def");
            tableDDLExecutor.create(createDDLBuilder);
        } else {
            System.out.println("无需进行升级.task_def");
        }
        
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private static void table_td_task_status(
            TableDDLExecutor tableDDLExecutor) {
        String tableName = "task_status";
        
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
        
        TaskContextTableInitializer.task_status(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            System.out.println("检测到需要升级.task_status");
            tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            System.out.println("创建表成功.task_status");
            tableDDLExecutor.create(createDDLBuilder);
        } else {
            System.out.println("无需进行升级.task_status");
        }
    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private static void table_task_execute_log(
            TableDDLExecutor tableDDLExecutor) {
        String tableName = "task_execute_log";
        
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
        
        TaskContextTableInitializer.task_execute_log(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            System.out.println("检测到需要升级.task_execute_log");
            tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            System.out.println("创建表成功.task_execute_log");
            tableDDLExecutor.create(createDDLBuilder);
        } else {
            System.out.println("无需进行升级.task_execute_log");
        }
    }
}
