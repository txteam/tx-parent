/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.alter.impl;

import com.tx.core.ddlutil.builder.alter.AbstractAlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.dialect.Dialect4DDL;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * MySQL创建表DDL构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MysqlAlterTableDDLBuilder extends AbstractAlterTableDDLBuilder {
    
    /** <默认构造函数> */
    MysqlAlterTableDDLBuilder(Dialect4DDL ddlDialect) {
        super(ddlDialect);
    }
    
    /** <默认构造函数> */
    public MysqlAlterTableDDLBuilder(TableDef sourceTable, Dialect4DDL ddlDialect) {
        super(sourceTable, ddlDialect);
    }
    
    /** <默认构造函数> */
    public MysqlAlterTableDDLBuilder(TableDef newTable, TableDef sourceTable,
            Dialect4DDL ddlDialect) {
        super(newTable, sourceTable, ddlDialect);
    }
    
    /**
     * @param sourceTableDef
     * @param ddlDialect
     * @return
     */
    @Override
    public AlterTableDDLBuilder newInstance(TableDef sourceTable,
            Dialect4DDL ddlDialect) {
        AssertUtils.notNull(ddlDialect, "ddlDialect is null.");
        AssertUtils.notNull(sourceTable, "sourceTable is null.");
        AssertUtils.notEmpty(sourceTable.getTableName(),
                "sourceTable.tableName is empty.");
        AssertUtils.notEmpty(sourceTable.getColumns(),
                "sourceTable.columns is empty.");
        
        MysqlAlterTableDDLBuilder builder = new MysqlAlterTableDDLBuilder(
                sourceTable, ddlDialect);
        return builder;
    }
    
    /**
     * @param newTableDef
     * @param sourceTableDef
     * @param ddlDialect
     * @return
     */
    @Override
    public AlterTableDDLBuilder newInstance(TableDef newTable,
            TableDef sourceTable, Dialect4DDL ddlDialect) {
        AssertUtils.notNull(ddlDialect, "ddlDialect is null.");
        AssertUtils.notNull(newTable, "newTable is null.");
        AssertUtils.notEmpty(newTable.getTableName(),
                "newTable.tableName is empty.");
        AssertUtils.notNull(sourceTable, "sourceTable is null.");
        AssertUtils.notEmpty(sourceTable.getTableName(),
                "sourceTable.tableName is empty.");
        AssertUtils.isTrue(newTable.getTableName()
                .equalsIgnoreCase(sourceTable.getTableName()),
                "newTable.tableName:{} should equalsIgnoreCase sourceTable.tableName:{}",
                new Object[] { newTable.getTableName(),
                        sourceTable.getTableName() });
        
        MysqlAlterTableDDLBuilder builder = new MysqlAlterTableDDLBuilder(
                newTable, sourceTable, ddlDialect);
        this.columns.addAll(newTable.getColumns());
        this.indexes.addAll(newTable.getIndexes());
        return builder;
    }
}
