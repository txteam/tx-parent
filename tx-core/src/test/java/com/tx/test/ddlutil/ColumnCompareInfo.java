/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月29日
 * <修改描述:>
 */
package com.tx.test.ddlutil;

import com.tx.core.ddlutil.model.TableColumnDef;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年4月29日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ColumnCompareInfo {
    
    private String columnName;
    
    private TableColumnDef sourceColumn;
    
    private TableColumnDef targetColumn;
    
    /** <默认构造函数> */
    public ColumnCompareInfo(String columnName, TableColumnDef sourceColumn) {
        super();
        this.columnName = columnName;
        this.sourceColumn = sourceColumn;
    }
    
    /** <默认构造函数> */
    public ColumnCompareInfo(String columnName, TableColumnDef sourceColumn,
            TableColumnDef targetColumn) {
        super();
        this.columnName = columnName;
        this.sourceColumn = sourceColumn;
        this.targetColumn = targetColumn;
    }
    
    /**
     * @return 返回 columnName
     */
    public String getColumnName() {
        return columnName;
    }
    
    /**
     * @param 对columnName进行赋值
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    /**
     * @return 返回 sourceColumn
     */
    public TableColumnDef getSourceColumn() {
        return sourceColumn;
    }
    
    /**
     * @param 对sourceColumn进行赋值
     */
    public void setSourceColumn(TableColumnDef sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    /**
     * @return 返回 targetColumn
     */
    public TableColumnDef getTargetColumn() {
        return targetColumn;
    }

    /**
     * @param 对targetColumn进行赋值
     */
    public void setTargetColumn(TableColumnDef targetColumn) {
        this.targetColumn = targetColumn;
    }
}
