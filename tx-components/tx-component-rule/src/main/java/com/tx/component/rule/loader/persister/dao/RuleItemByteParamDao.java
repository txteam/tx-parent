/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.rule.loader.persister.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.rule.loader.RuleItemByteParam;

/**
 * RuleItemByteParam持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleItemByteParamDao {
    
    /**
      * 批量插入
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchInsertRuleItemByteParam(List<RuleItemByteParam> condition);
    
    /**
      * 删除RuleItemByteParam对象
      * 1、auto generate
      * 2、根据入参条件进行删除
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int deleteRuleItemByteParam(RuleItemByteParam condition);
    
    /**
      * 根据条件查询RuleItemByteParam列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<RuleItemByteParam> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<RuleItemByteParam> queryRuleItemByteParamList(Map<String, Object> params);
}
