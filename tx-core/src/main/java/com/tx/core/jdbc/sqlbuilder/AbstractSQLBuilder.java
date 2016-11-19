/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月19日
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlbuilder;

/**
 * Sql构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractSQLBuilder<T> {
    
    private static final String AND = ") \nAND (";
    
    private static final String OR = ") \nOR (";
    
    private SQLStatement sql;
    
    /** <默认构造函数> */
    public AbstractSQLBuilder() {
        super();
        this.sql = initSQLStatement();
    }
    
    protected SQLStatement initSQLStatement() {
        SQLStatement sqlStatement = new SQLStatement();
        return sqlStatement;
    }
    
    public abstract T getSelf();
    
    public T UPDATE(String table) {
        sql().statementType = SQLStatementTypeEnum.UPDATE;
        sql().tables.add(table);
        return getSelf();
    }
    
    public T SET(String sets) {
        sql().sets.add(sets);
        return getSelf();
    }
    
    public T INSERT_INTO(String tableName) {
        sql().statementType = SQLStatementTypeEnum.INSERT;
        sql().tables.add(tableName);
        return getSelf();
    }
    
    public T VALUES(String columns, String values) {
        sql().columns.add(columns);
        sql().values.add(values);
        return getSelf();
    }
    
    public T SELECT(String columns) {
        sql().statementType = SQLStatementTypeEnum.SELECT;
        sql().select.add(columns);
        return getSelf();
    }
    
    public T SELECT_DISTINCT(String columns) {
        sql().distinct = true;
        SELECT(columns);
        return getSelf();
    }
    
    public T DELETE_FROM(String table) {
        sql().statementType = SQLStatementTypeEnum.DELETE;
        sql().tables.add(table);
        return getSelf();
    }
    
    public T FROM(String table) {
        sql().tables.add(table);
        return getSelf();
    }
    
    public T JOIN(String join) {
        sql().join.add(join);
        return getSelf();
    }
    
    public T INNER_JOIN(String join) {
        sql().innerJoin.add(join);
        return getSelf();
    }
    
    public T LEFT_OUTER_JOIN(String join) {
        sql().leftOuterJoin.add(join);
        return getSelf();
    }
    
    public T RIGHT_OUTER_JOIN(String join) {
        sql().rightOuterJoin.add(join);
        return getSelf();
    }
    
    public T OUTER_JOIN(String join) {
        sql().outerJoin.add(join);
        return getSelf();
    }
    
    public T WHERE(String conditions) {
        sql().where.add(conditions);
        sql().lastList = sql().where;
        return getSelf();
    }
    
    public T OR() {
        sql().lastList.add(OR);
        return getSelf();
    }
    
    public T AND() {
        sql().lastList.add(AND);
        return getSelf();
    }
    
    public T GROUP_BY(String columns) {
        sql().groupBy.add(columns);
        return getSelf();
    }
    
    public T HAVING(String conditions) {
        sql().having.add(conditions);
        sql().lastList = sql().having;
        return getSelf();
    }
    
    public T ORDER_BY(String columns) {
        sql().orderBy.add(columns);
        return getSelf();
    }
    
    private SQLStatement sql() {
        return sql;
    }
    
    public <A extends Appendable> A usingAppender(A a) {
        sql().sql(a);
        return a;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sql().sql(sb);
        return sb.toString();
    }
}
