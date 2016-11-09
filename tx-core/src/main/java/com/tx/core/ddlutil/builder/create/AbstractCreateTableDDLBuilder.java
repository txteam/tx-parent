/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.create;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
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
 * 创建表DDL构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractCreateTableDDLBuilder extends
        AbstractDDLBuilder<CreateTableDDLBuilder> implements
        CreateTableDDLBuilder, CreateTableDDLBuilderFactory {
    
    /** <默认构造函数> */
    public AbstractCreateTableDDLBuilder(DDLDialect ddlDialect) {
        super(ddlDialect);
    }
    
    /** <默认构造函数> */
    public AbstractCreateTableDDLBuilder(String tableName, DDLDialect ddlDialect) {
        super(tableName, ddlDialect);
    }
    
    /** <默认构造函数> */
    public AbstractCreateTableDDLBuilder(TableDef table, DDLDialect ddlDialect) {
        super(table, ddlDialect);
    }
    
    /**
     * @return
     */
    @Override
    public DDLDialect getDefaultDDLDialect() {
        return getDDLDialect();
    }
    
    /**
     * @param tableDef
     * @return
     */
    @Override
    public final CreateTableDDLBuilder newInstance(TableDef tableDef) {
        AssertUtils.notNull(tableDef, "tableDef is empty.");
        AssertUtils.notEmpty(tableDef.getTableName(),
                "tableDef.tableName is empty.");
        
        CreateTableDDLBuilder builder = newInstance(tableDef,
                getDefaultDDLDialect());
        return builder;
    }
    
    /**
     * 创建建设表构建器实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public final CreateTableDDLBuilder newInstance(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        CreateTableDDLBuilder builder = newInstance(tableName,
                getDefaultDDLDialect());
        return builder;
    }
    
    /**
     * @return
     */
    @Override
    public String createSql() {
        try {
            doBuildCreateSql();
        } catch (IOException e) {
            throw new SILException("generate create table sql exception.", e);
        }
        String createSql = this.writer.toString();
        return createSql;
    }
    
    protected void doBuildCreateSql() throws IOException {
        this.writer = new StringWriter();
        //写入表注释
        writeCreateTableComment();
        //写入建表语句
        writeTableCreationStmt();
        //写入索引创建语句
        writeCreateIndexesStmt();
    }
    
    /** 
     * 写入表注释信息<br/>
     * @param table The table
     */
    protected void writeCreateTableComment() throws IOException {
        printComment("-----------------------------------------------------------------------");
        printComment(this.tableName);
        printComment("-----------------------------------------------------------------------");
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
    protected void writeTableCreationStmt() throws IOException {
        print("CREATE TABLE ");
        print(this.tableName);
        println("(");//输出括号后换行
        
        List<TableColumnDef> primaryColumns = new ArrayList<>();
        //输出字段
        OrderedSupportComparator.sort(this.columns);
        int columnSize = this.columns.size();
        int index = 0;
        for (TableColumnDef column : this.columns) {
            print(" ");//输出缩进
            writeColumn(column);
            if (++index < columnSize) {
                println(",");
            }
            if (column.isPrimaryKey()) {
                primaryColumns.add(column);
            }
        }
        
        if (CollectionUtils.isNotEmpty(primaryColumns)) {
            println(",");
            print(" ");//输出缩进
            print("primary key (");
            
            int primaryColumnSize = primaryColumns.size();
            int primaryIndex = 0;
            for (TableColumnDef column : primaryColumns) {
                print(column.getColumnName());
                if ((++primaryIndex) < primaryColumnSize) {
                    print(",");
                }
            }
            print(")");
        }
        println();
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
    protected void writeCreateIndexesStmt() throws IOException {
        if (CollectionUtils.isEmpty(this.indexes)) {
            return;
        }
        MultiValueMap<String, TableIndexDef> idxMutiMap = new LinkedMultiValueMap<>();
        OrderedSupportComparator.sort(this.indexes);
        for (TableIndexDef idex : this.indexes) {
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
      * 修改表名<br/>
      * <功能详细描述>
      * @param tableName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setTableName(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        this.tableName = tableName;
    }
}
