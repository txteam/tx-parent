/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.rule.loader.persister.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.rule.loader.RuleItemValueParam;

/**
 * RuleItemValueParam持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleItemValueParamDao {

    /**
      * 批量插入RuleItemValueParam对象实体
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchInsertRuleItemValueParam(List<RuleItemValueParam> condition);
    
    /**
      * 删除RuleItemValueParam对象
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
    public int deleteRuleItemValueParam(RuleItemValueParam condition);
    
    /**
      * 根据条件查询RuleItemValueParam列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<RuleItemValueParam> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<RuleItemValueParam> queryRuleItemValueParamList(Map<String, Object> params);
}
