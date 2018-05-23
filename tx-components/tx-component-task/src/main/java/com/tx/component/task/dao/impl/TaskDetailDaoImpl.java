/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年11月2日
 * <修改描述:>
 */
package com.tx.component.task.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.task.dao.TaskDetailDao;
import com.tx.component.task.model.TaskDetail;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
  * 任务详情持久层<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年11月2日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TaskDetailDaoImpl implements TaskDetailDao {
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public TaskDetailDaoImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskDetailDaoImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TaskDetail find(TaskDetail condition) {
        return this.myBatisDaoSupport
                .<TaskDetail> find("taskDetail.findTaskDetail", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TaskDetail> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .<TaskDetail> queryList("taskDetail.queryTaskDetail", params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<TaskDetail> queryList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<TaskDetail> queryList(
                "taskDetail.queryTaskDetail", params, orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport
                .<Integer> find("taskDetail.queryTaskDetailCount", params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TaskDetail> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<TaskDetail> queryPagedList(
                "taskDetail.queryTaskDetail", params, pageIndex, pageSize);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param orderList
     * @return
     */
    @Override
    public PagedList<TaskDetail> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<TaskDetail> queryPagedList(
                "taskDetail.queryTaskDetail",
                params,
                pageIndex,
                pageSize,
                orderList);
    }
    
}
