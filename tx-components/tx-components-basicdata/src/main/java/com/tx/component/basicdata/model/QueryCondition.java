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
    
    /** 条件传入关键字 */
    private String key;
    
    /** <默认构造函数> */
    public QueryCondition() {
        // TODO Auto-generated constructor stub
    }

    /** <默认构造函数> */
    public QueryCondition(String name, JdbcType jdbcType, String key,
            String expression) {
        super();
        this.name = name;
        this.key = key;
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
}
