/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月21日
 * <修改描述:>
 */
package com.tx.core.ddlutil;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.core.datasource.DataSourceFinder;
import com.tx.core.datasource.finder.SimpleDataSourceFinder;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;
import com.tx.core.ddlutil.model.DBColumnDef;
import com.tx.core.ddlutil.model.DBIndexDef;
import com.tx.core.exceptions.SILException;
import com.tx.core.model.OrderedSupportComparator;

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
    
    public static void printCreateTableJavaCode(TableDDLExecutor ddlExecutor,
            String tableName) {
        boolean flag = ddlExecutor.exists(tableName);
        if (!flag) {
            System.out.println("table not exist.");
        }
        
        List<DBColumnDef> cols = ddlExecutor.queryDBColumnsByTableName(tableName);
        for (DBColumnDef col : cols) {
            switch (col.getJdbcType()) {
                case VARCHAR:
                case CHAR:
                case TEXT:
                case LONGTEXT:
                case LONGVARCHAR:
                    if (col.isPrimaryKey()) {
                        System.out.println(MessageFormatter.arrayFormat(".newColumnOfVarchar(true, \"{}\", {}, {}, {})",
                                new Object[] { col.getColumnName(),
                                        col.getSize(), col.isRequired(),
                                        col.getDefaultValue() })
                                .getMessage());
                    } else {
                        System.out.println(MessageFormatter.arrayFormat(".newColumnOfVarchar(\"{}\", {}, {}, {})",
                                new Object[] { col.getColumnName(),
                                        col.getSize(), col.isRequired(),
                                        col.getDefaultValue() })
                                .getMessage());
                    }
                    break;
                case INT:
                case TINYINT:
                case SMALLINT:
                case INTEGER:
                case BIGINT:
                    if (col.isPrimaryKey()) {
                        System.out.println(MessageFormatter.arrayFormat(".newColumnOfInteger(true, \"{}\", {}, {}, {})",
                                new Object[] { col.getColumnName(),
                                        col.getSize(), col.isRequired(),
                                        col.getDefaultValue() })
                                .getMessage());
                    } else {
                        System.out.println(MessageFormatter.arrayFormat(".newColumnOfInteger(\"{}\", {}, {}, {})",
                                new Object[] { col.getColumnName(),
                                        col.getSize(), col.isRequired(),
                                        col.getDefaultValue() })
                                .getMessage());
                    }
                    break;
                case FLOAT:
                case DOUBLE:
                case DECIMAL:
                case NUMERIC:
                    System.out.println(MessageFormatter.arrayFormat(".newColumnOfBigDecimal(\"{}\", {}, {}, {}, {})",
                            new Object[] { col.getColumnName(), col.getSize(),
                                    col.getScale(), col.isRequired(),
                                    col.getDefaultValue() })
                            .getMessage());
                    break;
                case BIT:
                    System.out.println(MessageFormatter.arrayFormat(".newColumnOfBoolean(\"{}\", {}, {})",
                            new Object[] {
                                    col.getColumnName(),
                                    col.isRequired(),
                                    col.getDefaultValue() == null ? null
                                            : "1".equals(col.getDefaultValue()) })
                            .getMessage());
                    break;
                case DATE:
                case DATETIME:
                case TIMESTAMP:
                case TIME:
                    System.out.println(MessageFormatter.arrayFormat(".newColumnOfDate(\"{}\", {}, {})",
                            new Object[] { col.getColumnName(),
                                    col.isRequired(),
                                    "now()".equals(col.getDefaultValue()) })
                            .getMessage());
                    break;
                default:
                    throw new SILException("error type." + col.getJdbcType());
            }
        }
        
        System.out.println();
        
        List<DBIndexDef> indexes = ddlExecutor.queryDBIndexesByTableName(tableName);
        MultiValueMap<String, DBIndexDef> idxMap = new LinkedMultiValueMap<>();
        for (DBIndexDef idx : indexes) {
            if (idx.isPrimaryKey()) {
                continue;
            }
            idxMap.add(idx.getIndexName().toUpperCase(), idx);
        }
        
        for (List<DBIndexDef> idxList : idxMap.values()) {
            String indexName = idxList.get(0).getIndexName();
            boolean uniqueKey = idxList.get(0).isUniqueKey();
            OrderedSupportComparator.sort(idxList);
            StringBuffer sb = new StringBuffer();
            for (DBIndexDef d : idxList) {
                sb.append("\"").append(d.getColumnName()).append("\",");
            }
            sb.deleteCharAt(sb.length() - 1);
            String columnNames = sb.toString();
            
            if (uniqueKey) {
                System.out.println(MessageFormatter.arrayFormat(".newIndex(true,\"{}\",{})",
                        new Object[] { indexName, columnNames })
                        .getMessage());
            } else {
                System.out.println(MessageFormatter.arrayFormat(".newIndex(\"{}\",{})",
                        new Object[] { indexName, tableName, columnNames })
                        .getMessage());
            }
        }
        
    }
    
    public static void main(String[] args) {
        DataSourceFinder finder = new SimpleDataSourceFinder(
                "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/wtms_db",
                "root", "root");
        DataSource ds = finder.getDataSource();
        JdbcTemplate jt = new JdbcTemplate(ds);
        
        String tableName = "test_001";
        TableDDLExecutor ddlExecutor = new MysqlTableDDLExecutor(jt);
        if (ddlExecutor.exists(tableName)) {
            ddlExecutor.drop(tableName);
        }
        
        printCreateTableJavaCode(ddlExecutor, "dt_table_column");
        //        boolean flag = ddlExecutor.exists("comm_message_tempalte");
        //        System.out.println("exists:" + flag);
        //        TableDef tab = ddlExecutor.findDBTableByTableName("comm_message_tempalte");
        //        if (tab instanceof DBTableDef) {
        //            DBTableDef ddlTab = (DBTableDef) tab;
        //            System.out.println(ddlTab.getCatalog() + ":" + ddlTab.getSchema()
        //                    + ":" + ddlTab.getTableName() + ":" + ddlTab.getType());
        //        } else {
        //            System.out.println(tab.getTableName());
        //        }
        //        
        //        CreateTableDDLBuilder createBuilder = ddlExecutor.generateCreateTableDDLBuilder(tab.getTableName());
        //        List<DBColumnDef> cols = ddlExecutor.queryDBColumnsByTableName("comm_message_tempalte");
        //        for (DBColumnDef col : cols) {
        //            createBuilder.newColumn(col);
        //            System.out.println(col.getColumnName()
        //                    + ":"
        //                    + col.getColumnType()
        //                    + ":"
        //                    + col.getJdbcType()
        //                    + ":"
        //                    + col.isPrimaryKey()
        //                    + ":"
        //                    + col.isRequired()
        //                    + ":"
        //                    + (col.getDefaultValue() == null ? ""
        //                            : col.getDefaultValue()) + ":" + col.getSize()
        //                    + ":" + col.getScale());
        //        }
        //        List<DBIndexDef> idxs = ddlExecutor.queryDBIndexesByTableName("comm_message_tempalte");
        //        int ii = 0;
        //        for (DBIndexDef idx : idxs) {
        //            idx.setIndexName("idx_" + tableName + "_" + ii++);
        //            createBuilder.newIndex(idx);
        //        }
        //        createBuilder.setTableName(tableName);
        //        //System.out.println(createBuilder.createSql());
        //        //System.out.println(SqlUtils.format(createBuilder.createSql()));
        //        
        //        //增加字段
        //        createBuilder.newColumnOfBoolean("testBoolean", true, true)
        //                .newColumnOfDate("testDateTime", false, false)
        //                .newColumnOfDate("testDateTime2", true, true)
        //                .newColumnOfBigDecimal("testDecimal",
        //                        16,
        //                        4,
        //                        true,
        //                        new BigDecimal("0"));
        //        createBuilder.newColumnOfInteger("testInteger", 8, false, null);
        //        createBuilder.newColumnOfInteger("testInteger1", 8, true, 999);
        //        createBuilder.newColumnOfVarchar("testVarchar",
        //                16,
        //                true,
        //                "defaultVarchar");
        //        createBuilder.newIndex("idx_t_test_001_10", "testBoolean","testInteger1");
        //        createBuilder.newIndex("idx_t_test_001_20", "testDateTime2");
        //        System.out.println(createBuilder.createSql());
        //        ddlExecutor.create(createBuilder);
        //        
        //        DBTableDef sourceTable = ddlExecutor.findDBTableDetailByTableName(tableName);
        //        DBTableDef newTable = ddlExecutor.findDBTableDetailByTableName("comm_message_tempalte");
        //        newTable.setTableName(tableName);
        //        AlterTableDDLBuilder alterBuilder = ddlExecutor.generateAlterTableDDLBuilder(newTable,sourceTable);
        //        
        //        System.out.println(alterBuilder.alterSql());
        //        System.out.println(alterBuilder.alterSql(false, false));
    }
}
