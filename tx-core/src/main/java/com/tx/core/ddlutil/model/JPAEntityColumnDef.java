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
public class JPAEntityColumnDef implements Serializable, TableColumnDef {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6431639195048067292L;
    
    /** 字段名. */
    private String columnName;
    
    /** 表字段注释 */
    private String comment;
    
    /** 索引所在表 */
    private String tableName;
    
    /** 对应的类的类型 */
    private Class<?> javaType;
    
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
    
    /** 排序优先级 */
    private int orderPriority = 0;
    
    /** <默认构造函数> */
    public JPAEntityColumnDef() {
        super();
    }
    
    /** <默认构造函数> */
    public JPAEntityColumnDef(String columnName, Class<?> javaType,
            JdbcTypeEnum jdbcType, int size, int scale, boolean required) {
        super();
        this.columnName = columnName;
        this.javaType = javaType;
        this.jdbcType = jdbcType;
        this.size = size;
        this.scale = scale;
        this.required = required;
        
        this.primaryKey = false;
        this.tableName = null;
        this.defaultValue = null;
    }
    
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
     * @return
     */
    @Override
    public String getColumnType() {
        return columnType;
    }
    
    /**
     * @param 对columnType进行赋值
     */
    @Override
    public void setColumnType(String columnType) {
        this.columnType = columnType;
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
        return required;
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
     * @return
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
     * @return 返回 orderPriority
     */
    public int getOrderPriority() {
        return orderPriority;
    }
    
    /**
     * @param 对orderPriority进行赋值
     */
    public void setOrderPriority(int orderPriority) {
        this.orderPriority = orderPriority;
    }
    
    /**
     * @return 返回 javaType
     */
    public Class<?> getJavaType() {
        return javaType;
    }
    
    /**
     * @param 对javaType进行赋值
     */
    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }
    
    /**
     * @return 返回 comment
     */
    public String getComment() {
        return comment;
    }
    
    /**
     * @param 对comment进行赋值
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int hashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "columnName",
                "tableName");
        return hashCode;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = ObjectUtils.equals(this, obj, "columnName", "tableName");
        return flag;
    }
    
}
