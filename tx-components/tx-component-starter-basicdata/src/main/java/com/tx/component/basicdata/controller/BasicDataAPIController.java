/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年7月18日
 * <修改描述:>
 */
package com.tx.component.basicdata.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.component.basicdata.context.BasicDataContext;
import com.tx.component.basicdata.facade.BasicDataFacade;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.BasicDataService;
import com.tx.component.basicdata.service.TreeAbleBasicDataService;
import com.tx.component.basicdata.util.BasicDataUtils;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

import io.swagger.annotations.Api;

/**
 * 基础数据远程调用接口<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年7月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@SuppressWarnings("unchecked")
@RestController
@Api(tags = "基础数据容器API")
@RequestMapping("/api/basicdata")
public class BasicDataAPIController<T extends BasicData>
        implements BasicDataFacade {
    
    /**
     * 插入基础数据对象<br/>
     * <功能详细描述>
     * @param data [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public void insert(
            @PathVariable(value = "type", required = true) String type,
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
    @Override
    public void batchInsert(
            @PathVariable(value = "type", required = true) String type,
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
    @Override
    public boolean deleteById(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "id", required = true) String id) {
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
    @Override
    public boolean deleteByCode(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "code", required = true) String code) {
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
    @Override
    public boolean disableById(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "id", required = true) String id) {
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
    @Override
    public boolean enableById(
            @PathVariable(value = "type", required = true) String type,
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
    @Override
    public boolean updateById(
            @PathVariable(value = "type", required = true) String type,
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
    @Override
    public boolean updateByCode(
            @PathVariable(value = "type", required = true) String type,
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
    @Override
    public void batchUpdate(
            @PathVariable(value = "type", required = true) String type,
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
    @Override
    public boolean exists(
            @PathVariable(value = "type", required = true) String type,
            @RequestBody Querier querier,
            @RequestParam(value = "excludeId", required = false) String excludeId) {
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
        
        boolean flag = service.exists(querier, excludeId);
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
    @Override
    public DataDict findById(
            @PathVariable(value = "type", required = true) String type,
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
    @Override
    public DataDict findByCode(
            @PathVariable(value = "type", required = true) String type,
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
     * @param querier
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<DataDict> queryList(
            @PathVariable(value = "type", required = true) String type,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier) {
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
        
        List<T> objectList = service.queryList(valid, querier);
        List<DataDict> dataList = BasicDataUtils.toDataDictList(objectList);
        return dataList;
    }
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param valid
     * @param querier
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<DataDict> queryPagedList(
            @PathVariable(value = "type", required = true) String type,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier,
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
                querier,
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
    @SuppressWarnings("rawtypes")
    @Override
    public List<DataDict> queryChildrenByParentId(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "parentId", required = true) String parentId,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<? extends TreeAbleBasicData> entityClass = (Class<? extends TreeAbleBasicData>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        TreeAbleBasicDataService service = BasicDataContext.getContext()
                .getTreeAbleBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        List<? extends TreeAbleBasicData> objectList = service.queryChildrenByParentId(parentId,
                valid,
                querier);
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
    @SuppressWarnings("rawtypes")
    @Override
    public List<DataDict> queryDescendantsByParentId(
            @PathVariable(value = "type", required = true) String type,
            @PathVariable(value = "parentId", required = true) String parentId,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier) {
        //获取对应的实体类型
        AssertUtils.notEmpty(type, "type is empty.");
        Class<? extends TreeAbleBasicData> entityClass = (Class<? extends TreeAbleBasicData>) BasicDataContext.getContext()
                .getEntityClass(type);
        AssertUtils.notNull(entityClass, "entityClass is null.type:{}", type);
        
        //获取对应的业务层
        TreeAbleBasicDataService service = BasicDataContext.getContext()
                .getTreeAbleBasicDataService(entityClass);
        AssertUtils.notNull(service,
                "service is not exist.type:{} entityClass:{}",
                new Object[] { type, entityClass });
        
        List<? extends TreeAbleBasicData> objectList = service.queryDescendantsByParentId(parentId,
                valid,
                querier);
        List<DataDict> dataList = BasicDataUtils.toDataDictList(objectList);
        return dataList;
    }
}
