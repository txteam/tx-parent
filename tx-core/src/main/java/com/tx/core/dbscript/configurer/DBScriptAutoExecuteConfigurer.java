/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-19
 * <修改描述:>
 */
package com.tx.core.dbscript.configurer;

import javax.activation.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;


 /**
  * 数据脚本自动执行配置器<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-8-19]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DBScriptAutoExecuteConfigurer {
    
    private String dbScriptLocation;
    
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    
    
}
