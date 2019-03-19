/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月6日
 * <修改描述:>
 */
package com.tx.component.configuration.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.model.ConfigPropertyItem;
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
    
    /** <默认构造函数> */
    public ConfigPropertyItemService(TransactionTemplate transactionTemplate,
            ConfigPropertyItemDao configPropertyItemDao,
            CacheManager cacheManager) {
        super();
        this.transactionTemplate = transactionTemplate;
        this.configPropertyItemDao = configPropertyItemDao;
        this.cacheManager = cacheManager;
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
    public void update(ConfigPropertyItem configPropertyItem) {
        
    }
    
    /**
     * 根据系统id查询配置属性项列表 
     *<功能详细描述>
     * @param systemId
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigPropertyItem> queryList(Map<String, Object> params) {
        List<ConfigPropertyItem> resList = this.configPropertyItemDao
                .queryList(params);
        return resList;
    }
}
