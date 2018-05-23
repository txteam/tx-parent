/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.task.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.task.dao.TaskExecuteLogDao;
import com.tx.component.task.model.TaskExecuteLog;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * TaskExecuteLog持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskExecuteLogDaoImpl implements TaskExecuteLogDao {
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public TaskExecuteLogDaoImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskExecuteLogDaoImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<TaskExecuteLog> condition) {
        this.myBatisDaoSupport.batchInsertUseUUID(
                "taskExecuteLog.insertTaskExecuteLog", condition, "id", true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateRowMapList) {
        this.myBatisDaoSupport.batchUpdate(
                "taskExecuteLog.updateTaskExecuteLog", updateRowMapList, true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(TaskExecuteLog condition) {
        this.myBatisDaoSupport.insertUseUUID(
                "taskExecuteLog.insertTaskExecuteLog", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(TaskExecuteLog condition) {
        return this.myBatisDaoSupport
                .delete("taskExecuteLog.deleteTaskExecuteLog", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TaskExecuteLog find(TaskExecuteLog condition) {
        return this.myBatisDaoSupport.<TaskExecuteLog> find(
                "taskExecuteLog.findTaskExecuteLog", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TaskExecuteLog> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<TaskExecuteLog> queryList(
                "taskExecuteLog.queryTaskExecuteLog", params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<TaskExecuteLog> queryList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<TaskExecuteLog> queryList(
                "taskExecuteLog.queryTaskExecuteLog", params, orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find(
                "taskExecuteLog.queryTaskExecuteLogCount", params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TaskExecuteLog> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<TaskExecuteLog> queryPagedList(
                "taskExecuteLog.queryTaskExecuteLog",
                params,
                pageIndex,
                pageSize);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param orderList
     * @return
     */
    @Override
    public PagedList<TaskExecuteLog> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<TaskExecuteLog> queryPagedList(
                "taskExecuteLog.queryTaskExecuteLog",
                params,
                pageIndex,
                pageSize,
                orderList);
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport
                .update("taskExecuteLog.updateTaskExecuteLog", updateRowMap);
    }
}
