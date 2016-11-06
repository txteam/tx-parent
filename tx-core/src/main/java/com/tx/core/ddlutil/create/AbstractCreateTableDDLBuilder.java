/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.create;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.model.DBColumnDef;
import com.tx.core.ddlutil.model.DBIndexDef;
import com.tx.core.ddlutil.model.JdbcTypeEnum;
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
public abstract class AbstractCreateTableDDLBuilder implements
        CreateTableDDLBuilder, CreateTableDDLBuilderFactory {
    
    /** 分行字符. */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator",
            "\n");
    
    /** ddl方言类 */
    private DDLDialect ddlDialect;
    
    /** 表名 */
    private String tableName;
    
    /** 字段集合 */
    private final List<TableColumnDef> columns = new LinkedList<>();
    
    /** 索引集合 */
    private final List<TableIndexDef> indexes = new LinkedList<>();
    
    /** 是否要写入字符 */
    private StringWriter writer;
    
    /** <默认构造函数> */
    protected AbstractCreateTableDDLBuilder(DDLDialect ddlDialect) {
        super();
        this.ddlDialect = ddlDialect;
        
        AssertUtils.notNull(this.ddlDialect, "ddlDialect is null.");
    }
    
    /** <默认构造函数> */
    protected AbstractCreateTableDDLBuilder(String tableName,
            DDLDialect ddlDialect) {
        this(ddlDialect);
        
        this.tableName = tableName;
    }
    
    /** <默认构造函数> */
    protected AbstractCreateTableDDLBuilder(TableDef table,
            DDLDialect ddlDialect) {
        this(ddlDialect);
        
        AssertUtils.notNull(table, "table is null.");
        AssertUtils.notEmpty(table.getTableName(), "table.tableName is empty.");
        AssertUtils.notEmpty(table.getColumns(), "table.columns is empty.");
        
        this.tableName = table.getTableName();
        this.columns.clear();
        this.columns.addAll(table.getColumns());
        this.indexes.clear();
        this.indexes.addAll(table.getIndexes());
    }
    
    /**
     * @return
     */
    @Override
    public DDLDialect getDefaultDDLDialect() {
        return this.ddlDialect;
    }
    
    /**
     * @param tableDef
     * @param ddlDialect
     * @return
     */
    public abstract CreateTableDDLBuilder newInstance(TableDef tableDef,
            DDLDialect ddlDialect);
    
    /**
     * 创建建设表构建器实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public abstract CreateTableDDLBuilder newInstance(String tableName,
           DDLDialect ddlDialect);
    
    /**
     * @param tableDef
     * @return
     */
    @Override
    public final CreateTableDDLBuilder newInstance(TableDef tableDef) {
        AssertUtils.notNull(tableDef, "tableDef is empty.");
        AssertUtils.notEmpty(tableDef.getTableName(), "tableDef.tableName is empty.");
        
        CreateTableDDLBuilder builder = newInstance(tableDef, getDefaultDDLDialect());
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
        
        CreateTableDDLBuilder builder = newInstance(tableName, getDefaultDDLDialect());
        return builder;
    }
    
    
    
    /**
     * @param tableColumn
     * @return
     */
    @Override
    public CreateTableDDLBuilder newColumn(TableColumnDef tableColumn) {
        AssertUtils.notNull(tableColumn, "tableColumn is null.");
        AssertUtils.notEmpty(tableColumn.getColumnName(),
                "tableColumn.columnName is empty.");
        AssertUtils.notNull(tableColumn.getJdbcType(),
                "ddlColumn.jdbcType is empty.");
        if (StringUtils.isEmpty(tableColumn.getColumnType())) {
            //字段类型
            String columnType = this.ddlDialect.getDialect()
                    .getTypeName(tableColumn.getJdbcType().getSqlType(),
                            tableColumn.getSize(),
                            tableColumn.getSize(),
                            tableColumn.getScale());
            tableColumn.setColumnType(columnType);
        }
        
        addAndValidateNewColumn(tableColumn);
        
        return this;
    }
    
    /**
     * @param columnName
     * @param size
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public CreateTableDDLBuilder newColumnOfVarchar(String columnName,
            int size, boolean required, String defaultValue) {
        newColumnOfVarchar(false, columnName, size, required, defaultValue);
        return this;
    }
    
    /**
     * @param primaryKey
     * @param columnName
     * @param size
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public CreateTableDDLBuilder newColumnOfVarchar(boolean primaryKey,
            String columnName, int size, boolean required, String defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.VARCHAR;
        if (size <= 0) {
            size = 64;
        } else if (size > 4000 && size < 65535) {
            jdbcType = JdbcTypeEnum.TEXT;
        } else if (size > 65535) {
            jdbcType = JdbcTypeEnum.LONGVARCHAR;
        }
        
        String defaultValueString = null;
        if (!StringUtils.isEmpty(defaultValue)) {
            defaultValueString = "'" + defaultValue + "'";
        }
        DBColumnDef tableColumn = new DBColumnDef(primaryKey, columnName,
                this.tableName, jdbcType, size, 0, required, defaultValueString);
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), size, size, 0);
        tableColumn.setColumnType(columnType);
        
        addAndValidateNewColumn(tableColumn);
        
        return this;
    }
    
    /**
     * @param columnName
     * @param size
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public CreateTableDDLBuilder newColumnOfInteger(String columnName,
            int size, boolean required, Integer defaultValue) {
        newColumnOfInteger(false, columnName, size, required, defaultValue);
        return this;
    }
    
    /**
     * @param primaryKey
     * @param columnName
     * @param size
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public CreateTableDDLBuilder newColumnOfInteger(boolean primaryKey,
            String columnName, int size, boolean required, Integer defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.INTEGER;
        if (size <= 0) {
            size = 10;
        } else if (size < 4) {
            jdbcType = JdbcTypeEnum.TINYINT;
        } else if (size < 6) {
            jdbcType = JdbcTypeEnum.SMALLINT;
        } else if (size < 11) {
            jdbcType = JdbcTypeEnum.INTEGER;
        } else if (size < 20) {
            jdbcType = JdbcTypeEnum.BIGINT;
        } else {
            jdbcType = JdbcTypeEnum.DECIMAL;
        }
        
        String defaultValueString = null;
        if (defaultValue != null) {
            defaultValueString = String.valueOf(defaultValue);
        }
        DBColumnDef tableColumn = new DBColumnDef(primaryKey, columnName,
                this.tableName, jdbcType, size, 0, required, defaultValueString);
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), size, size, 0);
        tableColumn.setColumnType(columnType);
        
        addAndValidateNewColumn(tableColumn);
        
        return this;
    }
    
    /**
     * @param columnName
     * @param required
     * @param isDefaultNow
     * @return
     */
    @Override
    public CreateTableDDLBuilder newColumnOfDate(String columnName,
            boolean required, boolean isDefaultNow) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.DATETIME;
        DBColumnDef tableColumn = new DBColumnDef(false, columnName,
                this.tableName, jdbcType, 0, 0, required,
                isDefaultNow ? "now()" : null);
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), 0, 0, 0);
        tableColumn.setColumnType(columnType);
        
        addAndValidateNewColumn(tableColumn);
        
        return this;
    }
    
    /**
     * @param columnName
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public CreateTableDDLBuilder newColumnOfBoolean(String columnName,
            boolean required, Boolean defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.BIT;
        TableColumnDef tableColumn = new DBColumnDef(
                false,
                columnName,
                this.tableName,
                jdbcType,
                1,
                0,
                required,
                defaultValue != null ? (defaultValue.booleanValue() ? "1" : "0")
                        : null);
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), 1, 1, 0);
        tableColumn.setColumnType(columnType);
        
        addAndValidateNewColumn(tableColumn);
        
        return this;
    }
    
    /**
     * @param columnName
     * @param size
     * @param scale
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public CreateTableDDLBuilder newColumnOfBigDecimal(String columnName,
            int size, int scale, boolean required, BigDecimal defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        if (size <= 0) {
            size = 16;
        }
        if (scale <= 0) {
            scale = 2;
        }
        JdbcTypeEnum jdbcType = JdbcTypeEnum.NUMERIC;
        TableColumnDef tableColumn = new DBColumnDef(false, columnName,
                this.tableName, jdbcType, size, scale, required,
                defaultValue == null ? null : defaultValue.toString());
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), 1, 1, 0);
        tableColumn.setColumnType(columnType);
        
        addAndValidateNewColumn(tableColumn);
        
        return this;
    }
    
    /**
      * 写入一个新字段<br/>
      * <功能详细描述>
      * @param tableColumn [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void addAndValidateNewColumn(TableColumnDef tableColumn) {
        AssertUtils.notNull(tableColumn, "ddlColumn is null.");
        AssertUtils.notEmpty(tableColumn.getColumnName(),
                "tableColumn.name is empty.");
        AssertUtils.notNull(tableColumn.getJdbcType(),
                "tableColumn.jdbcType is null.");
        AssertUtils.notEmpty(tableColumn.getColumnType(),
                "tableColumn.columnType is empty.");
        
        //判断是否有重复的表字段
        for (TableColumnDef tc : this.columns) {
            AssertUtils.notTrue(StringUtils.equalsIgnoreCase(tc.getColumnName(),
                    tableColumn.getColumnName()),
                    "Duplicate column.columnName:{}",
                    tableColumn.getColumnName());
        }
        
        this.columns.add(tableColumn);//添加字段
    }
    
    /**
     * @param ddlIndex
     * @return
     */
    @Override
    public CreateTableDDLBuilder newIndex(TableIndexDef ddlIndex) {
        AssertUtils.notNull(ddlIndex, "ddlIndex is null.");
        
        addAndValidateNewIndex(ddlIndex);//添加索引
        return this;
    }
    
    /**
     * @param indexName
     * @param columnNames
     * @return
     */
    @Override
    public CreateTableDDLBuilder newIndex(String indexName,
            String... columnNames) {
        newIndex(false, indexName, columnNames);
        
        return this;
    }
    
    /**
     * @param unique
     * @param indexName
     * @param columnNames
     * @return
     */
    @Override
    public CreateTableDDLBuilder newIndex(boolean unique, String indexName,
            String... columnNames) {
        AssertUtils.notEmpty(indexName, "indexName is empty.");
        AssertUtils.notEmpty(columnNames, "columnNames is empty.");
        
        for (String columnName : columnNames) {
            DBIndexDef newIndex = new DBIndexDef(indexName, columnName,
                    this.tableName, unique);
            
            addAndValidateNewIndex(newIndex);
        }
        return this;
    }
    
    /**
      * 添加并验证索引<br/>
      * <功能详细描述>
      * @param tableIndex [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void addAndValidateNewIndex(TableIndexDef tableIndex) {
        AssertUtils.notNull(tableIndex, "tableIndex is null.");
        AssertUtils.notEmpty(tableIndex.getIndexName(),
                "tableIndex.indexName is empty.");
        AssertUtils.notEmpty(tableIndex.getColumnName(),
                "tableIndex.columnName is empty.");
        
        //判断是否有重复的索引字段
        for (TableIndexDef tidx : this.indexes) {
            //当indexName与columnName都相当时认为是重复的索引
            AssertUtils.notTrue(StringUtils.equalsIgnoreCase(tidx.getIndexName(),
                    tableIndex.getIndexName())
                    && StringUtils.equalsIgnoreCase(tidx.getColumnName(),
                            tableIndex.getColumnName()),
                    "Duplicate index:indexName:{} columnName:{}",
                    tableIndex.getIndexName(),
                    tableIndex.getColumnName());
        }
        
        this.indexes.add(tableIndex);//添加字段
    }
    
    /**
     * @return
     */
    @Override
    public String createSql() {
        //        if (this.needRebuildSql) {
        //            doRebuildCreateSql();
        //            this.needRebuildSql = false;
        //        }
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
        writeTableComment();
        //写入建表语句
        writeTableCreationStmt();
        //写入索引创建语句
        writeCreateIndexesStmt();
    }
    
    /** 
     * 写入表注释信息<br/>
     * @param table The table
     */
    protected void writeTableComment() throws IOException {
        printComment("-----------------------------------------------------------------------");
        printComment(this.tableName);
        printComment("-----------------------------------------------------------------------");
        //println();
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
      * 写入表注释<br/>
      * <功能详细描述>
      * @param text
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void printComment(String text) throws IOException {
        if (this.ddlDialect.isSqlCommentsOn()) {
            print(this.ddlDialect.getCommentPrefix());
            print(" ");
            print(text);
            print(" ");
            print(this.ddlDialect.getCommentSuffix());
            println();
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
     * 写入一个字符，和一个换行符
     * @param text The text to print
     */
    protected void println(String text) throws IOException {
        print(text);
        println();
    }
    
    /** 
     * 写入一个换行符
     */
    protected void println() throws IOException {
        print(LINE_SEPARATOR);
    }
    
    /**
     * 写入指定字符集
     * @param text The text to print
     */
    protected void print(String text) throws IOException {
        this.writer.write(text);
    }
    
    /**
     * @return 返回 ddlDialect
     */
    protected DDLDialect getDDLDialect() {
        return ddlDialect;
    }
    
    /**
     * @return
     */
    @Override
    public String getTableName() {
        return tableName;
    }
}
