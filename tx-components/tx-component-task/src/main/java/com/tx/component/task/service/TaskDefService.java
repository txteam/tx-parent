package com.tx.component.task.service;

import java.util.List;
import java.util.Map;

import com.tx.component.task.model.TaskDef;
import com.tx.core.paged.model.PagedList;

public interface TaskDefService {
    
    /**
     * 将taskDef实例插入数据库中保存
     * 1、如果taskDef 为空时抛出参数为空异常
     * 2、如果taskDef 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param taskDef [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    void insert(TaskDef taskDef);
    
    /**
     * 根据id删除taskDef实例
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
     * 根据Id查询TaskDef实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TaskDef [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    TaskDef findAndlockById(String id);
    
    /**
     * 根据Id查询TaskDef实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TaskDef [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    TaskDef findById(String id);
    
    /**
     * 根据Id查询TaskDef实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TaskDef [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    TaskDef findByCode(String code);
    
    /**
     * 查询TaskDef实体列表
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<TaskDef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<TaskDef> queryList(Map<String, Object> params);
    
    /**
     * 分页查询TaskDef实体列表
     * <功能详细描述>
      * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TaskDef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    PagedList<TaskDef> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize);
    
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
      * @param taskDef
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean updateById(TaskDef taskDef);
    
}