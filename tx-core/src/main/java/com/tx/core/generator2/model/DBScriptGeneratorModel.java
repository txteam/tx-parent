/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-27
 * <修改描述:>
 */
package com.tx.core.generator2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tx.core.ddlutil.dialect.Dialect4DDL;
import com.tx.core.ddlutil.helper.JPAEntityDDLHelper;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.ddlutil.model.TableIndexDef;

/**
 * 数据脚本映射<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DBScriptGeneratorModel {
    
    /** 表注释 */
    private final String comment;
    
    /** 表名 */
    private final String tableName;
    
    /** 主键字段名 */
    private final String primaryKey;
    
    /** 表定义 */
    private final TableDef tableDef;
    
    /** 字段定义 */
    private final List<? extends TableColumnDef> columnDefs;
    
    /** 索引定义 */
    private final List<? extends TableIndexDef> indexDefs;
    
    /** 字段类型映射 */
    private final Map<String, String> columnTypeMap;
    
    /** 默认构造函数 */
    public DBScriptGeneratorModel(Class<?> entityType, Dialect4DDL ddlDialect) {
        super();
        TableDef td = JPAEntityDDLHelper.analyzeToTableDefDetail(entityType,
                ddlDialect);
        
        this.tableDef = td;
        this.tableName = td.getTableName();
        this.comment = td.getComment();
        this.columnDefs = td.getColumns();
        this.indexDefs = td.getIndexes();
        this.primaryKey = String.join(",",
                columnDefs.stream().filter(column -> {
                    return column.isPrimaryKey();
                }).map(TableColumnDef::getColumnName).collect(
                        Collectors.toList()));
        
        this.columnTypeMap = new HashMap<>();
        for (TableColumnDef column : this.columnDefs) {
            this.columnTypeMap.put(column.getColumnName(),
                    column.getColumnType(ddlDialect));
        }
    }
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return this.tableName;
    }
    
    /**
     * @return 返回 comment
     */
    public String getComment() {
        return comment;
    }
    
    /**
     * @return 返回 primaryKey
     */
    public String getPrimaryKey() {
        return primaryKey;
    }
    
    /**
     * @return 返回 tableDef
     */
    public TableDef getTableDef() {
        return tableDef;
    }
    
    /**
     * @return 返回 columnDefs
     */
    public List<? extends TableColumnDef> getColumnDefs() {
        return columnDefs;
    }
    
    /**
     * @return 返回 indexDefs
     */
    public List<? extends TableIndexDef> getIndexDefs() {
        return indexDefs;
    }

    /**
     * @return 返回 columnTypeMap
     */
    public Map<String, String> getColumnTypeMap() {
        return columnTypeMap;
    }
}
