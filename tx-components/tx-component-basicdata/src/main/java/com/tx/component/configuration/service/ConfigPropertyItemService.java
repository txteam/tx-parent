/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月6日
 * <修改描述:>
 */
package com.tx.component.configuration.service;

import java.util.List;
import java.util.Map;

import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.model.ConfigPropertyItem;

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
    
    /** 配置属相项持久层实现 */
    private ConfigPropertyItemDao configPropertyItemDao;
    
    /** <默认构造函数> */
    public ConfigPropertyItemService(
            ConfigPropertyItemDao configPropertyItemDao) {
        super();
        this.configPropertyItemDao = configPropertyItemDao;
    }

    /**
     * 插入配置属性项目<br/>
     *<功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(ConfigPropertyItem configPropertyItem){
        
    }
    
    /**
     * 更新配置属性项<br/>
     *<功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void update(ConfigPropertyItem configPropertyItem){
        
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
    public List<ConfigPropertyItem> queryList(Map<String, Object> params){
        return null;
    }
}
