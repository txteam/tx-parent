/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.rule.loader.persister.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.rule.loader.RuleItem;

/**
 * SimplePersistenceRule持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleItemDao {
    
    /**
      * 插入RuleItem对象实体
      * 1、auto generate
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insertRuleItem(RuleItem condition);
    
    /**
      * 删除RuleItem对象
      * 1、auto generate
      * 2、根据入参条件进行删除
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int deleteRuleItem(RuleItem condition);
    
    /**
      * 查询RuleItem实体
      * auto generate
      * <功能详细描述>
      * @param condition
      * @return [参数说明]
      * 
      * @return SimplePersistenceRule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleItem findRuleItem(RuleItem condition);
    
    /**
      * 根据条件查询RuleItem列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<SimplePersistenceRule> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<RuleItem> queryRuleItemList(Map<String, Object> params);
    
    /**
      * 更新RuleItem实体，
      * auto generate
      * 1、传入SimplePersistenceRule中主键不能为空
      * <功能详细描述>
      * @param updateSimplePersistenceRuleRowMap
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
      */
    public int updateRuleItem(Map<String, Object> updateRowMap);
}
