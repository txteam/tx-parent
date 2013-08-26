/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-26
 * <修改描述:>
 */
package com.tx.core.jpa;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;


 /**
  * 类似jdbcTemplate的支持jpa的数据处理句柄
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-8-26]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class JPAJdbcTemplate {
    
    private JdbcTemplate jdbcTemplate;
    
    
    /** <默认构造函数> */
    public JPAJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /** <默认构造函数> */
    public JPAJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public void insert(Object obj){
        String insertSql = null;
        
        this.jdbcTemplate.update(insertSql, new PreparedStatementSetter(){

            /**
             * @param ps
             * @throws SQLException
             */
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                
            }
        });
    }
    
    public void delete(Object obj){
        
    }
    
    public <T> T find(Class<T> type,Object obj){
        //this.jdbcTemplate.query
        return null;
    }
    
    public <T> T query(Class<T> type,Object obj){
        return null;
    }
    
}
