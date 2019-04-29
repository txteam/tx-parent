/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月6日
 * <修改描述:>
 */
package com.tx.component.configuration.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.configuration.ConfigContextConstants;
import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 配置属性项业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyItemService {
    
    /** 事务管理 */
    private final TransactionTemplate transactionTemplate;
    
    /** 配置属相项持久层实现 */
    private final ConfigPropertyItemDao configPropertyItemDao;
    
    /** 缓存Manager */
    private final CacheManager cacheManager;
    
    /** 对应的缓存实例 */
    private final Cache cache;
    
    /** <默认构造函数> */
    public ConfigPropertyItemService(TransactionTemplate transactionTemplate,
            ConfigPropertyItemDao configPropertyItemDao,
            CacheManager cacheManager) {
        super();
        this.transactionTemplate = transactionTemplate;
        this.configPropertyItemDao = configPropertyItemDao;
        this.cacheManager = cacheManager;
        
        //获取缓存实例
        this.cache = this.cacheManager
                .getCache(ConfigContextConstants.CACHE_NAME);
    }
    
    /**
     * 生成缓存Key<br/>
     * <功能详细描述>
     * @param module
     * @param code
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String generateCacheKey(String module, String code) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append(module).append("_").append(code);
        return sb.toString();
    }
    
    /**
     * 插入配置属性项目<br/>
     * <功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(ConfigPropertyItem configPropertyItem) {
        AssertUtils.notNull(configPropertyItem, "configPropertyItem is null.");
        AssertUtils.notEmpty(configPropertyItem.getCode(),
                "configPropertyItem.code is empty.");
        AssertUtils.notEmpty(configPropertyItem.getModule(),
                "configPropertyItem.module is empty.");
        AssertUtils.notEmpty(configPropertyItem.getName(),
                "configPropertyItem.name is empty.");
        AssertUtils.notEmpty(configPropertyItem.getValue(),
                "configPropertyItem.value is empty.");
        
        Date now = new Date();
        configPropertyItem.setLastUpdateDate(now);
        configPropertyItem.setCreateDate(now);
        
        this.transactionTemplate
                .execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(
                            TransactionStatus status) {
                        configPropertyItemDao.insert(configPropertyItem);
                    }
                });
    }
    
    /**
     * 更新配置属性项<br/>
     * <功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean update(String module, String code, String value) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        AssertUtils.notEmpty(value, "value is empty.");
        
        Map<String, Object> rowMap = new HashMap<>();
        rowMap.put("module", module);
        rowMap.put("code", code);
        rowMap.put("value", value);
        Date now = new Date();
        rowMap.put("lastUpdateDate", now);
        
        //从缓存中移除相关数据，因为仅仅移除所以不用放到持久层逻辑
        this.cache.evict(generateCacheKey(module, code));
        
        boolean res = this.transactionTemplate
                .execute(new TransactionCallback<Boolean>() {
                    /**
                     * @param status
                     * @return
                     */
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        int rownum = configPropertyItemDao.update(rowMap);
                        return rownum > 0;
                    }
                });
        return res;
    }
    
    /**
     * 更新配置属性项<br/>
     * <功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean update(ConfigPropertyItem configPropertyItem) {
        AssertUtils.notNull(configPropertyItem, "configPropertyItem is null.");
        AssertUtils.notEmpty(configPropertyItem.getId(),
                "configPropertyItem.id is empty.");
        AssertUtils.notEmpty(configPropertyItem.getCode(),
                "configPropertyItem.code is empty.");
        AssertUtils.notEmpty(configPropertyItem.getName(),
                "configPropertyItem.name is empty.");
        AssertUtils.notEmpty(configPropertyItem.getValue(),
                "configPropertyItem.value is empty.");
        
        Date now = new Date();
        configPropertyItem.setLastUpdateDate(now);
        
        final Map<String, Object> rowMap = new HashMap<>();
        rowMap.put("id", configPropertyItem.getId());
        rowMap.put("parentId", configPropertyItem.getParentId());
        rowMap.put("code", configPropertyItem.getCode());
        rowMap.put("name", configPropertyItem.getName());
        rowMap.put("remark", configPropertyItem.getRemark());
        rowMap.put("validateExpression",
                configPropertyItem.getValidateExpression());
        rowMap.put("value", configPropertyItem.getValue());
        rowMap.put("modifyAble", configPropertyItem.isModifyAble());
        rowMap.put("leaf", configPropertyItem.isLeaf());
        rowMap.put("lastUpdateDate", configPropertyItem.getLastUpdateDate());
        
        boolean res = this.transactionTemplate
                .execute(new TransactionCallback<Boolean>() {
                    /**
                     * @param status
                     * @return
                     */
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        int rownum = configPropertyItemDao.update(rowMap);
                        
                        //从缓存中移除相关数据，因为仅仅移除所以不用放到持久层逻辑
                        cache.evict(
                                generateCacheKey(configPropertyItem.getModule(),
                                        configPropertyItem.getCode()));
                        
                        return rownum > 0;
                    }
                });
        return res;
    }
    
    /**
     * 根据编码查询配置项目<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigPropertyItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigPropertyItem findByCode(String module, final String code) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        String cacheKey = generateCacheKey(module, code);
        ConfigPropertyItem res = null;
        if (this.cache.get(code) != null) {
            res = (ConfigPropertyItem) this.cache.get(cacheKey,
                    ConfigPropertyItem.class);
            return res;
        }
        
        res = this.transactionTemplate
                .execute(new TransactionCallback<ConfigPropertyItem>() {
                    
                    @Override
                    public ConfigPropertyItem doInTransaction(
                            TransactionStatus status) {
                        ConfigPropertyItem condition = new ConfigPropertyItem();
                        condition.setCode(code);
                        ConfigPropertyItem item = configPropertyItemDao
                                .find(condition);
                        
                        return item;
                    }
                });
        
        cache.put(cacheKey, res);
        return res;
    }
    
    /**
     * 根据系统id查询配置属性项列表 
     *    配置属性项列表功能不考虑缓存<br/>
     * <功能详细描述>
     * @param systemId
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigPropertyItem> queryList(String module,
            Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        
        List<ConfigPropertyItem> resList = null;
        final Map<String, Object> queryParams = params == null ? new HashMap<>()
                : params;
        queryParams.put("module", module);
        
        resList = this.transactionTemplate
                .execute(new TransactionCallback<List<ConfigPropertyItem>>() {
                    
                    @Override
                    public List<ConfigPropertyItem> doInTransaction(
                            TransactionStatus status) {
                        return configPropertyItemDao.queryList(queryParams);
                    }
                });
        return resList;
    }
    
    /**
     * 根据系统id查询配置属性项列表 
     *    配置属性项列表功能不考虑缓存<br/>
     * <功能详细描述>
     * @param systemId
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigPropertyItem> queryNestedListByParentId(String module,
            String parentId, Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigPropertyItem> resList = null;
        final Map<String, Object> queryParams = params == null ? new HashMap<>()
                : params;
        queryParams.put("module", module);
        
        Set<String> ids = new HashSet<>();
        Set<String> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        resList = this.transactionTemplate
                .execute(new TransactionCallback<List<ConfigPropertyItem>>() {
                    
                    @Override
                    public List<ConfigPropertyItem> doInTransaction(
                            TransactionStatus status) {
                        List<ConfigPropertyItem> resListTemp = doQueryNestedList(
                                ids, parentIds, queryParams);
                        
                        return resListTemp;
                    }
                });
        return resList;
    }
    
    /**
     * 查询嵌套列表<br/>
     * <功能详细描述>
     * @param ids
     * @param parentIds
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<ConfigPropertyItem> doQueryNestedList(Set<String> ids,
            Set<String> parentIds, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<ConfigPropertyItem>();
        }
        
        //ids避免数据出错时导致无限循环
        final Map<String, Object> queryParams = params == null ? new HashMap<>()
                : params;
        queryParams.put("parentIds", parentIds);
        
        Set<String> newParentIds = new HashSet<>();
        List<ConfigPropertyItem> resList = configPropertyItemDao
                .queryList(queryParams);
        
        for (ConfigPropertyItem cpTemp : resList) {
            if (!ids.contains(cpTemp.getId())) {
                newParentIds.add(cpTemp.getId());
            }
            ids.add(cpTemp.getId());
        }
        
        //嵌套查询下一层级
        resList.addAll(doQueryNestedList(ids, newParentIds, queryParams));
        return resList;
    }
    
}
