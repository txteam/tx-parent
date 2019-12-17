/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.plugin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.plugin.dao.PluginInstanceDao;
import com.tx.component.plugin.model.PluginInstance;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Filter;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QuerierBuilder;

/**
 * PluginInstance的业务层[PluginInstanceService]
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PluginInstanceService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory
            .getLogger(PluginInstanceService.class);
    
    private PluginInstanceDao pluginInstanceDao;
    
    /** <默认构造函数> */
    public PluginInstanceService() {
        super();
    }
    
    /** <默认构造函数> */
    public PluginInstanceService(PluginInstanceDao pluginInstanceDao) {
        super();
        this.pluginInstanceDao = pluginInstanceDao;
    }
    
    /**
     * 新增插件实例实例<br/>
     * 将插件实例插入数据库中保存
     * 1、如果pluginInstance 为空时抛出参数为空异常
     * 2、如果pluginInstance 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param pluginInstance [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(PluginInstance pluginInstance) {
        //验证参数是否合法
        AssertUtils.notNull(pluginInstance, "pluginInstance is null.");
        AssertUtils.notEmpty(pluginInstance.getId(),
                "pluginInstance.id is empty.");
        AssertUtils.notEmpty(pluginInstance.getPrefix(),
                "pluginInstance.prefix is empty.");
        
        //为添加的数据需要填入默认值的字段填入默认值
        Date now = new Date();
        pluginInstance.setLastUpdateDate(now);
        pluginInstance.setCreateDate(now);
        
        //调用数据持久层对实例进行持久化操作
        this.pluginInstanceDao.insert(pluginInstance);
    }
    
    /**
     * 根据id删除插件实例实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param id
     * @return boolean 删除的条数>0则为true [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        PluginInstance condition = new PluginInstance();
        condition.setId(id);
        
        int resInt = this.pluginInstanceDao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据id查询插件实例实例
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return PluginInstance [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public PluginInstance findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        PluginInstance condition = new PluginInstance();
        condition.setId(id);
        
        PluginInstance res = this.pluginInstanceDao.find(condition);
        return res;
    }
    
    /**
     * 查询插件实例实例列表
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<PluginInstance> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<PluginInstance> queryList(Boolean valid,
            Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<PluginInstance> resList = this.pluginInstanceDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 查询插件实例实例列表
     * <功能详细描述>
     * @param valid
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<PluginInstance> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<PluginInstance> queryList(Boolean valid, Querier querier) {
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        if (valid != null) {
            querier.getFilters().add(Filter.eq("valid", valid));
        }
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<PluginInstance> resList = this.pluginInstanceDao
                .queryList(querier);
        
        return resList;
    }
    
    /**
     * 分页查询插件实例实例列表
     * <功能详细描述>
     * @param valid
     * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<PluginInstance> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<PluginInstance> queryPagedList(Boolean valid,
            Map<String, Object> params, int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<PluginInstance> resPagedList = this.pluginInstanceDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 分页查询插件实例实例列表
     * <功能详细描述>
     * @param valid
     * @param querier    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<PluginInstance> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<PluginInstance> queryPagedList(Boolean valid,
            Querier querier, int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        if (valid != null) {
            querier.getFilters().add(Filter.eq("valid", valid));
        }
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<PluginInstance> resPagedList = this.pluginInstanceDao
                .queryPagedList(querier, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 查询插件实例实例数量<br/>
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<PluginInstance> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Boolean valid, Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.pluginInstanceDao.count(params);
        
        return res;
    }
    
    /**
     * 查询插件实例实例数量<br/>
     * <功能详细描述>
     * @param valid
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<PluginInstance> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(Boolean valid, Querier querier) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        if (valid != null) {
            querier.getFilters().add(Filter.eq("valid", valid));
        }
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.pluginInstanceDao.count(querier);
        
        return res;
    }
    
    /**
     * 判断插件实例实例是否已经存在<br/>
     * <功能详细描述>
     * @param key2valueMap
     * @param excludeId
     * @return
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.pluginInstanceDao.count(params, excludeId);
        
        return res > 0;
    }
    
    /**
     * 判断插件实例实例是否已经存在<br/>
     * <功能详细描述>
     * @param key2valueMap
     * @param excludeId
     * @return
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Querier querier, String excludeId) {
        AssertUtils.notNull(querier, "querier is null.");
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.pluginInstanceDao.count(querier, excludeId);
        
        return res > 0;
    }
    
    /**
     * 根据id更新插件实例实例<br/>
     * <功能详细描述>
     * @param pluginInstance
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(String id, PluginInstance pluginInstance) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(pluginInstance, "pluginInstance is null.");
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        
        //需要更新的字段
        updateRowMap.put("catalog", pluginInstance.getCatalog());
        updateRowMap.put("prefix", pluginInstance.getPrefix());
        updateRowMap.put("version", pluginInstance.getVersion());
        
        updateRowMap.put("name", pluginInstance.getName());
        updateRowMap.put("author", pluginInstance.getAuthor());
        updateRowMap.put("describeUrl", pluginInstance.getDescribeUrl());
        updateRowMap.put("uninstallUrl", pluginInstance.getUninstallUrl());
        updateRowMap.put("installUrl", pluginInstance.getInstallUrl());
        updateRowMap.put("settingUrl", pluginInstance.getSettingUrl());
        updateRowMap.put("priority", pluginInstance.getPriority());
        updateRowMap.put("remark", pluginInstance.getRemark());
        
        updateRowMap.put("installed", pluginInstance.isInstalled());
        updateRowMap.put("valid", pluginInstance.isValid());
        
        updateRowMap.put("lastUpdateDate", new Date());
        
        boolean flag = this.pluginInstanceDao.update(id, updateRowMap);
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
    
    /**
     * 根据id更新插件实例实例<br/>
     * <功能详细描述>
     * @param pluginInstance
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(PluginInstance pluginInstance) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(pluginInstance, "pluginInstance is null.");
        AssertUtils.notEmpty(pluginInstance.getId(),
                "pluginInstance.id is empty.");
        
        boolean flag = updateById(pluginInstance.getId(), pluginInstance);
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
    
    /**
     * 根据id禁用插件实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean disableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("valid", false);
        params.put("lastUpdateDate", new Date());
        
        boolean flag = this.pluginInstanceDao.update(params) > 0;
        
        return flag;
    }
    
    /**
     * 根据id启用插件实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean enableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("valid", true);
        params.put("lastUpdateDate", new Date());
        
        boolean flag = this.pluginInstanceDao.update(params) > 0;
        
        return flag;
    }
    
    /**
     * 根据插件id安装插件<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean install(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //查询插件
        PluginInstance pluginInstance = findById(id);
        AssertUtils.notNull(pluginInstance, "pluginInstance is null.id:{}", id);
        AssertUtils.isTrue(!pluginInstance.isInstalled(),
                "pluginInstance is installed.id:{}",
                id);
        
        //安装
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        
        params.put("installed", true);
        params.put("valid", false);
        params.put("lastUpdateDate", new Date());
        
        boolean flag = this.pluginInstanceDao.update(params) > 0;
        
        return flag;
    }
    
    /**
     * 根据插件id卸载插件<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean uninstall(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //查询插件
        PluginInstance pluginInstance = findById(id);
        AssertUtils.notNull(pluginInstance, "pluginInstance is null.id:{}", id);
        AssertUtils.isTrue(pluginInstance.isInstalled(),
                "pluginInstance is not installed.id:{}",
                id);
        
        //安装
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        
        params.put("installed", false);
        params.put("valid", false);
        params.put("lastUpdateDate", new Date());
        
        boolean flag = this.pluginInstanceDao.update(params) > 0;
        
        return flag;
    }
}
