/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle9iDialect;
import org.hibernate.dialect.function.SQLFunction;

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
    public static String escapeSql(String srcSql) {
        String newSql = StringEscapeUtils.escapeSql(srcSql);
        return newSql;
    }
    
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
    
    public static void main(String[] args) {
        /*
        String sqlContent = "create table \n (id_ " +
        		"varchar2(32));\n create index xxx;\n/ " +
        		"insert into (id) valuse ('absdsdf;dsfasd;asdf');\n " +
        		"insert into(id) values('test');";
        
        List<String> res = splitSqlScript(sqlContent);
        for (String temp : res) {
            System.out.print("   newSql:");
            System.out.println(temp);
        }
        
        MySQL5InnoDBDialect dia = new MySQL5InnoDBDialect();
        Oracle9iDialect or = new Oracle9iDialect();
        H2Dialect hr = new H2Dialect();
        SQLFunction sqlFun = dia.getFunctions().get("concat");
        List<String> arg = new ArrayList<String>();
        arg.add("abc");
        arg.add("?");
        arg.add("bcd");
        System.out.println(sqlFun.render(null, arg, null));
        System.out.println(or.getFunctions().get("concat").render(null, arg, null));
        System.out.println(hr.getFunctions().get("concat").render(null, arg, null));
        */
        String sqlContent = "select key from test";
        System.out.println(SqlUtils.escapeSql(sqlContent));
    }
}
