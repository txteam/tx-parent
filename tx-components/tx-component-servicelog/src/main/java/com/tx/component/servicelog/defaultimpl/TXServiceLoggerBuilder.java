/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.defaultimpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.tx.component.servicelog.context.BaseServiceLoggerBuilder;
import com.tx.component.servicelog.context.ServiceLoggerSessionContext;
import com.tx.component.servicelog.exception.UnsupportServiceLoggerTypeException;
import com.tx.component.servicelog.logger.ServiceLogDecorate;
import com.tx.component.servicelog.logger.ServiceLogPersister;
import com.tx.component.servicelog.logger.ServiceLogQuerier;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.jdbc.sqlsource.SqlSourceBuilder;
import com.tx.core.paged.model.PagedList;
import com.tx.core.util.UUIDUtils;

/**
 * 业务日志记录器的构建器在这里是利用SqlSource对业务日志记录器进行构建<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TXServiceLoggerBuilder extends BaseServiceLoggerBuilder {
    
    private Logger logger = LoggerFactory.getLogger(TXServiceLoggerBuilder.class);
    
    private static SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder();
    
    /**
     * @param srcObjType
     * @return
     */
    @Override
    protected boolean isSupport(Class<?> srcObjType,
            DataSourceTypeEnum dataSourceType) {
        try {
            @SuppressWarnings("unused")
            SqlSource<?> sqlSource = sqlSourceBuilder.build(srcObjType,
                    dataSourceType.getDialect());
        } catch (SILException e) {
            logger.warn(e.toString(), e);
            return false;
        }
        return true;
    }
    
    /**
     * @param srcObjType
     * @param dataSourceType
     * @param dataSource
     * @return
     */
    @Override
    protected ServiceLogPersister buildServiceLogPersister(Class<?> srcObjType,
            DataSourceTypeEnum dataSourceType, final JdbcTemplate jdbcTemplate) {
        final SqlSource<?> sqlSource = sqlSourceBuilder.build(srcObjType,
                dataSourceType.getDialect());
        
        ServiceLogPersister txLogPersister = new ServiceLogPersister() {
            @Override
            public void persist(Object logInstance) {
                jdbcTemplate.update(sqlSource.insertSql(),
                        sqlSource.getInsertSetter(logInstance));
            }
        };
        return txLogPersister;
    }
    
    /**
     * @param srcObjType
     * @return
     */
    @Override
    protected <T> ServiceLogQuerier<T> buildServiceLogQuerier(
            Class<T> srcObjType, DataSourceTypeEnum dataSourceType,
            final JdbcTemplate jdbcTemplate) {
        final SqlSource<T> sqlSource = sqlSourceBuilder.build(srcObjType,
                dataSourceType.getDialect());
        
        ServiceLogQuerier<T> querier = new ServiceLogQuerier<T>() {
            /**
             * @param minCreateDate
             * @param maxCreateDate
             * @return
             */
            @Override
            public PagedList<T> queryPagedList(Map<String, Object> params,
                    int pageIndex, int pageSize) {
                //查询总条数
                RowMapper<Integer> integerRowMapper = new SingleColumnRowMapper<Integer>(
                        Integer.class);
                List<Integer> resCountList = jdbcTemplate.query(sqlSource.countSql(params),
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
                
                //查询指定索引条数
                List<T> resList = jdbcTemplate.query(sqlSource.queryPagedSql(params,
                        pageIndex,
                        pageSize),
                        sqlSource.getPagedQueryCondtionSetter(params,
                                pageIndex,
                                pageSize),
                        sqlSource.getSelectRowMapper());
                result.setList(resList);
                
                return result;
            }
        };
        return querier;
    }
    
    /**
     * @param srcObjType
     * @return
     */
    @Override
    protected <T> ServiceLogDecorate<T> buildServiceLogDecorate(Class<T> srcObjType) {
        
        ServiceLogDecorate<T> serviceLogDecorate = new ServiceLogDecorate<T>() {
            
            /**
             * @param srcObj
             * @return
             */
            @Override
            public Object decorate(T srcObj) {
                AssertUtils.notNull(srcObj, "srcObj is null");
                
                if (srcObj instanceof TXServiceLog) {
                    TXServiceLog other = (TXServiceLog) srcObj;
                    Object res = decorateServiceLog(other);
                    return res;
                } else {
                    throw new UnsupportServiceLoggerTypeException(
                            "srcObject:{} not support.",
                            new Object[] { srcObj });
                }
            }
            
            /**
              * 装饰业务日志实例<br/>
              *<功能详细描述>
              * @param logInstance
              * @return [参数说明]
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
                return logInstance;
            }
        };
        
        return serviceLogDecorate;
    }
    
}
