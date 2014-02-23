/*
  * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.configuration.persister;

import com.tx.component.configuration.context.ConfigContext;

/**
 * 配置属性持久接口<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-8-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigPropertiesPersister {
    
    /**
      * 将属性初始化
      *<功能详细描述>
      * @param locations [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void load(ConfigContext configContext);
    
    /**
      * 根据关键字key获取配置属性实例<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return ConfigProperty [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getConfigPropertyValueByKey(String key);
    
    /**
      * 是否支持属性编辑<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isSupportModifyAble();
    
    /**
      * 更新配置容器中配置属性值<br/> 
      *<功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void updateConfigProperty(String key,String value);
}
