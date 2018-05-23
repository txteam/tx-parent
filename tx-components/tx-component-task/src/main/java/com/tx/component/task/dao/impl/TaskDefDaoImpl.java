/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.task.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.task.dao.TaskDefDao;
import com.tx.component.task.model.TaskDef;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * TaskDef持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskDefDaoImpl implements TaskDefDao {
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public TaskDefDaoImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskDefDaoImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<TaskDef> condition) {
        this.myBatisDaoSupport.batchInsertUseUUID("taskDef.insertTaskDef",
                condition,
                "id",
                true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateRowMapList) {
        this.myBatisDaoSupport.batchUpdate("taskDef.updateTaskDef",
                updateRowMapList,
                true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(TaskDef condition) {
        this.myBatisDaoSupport.insertUseUUID("taskDef.insertTaskDef",
                condition,
                "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(TaskDef condition) {
        return this.myBatisDaoSupport.delete("taskDef.deleteTaskDef",
                condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TaskDef findAndlock(TaskDef condition) {
        return this.myBatisDaoSupport
                .<TaskDef> find("taskDef.findAndlockTaskDef", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TaskDef find(TaskDef condition) {
        return this.myBatisDaoSupport.<TaskDef> find("taskDef.findTaskDef",
                condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TaskDef> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .<TaskDef> queryList("taskDef.queryTaskDef", params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<TaskDef> queryList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport
                .<TaskDef> queryList("taskDef.queryTaskDef", params, orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .<Integer> find("taskDef.queryTaskDefCount", params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TaskDef> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<TaskDef> queryPagedList(
                "taskDef.queryTaskDef", params, pageIndex, pageSize);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param orderList
     * @return
     */
    @Override
    public PagedList<TaskDef> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<TaskDef> queryPagedList(
                "taskDef.queryTaskDef", params, pageIndex, pageSize, orderList);
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("taskDef.updateTaskDef",
                updateRowMap);
    }
}
