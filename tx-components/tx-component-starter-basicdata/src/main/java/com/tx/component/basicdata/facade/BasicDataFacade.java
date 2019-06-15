/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月13日
 * <修改描述:>
 */
package com.tx.component.basicdata.facade;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.component.basicdata.model.DataDict;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface BasicDataFacade {
    
    /**
     * 插入基础数据对象<br/>
     * <功能详细描述>
     * @param dataMap [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "增加基础数据实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true),
            @ApiImplicitParam(name = "data", value = "数据字典实例", required = true, example = "{code:'...',name='...'}") })
    @RequestMapping(value = "/{type}", method = RequestMethod.POST)
    public void insert(
            @PathVariable(value = "type", required = true) String type,
            @RequestBody DataDict data);
    
    /**
     * 批量插入基础数据对象<br/>
     * <功能详细描述>
     * @param type
     * @param mapList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "批量增加基础数据实例")
    @RequestMapping(value = "/{type}/batch", method = RequestMethod.POST)
    public void batchInsert(
            @PathVariable(value = "type", required = true) String type,
            @RequestBody List<DataDict> dataList);
    
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
    @ApiOperation(value = "根据ID删除实例")
    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.DELETE)
    public boolean deleteById(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "id", required = true) String id);
    
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
    @ApiOperation(value = "根据code删除实例")
    @RequestMapping(value = "/{type}/code/{code}", method = RequestMethod.DELETE)
    public boolean deleteByCode(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "code", required = true) String code);
    
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
    @ApiOperation(value = "禁用基础数据实例")
    @RequestMapping(value = "/{type}/disable/{id}", method = RequestMethod.PATCH)
    public boolean disableById(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "id", required = true) String id);
    
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
    @ApiOperation(value = "启用基础数据实例")
    @RequestMapping(value = "/{type}/enable/{id}", method = RequestMethod.PATCH)
    public boolean enableById(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "id", required = true) String id);
    
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
    @ApiOperation(value = "根据id更新基础数据实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, paramType = "path"),
            @ApiImplicitParam(name = "id", value = "唯一键", required = true, paramType = "path"),
            @ApiImplicitParam(name = "data", value = "数据字典实例", required = true, dataTypeClass = DataDict.class, example = "{code:'...',name='...'}") })
    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.PUT)
    public boolean updateById(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "id", required = true) String id,
            @RequestBody DataDict data);
    
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
    @ApiOperation(value = "根据编码更新基础数据实例")
    @RequestMapping(value = "/{type}/code/{code}", method = RequestMethod.PUT)
    public boolean updateByCode(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "code", required = true) String code,
            @RequestBody DataDict data);
    
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
    @ApiOperation(value = "批量更新基础数据实例")
    @RequestMapping(value = "/{type}/batch", method = RequestMethod.PUT)
    public void batchUpdate(
            @PathVariable(value = "type", required = true) String type,
            @RequestBody List<DataDict> dataMapList);
    
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
    @ApiOperation(value = "判断符合条件的实例是否存在")
    @RequestMapping(value = "/{type}/exists", method = RequestMethod.GET)
    public boolean exists(
            @PathVariable(value = "type", required = true) String type,
            @RequestBody Querier querier,
            @RequestParam(value = "excludeId", required = false) String excludeId);
    
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
    @ApiOperation(value = "根据id查询基础数据实例")
    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.GET)
    public DataDict findById(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "id", required = true) String id);
    
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
    @ApiOperation(value = "根据code查询基础数据实例")
    @RequestMapping(value = "/{type}/code/{code}", method = RequestMethod.GET)
    public DataDict findByCode(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "code", required = true) String code);
    
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
    @ApiOperation(value = "查询基础数据实例列表")
    @RequestMapping(value = "/{type}/list", method = RequestMethod.GET)
    public List<DataDict> queryList(
            @PathVariable(value = "type", required = true) String type,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier);
    
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
    @ApiOperation(value = "查询基础数据实例分页列表")
    @RequestMapping(value = "/{type}/pagedlist", method = RequestMethod.GET)
    public PagedList<DataDict> queryPagedList(
            @PathVariable(value = "type", required = true) String type,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize);
    
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
    @ApiOperation(value = "查询基础数据实例子代列表")
    @RequestMapping(value = "/{type}/children/{parentId}", method = RequestMethod.GET)
    public List<DataDict> queryChildrenByParentId(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "parentId", required = true) String parentId,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier);
    
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
    @ApiOperation(value = "查询基础数据实例后代列表")
    @RequestMapping(value = "/{type}/descendants/{parentId}", method = RequestMethod.GET)
    public List<DataDict> queryDescendantsByParentId(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "parentId", required = true) String parentId,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier);
}
