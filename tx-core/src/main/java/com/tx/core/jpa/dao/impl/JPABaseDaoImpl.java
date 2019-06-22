/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月28日
 * <修改描述:>
 */
package com.tx.core.jpa.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.jpa.dao.JPABaseDao;

/**
 * JPA基础持久层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class JPABaseDaoImpl<T, ID extends Serializable>
        implements JPABaseDao<T, ID> {
    
    /** 日志记录句柄 */
    protected Logger logger = LoggerFactory.getLogger(JPABaseDaoImpl.class);
    
    /** 默认在batch功能执行过程中批量持久的条数 */
    private static final int defaultDoFlushSize = 500;
    
    /** 实体类类型 */
    protected Class<T> entityClass;
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    /** <默认构造函数> */
    @SuppressWarnings("unchecked")
    public JPABaseDaoImpl() {
        super();
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        entityClass = (Class<T>) resolvableType.as(JPABaseDaoImpl.class)
                .getGeneric()
                .resolve();
    }
    
    /**
     * @param entity
     */
    @Override
    public void persist(T entity) {
        AssertUtils.notNull(entity, "entity is null.");
        
        entityManager.persist(entity);
    }
    
    /**
     * @param entity
     * @return
     */
    @Override
    public T merge(T entity) {
        AssertUtils.notNull(entity, "entity is null.");
        
        return entityManager.merge(entity);
    }
    
    /**
     * @param entity
     * @param lockModeType
     */
    @Override
    public void refresh(T entity, LockModeType lockModeType) {
        if (entity != null) {
            if (lockModeType != null) {
                entityManager.refresh(entity, lockModeType);
            } else {
                entityManager.refresh(entity);
            }
        }
    }
    
    /**
     * @param entity
     */
    @Override
    public void remove(T entity) {
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
    
    /**
     * @param entity<br/>
     */
    @Override
    public void detach(T entity) {
        if (entity != null) {
            entityManager.detach(entity);
        }
    }
    
    /**
     * @param id
     * @param lockModeType
     * @return
     */
    @Override
    public T find(ID id, LockModeType lockModeType) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        if (lockModeType != null) {
            return entityManager.find(entityClass, id, lockModeType);
        } else {
            return entityManager.find(entityClass, id);
        }
    }
    
    /**
     * @param entity<br/>
     * @return
     */
    @Override
    public LockModeType getLockMode(Object entity) {
        AssertUtils.notNull(entity, "entity is null.");
        
        return entityManager.getLockMode(entity);
    }
    
    /**
     * flush<br/>
     */
    @Override
    public void flush() {
        entityManager.flush();
    }
    
    /**
     * clear<br/>
     */
    @Override
    public void clear() {
        entityManager.clear();
    }
    
    /**
     * @return
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
