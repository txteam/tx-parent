/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.config.support;

import java.util.List;
import java.util.Observer;

import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.component.config.setting.ConfigResource;



 /**
  * 配置属性持久接口<br/>
  * <功能详细描述>
  * 
  * @author  wanxin
  * @version  [版本号, 2013-8-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ConfigResourcePropertiesPersister extends Observer{
    
    /**
      * 返回当前解析器默认处理的配置属性解析类型<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String configPropertyTypeName();
    
    /**
      * 获取配置属性解析器实例<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return PropertiesPersister [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ConfigResourcePropertiesPersister newInstance(ConfigResource configResource);
    
    /**
      * 加载所有的配置属性<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ConfigProperty> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ConfigPropertyItem> load();
    
    /**
     * 更新配置属性值<br/>
      *<功能简述>
      *<功能详细描述>
      * @param configPropertyItem [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void update(ConfigPropertyItem configPropertyItem);
    
}
