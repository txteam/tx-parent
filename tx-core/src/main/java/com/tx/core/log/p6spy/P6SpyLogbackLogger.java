/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-10-18
 * <修改描述:>
 */
package com.tx.core.log.p6spy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.P6Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * <在p6spy中利用logback统一打印日志，统一日志记录的位置>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-10-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class P6SpyLogbackLogger implements P6Logger {
    
    private static final Logger logger = LoggerFactory.getLogger(P6SpyLogbackLogger.class);
    private static  Set<Category> debugCategory = new HashSet<>();

   static  {
        debugCategory.add(Category.BATCH);
        debugCategory.add(Category.STATEMENT);
        debugCategory.add(Category.RESULTSET);
        debugCategory.add(Category.COMMIT);
        debugCategory.add(Category.ROLLBACK);
        debugCategory.add(Category.RESULT);
        debugCategory.add(Category.OUTAGE);
    }
    
    /**
     * @param category
     * @return
     */
    @Override
    public boolean isCategoryEnabled(Category category) {

        if (Category.ERROR.equals(category) ) {
            return logger.isErrorEnabled();
        } else if (Category.WARN.equals(category)) {
            return logger.isWarnEnabled();
        } else if (Category.DEBUG.equals(category) || debugCategory.contains(category)
                ) {
            return logger.isDebugEnabled();
        } else {
            return logger.isInfoEnabled();
        }
    }
    
    /**
     * 记录异常日志<br/>
     * @param e
     */
    @Override
    public void logException(Exception e) {
        logger.info(e.getMessage(), e);
    }
    
    /**
     * 记录文本日志<br/>
     * @param text
     */
    @Override
    public void logText(String text) {
        logger.info(text);
    }
    
    /**
     * @param connectionId
     * @param now
     * @param elapsed
     * @param category
     * @param prepared
     * @param sql
     */
    @Override
    public void logSQL(int connectionId, String now, long elapsed,
            Category category, String prepared, String sql) {
        if (logger.isInfoEnabled()) {
            if (!Category.RESULTSET.equals(category)) {
                if (Category.STATEMENT.equals(category)) {
                    logger.info("statement: " + trim(prepared));
                    logger.info(trim(sql));
                } else {
                    logger.info(category.getName() + ": " + trim(prepared));
                    logger.info(trim(sql));
                }
            } else if (logger.isDebugEnabled()) {
                logger.debug("resultset: " + trim(sql));
            }
        }
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
    private static String trim(String sql) {
        if (sql == null) {
            return sql;
        }
        sql = sql.replaceAll("\\s+", " ");
        return sql;
    }
}
