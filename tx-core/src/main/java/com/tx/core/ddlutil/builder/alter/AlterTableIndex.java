/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月2日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.alter;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.ddlutil.model.TableIndexDef;
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
public class AlterTableIndex {
    
    /** 字段名. */
    private String indexName;
    
    /** alterType */
    private AlterTypeEnum alterType;
    
    /** 是否需要改变 */
    private boolean needAlter = false;
    
    /** 原索引 */
    private TableIndexDef sourceIndex;
    
    /** 目标索引 */
    private TableIndexDef targetIndex;
    
    /** 备注信息 */
    private String remark;
    
    /** <默认构造函数> */
    public AlterTableIndex() {
        super();
    }
    
    /** <默认构造函数> */
    public AlterTableIndex(TableIndexDef sourceIndex) {
        super();
        
        setSourceColumn(sourceIndex);
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
    public void setSourceColumn(TableIndexDef sourceIndex) {
        AssertUtils.notNull(sourceIndex, "sourceIndex is null.");
        AssertUtils.notEmpty(sourceIndex.getIndexName(),
                "sourceIndex.indexName is empty.");
        
        this.sourceIndex = sourceIndex;
        if (StringUtils.isEmpty(this.indexName)) {
            this.indexName = sourceIndex.getIndexName().toUpperCase();
        } else {
            AssertUtils.isTrue(
                    StringUtils.equalsAnyIgnoreCase(this.indexName,
                            sourceIndex.getIndexName()),
                    "sourceIndex.indexName is not match.");
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
    public void setTargetColumn(TableIndexDef targetIndex) {
        AssertUtils.notNull(targetIndex, "targetIndex is null.");
        AssertUtils.notEmpty(targetIndex.getIndexName(),
                "targetIndex.indexName is empty.");
        
        this.targetIndex = targetIndex;
        if (StringUtils.isEmpty(this.indexName)) {
            this.indexName = targetIndex.getIndexName().toUpperCase();
        } else {
            AssertUtils.isTrue(
                    StringUtils.equalsAnyIgnoreCase(this.indexName,
                            targetIndex.getIndexName()),
                    "targetIndex.columnName is not match.");
        }
    }
    
    /**
     * @return 返回 indexName
     */
    public String getIndexName() {
        return indexName;
    }
    
    /**
     * @param 对indexName进行赋值
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
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
     * @return 返回 sourceIndex
     */
    public TableIndexDef getSourceIndex() {
        return sourceIndex;
    }
    
    /**
     * @param 对sourceIndex进行赋值
     */
    public void setSourceIndex(TableIndexDef sourceIndex) {
        this.sourceIndex = sourceIndex;
    }
    
    /**
     * @return 返回 targetIndex
     */
    public TableIndexDef getTargetIndex() {
        return targetIndex;
    }
    
    /**
     * @param 对targetIndex进行赋值
     */
    public void setTargetIndex(TableIndexDef targetIndex) {
        this.targetIndex = targetIndex;
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
     * 检测字段是否一致，是否需要升级<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void compare() {
        AssertUtils.isTrue(this.sourceIndex != null || this.targetIndex != null,
                "sourceIndex and targetIndex are null.");
        
        if (this.sourceIndex == null) {
            this.needAlter = true;
            this.alterType = AlterTypeEnum.ADD;
            this.remark = "新增索引:" + this.targetIndex.getIndexName();
            return;
        }
        
        if (this.targetIndex == null) {
            this.alterType = AlterTypeEnum.DROP;
            this.needAlter = true;
            this.remark = "刪除索引:" + this.sourceIndex.getIndexName();
            return;
        }
        
        if (this.sourceIndex.isUniqueKey() != this.targetIndex.isUniqueKey()
                || StringUtils.equalsIgnoreCase(
                        this.sourceIndex.getColumnNames(),
                        this.targetIndex.getColumnNames())) {
            this.alterType = AlterTypeEnum.MODIFY;
            this.needAlter = false;
            this.remark = "编辑索引:" + this.sourceIndex.getIndexName();
            return;
        }
    }
}
