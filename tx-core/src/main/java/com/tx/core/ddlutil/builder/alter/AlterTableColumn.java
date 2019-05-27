/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月2日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.alter;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;

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
    
    /** 是否嚴格匹配 */
    private boolean strictMatch = true;
    
    /** 是否需要改变 */
    private boolean needAlter = false;
    
    /** 是否需要备份表 */
    private boolean needBackup = false;
    
    /** alterType */
    private AlterTypeEnum alterType;
    
    /** 原字段 */
    private TableColumnDef sourceColumn;
    
    /** 目标字段 */
    private TableColumnDef targetColumn;
    
    /** 备注信息 */
    private String remark;
    
    /** <默认构造函数> */
    public AlterTableColumn() {
        super();
    }
    
    /** <默认构造函数> */
    public AlterTableColumn(TableColumnDef sourceColumn) {
        super();
        
        setSourceColumn(sourceColumn);
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
                    StringUtils.equalsIgnoreCase(this.columnName,
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
                    StringUtils.equalsIgnoreCase(this.columnName,
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
     * @return 返回 strictMatch
     */
    public boolean isStrictMatch() {
        return strictMatch;
    }
    
    /**
     * @param 对strictMatch进行赋值
     */
    public void setStrictMatch(boolean strictMatch) {
        this.strictMatch = strictMatch;
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
     * @return 返回 remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * 检测字段是否一致，是否需要升级<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void compare() {
        AssertUtils.isTrue(
                this.sourceColumn != null || this.targetColumn != null,
                "sourceColumn and targetColumn are null.");
        
        if (this.sourceColumn == null) {
            this.strictMatch = false;
            this.needAlter = true;
            this.needBackup = false;//新增字段时无需进行表备份
            this.alterType = AlterTypeEnum.ADD;
            this.remark = "新增字段";
            return;
        }
        
        if (this.targetColumn == null) {
            //刪除字段的操作
            this.strictMatch = false;
            if (this.sourceColumn.isRequired()) {
                //如果原字段为必填字段则需要修改字段为非必填
                this.alterType = AlterTypeEnum.MODIFY_TO_NULLABLE;
                this.needAlter = true;
                this.remark = "刪除必填字段: 将字段修改为非必填";
            } else {
                this.alterType = AlterTypeEnum.DROP;
                this.needAlter = false;
                this.remark = "刪除非必填字段";
            }
            this.needBackup = false;//删除字段时无需进行表备份
            return;
        }
        
        //根据类型判断是否需要进行升级，如果类型不匹配则需要进行升级
        if (!isMatchOfJdbcType(this.sourceColumn.getJdbcType(),
                this.targetColumn.getJdbcType())) {
            this.strictMatch = false;
            this.needAlter = true;
            this.alterType = AlterTypeEnum.MODIFY;
            this.remark = MessageUtils.format(
                    "类型不匹配.sourceType:{} targetType:{}",
                    new Object[] { this.sourceColumn.getJdbcType(),
                            this.targetColumn.getJdbcType() });
            this.needBackup = true;//表字段类型变更，需要备份表
            return;
        }
        
        if (!isMatchOfSize(this.targetColumn.getJdbcType(),
                this.sourceColumn.getSize(),
                this.targetColumn.getSize())) {
            this.strictMatch = false;
            this.needAlter = true;
            this.alterType = AlterTypeEnum.MODIFY;
            this.remark = MessageUtils.format(
                    "size不匹配.jdbcType:{} sourceSize:{} targetSize:{}",
                    new Object[] { this.sourceColumn.getJdbcType(),
                            this.sourceColumn.getSize(),
                            this.targetColumn.getSize() });
            this.needBackup = false;//表字段长度变化，变长，无需进行备份表
            return;
        }
        
        if (!isMatchOfScale(this.targetColumn.getJdbcType(),
                this.sourceColumn.getScale(),
                this.targetColumn.getScale())) {
            this.strictMatch = false;
            this.needAlter = true;
            this.alterType = AlterTypeEnum.MODIFY;
            this.remark = MessageUtils.format(
                    "size不匹配.jdbcType:{} sourceSize:{} targetSize:{}",
                    new Object[] { this.sourceColumn.getJdbcType(),
                            this.sourceColumn.getScale(),
                            this.targetColumn.getScale() });
            if (this.sourceColumn.getScale() > this.targetColumn.getScale()) {
                this.needBackup = true;//表字段精度变化，可能出现精度丢失，需要备份数据
            } else {
                this.needBackup = false;
            }
            return;
        }
        
        if (this.sourceColumn.isRequired() != this.targetColumn.isRequired()) {
            this.strictMatch = false;
            this.needAlter = true;
            this.alterType = AlterTypeEnum.MODIFY;
            //是否必填属性变化进行编辑
            this.needBackup = false;//必填属性变化无需进行数据备份
            return;
        }
        
        if (!isMatchOfDefaultValue(this.targetColumn.getJdbcType(),
                this.sourceColumn.getDefaultValue(),
                this.targetColumn.getDefaultValue())) {
            this.strictMatch = false;
            this.needAlter = true;
            this.alterType = AlterTypeEnum.MODIFY;
            //是否必填属性变化进行编辑
            this.needBackup = false;//默认值属性变化无需进行数据备份
            return;
        }
        
        this.strictMatch = true;
        this.needAlter = false;
        this.needBackup = false;
        this.alterType = null;
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
                    case BIT:
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
                AssertUtils.isTrue(false,
                        "未知的jdbcType:{}",
                        new Object[] { sourceJdbcType });
                break;
        }
        
        return false;
    }
    
    /**
     * 判断长度字段是否匹配<br/>
     * <功能详细描述>
     * @param jdbcType
     * @param sourceSize
     * @param targetSize
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static boolean isMatchOfSize(JdbcTypeEnum jdbcType, int sourceSize,
            int targetSize) {
        AssertUtils.notNull(jdbcType, "jdbcType is null.");
        
        switch (jdbcType) {
            //无需检测长度的类型
            case BIT:
            case TINYINT:
            case SMALLINT:
            case INT:
            case INTEGER:
            case BIGINT:
                //...
            case DATE:
            case DATETIME:
            case TIMESTAMP:
            case TIME:
                return true;
            case FLOAT:
            case DOUBLE:
            case REAL:
            case NUMERIC:
            case DECIMAL:
                if (targetSize > sourceSize) {
                    return false;
                }
                return true;
            case CHAR:
            case VARCHAR:
            case NCHAR:
            case NVARCHAR:
            case TEXT:
            case LONGTEXT:
            case LONGVARCHAR:
            case TINYTEXT:
                if (targetSize > sourceSize) {
                    return false;
                }
                return true;
            default:
                AssertUtils.isTrue(false,
                        "未知的jdbcType:{}",
                        new Object[] { jdbcType });
                return false;
        }
    }
    
    private static boolean isMatchOfScale(JdbcTypeEnum jdbcType,
            int sourceScale, int targetScale) {
        AssertUtils.notNull(jdbcType, "jdbcType is null.");
        
        switch (jdbcType) {
            //无需检测长度的类型
            case BIT:
            case TINYINT:
            case SMALLINT:
            case INT:
            case INTEGER:
            case BIGINT:
                //...
            case DATE:
            case DATETIME:
            case TIMESTAMP:
            case TIME:
                return true;
            case CHAR:
            case VARCHAR:
            case NCHAR:
            case NVARCHAR:
            case TEXT:
            case LONGTEXT:
            case LONGVARCHAR:
            case TINYTEXT:
                return true;
            case FLOAT:
            case DOUBLE:
            case REAL:
            case NUMERIC:
            case DECIMAL:
                if (targetScale != sourceScale) {
                    return false;
                }
                return true;
            default:
                AssertUtils.isTrue(false,
                        "未知的jdbcType:{}",
                        new Object[] { jdbcType });
                return false;
        }
    }
    
    private static boolean isMatchOfDefaultValue(JdbcTypeEnum jdbcType,
            String sourceDefaultValue, String targetDefaultValue) {
        AssertUtils.notNull(jdbcType, "jdbcType is null.");
        
        switch (jdbcType) {
            case DATE:
            case DATETIME:
            case TIMESTAMP:
            case TIME:
                if (StringUtils.isEmpty(sourceDefaultValue)
                        && StringUtils.isEmpty(targetDefaultValue)) {
                    return true;
                } else if (!StringUtils.isEmpty(sourceDefaultValue)
                        && !StringUtils.isEmpty(targetDefaultValue)) {
                    return true;
                } else {
                    return false;
                }
                //无需检测长度的类型
            case BIT:
            case TINYINT:
            case SMALLINT:
            case INT:
            case INTEGER:
            case BIGINT:
            case CHAR:
            case VARCHAR:
            case NCHAR:
            case NVARCHAR:
            case TEXT:
            case LONGTEXT:
            case LONGVARCHAR:
            case TINYTEXT:
            case FLOAT:
            case DOUBLE:
            case REAL:
            case NUMERIC:
            case DECIMAL:
            default:
                if (StringUtils.equals(sourceDefaultValue,
                        targetDefaultValue)) {
                    return true;
                } else {
                    return false;
                }
        }
    }
}
