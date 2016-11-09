/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月21日
 * <修改描述:>
 */
package com.tx.core.ddlutil;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.datasource.DataSourceFinder;
import com.tx.core.datasource.finder.SimpleDataSourceFinder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;
import com.tx.core.ddlutil.model.DBColumnDef;
import com.tx.core.ddlutil.model.DBIndexDef;
import com.tx.core.ddlutil.model.DBTableDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.util.SqlUtils;

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
        
        String tableName = "test_001";
        TableDDLExecutor ddlExecutor = new MysqlTableDDLExecutor(jt);
        if(ddlExecutor.exists(tableName)){
            ddlExecutor.drop(tableName);
        }
        
        boolean flag = ddlExecutor.exists("comm_message_tempalte");
        System.out.println("exists:" + flag);
        TableDef tab = ddlExecutor.findDBTableByTableName("comm_message_tempalte");
        if (tab instanceof DBTableDef) {
            DBTableDef ddlTab = (DBTableDef) tab;
            System.out.println(ddlTab.getCatalog() + ":" + ddlTab.getSchema()
                    + ":" + ddlTab.getTableName() + ":" + ddlTab.getType());
        } else {
            System.out.println(tab.getTableName());
        }
        
        CreateTableDDLBuilder createBuilder = ddlExecutor.generateCreateTableDDLBuilder(tab.getTableName());
        List<DBColumnDef> cols = ddlExecutor.queryDBColumnsByTableName("comm_message_tempalte");
        for (DBColumnDef col : cols) {
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
        List<DBIndexDef> idxs = ddlExecutor.queryDBIndexesByTableName("comm_message_tempalte");
        int ii = 0;
        for (DBIndexDef idx : idxs) {
            idx.setIndexName("idx_" + tableName + "_" + ii++);
            createBuilder.newIndex(idx);
        }
        createBuilder.setTableName(tableName);
        System.out.println(createBuilder.createSql());
        System.out.println(SqlUtils.format(createBuilder.createSql()));
        ddlExecutor.create(createBuilder);
        
        //增加字段
        createBuilder.newColumnOfBoolean("testBoolean", true, true)
                .newColumnOfDate("testDateTime", false, false)
                .newColumnOfDate("testDateTime2", true, true)
                .newColumnOfBigDecimal("testDecimal",
                        16,
                        4,
                        true,
                        new BigDecimal("0"));
        createBuilder.newColumnOfInteger("testInteger", 8, false, null);
        createBuilder.newColumnOfInteger("testInteger1", 8, true, 999);
        createBuilder.newColumnOfVarchar("testVarchar",
                16,
                true,
                "defaultVarchar");
    }
}
