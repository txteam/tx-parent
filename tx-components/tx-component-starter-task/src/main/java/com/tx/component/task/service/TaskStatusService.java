package com.tx.component.task.service;

import java.util.List;
import java.util.Map;

import com.tx.component.task.model.TaskStatus;
import com.tx.core.paged.model.PagedList;

public interface TaskStatusService {
    
    /**
     * 将taskDetail实例插入数据库中保存
     * 1、如果taskDetail 为空时抛出参数为空异常
     * 2、如果taskDetail 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param taskDetail [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    void insert(TaskStatus taskDetail);
    
    /**
     * 根据id删除taskDetail实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param id
     * @return boolean 删除的条数>0则为true [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    boolean deleteById(String id);
    
    /**
     * 根据Id查询TaskStatus实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TaskStatus [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    TaskStatus findById(String id);
    
    /**
     * 根据Id查询TaskStatus实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TaskStatus [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    TaskStatus findByTaskId(String taskId);
    
    /**
     * 根据Id查询TaskStatus实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TaskStatus [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    TaskStatus findAndlockByTaskId(String taskId);
    
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
    List<TaskStatus> queryList(Map<String, Object> params);
    
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
    PagedList<TaskStatus> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize);
    
    /**
     * 判断是否已经存在<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    boolean isExist(Map<String, String> key2valueMap, String excludeId);
    
    /**
      * 根据id更新对象
      * <功能详细描述>
      * @param taskDetail
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean updateById(TaskStatus taskDetail);
    
}