/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年11月2日
 * <修改描述:>
 */
package com.tx.component.task.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.task.model.TaskDetail;
import com.tx.core.mybatis.model.Order;
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
public interface TaskDetailDao {
    
    /**
     * 查询TaskDetail实体
     * auto generate
     * <功能详细描述>
     * @param condition
     * @return [参数说明]
     * 
     * @return TaskDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public TaskDetail find(TaskDetail condition);
    
    /**
     * 根据条件查询TaskDetail列表
     * auto generate
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<TaskDetail> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<TaskDetail> queryList(Map<String, Object> params);
    
    /**
     * 根据指定查询条件以及排序列查询TaskDetail列表
     * auto generate
     * <功能详细描述>
     * @param params
     * @param orderList
     * @return [参数说明]
     * 
     * @return List<TaskDetail> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<TaskDetail> queryList(Map<String, Object> params, List<Order> orderList);
    
    /**
     * 根据条件查询TaskDetail列表总数
     * auto generated
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Map<String, Object> params);
    
    /**
     * 分页查询TaskDetail列表
     * auto generate
     * <功能详细描述>
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<TaskDetail> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<TaskDetail> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize);
    
    /**
     * 分页查询TaskDetail列表，传入排序字段
     * auto generate
     * <功能详细描述>
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param orderList
     * @return [参数说明]
     * 
     * @return PagedList<TaskDetail> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<TaskDetail> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize,
            List<Order> orderList);
}
