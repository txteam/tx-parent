/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月6日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.alter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.tx.core.ddlutil.builder.AbstractDDLBuilder;
import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.order.OrderedSupportComparator;

/**
 * 抽象的修改表DDL构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractAlterTableDDLBuilder
        extends AbstractDDLBuilder<AlterTableDDLBuilder>
        implements AlterTableDDLBuilder, AlterTableDDLBuilderFactory {
    
    /** 待修改表 */
    private final TableDef sourceTable;
    
    /** <默认构造函数> */
    public AbstractAlterTableDDLBuilder(DDLDialect ddlDialect) {
        super(ddlDialect);
        
        this.sourceTable = null;
    }
    
    /** <默认构造函数> */
    public AbstractAlterTableDDLBuilder(TableDef sourceTable,
            DDLDialect ddlDialect) {
        super(sourceTable.getTableName(), ddlDialect);
        
        AssertUtils.notNull(sourceTable, "sourceTable is null.");
        AssertUtils.notEmpty(sourceTable.getTableName(),
                "sourceTable.tableName is empty.");
        AssertUtils.notEmpty(sourceTable.getColumns(),
                "sourceTable.columns is empty.");
        
        this.sourceTable = sourceTable;
    }
    
    /** <默认构造函数> */
    public AbstractAlterTableDDLBuilder(TableDef newTable, TableDef sourceTable,
            DDLDialect ddlDialect) {
        super(newTable, ddlDialect);
        
        AssertUtils.notNull(newTable, "newTable is null.");
        AssertUtils.notEmpty(newTable.getTableName(),
                "newTable.tableName is empty.");
        AssertUtils.notNull(sourceTable, "sourceTable is null.");
        AssertUtils.notEmpty(sourceTable.getTableName(),
                "sourceTable.tableName is empty.");
        AssertUtils.isTrue(
                newTable.getTableName()
                        .equalsIgnoreCase(sourceTable.getTableName()),
                "newTable.tableName:{} should equalsIgnoreCase sourceTable.tableName:{}",
                new Object[] { newTable.getTableName(),
                        sourceTable.getTableName() });
        
        this.sourceTable = sourceTable;
    }
    
    /**
     * @return
     */
    @Override
    public DDLDialect getDefaultDDLDialect() {
        return getDDLDialect();
    }
    
    /**
     * @param sourceTableDef
     * @return
     */
    @Override
    public final AlterTableDDLBuilder newInstance(TableDef sourceTableDef) {
        AssertUtils.notNull(sourceTableDef, "sourceTableDef is null.");
        AssertUtils.notEmpty(sourceTableDef.getTableName(),
                "sourceTableDef.tableName is empty.");
        
        AlterTableDDLBuilder builder = newInstance(sourceTableDef,
                getDefaultDDLDialect());
        return builder;
    }
    
    /**
     * @param newTableDef
     * @param sourceTableDef
     * @return
     */
    @Override
    public final AlterTableDDLBuilder newInstance(TableDef newTableDef,
            TableDef sourceTableDef) {
        AssertUtils.notNull(newTableDef, "newTableDef is null.");
        AssertUtils.notEmpty(newTableDef.getTableName(),
                "newTableDef.tableName is empty.");
        AssertUtils.notNull(sourceTableDef, "sourceTableDef is null.");
        AssertUtils.notEmpty(sourceTableDef.getTableName(),
                "sourceTableDef.tableName is empty.");
        AssertUtils.isTrue(
                newTableDef.getTableName()
                        .equalsIgnoreCase(sourceTableDef.getTableName()),
                "newTableDef.tableName:{} should equalsIgnoreCase sourceTableDef.tableName:{}.",
                new Object[] { newTableDef.getTableName(),
                        sourceTableDef.getTableName() });
        
        AlterTableDDLBuilder builder = newInstance(newTableDef,
                sourceTableDef,
                getDefaultDDLDialect());
        
        return builder;
    }
    
    /**
     * @return
     */
    @Override
    public AlterTableComparetor compare() {
        AlterTableComparetor comparetor = new AlterTableComparetor();
        comparetor.setSourceTableDef(this.sourceTable);
        comparetor.setTargetTableColumns(this.columns);
        comparetor.setTargetTableIndexes(this.indexes);
        comparetor.compare();
        
        return comparetor;
    }
    
    /**
     * @return
     */
    @Override
    public final String alterSql() {
        AlterTableComparetor comparetor = new AlterTableComparetor();
        comparetor.setSourceTableDef(this.sourceTable);
        comparetor.setTargetTableColumns(this.columns);
        comparetor.setTargetTableIndexes(this.indexes);
        comparetor.compare();
        
        try {
            doBuildAlterSql(comparetor);
        } catch (IOException e) {
            throw new SILException("generate alter table sql exception.", e);
        }
        
        String alterSql = this.writer.toString();
        
        return alterSql;
    }
    
    /**
      * 构建修改表的Sql
      * <功能详细描述>
      * @param isIncrementUpdate
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doBuildAlterSql(AlterTableComparetor comparetor)
            throws IOException {
        this.writer = new StringWriter();
        
        if (!comparetor.isNeedAlter()) {
            return;
        }
        
        //输出修改表注释
        writeAlterTableComment();
        //写入修改表开始
        writeAlterStartStmt();
        
        //删除主键
        if (comparetor.isNeedModifyPrimaryKey()) {
            writeAlterDropPrimaryKey();
        }
        //删除索引
        writeAlterDropIndex(comparetor.getAlterTableIndexes());
        
        //增加字段
        writeAlterAddColumnsStmt(comparetor.getAlterTableColumns());
        //修改字段
        writeAlterModifyColumnsStmt(comparetor.getAlterTableColumns());
        
        //增加主键
        if (comparetor.isNeedModifyPrimaryKey()) {
            writeAlterAddPrimaryKey(comparetor.getPrimaryKeyColumnNames());
        }
        
        //增加索引
        writeAlterAddIndexesStmt(comparetor.getAlterTableIndexes());
        
        //写入修改表结束
        writeAlterEndStmt();
    }
    
    /**
     * 写入修改表:
     *     //Alter Table xxx
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeAlterStartStmt() throws IOException {
        //写入修修改表：加字段的注释
        print("ALTER TABLE ");
        print(this.tableName);
        println(" ");//输出括号后换行
    }
    
    /**
      * 写入结束段<br/>
      * <功能详细描述>
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void writeAlterEndStmt() throws IOException {
        deleteLastIndexOf(",");//删除最后一个,
        //写入修修改表：加字段的注释
        println(";");
    }
    
    /**
      * 写入删除主键<br/>
      * <功能详细描述>
      * @param isNeedDropPrimaryKey
      * @param primaryKey
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void writeAlterDropPrimaryKey() throws IOException {
        //String primaryKeyName
        //        if (!isNeedDropPrimaryKey) {
        //            return;
        //        }
        //AssertUtils.notEmpty(primaryKeyName, "primaryKeyName is empty.");
        //print("   DROP CONSTRAINT ");//输出缩进
        //print(primaryKeyName);
        //println(",");
        println("   DROP PRIMARY KEY,");
    }
    
    /**
     * 写入删除主键<br/>
     * <功能详细描述>
     * @param isNeedDropPrimaryKey
     * @param primaryKey
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeAlterAddPrimaryKey(String primaryKeyColumnNames)
            throws IOException {
        if (StringUtils.isEmpty(primaryKeyColumnNames)) {
            return;
        }
        
        print("   ADD PRIMARY KEY (");//输出缩进
        print(primaryKeyColumnNames);
        println("),");
    }
    
    /**
     * 增加字段<br/>
     * <功能详细描述>
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void writeAlterAddColumnsStmt(
            List<AlterTableColumn> alterTableColumns) throws IOException {
        List<TableColumnDef> addColumns = new ArrayList<>();
        for (AlterTableColumn col : alterTableColumns) {
            if (!col.isNeedAlter()) {
                continue;
            }
            if (!AlterTypeEnum.ADD.equals(col.getAlterType())) {
                continue;
            }
            addColumns.add(col.getTargetColumn());
        }
        
        if (CollectionUtils.isEmpty(addColumns)) {
            return;
        }
        OrderedSupportComparator.sort(addColumns);
        for (TableColumnDef column : addColumns) {
            print("   ADD COLUMN ");//输出缩进
            writeColumn(column);
            println(",");
        }
    }
    
    /**
     * 修改字段
     * <功能详细描述>
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void writeAlterModifyColumnsStmt(
            List<AlterTableColumn> alterTableColumns) throws IOException {
        List<TableColumnDef> modifyColumns = new ArrayList<>();
        for (AlterTableColumn col : alterTableColumns) {
            if (!col.isNeedAlter()) {
                continue;
            }
            if (!AlterTypeEnum.MODIFY.equals(col.getAlterType())
                    && !AlterTypeEnum.MODIFY_TO_NULLABLE
                            .equals(col.getAlterType())) {
                continue;
            }
            if (col.getTargetColumn() != null) {
                modifyColumns.add(col.getTargetColumn());
            } else {
                col.getSourceColumn().setRequired(false);
                modifyColumns.add(col.getSourceColumn());
            }
        }
        
        if (CollectionUtils.isEmpty(modifyColumns)) {
            return;
        }
        
        OrderedSupportComparator.sort(modifyColumns);
        for (TableColumnDef column : modifyColumns) {
            print("   MODIFY COLUMN ");//输出缩进
            writeColumn(column);
            println(",");
        }
    }
    
    /**
     * 写入表字段<br/>
     * <功能详细描述>
     * @param column
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeColumn(TableColumnDef column) throws IOException {
        print(column.getColumnName());
        print(" ");
        print(column.getColumnType(this.ddlDialect));
        
        if (!StringUtils.isEmpty(column.getDefaultValue())) {
            print(" default ");
            print(column.getDefaultValue());
        }
        
        if (column.isRequired()) {
            print(" not null");
        }
    }
    
    /**
     * 写入删除主键<br/>
     * <功能详细描述>
     * @param isNeedDropPrimaryKey
     * @param primaryKey
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeAlterDropIndex(List<AlterTableIndex> alterTableIndexes)
            throws IOException {
        if (CollectionUtils.isEmpty(alterTableIndexes)) {
            return;
        }
        
        for (AlterTableIndex index : alterTableIndexes) {
            if (!index.isNeedAlter()) {
                continue;
            }
            if (!AlterTypeEnum.DROP.equals(index.getAlterType())
                    && !AlterTypeEnum.MODIFY.equals(index.getAlterType())) {
                continue;
            }
            
            print("   DROP INDEX ");//输出缩进
            print(index.getIndexName());
            println(",");
        }
    }
    
    /**
     * 写入索引创建语句<br/>
     * <功能详细描述>
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeAlterAddIndexesStmt(
            List<AlterTableIndex> alterTableIndexes) throws IOException {
        if (CollectionUtils.isEmpty(alterTableIndexes)) {
            return;
        }
        
        for (AlterTableIndex index : alterTableIndexes) {
            if (!index.isNeedAlter()) {
                continue;
            }
            if (!AlterTypeEnum.ADD.equals(index.getAlterType())
                    && !AlterTypeEnum.MODIFY.equals(index.getAlterType())) {
                continue;
            }
            
            print(index.getTargetIndex().isUniqueKey() ? "   ADD UNIQUE INDEX "
                    : "   ADD INDEX ");
            print(index.getTargetIndex().getIndexName());
            //print(" ON ");
            print(" ");
            print("(");
            print(index.getTargetIndex().getColumnNames());
            print(")");
            println(",");
        }
    }
    
    /** 
     * 写入表注释信息<br/>
     * @param table The table
     */
    protected void writeAlterTableComment() throws IOException {
        printComment("alter talbe " + this.tableName);
    }
    
    /**
     * @return 返回 sourceTable
     */
    @Override
    public TableDef sourceTable() {
        return sourceTable;
    }
}
