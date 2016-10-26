/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月21日
 * <修改描述:>
 */
package com.tx.core.ddlutil;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.datasource.DataSourceFinder;
import com.tx.core.datasource.finder.SimpleDataSourceFinder;
import com.tx.core.ddlutil.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;
import com.tx.core.ddlutil.model.DDLColumn;
import com.tx.core.ddlutil.model.DDLIndex;
import com.tx.core.ddlutil.model.DDLTable;
import com.tx.core.ddlutil.model.Table;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DDLUtilTest {
    
    public static void main(String[] args) {
        DataSourceFinder finder = new SimpleDataSourceFinder(
                "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/wtms_db",
                "root", "root");
        DataSource ds = finder.getDataSource();
        JdbcTemplate jt = new JdbcTemplate(ds);
        
        TableDDLExecutor ddlExecutor = new MysqlTableDDLExecutor(jt);
        
        boolean flag = ddlExecutor.exists("comm_message_tempalte");
        System.out.println("exists:" + flag);
        
        Table tab = ddlExecutor.findDDLTableByTableName("comm_message_tempalte");
        if (tab instanceof DDLTable) {
            DDLTable ddlTab = (DDLTable) tab;
            System.out.println(ddlTab.getCatalog() + ":" + ddlTab.getSchema()
                    + ":" + ddlTab.getTableName() + ":" + ddlTab.getType());
        } else {
            System.out.println(tab.getTableName());
        }
        
        CreateTableDDLBuilder createBuilder = ddlExecutor.generateCreateTableDDLBuilder(tab.getTableName());
        
        List<DDLColumn> cols = ddlExecutor.queryDDLColumnsByTableName("comm_message_tempalte");
        for (DDLColumn col : cols) {
            createBuilder.newColumn(col);
            
            System.out.println(col.getColumnName()
                    + ":"
                    + col.getColumnType()
                    + ":"
                    + col.getJdbcType()
                    + ":"
                    + col.isPrimaryKey()
                    + ":"
                    + col.isRequired()
                    + ":"
                    + (col.getDefaultValue() == null ? ""
                            : col.getDefaultValue()) + ":" + col.getSize()
                    + ":" + col.getScale());
        }
        
        List<DDLIndex> idxs = ddlExecutor.queryDDLIndexesByTableName("comm_message_tempalte");
        for (DDLIndex idx : idxs) {
            createBuilder.newIndex(idx);
        }
        
        createBuilder.newColumnOfBoolean("testBoolean", true, true);
        createBuilder.newColumnOfDate("testDateTime", false, false);
        createBuilder.newColumnOfDate("testDateTime2", false, true);
        createBuilder.newColumnOfDecimal("testDecimal", 16, 4, true, "0");
        createBuilder.newColumnOfInteger("testInteger", 8, false, null);
        createBuilder.newColumnOfVarchar("testVarchar",
                16,
                true,
                "defaultVarchar");
        
        System.out.println(createBuilder.createSql());
    }
}
