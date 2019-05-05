/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月6日
 * <修改描述:>
 */
package com.tx.component.configuration.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.component.configuration.service.ConfigPropertyItemService;
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
public class ConfigPropertyItemServiceImpl implements ConfigPropertyItemService {
    
    /** 配置属相项持久层实现 */
    private final ConfigPropertyItemDao configPropertyItemDao;
    
    /** <默认构造函数> */
    public ConfigPropertyItemServiceImpl(ConfigPropertyItemDao configPropertyItemDao) {
        super();
        this.configPropertyItemDao = configPropertyItemDao;
    }
    
    /**
     * @param configPropertyItem
     */
    @Override
    @Transactional
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
        
        this.configPropertyItemDao.insert(configPropertyItem);
    }
    
    /**
     * @param configPropertyItem
     * @return
     */
    @Override
    public boolean delete(ConfigPropertyItem configPropertyItem) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * @param module
     * @param code
     * @param value
     * @return
     */
    @Override
    @Transactional
    public boolean patch(String module, String code, String value) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        AssertUtils.notEmpty(value, "value is empty.");
        
        Map<String, Object> rowMap = new HashMap<>();
        rowMap.put("module", module);
        rowMap.put("code", code);
        rowMap.put("value", value);
        Date now = new Date();
        rowMap.put("lastUpdateDate", now);
        
        int rownum = configPropertyItemDao.update(rowMap);
        boolean res = rownum > 0;
        return res;
    }
    
    /**
     * @param configPropertyItem
     * @return
     */
    @Override
    @Transactional
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
        
        int rownum = configPropertyItemDao.update(rowMap);
        boolean res = rownum > 0;
        
        return res;
    }
    
    /**
     * @param module
     * @param code
     * @return
     */
    @Override
    public ConfigPropertyItem findByCode(String module, final String code) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        ConfigPropertyItem condition = new ConfigPropertyItem();
        condition.setCode(code);
        ConfigPropertyItem item = configPropertyItemDao
                .find(condition);
        
        return item;
    }
    
    /**
     * @param module
     * @param params
     * @return
     */
    @Override
    public List<ConfigPropertyItem> queryList(String module,
            Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        
        List<ConfigPropertyItem> resList = null;
        final Map<String, Object> queryParams = params == null ? new HashMap<>()
                : params;
        queryParams.put("module", module);
        
        resList = configPropertyItemDao.queryList(queryParams);
        return resList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param params
     * @return
     */
    @Override
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
        
        List<ConfigPropertyItem> resListTemp = doQueryNestedList(
                ids, parentIds, queryParams);
        
        return resListTemp;
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
