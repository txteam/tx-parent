/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.model.DBColumnDef;
import com.tx.core.ddlutil.model.DBIndexDef;
import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.ddlutil.model.TableIndexDef;
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
public abstract class AbstractDDLBuilder<B extends DDLBuilder<B>>
        implements DDLBuilder<B> {
    
    /** 分行字符. */
    private static final String LINE_SEPARATOR = System
            .getProperty("line.separator", "\n");
    
    /** 表名 */
    protected String tableName;
    
    /** ddl方言类 */
    protected final DDLDialect ddlDialect;
    
    /** 字段集合 */
    protected final List<TableColumnDef> columns = new LinkedList<>();
    
    /** 索引集合 */
    protected final List<TableIndexDef> indexes = new LinkedList<>();
    
    /** 是否要写入字符 */
    protected StringWriter writer;
    
    /** <默认构造函数> */
    protected AbstractDDLBuilder(DDLDialect ddlDialect) {
        super();
        this.ddlDialect = ddlDialect;
        
        AssertUtils.notNull(this.ddlDialect, "ddlDialect is null.");
    }
    
    /** <默认构造函数> */
    protected AbstractDDLBuilder(String tableName, DDLDialect ddlDialect) {
        this(ddlDialect);
        
        this.tableName = tableName;
    }
    
    /** <默认构造函数> */
    protected AbstractDDLBuilder(TableDef table, DDLDialect ddlDialect) {
        this(ddlDialect);
        
        AssertUtils.notNull(table, "table is null.");
        AssertUtils.notEmpty(table.getTableName(), "table.tableName is empty.");
        
        this.tableName = table.getTableName();
        this.columns.clear();
        this.columns.addAll(table.getColumns());
        this.indexes.clear();
        this.indexes.addAll(table.getIndexes());
    }
    
    /**
     * @param tableColumn
     * @return
     */
    @Override
    public B newColumn(TableColumnDef tableColumn) {
        AssertUtils.notNull(tableColumn, "tableColumn is null.");
        AssertUtils.notEmpty(tableColumn.getColumnName(),
                "tableColumn.columnName is empty.");
        AssertUtils.notNull(tableColumn.getJdbcType(),
                "ddlColumn.jdbcType is empty.");
        
        addAndValidateNewColumn(tableColumn);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
    }
    
    /**
     * @param columnName
     * @param size
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public B newColumnOfVarchar(String columnName, int size, boolean required,
            String defaultValue) {
        newColumnOfVarchar(false, columnName, size, required, defaultValue);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
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
    public B newColumnOfVarchar(boolean primaryKey, String columnName, int size,
            boolean required, String defaultValue) {
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
                this.tableName, jdbcType, size, 0, required,
                defaultValueString);
        
        addAndValidateNewColumn(tableColumn);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
    }
    
    /**
     * @param columnName
     * @param size
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public B newColumnOfInteger(String columnName, boolean required,
            Integer defaultValue) {
        newColumnOfInteger(false, columnName, 10, required, defaultValue);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
    }
    
    /**
     * @param columnName
     * @param size
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public B newColumnOfInteger(String columnName, int size, boolean required,
            Integer defaultValue) {
        newColumnOfInteger(false, columnName, size, required, defaultValue);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
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
    public B newColumnOfInteger(boolean primaryKey, String columnName, int size,
            boolean required, Integer defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.INTEGER;
        if (size <= 0) {
            size = 10;
        } else if (size < 4) {
            jdbcType = JdbcTypeEnum.TINYINT;
        } else if (size < 6) {
            jdbcType = JdbcTypeEnum.SMALLINT;
        } else if (size < 11) {
            jdbcType = JdbcTypeEnum.INT;
        } else if (size < 20) {
            jdbcType = JdbcTypeEnum.BIGINT;
        } else {
            jdbcType = JdbcTypeEnum.NUMERIC;
        }
        
        String defaultValueString = null;
        if (defaultValue != null) {
            defaultValueString = String.valueOf(defaultValue);
        }
        DBColumnDef tableColumn = new DBColumnDef(primaryKey, columnName,
                this.tableName, jdbcType, size, 0, required,
                defaultValueString);
        
        addAndValidateNewColumn(tableColumn);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
    }
    
    /**
     * @param columnName
     * @param required
     * @param isDefaultNow
     * @return
     */
    @Override
    public B newColumnOfDate(String columnName, boolean required,
            boolean isDefaultNow) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.DATETIME;
        DBColumnDef tableColumn = new DBColumnDef(false, columnName,
                this.tableName, jdbcType, 0, 0, required,
                isDefaultNow ? "now(6)" : null);
        
        addAndValidateNewColumn(tableColumn);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
    }
    
    /**
     * @param columnName
     * @param required
     * @param defaultValue
     * @return
     */
    @Override
    public B newColumnOfBoolean(String columnName, boolean required,
            Boolean defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        
        JdbcTypeEnum jdbcType = JdbcTypeEnum.BIT;
        TableColumnDef tableColumn = new DBColumnDef(false, columnName,
                this.tableName, jdbcType, 1, 0, required, defaultValue != null
                        ? (defaultValue.booleanValue() ? "1" : "0") : null);
        
        addAndValidateNewColumn(tableColumn);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
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
    public B newColumnOfBigDecimal(String columnName, int precision, int scale,
            boolean required, BigDecimal defaultValue) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        if (precision <= 0) {
            precision = 16;
        }
        if (scale < 0) {
            scale = 2;
        }
        JdbcTypeEnum jdbcType = JdbcTypeEnum.NUMERIC;
        TableColumnDef tableColumn = new DBColumnDef(false, columnName,
                this.tableName, jdbcType, precision, scale, required,
                defaultValue == null ? null : defaultValue.toString());
        
        addAndValidateNewColumn(tableColumn);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
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
        
        //判断是否有重复的表字段
        for (TableColumnDef tc : this.columns) {
            AssertUtils.notTrue(
                    StringUtils.equalsIgnoreCase(tc.getColumnName(),
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
    public B newIndex(TableIndexDef ddlIndex) {
        AssertUtils.notNull(ddlIndex, "ddlIndex is null.");
        
        addAndValidateNewIndex(ddlIndex);//添加索引
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
    }
    
    /**
     * @param indexName
     * @param columnNames
     * @return
     */
    @Override
    public B newIndex(String indexName, String columnNames) {
        newIndex(false, indexName, columnNames);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
    }
    
    /**
     * @param constraintType
     * @param indexName
     * @param columnNames
     * @return
     */
    @Override
    public B newIndex(boolean unique, String indexName, String columnNames) {
        AssertUtils.notEmpty(indexName, "indexName is empty.");
        AssertUtils.notEmpty(columnNames, "columnNames is empty.");
        
        DBIndexDef newIndex = new DBIndexDef(indexName, columnNames, unique,
                this.tableName);
        
        addAndValidateNewIndex(newIndex);
        
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        return builder;
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
        AssertUtils.notEmpty(tableIndex.getColumnNames(),
                "tableIndex.columnNames is empty.");
        
        //判断是否有重复的索引字段
        for (TableIndexDef tidx : this.indexes) {
            //当indexName与columnName都相当时认为是重复的索引
            AssertUtils.notTrue(
                    StringUtils.equalsIgnoreCase(tidx.getIndexName(),
                            tableIndex.getIndexName()),
                    "Duplicate index:indexName:{} columnName:{}",
                    tableIndex.getIndexName(),
                    tidx.getColumnNames());
        }
        
        this.indexes.add(tableIndex);//添加字段
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
    protected final void printComment(String text) throws IOException {
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
     * 写入一个字符，和一个换行符
     * @param text The text to print
     */
    protected final void println(String text) throws IOException {
        print(text);
        println();
    }
    
    /** 
     * 写入一个换行符
     */
    protected final void println() throws IOException {
        print(LINE_SEPARATOR);
    }
    
    /**
     * 写入指定字符集
     * @param text The text to print
     */
    protected final void print(String text) throws IOException {
        this.writer.write(text);
    }
    
    /**
     * 写入指定字符集
     * @param text The text to print
     */
    protected final void deleteLastIndexOf(String text) throws IOException {
        StringBuffer sb = this.writer.getBuffer();
        if (sb.lastIndexOf(text) < 0) {
            return;
        }
        int start = sb.lastIndexOf(text);
        sb.delete(start, start + text.length());
    }
    
    /**
     * @return 返回 ddlDialect
     */
    protected final DDLDialect getDDLDialect() {
        return ddlDialect;
    }
    
    /**
     * @return
     */
    public final String tableName() {
        return tableName;
    }
    
    /**
     * @return 返回 columns
     */
    public List<TableColumnDef> getColumns() {
        return columns;
    }
    
    /**
     * @return 返回 indexes
     */
    public List<TableIndexDef> getIndexes() {
        return indexes;
    }
}
