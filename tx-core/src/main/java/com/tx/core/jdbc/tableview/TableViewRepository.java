/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年6月20日
 * <修改描述:>
 */
package com.tx.core.jdbc.tableview;

import java.util.List;
import java.util.Map;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.model.ParameterizedTypeReference;
import com.tx.core.paged.model.PagedList;

/**
 * 视图逻辑层继承基础类，目的在于降低View对象的编码量<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年6月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TableViewRepository<T> extends ParameterizedTypeReference<T>
        implements ApplicationContextAware, InitializingBean {
    
    private ApplicationContext applicationContext;
    
    /** 数据库句柄 */
    private JdbcTemplate jdbcTemplate;
    
    /** rowMapper */
    private RowMapper<T> rowMapper;
    
    /** 方言类 */
    private Dialect dialect;
    
    /** 是否初始化 */
    private boolean init = false;
    
    /** named级JdbcTemplate对象 */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    public TableViewRepository(JdbcTemplate jdbcTemplate, Dialect dialect,
            RowMapper<T> rowMapper) {
        super();
        this.dialect = dialect;
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.dialect = dialect;
        if (!this.init) {
            afterPropertiesSet();
        }
    }
    
    public TableViewRepository(Dialect dialect, RowMapper<T> rowMapper) {
        super();
        this.rowMapper = rowMapper;
        this.dialect = dialect;
        if (!this.init) {
            afterPropertiesSet();
        }
    }
    
    public TableViewRepository(Dialect dialect) {
        super();
        this.dialect = dialect;
        if (!this.init) {
            afterPropertiesSet();
        }
    }
    
    /** <默认构造函数> */
    public TableViewRepository() {
        super();
        if (!this.init) {
            afterPropertiesSet();
        }
    }
    
    /**
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() {
        if (rowMapper == null) {
            this.rowMapper = ParameterizedBeanPropertyRowMapper.newInstance((Class<T>) getRawType());
        }
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = this.applicationContext.getBean(JdbcTemplate.class);
        }
        if (this.dialect == null) {
            this.dialect = new MySQLDialect();
        }
        
        this.init = true;
        AssertUtils.notNull(this.jdbcTemplate, "jdbcTemplate is null.");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                this.jdbcTemplate);
    }
    
    public T findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        return null;
    }
    
    /**
      * 根据Sql查询对象<br/>
      * <功能详细描述>
      * @param sql
      * @param params
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public T findBySql(String sql, Object... params) {
        T resObj = this.jdbcTemplate.queryForObject(sql, this.rowMapper, params);
        return resObj;
    }
    
    /**
      * 根据NamedSql进行对象查询:
      *     例：select * from test where name=:name 
      * <功能详细描述>
      * @param sql
      * @param paramMap
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public T findByNamedSql(String sql, Map<String, Object> paramMap) {
        T resObj = this.namedParameterJdbcTemplate.queryForObject(sql,
                paramMap,
                this.rowMapper);
        return resObj;
    }
    
    public List<T> queryBySql(String sql, Object... params) {
        List<T> resList = this.jdbcTemplate.query(sql, rowMapper, params);
        return resList;
    }
    
    public List<T> queryByNamedSql(String sql, Map<String, Object> paramMap) {
        List<T> resList = this.namedParameterJdbcTemplate.query(sql, paramMap, rowMapper);
        return resList;
    }
    
    public PagedList<T> queryByPagedListBySql(String sql, 
            int pageIndex, int pageSize,Object... params) {
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        final boolean isSupportsVariableLimit = dialect.supportsVariableLimit();//是否支持物理分页
        final boolean isSupportsLimit = dialect.supportsLimit();//是否支持limit
        final boolean isSupportsLimitOffset = dialect.supportsLimitOffset();//是否支持offset
        
        //如果不支持物理分页，直接返回sql
        if (!isSupportsVariableLimit
                || (!isSupportsLimit && !isSupportsLimitOffset)) {
            
        } else {
            //如果支持
            String limitSql = dialect.getLimitString(sql, offset, limit);
        }
        return null;
    }
    
    public long countBySql(String sql, Object... params) {
        return 0;
    }
}
