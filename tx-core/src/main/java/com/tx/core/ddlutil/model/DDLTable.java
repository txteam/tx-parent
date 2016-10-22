/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.tx.core.ddlutil.DDLUtilsConstants;
import com.tx.core.util.ObjectUtils;

/**
 * 表定义<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DDLTable implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 829927103002085200L;
    
    /** 目录 */
    private String catalog = null;
    
    /** 数据库 */
    private String schema = null;
    
    /** 表名 */
    private String name = null;
    
    /** 表内容备注 */
    private String comment = "";
    
    /** 表类型：BASE TABLE|VIEW */
    private String type = DDLUtilsConstants.DDL_TABLE_TYPE_TABLE;
    
    /** 表字段. */
    private ArrayList<DDLColumn> columns = new ArrayList<DDLColumn>();
    
    /** 索引集合. */
    private ArrayList<DDLIndex> indexes = new ArrayList<DDLIndex>();
    
    /**
     * @return 返回 catalog
     */
    public String getCatalog() {
        return catalog;
    }
    
    /**
     * @param 对catalog进行赋值
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    
    /**
     * @return 返回 schema
     */
    public String getSchema() {
        return schema;
    }
    
    /**
     * @param 对schema进行赋值
     */
    public void setSchema(String schema) {
        this.schema = schema;
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
     * @return 返回 type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return 返回 columns
     */
    public ArrayList<DDLColumn> getColumns() {
        return columns;
    }
    
    /**
     * @param 对columns进行赋值
     */
    public void setColumns(ArrayList<DDLColumn> columns) {
        this.columns = columns;
    }
    
    /**
     * @return 返回 indexes
     */
    public ArrayList<DDLIndex> getIndexes() {
        return indexes;
    }
    
    /**
     * @param 对indexes进行赋值
     */
    public void setIndexes(ArrayList<DDLIndex> indexes) {
        this.indexes = indexes;
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int hashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "name");
        return hashCode;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = ObjectUtils.equals(this, obj, "name");
        return flag;
    }
}
