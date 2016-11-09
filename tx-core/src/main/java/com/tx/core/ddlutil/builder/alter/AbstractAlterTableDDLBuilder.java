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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.core.ddlutil.builder.AbstractDDLBuilder;
import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.ddlutil.model.TableIndexDef;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.model.OrderedSupportComparator;

/**
 * 抽象的修改表DDL构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractAlterTableDDLBuilder extends
        AbstractDDLBuilder<AlterTableDDLBuilder> implements
        AlterTableDDLBuilder, AlterTableDDLBuilderFactory {
    
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
    public AbstractAlterTableDDLBuilder(TableDef newTable,
            TableDef sourceTable, DDLDialect ddlDialect) {
        super(newTable, ddlDialect);
        
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
        AssertUtils.isTrue(newTableDef.getTableName()
                .equalsIgnoreCase(sourceTableDef.getTableName()),
                "newTableDef.tableName:{} should equalsIgnoreCase sourceTableDef.tableName:{}.",
                new Object[] { newTableDef.getTableName(),
                        sourceTableDef.getTableName() });
        
        AlterTableDDLBuilder builder = newInstance(sourceTableDef,
                getDefaultDDLDialect());
        return builder;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isNeedAlter() {
        boolean flag = isNeedAlter(true);
        return flag;
    }
    
    /**
     * @param isIncrementUpdate
     * @return
     */
    @Override
    public boolean isNeedAlter(boolean isIncrementUpdate) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * @return
     */
    @Override
    public final String alterSql() {
        String alterSql = alterSql(true);
        return alterSql;
    }
    
    /**
     * @param isIncrementUpdate
     * @return
     */
    @Override
    public final String alterSql(boolean isIncrementUpdate) {
        try {
            doBuildAlterSql(isIncrementUpdate);
        } catch (IOException e) {
            throw new SILException("generate create table sql exception.", e);
        }
        String createSql = this.writer.toString();
        return createSql;
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
    protected void doBuildAlterSql(boolean isIncrementUpdate)
            throws IOException {
        this.writer = new StringWriter();
        Map<String, TableColumnDef> sourceColumnMap = new HashMap<String, TableColumnDef>();
        for (TableColumnDef col : this.sourceTable.getColumns()) {
            //源字段集合
            sourceColumnMap.put(col.getColumnName().toUpperCase(), col);
        }
        
        List<TableColumnDef> needAddColumns = new ArrayList<>();
        List<TableColumnDef> needModifyColumns = new ArrayList<>();
        List<TableColumnDef> needDeleteColumns = new ArrayList<>();
        Map<String, TableColumnDef> newColumnMap = new HashMap<String, TableColumnDef>();
        for (TableColumnDef col : this.columns) {
            newColumnMap.put(col.getColumnName().toUpperCase(), col);
            if (!sourceColumnMap.containsKey(col.getColumnName())) {
                needAddColumns.add(col);//添加新增字段集合
            } else {
                
            }
        }
        //输出修改表注释
        writeAlterTableComment();
        //写入修改表添加字段语句
        writeAlterAddColumnsStmt(needAddColumns);
        //输出修改主键
        
        //写入修改表修改字段语句
        writeAlterModifyColumnsStmt(needModifyColumns);
        //写入索引创建语句
        writeCreateIndexesStmt(null);
        
        //写入修改索引语句
        if (!isIncrementUpdate) {
            //写入删除字段语句
            
            //写入删除索引语句
        }
    }
    
    /**
     * 写入修改表增加字段sql.
     * <功能详细描述>
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeAlterAddColumnsStmt(List<TableColumnDef> addColumns)
            throws IOException {
        if (CollectionUtils.isEmpty(addColumns)) {
            return;
        }
        //写入修修改表：加字段的注释
        print("ALTER TABLE ");
        print(this.tableName);
        println(" ");//输出括号后换行
        
        OrderedSupportComparator.sort(addColumns);
        int columnSize = addColumns.size();
        int index = 0;
        for (TableColumnDef column : addColumns) {
            print(" ADD COLUMN ");//输出缩进
            writeColumn(column);
            if (++index < columnSize) {
                println(",");
            }
        }
        println(";");
    }
    
    /**
     * 写入建表语句
     * <功能详细描述>
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeAlterModifyColumnsStmt(
            List<TableColumnDef> modifyColumns) throws IOException {
        if (CollectionUtils.isEmpty(modifyColumns)) {
            return;
        }
        print("ALTER TABLE ");
        print(this.tableName);
        println(" ");//输出括号后换行
        
        OrderedSupportComparator.sort(modifyColumns);
        int columnSize = modifyColumns.size();
        int index = 0;
        for (TableColumnDef column : modifyColumns) {
            print(" MODIFY COLUMN ");//输出缩进
            writeColumn(column);
            if (++index < columnSize) {
                println(",");
            }
        }
        println(";");
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
        print(column.getColumnType());
        
        if (!StringUtils.isEmpty(column.getDefaultValue())) {
            print(" default ");
            print(column.getDefaultValue());
        }
        
        if (column.isRequired()) {
            print(" not null");
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
    protected void writeCreateIndexesStmt(List<TableIndexDef> addIndexes)
            throws IOException {
        if (CollectionUtils.isEmpty(addIndexes)) {
            return;
        }
        MultiValueMap<String, TableIndexDef> idxMutiMap = new LinkedMultiValueMap<>();
        OrderedSupportComparator.sort(addIndexes);
        for (TableIndexDef idex : addIndexes) {
            idxMutiMap.add(idex.getIndexName(), idex);
        }
        
        for (Entry<String, List<TableIndexDef>> entryTemp : idxMutiMap.entrySet()) {
            String indexName = entryTemp.getKey();
            TableIndexDef idxFirst = idxMutiMap.getFirst(indexName);
            boolean unique = idxFirst.isUnique();
            
            writeIndex(unique, indexName, entryTemp.getValue());
        }
    }
    
    /**
     * 写入表索引<br/>
     * <功能详细描述>
     * @param unique
     * @param indexName
     * @param ddlIndex
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeIndex(boolean unique, String indexName,
            List<TableIndexDef> ddlIndexes) throws IOException {
        print(unique ? "create unique index " : "create index ");
        print(indexName);
        print(" on ");
        print(this.tableName);
        print("(");
        
        int primaryColumnSize = ddlIndexes.size();
        int primaryIndex = 0;
        for (TableIndexDef column : ddlIndexes) {
            print(column.getColumnName());
            if ((++primaryIndex) < primaryColumnSize) {
                print(",");
            }
        }
        print(")");
        println(";");
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
    protected void writeDropIndexesStmt(List<TableIndexDef> needDropIndexes)
            throws IOException {
        if (CollectionUtils.isEmpty(needDropIndexes)) {
            return;
        }
        
        MultiValueMap<String, TableIndexDef> idxMutiMap = new LinkedMultiValueMap<>();
        for (TableIndexDef idex : needDropIndexes) {
            idxMutiMap.add(idex.getIndexName(), idex);
        }
        
        for (Entry<String, List<TableIndexDef>> entryTemp : idxMutiMap.entrySet()) {
            String indexName = entryTemp.getKey();
            //删除索引
            writeDropIndex(indexName);
        }
    }
    
    /**
      * 删除表索引<br/>
      * <功能详细描述>
      * @param indexName
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //ALTER TABLE table_name DROP INDEX index_name;
    protected void writeDropIndex(String indexName) throws IOException {
        print("ALTER TABLE ");
        print(this.tableName);
        print(" DROP INDEX ");
        print(indexName);
        println(";");
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
