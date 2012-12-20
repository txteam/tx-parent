/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-4
 * <修改描述:>
 */
package com.tx.core.mybatis.support;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.PersistenceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.BatchExecutorException;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.tx.core.mybatis.model.BatchResult;
import com.tx.core.mybatis.model.Order;
import com.tx.core.paged.model.PagedList;

/**
 * <mybatis数据库查询类> <功能详细描述>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-11-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyBatisDaoSupport {
    
    private Logger logger = LoggerFactory.getLogger(MyBatisDaoSupport.class);
    
    /**
     * 默认在batch功能执行过程中批量持久的条数
     */
    private static final int defaultDoFlushSize = 100;
    
    private SqlSessionTemplate sqlSessionTemplate;
    
    /**
     * 查询实体对象<br/>
     * 1、当不需要参数时，parameter传入null即可<br/>
     * <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <T> T find(String statement, Object parameter) {
        if (parameter != null) {
            return this.sqlSessionTemplate.<T> selectOne(statement, parameter);
        }
        else {
            return this.sqlSessionTemplate.<T> selectOne(statement);
        }
    }
    
    /**
     * <查询实体对象数> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(String statement, Object parameter) {
        if (parameter != null) {
            return (Integer) this.sqlSessionTemplate.selectOne(statement,
                    parameter);
        }
        else {
            return (Integer) this.sqlSessionTemplate.selectOne(statement);
        }
    }
    
    /**
     * <查询列表对象> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @return [参数说明]
     * 
     * @return List<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <T> List<T> queryList(String statement, Object parameter) {
        if (parameter != null) {
            return this.sqlSessionTemplate.<T> selectList(statement, parameter);
        }
        else {
            return this.sqlSessionTemplate.<T> selectList(statement);
        }
    }
    
    /**
     * <查询列表对象> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @return [参数说明]
     * 
     * @return List<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <T> List<T> queryList(String statement,
            Map<String, Object> parameter, List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Order orderTemp : orders) {
                sb.append(orderTemp.toSqlString()).append(",");
            }
            if (sb.length() > 0) {
                String orderSql = sb.substring(0, sb.length() - 1);
                
                parameter.put("orderSql", orderSql);
            }
        }
        return this.<T> queryList(statement, parameter);
    }
    
    /**
     * <查询分页对象> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @param pageIndex
     * @param pageSize
     * @param orders
     * @return [参数说明]
     * 
     * @return PagedList<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <T> PagedList<T> queryPagedList(String statement,
            Map<String, Object> parameter, int pageIndex, int pageSize,
            List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Order orderTemp : orders) {
                sb.append(orderTemp.toSqlString()).append(",");
            }
            if (sb.length() > 0) {
                String orderSql = sb.substring(0, sb.length() - 1);
                
                parameter.put("orderSql", orderSql);
            }
        }
        return this.<T> queryPagedList(statement,
                parameter,
                pageIndex,
                pageSize);
    }
    
    /**
     * <查询分页对象> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @param pageIndex
     * @param pageSize
     * @param orders
     * @return [参数说明]
     * 
     * @return PagedList<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <T> PagedList<T> queryPagedList(String statement,
            Map<String, Object> parameter, int pageIndex, int pageSize,
            int count, List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Order orderTemp : orders) {
                sb.append(orderTemp.toSqlString()).append(",");
            }
            if (sb.length() > 0) {
                String orderSql = sb.substring(0, sb.length() - 1);
                
                parameter.put("orderSql", orderSql);
            }
        }
        return this.<T> queryPagedList(statement,
                parameter,
                pageIndex,
                pageSize,
                count);
    }
    
    /**
     * <查询分页对象> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <T> PagedList<T> queryPagedList(String statement, Object parameter,
            int pageIndex, int pageSize) {
        PagedList<T> result = new PagedList<T>();
        
        // 构建Count查询列表中数目
        String queryCountStatement = statement + "Count";
        int count = (Integer) this.sqlSessionTemplate.selectOne(queryCountStatement,
                parameter);
        result.setCount(count);
        if (count <= 0) {
            return result;
        }
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        List<T> list = this.sqlSessionTemplate.<T> selectList(statement,
                parameter,
                new RowBounds(offset, limit));
        result.setList(list);
        return result;
    }
    
    /**
     * <查询分页对象,传入count> <当count <= 0 或当前显示页已经是最后一页，则会调用对应count>
     * 
     * @param statement
     * @param parameter
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <T> PagedList<T> queryPagedList(String statement, Object parameter,
            int pageIndex, int pageSize, int count) {
        PagedList<T> result = new PagedList<T>();
        
        // 构建Count查询列表中数目
        if (count <= 0 || pageIndex * pageSize >= count) {
            String queryCountStatement = statement + "Count";
            count = (Integer) this.sqlSessionTemplate.selectOne(queryCountStatement,
                    parameter);
        }
        result.setCount(count);
        if (count <= 0) {
            return result;
        }
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        List<T> list = this.sqlSessionTemplate.<T> selectList(statement,
                parameter,
                new RowBounds(offset, limit));
        result.setList(list);
        return result;
    }
    
    /**
     * <查询分页对象,传入count> <当count <= 0 或当前显示页已经是最后一页，则会调用对应count>
     * 
     * @param statement
     * @param parameter
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <T> PagedList<T> querySkipQueryCountPagedList(String statement,
            Object parameter, int pageIndex, int pageSize, int count) {
        PagedList<T> result = new PagedList<T>();
        result.setCount(count);
        if (count <= 0) {
            return result;
        }
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        List<T> list = this.sqlSessionTemplate.selectList(statement,
                parameter,
                new RowBounds(offset, limit));
        result.setList(list);
        return result;
    }
    
    /**
     * <查询列表，并映射为map> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @param mapKey
     * @return [参数说明]
     * 
     * @return Map<?,?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <K, V> Map<K, V> queryToMap(String statement, Object parameter,
            String mapKey) {
        if (parameter != null) {
            return this.sqlSessionTemplate.<K, V> selectMap(statement,
                    parameter,
                    mapKey);
        }
        else {
            return this.sqlSessionTemplate.<K, V> selectMap(statement, mapKey);
        }
    }
    
    /**
     * <查询列表，并映射为map>
     * 
     * @param statement
     * @param parameter
     * @param mapKey
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return Map<?,?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <K, V> Map<K, V> queryToMapByPage(String statement,
            Object parameter, String mapKey, int pageIndex, int pageSize) {
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        
        return this.sqlSessionTemplate.<K, V> selectMap(statement,
                parameter,
                mapKey,
                new RowBounds(offset, limit));
    }
    
    /**
     * <查询> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @param handler
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void queryByResultHandler(String statement, Object parameter,
            ResultHandler handler) {
        if (parameter != null) {
            this.sqlSessionTemplate.select(statement, parameter, handler);
        }
        else {
            this.sqlSessionTemplate.select(statement, handler);
        }
    }
    
    /**
      * 插入前使用uuid为指定属性赋值，
      * 1、如果在sqlMap上指定了selectKey那最终会是selectKey生效
      * <功能详细描述>
      * @param statement
      * @param parameter
      * @param keyPropertyName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insertUseUUID(String statement, Object parameter,
            String keyPropertyName) {
        if (!StringUtils.isEmpty(keyPropertyName)) {
            //如果指定了keyProperty
            MetaObject metaObject = MetaObject.forObject(parameter);
            if (metaObject.hasSetter(keyPropertyName)
                    && String.class.equals(metaObject.getSetterType(keyPropertyName))
                    && StringUtils.isEmpty((String) metaObject.getValue(keyPropertyName))) {
                metaObject.setValue(keyPropertyName, UUID.randomUUID().toString());
            }
        }
        insert(statement, parameter);
    }
    
    /**
     * <插入对象> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(String statement, Object parameter) {
        if (parameter != null) {
            this.sqlSessionTemplate.insert(statement, parameter);
        }
        else {
            this.sqlSessionTemplate.insert(statement);
        }
    }
    
    /**
     * 提供给批量插入使用 需要使用用户自己控制异常处理， 以及flush的时候 需要自己调用
     * 
     * @param statement
     * @param parameter
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void insertForBatch(SqlSession sqlSession, String statement,
            Object parameter, String keyProperty) {
        if (!StringUtils.isEmpty(keyProperty)) {
            //如果指定了keyProperty
            MetaObject metaObject = MetaObject.forObject(parameter);
            if (metaObject.hasSetter(keyProperty)
                    && String.class.equals(metaObject.getSetterType(keyProperty))
                    && StringUtils.isEmpty((String) metaObject.getValue(keyProperty))) {
                metaObject.setValue(keyProperty, UUID.randomUUID().toString());
            }
        }
        
        if (parameter != null) {
            sqlSession.insert(statement, parameter);
        }
        else {
            sqlSession.insert(statement);
        }
    }
    
    /**
     * 批量插入数据 <br/>
     * 1、数据批量插入，默认一次提交100条，当发生异常后继续提交异常行以后的数据，待集合全部进行提交后返回批量处理结果<br/>
     * 2、数据批量插入，如果需要回滚，当发生异常后，数据库异常即向外抛出，不会进行至全部执行后再抛出异常 <br/>
     * <功能详细描述>
     * 
     * @param statement
     * @param objectCollection
     * @param isRollback
     * @return [参数说明]
     * 
     * @return BatchResult<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BatchResult batchInsert(String statement, List<?> objectList,
            boolean isStopWhenFlushHappenedException) {
        return batchInsert(statement,
                objectList,
                defaultDoFlushSize,
                isStopWhenFlushHappenedException);
    }
    
    /**
     * 批量插入数据
     * 
     * @param statement
     * @param objectList
     *            对象列表
     * @param doFlushSize
     * @param isStopWhenFlushHappenedException
     *            当在flush发生异常时是否停止，如果在调用insert时抛出的异常，不在此设置影响范围内
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    // 批量插入
    public BatchResult batchInsert(String statement, List<?> objectList,
            int doFlushSize, boolean isStopWhenFlushHappenedException) {
        BatchResult result = new BatchResult();
        if (CollectionUtils.isEmpty(objectList)) {
            return result;
        }
        if (doFlushSize <= 0) {
            doFlushSize = defaultDoFlushSize;
        }
        //设置总条数
        result.setTotalNum(objectList.size());
        
        //从当前环境中根据connection生成批量提交的sqlSession
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory()
                .openSession(ExecutorType.BATCH);
        
        try {
            // 本次flush的列表开始行行索引
            int startFlushRowIndex = 0;
            for (int index = 0; index < objectList.size(); index++) {
                // 插入对象
                insertForBatch(sqlSession,
                        statement,
                        objectList.get(index),
                        null);
                if ((index > 0 && index % doFlushSize == 0)
                        || index == objectList.size() - 1) {
                    try {
                        List<org.apache.ibatis.executor.BatchResult> test = flushBatchStatements(sqlSession);
                        System.out.println(test);
                        startFlushRowIndex = index + 1;
                    }
                    catch (Exception ex) {
                        if (!(ex.getCause() instanceof BatchExecutorException)
                                || isStopWhenFlushHappenedException) {
                            DataAccessException translated = this.sqlSessionTemplate.getPersistenceExceptionTranslator()
                                    .translateExceptionIfPossible((PersistenceException) ex);
                            throw translated;
                        }
                        
                        BatchExecutorException e = (BatchExecutorException) ex.getCause();
                        // 如果为忽略错误异常则记录警告日志即可，无需打印堆栈，如果需要堆栈，需将日志级别配置为debug
                        logger.warn("batchInsert hanppend Exception:{},the exception be igorned.",
                                ex.toString());
                        if (logger.isDebugEnabled()) {
                            logger.debug(ex.toString(), ex);
                        }
                        
                        // 获取错误行数，由于错误行发生的地方
                        int errorRownumIndex = startFlushRowIndex
                                + e.getSuccessfulBatchResults().size();
                        result.addErrorInfoWhenException(objectList.get(index),
                                errorRownumIndex,
                                ex);
                        
                        //将行索引调整为错误行的行号，即从发生错误的行后面一行继续执行
                        index = errorRownumIndex;
                        startFlushRowIndex = errorRownumIndex + 1;
                    }
                }
            }
        }
        finally {
            sqlSession.close();
        }
        return result;
    }
    
    /**
     * <更新对象> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int update(String statement, Object parameter) {
        if (parameter != null) {
            return this.sqlSessionTemplate.update(statement, parameter);
        }
        else {
            return this.sqlSessionTemplate.update(statement);
        }
    }
    
    /**
     * 提供给批量更新使用 需要使用用户自己控制异常处理， 以及flush的时候 需要自己调用
     * 
     * @param statement
     * @param parameter
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void updateForBatch(SqlSession sqlSession, String statement,
            Object parameter) {
        if (parameter != null) {
            sqlSession.update(statement, parameter);
        }
        else {
            sqlSession.update(statement);
        }
    }
    
    /**
     * 批量更新数据 1、数据批量更新，默认一次提交100条，当发生异常后继续提交异常行以后的数据，待集合全部进行提交后返回批量处理结果
     * 2、数据批量更新，如果需要回滚，当发生异常后，数据库异常即向外抛出，不会进行至全部执行后再抛出异常 <功能详细描述>
     * 
     * @param statement
     * @param objectCollection
     * @param isRollback
     * @return [参数说明]
     * 
     * @return BatchResult<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BatchResult batchUpdate(String statement, List<?> objectList,
            boolean isStopWhenFlushHappenedException) {
        return batchUpdate(statement,
                objectList,
                defaultDoFlushSize,
                isStopWhenFlushHappenedException);
    }
    
    /**
     * 批量更新数据
     * 
     * @param statement
     * @param objectList
     *            对象列表
     * @param doFlushSize
     * @param isStopWhenFlushHappenedException
     *            当在flush发生异常时是否停止，如果在调用insert时抛出的异常，不在此设置影响范围内
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    // 批量插入
    public BatchResult batchUpdate(String statement, List<?> objectList,
            int doFlushSize, boolean isStopWhenFlushHappenedException) {
        BatchResult result = new BatchResult();
        if (CollectionUtils.isEmpty(objectList)) {
            return result;
        }
        if (doFlushSize <= 0) {
            doFlushSize = defaultDoFlushSize;
        }
        
        //从当前环境中根据connection生成批量提交的sqlSession
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory()
                .openSession(ExecutorType.BATCH);
        
        try {
            // 本次flush的列表开始行行索引
            int startFlushRowIndex = 0;
            for (int index = 0; index < objectList.size(); index++) {
                // 插入对象
                updateForBatch(sqlSession, statement, objectList.get(index));
                if ((index > 0 && index % doFlushSize == 0)
                        || index == objectList.size() - 1) {
                    try {
                        List<org.apache.ibatis.executor.BatchResult> test = flushBatchStatements(sqlSession);
                        System.out.println(test);
                        startFlushRowIndex = index + 1;
                    }
                    catch (Exception ex) {
                        if (!(ex.getCause() instanceof BatchExecutorException)
                                || isStopWhenFlushHappenedException) {
                            DataAccessException translated = this.sqlSessionTemplate.getPersistenceExceptionTranslator()
                                    .translateExceptionIfPossible((PersistenceException) ex);
                            throw translated;
                        }
                        
                        BatchExecutorException e = (BatchExecutorException) ex.getCause();
                        // 如果为忽略错误异常则记录警告日志即可，无需打印堆栈，如果需要堆栈，需将日志级别配置为debug
                        logger.warn("batchUpdate hanppend Exception:{},the exception be igorned.",
                                ex.toString());
                        if (logger.isDebugEnabled()) {
                            logger.debug(ex.toString(), ex);
                        }
                        
                        // 获取错误行数，由于错误行发生的地方
                        int errorRownumIndex = startFlushRowIndex
                                + e.getSuccessfulBatchResults().size();
                        result.addErrorInfoWhenException(objectList.get(index),
                                errorRownumIndex,
                                ex);
                        
                        //将行索引调整为错误行的行号，即从发生错误的行后面一行继续执行
                        index = errorRownumIndex;
                        startFlushRowIndex = errorRownumIndex + 1;
                    }
                }
            }
        }
        finally {
            sqlSession.close();
        }
        
        return result;
    }
    
    /**
     * <增加或删除对象，先查询对应对象是否存在，如果存在，则执行更新操作，如果不存在，执行插入操作> <功能详细描述>
     * 
     * @param findStatement
     *            查询所用到的查询statement
     * @param insertStatement
     *            插入所用到的查询statement
     * @param updateStatement
     *            更新所用到的查询statement
     * @param parameter
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void save(String findStatement, String insertStatement,
            String updateStatement, Object parameter) {
        Object resObj = find(findStatement, parameter);
        if (resObj == null) {
            insert(insertStatement, parameter);
        }
        else {
            update(updateStatement, parameter);
        }
    }
    
    /**
     * 提供给批量save(新增或修改)使用 需要使用用户自己控制异常处理， 以及flush的时候 需要自己调用
     * 
     * @param statement
     * @param parameter
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void saveForBatch(SqlSession sqlSession, String findStatement,
            String insertStatement, String updateStatement, Object parameter) {
        Object resObj = find(findStatement, parameter);
        if (resObj == null) {
            insertForBatch(sqlSession, insertStatement, parameter, null);
        }
        else {
            updateForBatch(sqlSession, updateStatement, parameter);
        }
    }
    
    /**
     * 批量save(新增或修改)数据 1、数据批量删除，默认一次提交100条数据的删除，当发生异常后继续提交异常行以后的数据，待集合全部进行提交后返回批量处理结果
     * 2、数据批量save(新增或修改)，如果需要回滚，当发生异常后，数据库异常即向外抛出，不会进行至全部执行后再抛出异常 <功能详细描述>
     * 
     * @param statement
     * @param objectCollection
     * @param isRollback
     * @return [参数说明]
     * 
     * @return BatchResult<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BatchResult batchSave(String findStatement, String insertStatement,
            String updateStatement, List<?> objectList,
            boolean isStopWhenFlushHappenedException) {
        return batchSave(findStatement,
                insertStatement,
                updateStatement,
                objectList,
                defaultDoFlushSize,
                isStopWhenFlushHappenedException);
    }
    
    /**
     * 批量save(新增或修改)
     * 
     * @param statement
     * @param objectList
     *            对象列表
     * @param doFlushSize
     * @param isStopWhenFlushHappenedException
     *            当在flush发生异常时是否停止，如果在调用insert时抛出的异常，不在此设置影响范围内
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    // 批量插入
    public BatchResult batchSave(String findStatement, String insertStatement,
            String updateStatement, List<?> objectList, int doFlushSize,
            boolean isStopWhenFlushHappenedException) {
        BatchResult result = new BatchResult();
        if (CollectionUtils.isEmpty(objectList)) {
            return result;
        }
        if (doFlushSize <= 0) {
            doFlushSize = defaultDoFlushSize;
        }
        
        //从当前环境中根据connection生成批量提交的sqlSession
        SqlSession sqlSession = SqlSessionUtils.getSqlSession(this.sqlSessionTemplate.getSqlSessionFactory(),
                ExecutorType.BATCH,
                this.sqlSessionTemplate.getPersistenceExceptionTranslator());
        
        // 本次flush的列表开始行行索引
        int startFlushRowIndex = 0;
        for (int index = 0; index < objectList.size(); index++) {
            // 插入对象
            saveForBatch(sqlSession,
                    findStatement,
                    insertStatement,
                    updateStatement,
                    objectList.get(index));
            index++;
            if (index % doFlushSize == 0 || index == objectList.size()) {
                try {
                    flushBatchStatements(sqlSession);
                    startFlushRowIndex = index;
                }
                catch (BatchExecutorException ex) {
                    if (isStopWhenFlushHappenedException) {
                        throw ex;
                    }
                    
                    // 如果为忽略错误异常则记录警告日志即可，无需打印堆栈，如果需要堆栈，需将日志级别配置为debug
                    logger.warn("batchSave hanppend Exception:{},the exception be igorned.",
                            ex.toString());
                    if (logger.isDebugEnabled()) {
                        logger.debug(ex.toString(), ex);
                    }
                    
                    // 获取错误行数，由于错误行发生的地方
                    int errorRownumIndex = startFlushRowIndex
                            + ex.getSuccessfulBatchResults().size();
                    result.addErrorInfoWhenException(objectList.get(index),
                            errorRownumIndex,
                            ex);
                    
                    // 将行索引调整为错误行的行号，即从发生错误的行后面一行继续执行
                    index = errorRownumIndex + 1;
                    startFlushRowIndex = index;
                }
            }
        }
        
        return result;
    }
    
    /**
     * <查询> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     * @param rowBounds
     * @param handler
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void query(String statement, Object parameter, RowBounds rowBounds,
            ResultHandler handler) {
        this.sqlSessionTemplate.select(statement, parameter, rowBounds, handler);
    }
    
    /**
     * = <删除对象> <功能详细描述>
     * 
     * @param statement
     * @param parameter
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int delete(String statement, Object parameter) {
        if (parameter != null) {
            return this.sqlSessionTemplate.delete(statement, parameter);
        }
        else {
            return this.sqlSessionTemplate.delete(statement);
        }
    }
    
    /**
     * 提供给批量删除使用 需要使用用户自己控制异常处理， 以及flush的时候 需要自己调用
     * 
     * @param statement
     * @param parameter
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void deleteForBatch(SqlSession sqlSession, String statement,
            Object parameter) {
        if (parameter != null) {
            sqlSession.delete(statement, parameter);
        }
        else {
            sqlSession.delete(statement);
        }
    }
    
    /**
     * 批量删除数据 1、数据批量删除，默认一次提交100条数据的删除，当发生异常后继续提交异常行以后的数据，待集合全部进行提交后返回批量处理结果
     * 2、数据批量删除，如果需要回滚，当发生异常后，数据库异常即向外抛出，不会进行至全部执行后再抛出异常 <功能详细描述>
     * 
     * @param statement
     * @param objectCollection
     * @param isRollback
     * @return [参数说明]
     * 
     * @return BatchResult<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BatchResult batchDelete(String statement, List<?> objectList,
            boolean isStopWhenFlushHappenedException) {
        return batchDelete(statement,
                objectList,
                defaultDoFlushSize,
                isStopWhenFlushHappenedException);
    }
    
    /**
     * 批量删除数据
     * 
     * @param statement
     * @param objectList
     *            对象列表
     * @param doFlushSize
     * @param isStopWhenFlushHappenedException
     *            当在flush发生异常时是否停止，如果在调用insert时抛出的异常，不在此设置影响范围内
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    // 批量插入
    public BatchResult batchDelete(String statement, List<?> objectList,
            int doFlushSize, boolean isStopWhenFlushHappenedException) {
        BatchResult result = new BatchResult();
        if (CollectionUtils.isEmpty(objectList)) {
            return result;
        }
        if (doFlushSize <= 0) {
            doFlushSize = defaultDoFlushSize;
        }
        
        //从当前环境中根据connection生成批量提交的sqlSession
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory()
                .openSession(ExecutorType.BATCH);
        
        try {
            // 本次flush的列表开始行行索引
            int startFlushRowIndex = 0;
            for (int index = 0; index < objectList.size(); index++) {
                // 插入对象
                deleteForBatch(sqlSession, statement, objectList.get(index));
                if ((index > 0 && index % doFlushSize == 0)
                        || index == objectList.size() - 1) {
                    try {
                        List<org.apache.ibatis.executor.BatchResult> test = flushBatchStatements(sqlSession);
                        System.out.println(test);
                        startFlushRowIndex = index + 1;
                    }
                    catch (Exception ex) {
                        if (!(ex.getCause() instanceof BatchExecutorException)
                                || isStopWhenFlushHappenedException) {
                            DataAccessException translated = this.sqlSessionTemplate.getPersistenceExceptionTranslator()
                                    .translateExceptionIfPossible((PersistenceException) ex);
                            throw translated;
                        }
                        
                        BatchExecutorException e = (BatchExecutorException) ex.getCause();
                        // 如果为忽略错误异常则记录警告日志即可，无需打印堆栈，如果需要堆栈，需将日志级别配置为debug
                        logger.warn("batchUpdate hanppend Exception:{},the exception be igorned.",
                                ex.toString());
                        if (logger.isDebugEnabled()) {
                            logger.debug(ex.toString(), ex);
                        }
                        
                        // 获取错误行数，由于错误行发生的地方
                        int errorRownumIndex = startFlushRowIndex
                                + e.getSuccessfulBatchResults().size();
                        result.addErrorInfoWhenException(objectList.get(index),
                                errorRownumIndex,
                                ex);
                        
                        //将行索引调整为错误行的行号，即从发生错误的行后面一行继续执行
                        index = errorRownumIndex;
                        startFlushRowIndex = errorRownumIndex + 1;
                    }
                }
            }
        }
        finally {
            sqlSession.close();
        }
        
        return result;
    }
    
    /**
     * 为批量提交更新删除等提供 批次提交的功能，在大列表发生时调用该statements 
     * 1、提供给用户自己控制批量执行的灵活性
     * 2、仅在批量功能中有效，非batch操作不会受该方法调用所影响
     * 
     * @return [参数说明]
     * 
     * @return List<org.apache.ibatis.executor.BatchResult> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<org.apache.ibatis.executor.BatchResult> flushBatchStatements(
            SqlSession sqlSession) {
        return sqlSession.flushStatements();
    }
    
    /**
     * <获取sqlSessionFactory> <功能详细描述>
     * 
     * @return [参数说明]
     * 
     * @return SqlSessionFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionTemplate.getSqlSessionFactory();
    }
    
    /**
     * @return 返回 sqlSessionTemplate
     */
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
    
    /**
     * @param 对sqlSessionTemplate进行赋值
     */
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
}
