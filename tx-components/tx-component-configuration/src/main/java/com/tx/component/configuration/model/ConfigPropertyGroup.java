/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-10
 * <修改描述:>
 */
package com.tx.component.configuration.model;

import java.util.List;


 /**
  * 配置属性分组<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2014-2-10]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ConfigPropertyGroup {
    
    /**
     * 获取配置属性类型
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return ConfigPropertyTypeEnum [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public ConfigPropertyTypeEnum getConfigPropertyType();

    /**
      * 配置属性分组名<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getName();

    /**
      * 配置属性分组父级id
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getParentName();

    /**
      * 下级配置属性分组集合<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ConfigPropertyGroupItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ConfigPropertyGroup> getChilds();

    /**
      * 配置属性组是否可见<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isViewAble();

    /**
      * 配置属性组是否有效
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isValid();

    /**
      * 获取该配置属性组下属的配置属性<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ConfigProperty> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> getConfigs();
}
