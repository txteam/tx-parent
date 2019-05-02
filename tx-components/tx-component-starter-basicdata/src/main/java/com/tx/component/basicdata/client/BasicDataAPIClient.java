/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.client;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.component.basicdata.model.BasicData;
import com.tx.core.paged.model.PagedList;

/**
 * 基础数据业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RequestMapping(value = "/api/basicdata")
public interface BasicDataAPIClient {
    
    /**
     * 插入基础数据对象<br/>
     * <功能详细描述>
     * @param dataMap [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/", method = RequestMethod.POST)
    public void insert(@PathVariable String type,
            @RequestParam Map<String, Object> dataMap);
    
    /**
     * 根据id进行删除
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.DELETE)
    public <T extends BasicData> boolean deleteById(@PathVariable String type,
            @PathVariable String id);
    
    /**
     * 根据code进行删除
     * <功能详细描述>
     * @param type
     * @param code
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/code/{code}", method = RequestMethod.DELETE)
    public <T extends BasicData> boolean deleteByCode(@PathVariable String type,
            @PathVariable String code);
    
    /**
     * 根据id更新基础数据对象<br/>
     * <功能详细描述>
     * @param data
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/", method = RequestMethod.PUT)
    public boolean updateById(@PathVariable String type,
            @RequestParam Map<String, Object> dataMap);
    
    /**
     * 根据id禁用DataDict<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/disable/{id}", method = RequestMethod.PATCH)
    public boolean disableById(@PathVariable String type,
            @PathVariable String id);
    
    /**
     * 根据id启用DataDict<br/>
     * <功能详细描述>
     * @param postId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/enable/{id}", method = RequestMethod.PATCH)
    public boolean enableById(@PathVariable String type,
            @PathVariable String id);
    
    /**
     * 判断基础数据是否存在<br/>
     * <功能详细描述>
     * @param key2valueMap
     * @param excludeId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/exist/{excludeId}", method = RequestMethod.GET)
    public boolean isExist(@PathVariable String type,
            @RequestParam Map<String, String> key2valueMap,
            @PathVariable(required = false) String excludeId);
    
    /**
     * 根据id查询基础数据实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.GET)
    public Map<String, Object> findById(@PathVariable String type,
            @PathVariable String id);
    
    /**
     * 根据code查询基础数据实例<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/code/{code}", method = RequestMethod.GET)
    public Map<String, Object> findByCode(@PathVariable String type,
            @PathVariable String code);
    
    /**
     * 根据条件查询基础数据列表<br/>
     * <功能详细描述>
     * @param valid
     * @param params
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/list/{valid}", method = RequestMethod.GET)
    public List<Map<String, Object>> queryList(@PathVariable String type,
            @PathVariable(required = false) Boolean valid,
            @RequestParam Map<String, Object> params);
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param valid
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/{type}/pagedlist/{pageSize}/{pageIndex}/{valid}", method = RequestMethod.GET)
    public PagedList<Map<String, Object>> queryPagedList(
            @PathVariable String type,
            @PathVariable(required = false) Boolean valid,
            @RequestParam Map<String, Object> params,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize);
}
