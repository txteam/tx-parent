/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月21日
 * <修改描述:>
 */
package com.tx.component.configuration.client;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.component.configuration.model.ConfigProperty;

/**
 * 配置容器接口<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RequestMapping(value = "/api/config")
public interface ConfigAPIClient {
    
    /**
     * 修改配置项值<br/>
     * <功能详细描述>
     * @param code
     * @param value
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{code}/{value}", method = RequestMethod.PATCH)
    public boolean patch(
            @PathVariable(required = true, name = "code") String code,
            @PathVariable(required = false, name = "value") String value);
    
    /**
     * 根据配置项编码获取配置属性实例<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public ConfigProperty findByCode(
            @PathVariable(required = true, name = "code") String code);
    
    /**
     * 查询配置属性<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ConfigProperty> queryList(
            @RequestParam Map<String, Object> params);
    
    /**
     * 查询配置属性<br/>
     * <功能详细描述>
     * @param module
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/childs/{parentId}", method = RequestMethod.GET)
    public List<ConfigProperty> queryChildsByParentId(
            @PathVariable(required = true, name = "parentId") String parentId,
            @RequestParam Map<String, Object> params);
    
    /**
     * 查询配置属性<br/>
     * <功能详细描述>
     * @param module
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/nestedchilds/{parentId}", method = RequestMethod.GET)
    public List<ConfigProperty> queryNestedChildsByParentId(
            @PathVariable(required = true, name = "parentId") String parentId,
            @RequestParam Map<String, Object> params);
}
