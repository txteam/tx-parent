/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.task.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.task.dao.TaskStatusDao;
import com.tx.component.task.model.TaskStatus;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * TaskStatus持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskStatusDaoImpl implements TaskStatusDao {
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public TaskStatusDaoImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskStatusDaoImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<TaskStatus> condition) {
        this.myBatisDaoSupport.batchInsertUseUUID("taskStatus.insertTaskStatus",
                condition,
                "id",
                true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateRowMapList) {
        this.myBatisDaoSupport.batchUpdate("taskStatus.updateTaskStatus",
                updateRowMapList,
                true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(TaskStatus condition) {
        this.myBatisDaoSupport.insertUseUUID("taskStatus.insertTaskStatus",
                condition,
                "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(TaskStatus condition) {
        return this.myBatisDaoSupport.delete("taskStatus.deleteTaskStatus",
                condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TaskStatus find(TaskStatus condition) {
        return this.myBatisDaoSupport
                .<TaskStatus> find("taskStatus.findTaskStatus", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TaskStatus findAndlock(TaskStatus condition) {
        return this.myBatisDaoSupport.<TaskStatus> find(
                "taskStatus.findAndlockTaskStatus", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TaskStatus> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .<TaskStatus> queryList("taskStatus.queryTaskStatus", params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<TaskStatus> queryList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<TaskStatus> queryList(
                "taskStatus.queryTaskStatus", params, orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .<Integer> find("taskStatus.queryTaskStatusCount", params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TaskStatus> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<TaskStatus> queryPagedList(
                "taskStatus.queryTaskStatus", params, pageIndex, pageSize);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param orderList
     * @return
     */
    @Override
    public PagedList<TaskStatus> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<TaskStatus> queryPagedList(
                "taskStatus.queryTaskStatus",
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
        return this.myBatisDaoSupport.update("taskStatus.updateTaskStatus",
                updateRowMap);
    }
    
}
