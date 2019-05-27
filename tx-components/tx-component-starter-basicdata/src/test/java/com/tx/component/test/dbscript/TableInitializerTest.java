/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.test.dbscript;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.tx.component.basicdata.script.BasicDataContextTableInitializer;
import com.tx.component.configuration.script.ConfigContextTableInitializer;
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
        //com.mysql.cj.jdbc.Driver
        //app.jdbc.url=jdbc:p6spy:mysql://47.94.136.230:3306/webdemo?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
        //app.jdbc.username=pqy
        //app.jdbc.password=tx123321qQ!
        DriverManagerDataSource ds = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/webdemo?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false",
                "root", "root");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        JdbcTemplate jt = new JdbcTemplate(ds);
        TableDDLExecutor tableDDLExecutor = new MysqlTableDDLExecutor(jt);
        
        if (tableDDLExecutor.exists("bd_data_dict")) {
            tableDDLExecutor.drop("bd_data_dict");
        }
        table_bd_data_dict(tableDDLExecutor);//创建表
        table_bd_data_dict(tableDDLExecutor);//更新表
        
        if (tableDDLExecutor.exists("bd_config_context")) {
            tableDDLExecutor.drop("bd_config_context");
        }
        table_bd_config_context(tableDDLExecutor);
        table_bd_config_context(tableDDLExecutor);
    }
    
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
            System.out.println(
                    "检测到需要升级.bd_data_dict: " + alterDDLBuilder.alterSql());
            tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            System.out.println(
                    "创建表成功.bd_data_dict: " + createDDLBuilder.createSql());
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
    private static void table_bd_config_context(TableDDLExecutor tableDDLExecutor) {
        String tableName = "bd_config_context";
        
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
        
        ConfigContextTableInitializer.bd_config_context(ddlBuilder);//写入表结构
        
        if (alterDDLBuilder != null
                && alterDDLBuilder.compare().isNeedAlter()) {
            System.out.println(
                    "检测到需要升级.bd_config_context: " + alterDDLBuilder.alterSql());
            tableDDLExecutor.alter(alterDDLBuilder);
        } else if (createDDLBuilder != null) {
            System.out.println(
                    "创建表成功.bd_config_context: " + createDDLBuilder.createSql());
            tableDDLExecutor.create(createDDLBuilder);
        } else {
            System.out.println("无需进行升级.bd_config_context");
        }
    }
}
