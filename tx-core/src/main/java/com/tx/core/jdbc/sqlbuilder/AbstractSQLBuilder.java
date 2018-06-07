/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月19日
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlbuilder;

import java.util.Arrays;

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
    
    private SQLStatement sql;
    
    /** <默认构造函数> */
    public AbstractSQLBuilder() {
        super();
        
        this.sql = initSQLStatement();
    }
    
    /**
     * 获取当前对象自身的句柄<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract T getSelf();
    
    /**
     * 提供给子类扩展SQLStatement<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return SQLStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected SQLStatement initSQLStatement() {
        SQLStatement sqlStatement = new SQLStatement();
        return sqlStatement;
    }
    
    public T STATEMENT_TYPE(SQLStatementTypeEnum StatementType) {
        if (StatementType != null) {
            sql().statementType = StatementType;
        }
        return getSelf();
    }
    
    public T UPDATE(String table) {
        sql().statementType = SQLStatementTypeEnum.UPDATE;
        sql().tables.add(table);
        return getSelf();
    }
    
    public T SET(String sets) {
        sql().sets.add(sets);
        return getSelf();
    }
    
    public T SET(String... sets) {
        sql().sets.addAll(Arrays.asList(sets));
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
    
    public T INTO_COLUMNS(String... columns) {
        sql().columns.addAll(Arrays.asList(columns));
        return getSelf();
    }
    
    public T INTO_VALUES(String... values) {
        sql().values.addAll(Arrays.asList(values));
        return getSelf();
    }
    
    public T FIND(String columns) {
        sql().statementType = SQLStatementTypeEnum.FIND;
        sql().select.add(columns);
        return getSelf();
    }
    
    public T FIND(String... columns) {
        sql().statementType = SQLStatementTypeEnum.FIND;
        sql().select.addAll(Arrays.asList(columns));
        return getSelf();
    }
    
    public T FIND_DISTINCT(String columns) {
        sql().distinct = true;
        FIND(columns);
        return getSelf();
    }
    
    public T FIND_DISTINCT(String... columns) {
        sql().distinct = true;
        FIND(columns);
        return getSelf();
    }
    
    public T QUERY(String columns) {
        sql().statementType = SQLStatementTypeEnum.QUERY;
        sql().select.add(columns);
        return getSelf();
    }
    
    public T QUERY(String... columns) {
        sql().statementType = SQLStatementTypeEnum.QUERY;
        sql().select.addAll(Arrays.asList(columns));
        return getSelf();
    }
    
    public T QUERY_DISTINCT(String columns) {
        sql().distinct = true;
        QUERY(columns);
        return getSelf();
    }
    
    public T QUERY_DISTINCT(String... columns) {
        sql().distinct = true;
        QUERY(columns);
        return getSelf();
    }
    
    public T COUNT() {
        sql().statementType = SQLStatementTypeEnum.COUNT;
        return getSelf();
    }
    
    public T COUNT(String columns) {
        sql().statementType = SQLStatementTypeEnum.COUNT;
        sql().select.add(columns);
        return getSelf();
    }
    
    public T SELECT(String columns) {
        sql().statementType = SQLStatementTypeEnum.SELECT;
        sql().select.add(columns);
        return getSelf();
    }
    
    public T SELECT(String... columns) {
        sql().statementType = SQLStatementTypeEnum.SELECT;
        sql().select.addAll(Arrays.asList(columns));
        return getSelf();
    }
    
    public T SELECT_DISTINCT(String columns) {
        sql().distinct = true;
        SELECT(columns);
        return getSelf();
    }
    
    public T SELECT_DISTINCT(String... columns) {
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
    
    public T FROM(String... tables) {
        sql().tables.addAll(Arrays.asList(tables));
        return getSelf();
    }
    
    public T JOIN(String join) {
        sql().join.add(join);
        return getSelf();
    }
    
    public T JOIN(String... joins) {
        sql().join.addAll(Arrays.asList(joins));
        return getSelf();
    }
    
    public T INNER_JOIN(String join) {
        sql().innerJoin.add(join);
        return getSelf();
    }
    
    public T INNER_JOIN(String... joins) {
        sql().innerJoin.addAll(Arrays.asList(joins));
        return getSelf();
    }
    
    public T LEFT_OUTER_JOIN(String join) {
        sql().leftOuterJoin.add(join);
        return getSelf();
    }
    
    public T LEFT_OUTER_JOIN(String... joins) {
        sql().leftOuterJoin.addAll(Arrays.asList(joins));
        return getSelf();
    }
    
    public T RIGHT_OUTER_JOIN(String join) {
        sql().rightOuterJoin.add(join);
        return getSelf();
    }
    
    public T RIGHT_OUTER_JOIN(String... joins) {
        sql().rightOuterJoin.addAll(Arrays.asList(joins));
        return getSelf();
    }
    
    public T OUTER_JOIN(String join) {
        sql().outerJoin.add(join);
        return getSelf();
    }
    
    public T OUTER_JOIN(String... joins) {
        sql().outerJoin.addAll(Arrays.asList(joins));
        return getSelf();
    }
    
    public T WHERE(String conditions) {
        sql().where.add(conditions);
        sql().lastList = sql().where;
        return getSelf();
    }
    
    public T OR() {
        sql().lastList.add(SQLStatement.OR);
        return getSelf();
    }
    
    public T AND() {
        sql().lastList.add(SQLStatement.AND);
        return getSelf();
    }
    
    public T GROUP_BY(String columns) {
        sql().groupBy.add(columns);
        return getSelf();
    }
    
    public T GROUP_BY(String... columns) {
        sql().groupBy.addAll(Arrays.asList(columns));
        return getSelf();
    }
    
    public T HAVING(String conditions) {
        sql().having.add(conditions);
        sql().lastList = sql().having;
        return getSelf();
    }
    
    public T HAVING(String... conditions) {
        sql().having.addAll(Arrays.asList(conditions));
        sql().lastList = sql().having;
        return getSelf();
    }
    
    public T ORDER_BY(String columns) {
        sql().orderBy.add(columns);
        return getSelf();
    }
    
    public T ORDER_BY(String... columns) {
        sql().orderBy.addAll(Arrays.asList(columns));
        return getSelf();
    }
    
    protected SQLStatement sql() {
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
