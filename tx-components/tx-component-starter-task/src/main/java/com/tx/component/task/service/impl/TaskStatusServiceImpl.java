/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.task.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.task.dao.TaskStatusDao;
import com.tx.component.task.model.TaskStatus;
import com.tx.component.task.service.TaskStatusService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * TaskStatus的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskStatusServiceImpl implements TaskStatusService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory
            .getLogger(TaskStatusServiceImpl.class);
    
    private TaskStatusDao taskStatusDao;
    
    /** <默认构造函数> */
    public TaskStatusServiceImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskStatusServiceImpl(TaskStatusDao taskStatusDao) {
        super();
        this.taskStatusDao = taskStatusDao;
    }
    
    /**
     * @param taskStatus
     */
    @Override
    @Transactional
    public void insert(TaskStatus taskStatus) {
        //验证参数是否合法
        AssertUtils.notNull(taskStatus, "taskStatus is null.");
        AssertUtils.notEmpty(taskStatus.getTaskId(),
                "taskStatus.taskId is empty.");
        AssertUtils.notNull(taskStatus.getStatus(),
                "taskStatus.status is null.");
        
        //为添加的数据需要填入默认值的字段填入默认值
        Date now = new Date();
        taskStatus.setLastUpdateDate(now);
        taskStatus.setCreateDate(now);
        
        //调用数据持久层对实体进行持久化操作
        this.taskStatusDao.insert(taskStatus);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TaskStatus condition = new TaskStatus();
        condition.setId(id);
        int resInt = this.taskStatusDao.delete(condition);
        
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public TaskStatus findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TaskStatus condition = new TaskStatus();
        condition.setId(id);
        
        TaskStatus res = this.taskStatusDao.find(condition);
        return res;
    }
    
    /**
     * @param taskId
     * @return
     */
    @Override
    public TaskStatus findByTaskId(String taskId) {
        AssertUtils.notEmpty(taskId, "taskId is empty.");
        
        TaskStatus condition = new TaskStatus();
        condition.setTaskId(taskId);
        
        TaskStatus res = this.taskStatusDao.find(condition);
        return res;
    }
    
    /**
     * @param taskId
     * @return
     */
    @Override
    @Transactional
    public TaskStatus findAndlockByTaskId(String taskId) {
        AssertUtils.notEmpty(taskId, "taskId is empty.");
        
        TaskStatus condition = new TaskStatus();
        condition.setTaskId(taskId);
        
        TaskStatus res = this.taskStatusDao.findAndlock(condition);
        return res;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TaskStatus> queryList(Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TaskStatus> resList = this.taskStatusDao.queryList(params);
        
        return resList;
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
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TaskStatus> resPagedList = this.taskStatusDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * @param key2valueMap
     * @param excludeId
     * @return
     */
    @Override
    public boolean isExist(Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("excludeId", excludeId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.taskStatusDao.count(params);
        
        return res > 0;
    }
    
    /**
     * @param taskStatus
     * @return
     */
    @Override
    @Transactional
    public boolean updateById(TaskStatus taskStatus) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(taskStatus, "taskStatus is null.");
        AssertUtils.notEmpty(taskStatus.getId(), "taskStatus.id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", taskStatus.getId());
        
        //需要更新的字段
        updateRowMap.put("status", taskStatus.getStatus());
        updateRowMap.put("result", taskStatus.getResult());
        updateRowMap.put("consuming", taskStatus.getConsuming());
        updateRowMap.put("startDate", taskStatus.getStartDate());
        updateRowMap.put("endDate", taskStatus.getEndDate());
        updateRowMap.put("signature", taskStatus.getSignature());
        
        updateRowMap.put("successStartDate", taskStatus.getSuccessStartDate());
        updateRowMap.put("successEndDate", taskStatus.getSuccessEndDate());
        updateRowMap.put("successConsuming", taskStatus.getSuccessConsuming());
        updateRowMap.put("successCount", taskStatus.getSuccessCount());
        updateRowMap.put("failStartDate", taskStatus.getFailStartDate());
        updateRowMap.put("failEndDate", taskStatus.getFailEndDate());
        updateRowMap.put("failConsuming", taskStatus.getFailConsuming());
        updateRowMap.put("failCount", taskStatus.getFailCount());
        
        updateRowMap.put("executeCount", taskStatus.getExecuteCount());
        
        updateRowMap.put("attributes", taskStatus.getAttributes());
        updateRowMap.put("nextFireDate", taskStatus.getNextFireDate());
        
        updateRowMap.put("lastUpdateDate", new Date());
        
        int updateRowCount = this.taskStatusDao.update(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
