/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.core.ddlutil.model;

import com.tx.core.ddlutil.dialect.Dialect4DDL;

/**
  * 抽象字段定义<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月1日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public abstract class AbstractColumnDef implements TableColumnDef {
    
    /** 字段名. */
    private String columnName;
    
    /** 表字段注释 */
    private String comment;
    
    /** jdbc类型 */
    private JdbcTypeEnum jdbcType;
    
    /** 长度 */
    private int size = 255;
    
    /** 精度. */
    private int scale = 0;
    
    /** 默认值. */
    private String defaultValue;
    
    /** 是否主键.:保留属性，主键根据索引进行建立 */
    private boolean primaryKey = false;
    
    /** 是否必填. */
    private boolean required = false;
    
    /**
     * @return
     */
    @Override
    public String getColumnName() {
        return this.columnName;
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    /**
     * @return 返回 primaryKey
     */
    @Override
    public boolean isPrimaryKey() {
        return primaryKey;
    }
    
    /**
     * @param 对primaryKey进行赋值
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    /**
     * @return 返回 required
     */
    @Override
    public boolean isRequired() {
        //如果为主键则必填
        return this.primaryKey || required;
    }
    
    /**
     * @param 对required进行赋值
     */
    public void setRequired(boolean required) {
        this.required = required;
    }
    
    /**
     * @return
     */
    @Override
    public JdbcTypeEnum getJdbcType() {
        return jdbcType;
    }
    
    /**
     * @param 对jdbcType进行赋值
     */
    public void setJdbcType(JdbcTypeEnum jdbcType) {
        this.jdbcType = jdbcType;
    }
    
    /**
     * @return
     */
    @Override
    public int getSize() {
        return size;
    }
    
    /**
     * @param 对size进行赋值
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * @return
     */
    @Override
    public int getScale() {
        return scale;
    }
    
    /**
     * @param 对scale进行赋值
     */
    public void setScale(int scale) {
        this.scale = scale;
    }
    
    /**
     * @return
     */
    @Override
    public String getDefaultValue() {
        return defaultValue;
    }
    
    /**
     * @param 对defaultValue进行赋值
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    /**
     * @return 返回 comment
     */
    @Override
    public String getComment() {
        return comment;
    }
    
    /**
     * @param 对comment进行赋值
     */
    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * @param ddlDialect
     * @return
     */
    @Override
    public String getColumnType(Dialect4DDL ddlDialect) {
        String columnType = ddlDialect.getDialect()
                .getTypeName(jdbcType.getSqlType(), size, size, scale);
        return columnType;
    }
}
