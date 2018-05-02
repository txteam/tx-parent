/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月21日
 * <修改描述:>
 */
package com.tx.test.ddlutil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.datasource.DataSourceFinder;
import com.tx.core.datasource.finder.SimpleDataSourceFinder;
import com.tx.core.ddlutil.builder.alter.AlterTableColumn;
import com.tx.core.ddlutil.builder.alter.AlterTableComparetor;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.dialect.MysqlDDLDialect;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;
import com.tx.core.ddlutil.helper.JPAEntityDDLHelper;
import com.tx.core.ddlutil.model.DBTableDef;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.test.ddlutil.model.ColumnCompareInfo;
import com.tx.test.ddlutil.model.DDLTestDemo;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JPA_CREATE_DDL_Test {
    
    public static void main(String[] args) {
        AssertUtils.isTrue(false, "11");
        
        DataSourceFinder finder = new SimpleDataSourceFinder(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://120.24.75.25:3306/test?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
                "pqy", "pqy");
        DataSource ds = finder.getDataSource();
        JdbcTemplate jt = new JdbcTemplate(ds);
        
        TableDDLExecutor ddlExecutor = new MysqlTableDDLExecutor(jt);
        
        //解析对象
        TableDef tableDef = JPAEntityDDLHelper.analyzeToTableDefDetail(
                DDLTestDemo.class, MysqlDDLDialect.INSTANCE);
        if (ddlExecutor.exists(tableDef.getTableName())) {
            ddlExecutor.drop(tableDef.getTableName());
        }
        
        Map<String, ColumnCompareInfo> map = new HashMap<>();
        
        //开始构建创建器
        CreateTableDDLBuilder createBuilder = ddlExecutor
                .generateCreateTableDDLBuilder(tableDef);
        List<?> cols = createBuilder.getColumns();
        for (Object colObject : cols) {
            TableColumnDef col = (TableColumnDef) colObject;
            writeJPAColumn(map, col);
        }
        ddlExecutor.create(createBuilder);
        
        System.out.println("-----------   查询创建好的数据库表结构      --------------");
        
        DBTableDef dbdef = ddlExecutor
                .findDBTableDetailByTableName(tableDef.getTableName());
        cols = dbdef.getColumns();
        for (Object colObject : cols) {
            TableColumnDef col = (TableColumnDef) colObject;
            writeDBColumn(map, col);
        }
        
        System.out.println("-----------   显示构建字段以及结果字段对比      --------------");
        
        AlterTableDDLBuilder alterTableBuilder = ddlExecutor.generateAlterTableDDLBuilder(tableDef, dbdef);
        AlterTableComparetor comparetor;
        try {
            comparetor = alterTableBuilder.compare();
            
            System.out.println("needModifyPrimaryKey:" + comparetor.isNeedModifyPrimaryKey());
            System.out.println("primaryKeyColumnNames:" + comparetor.getPrimaryKeyColumnNames());
            System.out.println("needAlter:" + comparetor.isNeedAlter());
            System.out.println("needBackup:" + comparetor.isNeedBackup());
            for(AlterTableColumn atc : comparetor.getAlterTableColumns()){
                if(atc.isStrictMatch()){
                    System.out.println("NotStrictMatch:" + " : " + atc.getColumnName() + " : " + atc.getRemark());
                }
                if(!atc.isNeedAlter()){
                    continue;
                }
                System.out.println(atc.getColumnName() + " : " + atc.getRemark());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        //        StringBuilder sb = new StringBuilder();
        //        sb.append("\t\n");
        //        sb.append("字段名").append("\t\t").append("|");
        //        sb.append("JPA_JDBCTYPE").append("\t\t").append("|");
        //        sb.append("JPA_ISPRIMARYKEY").append("\t\t").append("|");
        //        sb.append("JPA_ISREQUIRED").append("\t\t").append("|");
        //        sb.append("JPA_SIZE").append("\t\t").append("|");
        //        sb.append("JPA_SCALE").append("\t\t").append("|");
        //        sb.append("").append("\t\t").append("|");
        //        sb.append("DB_JDBCTYPE").append("\t\t").append("|");
        //        sb.append("DB_ISPRIMARYKEY").append("\t\t").append("|");
        //        sb.append("DB_ISREQUIRED").append("\t\t").append("|");
        //        sb.append("DB_SIZE").append("\t\t").append("|");
        //        sb.append("DB_SCALE").append("\t\t").append("|");
        //        sb.append("\t\n");
        //        
        //        for (Entry<String, ColumnCompareInfo> entryTemp : map.entrySet()) {
        //            sb.append(StringUtils.rightPad(entryTemp.getKey(), 32))
        //                    .append("\t\t")
        //                    .append("|");
        //            
        //            TableColumnDef column = entryTemp.getValue().getSourceColumn();
        //            sb.append(column.getJdbcType()).append("\t\t").append("|");
        //            sb.append(column.isPrimaryKey()).append("\t\t").append("|");
        //            sb.append(column.isRequired()).append("\t\t").append("|");
        //            sb.append(column.getSize()).append("\t\t").append("|");
        //            sb.append(column.getScale()).append("\t\t").append("|");
        //            sb.append("").append("\t\t").append("|");
        //            
        //            column = entryTemp.getValue().getTargetColumn();
        //            sb.append(column.getJdbcType()).append("\t\t").append("|");
        //            sb.append(column.isPrimaryKey()).append("\t\t").append("|");
        //            sb.append(column.isRequired()).append("\t\t").append("|");
        //            sb.append(column.getSize()).append("\t\t").append("|");
        //            sb.append(column.getScale()).append("\t\t").append("|");
        //            
        //            sb.append("\t\n");
        //        }
        //        System.out.println(sb.toString());
    }
    
    private static void writeJPAColumn(Map<String, ColumnCompareInfo> map,
            TableColumnDef jpaColumn) {
        map.put(jpaColumn.getColumnName().toUpperCase(),
                new ColumnCompareInfo(jpaColumn.getColumnName().toUpperCase(),
                        jpaColumn));
    }
    
    private static void writeDBColumn(Map<String, ColumnCompareInfo> map,
            TableColumnDef dbColumn) {
        if (!map.containsKey(dbColumn.getColumnName().toUpperCase())) {
            map.put(dbColumn.getColumnName().toUpperCase(),
                    new ColumnCompareInfo(
                            dbColumn.getColumnName().toUpperCase(), null,
                            dbColumn));
        } else {
            map.get(dbColumn.getColumnName().toUpperCase())
                    .setTargetColumn(dbColumn);
        }
    }
}
