/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.test.dbscript;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.component.basicdata.script.BasicDataContextTableInitializer;
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
public class TableInitializerTest {
    
    public static void main(String[] args) {
        DataSourceFinder finder = new TomcatDataSourceFinder(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://120.24.75.25:3306/test?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
                "pqy", "pqy");
        DataSource ds = finder.getDataSource();
        JdbcTemplate jt = new JdbcTemplate(ds);
        TableDDLExecutor tableDDLExecutor = new MysqlTableDDLExecutor(jt);
        
        //        //创建表
        //        if (tableDDLExecutor.exists("bd_basic_data_type")) {
        //            tableDDLExecutor.drop("bd_basic_data_type");
        //        }
        //        table_bd_basic_data_type(tableDDLExecutor);//创建表
        //        table_bd_basic_data_type(tableDDLExecutor);//更新表
        
        if (tableDDLExecutor.exists("bd_data_dict")) {
            tableDDLExecutor.drop("bd_data_dict");
        }
        table_bd_data_dict(tableDDLExecutor);//创建表
        table_bd_data_dict(tableDDLExecutor);//更新表
        
        if (tableDDLExecutor.exists("bd_data_dict_entry")) {
            tableDDLExecutor.drop("bd_data_dict_entry");
        }
        table_bd_data_dict_entry(tableDDLExecutor);//创建表
        table_bd_data_dict_entry(tableDDLExecutor);//更新表
    }
    
    //    /**
    //     * 核心文件定义表<br/>
    //     * <功能详细描述> [参数说明]
    //     * 
    //     * @return void [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //    */
    //    private static void table_bd_basic_data_type(TableDDLExecutor tableDDLExecutor) {
    //        String tableName = "bd_basic_data_type";
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
    //        BasicDataContextTableInitializer.bd_basic_data_type(ddlBuilder);//写入表结构
    //        
    //        if (alterDDLBuilder != null
    //                && alterDDLBuilder.compare().isNeedAlter()) {
    //            System.out.println("检测到需要升级.bd_basic_data_type");
    //            tableDDLExecutor.alter(alterDDLBuilder);
    //        } else if (createDDLBuilder != null) {
    //            System.out.println("创建表成功.bd_basic_data_type");
    //            tableDDLExecutor.create(createDDLBuilder);
    //        } else {
    //            System.out.println("无需进行升级.bd_basic_data_type");
    //        }
    //        
    //    }
    
    /**
     * 核心文件定义表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private static void table_bd_data_dict(TableDDLExecutor tableDDLExecutor) {
        String tableName = "bd_data_dict";
        
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
        
        BasicDataContextTableInitializer.bd_data_dict(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            System.out.println("检测到需要升级.bd_data_dict");
            tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            System.out.println("创建表成功.bd_data_dict");
            tableDDLExecutor.create(createDDLBuilder);
        } else {
            System.out.println("无需进行升级.bd_data_dict");
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
    private static void table_bd_data_dict_entry(
            TableDDLExecutor tableDDLExecutor) {
        String tableName = "bd_data_dict_entry";
        
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
        
        BasicDataContextTableInitializer.bd_data_dict_entry(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            System.out.println("检测到需要升级.bd_data_dict_entry");
            tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            System.out.println("创建表成功.bd_data_dict_entry");
            tableDDLExecutor.create(createDDLBuilder);
        } else {
            System.out.println("无需进行升级.bd_data_dict_entry");
        }
    }
}
