/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月19日
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlbuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql语句构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SQLStatement {
    
    public static final String AND = ") \nAND (";
    
    public static final String OR = ") \nOR (";
    
    protected boolean distinct;
    
    protected SQLStatementTypeEnum statementType;
    
    protected List<String> sets = new ArrayList<String>();
    
    protected List<String> select = new ArrayList<String>();
    
    protected List<String> tables = new ArrayList<String>();
    
    protected List<String> join = new ArrayList<String>();
    
    protected List<String> innerJoin = new ArrayList<String>();
    
    protected List<String> outerJoin = new ArrayList<String>();
    
    protected List<String> leftOuterJoin = new ArrayList<String>();
    
    protected List<String> rightOuterJoin = new ArrayList<String>();
    
    protected List<String> where = new ArrayList<String>();
    
    protected List<String> having = new ArrayList<String>();
    
    protected List<String> groupBy = new ArrayList<String>();
    
    protected List<String> orderBy = new ArrayList<String>();
    
    protected List<String> lastList = new ArrayList<String>();
    
    protected List<String> columns = new ArrayList<String>();
    
    protected List<String> values = new ArrayList<String>();
    
    /** <默认构造函数> */
    public SQLStatement() {
        super();
    }
    
    /**
     * sql拼接<br/>
     * <功能详细描述>
     * @param builder
     * @param keyword
     * @param parts
     * @param open
     * @param close
     * @param conjunction [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void sqlClause(SafeAppendable builder, String keyword,
            List<String> parts, String open, String close, String conjunction) {
        if (!parts.isEmpty()) {
            if (!builder.isEmpty()) {
                builder.append("\n");
            }
            builder.append(keyword);
            builder.append(" ");
            builder.append(open);
            String last = "________";
            for (int i = 0, n = parts.size(); i < n; i++) {
                String part = parts.get(i);
                if (i > 0 && !part.equals(AND) && !part.equals(OR)
                        && !last.equals(AND) && !last.equals(OR)) {
                    builder.append(conjunction);
                }
                builder.append(part);
                last = part;
            }
            builder.append(close);
        }
    }
    
    /**
     * findSql<br/>
     * <功能详细描述>
     * @param builder
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String findSQL(SafeAppendable builder) {
        return selectSQL(builder);
    }
    
    /**
     * querySQL
     * <功能详细描述>
     * @param builder
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String querySQL(SafeAppendable builder) {
        return selectSQL(builder);
    }
    
    /**
     * countSQL
     * <功能详细描述>
     * @param builder
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String countSQL(SafeAppendable builder) {
        return selectSQL(builder);
    }
    
    /**
     * 构建查询Sql
     * <功能详细描述>
     * @param builder
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String selectSQL(SafeAppendable builder) {
        if (distinct) {
            sqlClause(builder, "SELECT DISTINCT", select, "", "", ", ");
        } else {
            sqlClause(builder, "SELECT", select, "", "", ", ");
        }
        
        sqlClause(builder, "FROM", tables, "", "", ", ");
        joins(builder);
        sqlClause(builder, "WHERE", where, "(", ")", " AND ");
        sqlClause(builder, "GROUP BY", groupBy, "", "", ", ");
        sqlClause(builder, "HAVING", having, "(", ")", " AND ");
        sqlClause(builder, "ORDER BY", orderBy, "", "", ", ");
        return builder.toString();
    }
    
    /**
     * 插入Sql
     * <功能详细描述>
     * @param builder
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String insertSQL(SafeAppendable builder) {
        sqlClause(builder, "INSERT INTO", tables, "", "", "");
        sqlClause(builder, "", columns, "(", ")", ", ");
        sqlClause(builder, "VALUES", values, "(", ")", ", ");
        return builder.toString();
    }
    
    /**
     * 删除Sql
     * <功能详细描述>
     * @param builder
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String deleteSQL(SafeAppendable builder) {
        sqlClause(builder, "DELETE FROM", tables, "", "", "");
        sqlClause(builder, "WHERE", where, "(", ")", " AND ");
        return builder.toString();
    }
    
    /**
     * 更新Sql
     * <功能详细描述>
     * @param builder
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String updateSQL(SafeAppendable builder) {
        
        sqlClause(builder, "UPDATE", tables, "", "", "");
        joins(builder);
        sqlClause(builder, "SET", sets, "", "", ", ");
        sqlClause(builder, "WHERE", where, "(", ")", " AND ");
        return builder.toString();
    }
    
    /**
     * 关联表<br/>
     * <功能详细描述>
     * @param builder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void joins(SafeAppendable builder) {
        sqlClause(builder, "JOIN", join, "", "", "\nJOIN ");
        sqlClause(builder, "INNER JOIN", innerJoin, "", "", "\nINNER JOIN ");
        sqlClause(builder, "OUTER JOIN", outerJoin, "", "", "\nOUTER JOIN ");
        sqlClause(builder,
                "LEFT OUTER JOIN",
                leftOuterJoin,
                "",
                "",
                "\nLEFT OUTER JOIN ");
        sqlClause(builder,
                "RIGHT OUTER JOIN",
                rightOuterJoin,
                "",
                "",
                "\nRIGHT OUTER JOIN ");
    }
    
    /**
      * 获取对应的sql
      * <功能详细描述>
      * @param a
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public final String sql(Appendable a) {
        SafeAppendable builder = new SafeAppendable(a);
        if (statementType == null) {
            return null;
        }
        
        String answer;
        
        switch (statementType) {
            case DELETE:
                answer = deleteSQL(builder);
                break;
            
            case INSERT:
                answer = insertSQL(builder);
                break;
            
            case SELECT:
                answer = selectSQL(builder);
                break;
            
            case UPDATE:
                answer = updateSQL(builder);
                break;
            
            case FIND:
                answer = findSQL(builder);
                break;
            
            case QUERY:
                answer = querySQL(builder);
                break;
            
            case COUNT:
                answer = countSQL(builder);
                break;
            
            default:
                answer = null;
        }
        
        return answer;
    }

    /**
     * @return 返回 distinct
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * @return 返回 statementType
     */
    public SQLStatementTypeEnum getStatementType() {
        return statementType;
    }

    /**
     * @return 返回 sets
     */
    public List<String> getSets() {
        return sets;
    }

    /**
     * @return 返回 select
     */
    public List<String> getSelect() {
        return select;
    }

    /**
     * @return 返回 tables
     */
    public List<String> getTables() {
        return tables;
    }

    /**
     * @return 返回 join
     */
    public List<String> getJoin() {
        return join;
    }

    /**
     * @return 返回 innerJoin
     */
    public List<String> getInnerJoin() {
        return innerJoin;
    }

    /**
     * @return 返回 outerJoin
     */
    public List<String> getOuterJoin() {
        return outerJoin;
    }

    /**
     * @return 返回 leftOuterJoin
     */
    public List<String> getLeftOuterJoin() {
        return leftOuterJoin;
    }

    /**
     * @return 返回 rightOuterJoin
     */
    public List<String> getRightOuterJoin() {
        return rightOuterJoin;
    }

    /**
     * @return 返回 where
     */
    public List<String> getWhere() {
        return where;
    }

    /**
     * @return 返回 having
     */
    public List<String> getHaving() {
        return having;
    }

    /**
     * @return 返回 groupBy
     */
    public List<String> getGroupBy() {
        return groupBy;
    }

    /**
     * @return 返回 orderBy
     */
    public List<String> getOrderBy() {
        return orderBy;
    }

    /**
     * @return 返回 lastList
     */
    public List<String> getLastList() {
        return lastList;
    }

    /**
     * @return 返回 columns
     */
    public List<String> getColumns() {
        return columns;
    }

    /**
     * @return 返回 values
     */
    public List<String> getValues() {
        return values;
    }
}
