/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月4日
 * <修改描述:>
 */
package com.tx.core.mybatis.sqlbuilder;

import com.tx.core.exceptions.SILException;
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
        throw new SILException("不支持的操作.");
        
        //sql().getLastList().add(SqlMapSQLStatement.SQL_MAP_OR);
        //return getSelf();
    }
    
    /**
     * AND条件
     * @return
     */
    public SqlMapSQLBuilder AND() {
        throw new SILException("不支持的操作.");
        
        //sql().getLastList().add(SqlMapSQLStatement.SQL_MAP_AND);
        //return getSelf();
    }
}
