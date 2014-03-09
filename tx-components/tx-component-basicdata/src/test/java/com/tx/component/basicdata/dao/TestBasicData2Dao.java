/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.dao;

import java.util.List;
import java.util.Map;

import com.tx.core.mybatis.model.Order;
import com.tx.core.paged.model.PagedList;
import com.tx.component.basicdata.model.TestBasicData2;

/**
 * TestBasicData2持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TestBasicData2Dao {
    
    /**
      * 插入TestBasicData2对象实体
      * 1、auto generate
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insertTestBasicData2(TestBasicData2 condition);
    
    /**
      * 删除TestBasicData2对象
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
    public int deleteTestBasicData2(TestBasicData2 condition);
    
    /**
      * 查询TestBasicData2实体
      * auto generate
      * <功能详细描述>
      * @param condition
      * @return [参数说明]
      * 
      * @return TestBasicData2 [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public TestBasicData2 findTestBasicData2(TestBasicData2 condition);
    
    /**
      * 根据条件查询TestBasicData2列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<TestBasicData2> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<TestBasicData2> queryTestBasicData2List(Map<String, Object> params);
    
    /**
      * 根据指定查询条件以及排序列查询TestBasicData2列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param orderList
      * @return [参数说明]
      * 
      * @return List<TestBasicData2> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<TestBasicData2> queryTestBasicData2List(Map<String, Object> params,
            List<Order> orderList);
    
    /**
      * 根据条件查询TestBasicData2列表总数
      * auto generated
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int countTestBasicData2(Map<String, Object> params);
    
    /**
      * 分页查询TestBasicData2列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return PagedList<TestBasicData2> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<TestBasicData2> queryTestBasicData2PagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
    /**
      * 分页查询TestBasicData2列表，传入排序字段
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @param orderList
      * @return [参数说明]
      * 
      * @return PagedList<TestBasicData2> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<TestBasicData2> queryTestBasicData2PagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList);
    
    
    /**
      * 更新TestBasicData2实体，
      * auto generate
      * 1、传入TestBasicData2中主键不能为空
      * <功能详细描述>
      * @param updateTestBasicData2RowMap
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int updateTestBasicData2(Map<String, Object> updateRowMap);
}
