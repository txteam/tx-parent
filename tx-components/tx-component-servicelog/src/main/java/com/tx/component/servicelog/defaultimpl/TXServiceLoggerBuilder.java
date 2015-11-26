/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.defaultimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tx.component.servicelog.context.BaseServiceLoggerBuilder;
import com.tx.component.servicelog.context.ServiceLoggerSessionContext;
import com.tx.component.servicelog.context.logger.ServiceLogDecorate;
import com.tx.component.servicelog.context.logger.ServiceLogPersister;
import com.tx.component.servicelog.context.logger.ServiceLogQuerier;
import com.tx.component.servicelog.exception.UnsupportServiceLoggerTypeException;
import com.tx.component.servicelog.logger.TXServiceLog;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.jdbc.sqlsource.SqlSourceBuilder;
import com.tx.core.paged.model.PagedList;
import com.tx.core.util.UUIDUtils;

/**
 * 业务日志记录器的构建器在这里是利用SqlSource对业务日志记录器进行构建<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TXServiceLoggerBuilder extends BaseServiceLoggerBuilder implements InitializingBean {
    
    /** 日志 */
    private Logger logger = LoggerFactory.getLogger(TXServiceLoggerBuilder.class);
    
    /** sql建造者,如果为 null 则 new 一个出来 */
    protected SqlSourceBuilder sqlSourceBuilder;
    
    /** 事务管理器,如果为 null 则 new 一个出来 */
    protected PlatformTransactionManager txManager;
    
    /** jdbcTemplate,如果为 null 则 new 一个出来 */
    protected JdbcTemplate jdbcTemplate;
    
    /** 数据源类型,必须注入 */
    protected DataSourceTypeEnum dataSourceType;
    
    /** 数据源,必须注入 */
    protected DataSource dataSource;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        //        if (sqlSourceBuilder == null) {
        //            sqlSourceBuilder = new SqlSourceBuilder();
        //        }
    }
    
    /**
     * 
     * 初始化建造者<br />
     * 此方法是位了兼容上一个配置文件而写的.如果去掉上一个配置文件写法,则把此方法校验移动到afterPropertiesSet()里面
     *
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月26日]
     * @author rain
     */
    @Deprecated
    public void initBuilder() {
        AssertUtils.notNull(dataSource, "dataSource is null!");
        AssertUtils.notNull(dataSourceType, "dataSourceType is null!");
        
        if (sqlSourceBuilder == null) {
            sqlSourceBuilder = new SqlSourceBuilder();
        }
        if (txManager == null) {
            txManager = new DataSourceTransactionManager(dataSource);
        }
        
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
    }
    
    @Override
    public <T> boolean isSupport(Class<T> logObjectType) {
        try {
            // 检查能否生成 SqlSource
            sqlSourceBuilder.build(logObjectType, dataSourceType.getDialect());
        } catch (SILException e) {
            logger.warn(e.toString(), e);
            return false;
        }
        return ClassUtils.isAssignable(logObjectType, TXServiceLog.class);
    }
    
    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }
    
    /** @param 对 dataSource 进行赋值 */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        
    }
    
    /** @param 对 dataSourceType 进行赋值 */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
    
    /** @param 对 jdbcTemplate 进行赋值 */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /** @param 对 sqlSourceBuilder 进行赋值 */
    public void setSqlSourceBuilder(SqlSourceBuilder sqlSourceBuilder) {
        this.sqlSourceBuilder = sqlSourceBuilder;
    }
    
    /** @param 对 txManager 进行赋值 */
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
    
    @Override
    protected <T> ServiceLogDecorate<T> buildServiceLogDecorate(Class<T> srcObjType) {
        ServiceLogDecorate<T> serviceLogDecorate = new ServiceLogDecorate<T>() {
            
            @Override
            public Object decorate(T srcObj) {
                AssertUtils.notNull(srcObj, "srcObj is null");
                
                if (srcObj instanceof TXServiceLog) {
                    TXServiceLog other = (TXServiceLog) srcObj;
                    Object res = decorateServiceLog(other);
                    return res;
                } else {
                    throw new UnsupportServiceLoggerTypeException("srcObject:{} not support.", new Object[] { srcObj });
                }
            }
            
            /**
             * 装饰业务日志实例<br/>
             * 
             * @param logInstance
             *            
             * @return Object [返回类型说明]
             * @exception throws [异常类型] [异常说明]
             * @see [类、类#方法、类#成员]
             */
            private Object decorateServiceLog(TXServiceLog logInstance) {
                ServiceLoggerSessionContext context = ServiceLoggerSessionContext.getContext();
                
                logInstance.setId(UUIDUtils.generateUUID());
                logInstance.setClientIpAddress((String) context.getAttribute("clientIpAddress"));
                logInstance.setOperatorId((String) context.getAttribute("operatorId"));
                logInstance.setOrganizationId((String) context.getAttribute("organizationId"));
                logInstance.setVcid((String) context.getAttribute("vcid"));
                logInstance.setCreateDate(new Date());
                logInstance.setOperatorName((String) context.getAttribute("operatorName"));
                logInstance.setOperatorLoginName((String) context.getAttribute("operatorLoginName"));
                
                return logInstance;
            }
        };
        
        return serviceLogDecorate;
    }
    
    @Override
    protected ServiceLogPersister buildServiceLogPersister(Class<?> srcObjType) {
        final SqlSource<?> sqlSource = sqlSourceBuilder.build(srcObjType, dataSourceType.getDialect());
        
        ServiceLogPersister txLogPersister = new ServiceLogPersister() {
            
            @Override
            public void persist(Object logInstance) {
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setName("serviceLoggerTxName");
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus status = txManager.getTransaction(def);
                
                try {
                    jdbcTemplate.update(sqlSource.insertSql(), sqlSource.getInsertSetter(logInstance));
                } catch (DataAccessException e) {
                    txManager.rollback(status);
                    throw e;
                }
                txManager.commit(status);
            }
        };
        return txLogPersister;
    }
    
    @Override
    protected <T> ServiceLogQuerier<T> buildServiceLogQuerier(final Class<T> srcObjType) {
        final SqlSource<T> sqlSource = sqlSourceBuilder.build(srcObjType, dataSourceType.getDialect());
        ServiceLogQuerier<T> querier = new ServiceLogQuerier<T>() {
            
            @Override
            public T find(String id) {
                return jdbcTemplate.queryForObject(sqlSource.findSql(), sqlSource.getSelectRowMapper(), id);
            }
            
            @Override
            public PagedList<T> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize) {
                //查询总条数
                RowMapper<Integer> integerRowMapper = new SingleColumnRowMapper<Integer>(Integer.class);
                List<Integer> resCountList = jdbcTemplate.query(
                        sqlSource.countSql(params),
                        sqlSource.getQueryCondtionSetter(params),
                        integerRowMapper);
                int count = DataAccessUtils.singleResult(resCountList);
                
                PagedList<T> result = new PagedList<T>();
                result.setPageIndex(pageIndex);
                result.setPageSize(pageSize);
                result.setCount(count);
                
                /* 如果count得到的结果为0则不再继续查询具体的哪几条 */
                if (count == 0) {
                    return result;
                }
                int offset = pageSize * (pageIndex - 1);
                int limit = pageSize * pageIndex;
                limit = limit > count ? count : limit;
                
                //查询指定索引条数
                List<T> resList = jdbcTemplate.query(
                        sqlSource.queryPagedSql(params, pageIndex, pageSize),
                        sqlSource.getPagedQueryCondtionSetter(params, offset, limit),
                        sqlSource.getSelectRowMapper());
                result.setList(resList);
                
                return result;
            }
        };
        return querier;
    }
}
