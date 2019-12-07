/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月4日
 * <修改描述:>
 */
package com.tx.component.plugin.service;


/**
 * 插件实例业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface PluginInstanceService {
    
    /**
     * 插件是否已经安装<br/>
     * <功能详细描述>
     * @param pluginId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isInstalled(String pluginId);
}
