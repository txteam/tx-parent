/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.model;

import java.io.Serializable;

import com.tx.core.util.ObjectUtils;

/**
 * 表字段<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DDLColumn implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6431639195048067292L;
    
    /** 字段名. */
    private String name;
    
    /** 索引所在表 */
    private String tableName;
    
    /** jdbc类型 */
    private JdbcTypeEnum jdbcType;
    
    /** 字段类型 */
    private String columnType;
    
    /** 是否主键. */
    private boolean primaryKey;
    
    /** 是否必填. */
    private boolean required;
    
    /** 长度 */
    private int size = 64;
    
    /** 精度. */
    private int scale = 2;
    
    /** 默认值. */
    private String defaultValue;
    
    /** <默认构造函数> */
    public DDLColumn() {
        super();
    }
    
    /** <默认构造函数> */
    public DDLColumn(boolean primaryKey, String name, String tableName,
            JdbcTypeEnum jdbcType, int size, int scale, boolean required,
            String defaultValue) {
        super();
        this.name = name;
        this.tableName = tableName;
        this.jdbcType = jdbcType;
        this.primaryKey = primaryKey;
        this.required = required;
        this.size = size;
        this.scale = scale;
        this.defaultValue = defaultValue;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return 返回 columnType
     */
    public String getColumnType() {
        return columnType;
    }
    
    /**
     * @param 对columnType进行赋值
     */
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
    
    /**
     * @return 返回 primaryKey
     */
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
    public boolean isRequired() {
        return required;
    }
    
    /**
     * @param 对required进行赋值
     */
    public void setRequired(boolean required) {
        this.required = required;
    }
    
    /**
     * @return 返回 jdbcType
     */
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
     * @return 返回 size
     */
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
     * @return 返回 scale
     */
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
     * @return 返回 defaultValue
     */
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
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * @param 对tableName进行赋值
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int hashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "name",
                "tableName");
        return hashCode;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = ObjectUtils.equals(this, obj, "name", "tableName");
        return flag;
    }
    
}
