/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-5
 * <修改描述:>
 */
package com.tx.core.mybatis.interceptor;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.SimpleStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;
import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <数据库分页容器处理器>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }),
        @Signature(type = StatementHandler.class, method = "parameterize", args = { Statement.class }) })
public class PagedDiclectStatementHandlerInterceptor implements Interceptor {
    
    private Logger logger = LoggerFactory.getLogger(PagedDiclectStatementHandlerInterceptor.class);
    
    private Dialect dialect;
    
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
        } else if ("parameterize".equals(m.getName())) {
            return parameterize(invocation);
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
        if (!(invocation.getTarget() instanceof RoutingStatementHandler)) {
            return invocation.proceed();
        }
        
        //提取statement
        RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
        
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);
        
        
        StatementHandler statement = (StatementHandler) metaStatementHandler.getValue("delegate");
        //如果不为两种statement则不继续进行处理
        if (!(statement instanceof SimpleStatementHandler)
                && !(statement instanceof PreparedStatementHandler)) {
            return invocation.proceed();
        }
        
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        //根据rowBounds判断是否需要进行物理分页
        if (rowBounds == null
                || rowBounds.equals(RowBounds.DEFAULT)
                || (rowBounds.getOffset() <= RowBounds.NO_ROW_OFFSET && rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT)) {
            return invocation.proceed();
        }
        
        //进行处理
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        String limitSql = dialect.getLimitString(sql,
                rowBounds.getOffset(),
                rowBounds.getLimit());
        
        if (statement instanceof SimpleStatementHandler) {
            limitSql.replaceAll("rownum <= ?", "rownum <= " + rowBounds.getLimit());
            limitSql.replaceAll("rownum_ > ?", "rownum_ > " + rowBounds.getOffset());
        }
        
        //如果为PreparedStatementHandler则无需替换即可
        metaStatementHandler.setValue("delegate.boundSql.sql",limitSql);
        
        if (logger.isDebugEnabled()) {
            logger.debug("生成分页SQL : " + boundSql.getSql());
        }
        
        return invocation.proceed();
    }
    
    /**
      * 设置分页参数
      * <功能详细描述>
      * @param invocation
      * @return
      * @throws Throwable [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Object parameterize(Invocation invocation) throws Throwable {
        //先执行系统默认的参数设置
        Object returnObj = invocation.proceed();
        
        
        //提取statement
        RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(routingStatementHandler);
        
        
        StatementHandler statementHandler = (StatementHandler) metaStatementHandler.getValue("delegate");
        //如果不为两种statement则不继续进行处理
        if (!(statementHandler instanceof PreparedStatementHandler)) {
            return returnObj;
        }
        
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        //根据rowBounds判断是否需要进行物理分页
        if (rowBounds == null
                || rowBounds.equals(RowBounds.DEFAULT)
                || (rowBounds.getOffset() <= RowBounds.NO_ROW_OFFSET && rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT)) {
            return returnObj;
        }
        
        //提取参数设置statement
        Statement statement = (Statement) invocation.getArgs()[0]; 
        if (!(statement instanceof PreparedStatement)) {  
            //如果对应statement不为PreparedStatement则直接返回
            return returnObj;
        }
        
        //设置分页的参数
        PreparedStatement ps = (PreparedStatement) statement;
        int parameterSize = statementHandler.getBoundSql().getParameterMappings().size();
        if(rowBounds.getOffset() > RowBounds.NO_ROW_OFFSET 
                || rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT){
            ps.setInt(parameterSize + 1, rowBounds.getLimit());
            parameterSize++;
            if(rowBounds.getOffset() > RowBounds.NO_ROW_OFFSET){
                ps.setInt(parameterSize + 1, rowBounds.getOffset());
            }
        }
        
        //替换rowBounds
        metaStatementHandler.setValue("delegate.rowBounds",
                RowBounds.DEFAULT);
        
        return returnObj;
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
}
