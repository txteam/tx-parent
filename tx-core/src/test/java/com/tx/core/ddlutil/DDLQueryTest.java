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
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.dialect.MysqlDDLDialect;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;
import com.tx.core.ddlutil.helper.JPAEntityDDLHelper;
import com.tx.core.ddlutil.model.DBColumnDef;
import com.tx.core.ddlutil.model.DBIndexDef;
import com.tx.core.ddlutil.model.DBTableDef;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.SILException;
import com.tx.core.util.order.OrderedSupportComparator;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DDLQueryTest {
    
    public static void main(String[] args) {
        DataSourceFinder finder = new SimpleDataSourceFinder(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://120.24.75.25:3306/test?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
                "pqy", "pqy");
        DataSource ds = finder.getDataSource();
        JdbcTemplate jt = new JdbcTemplate(ds);
        
        TableDDLExecutor ddlExecutor = new MysqlTableDDLExecutor(jt);
        
        TableDef tableDef = JPAEntityDDLHelper
                .analyzeToTableDefDetail(DDLTestDemo.class,new MysqlDDLDialect());
        
        CreateTableDDLBuilder createBuilder = ddlExecutor
                .generateCreateTableDDLBuilder(tableDef);
        List<?> cols = createBuilder.getColumns();
        for (Object colObject : cols) {
            TableColumnDef col = (TableColumnDef) colObject;
            System.out.println(col.getColumnName() + ":" + col.getColumnType()
                    + ":" + col.getJdbcType() + ":" + col.isPrimaryKey() + ":"
                    + col.isRequired() + ":"
                    + (col.getDefaultValue() == null ? ""
                            : col.getDefaultValue())
                    + ":" + col.getSize() + ":" + col.getScale());
        }
        if(ddlExecutor.exists(tableDef.getTableName())){
            ddlExecutor.drop(tableDef.getTableName());
        }
        ddlExecutor.create(createBuilder);
        
        System.out.println("------------");
        
        DBTableDef dbdef = ddlExecutor.findDBTableDetailByTableName(tableDef.getTableName());
        cols = dbdef.getColumns();
        for (Object colObject : cols) {
            TableColumnDef col = (TableColumnDef) colObject;
            System.out.println(col.getColumnName() + ":" + col.getColumnType()
                    + ":" + col.getJdbcType() + ":" + col.isPrimaryKey() + ":"
                    + col.isRequired() + ":"
                    + (col.getDefaultValue() == null ? ""
                            : col.getDefaultValue())
                    + ":" + col.getSize() + ":" + col.getScale());
        }
        
        AlterTableDDLBuilder alterBuilder = ddlExecutor.generateAlterTableDDLBuilder(tableDef);
        System.out.println(alterBuilder.isNeedAlter());
    }
}
