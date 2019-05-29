/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.generator2test.dao;

import java.util.List;
import java.util.Map;

import com.tx.core.paged.model.PagedList;
import com.tx.generator2test.model.TestMode;

/**
 * TestMode持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TestModeDao {

    /**
     * 插入TestMode对象实体
     * 1、auto generate
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insert(TestMode testMode);
    
    /**
     * 批量插入TestMode对象实体
     * 1、auto generate
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void batchInsert(List<TestMode> testModeList); 
    
    /**
     * 删除TestMode对象
     * 1、auto generate
     * 2、根据入参条件进行删除
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public int delete(TestMode testMode);
    
    /**
     * 更新TestMode实体，
     * auto generate
     * 1、传入TestMode中主键不能为空
     * <功能详细描述>
     * @param updateTestModeRowMap
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public int update(Map<String, Object> updateRowMap);
    
    /**
     * 批量更新TestMode实体，
     * auto generate
     * 1、传入TestMode中主键不能为空
     * <功能详细描述>
     * @param updateTestModeRowMap
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void batchUpdate(List<Map<String,Object>> updateRowMapList);
    
    /**
     * 查询TestMode实体
     * auto generate
     * <功能详细描述>
     * @param condition
     * @return [参数说明]
     * 
     * @return TestMode [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public TestMode find(TestMode testMode);
    
    /**
     * 根据条件查询TestMode列表
     * auto generate
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<TestMode> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<TestMode> queryList(Map<String, Object> params);
    
    /**
     * 根据条件查询TestMode列表总数
     * auto generated
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public int count(Map<String, Object> params);
    
    /**
     * 分页查询TestMode列表
     * auto generate
     * <功能详细描述>
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<TestMode> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<TestMode> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
}
