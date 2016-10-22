/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月21日
 * <修改描述:>
 */
package com.tx.core.ddlutil.model;

import java.io.Serializable;

import com.tx.core.util.ObjectUtils;

/**
 * DDL索引定义：如果是联合唯一键，那么这里就会有多条数据<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DDLIndex implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 1491278125877027478L;
    
    /** 索引名称. */
    private String name;
    
    /** 索引所在字段名 */
    private String columnName;
    
    /** 索引所在表名 */
    private String tableName;
    
    /** 是否唯一键 */
    private boolean unique;
    
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
     * @return 返回 unique
     */
    public boolean isUnique() {
        return unique;
    }
    
    /**
     * @param 对unique进行赋值
     */
    public void setUnique(boolean unique) {
        this.unique = unique;
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
     * @return
     */
    @Override
    public int hashCode() {
        int hashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "name",
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
        boolean flag = ObjectUtils.equals(this,
                obj,
                "name",
                "columnName",
                "tableName");
        return flag;
    }
    
}
