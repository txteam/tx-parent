/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-24
 * <修改描述:>
 */
package com.tx.component.config.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.config.dao.PropertiesPersisterDao;
import com.tx.component.config.model.ConfigProperty;
import com.tx.component.config.setting.ConfigPropertiesSettings;

/**
 * <属性持久类>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PropertiesPersisterDaoImpl implements PropertiesPersisterDao{
    
    /** datasource Map */
    private Map<String, DataSource> datasourceMap = new HashMap<String, DataSource>();
    
    private boolean isSupportP6spy = false;
    
    

    /**
     * @param configContextCfg
     */
    @Override
    public void init(ConfigPropertiesSettings configContextCfg) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param resourceId
     * @return
     */
    @Override
    public List<Map<String, String>> queryPropertiesMapList(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param resourceId
     * @param configProperty
     */
    @Override
    public void insertProperty(String resourceId, ConfigProperty configProperty) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param resourceId
     * @param configProperty
     */
    @Override
    public void updateProperty(String resourceId, ConfigProperty configProperty) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param resourceId
     * @param configProperty
     */
    @Override
    public void deleteProperty(String resourceId, ConfigProperty configProperty) {
        // TODO Auto-generated method stub
        
    }

    /**
      *<根据jndiname获取数据源>
      *<功能详细描述>
      * @param jndiname
      * @return [参数说明]
      * 
      * @return DataSource [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private DataSource getDatasourceByJndiname(String jndiname){
        
        return null;
    }
    
    /**
      *<根据数据库配置获取数据源>
      *<功能详细描述>
      * @param dbDriver
      * @param url
      * @param username
      * @param password
      * @return [参数说明]
      * 
      * @return DataSource [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private DataSource getDatasourceByDbConfig(String dbDriver, String url,
            String username, String password) {
        
        return null;
    }
}
