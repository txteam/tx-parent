/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

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
    
//    public static List<String> splitSql(String srcSql){
//        StringUtils.splitByWholeSeparator(str, separator, max)
//    }
}
