/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.config.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.config.dao.ConfigPropertyItemDao;
import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.component.config.util.UUIDUtils;
import com.tx.core.exceptions.argument.NullArgException;

/**
 * ConfigPropertyItem的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyItemService {
    
    //private Logger logger = LoggerFactory.getLogger(ConfigContext.class);
    
    private TransactionTemplate transactionTemplate;
    
    private ConfigPropertyItemDao configPropertyItemDao;
    
    public ConfigPropertyItemService(
            ConfigPropertyItemDao configPropertyItemDao,
            TransactionTemplate transactionTemplate) {
        this.configPropertyItemDao = configPropertyItemDao;
        this.transactionTemplate = transactionTemplate;
    }
    
    /**
      * 根据Id查询ConfigPropertyItem实体
      * 1、当id为empty时返回null
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return ConfigPropertyItem [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    public ConfigPropertyItem findConfigPropertyItemById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        
        ConfigPropertyItem condition = new ConfigPropertyItem();
        condition.setId(id);
        return this.configPropertyItemDao.findConfigPropertyItem(condition);
    }
    
    /**
      * 根据ConfigPropertyItem实体列表
      * TODO:补充说明
      * 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ConfigPropertyItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ConfigPropertyItem> queryConfigPropertyItemList(
            ConfigPropertyItem configPropertyItem) {
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        if (configPropertyItem != null
                && StringUtils.isNotEmpty(configPropertyItem.getKey())) {
            params.put("key", configPropertyItem.getKey());
        }
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<ConfigPropertyItem> resList = this.configPropertyItemDao.queryConfigPropertyItemList(params);
        
        return resList;
    }
    
    /**
      * 将configPropertyItem实例插入数据库中保存
      * 1、如果configPropertyItem为空时抛出参数为空异常
      * 2、如果configPropertyItem中部分必要参数为非法值时抛出参数不合法异常
      * <功能详细描述>
      * @param configPropertyItem [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insertConfigPropertyItem(ConfigPropertyItem configPropertyItem) {
        //TODO:验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalidException
        if (configPropertyItem == null /*TODO:|| 其他参数验证*/) {
            throw new NullArgException(
                    "ConfigPropertyItemService.insertConfigPropertyItem configPropertyItem isNull.");
        }
        //随机生成一个UUID
        configPropertyItem.setId(UUIDUtils.createUUID());
        this.configPropertyItemDao.insertConfigPropertyItem(configPropertyItem);
    }
    
    /**
      * 根据id删除configPropertyItem实例
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
    public int deleteById(final String id) {
        if (StringUtils.isEmpty(id)) {
            throw new NullArgException(
                    "ConfigPropertyItemService.deleteById id isEmpty.");
        }
        
        int res = transactionTemplate.execute(new TransactionCallback<Integer>() {
            /**
             * @param status
             * @return
             */
            public Integer doInTransaction(TransactionStatus status) {
                ConfigPropertyItem condition = new ConfigPropertyItem();
                condition.setId(id);
                return configPropertyItemDao.deleteConfigPropertyItem(condition);
            }
        });
        
        return res;
       
    }
    
    /**
      * 根据id更新对象
      * <功能详细描述>
      * @param configPropertyItem
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(ConfigPropertyItem configPropertyItem) {
        //TODO:验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalidException
        if (configPropertyItem == null
                || StringUtils.isEmpty(configPropertyItem.getId())) {
            throw new NullArgException(
                    "ConfigPropertyItemService.updateById configPropertyItem or configPropertyItem.id is empty.");
        }
        
        //TODO:生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", configPropertyItem.getId());
        
        //TODO:需要更新的字段
        updateRowMap.put("valid", configPropertyItem.isValid());
        updateRowMap.put("parentKey", configPropertyItem.getParentKey());
        updateRowMap.put("viewExpression",
                configPropertyItem.getValidateExpression());
        updateRowMap.put("viewAble", configPropertyItem.isViewAble());
        updateRowMap.put("editAble", configPropertyItem.isEditAble());
        updateRowMap.put("description", configPropertyItem.getDescription());
        updateRowMap.put("configResourceId",
                configPropertyItem.getConfigResourceId());
        updateRowMap.put("name", configPropertyItem.getName());
        updateRowMap.put("value", configPropertyItem.getValue());
        updateRowMap.put("validateExpression",
                configPropertyItem.getValidateExpression());
        updateRowMap.put("key", configPropertyItem.getKey());
        
        int updateRowCount = this.configPropertyItemDao.updateConfigPropertyItem(updateRowMap);
        
        //TODO:如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
    /**
     * 
      *删除表中全部数据<br/>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void deleteAll() {
        
        this.configPropertyItemDao.deleteConfigPropertyItem(null);
    }
}
