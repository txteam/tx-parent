/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.core.util;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * sql工具类
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-4-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlUtils {
    
    /**
      * 对sql进行处理放置sql注入
      * <功能详细描述>
      * @param srcSql
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String escapeSql(String srcSql){
        String newSql = StringEscapeUtils.escapeSql(srcSql);
        return newSql;
    }
    
    /**
      * 创建%在开头的字符串
      *<功能详细描述>
      * @param srcString
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String createLikeStringAtStart(String srcString) {
        String newString = "%" + StringEscapeUtils.escapeSql(srcString);
        return newString;
    }
    
    /**
     * 创建%在末尾的字符串
     *<功能详细描述>
     * @param srcString
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static String createLikeStringAtEnd(String srcString) {
        String newString = StringEscapeUtils.escapeSql(srcString) + "%";
        return newString;
    }
    
    /**
      * 创建%在前后均有的字符串
      * <功能详细描述>
      * @param srcString
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String createLikeString(String srcString) {
        String newString = "%" + StringEscapeUtils.escapeSql(srcString) + "%";
        return newString;
    }
}
