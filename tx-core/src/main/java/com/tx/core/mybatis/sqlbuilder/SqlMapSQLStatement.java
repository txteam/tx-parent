/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月4日
 * <修改描述:>
 */
package com.tx.core.mybatis.sqlbuilder;

import java.util.Arrays;
import java.util.List;

import com.tx.core.jdbc.sqlbuilder.SQLStatement;
import com.tx.core.jdbc.sqlbuilder.SafeAppendable;

/**
 * SqlMapStatement
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlMapSQLStatement extends SQLStatement {
    
    //public static final String SQL_MAP_AND = "</trim>) \nAND (<trim prefixOverrides=\"AND | OR\">";
    
    //public static final String SQL_MAP_OR = "</trim>) \nOR (<trim prefixOverrides=\"AND | OR\">";
    
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
            //String last = "________";
            for (int i = 0, n = parts.size(); i < n; i++) {
                String part = parts.get(i);
                if (i > 0
                //&& !part.equals(SQL_MAP_AND) && !part.equals(SQL_MAP_OR) && !last.equals(SQL_MAP_AND) && !last.equals(SQL_MAP_OR)
                ) {
                    builder.append(conjunction);
                }
                builder.append(part);
                //last = part;
            }
            builder.append(close);
        }
    }
    
    /**
     * @param builder
     * @return
     */
    @Override
    protected String deleteSQL(SafeAppendable builder) {
        sqlClause(builder, "DELETE FROM", tables, "", "", "");
        sqlClause(builder,
                "WHERE",
                where,
                "(<trim prefixOverrides=\"AND | OR\">",
                "</trim>)",
                " ");
        return builder.toString();
    }
    
    /**
     * @param builder
     * @return
     */
    @Override
    protected String updateSQL(SafeAppendable builder) {
        sqlClause(builder, "UPDATE", tables, "", "", "");
        joins(builder);
        sqlClause(builder,
                "SET",
                sets,
                "<trim suffixOverrides=\",\">",
                "</trim>",
                " ");
        sqlClause(builder,
                "WHERE",
                where,
                "(<trim prefixOverrides=\"AND | OR\">",
                "</trim>)",
                " ");
        return builder.toString();
    }
    
    /**
     * @param builder
     * @return
     */
    @Override
    protected String findSQL(SafeAppendable builder) {
        if (distinct) {
            sqlClause(builder, "SELECT DISTINCT", select, "", "", ", ");
        } else {
            sqlClause(builder, "SELECT", select, "", "", ", ");
        }
        sqlClause(builder, "FROM", tables, "", "", ", ");
        joins(builder);
        sqlClause(builder,
                "WHERE",
                where,
                "(<trim prefixOverrides=\"AND | OR\">",
                "</trim>)",
                " ");
        sqlClause(builder, "GROUP BY", groupBy, "", "", ", ");
        sqlClause(builder, "HAVING", having, "(", ")", " AND ");
        return builder.toString();
    }
    
    /**
     * @param builder
     * @return
     */
    @Override
    protected String countSQL(SafeAppendable builder) {
        sqlClause(builder, "SELECT", Arrays.asList("COUNT(1)"), "", "", ", ");
        sqlClause(builder, "FROM", tables, "", "", ", ");
        joins(builder);
        //        sqlClause(builder,
        //                "",
        //                where,
        //                "(<trim prefix=\"WHERE\" prefixOverrides=\"AND | OR\">",
        //                "</trim>)",
        //                " ");
        sqlClause(builder,
                "",
                where,
                "<trim prefix=\"WHERE\" prefixOverrides=\"AND | OR\">",
                "</trim>",
                " ");
        
        sqlClause(builder, "GROUP BY", groupBy, "", "", ", ");
        sqlClause(builder, "HAVING", having, "(", ")", " AND ");
        return builder.toString();
    }
    
    /**
     * @param builder
     * @return
     */
    @Override
    protected String querySQL(SafeAppendable builder) {
        if (distinct) {
            sqlClause(builder, "SELECT DISTINCT", select, "", "", ", ");
        } else {
            sqlClause(builder, "SELECT", select, "", "", ", ");
        }
        sqlClause(builder, "FROM", tables, "", "", ", ");
        joins(builder);
        //        sqlClause(builder,
        //                "",
        //                where,
        //                "(<trim prefix=\"WHERE\" prefixOverrides=\"AND | OR\">",
        //                "</trim>)",
        //                " ");
        sqlClause(builder,
                "",
                where,
                "<trim prefix=\"WHERE\" prefixOverrides=\"AND | OR\">",
                "</trim>",
                " ");
        sqlClause(builder, "GROUP BY", groupBy, "", "", ", ");
        sqlClause(builder, "HAVING", having, "(", ")", " AND ");
        sqlClause(builder,
                "",
                orderBy,
                "<choose><when test=\"@com.tx.core.util.OgnlUtils@isNotEmpty(orderSql)\">ORDER BY ${orderSql}</when><otherwise>ORDER BY ",
                "</otherwise></choose>",
                ", ");
        return builder.toString();
    }
    
    /**
     * @param builder
     * @return
     */
    @Override
    protected String selectSQL(SafeAppendable builder) {
        return querySQL(builder);
    }
}
