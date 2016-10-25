/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.create;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.dialect.MysqlDDLDialect;
import com.tx.core.ddlutil.model.DDLColumn;
import com.tx.core.ddlutil.model.DDLIndex;
import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.ddlutil.model.TableColumn;
import com.tx.core.ddlutil.model.TableIndex;
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
public abstract class CreateTableDDLBuilder implements
        CreateTableDDLBuilderFactory {
    
    protected static Map<DataSourceTypeEnum, CreateTableDDLBuilderFactory> type2builderMap = new HashMap<>();
    
    /**
     * 根据数据库类型获取数据库DDL构建器实例<br/>
     * <功能详细描述>
     * @param dataSourceType
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static CreateTableDDLBuilderFactory getFactory(
            DataSourceTypeEnum dataSourceType) {
        AssertUtils.notNull(dataSourceType, "dataSourceType is null.");
        
        CreateTableDDLBuilderFactory builder = null;
        switch (dataSourceType) {
            case MySQL5InnoDBDialect:
            case MYSQL:
                DDLDialect ddlDialect = new MysqlDDLDialect();
                builder = new MysqlCreateTableDDLBuilder(ddlDialect);
                break;
            default:
                //其他的数据库暂不支持即可
                break;
        }
        AssertUtils.notNull(builder,
                "builder is not exist.dataSourceType:{}",
                new Object[] { dataSourceType });
        
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
        AssertUtils.notEmpty(tableName,"tableName is empty.");
        
        CreateTableDDLBuilder builder = newInstance(tableName, null);
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
    public abstract CreateTableDDLBuilder newInstance(String tableName,
            DDLDialect ddlDialect);
    
    /** 分行字符. */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator",
            "\n");
    
    /** ddl方言类 */
    private DDLDialect ddlDialect;
    
    /** 表名 */
    private String tableName;
    
    /** 字段集合 */
    private final List<TableColumn> columns = new LinkedList<>();
    
    /** 索引集合 */
    private final List<TableIndex> indexes = new LinkedList<>();
    
    /** 是否要写入字符 */
    private StringWriter writer;
    
    /** <默认构造函数> */
    protected CreateTableDDLBuilder(DDLDialect ddlDialect) {
        super();
        this.ddlDialect = ddlDialect;
    }
    
    /** <默认构造函数> */
    protected CreateTableDDLBuilder(String tableName, DDLDialect ddlDialect) {
        super();
        this.ddlDialect = ddlDialect;
        this.tableName = tableName;
    }
    
    /**
      * 新增字段<br/>
      * <功能详细描述>
      * @param ddlColumn
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CreateTableDDLBuilder newColumn(TableColumn tableColumn) {
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
      * 增加非主键Varchar字段<br/>
      * <功能详细描述>
      * @param columnName
      * @param size
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CreateTableDDLBuilder newColumnOfVarchar(String columnName,
            int size, boolean required, String defaultValue) {
        newColumnOfVarchar(false, columnName, size, required, defaultValue);
        return this;
    }
    
    /**
      * 增加Varchar类型字段<br/>
      * <功能详细描述>
      * @param primaryKey
      * @param columnName
      * @param size
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
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
        DDLColumn tableColumn = new DDLColumn(primaryKey, columnName,
                this.tableName, jdbcType, size, 0, required, defaultValueString);
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), size, size, 0);
        tableColumn.setColumnType(columnType);
        
        addAndValidateNewColumn(tableColumn);
        
        return this;
    }
    
    /**
      * 添加非主键Integer字段<br/>
      * <功能详细描述>
      * @param columnName
      * @param size
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CreateTableDDLBuilder newColumnOfInteger(String columnName,
            int size, boolean required, Integer defaultValue) {
        newColumnOfInteger(false, columnName, size, required, defaultValue);
        return this;
    }
    
    /**
      * 添加integer类字段<br/>
      * <功能详细描述>
      * @param primaryKey
      * @param columnName
      * @param size
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
      */
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
        DDLColumn tableColumn = new DDLColumn(primaryKey, columnName,
                this.tableName, jdbcType, size, 0, required, defaultValueString);
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), size, size, 0);
        tableColumn.setColumnType(columnType);
        
        addAndValidateNewColumn(tableColumn);
        
        return this;
    }
    
    /**
      * 新增时间字段<br/>
      * <功能详细描述>
      * @param columnName
      * @param required
      * @param isDefaultNow
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CreateTableDDLBuilder newColumnOfDate(String columnName,
            boolean required, boolean isDefaultNow) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.DATETIME;
        DDLColumn tableColumn = new DDLColumn(false, columnName,
                this.tableName, jdbcType, 0, 0, required,
                isDefaultNow ? "now()" : null);
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), 0, 0, 0);
        tableColumn.setColumnType(columnType);
        
        addAndValidateNewColumn(tableColumn);
        
        return this;
    }
    
    /**
      * 添加一个Boolean类型对应的字段：从兼容性考虑，统一使用Bit去表示一个boolean类型<br/>
      * <功能详细描述>
      * @param columnName
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CreateTableDDLBuilder newColumnOfBoolean(String columnName,
            boolean required, Boolean defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.BIT;
        TableColumn tableColumn = new DDLColumn(
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
      * 新增一个decimal类型的字段<br/>   
      * <功能详细描述>
      * @param columnName
      * @param size
      * @param scale
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CreateTableDDLBuilder newColumnOfDecimal(String columnName,
            int size, int scale, boolean required, String defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        if (size <= 0) {
            size = 16;
        }
        if (scale <= 0) {
            scale = 2;
        }
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.NUMERIC;
        TableColumn tableColumn = new DDLColumn(false, columnName,
                this.tableName, jdbcType, size, scale, required, defaultValue);
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
    private void addAndValidateNewColumn(TableColumn tableColumn) {
        AssertUtils.notNull(tableColumn, "ddlColumn is null.");
        AssertUtils.notEmpty(tableColumn.getColumnName(),
                "tableColumn.name is empty.");
        AssertUtils.notNull(tableColumn.getJdbcType(),
                "tableColumn.jdbcType is null.");
        AssertUtils.notEmpty(tableColumn.getColumnType(),
                "tableColumn.columnType is empty.");
        
        //判断是否有重复的表字段
        for (TableColumn tc : this.columns) {
            AssertUtils.notTrue(StringUtils.equalsIgnoreCase(tc.getColumnName(),
                    tableColumn.getColumnName()),
                    "Duplicate column.columnName:{}",
                    tableColumn.getColumnName());
        }
        
        this.columns.add(tableColumn);//添加字段
    }
    
    /**
      * 添加索引<br/>
      * <功能详细描述>
      * @param ddlIndex
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CreateTableDDLBuilder newIndex(TableIndex ddlIndex) {
        AssertUtils.notNull(ddlIndex, "ddlIndex is null.");
        
        addAndValidateNewIndex(ddlIndex);//添加索引
        return this;
    }
    
    /**
      * 新增非唯一键索引<br/>
      * <功能详细描述>
      * @param indexName
      * @param columnNames
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CreateTableDDLBuilder newIndex(String indexName,
            String... columnNames) {
        newIndex(false, indexName, columnNames);
        
        return this;
    }
    
    /**
      * 新增表索引<br/>
      * <功能详细描述>
      * @param unique
      * @param indexName
      * @param columnNames
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CreateTableDDLBuilder newIndex(boolean unique, String indexName,
            String... columnNames) {
        AssertUtils.notEmpty(indexName, "indexName is empty.");
        AssertUtils.notEmpty(columnNames, "columnNames is empty.");
        
        for (String columnName : columnNames) {
            DDLIndex newIndex = new DDLIndex(indexName, columnName,
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
    private void addAndValidateNewIndex(TableIndex tableIndex) {
        AssertUtils.notNull(tableIndex, "tableIndex is null.");
        AssertUtils.notEmpty(tableIndex.getIndexName(),
                "tableIndex.indexName is empty.");
        AssertUtils.notEmpty(tableIndex.getColumnName(),
                "tableIndex.columnName is empty.");
        
        //判断是否有重复的索引字段
        for (TableIndex tidx : this.indexes) {
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
        
        List<TableColumn> primaryColumns = new ArrayList<>();
        //输出字段
        OrderedSupportComparator.sort(this.columns);
        int columnSize = this.columns.size();
        int index = 0;
        for (TableColumn column : this.columns) {
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
            for (TableColumn column : primaryColumns) {
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
        MultiValueMap<String, TableIndex> idxMutiMap = new LinkedMultiValueMap<>();
        OrderedSupportComparator.sort(this.indexes);
        for (TableIndex idex : this.indexes) {
            idxMutiMap.add(idex.getIndexName(), idex);
        }
        
        for (Entry<String, List<TableIndex>> entryTemp : idxMutiMap.entrySet()) {
            String indexName = entryTemp.getKey();
            TableIndex idxFirst = idxMutiMap.getFirst(indexName);
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
    protected void writeColumn(TableColumn column) throws IOException {
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
            List<TableIndex> ddlIndexes) throws IOException {
        print(unique ? "create unique index " : "create index ");
        print(indexName);
        print(" on ");
        print(this.tableName);
        print("(");
        
        int primaryColumnSize = ddlIndexes.size();
        int primaryIndex = 0;
        for (TableIndex column : ddlIndexes) {
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
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
}
