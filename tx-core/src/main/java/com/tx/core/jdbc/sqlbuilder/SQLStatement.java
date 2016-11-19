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
    
    private static final String AND = ") \nAND (";
    
    private static final String OR = ") \nOR (";
    
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
     * 拼接Sql的构建器<br/>
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
    protected final void sqlClause(SafeAppendable builder, String append) {
        builder.append(append);
    }
    
    /**
      * 拼接Sql的构建器<br/>
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
    protected final void sqlClause(SafeAppendable builder, String keyword,
            List<String> parts, String open, String close, String conjunction) {
        sqlClause(builder, keyword, parts, open, close, conjunction, "", "");
    }
    
    /**
     * 拼接Sql的构建器<br/>
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
    protected final void sqlClause(SafeAppendable builder, String keyword,
            List<String> parts, String open, String close, String conjunction,
            String partopen, String partclose) {
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
                builder.append(partopen).append(part).append(partclose);
                last = part;
            }
            builder.append(close);
        }
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
        sqlClause(builder, "SET", sets, "", "", ", ");
        sqlClause(builder, "WHERE", where, "(", ")", " AND ");
        return builder.toString();
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
            
            default:
                answer = null;
        }
        
        return answer;
    }
}
