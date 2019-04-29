///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2019年4月28日
// * <修改描述:>
// */
//package com.tx.core.jpa.support;
//
//import java.io.Serializable;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.ResolvableType;
//import org.springframework.dao.DataAccessException;
//
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.mybatis.model.BatchResult;
//
///**
// * JPA基础持久层实现<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2019年4月28日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public abstract class JPABaseDaoImpl<T, ID extends Serializable>
//        implements JPABaseDao<T, ID> {
//    
//    /** 默认在batch功能执行过程中批量持久的条数 */
//    private static final int defaultDoFlushSize = 100;
//    
//    protected Logger logger = LoggerFactory.getLogger(JPABaseDaoImpl.class);
//    
//    /** 实体类类型 */
//    protected Class<T> entityClass;
//    
//    @PersistenceContext
//    protected EntityManager entityManager;
//    
//    /** <默认构造函数> */
//    @SuppressWarnings("unchecked")
//    public JPABaseDaoImpl() {
//        super();
//        ResolvableType resolvableType = ResolvableType.forClass(getClass());
//        entityClass = (Class<T>) resolvableType.as(JPABaseDaoImpl.class)
//                .getGeneric()
//                .resolve();
//    }
//    
//    /**
//     * 插入数据<br/>
//     * @param entity
//     */
//    @Override
//    public void insert(T entity) {
//        this.entityManager.persist(entity);
//    }
//    
//    /**
//     * 批量插入数据<br/>
//     * @param objectList
//     */
//    @Override
//    public void batchInsert(List<T> objectList) {
//        this.batchInsert(objectList, defaultDoFlushSize, true);
//    }
//    
//    /**
//     * 批量插入数据<br/>
//     * 
//     * @param objectList 对象列表
//     * @param doFlushSize
//     * @param isStopWhenException 当在flush发生异常时是否停止，如果在调用insert时抛出的异常，不在此设置影响范围内
//     * @return BatchResult [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    @Override
//    public BatchResult batchInsert(List<T> objectList, int doFlushSize,
//            boolean isStopWhenException) {
//        BatchResult result = new BatchResult();
//        if (CollectionUtils.isEmpty(objectList)) {
//            return result;
//        }
//        if (doFlushSize <= 0) {
//            doFlushSize = defaultDoFlushSize;
//        }
//        
//        //设置总条数
//        result.setTotalNum(objectList.size());
//        // 本次flush的列表开始行行索引
//        for (int index = 0; index < objectList.size(); index++) {
//            try {
//                // 插入对象
//                this.entityManager.persist(objectList.get(index));
//                if ((index > 0 && index % doFlushSize == 0)
//                        || (index + 1 == objectList.size())) {
//                    //刷新
//                    this.entityManager.flush();
//                    //清除对象关联
//                    this.entityManager.clear();
//                }
//            } catch (DataAccessException ex) {
//                if (isStopWhenException) {
//                    throw ex;
//                }
//                
//                // 如果为忽略错误异常则记录警告日志即可，无需打印堆栈，如果需要堆栈，需将日志级别配置为debug
//                logger.warn(
//                        "Batch insert error.Exception be igorned. Exception:[{}]",
//                        ex.toString());
//                if (logger.isDebugEnabled()) {
//                    logger.debug(ex.toString(), ex);
//                }
//                // 获取错误行数，由于错误行发生的地方
//                result.addErrorInfo(objectList.get(index), index, ex);
//            }
//        }
//        return result;
//    }
//    
//    /**
//     * 删除对象<br/>
//     * @param entity
//     * @return
//     */
//    @Override
//    public int delete(T entity) {
//        this.entityManager.remove(entity);
//        return 0;
//    }
//    
//    /**
//     * 批量删除<br/>
//     * @param objectList
//     */
//    @Override
//    public void batchDelete(List<T> objectList) {
//        this.batchDelete(objectList, defaultDoFlushSize, true);
//    }
//    
//    /**
//     * 批量删除<br/>
//     * @param objectList
//     * @param doFlushSize
//     * @param isStopWhenException
//     * @return
//     */
//    @Override
//    public BatchResult batchDelete(List<T> objectList, int doFlushSize,
//            boolean isStopWhenException) {
//        BatchResult result = new BatchResult();
//        if (CollectionUtils.isEmpty(objectList)) {
//            return result;
//        }
//        if (doFlushSize <= 0) {
//            doFlushSize = defaultDoFlushSize;
//        }
//        
//        //设置总条数
//        result.setTotalNum(objectList.size());
//        // 本次flush的列表开始行行索引
//        for (int index = 0; index < objectList.size(); index++) {
//            try {
//                // 插入对象
//                this.entityManager.remove(objectList.get(index));
//                if ((index > 0 && index % doFlushSize == 0)
//                        || (index == objectList.size() - 1)) {
//                    //刷新
//                    this.entityManager.flush();
//                    //清除对象关联
//                    this.entityManager.clear();
//                }
//            } catch (DataAccessException ex) {
//                if (isStopWhenException) {
//                    throw ex;
//                }
//                // 如果为忽略错误异常则记录警告日志即可，无需打印堆栈，如果需要堆栈，需将日志级别配置为debug
//                logger.warn(
//                        "batchInsert hanppend Exception:{}.The exception be igorned.",
//                        ex.toString());
//                if (logger.isDebugEnabled()) {
//                    logger.debug(ex.toString(), ex);
//                }
//                // 获取错误行数，由于错误行发生的地方
//                result.addErrorInfo(objectList.get(index), index, ex);
//            }
//        }
//        this.entityManager.clear();
//        return result;
//    }
//    
//    public boolean exists(String attributeName, Object attributeValue) {
//        return exists(null, attributeName, attributeValue);
//    }
//    
//    public int count(ID id, String attributeName, Object attributeValue) {
//        AssertUtils.notEmpty(attributeName, "attributeName is empty.");
//        
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> criteriaQuery = criteriaBuilder
//                .createQuery(Long.class);
//        Root<T> root = criteriaQuery.from(entityClass);
//        criteriaQuery.select(criteriaBuilder.count(root));
//        if (id == null) {
//            criteriaQuery.where(criteriaBuilder.equal(root.get(attributeName),
//                    attributeValue));
//        } else {
//            criteriaQuery.where(criteriaBuilder.and(
//                    criteriaBuilder.equal(root.get(attributeName),
//                            attributeValue),
//                    criteriaBuilder.notEqual(
//                            root.get(BaseEntity.ID_PROPERTY_NAME), id)));
//        }
//        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
//        
//        return (int) query.getSingleResult();
//    }
//    
//    /**
//     * @param condition
//     * @return
//     */
//    @Override
//    public T find(T condition) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//    
//    public void clear() {
//        entityManager.clear();
//    }
//    
//    public void flush() {
//        entityManager.flush();
//    }
//}
