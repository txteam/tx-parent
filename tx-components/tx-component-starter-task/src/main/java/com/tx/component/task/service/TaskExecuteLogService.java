package com.tx.component.task.service;

import java.util.List;
import java.util.Map;

import com.tx.component.task.model.TaskExecuteLog;
import com.tx.core.paged.model.PagedList;

public interface TaskExecuteLogService {
    
    /**
     * 将taskExecuteLog实例插入数据库中保存
     * 1、如果taskExecuteLog 为空时抛出参数为空异常
     * 2、如果taskExecuteLog 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param taskExecuteLog [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    void insert(TaskExecuteLog taskExecuteLog);
    
    /**
     * 根据id删除taskExecuteLog实例
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
     * 根据Id查询TaskExecuteLog实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TaskExecuteLog [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    TaskExecuteLog findById(String id);
    
    /**
     * 查询TaskExecuteLog实体列表
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<TaskExecuteLog> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<TaskExecuteLog> queryList(Map<String, Object> params);
    
    /**
     * 分页查询TaskExecuteLog实体列表
     * <功能详细描述>
      * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TaskExecuteLog> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    PagedList<TaskExecuteLog> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize);
    
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
      * @param taskExecuteLog
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean updateById(TaskExecuteLog taskExecuteLog);
    
}