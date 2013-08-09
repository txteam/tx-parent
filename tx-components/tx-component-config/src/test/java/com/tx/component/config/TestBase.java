/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.component.config;

import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestBase {
    
    /**
      *<功能简述>
      *1789
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void bindDsToJNDI() {
        try {
            SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            DataSource losDs = getLosDataSource();
            builder.bind("java:comp/env/los_db", losDs);
            builder.activate();
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return DataSource [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static DataSource getLosDataSource() {
        DriverManagerDataSource dmd = new DriverManagerDataSource(
                "jdbc:oracle:thin:@172.16.18.10:1521:bdlms", "zlk1113", "zlk1113");
        dmd.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        return dmd;
    }
    
}
