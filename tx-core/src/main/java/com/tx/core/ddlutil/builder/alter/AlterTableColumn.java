/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月2日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.alter;

import javax.swing.table.TableColumn;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 更新表字段<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AlterTableColumn {
    
    /** 字段名. */
    private String columnName;
    
    /** 是否匹配 */
    private boolean match = true;
    
    /** alterType */
    private AlterTypeEnum alterType;
    
    /** 原字段 */
    private TableColumnDef sourceColumn;
    
    /** 目标字段 */
    private TableColumnDef targetColumn;
    
    /** <默认构造函数> */
    public AlterTableColumn() {
        super();
    }
    
    /**
     * 设置原字段<br/>
     * <功能详细描述>
     * @param sourceColumn [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setSourceColumn(TableColumnDef sourceColumn) {
        AssertUtils.notNull(sourceColumn, "sourceColumn is null.");
        AssertUtils.notEmpty(sourceColumn.getColumnName(),
                "sourceColumn.columnName is empty.");
        
        this.sourceColumn = sourceColumn;
        if (StringUtils.isEmpty(this.columnName)) {
            this.columnName = sourceColumn.getColumnName().toUpperCase();
        } else {
            AssertUtils.isTrue(
                    StringUtils.equalsAnyIgnoreCase(this.columnName,
                            sourceColumn.getColumnName()),
                    "sourceColumn.columnName is not match.");
        }
    }
    
    /**
     * 设置目标字段<br/>
     * <功能详细描述>
     * @param targetColumn [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setTargetColumn(TableColumnDef targetColumn) {
        AssertUtils.notNull(targetColumn, "targetColumn is null.");
        AssertUtils.notEmpty(targetColumn.getColumnName(),
                "targetColumn.columnName is empty.");
        
        this.targetColumn = targetColumn;
        if (StringUtils.isEmpty(this.columnName)) {
            this.columnName = targetColumn.getColumnName().toUpperCase();
        } else {
            AssertUtils.isTrue(
                    StringUtils.equalsAnyIgnoreCase(this.columnName,
                            targetColumn.getColumnName()),
                    "targetColumn.columnName is not match.");
        }
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
     * @return 返回 match
     */
    public boolean isMatch() {
        return match;
    }
    
    /**
     * @param 对match进行赋值
     */
    public void setMatch(boolean match) {
        this.match = match;
    }
    
    /**
     * @return 返回 alterType
     */
    public AlterTypeEnum getAlterType() {
        return alterType;
    }
    
    /**
     * @param 对alterType进行赋值
     */
    public void setAlterType(AlterTypeEnum alterType) {
        this.alterType = alterType;
    }
    
    /**
     * @return 返回 sourceColumn
     */
    public TableColumnDef getSourceColumn() {
        return sourceColumn;
    }
    
    /**
     * @return 返回 targetColumn
     */
    public TableColumnDef getTargetColumn() {
        return targetColumn;
    }
    
    /**
     * 检测字段是否一致，是否需要升级<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void check() {
        AssertUtils.isTrue(
                this.sourceColumn != null || this.targetColumn != null,
                "sourceColumn and targetColumn are null.");
        
        if (this.sourceColumn == null) {
            this.match = false;
            this.alterType = AlterTypeEnum.ADD;
            return;
        }
        
        if (this.targetColumn == null) {
            this.match = false;
            this.alterType = AlterTypeEnum.DROP;
            return;
        }
        
        //根据类型判断是否需要进行升级，如果类型不匹配则需要进行升级
        if (!isMatchOfJdbcType(this.sourceColumn.getJdbcType(),
                this.targetColumn.getJdbcType())) {
            this.match = false;
            this.alterType = AlterTypeEnum.MODIFY;
            return;
        }
    }
    
    /**
     * 判断jdbc类型是否匹配
     * <功能详细描述>
     * @param sourceJdbcType
     * @param targetJdbcType
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("incomplete-switch")
    private static boolean isMatchOfJdbcType(JdbcTypeEnum sourceJdbcType,
            JdbcTypeEnum targetJdbcType) {
        AssertUtils.notNull(sourceJdbcType, "sourceJdbcType is null.");
        AssertUtils.notNull(targetJdbcType, "targetJdbcType is null.");
        
        if (sourceJdbcType.equals(targetJdbcType)) {
            return true;
        }
        
        switch (sourceJdbcType) {
            case TINYINT:
            case SMALLINT:
            case INT:
            case INTEGER:
            case BIGINT:
                switch (targetJdbcType) {
                    case TINYINT:
                    case SMALLINT:
                    case INT:
                    case INTEGER:
                    case BIGINT:
                        return true;
                }
                break;
            case FLOAT:
            case DOUBLE:
            case REAL:
            case NUMERIC:
            case DECIMAL:
                switch (targetJdbcType) {
                    case FLOAT:
                    case DOUBLE:
                    case REAL:
                    case NUMERIC:
                    case DECIMAL:
                        return true;
                }
                break;
            case CHAR:
            case VARCHAR:
            case NCHAR:
            case NVARCHAR:
            case TEXT:
            case LONGTEXT:
            case LONGVARCHAR:
            case TINYTEXT:
                switch (targetJdbcType) {
                    case CHAR:
                    case VARCHAR:
                    case NCHAR:
                    case NVARCHAR:
                    case TEXT:
                    case LONGTEXT:
                    case LONGVARCHAR:
                    case TINYTEXT:
                        return true;
                }
                break;
            case DATE:
            case DATETIME:
            case TIMESTAMP:
            case TIME:
                switch (targetJdbcType) {
                    case DATE:
                    case DATETIME:
                    case TIMESTAMP:
                    case TIME:
                        return true;
                }
                break;
            default:
                break;
        }
        
        return false;
    }
}
