/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import org.apache.ibatis.type.JdbcType;

/**
 * 查询条件
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryCondition {
    
    /** 服务于界面显示查询条件 */
    private String name;
    
    /** 传入数据映射 */
    private JdbcType jdbcType = JdbcType.VARCHAR;
    
    /** 条件传入关键字 */
    private String key;
    
    /** 条件拼接表达式 */
    private String expression;
    
    /** <默认构造函数> */
    public QueryCondition() {
        // TODO Auto-generated constructor stub
    }

    /** <默认构造函数> */
    public QueryCondition(String name, JdbcType jdbcType, String key,
            String expression) {
        super();
        this.name = name;
        this.jdbcType = jdbcType;
        this.key = key;
        this.expression = expression;
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
     * @return 返回 jdbcType
     */
    public JdbcType getJdbcType() {
        return jdbcType;
    }

    /**
     * @param 对jdbcType进行赋值
     */
    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    /**
     * @return 返回 key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param 对key进行赋值
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return 返回 expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * @param 对expression进行赋值
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }
}
