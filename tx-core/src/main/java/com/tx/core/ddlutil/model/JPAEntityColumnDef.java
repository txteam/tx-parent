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
public class JPAEntityColumnDef extends AbstractColumnDef
        implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6431639195048067292L;
    
    /** 对应的类的类型 */
    private Class<?> javaType;
    
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
        setColumnName(columnName);
        setJdbcType(jdbcType);
        setSize(size);
        setScale(scale);
        setRequired(required);
        setPrimaryKey(false);
        setDefaultValue(null);
        
        this.javaType = javaType;
    }
    
    /** <默认构造函数> */
    public JPAEntityColumnDef(String columnName, Class<?> javaType,
            JdbcTypeEnum jdbcType, int size, int scale, boolean required,boolean primaryKey) {
        super();
        setColumnName(columnName);
        setJdbcType(jdbcType);
        setSize(size);
        setScale(scale);
        setRequired(required);
        setPrimaryKey(primaryKey);
        setDefaultValue(null);
        
        this.javaType = javaType;
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
