/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月6日
 * <修改描述:>
 */
package com.tx.core.jdbc.model;

import org.apache.ibatis.type.JdbcType;

/**
 * 查询条件信息<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryConditionInfo {
    
    /** 查询条件类型 */
    private QueryConditionTypeEnum queryConditionType;
    
    /** 查询条件 key值，读取至anno,如果不存在则为getterName 如果是可识别类型则为getterName.foreignKeyGetterName */
    private String queryConditionKey;
    
    /** javaType */
    private Class<?> queryConditionJavaType;
    
    /** jdbcType */
    private JdbcType queryConditionJdbcType;
    
    /** <默认构造函数> */
    public QueryConditionInfo() {
        super();
    }

    /** <默认构造函数> */
    public QueryConditionInfo(QueryConditionTypeEnum queryConditionType,
            String queryConditionKey, Class<?> queryConditionJavaType,
            JdbcType queryConditionJdbcType) {
        super();
        this.queryConditionType = queryConditionType;
        this.queryConditionKey = queryConditionKey;
        this.queryConditionJavaType = queryConditionJavaType;
        this.queryConditionJdbcType = queryConditionJdbcType;
    }

    /**
     * @return 返回 queryConditionType
     */
    public QueryConditionTypeEnum getQueryConditionType() {
        return queryConditionType;
    }

    /**
     * @param 对queryConditionType进行赋值
     */
    public void setQueryConditionType(QueryConditionTypeEnum queryConditionType) {
        this.queryConditionType = queryConditionType;
    }

    /**
     * @return 返回 queryConditionKey
     */
    public String getQueryConditionKey() {
        return queryConditionKey;
    }

    /**
     * @param 对queryConditionKey进行赋值
     */
    public void setQueryConditionKey(String queryConditionKey) {
        this.queryConditionKey = queryConditionKey;
    }

    /**
     * @return 返回 queryConditionJavaType
     */
    public Class<?> getQueryConditionJavaType() {
        return queryConditionJavaType;
    }

    /**
     * @param 对queryConditionJavaType进行赋值
     */
    public void setQueryConditionJavaType(Class<?> queryConditionJavaType) {
        this.queryConditionJavaType = queryConditionJavaType;
    }

    /**
     * @return 返回 queryConditionJdbcType
     */
    public JdbcType getQueryConditionJdbcType() {
        return queryConditionJdbcType;
    }

    /**
     * @param 对queryConditionJdbcType进行赋值
     */
    public void setQueryConditionJdbcType(JdbcType queryConditionJdbcType) {
        this.queryConditionJdbcType = queryConditionJdbcType;
    }
}