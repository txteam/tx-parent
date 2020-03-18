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
public class DBColumnDef extends AbstractColumnDef implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6431639195048067292L;
    
    /** 索引所在表 */
    private String tableName;
    
    /** 排序优先级 */
    private int priority = 0;
    
    /** <默认构造函数> */
    public DBColumnDef() {
        super();
    }
    
    /** <默认构造函数> */
    public DBColumnDef(boolean primaryKey, String columnName, String tableName,
            JdbcTypeEnum jdbcType, int size, int scale, boolean required,
            String defaultValue) {
        super();
        setPrimaryKey(primaryKey);
        setColumnName(columnName);
        setTableName(tableName);
        setJdbcType(jdbcType);
        setRequired(required);
        setSize(size);
        setScale(scale);
        setDefaultValue(defaultValue);
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
    public int getPriority() {
        return priority;
    }
    
    /**
     * @param 对orderPriority进行赋值
     */
    public void setPriority(int priority) {
        this.priority = priority;
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
        if (obj == null) {
            return false;
        }
        
        boolean flag = ObjectUtils.equals(this, obj, "columnName", "tableName");
        return flag;
    }
    
}
