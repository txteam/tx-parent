/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-24
 * <修改描述:>
 */
package com.tx.component.config.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.config.model.ConfigProperty;
import com.tx.component.config.setting.ConfigPropertiesSettings;


 /**
  * <属性持久层接口>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-10-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface PropertiesPersisterDao {
    
    /**
      *<根据配置初始化属性持久及dao>
      *<功能详细描述>
      * @param configContextCfg [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void init(ConfigPropertiesSettings configContextCfg);
    
    /**
      *<查询属性maplist>
      *<功能详细描述>
      * @param resourceId
      * @return [参数说明]
      * 
      * @return List<Map<String,String>> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<Map<String, String>> queryPropertiesMapList(String resourceId);
    
    /**
      *<插入属性>
      *<功能详细描述>
      * @param resourceId
      * @param configProperty [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insertProperty(String resourceId,ConfigProperty configProperty);
    
    /**
      *<更新属性>
      *<功能详细描述>
      * @param resourceId
      * @param configProperty [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void updateProperty(String resourceId,ConfigProperty configProperty);
    
    public void deleteProperty(String resourceId,ConfigProperty configProperty);
}
