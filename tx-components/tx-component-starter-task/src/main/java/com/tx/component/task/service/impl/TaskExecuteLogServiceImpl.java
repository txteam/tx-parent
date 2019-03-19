/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.task.dao.TaskExecuteLogDao;
import com.tx.component.task.model.TaskExecuteLog;
import com.tx.component.task.service.TaskExecuteLogService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * TaskExecuteLog的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskExecuteLogServiceImpl implements TaskExecuteLogService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory
            .getLogger(TaskExecuteLogServiceImpl.class);
    
    private TaskExecuteLogDao taskExecuteLogDao;
    
    /** <默认构造函数> */
    public TaskExecuteLogServiceImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskExecuteLogServiceImpl(TaskExecuteLogDao taskExecuteLogDao) {
        super();
        this.taskExecuteLogDao = taskExecuteLogDao;
    }
    
    /**
     * @param taskExecuteLog
     */
    @Override
    @Transactional
    public void insert(TaskExecuteLog taskExecuteLog) {
        //验证参数是否合法
        AssertUtils.notNull(taskExecuteLog, "taskExecuteLog is null.");
        AssertUtils.notEmpty(taskExecuteLog.getTaskId(),
                "taskExecuteLog.taskId is empty.");
        AssertUtils.notEmpty(taskExecuteLog.getCode(),
                "taskExecuteLog.code is empty.");
        AssertUtils.notEmpty(taskExecuteLog.getName(),
                "taskExecuteLog.name is empty.");
        AssertUtils.notNull(taskExecuteLog.getResult(),
                "taskExecuteLog.result is null.");
        
        //为添加的数据需要填入默认值的字段填入默认值
        
        //调用数据持久层对实体进行持久化操作
        this.taskExecuteLogDao.insert(taskExecuteLog);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TaskExecuteLog condition = new TaskExecuteLog();
        condition.setId(id);
        int resInt = this.taskExecuteLogDao.delete(condition);
        
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public TaskExecuteLog findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TaskExecuteLog condition = new TaskExecuteLog();
        condition.setId(id);
        
        TaskExecuteLog res = this.taskExecuteLogDao.find(condition);
        return res;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TaskExecuteLog> queryList(Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TaskExecuteLog> resList = this.taskExecuteLogDao.queryList(params);
        
        return resList;
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
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TaskExecuteLog> resPagedList = this.taskExecuteLogDao
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
        int res = this.taskExecuteLogDao.count(params);
        
        return res > 0;
    }
    
    /**
     * @param taskExecuteLog
     * @return
     */
    @Override
    @Transactional
    public boolean updateById(TaskExecuteLog taskExecuteLog) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(taskExecuteLog, "taskExecuteLog is null.");
        AssertUtils.notEmpty(taskExecuteLog.getId(),
                "taskExecuteLog.id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", taskExecuteLog.getId());
        
        //需要更新的字段
        updateRowMap.put("consuming", taskExecuteLog.getConsuming());
        updateRowMap.put("result", taskExecuteLog.getResult());
        updateRowMap.put("remark", taskExecuteLog.getRemark());
        updateRowMap.put("name", taskExecuteLog.getName());
        int updateRowCount = this.taskExecuteLogDao.update(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
}
