/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-5
 * <修改描述:>
 */
package com.tx.core.mybatis.interceptor;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.util.MetaObjectUtils;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * 数据库分页容器处理器
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = {
        Connection.class, Integer.class }) })
public class PagedDialectStatementHandlerInterceptor implements Interceptor {
    
    private Logger logger = LoggerFactory.getLogger(PagedDialectStatementHandlerInterceptor.class);
    
    private Dialect dialect;
    
    private DataSourceTypeEnum dataSourceType;
    
    /**
     * 物理分页插件拦截
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object intercept(Invocation invocation) throws Throwable {
        Method m = invocation.getMethod();
        if ("prepare".equals(m.getName())) {
            return prepare(invocation);
        }
        return invocation.proceed();
    }
    
    /**
     * @param target
     * @return
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    
    /**
     * @param properties
     */
    public void setProperties(Properties properties) {
    }
    
    /**
      * 拦截prepare修改分页
      * <功能详细描述>
      * @param invocation
      * @return
      * @throws Throwable [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Object prepare(Invocation invocation) throws Throwable {
        if (!(invocation.getTarget() instanceof RoutingStatementHandler)
                || dialect == null) {
            return invocation.proceed();
        }
        
        //提取statement
        RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
        
        MetaObject metaStatementHandler = MetaObjectUtils.forObject(statementHandler);
        //提取statement
        StatementHandler statement = (StatementHandler) metaStatementHandler.getValue("delegate");
        //获取rowBounds
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        //获取configuration
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        
        //如果不为PreparedStatementHandler则不继续进行处理
        //不考虑simpleStatementHandle的情况
        //如果为需要分页的地方认为都应该是PreparedStatementHandler
        if (!(statement instanceof PreparedStatementHandler)) {
            return invocation.proceed();
        }
        
        boolean isSupportsVariableLimit = dialect.supportsVariableLimit();//是否支持物理分页
        boolean isBindOnFirst = dialect.bindLimitParametersFirst();//是否绑定在前
        boolean isSupportsLimit = dialect.supportsLimit();//是否支持limit
        boolean isSupportsLimitOffset = dialect.supportsLimitOffset();//是否支持offset
        boolean isUseMaxForLimit = dialect.useMaxForLimit();
        //根据rowBounds判断是否需要进行物理分页
        //是否指定了分页条数
        if (rowBounds == null
                || rowBounds.equals(RowBounds.DEFAULT)
                || (rowBounds.getOffset() <= RowBounds.NO_ROW_OFFSET && rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT)
                || !isSupportsVariableLimit
                || (!isSupportsLimit && !isSupportsLimitOffset)) {
            //如果不需要分页
            //或者容器不支持物理分页!dialect.supportsVariableLimit()
            return invocation.proceed();
        }
        
        //如果需要分页
        //进行处理
        BoundSql boundSql = statementHandler.getBoundSql();
        List<ParameterMapping> newParameterMappingList = new ArrayList<ParameterMapping>(
                boundSql.getParameterMappings());
        boundSql.setAdditionalParameter("_offset",
                Integer.valueOf(rowBounds.getOffset()));
        boundSql.setAdditionalParameter("_limit",
                isUseMaxForLimit ? Integer.valueOf(rowBounds.getLimit())
                        : Integer.valueOf(rowBounds.getLimit()
                                - rowBounds.getOffset()));
        
        String sql = boundSql.getSql();
        //利用hibernate方言类生成新的分页语句
        String limitSql = dialect.getLimitString(sql,
                rowBounds.getOffset(),
                rowBounds.getLimit());
        //在调试模式下打印分页sql
        if (logger.isDebugEnabled()) {
            logger.debug("生成分页SQL : " + boundSql.getSql());
        }
        
        //如果支持isSupportsLimitOffset并且当前需要偏移值
        boolean isNeedSetOffset = isSupportsLimitOffset
                && (rowBounds.getOffset() > 0 || dialect.forceLimitUsage());
        
        //构建设置的入参
        //如果不支持物理分页
        //设置参数
        if (isBindOnFirst) {
            if (dialect.bindLimitParametersInReverseOrder()) {
                //true if the correct order is limit, offset
                if (isSupportsLimit) {
                    newParameterMappingList.add(0,
                            createLimitParameterMapping(configuration));
                }
                if (isNeedSetOffset) {
                    newParameterMappingList.add(1,
                            createOffsetParameterMapping(configuration));
                }
            } else {
                if (isNeedSetOffset) {
                    newParameterMappingList.add(0,
                            createOffsetParameterMapping(configuration));
                }
                if (isSupportsLimit) {
                    newParameterMappingList.add(1,
                            createLimitParameterMapping(configuration));
                }
            }
        } else {
            if (dialect.bindLimitParametersInReverseOrder()) {
                //true if the correct order is limit, offset
                if (isSupportsLimit) {
                    newParameterMappingList.add(createLimitParameterMapping(configuration));
                }
                if (isNeedSetOffset) {
                    newParameterMappingList.add(createOffsetParameterMapping(configuration));
                }
            } else {
                if (isNeedSetOffset) {
                    newParameterMappingList.add(createOffsetParameterMapping(configuration));
                }
                if (isSupportsLimit) {
                    newParameterMappingList.add(createLimitParameterMapping(configuration));
                }
            }
        }
        
        //将sql以及rowBounds替换为不需要再进行多余处理的形式
        metaStatementHandler.setValue("delegate.boundSql.parameterMappings",
                newParameterMappingList);
        metaStatementHandler.setValue("delegate.boundSql.sql", limitSql);
        metaStatementHandler.setValue("delegate.rowBounds.offset",
                RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit",
                RowBounds.NO_ROW_LIMIT);
        
        return invocation.proceed();
    }
    
    /**
      * 创建offsetParameter实体
      * <功能详细描述>
      * @param configuration
      * @return [参数说明]
      * 
      * @return ParameterMapping [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private ParameterMapping createOffsetParameterMapping(
            Configuration configuration) {
        ParameterMapping offsetParameterMapping = (new ParameterMapping.Builder(
                configuration, "_offset", Integer.class)).build();
        return offsetParameterMapping;
    }
    
    /**
      * 创建limitParameter实体
      *<功能详细描述>
      * @param configuration
      * @return [参数说明]
      * 
      * @return ParameterMapping [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private ParameterMapping createLimitParameterMapping(
            Configuration configuration) {
        ParameterMapping limitParameterMapping = (new ParameterMapping.Builder(
                configuration, "_limit", Integer.class)).build();
        return limitParameterMapping;
    }
    
    /**
     * @return 返回 dialect
     */
    public Dialect getDialect() {
        return dialect;
    }
    
    /**
     * @param 对dialect进行赋值
     */
    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
        this.dialect = this.dataSourceType.getDialect();
    }
}
