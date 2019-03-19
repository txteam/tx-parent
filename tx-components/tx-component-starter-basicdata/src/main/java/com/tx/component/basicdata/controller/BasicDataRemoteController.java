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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tx.component.basicdata.context.BasicDataContext;
import com.tx.component.basicdata.context.BasicDataService;
import com.tx.component.basicdata.context.TreeAbleBasicDataService;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.TreeAbleBasicData;
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
@Api(value = "/basicDataRemote", tags = "基础数据远程调用API")
@RestController(value = "/basicDataRemote")
public class BasicDataRemoteController {
    
    /**
     * 获取对应的表名<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "根据基础数据类获取数据库表名", notes = "")
    @ApiImplicitParam(name = "type", value = "类型", required = true, dataType = "string", paramType = "path")
    @RequestMapping(value = "{type}/tableName", method = RequestMethod.GET)
    public <T extends BasicData> String tableName(@PathVariable Class<T> type) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        String tableName = service.tableName();
        
        return tableName;
    }
    
    /**
     * 插入基础数据对象
     * <功能详细描述>
     * @param data [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "增加基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "data", value = "基础数据类型", required = true, dataType = "JSONObject", example = "{code:'...',name='...'}") })
    @RequestMapping(value = "{type}/insert", method = RequestMethod.POST)
    public <T extends BasicData> void insert(@PathVariable Class<T> type,
            @RequestBody JSONObject data) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        T object = data.toJavaObject(type);
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
    @ApiOperation(value = "批量增加基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "datas", value = "基础数据类型", required = true, dataType = "JSONArray", example = "[{code:'...',name='...'}]") })
    @RequestMapping(value = "{type}/batchInsert", method = RequestMethod.POST)
    public <T extends BasicData> void batchInsert(@PathVariable Class<T> type,
            @RequestBody JSONArray datas) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        List<T> objectList = datas.toJavaList(type);
        service.batchInsert(objectList);
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
    @ApiOperation(value = "更新基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "data", value = "基础数据类型", required = true, dataType = "JSONObject", example = "{code:'...',name='...'}") })
    @RequestMapping(value = "{type}/updateById", method = RequestMethod.PUT)
    public <T extends BasicData> boolean updateById(@PathVariable Class<T> type,
            @RequestBody JSONObject data) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        T object = data.toJavaObject(type);
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
    @ApiOperation(value = "批量更新基础数据实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "datas", value = "基础数据类型", required = true, dataType = "JSONArray", example = "[{code:'...',name='...'}]") })
    @RequestMapping(value = "{type}/batchUpdate", method = RequestMethod.PUT)
    public <T extends BasicData> void batchUpdate(@PathVariable Class<T> type,
            @RequestBody JSONArray datas) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        List<T> objectList = datas.toJavaList(type);
        service.batchUpdate(objectList);
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
    @ApiOperation(value = "根据ID删除实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "基础数据ID", required = true, dataType = "string") })
    @RequestMapping(value = "{type}/deleteById", method = RequestMethod.DELETE)
    public <T extends BasicData> boolean deleteById(@PathVariable Class<T> type,
            String id) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
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
    @ApiOperation(value = "根据code删除实例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "code", value = "基础数据code", required = true, dataType = "string") })
    @RequestMapping(value = "{type}/deleteByCode", method = RequestMethod.DELETE)
    public <T extends BasicData> boolean deleteByCode(
            @PathVariable Class<T> type, String code) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        boolean flag = service.deleteByCode(code);
        return flag;
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
    @ApiOperation(value = "判断code对应实例是否存在", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "基础数据类型", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "key2valueMap", value = "基础数据code", required = true, dataType = "string") })
    @RequestMapping(value = "{type}/isExist", method = RequestMethod.GET)
    public <T extends BasicData> boolean isExist(@PathVariable Class<T> type,
            Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        boolean flag = service.isExist(key2valueMap, excludeId);
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
    @RequestMapping(value = "{type}/findById", method = RequestMethod.GET)
    public <T extends BasicData> T findById(@PathVariable Class<T> type,
            String id) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        T res = service.findById(id);
        return res;
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
    @RequestMapping(value = "{type}/findByCode", method = RequestMethod.GET)
    public <T extends BasicData> T findByCode(@PathVariable Class<T> type,
            String code) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        T res = service.findByCode(code);
        return res;
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
    @RequestMapping(value = "{type}/queryList", method = RequestMethod.GET)
    public <T extends BasicData> List<T> queryList(@PathVariable Class<T> type,
            Boolean valid, Map<String, Object> params) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        List<T> resList = service.queryList(valid, params);
        return resList;
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
    @RequestMapping(value = "{type}/queryPagedList", method = RequestMethod.GET)
    public <T extends BasicData> PagedList<T> queryPagedList(
            @PathVariable Class<T> type, Boolean valid,
            Map<String, Object> params, int pageIndex, int pageSize) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        PagedList<T> resPagedList = service.queryPagedList(valid,
                params,
                pageIndex,
                pageSize);
        return resPagedList;
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
    @RequestMapping(value = "{type}/disableById", method = RequestMethod.PATCH)
    public <T extends BasicData> boolean disableById(
            @PathVariable Class<T> type, String id) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
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
    @RequestMapping(value = "{type}/enableById", method = RequestMethod.PATCH)
    public <T extends BasicData> boolean enableById(@PathVariable Class<T> type,
            String id) {
        AssertUtils.notNull(type, "type is null.");
        BasicDataService<T> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        boolean flag = service.enableById(id);
        return flag;
    }
    
    /**
     * 根据条件查询基础数据列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param params
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "{type}/queryListByParentId", method = RequestMethod.GET)
    public <T extends TreeAbleBasicData<T>> List<T> queryListByParentId(
            @PathVariable Class<T> type, String parentId, Boolean valid,
            Map<String, Object> params) {
        AssertUtils.notNull(type, "type is null.");
        TreeAbleBasicDataService<T> service = BasicDataContext.getContext()
                .getTreeAbleBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        List<T> resList = service.queryListByParentId(parentId, valid, params);
        return resList;
    }
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
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
    @RequestMapping(value = "{type}/queryPagedListByParentId", method = RequestMethod.GET)
    public <T extends TreeAbleBasicData<T>> PagedList<T> queryPagedListByParentId(
            @PathVariable Class<T> type, String parentId, Boolean valid,
            Map<String, Object> params, int pageIndex, int pageSize) {
        AssertUtils.notNull(type, "type is null.");
        TreeAbleBasicDataService<T> service = BasicDataContext.getContext()
                .getTreeAbleBasicDataService(type);
        AssertUtils.notNull(service,
                "service is not exist.type:{}",
                new Object[] { type });
        
        PagedList<T> resPagedList = service.queryPagedListByParentId(parentId,
                valid,
                params,
                pageIndex,
                pageSize);
        return resPagedList;
    }
}
