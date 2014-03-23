/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-20
 * <修改描述:>
 */
package com.tx.component.rule.loader.persister.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleItemByteParam;
import com.tx.component.rule.loader.RuleItemValueParam;
import com.tx.component.rule.loader.persister.dao.RuleItemByteParamDao;
import com.tx.component.rule.loader.persister.dao.RuleItemDao;
import com.tx.component.rule.loader.persister.dao.RuleItemValueParamDao;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 规则项处理业务层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleItemService{
    
    @Resource(name = "ruleItemDao")
    private RuleItemDao ruleItemDao;
    
    @Resource(name = "ruleItemByteParamDao")
    private RuleItemByteParamDao ruleItemByteParamDao;
    
    @Resource(name = "ruleItemValueParamDao")
    private RuleItemValueParamDao ruleItemValueParamDao;
    
    
    
    /**
     * 装在规则参数
     * <功能详细描述>
     * @param rule [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void setupRuleItemParam(RuleItem ruleItem) {
        if (ruleItem == null) {
            return;
        }
        //如果参数为空，装载参数
        AssertUtils.notEmpty(ruleItem.getKey(), "ruleItem.key is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleKey", ruleItem.getKey());
        ruleItem.addByteParamList(this.ruleItemByteParamDao.queryRuleItemByteParamList(params));
        ruleItem.addValueParamList(this.ruleItemValueParamDao.queryRuleItemValueParamList(params));
    }
    
    /**
     * 根据RuleType查询SimplePersistenceRule实体列表
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<SimplePersistenceRule> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public List<RuleItem> listRuleItem() {
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<RuleItem> resList = this.ruleItemDao.queryRuleItemList(params);
        
        //如果列表为空直接返回
        if (resList == null) {
            return resList;
        }
        
        //列表不为空，设置参数集合，以及查询对应的参数值
        for (RuleItem ruleItemTemp : resList) {
            //装载参数
            setupRuleItemParam(ruleItemTemp);
        }
        return resList;
    }
    
    /**
      * 根据规则主键查询规则实体
      * <功能详细描述>
      * @param ruleType
      * @param serviceType
      * @param rule
      * @return [参数说明]
      * 
      * @return SimplePersistenceRule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleItem findRuleItemByKey(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        RuleItem condition = new RuleItem();
        condition.setKey(key);
        
        RuleItem res = this.ruleItemDao.findRuleItem(condition);
        
        if (res != null) {
            setupRuleItemParam(res);
        }
        return res;
    }
    
    /**
      * 将simplePersistenceRule实例插入数据库中保存
      * 1、如果simplePersistenceRule为空时抛出参数为空异常
      * 2、如果simplePersistenceRule中部分必要参数为非法值时抛出参数不合法异常
      * <功能详细描述>
      * @param rule [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void saveRuleItem(RuleItem ruleItem) {
        //验证参数是否合法
        AssertUtils.notNull(ruleItem, "ruleItem is null.");
        AssertUtils.notEmpty(ruleItem.getKey(), "ruleItem.key is empty.");
        AssertUtils.notNull(ruleItem.getRuleType(),
                "ruleItem.ruleType is empty.");
        AssertUtils.notEmpty(ruleItem.getServiceType(),
                "ruleItem.serviceType is empty.");
        
        //查询对应规则，是否已经存在
        RuleItem srcRuleItem = findRuleItemByKey(ruleItem.getKey());
        
        if (ruleItem.getRemark() != null
                && ruleItem.getRemark().length() > 1000) {
            ruleItem.setRemark(ruleItem.getRemark().substring(0, 1000) + "...");
        }
        
        Date now = new Date();
        if (srcRuleItem == null) {
            ruleItem.setCreateDate(now);
            ruleItem.setLastUpdateDate(now);
            ruleItem.setModifyAble(true);
            this.ruleItemDao.insertRuleItem(ruleItem);
        } else {
            Map<String, Object> updateRowMap = new HashMap<String, Object>();
            updateRowMap.put("key", srcRuleItem.getKey());
            
            updateRowMap.put("state", ruleItem.getState());
            updateRowMap.put("name", ruleItem.getName());
            updateRowMap.put("remark", ruleItem.getRemark());
            updateRowMap.put("serviceType", ruleItem.getServiceType());
            this.ruleItemDao.updateRuleItem(updateRowMap);
            
            RuleItemByteParam byteParamCondition = new RuleItemByteParam();
            byteParamCondition.setRuleKey(ruleItem.getKey());
            this.ruleItemByteParamDao.deleteRuleItemByteParam(byteParamCondition);
            RuleItemValueParam valueParamCondition = new RuleItemValueParam();
            valueParamCondition.setRuleKey(ruleItem.getKey());
            this.ruleItemValueParamDao.deleteRuleItemValueParam(valueParamCondition);
        }
        
        //设置属性
        if (!MapUtils.isEmpty(ruleItem.getByteParamMultiValueMap())) {
            List<RuleItemByteParam> addByteList = new ArrayList<RuleItemByteParam>();
            for (Entry<String, List<RuleItemByteParam>> entryTemp : ruleItem.getByteParamMultiValueMap()
                    .entrySet()) {
                addByteList.addAll(entryTemp.getValue());
            }
            this.ruleItemByteParamDao.batchInsertRuleItemByteParam(addByteList);
        }
        //装载参数值
        if (!MapUtils.isEmpty(ruleItem.getValueParamMultiValueMap())) {
            List<RuleItemValueParam> addValueList = new ArrayList<RuleItemValueParam>();
            for (Entry<String, List<RuleItemValueParam>> entryTemp : ruleItem.getValueParamMultiValueMap()
                    .entrySet()) {
                addValueList.addAll(entryTemp.getValue());
            }
            this.ruleItemValueParamDao.batchInsertRuleItemValueParam(addValueList);
        }
    }
    
    /**
      * 根据key删除ruleItem实例
      * 1、如果入参数为空，则抛出异常
      * 2、执行删除后，将返回数据库中被影响的条数
      * @param id
      * @return 返回删除的数据条数，<br/>
      * 有些业务场景，如果已经被别人删除同样也可以认为是成功的
      * 这里讲通用生成的业务层代码定义为返回影响的条数
      * @return int [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteByKey(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        RuleItem condition = new RuleItem();
        condition.setKey(key);
        
        boolean resFlag = this.ruleItemDao.deleteRuleItem(condition) > 0;
        if (resFlag) {
            RuleItemByteParam byteParamCondition = new RuleItemByteParam();
            byteParamCondition.setRuleKey(key);
            this.ruleItemByteParamDao.deleteRuleItemByteParam(byteParamCondition);
            RuleItemValueParam valueParamCondition = new RuleItemValueParam();
            valueParamCondition.setRuleKey(key);
            this.ruleItemValueParamDao.deleteRuleItemValueParam(valueParamCondition);
        }
        return resFlag;
    }
}
