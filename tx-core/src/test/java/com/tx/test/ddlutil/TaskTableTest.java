///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2018年5月1日
// * <修改描述:>
// */
//package com.tx.test.ddlutil;
//
//import java.math.BigDecimal;
//
//import javax.sql.DataSource;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import com.tx.core.datasource.DataSourceFinder;
//import com.tx.core.datasource.finder.SimpleDataSourceFinder;
//import com.tx.core.ddlutil.builder.DDLBuilder;
//import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
//import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
//import com.tx.core.ddlutil.executor.TableDDLExecutor;
//import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;
//
///**
//  * <功能简述>
//  * <功能详细描述>
//  * 
//  * @author  Administrator
//  * @version  [版本号, 2018年5月1日]
//  * @see  [相关类/方法]
//  * @since  [产品/模块版本]
//  */
//public class TaskTableTest {
//    
//    public static void main(String[] args) {
//        DataSourceFinder finder = new SimpleDataSourceFinder(
//                "com.mysql.jdbc.Driver",
//                "jdbc:mysql://120.24.75.25:3306/test?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
//                "pqy", "pqy");
//        DataSource ds = finder.getDataSource();
//        JdbcTemplate jt = new JdbcTemplate(ds);
//        TableDDLExecutor tableDDLExecutor = new MysqlTableDDLExecutor(jt);
//        
//        //创建表
//        if (tableDDLExecutor.exists("task_def")) {
//            tableDDLExecutor.drop("task_def");
//        }
//        table_td_task_def(tableDDLExecutor);//创建表
//        table_td_task_def(tableDDLExecutor);//更新表
//        
//        if (tableDDLExecutor.exists("task_status")) {
//            tableDDLExecutor.drop("task_status");
//        }
//        table_td_task_status(tableDDLExecutor);//创建表
//        table_td_task_status(tableDDLExecutor);//更新表
//        
//        if (tableDDLExecutor.exists("task_execute_log")) {
//            tableDDLExecutor.drop("task_execute_log");
//        }
//        table_task_execute_log(tableDDLExecutor);//创建表
//        table_task_execute_log(tableDDLExecutor);//更新表
//    }
//    
//    /**
//     * 核心文件定义表<br/>
//     * <功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private static void table_td_task_def(TableDDLExecutor tableDDLExecutor) {
//        String tableName = "task_def";
//        
//        CreateTableDDLBuilder createDDLBuilder = null;
//        AlterTableDDLBuilder alterDDLBuilder = null;
//        DDLBuilder<?> ddlBuilder = null;
//        
//        if (tableDDLExecutor.exists(tableName)) {
//            alterDDLBuilder = tableDDLExecutor
//                    .generateAlterTableDDLBuilder(tableName);
//            ddlBuilder = alterDDLBuilder;
//        } else {
//            createDDLBuilder = tableDDLExecutor
//                    .generateCreateTableDDLBuilder(tableName);
//            ddlBuilder = createDDLBuilder;
//        }
//        
//        td_task_def(ddlBuilder);//写入表结构
//        ddlBuilder.newIndex(true, "idx_task_def_00", "code");
//        ddlBuilder.newIndex(false, "idx_task_def_01", "createDate");
//        
//        if (alterDDLBuilder != null
//                && alterDDLBuilder.isNeedAlter(false, false)) {
//            System.out.println("检测到需要升级.task_def");
//            tableDDLExecutor.alter(alterDDLBuilder, false, false);
//        } else if (createDDLBuilder != null) {
//            System.out.println("创建表成功.task_def");
//            tableDDLExecutor.create(createDDLBuilder);
//        } else {
//            System.out.println("无需进行升级.task_def");
//        }
//        
//    }
//    
//    /**
//     * td_task_def的构建器<br/>
//     * <功能详细描述>
//     * @param ddlBuilder [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private static void td_task_def(DDLBuilder<?> ddlBuilder) {
//        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
//                .newColumnOfVarchar("code", 64, true, null)
//                .newColumnOfVarchar("parentCode", 64, false, null)
//                .newColumnOfVarchar("className", 256, true, null)
//                .newColumnOfVarchar("beanName", 128, true, null)
//                .newColumnOfVarchar("methodName", 128, true, null)
//                .newColumnOfVarchar("factory", 128, false, "DEFAULT")
//                .newColumnOfVarchar("attributes", 1024, false, null)
//                .newColumnOfVarchar("name", 64, true, null)
//                .newColumnOfVarchar("remark", 512, false, null)
//                .newColumnOfBoolean("valid", true, true)
//                .newColumnOfBoolean("executable", true, false)
//                .newColumnOfInteger("orderPriority", 16, true, 0)
//                .newColumnOfDate("lastUpdateDate", true, true)
//                .newColumnOfDate("createDate", true, true);
//    }
//    
//    /**
//     * 核心文件定义表<br/>
//     * <功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private static void table_td_task_status(
//            TableDDLExecutor tableDDLExecutor) {
//        String tableName = "task_status";
//        
//        CreateTableDDLBuilder createDDLBuilder = null;
//        AlterTableDDLBuilder alterDDLBuilder = null;
//        DDLBuilder<?> ddlBuilder = null;
//        
//        if (tableDDLExecutor.exists(tableName)) {
//            alterDDLBuilder = tableDDLExecutor
//                    .generateAlterTableDDLBuilder(tableName);
//            ddlBuilder = alterDDLBuilder;
//        } else {
//            createDDLBuilder = tableDDLExecutor
//                    .generateCreateTableDDLBuilder(tableName);
//            ddlBuilder = createDDLBuilder;
//        }
//        
//        td_task_status(ddlBuilder);//写入表结构
//        ddlBuilder.newIndex(true, "idx_task_status_00", "taskId");
//        ddlBuilder.newIndex(false, "idx_task_status_01", "createDate");
//        
//        if (alterDDLBuilder != null
//                && alterDDLBuilder.isNeedAlter(false, false)) {
//            System.out.println("检测到需要升级.task_status");
//            tableDDLExecutor.alter(alterDDLBuilder, false, false);
//        } else if (createDDLBuilder != null) {
//            System.out.println("创建表成功.task_status");
//            tableDDLExecutor.create(createDDLBuilder);
//        } else {
//            System.out.println("无需进行升级.task_def");
//        }
//    }
//    
//    /**
//     * td_task_status的构建器<br/>
//     * <功能详细描述>
//     * @param ddlBuilder [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private static void td_task_status(DDLBuilder<?> ddlBuilder) {
//        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
//                .newColumnOfVarchar("taskId", 64, true, null)
//                .newColumnOfVarchar("status", 64, true, null)
//                .newColumnOfVarchar("result", 64, false, null)
//                .newColumnOfDate("startDate", false, true)
//                .newColumnOfDate("endDate", false, true)
//                .newColumnOfBigDecimal("consuming",
//                        8,
//                        0,
//                        false,
//                        BigDecimal.ZERO)//size 原值 32
//                .newColumnOfVarchar("signature", 128, false, null)
//                .newColumnOfVarchar("attributes", 1024, false, null)
//                .newColumnOfDate("nextFireDate", false, false)
//                .newColumnOfBigDecimal("executeCount",
//                        8,
//                        0,
//                        false,
//                        BigDecimal.ZERO)
//                .newColumnOfDate("successStartDate", false, false)
//                .newColumnOfDate("successEndDate", false, false)
//                .newColumnOfBigDecimal("successConsuming",
//                        8,
//                        0,
//                        false,
//                        BigDecimal.ZERO)//size 原值 32
//                .newColumnOfBigDecimal("successCount",
//                        8,
//                        0,
//                        false,
//                        BigDecimal.ZERO)
//                .newColumnOfDate("failStartDate", false, false)
//                .newColumnOfDate("failEndDate", false, false)
//                .newColumnOfBigDecimal("failConsuming",
//                        8,
//                        0,
//                        false,
//                        BigDecimal.ZERO)//size 原值 32
//                .newColumnOfBigDecimal("failCount",
//                        8,
//                        0,
//                        false,
//                        BigDecimal.ZERO)
//                .newColumnOfDate("lastUpdateDate", true, true)
//                .newColumnOfDate("createDate", true, true);
//    }
//    
//    /**
//     * 核心文件定义表<br/>
//     * <功能详细描述> [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private static void table_task_execute_log(
//            TableDDLExecutor tableDDLExecutor) {
//        String tableName = "task_execute_log";
//        
//        CreateTableDDLBuilder createDDLBuilder = null;
//        AlterTableDDLBuilder alterDDLBuilder = null;
//        DDLBuilder<?> ddlBuilder = null;
//        
//        if (tableDDLExecutor.exists(tableName)) {
//            alterDDLBuilder = tableDDLExecutor
//                    .generateAlterTableDDLBuilder(tableName);
//            ddlBuilder = alterDDLBuilder;
//        } else {
//            createDDLBuilder = tableDDLExecutor
//                    .generateCreateTableDDLBuilder(tableName);
//            ddlBuilder = createDDLBuilder;
//        }
//        
//        task_execute_log(ddlBuilder);//写入表结构
//        ddlBuilder.newIndex(false, "idx_task_execute_log_00", "taskId");
//        ddlBuilder.newIndex(false, "idx_task_execute_log_01", "startDate");
//        
//        if (alterDDLBuilder != null
//                && alterDDLBuilder.isNeedAlter(false, false)) {
//            System.out.println("检测到需要升级.task_execute_log");
//            tableDDLExecutor.alter(alterDDLBuilder, false, false);
//        } else if (createDDLBuilder != null) {
//            System.out.println("创建表成功.task_execute_log");
//            tableDDLExecutor.create(createDDLBuilder);
//        } else {
//            System.out.println("无需进行升级.task_def");
//        }
//    }
//    
//    /**
//     * task_execute_log的构建器<br/>
//     * <功能详细描述>
//     * @param ddlBuilder [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private static void task_execute_log(DDLBuilder<?> ddlBuilder) {
//        ddlBuilder.newColumnOfVarchar(true, "id", 64, true, null)
//                .newColumnOfVarchar("operatorId", 64, false, null)
//                .newColumnOfVarchar("vcid", 64, false, null)
//                .newColumnOfVarchar("taskId", 64, true, null)
//                .newColumnOfVarchar("code", 64, true, null)
//                .newColumnOfVarchar("name", 64, true, null)
//                .newColumnOfVarchar("remark", 512, false, null)
//                .newColumnOfVarchar("result", 64, false, null)
//                .newColumnOfDate("startDate", false, true)
//                .newColumnOfDate("endDate", false, true)
//                .newColumnOfBigDecimal("consuming",
//                        32,
//                        0,
//                        false,
//                        BigDecimal.ZERO)
//                .newColumnOfVarchar("signature", 128, false, null)
//                .newColumnOfVarchar("attributes", 512, false, null);
//    }
//}
