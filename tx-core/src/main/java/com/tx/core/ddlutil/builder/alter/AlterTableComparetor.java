/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.alter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.helper.TableDefHelper;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.ddlutil.model.TableIndexDef;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 编辑表辅助类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AlterTableComparetor {
    
    //日志记录器
    private Logger logger = LoggerFactory.getLogger(TableDDLExecutor.class);
    
    /** 编辑表字段集合 */
    private Map<String, AlterTableColumn> alterTableColumnMap = new HashMap<>();
    
    /** 编辑表索引集合 */
    private Map<String, AlterTableIndex> alterTableIndexMap = new HashMap<>();
    
    /** 原表定义 */
    private TableDef sourceTableDef;
    
    /** 表字段 */
    private List<TableColumnDef> targetTableColumns;
    
    /** 表索引 */
    private List<TableIndexDef> targetTableIndexes;
    
    /** 是否需要编辑 */
    private boolean needAlter = false;
    
    /** 是否需要备份 */
    private boolean needBackup = false;
    
    /** 是否需要编辑主键 */
    private boolean needModifyPrimaryKey = false;
    
    /** 主键字段名 */
    private String primaryKeyColumnNames;
    
    /** 编辑表字段 */
    private List<AlterTableColumn> alterTableColumns = new ArrayList<>();
    
    /** 编辑表索引 */
    private List<AlterTableIndex> alterTableIndexes = new ArrayList<>();
    
    /** <默认构造函数> */
    public AlterTableComparetor() {
        super();
    }
    
    /** <默认构造函数> */
    public AlterTableComparetor(TableDef sourceTableDef) {
        super();
        this.sourceTableDef = sourceTableDef;
    }
    
    /**
     * 对比<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void compare() {
        AssertUtils.notNull(sourceTableDef, "sourceTable is null.");
        AssertUtils.notEmpty(targetTableColumns, "targetTableColumns is null.");
        
        String sourcePrimaryKeyColumnNames = TableDefHelper
                .parsePrimaryKeyColumnNames(sourceTableDef.getColumns());
        String targetPrimaryKeyColumnNames = TableDefHelper
                .parsePrimaryKeyColumnNames(targetTableColumns);
        
        this.needModifyPrimaryKey = !StringUtils.equalsIgnoreCase(
                sourcePrimaryKeyColumnNames, targetPrimaryKeyColumnNames);
        this.primaryKeyColumnNames = targetPrimaryKeyColumnNames;
        if (this.needModifyPrimaryKey) {
            this.needAlter = true;
        }
        
        //对比字段
        compareColumns();
        
        //对比索引
        compareIndexes();
    }
    
    /** 
     * 对比字段
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void compareColumns() {
        this.alterTableColumnMap.clear();
        this.alterTableColumns.clear();
        
        for (TableColumnDef sourceColumn : sourceTableDef.getColumns()) {
            AssertUtils.notEmpty(sourceColumn.getColumnName(),
                    "sourceColumn.columnName is empty.");
            
            alterTableColumnMap.put(sourceColumn.getColumnName().toUpperCase(),
                    new AlterTableColumn(sourceColumn));
        }
        for (TableColumnDef targetColumn : targetTableColumns) {
            AssertUtils.notEmpty(targetColumn.getColumnName(),
                    "targetColumn.columnName is empty.");
            
            if (alterTableColumnMap
                    .containsKey(targetColumn.getColumnName().toUpperCase())) {
                alterTableColumnMap
                        .get(targetColumn.getColumnName().toUpperCase())
                        .setTargetColumn(targetColumn);
            } else {
                AlterTableColumn newAlterTableColumn = new AlterTableColumn();
                newAlterTableColumn.setTargetColumn(targetColumn);
                alterTableColumnMap.put(
                        targetColumn.getColumnName().toUpperCase(),
                        newAlterTableColumn);
            }
        }
        for (AlterTableColumn alterColumn : alterTableColumnMap.values()) {
            alterColumn.compare();
            if (alterColumn.isStrictMatch()) {
                continue;
            }
            if (alterColumn.isNeedAlter()) {
                logger.info(
                        "检测到表字段需要升级.tablename:'{}'.columnname:'{}' .remark:'{}'",
                        this.getSourceTableDef().getTableName(),
                        alterColumn.getColumnName(),
                        alterColumn.getRemark());
                
                TableColumnDef sourceColumn = alterColumn.getSourceColumn();
                if (sourceColumn != null) {
                    logger.info(
                            "scol: jdbcType:'{}',size:'{}',scale:'{}',comment:'{}',defaultvalue:'{}',isRequired:'{}',isPrimaryKey:'{}'",
                            sourceColumn.getJdbcType(),
                            sourceColumn.getSize(),
                            sourceColumn.getScale(),
                            sourceColumn.getComment(),
                            sourceColumn.getDefaultValue(),
                            sourceColumn.isRequired(),
                            sourceColumn.isPrimaryKey());
                }
                TableColumnDef targetColumn = alterColumn.getTargetColumn();
                if (targetColumn != null) {
                    logger.info(
                            "tcol: jdbcType:'{}',size:'{}',scale:'{}',comment:'{}',defaultvalue:'{}',isRequired:'{}',isPrimaryKey:'{}'",
                            targetColumn.getJdbcType(),
                            targetColumn.getSize(),
                            targetColumn.getScale(),
                            targetColumn.getComment(),
                            targetColumn.getDefaultValue(),
                            targetColumn.isRequired(),
                            targetColumn.isPrimaryKey());
                }
                
                this.needAlter = true;
                this.alterTableColumns.add(alterColumn);
            }
            if (alterColumn.isNeedBackup()) {
                this.needBackup = true;
            }
        }
    }
    
    /** 
     * 对比索引<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void compareIndexes() {
        this.alterTableIndexMap.clear();
        this.alterTableIndexes.clear();
        
        for (TableIndexDef sourceIndex : sourceTableDef.getIndexes()) {
            AssertUtils.notEmpty(sourceIndex.getIndexName(),
                    "sourceIndex.indexName is empty.");
            
            alterTableIndexMap.put(sourceIndex.getIndexName().toUpperCase(),
                    new AlterTableIndex(sourceIndex));
        }
        for (TableIndexDef targetIndex : targetTableIndexes) {
            AssertUtils.notEmpty(targetIndex.getIndexName(),
                    "targetIndex.indexName is empty.");
            
            if (alterTableIndexMap
                    .containsKey(targetIndex.getIndexName().toUpperCase())) {
                alterTableIndexMap.get(targetIndex.getIndexName().toUpperCase())
                        .setTargetIndex(targetIndex);
            } else {
                AlterTableIndex newAlterTableIndex = new AlterTableIndex();
                newAlterTableIndex.setTargetColumn(targetIndex);
                alterTableIndexMap.put(targetIndex.getIndexName().toUpperCase(),
                        newAlterTableIndex);
            }
        }
        
        for (AlterTableIndex alterIndex : alterTableIndexMap.values()) {
            alterIndex.compare();
            if (alterIndex.isNeedAlter()) {
                this.needAlter = true;
                this.alterTableIndexes.add(alterIndex);
            }
        }
    }
    
    /**
     * @return 返回 sourceTableDef
     */
    public TableDef getSourceTableDef() {
        return sourceTableDef;
    }
    
    /**
     * @param 对sourceTableDef进行赋值
     */
    public void setSourceTableDef(TableDef sourceTableDef) {
        this.sourceTableDef = sourceTableDef;
    }
    
    /**
     * @return 返回 targetTableColumns
     */
    public List<TableColumnDef> getTargetTableColumns() {
        return targetTableColumns;
    }
    
    /**
     * @param 对targetTableColumns进行赋值
     */
    public void setTargetTableColumns(List<TableColumnDef> targetTableColumns) {
        this.targetTableColumns = targetTableColumns;
    }
    
    /**
     * @return 返回 targetTableIndexes
     */
    public List<TableIndexDef> getTargetTableIndexes() {
        return targetTableIndexes;
    }
    
    /**
     * @param 对targetTableIndexes进行赋值
     */
    public void setTargetTableIndexes(List<TableIndexDef> targetTableIndexes) {
        this.targetTableIndexes = targetTableIndexes;
    }
    
    /**
     * @return 返回 needAlter
     */
    public boolean isNeedAlter() {
        return needAlter;
    }
    
    /**
     * @param 对needAlter进行赋值
     */
    public void setNeedAlter(boolean needAlter) {
        this.needAlter = needAlter;
    }
    
    /**
     * @return 返回 needBackup
     */
    public boolean isNeedBackup() {
        return needBackup;
    }
    
    /**
     * @param 对needBackup进行赋值
     */
    public void setNeedBackup(boolean needBackup) {
        this.needBackup = needBackup;
    }
    
    /**
     * @return 返回 needModifyPrimaryKey
     */
    public boolean isNeedModifyPrimaryKey() {
        return needModifyPrimaryKey;
    }
    
    /**
     * @param 对needModifyPrimaryKey进行赋值
     */
    public void setNeedModifyPrimaryKey(boolean needModifyPrimaryKey) {
        this.needModifyPrimaryKey = needModifyPrimaryKey;
    }
    
    /**
     * @return 返回 primaryKeyColumnNames
     */
    public String getPrimaryKeyColumnNames() {
        return primaryKeyColumnNames;
    }
    
    /**
     * @param 对primaryKeyColumnNames进行赋值
     */
    public void setPrimaryKeyColumnNames(String primaryKeyColumnNames) {
        this.primaryKeyColumnNames = primaryKeyColumnNames;
    }
    
    /**
     * @return 返回 alterTableColumns
     */
    public List<AlterTableColumn> getAlterTableColumns() {
        return alterTableColumns;
    }
    
    /**
     * @param 对alterTableColumns进行赋值
     */
    public void setAlterTableColumns(List<AlterTableColumn> alterTableColumns) {
        this.alterTableColumns = alterTableColumns;
    }
    
    /**
     * @return 返回 alterTableIndexes
     */
    public List<AlterTableIndex> getAlterTableIndexes() {
        return alterTableIndexes;
    }
    
    /**
     * @param 对alterTableIndexes进行赋值
     */
    public void setAlterTableIndexes(List<AlterTableIndex> alterTableIndexes) {
        this.alterTableIndexes = alterTableIndexes;
    }
}
