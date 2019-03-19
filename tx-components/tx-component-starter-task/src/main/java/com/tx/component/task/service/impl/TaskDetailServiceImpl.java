/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年11月2日
 * <修改描述:>
 */
package com.tx.component.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.task.dao.TaskDetailDao;
import com.tx.component.task.model.TaskDetail;
import com.tx.component.task.service.TaskDetailService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * 任务详情业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年11月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskDetailServiceImpl implements TaskDetailService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory
            .getLogger(TaskDetailServiceImpl.class);
    
    private TaskDetailDao taskDetailDao;
    
    /** <默认构造函数> */
    public TaskDetailServiceImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskDetailServiceImpl(TaskDetailDao taskDetailDao) {
        super();
        this.taskDetailDao = taskDetailDao;
    }
    
    /**
     * @param taskCode
     * @return
     */
    @Override
    public TaskDetail findByTaskCode(String taskCode) {
        AssertUtils.notEmpty(taskCode, "taskCode is empty.");
        
        TaskDetail condition = new TaskDetail();
        condition.setCode(taskCode);
        
        TaskDetail res = this.taskDetailDao.find(condition);
        return res;
    }
    
    /**
     * @param taskId
     * @return
     */
    @Override
    public TaskDetail findByTaskId(String taskId) {
        AssertUtils.notEmpty(taskId, "taskId is empty.");
        
        TaskDetail condition = new TaskDetail();
        condition.setTaskId(taskId);
        
        TaskDetail res = this.taskDetailDao.find(condition);
        return res;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TaskDetail> queryList(Map<String, Object> params) {
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TaskDetail> resList = this.taskDetailDao.queryList(params);
        
        return resList;
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
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TaskDetail> resPagedList = this.taskDetailDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
}
