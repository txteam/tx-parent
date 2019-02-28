/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月3日
 * <修改描述:>
 */
package com.tx.component.config.remote;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tx.component.config.model.ConfigRepositorySource;

/**
 * 配置服务器客户端<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RequestMapping(path = "/config/client")
public interface ConfigServerClientRemote {
    
    /**
     * 将本地的配置属性向配置服务中心进行注册<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public void register(ConfigRepositorySource configRepositoryResource);
    
    /**
     * 配置仓库来源信息<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<ConfigRepositorySource> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public List<ConfigRepositorySource> list();
}
