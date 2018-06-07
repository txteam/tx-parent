/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月4日
 * <修改描述:>
 */
package com.tx.core.mybatis.sqlbuilder;

import com.tx.core.jdbc.sqlbuilder.AbstractSQLBuilder;
import com.tx.core.jdbc.sqlbuilder.SQLStatement;

/**
 * Mapper构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlMapSQLBuilder extends AbstractSQLBuilder<SqlMapSQLBuilder> {
    
    /** <默认构造函数> */
    public SqlMapSQLBuilder() {
        super();
    }
    
    /**
     * @return
     */
    @Override
    public SqlMapSQLBuilder getSelf() {
        return this;
    }
    
    /**
     * @return
     */
    @Override
    protected SQLStatement initSQLStatement() {
        return new SqlMapSQLStatement();
    }
    
    /**
     * OR条件
     * @return
     */
    public SqlMapSQLBuilder OR() {
        sql().getLastList().add(SqlMapSQLStatement.SQL_MAP_OR);
        return getSelf();
    }
    
    /**
     * AND条件
     * @return
     */
    public SqlMapSQLBuilder AND() {
        sql().getLastList().add(SqlMapSQLStatement.SQL_MAP_AND);
        return getSelf();
    }
    
    //    public static void main(String[] args) {
    //        SqlMapSQLBuilder sql = new SqlMapSQLBuilder();
    //        sql.SELECT("id");
    //        sql.SELECT("code");
    //        sql.SELECT("name");
    //        sql.FROM("test_table");
    //        
    //        sql.WHERE("AND code1 = #{code1}");
    //        sql.WHERE("AND code2 = #{code2}");
    //        sql.WHERE("AND code3 = #{code3}");
    //        sql.WHERE("AND code = #{code}");
    //        
    //        sql.AND();
    //        sql.WHERE("AND andcode11 = #{andcode11}");
    //        sql.WHERE("AND andcode12 = #{andcode12}");
    //        
    //        System.out.println(sql.toString());
    //    }
}
