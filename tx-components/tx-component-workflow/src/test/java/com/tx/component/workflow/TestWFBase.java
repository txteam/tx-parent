/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-18
 * <修改描述:>
 */
package com.tx.component.workflow;

import javax.sql.DataSource;

import org.activiti.engine.test.ActivitiRule;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-18]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestWFBase{
    
    //@Rule
    //public ActivitiRule activitiRule = new ActivitiRule();
    
    /** 设置jndi */
    @BeforeClass
    public static void setUp() {
        TestWFBase.bindDsToJNDI();
    }
    
    /**
     *<功能简述>
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
   private static DataSource getLosDataSource() {
       DriverManagerDataSource dmd = new DriverManagerDataSource(
               "jdbc:mysql://192.168.1.1:3306/lms?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "lms", "zzxx1122");
       dmd.setDriverClassName("com.mysql.jdbc.Driver");
       return dmd;
   }
   
}
