package com.tx.component.task.service;

import java.util.List;
import java.util.Map;

import com.tx.component.task.model.TaskDetail;
import com.tx.core.paged.model.PagedList;

public interface TaskDetailService {
    
    /**
     * 根据Id查询TaskStatus实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TaskStatus [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    TaskDetail findByTaskCode(String taskCode);
    
    /**
     * 根据Id查询TaskStatus实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TaskStatus [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    TaskDetail findByTaskId(String taskId);
    
    /**
     * 查询TaskStatus实体列表
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<TaskStatus> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<TaskDetail> queryList(Map<String, Object> params);
    
    /**
     * 分页查询TaskStatus实体列表
     * <功能详细描述>
      * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TaskStatus> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    PagedList<TaskDetail> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize);
    
}