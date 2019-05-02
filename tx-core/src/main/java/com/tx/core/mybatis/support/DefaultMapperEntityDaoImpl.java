/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月9日
 * <修改描述:>
 */
package com.tx.core.mybatis.support;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * 默认的实体自动持久层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultMapperEntityDaoImpl<T> implements MapperEntityDao<T> {
    
    /** bean类型 */
    private Class<T> beanType;
    
    /** mybatis句柄 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** 实体Mpper构建辅助类 */
    private EntityMapperBuilderAssistant assistant;
    
    /** <默认构造函数> */
    public DefaultMapperEntityDaoImpl(Class<T> beanType,
            MyBatisDaoSupport myBatisDaoSupport,
            EntityMapperBuilderAssistant assistant) {
        super();
        AssertUtils.notNull(beanType, "beanType is null.");
        AssertUtils.notNull(myBatisDaoSupport, "myBatisDaoSupport is null.");
        AssertUtils.notNull(assistant, "assistant is null.");
        
        this.beanType = beanType;
        this.myBatisDaoSupport = myBatisDaoSupport;
        this.assistant = assistant;
    }
    
    /**
     * @return
     */
    @Override
    public Type getEntityType() {
        return this.beanType;
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<T> objectList) {
        List<String> primaryPropertyList = this.assistant
                .getPrimaryProperyNameList();
        if (CollectionUtils.isEmpty(primaryPropertyList)
                || primaryPropertyList.size() > 1) {
            this.myBatisDaoSupport.batchInsert(
                    this.assistant.getInsertStatementName(), objectList, true);
        } else {
            this.myBatisDaoSupport.batchInsertUseUUID(
                    this.assistant.getInsertStatementName(),
                    objectList,
                    primaryPropertyList.get(0),
                    true);
        }
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(T condition) {
        List<String> primaryPropertyList = this.assistant
                .getPrimaryProperyNameList();
        if (CollectionUtils.isEmpty(primaryPropertyList)
                || primaryPropertyList.size() > 1) {
            this.myBatisDaoSupport
                    .insert(this.assistant.getInsertStatementName(), condition);
        } else {
            this.myBatisDaoSupport.insertUseUUID(
                    this.assistant.getInsertStatementName(),
                    condition,
                    primaryPropertyList.get(0));
        }
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(T condition) {
        return this.myBatisDaoSupport
                .delete(this.assistant.getDeleteStatementName(), condition);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchDelete(List<T> condition) {
        this.myBatisDaoSupport.batchDelete(
                this.assistant.getDeleteStatementName(), condition, true);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public T find(T condition) {
        return this.myBatisDaoSupport
                .find(this.assistant.getFindStatementName(), condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<T> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .queryList(this.assistant.getQueryStatementName(), params);
    }
    
//    /**
//     * @param params
//     * @param orderList
//     * @return
//     */
//    @Override
//    public List<T> queryList(Map<String, Object> params,
//            List<Order> orderList) {
//        return this.myBatisDaoSupport.queryList(
//                this.assistant.getQueryStatementName(), params, orderList);
//    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .count(this.assistant.getCountStatmentName(), params);
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
        return this.myBatisDaoSupport.queryPagedList(
                this.assistant.getQueryStatementName(),
                params,
                pageIndex,
                pageSize);
    }
    
//    /**
//     * @param params
//     * @param pageIndex
//     * @param pageSize
//     * @param orderList
//     * @return
//     */
//    @Override
//    public PagedList<T> queryPagedList(Map<String, Object> params,
//            int pageIndex, int pageSize, List<Order> orderList) {
//        return this.myBatisDaoSupport.queryPagedList(
//                this.assistant.getQueryStatementName(),
//                params,
//                pageIndex,
//                pageSize,
//                orderList);
//    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport
                .update(this.assistant.getUpdateStatementName(), updateRowMap);
    }
    
    /**
     * @param updateRowMapList
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateRowMapList) {
        this.myBatisDaoSupport.batchUpdate(
                this.assistant.getUpdateStatementName(),
                updateRowMapList,
                true);
    }
}
