/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年7月18日
 * <修改描述:>
 */
package com.tx.component.basicdata.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.component.basicdata.context.BasicDataContext;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.BasicDataService;
import com.tx.component.basicdata.service.TreeAbleBasicDataService;
import com.tx.component.basicdata.util.BasicDataUtils;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 基础数据远程调用接口<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年7月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RestController
@Api(tags = "基础数据容器API")
@RequestMapping("/api/basicdata")
public class BasicDataAPIController {
    
    /**
     * 插入基础数据对象<br/>
     * <功能详细描述>
     * @param data [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "增加基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "data", value = "数据字典实例", required = true, dataTypeClass = DataDict.class, paramType = "form", example = "{code:'...',name='...'}") })
    @RequestMapping(value = "/{type}/", method = RequestMethod.POST)
    public <T extends BasicData> void insert(@PathVariable String type,
            @RequestBody DataDict data) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        //转换为对应的实例
        T object = BasicDataUtils.fromDataDict(data, entityClass);
        service.insert(object);
    }
    
    /**
     * 批量插入基础数据
     * <功能详细描述>
     * @param dataList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "批量增加基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dataMapList", value = "基础数据类型", required = true, dataTypeClass = List.class, paramType = "body", example = "[{code:'...',name='...'}]") })
    @RequestMapping(value = "/{type}/batch", method = RequestMethod.POST)
    public <T extends BasicData> void batchInsert(@PathVariable String type,
            @RequestBody List<DataDict> dataList) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        List<T> objectList = BasicDataUtils.fromDataDictList(dataList,
                entityClass);
        service.batchInsert(objectList);
    }
    
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
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "根据ID删除实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "基础数据ID", required = true, dataType = "string", paramType = "path") })
    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.DELETE)
    public <T extends BasicData> boolean deleteById(@PathVariable String type,
            @PathVariable String id) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        boolean flag = service.deleteById(id);
        return flag;
    }
    
    /**
     * 根据code进行删除
     * <功能详细描述>
     * @param basicDataTypeCode
     * @param code
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "根据code删除实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "code", value = "基础数据code", required = true, dataType = "string", paramType = "path") })
    @RequestMapping(value = "/{type}/code/{code}", method = RequestMethod.DELETE)
    public <T extends BasicData> boolean deleteByCode(@PathVariable String type,
            @PathVariable String code) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        boolean flag = service.deleteByCode(code);
        return flag;
    }
    
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
    @ApiOperation(value = "禁用基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "基础数据id", required = true, dataType = "string", paramType = "path") })
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{type}/disable/{id}", method = RequestMethod.PATCH)
    public <T extends BasicData> boolean disableById(@PathVariable String type,
            @PathVariable String id) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        boolean flag = service.disableById(id);
        return flag;
    }
    
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
    @ApiOperation(value = "启用基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "基础数据id", required = true, dataType = "string", paramType = "path") })
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{type}/enable/{id}", method = RequestMethod.PATCH)
    public <T extends BasicData> boolean enableById(@PathVariable String type,
            @PathVariable String id) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        boolean flag = service.enableById(id);
        return flag;
    }
    
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
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "根据id更新基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "唯一键", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "data", value = "数据字典实例", required = true, dataTypeClass = DataDict.class, example = "{code:'...',name='...'}") })
    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.PUT)
    public <T extends BasicData> boolean updateById(@PathVariable String type,
            @PathVariable String id, @RequestBody DataDict data) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        data.setId(id);
        T object = BasicDataUtils.fromDataDict(data, entityClass);
        boolean flag = service.updateById(object);
        return flag;
    }
    
    /**
     * 根据编码更新基础数据实例<br/>
     * <功能详细描述>
     * @param type
     * @param code
     * @param data
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "根据编码更新基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "code", value = "编码", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "data", value = "数据字典实例", required = true, dataTypeClass = DataDict.class, example = "{code:'...',name='...'}") })
    @RequestMapping(value = "/{type}/code/{code}", method = RequestMethod.PUT)
    public <T extends BasicData> boolean updateByCode(@PathVariable String type,
            @PathVariable String code, @RequestBody DataDict data) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        data.setCode(code);
        T object = BasicDataUtils.fromDataDict(data, entityClass);
        boolean flag = service.updateById(object);
        return flag;
    }
    
    /**
     * 批量更新基础数据<br/>
     * <功能详细描述>
     * @param dataList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "批量更新基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dataList", value = "数据字典实例集合", required = true, dataTypeClass = List.class, paramType = "body", example = "[{code:'...',name='...'}]") })
    @RequestMapping(value = "/{type}/batch", method = RequestMethod.PUT)
    public <T extends BasicData> void batchUpdate(@PathVariable String type,
            @RequestBody List<DataDict> dataList) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        List<T> objectList = BasicDataUtils.fromDataDictList(dataList,
                entityClass);
        service.batchUpdate(objectList);
    }
    
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
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "判断code对应实例是否存在", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "key2valueMap", value = "基础数据code", required = true, dataType = "string") })
    @RequestMapping(value = "/{type}/exist/{excludeId}", method = RequestMethod.GET)
    public <T extends BasicData> boolean exist(@PathVariable String type,
            @RequestParam Map<String, String> key2valueMap,
            @PathVariable(required = false) String excludeId) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        boolean flag = service.exist(key2valueMap, excludeId);
        return flag;
    }
    
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
    @ApiOperation(value = "根据id查询基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "唯一键", required = true, dataType = "string", paramType = "path") })
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.GET)
    public <T extends BasicData> DataDict findById(@PathVariable String type,
            @PathVariable String id) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        T object = service.findById(id);
        DataDict data = BasicDataUtils.toDataDict(object);
        return data;
    }
    
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
    @ApiOperation(value = "根据code查询基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "code", value = "编码", required = true, dataType = "string", paramType = "path") })
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{type}/code/{code}", method = RequestMethod.GET)
    public <T extends BasicData> DataDict findByCode(@PathVariable String type,
            @PathVariable String code) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        T object = service.findByCode(code);
        DataDict data = BasicDataUtils.toDataDict(object);
        return data;
    }
    
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
    @ApiOperation(value = "查询基础数据实例列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "valid", value = "是否有效", required = true, dataTypeClass = Boolean.class, paramType = "path"),
            @ApiImplicitParam(name = "params", value = "查询条件", required = false, dataTypeClass = Map.class) })
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{type}/list/{valid}", method = RequestMethod.GET)
    public <T extends BasicData> List<DataDict> queryList(
            @PathVariable String type,
            @PathVariable(required = false) Boolean valid,
            @RequestParam Map<String, Object> params) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        List<T> objectList = service.queryList(valid, params);
        List<DataDict> dataList = BasicDataUtils.toDataDictList(objectList);
        return dataList;
    }
    
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
    @ApiOperation(value = "查询基础数据实例分页列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "valid", value = "是否有效", required = true, dataTypeClass = Boolean.class, paramType = "path"),
            @ApiImplicitParam(name = "params", value = "查询条件", required = false, dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pageIndex", value = "第几页", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, dataTypeClass = Integer.class) })
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{type}/pagedlist/{valid}", method = RequestMethod.GET)
    public <T extends BasicData> PagedList<DataDict> queryPagedList(
            @PathVariable String type,
            @PathVariable(required = false) Boolean valid,
            @RequestParam Map<String, Object> params,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        PagedList<T> objectPagedList = service.queryPagedList(valid,
                params,
                pageIndex,
                pageSize);
        PagedList<DataDict> dataPagedList = BasicDataUtils
                .toDataDictPagedList(objectPagedList);
        return dataPagedList;
    }
    
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/{type}/children/{valid}", method = RequestMethod.GET)
    public <T extends TreeAbleBasicData> List<DataDict> queryChildrenByParentId(
            @PathVariable String type,
            @PathVariable(required = true) String parentId,
            @PathVariable(required = false) Boolean valid,
            @RequestParam Map<String, Object> params) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        TreeAbleBasicDataService service = BasicDataContext.getContext()
                .getTreeAbleBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        List<T> objectList = service.queryChildrenByParentId(parentId,
                valid,
                params);
        List<DataDict> dataList = BasicDataUtils.toDataDictList(objectList);
        return dataList;
    }
    
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
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{type}/descendants/{valid}", method = RequestMethod.GET)
    public <T extends TreeAbleBasicData<T>> List<DataDict> queryDescendantsByParentId(
            @PathVariable String type,
            @PathVariable(required = true) String parentId,
            @PathVariable(required = false) Boolean valid,
            @RequestParam Map<String, Object> params) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<T> entityClass = (Class<T>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        TreeAbleBasicDataService<T> service = BasicDataContext.getContext()
                .getTreeAbleBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        List<T> objectList = service.queryDescendantsByParentId(parentId,
                valid,
                params);
        List<DataDict> dataList = BasicDataUtils.toDataDictList(objectList);
        return dataList;
    }
}
