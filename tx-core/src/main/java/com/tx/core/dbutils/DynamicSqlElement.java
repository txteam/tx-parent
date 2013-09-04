/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-5
 * <修改描述:>
 */
package com.tx.core.dbutils;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;


 /**
  * 动态sql元件用以支持动态获取sql
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DynamicSqlElement {
    
    private String sql;
    
    private PreparedStatementSetter setter;
    
    private RowCallbackHandler handler;

    /** <默认构造函数> */
    public DynamicSqlElement(String sql, PreparedStatementSetter setter,
            RowCallbackHandler handler) {
        super();
        this.sql = sql;
        this.setter = setter;
        this.handler = handler;
    }

    /**
     * @return 返回 sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * @return 返回 setter
     */
    public PreparedStatementSetter getSetter() {
        return setter;
    }

    /**
     * @return 返回 handler
     */
    public RowCallbackHandler getHandler() {
        return handler;
    }
}
