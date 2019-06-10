/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-4
 * <修改描述:>
 */
package com.tx.core.mybatis.support;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDHexGenerator;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QueryConditionItem;
import com.tx.core.querier.model.QueryOrderItem;
import com.tx.core.querier.util.MybatisQuerierUtils;
import com.tx.core.util.ObjectUtils;

/**
 * mybatis数据库查询类<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-11-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyBatisDaoSupport implements InitializingBean {
    
    /** 默认在batch功能执行过程中批量持久的条数 */
    private static final int defaultDoFlushSize = 500;
    
    /** 默认的uuid生成方案 */
    private static final IdentifierGenerator generator = new UUIDHexGenerator();
    
    /** sqlSessionTemplate */
    private SqlSessionTemplate sqlSessionTemplate;
    
    /** batchSqlSessionTemplate */
    private SqlSessionTemplate batchSqlSessionTemplate;
    
    /** <默认构造函数> */
    public MyBatisDaoSupport() {
        super();
    }
    
    /** <默认构造函数> */
    public MyBatisDaoSupport(SqlSessionTemplate sqlSessionTemplate) {
        super();
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        AssertUtils.notNull(this.sqlSessionTemplate,
                "sqlSessionTemplate is null.");
    }
    
    /**
     * 获取批处理的sqlSessionTemplate句柄<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return SqlSessionTemplate [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public SqlSessionTemplate getBatchSqlSessionTemplate() {
        if (this.batchSqlSessionTemplate != null) {
            return this.batchSqlSessionTemplate;
        }
        this.batchSqlSessionTemplate = new SqlSessionTemplate(
                getSqlSessionFactory(), ExecutorType.BATCH,
                getExceptionTranslator());
        return this.batchSqlSessionTemplate;
    }
    
    /**
     * 利用hibernaeUUID生成器，生成唯一键
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String generateUUID() {
        return generator.generate(null, null).toString();
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
            String pkPropertyName) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.notEmpty(pkPropertyName, "pkPropertyName is empty.");
        
        AssertUtils.notNull(parameter, "parameter is null.");
        //如果指定了keyProperty
        BeanWrapper bw = PropertyAccessorFactory
                .forBeanPropertyAccess(parameter);
        PropertyDescriptor pd = bw.getPropertyDescriptor(pkPropertyName);
        AssertUtils.isTrue(
                pd != null && pd.getWriteMethod() != null
                        && pd.getReadMethod() != null
                        && String.class.isAssignableFrom(pd.getPropertyType()),
                "pkPropertyName is not exist or not writeable or not readable.class:{} pkPropertyName:{} or pkPropertyType is not String.",
                new Object[] { parameter.getClass(), pkPropertyName });
        
        if (ObjectUtils.isEmpty(bw.getPropertyValue(pkPropertyName))) {
            bw.setPropertyValue(pkPropertyName, generateUUID());
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
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (parameter != null) {
            this.sqlSessionTemplate.insert(statement, parameter);
        } else {
            this.sqlSessionTemplate.insert(statement);
        }
    }
    
    /**
     * 批量插入数据 <br/>
     * 1、数据批量插入，默认一次提交100条，当发生异常后继续提交异常行以后的数据，待集合全部进行提交后返回批量处理结果<br/>
     * 2、数据批量插入，如果需要回滚，当发生异常后，数据库异常即向外抛出，不会进行至全部执行后再抛出异常 <br/>
     * <功能详细描述>
     * @param statement
     * @param objectList
     * @param nonIgnoreError [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public void batchInsert(String statement, List<?> objectList,
            boolean nonIgnoreError) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.isTrue(nonIgnoreError, "nonIgnoreError is false.");
        
        //批量插入
        batchInsert(statement, objectList, defaultDoFlushSize);
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
    public void batchInsert(String statement, List<?> objectList) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        //批量插入
        batchInsert(statement, objectList, defaultDoFlushSize);
    }
    
    /**
     * 批量插入数据
     * 
     * @param statement
     * @param objectList 对象列表
     * @param doFlushSize
     * @param errorNonIgnore 当在flush发生异常时是否停止，如果在调用insert时抛出的异常，不在此设置影响范围内
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    // 批量插入
    public void batchInsert(String statement, List<?> objectList,
            int doFlushSize) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (CollectionUtils.isEmpty(objectList)) {
            return;
        }
        if (doFlushSize <= 0) {
            doFlushSize = defaultDoFlushSize;
        }
        
        // 本次flush的列表开始行行索引
        for (int index = 0; index < objectList.size(); index++) {
            Object object = objectList.get(index);
            getBatchSqlSessionTemplate().insert(statement, object);
            if ((index > 0 && index % doFlushSize == 0)
                    || index == objectList.size() - 1) {
                getBatchSqlSessionTemplate().flushStatements();
            }
        }
    }
    
    /**
     * 批量插入<br/>
     * <功能详细描述>
     * @param statement
     * @param objectList
     * @param keyPropertyName
     * @param nonIgnoreError [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public void batchInsertUseUUID(String statement, List<?> objectList,
            String keyPropertyName, boolean nonIgnoreError) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.isTrue(nonIgnoreError, "nonIgnoreError is false.");
        
        batchInsertUseUUID(statement, objectList, keyPropertyName);
    }
    
    /**
     * 批量插入数据 \主键字段自动生成并插入 <br/>
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
    public void batchInsertUseUUID(String statement, List<?> objectList,
            String keyPropertyName) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        batchInsertUseUUID(statement,
                objectList,
                keyPropertyName,
                defaultDoFlushSize);
    }
    
    /**
      * 批量插入数据 \主键字段自动生成并插入<功能简述>
      * <功能详 细描述>
      * @param statement
      * @param objectList
      * @param keyPropertyName
      * @param doFlushSize
      * @param isStopWhenFlushHappenedException
      * @return [参数说明]
      * 
      * @return BatchResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    // 批量插入
    public void batchInsertUseUUID(String statement, List<?> objectList,
            String pkPropertyName, int doFlushSize) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.notEmpty(pkPropertyName, "pkPropertyName is empty.");
        
        if (CollectionUtils.isEmpty(objectList)) {
            return;
        }
        if (doFlushSize <= 0) {
            doFlushSize = defaultDoFlushSize;
        }
        
        for (Object obj : objectList) {
            AssertUtils.notNull(obj, "parameter is null.");
            
            //如果指定了keyProperty
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(obj);
            PropertyDescriptor pd = bw.getPropertyDescriptor(pkPropertyName);
            AssertUtils.isTrue(
                    pd != null && pd.getWriteMethod() != null
                            && pd.getReadMethod() != null,
                    "pkPropertyName is not exist or not writeable or not readable.class:{} pkPropertyName:{}",
                    new Object[] { obj.getClass(), pkPropertyName });
            if (ObjectUtils.isEmpty(bw.getPropertyValue(pkPropertyName))) {
                bw.setPropertyValue(pkPropertyName, generateUUID());
            }
        }
        batchInsert(statement, objectList, doFlushSize);
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
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (parameter != null) {
            return this.sqlSessionTemplate.delete(statement, parameter);
        } else {
            return this.sqlSessionTemplate.delete(statement);
        }
    }
    
    /**
     * 批量删除<br/>
     * <功能详细描述>
     * @param statement
     * @param objectList
     * @param nonIgnoreError [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public void batchDelete(String statement, List<?> objectList,
            boolean nonIgnoreError) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.isTrue(nonIgnoreError, "nonIgnoreError is false.");
        
        batchDelete(statement, objectList, defaultDoFlushSize);
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
    public void batchDelete(String statement, List<?> objectList) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        batchDelete(statement, objectList, defaultDoFlushSize);
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
    public void batchDelete(String statement, List<?> objectList,
            int doFlushSize) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (CollectionUtils.isEmpty(objectList)) {
            return;
        }
        if (doFlushSize <= 0) {
            doFlushSize = defaultDoFlushSize;
        }
        
        // 本次flush的列表开始行行索引
        for (int index = 0; index < objectList.size(); index++) {
            Object object = objectList.get(index);
            getBatchSqlSessionTemplate().delete(statement, object);
            if ((index > 0 && index % doFlushSize == 0)
                    || index == objectList.size() - 1) {
                getBatchSqlSessionTemplate().flushStatements();
            }
        }
    }
    
    /**
     * 更新对象<br/>
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
        } else {
            return this.sqlSessionTemplate.update(statement);
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
    @Deprecated
    public void batchUpdate(String statement, List<?> objectList,
            boolean nonIgnoreError) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.isTrue(nonIgnoreError, "nonIgnoreError is false.");
        
        batchUpdate(statement, objectList, defaultDoFlushSize);
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
    public void batchUpdate(String statement, List<?> objectList) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        batchUpdate(statement, objectList, defaultDoFlushSize);
    }
    
    /**
     * 批量更新数据
     * 
     * @param statement
     * @param objectList 对象列表
     * @param doFlushSize
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    // 批量插入
    public void batchUpdate(String statement, List<?> objectList,
            int doFlushSize) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (CollectionUtils.isEmpty(objectList)) {
            return;
        }
        if (doFlushSize <= 0) {
            doFlushSize = defaultDoFlushSize;
        }
        
        // 本次flush的列表开始行行索引
        for (int index = 0; index < objectList.size(); index++) {
            Object object = objectList.get(index);
            getBatchSqlSessionTemplate().update(statement, object);
            if ((index > 0 && index % doFlushSize == 0)
                    || index == objectList.size() - 1) {
                getBatchSqlSessionTemplate().flushStatements();
            }
        }
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
        } else {
            update(updateStatement, parameter);
        }
    }
    
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
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (parameter != null) {
            return this.sqlSessionTemplate.<T> selectOne(statement, parameter);
        } else {
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
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (parameter != null) {
            return (Integer) this.sqlSessionTemplate.selectOne(statement,
                    parameter);
        } else {
            return (Integer) this.sqlSessionTemplate.selectOne(statement);
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
    public <T> int count(String statement, Class<T> entityType,
            Querier querier) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.notNull(entityType, "entityType is null.");
        
        Map<String, Object> params = querier.getParams() == null
                ? new HashMap<>() : querier.getParams();
        List<QueryConditionItem> conditions = MybatisQuerierUtils
                .parseQueryConditions(entityType, querier);
        List<QueryOrderItem> orders = MybatisQuerierUtils
                .parseQueryOrders(entityType, querier);
        params.put("conditions", conditions);
        params.put("orders", orders);
        
        int count = count(statement, params);
        return count;
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
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (parameter != null) {
            return this.sqlSessionTemplate.<T> selectList(statement, parameter);
        } else {
            return this.sqlSessionTemplate.<T> selectList(statement);
        }
    }
    
    /**
     * 根据查询器实例查询对象列表<br/>
     * 
     * @param statement
     * @param parameter
     * @return [参数说明]
     * 
     * @return List<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <T> List<T> queryList(String statement, Class<T> entityType,
            Querier querier) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.notNull(entityType, "entityType is null.");
        
        Map<String, Object> params = querier.getParams() == null
                ? new HashMap<>() : querier.getParams();
        List<QueryConditionItem> conditions = MybatisQuerierUtils
                .parseQueryConditions(entityType, querier);
        List<QueryOrderItem> orders = MybatisQuerierUtils
                .parseQueryOrders(entityType, querier);
        params.put("conditions", conditions);
        params.put("orders", orders);
        
        return this.<T> queryList(statement, params);
    }
    
    /**
     * <查询分页对象>
     * <功能详细描述>
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
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        PagedList<T> result = new PagedList<T>();
        AssertUtils.isTrue(pageIndex > 0,
                "pageIndex must be integer.but pageIndex now:{}",
                new Object[] { pageIndex });
        
        result.setPageIndex(pageIndex);
        result.setPageSize(pageSize);
        
        // 构建Count查询列表中数目
        String queryCountStatement = statement + "Count";
        int count = (Integer) this.sqlSessionTemplate
                .selectOne(queryCountStatement, parameter);
        result.setCount(count);
        if (count <= 0) {
            return result;
        }
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        limit = limit > count ? count : limit;
        List<T> list = this.sqlSessionTemplate.<T> selectList(statement,
                parameter,
                new RowBounds(offset, limit));
        result.setList(list);
        return result;
    }
    
    /**
     * 查询分页对象<br/>
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
            Class<T> entityType, Querier querier, int pageIndex, int pageSize) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.notNull(entityType, "entityType is null.");
        
        Map<String, Object> params = querier.getParams() == null
                ? new HashMap<>() : querier.getParams();
        
        List<QueryConditionItem> conditions = MybatisQuerierUtils
                .parseQueryConditions(entityType, querier);
        List<QueryOrderItem> orders = MybatisQuerierUtils
                .parseQueryOrders(entityType, querier);
        params.put("conditions", conditions);
        params.put("orders", orders);
        
        PagedList<T> pagedList = this.<T> queryPagedList(statement,
                params,
                pageIndex,
                pageSize);
        return pagedList;
    }
    
    /**
     * 查询分页对象,传入count<br/>
     * 当count <= 0 或当前显示页已经是最后一页，则会调用对应count
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
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        PagedList<T> result = new PagedList<T>();
        result.setPageIndex(pageIndex);
        result.setPageSize(pageSize);
        
        // 构建Count查询列表中数目,如果count<0，则忽略
        result.setCount(count);
        if (count == 0) {
            return result;
        }
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        limit = limit > count ? count : limit;
        List<T> list = this.sqlSessionTemplate.<T> selectList(statement,
                parameter,
                new RowBounds(offset, limit));
        result.setList(list);
        return result;
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
            Class<T> entityType, Querier querier, int pageIndex, int pageSize,
            int count) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        AssertUtils.notNull(entityType, "entityType is null.");
        
        Map<String, Object> params = querier.getParams() == null
                ? new HashMap<>() : querier.getParams();
        
        List<QueryConditionItem> conditions = MybatisQuerierUtils
                .parseQueryConditions(entityType, querier);
        List<QueryOrderItem> orders = MybatisQuerierUtils
                .parseQueryOrders(entityType, querier);
        params.put("conditions", conditions);
        params.put("orders", orders);
        
        PagedList<T> resPagedList = this.<T> queryPagedList(statement,
                params,
                pageIndex,
                pageSize,
                count);
        return resPagedList;
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
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (parameter != null) {
            return this.sqlSessionTemplate.<K, V> selectMap(statement,
                    parameter,
                    mapKey);
        } else {
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
    public <K, V> Map<K, V> queryToMapByPage(String statement, Object parameter,
            String mapKey, int pageIndex, int pageSize) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        
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
            ResultHandler<?> handler) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        if (parameter != null) {
            this.sqlSessionTemplate.select(statement, parameter, handler);
        } else {
            this.sqlSessionTemplate.select(statement, handler);
        }
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
            ResultHandler<?> handler) {
        AssertUtils.notEmpty(statement, "statement is empty.");
        
        this.sqlSessionTemplate.select(statement,
                parameter,
                rowBounds,
                handler);
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
    public void flush() {
        this.sqlSessionTemplate.flushStatements();
    }
    
    /**
      * 清空缓存<br/>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void clear() {
        this.sqlSessionTemplate.clearCache();
    }
    
    /**
     * sqlSessionTemplate
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return SqlSessionTemplate [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
    
    /**
     * ExceptionTranslator
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return PersistenceExceptionTranslator [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PersistenceExceptionTranslator getExceptionTranslator() {
        return this.sqlSessionTemplate.getPersistenceExceptionTranslator();
    }
    
    /**
     * 获取sqlSessionFactory
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
     * 获取Mybatis对应的sqlSessionFactory中的Configuration<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Configuration [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Configuration getConfiguration() {
        return getSqlSessionFactory().getConfiguration();
    }
    
    /**
     * setSqlSessionTemplate 
     * <功能详细描述>
     * @param sqlSessionTemplate [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}
