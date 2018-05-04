/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

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
      * 格式化sql： 去掉sql中的多余空格，去掉sql中的注释内容<br/>
      * <功能详细描述>
      * @param sql
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String format(String sql) {
        AssertUtils.notEmpty(sql, "sql is empty.");
        
        sql = sql.replaceAll("\\/\\*.*?\\*\\/", " ");//去掉多行注释
        StringReader sr = new StringReader(sql);
        BufferedReader br = new BufferedReader(sr);
        //最终存储
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        try {
            String lineTemp = null;
            while ((lineTemp = br.readLine()) != null) {
                if (StringUtils.isBlank(lineTemp)) {
                    //如果为空行则跳过
                    continue;
                }
                String lineFormatResult = lineTemp.replaceAll("--.*", " ");//去掉行注释
                sb.append(lineFormatResult);
            }
        } catch (IOException e) {
            throw new SILException("append line exception.", e);
        }
        String resString = sb.toString().replaceAll("\\s{2,}", " ").trim();//去掉多余的空格
        return resString;
    }
    
//    /**
//      * 对sql进行处理放置sql注入
//      * <功能详细描述>
//      * @param srcSql
//      * @return [参数说明]
//      * 
//      * @return String [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static String escapeSql(String srcSql) {
//        String newSql = StringEscapeUtils.escapeSql(srcSql);
//        return newSql;
//    }
    
    /**
      * 将sqlContent依据';\s' '/\s'进行分割，分割为多个sql语句
      *     但字符中的特殊符号要允许输入
      *     服务于，读取sql文件后，逐句sql进行执行 
      *<功能详细描述>
      * @param srcSqlScript
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static List<String> splitSqlScript(String srcSqlScript) {
        if (StringUtils.isBlank(srcSqlScript)) {
            return new ArrayList<String>();
        }
        String[] res = srcSqlScript.split("[;/]\\s|[;/]$");
        List<String> resList = new ArrayList<String>(res.length);
        for (String sqlTemp : res) {
            if (!StringUtils.isBlank(sqlTemp)) {
                resList.add(sqlTemp);
            }
        }
        return resList;
    }
}
