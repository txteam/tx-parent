/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.core.jdbc.query;


 /**
  * 前置类型枚举
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public enum PrependTypeEnum {
    
    AND("AND"),
    OR("OR"),
    AND_EXISTS("AND EXIST"),
    AND_NOT_EXIST("AND NOT EXIST");
    
    private String sql;
    
    /** <默认构造函数> */
    private PrependTypeEnum(String sql) {
        this.sql = sql;
    }

    /**
     * @return 返回 sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * @param 对sql进行赋值
     */
    public void setSql(String sql) {
        this.sql = sql;
    }
}
