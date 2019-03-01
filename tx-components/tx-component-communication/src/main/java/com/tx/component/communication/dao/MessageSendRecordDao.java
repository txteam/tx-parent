/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.communication.dao;

import com.github.pagehelper.Page;
import com.tx.component.communication.model.MessageSendRecord;
import com.tx.core.mybatis.model.Order;
import com.tx.core.paged.model.PagedList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;
import java.util.Map;

/**
 * MessageSendRecord持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//@Mapper
public interface MessageSendRecordDao {
    
    /**
      * 插入MessageSendRecord对象实体
      * 1、auto generate
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insertMessageSendRecord(MessageSendRecord condition);
    
    /**
      * 删除MessageSendRecord对象
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
    public int deleteMessageSendRecord(MessageSendRecord condition);
    
    /**
      * 查询MessageSendRecord实体
      * auto generate
      * <功能详细描述>
      * @param condition
      * @return [参数说明]
      * 
      * @return MessageSendRecord [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public MessageSendRecord findMessageSendRecord(MessageSendRecord condition);
    
    /**
      * 根据条件查询MessageSendRecord列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<MessageSendRecord> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<MessageSendRecord> queryMessageSendRecord(Map<String, Object> params);
    
    /**
      * 根据指定查询条件以及排序列查询MessageSendRecord列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param orderList
      * @return [参数说明]
      * 
      * @return List<MessageSendRecord> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
//    public List<MessageSendRecord> queryMessageSendRecord(Map<String, Object> params,
//                                                          List<Order> orderList);
    
    /**
      * 根据条件查询MessageSendRecord列表总数
      * auto generated
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int queryMessageSendRecordCount(Map<String, Object> params);
    

    

    
    /**
      * 更新MessageSendRecord实体，
      * auto generate
      * 1、传入MessageSendRecord中主键不能为空
      * <功能详细描述>
      * @param updateMessageSendRecordRowMap
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int updateMessageSendRecord(Map<String, Object> updateRowMap);
}
