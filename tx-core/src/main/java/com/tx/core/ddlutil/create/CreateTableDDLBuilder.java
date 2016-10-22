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
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

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
    private final List<DDLColumn> columns = new LinkedList<>();
    
    /** 索引集合 */
    private final List<DDLIndex> indexes = new LinkedList<>();
    
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
    
    public CreateTableDDLBuilder newColumn(DDLColumn ddlColumn) {
        validateNewColumn(ddlColumn);
        
        //添加字段
        this.columns.add(ddlColumn);
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
    public CreateTableDDLBuilder newVarcharColumn(String columnName, int size,
            boolean required, String defaultValue) {
        newVarcharColumn(false, columnName, size, required, defaultValue);
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
    public CreateTableDDLBuilder newVarcharColumn(boolean primaryKey,
            String columnName, int size, boolean required, String defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.VARCHAR;
        if (size <= 0) {
            size = 64;
        } else if (size > 4000 && size < 65535) {
            jdbcType = JdbcTypeEnum.TEXT;
        } else {
            jdbcType = JdbcTypeEnum.LONGVARCHAR;
        }
        
        DDLColumn ddlColumn = new DDLColumn(primaryKey, columnName,
                this.tableName, jdbcType, size, 0, required, defaultValue);
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), size, size, 0);
        ddlColumn.setColumnType(columnType);
        
        validateNewColumn(ddlColumn);
        this.columns.add(ddlColumn);
        
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
    public CreateTableDDLBuilder newIntegerColumn(String columnName, int size,
            boolean required, String defaultValue) {
        newIntegerColumn(false, columnName, size, required, defaultValue);
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
    public CreateTableDDLBuilder newIntegerColumn(boolean primaryKey,
            String columnName, int size, boolean required, String defaultValue) {
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
        if (!StringUtils.isBlank(defaultValue) && !defaultValue.startsWith("'")) {
            defaultValue = "'" + defaultValue;
        }
        if (!StringUtils.isBlank(defaultValue) && !defaultValue.endsWith("'")) {
            defaultValue = defaultValue + "'";
        }
        DDLColumn ddlColumn = new DDLColumn(primaryKey, columnName,
                this.tableName, jdbcType, size, 0, required, defaultValue);
        String columnType = this.ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), size, size, 0);
        ddlColumn.setColumnType(columnType);
        
        validateNewColumn(ddlColumn);
        this.columns.add(ddlColumn);
        
        return this;
    }
    
    public CreateTableDDLBuilder newDateColumn(String columnName,
            boolean required, boolean isDefaultNow) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        return this;
    }
    
    public CreateTableDDLBuilder newBooleanColumn(String columnName, int size,
            int scale, boolean required, boolean defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        return this;
    }
    
    public CreateTableDDLBuilder newDecimalColumn(String columnName,
            boolean required, boolean defaultValue) {
        
        return this;
    }
    
    private void validateNewColumn(DDLColumn ddlColumn) {
        AssertUtils.notNull(ddlColumn, "ddlColumn is null.");
        AssertUtils.notEmpty(ddlColumn.getName(), "ddlColumn.name is empty.");
        AssertUtils.notNull(ddlColumn.getJdbcType(),
                "ddlColumn.jdbcType is null.");
        
    }
    
    public CreateTableDDLBuilder newIndex(DDLIndex ddlIndex) {
        //添加字段
        this.indexes.add(ddlIndex);
        return this;
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
            doRebuildCreateSql();
        } catch (IOException e) {
            throw new SILException("generate create table sql exception.", e);
        }
        String createSql = this.writer.toString();
        return createSql;
    }
    
    protected void doRebuildCreateSql() throws IOException {
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
        println();
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
        
        List<DDLColumn> primaryColumns = new ArrayList<>();
        //输出字段
        int columnSize = this.columns.size();
        int index = 0;
        for (DDLColumn column : this.columns) {
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
            for (DDLColumn column : primaryColumns) {
                print(column.getName());
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
        MultiValueMap<String, DDLIndex> idxMutiMap = new LinkedMultiValueMap<>();
        for (DDLIndex idex : this.indexes) {
            idxMutiMap.add(idex.getName(), idex);
        }
        
        for (Entry<String, List<DDLIndex>> entryTemp : idxMutiMap.entrySet()) {
            String indexName = entryTemp.getKey();
            DDLIndex idxFirst = idxMutiMap.getFirst(indexName);
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
    protected void writeColumn(DDLColumn column) throws IOException {
        print(column.getName());
        print(" ");
        print(column.getColumnType());
        
        if (!StringUtils.isEmpty(column.getDefaultValue())) {
            print(" default");
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
            List<DDLIndex> ddlIndexes) throws IOException {
        print(unique ? "create unique index " : "create index ");
        print(indexName);
        print(" on ");
        print(this.tableName);
        print("(");
        
        int primaryColumnSize = ddlIndexes.size();
        int primaryIndex = 0;
        for (DDLIndex column : ddlIndexes) {
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
