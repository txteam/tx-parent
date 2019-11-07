/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月8日
 * <修改描述:>
 */
package com.tx.core.mybatis.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.InitializingBean;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.assistant.BaseDaoMapperBuilderAssistant;
import com.tx.core.mybatis.dao.MybatisBaseDao;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QuerierBuilder;

/**
 * 抽象持久层接口<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class MybatisBaseDaoImpl<T, ID extends Serializable>
        implements MybatisBaseDao<T, ID>, InitializingBean {
    
    /** 实体类型 */
    protected final Class<T> entityType;
    
    /** 主键属性类型 */
    protected final Class<ID> pkPropertyType;
    
    /** mapper助手 */
    protected BaseDaoMapperBuilderAssistant assistant;
    
    /** <默认构造函数> */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MybatisBaseDaoImpl() {
        super();
        Class<?> clazz = getClass();
        Type type = clazz.getGenericSuperclass();//getGenericSuperclass()获得带有泛型的父类  
        AssertUtils.notNull(type, "type is not empty.");
        
        //ParameterizedType参数化类型，即泛型  
        ParameterizedType p = (ParameterizedType) type;
        //getActualTypeArguments获取参数化类型的数组，泛型可能有多个  
        this.entityType = (Class) p.getActualTypeArguments()[0];
        this.pkPropertyType = (Class) p.getActualTypeArguments()[1];
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(getMyBatisDaoSupport(),
                "myBatisDaoSupport is null.");
        AssertUtils.notNull(this.entityType, "entityType is null.");
        AssertUtils.notNull(this.pkPropertyType, "pkPropertyType is null.");
        this.assistant = new BaseDaoMapperBuilderAssistant(
                getMyBatisDaoSupport().getConfiguration(), this.entityType);
        
        AssertUtils.isTrue(
                this.pkPropertyType.isAssignableFrom(
                        this.assistant.getPkColumn().getPropertyType()),
                "pkPropertyType:[{}] is not assignable from actual pkPropertyType:[{}]",
                new Object[] { this.pkPropertyType,
                        this.assistant.getPkColumn().getPropertyType() });
        
        this.assistant.registe();
    }
    
    /**
     * 实体类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected Class<T> getEntityType() {
        return this.entityType;
    }
    
    /**
     * 主键属性类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<ID> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected Class<ID> getPKPropertyType() {
        return this.pkPropertyType;
    }
    
    /**
     * @param entity
     */
    @Override
    public void insert(T entity) {
        AssertUtils.notNull(entity, "entity is null.");
        
        if (String.class.isAssignableFrom(getPKPropertyType())) {
            getMyBatisDaoSupport().insertUseUUID(
                    this.assistant.getInsertStatementName(),
                    entity,
                    this.assistant.getPkColumn().getPropertyName());
        } else {
            getMyBatisDaoSupport()
                    .insert(this.assistant.getInsertStatementName(), entity);
        }
    }
    
    /**
     * @param entityList
     */
    @Override
    public void batchInsert(List<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        if (String.class.isAssignableFrom(getPKPropertyType())) {
            getMyBatisDaoSupport().batchInsertUseUUID(
                    this.assistant.getInsertStatementName(),
                    entityList,
                    this.assistant.getPkColumn().getPropertyName());
        } else {
            getMyBatisDaoSupport().batchInsert(
                    this.assistant.getInsertStatementName(), entityList);
        }
    }
    
    /**
     * @param pk
     * @return
     */
    @Override
    public boolean delete(ID pk) {
        AssertUtils.notNull(pk, "pk is null.");
        
        T entity = BeanUtils.instantiateClass(this.entityType);
        BeanWrapper entityBW = PropertyAccessorFactory
                .forBeanPropertyAccess(entity);
        entityBW.setPropertyValue(
                this.assistant.getPkColumn().getPropertyName(), pk);
        
        int count = delete(entity);
        if (count < 1) {
            return false;
        }
        AssertUtils.isTrue(count == 1,
                "update count should == 1.but actual is :{}",
                new Object[] { count });
        return true;
    }
    
    /**
     * @param entity
     * @return
     */
    @Override
    public int delete(T entity) {
        AssertUtils.notNull(entity, "entity is null.");
        
        int res = getMyBatisDaoSupport()
                .delete(this.assistant.getDeleteStatementName(), entity);
        return res;
    }
    
    /**
     * @param entityList
     */
    @Override
    public void batchDelete(List<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        
        getMyBatisDaoSupport().batchDelete(
                this.assistant.getDeleteStatementName(), entityList);
    }
    
    /**
     * @param pk
     * @param updateEntityMap
     * @return
     */
    @Override
    public boolean update(ID pk, Map<String, Object> updateEntityMap) {
        AssertUtils.notNull(pk, "pk is null.");
        AssertUtils.notEmpty(updateEntityMap, "updateEntityMap is empty.");
        
        updateEntityMap.put(this.assistant.getPkColumn().getPropertyName(), pk);
        int count = update(updateEntityMap);
        
        if (count < 1) {
            return false;
        }
        AssertUtils.isTrue(count == 1,
                "update count should == 1.but actual is :{}",
                new Object[] { count });
        return true;
        
    }
    
    /**
     * @param updateEntityMap
     * @return
     */
    @Override
    public int update(Map<String, Object> updateEntityMap) {
        AssertUtils.notEmpty(updateEntityMap, "updateEntityMap is empty.");
        
        int count = getMyBatisDaoSupport().update(
                this.assistant.getUpdateStatementName(), updateEntityMap);
        return count;
    }
    
    /**
     * @param updateEntityMapList
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateEntityMapList) {
        if (CollectionUtils.isEmpty(updateEntityMapList)) {
            return;
        }
        
        getMyBatisDaoSupport().batchUpdate(
                this.assistant.getUpdateStatementName(), updateEntityMapList);
    }
    
    /**
     * @param pk
     * @return
     */
    @Override
    public T find(ID pk) {
        AssertUtils.notNull(pk, "pk is null.");
        
        T entity = BeanUtils.instantiateClass(this.entityType);
        BeanWrapper entityBW = PropertyAccessorFactory
                .forBeanPropertyAccess(entity);
        entityBW.setPropertyValue(
                this.assistant.getPkColumn().getPropertyName(), pk);
        
        T res = find(entity);
        return res;
    }
    
    /**
     * @param entityCondition
     * @return
     */
    @Override
    public T find(T entity) {
        AssertUtils.notNull(entity, "entity is null.");
        
        T res = getMyBatisDaoSupport()
                .find(this.assistant.getFindStatementName(), entity);
        return res;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<T> queryList(Map<String, Object> params) {
        List<T> resList = getMyBatisDaoSupport()
                .queryList(this.assistant.getQueryStatementName(), params);
        return resList;
    }
    
    /**
     * @param querier
     * @return
     */
    @Override
    public List<T> queryList(Querier querier) {
        List<T> resList = getMyBatisDaoSupport().queryList(
                this.assistant.getQueryStatementName(),
                this.entityType,
                querier);
        return resList;
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        PagedList<T> resPagedList = getMyBatisDaoSupport().queryPagedList(
                this.assistant.getQueryStatementName(),
                params,
                pageIndex,
                pageSize);
        return resPagedList;
    }
    
    /**
     * @param querier
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Querier querier, int pageIndex,
            int pageSize) {
        PagedList<T> resPagedList = getMyBatisDaoSupport().queryPagedList(
                this.assistant.getQueryStatementName(),
                this.entityType,
                querier,
                pageIndex,
                pageSize);
        return resPagedList;
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param count
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, int count) {
        PagedList<T> resPagedList = getMyBatisDaoSupport().queryPagedList(
                this.assistant.getQueryStatementName(),
                params,
                pageIndex,
                pageSize,
                count);
        return resPagedList;
    }
    
    /**
     * @param querier
     * @param pageIndex
     * @param pageSize
     * @param count
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Querier querier, int pageIndex,
            int pageSize, int count) {
        PagedList<T> resPagedList = getMyBatisDaoSupport().queryPagedList(
                this.assistant.getQueryStatementName(),
                this.entityType,
                querier,
                pageIndex,
                pageSize,
                count);
        return resPagedList;
    }
    
    /**
     * @param params
     * @param exclude
     * @return
     */
    @Override
    public int count(Map<String, Object> params, ID exclude) {
        //AssertUtils.notNull(exclude, "exclude is null.");
        params = params == null ? new HashMap<String, Object>() : params;
        params.put(
                "exclude" + StringUtils.capitalize(
                        this.assistant.getPkColumn().getPropertyName()),
                exclude);
        
        int count = getMyBatisDaoSupport()
                .count(this.assistant.getCountStatmentName(), params);
        return count;
    }
    
    /**
     * @param querier
     * @param exclude
     * @return
     */
    @Override
    public int count(Querier querier, ID exclude) {
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        if (querier.getParams() == null) {
            querier.setParams(new HashMap<>());
        }
        querier.getParams()
                .put("exclude" + StringUtils.capitalize(
                        this.assistant.getPkColumn().getPropertyName()),
                        exclude);
        
        int count = getMyBatisDaoSupport().count(
                this.assistant.getCountStatmentName(),
                this.entityType,
                querier);
        return count;
    }
    
    /**
     * @return 返回 myBatisDaoSupport
     */
    public abstract MyBatisDaoSupport getMyBatisDaoSupport();
}
