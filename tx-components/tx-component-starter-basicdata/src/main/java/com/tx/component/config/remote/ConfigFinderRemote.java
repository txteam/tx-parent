/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月4日
 * <修改描述:>
 */
package com.tx.component.config.remote;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tx.component.config.model.ConfigProperty;

/**
 * 配置查询器远程调用句柄<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RequestMapping(path = "/config/finder")
public interface ConfigFinderRemote {
    
    /**
     * 根据code查询配置属性<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(method = RequestMethod.GET, path = "/code/{code}")
    public ConfigProperty findByCode(@PathVariable(name = "code") String code);
    
    /**
     * 查询所有的属性<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Set<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public List<ConfigProperty> list();
}
