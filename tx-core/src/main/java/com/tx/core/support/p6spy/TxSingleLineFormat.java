/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年5月1日
 * <修改描述:>
 */
package com.tx.core.support.p6spy;

import java.util.regex.Pattern;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
  * 日志打印格式化<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年5月1日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TxSingleLineFormat implements MessageFormattingStrategy {
    
    private static Pattern lineBreakPattern = Pattern.compile("(\\r?\\n)+");
    
    private static Pattern spacPattern = Pattern.compile("\\s+");
    
    /**
     * Formats a log message for the logging module
     *
     * @param connectionId the id of the connection
     * @param now          the current ime expressing in milliseconds
     * @param elapsed      the time in milliseconds that the operation took to complete
     * @param category     the category of the operation
     * @param prepared     the SQL statement with all bind variables replaced with actual values
     * @param sql          the sql statement executed
     * @return the formatted log message
     */
    @Override
    public String formatMessage(final int connectionId, final String now, final long elapsed, final String category,
            final String prepared, final String sql) {
        String logText = now + "|" + elapsed + "|" + category + "|connection " + connectionId + "|";
        if ("statement".equals(category)) {
            logText = logText + trimSql(prepared) + "\n\t|\t" + trimSql(sql);
        } else {
            logText = logText + trimSql(prepared) + "|" + trimSql(sql);
        }
        return logText;
    }
    
    /**
     * 格式化Sql<br/>
     * <功能详细描述>
     * @param sql
     * @return [参数说明]
     *
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static String trimSql(String sql) {
        if (sql == null) {
            return sql;
        }
        sql = lineBreakPattern.matcher(sql).replaceAll(" ");
        sql = spacPattern.matcher(sql).replaceAll(" ");
        return sql;
    }
    
    public static void main(String[] args) {
        String test = "select * \n from \t\n test                        where tt          = '2324'";
        System.out.println(test);
        System.out.println(trimSql(test));
    }
}
