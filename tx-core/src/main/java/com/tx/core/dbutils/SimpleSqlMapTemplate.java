/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-5
 * <修改描述:>
 */
package com.tx.core.dbutils;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.jdbc.util.SimpleSqlMapMapperUtils;

/**
 * 简单的基于sqlMap实现的Template
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleSqlMapTemplate implements InitializingBean {
    
    /** 数据源 */
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(this.dataSource, "dataSource is null");
        
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }
    
    /**
      * 插入单条数据对象<br/>
      *<功能详细描述>
      * @param type
      * @param obj [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> void insert(Class<T> type, T obj) {
        SimpleSqlMapMapper sqlMapMapper = SimpleSqlMapMapperUtils.buildSqlMapMapper(type);
        
        this.jdbcTemplate.update(sqlMapMapper.insertSql(),
                sqlMapMapper.getInsertSetter(obj));
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    protected void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
}
