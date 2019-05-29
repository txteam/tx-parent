/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.create;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.core.ddlutil.builder.AbstractDDLBuilder;
import com.tx.core.ddlutil.dialect.Dialect4DDL;
import com.tx.core.ddlutil.helper.TableDefHelper;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.ddlutil.model.TableIndexDef;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.order.OrderedSupportComparator;

/**
 * 创建表DDL构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractCreateTableDDLBuilder
        extends AbstractDDLBuilder<CreateTableDDLBuilder>
        implements CreateTableDDLBuilder, CreateTableDDLBuilderFactory {
    
    /** <默认构造函数> */
    public AbstractCreateTableDDLBuilder(Dialect4DDL ddlDialect) {
        super(ddlDialect);
    }
    
    /** <默认构造函数> */
    public AbstractCreateTableDDLBuilder(String tableName,
            Dialect4DDL ddlDialect) {
        super(tableName, ddlDialect);
    }
    
    /** <默认构造函数> */
    public AbstractCreateTableDDLBuilder(TableDef table,
            Dialect4DDL ddlDialect) {
        super(table, ddlDialect);
    }
    
    /**
     * @return
     */
    @Override
    public Dialect4DDL getDefaultDDLDialect() {
        return getDDLDialect();
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
    
    /**
     * 构建创建表sql
     * <功能详细描述>
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void doBuildCreateSql() throws IOException {
        this.writer = new StringWriter();
        
        //写入表注释
        writeCreateTableComment();
        //写入表：开始
        writeCreateTableStartStmt();
        
        //写入表：增加字段
        writeCreateTableAddColumnsStmt();
        
        //写入表：增加主键
        writeCreateTablePrimaryKeyStmt();
        
        //写入表：增加索引
        writeCreateIndexesStmt();
        
        //写入表结束语句
        writeCreateTableEndStmt();
    }
    
    /**
      * 写入表注释信息<br/>
      * <功能详细描述>
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void writeCreateTableComment() throws IOException {
        printComment(
                "-----------------------------------------------------------------------");
        printComment(this.tableName);
        printComment(
                "-----------------------------------------------------------------------");
    }
    
    /**
      * 写入建表语句开始节点<br/>
      * <功能详细描述>
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void writeCreateTableStartStmt() throws IOException {
        print("CREATE TABLE ");
        print(this.tableName);
        println("(");//输出括号后换行
    }
    
    /**
      * 创建增加字段的节点<br/>
      * <功能详细描述>
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void writeCreateTableAddColumnsStmt() throws IOException {
        //输出字段
        OrderedSupportComparator.sort(this.columns);
        int columnSize = this.columns.size();
        int index = 0;
        for (TableColumnDef column : this.columns) {
            print("   ");//输出缩进
            writeColumn(column);
            if (++index < columnSize) {
                println(",");
            }
        }
    }
    
    /**
      * 写入表：主键<br/>
      * <功能详细描述>
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void writeCreateTablePrimaryKeyStmt() throws IOException {
        //遍历索引，获取其中主键
        String primaryNames = TableDefHelper
                .parsePrimaryKeyColumnNames(columns);
        
        //如果主键名集合为空
        if (StringUtils.isBlank(primaryNames)) {
            return;
        }
        
        //如果不为空
        println(",");
        print("   ");//输出缩进
        print("PRIMARY KEY (");
        
        print(primaryNames);
        
        print(")");
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
        
        for (TableIndexDef idx : this.indexes) {
            idxMutiMap.add(idx.getIndexName().toUpperCase(), idx);
            writeIndex(idx.isUniqueKey(),
                    idx.getIndexName().toUpperCase(),
                    idx.getColumnNames());
        }
    }
    
    /**
     * 写入索引<br/>
     * <功能详细描述>
     * @param unique
     * @param indexName
     * @param ddlIndexes
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeIndex(boolean unique, String indexName,
            String columnNames) throws IOException {
        if (unique) {
            println(",");
            print("   ");//输出缩进
            print("UNIQUE KEY ");
            print(indexName);
            print(" ");
            print("(");
            print(columnNames);
            print(")");
        } else {
            println(",");
            print("   ");//输出缩进
            print("INDEX ");
            print(indexName);
            print(" ");
            print("(");
            print(columnNames);
            print(")");
        }
    }
    
    /**
     * 写入创建表结束节点<br/>
     * <功能详细描述>
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void writeCreateTableEndStmt() throws IOException {
        println();
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
