/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月4日
 * <修改描述:>
 */
package com.tx.core.querier.model;

/**
 * 操作运算符<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum OperatorEnum {
    
    /**
     * 等于
     */
    eq("eq", "="),
    
    /**
     * 不等于
     */
    ne("ne", "<>"),
    
    /**
     * 大于
     */
    gt("gt", ">"),
    
    /**
     * 小于
     */
    lt("lt", "<"),
    
    /**
     * 大于等于
     */
    ge("ge", ">="),
    
    /**
     * 小于等于
     */
    le("le", "<="),
    
    /**
     * 相似
     */
    like("like", "LIKE"),
    
    /**
     * 包含
     */
    in("in", "IN"),
    
    /**
     * 不包含
     */
    notIn("notIn", "NOT IN"),
    
    /**
     * 为Null
     */
    isNull("isNull", "IS NULL"),
    
    /**
     * 不为Null
     */
    isNotNull("isNotNull", "IS NOT NULL");
    
    private final String key;
    
    private final String operator;
    
    /** <默认构造函数> */
    private OperatorEnum(String key, String operator) {
        this.key = key;
        this.operator = operator;
    }
    
    /**
     * @return 返回 key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * @return 返回 operator
     */
    public String getOperator() {
        return operator;
    }
    
}
