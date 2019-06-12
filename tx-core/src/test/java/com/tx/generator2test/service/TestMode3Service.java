/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.generator2test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.generator2test.dao.TestMode3Dao;
import com.tx.generator2test.model.TestMode3;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Filter;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QuerierBuilder;

/**
 * 测试对象的业务层[TestMode3Service]
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component("testMode3Service")
public class TestMode3Service {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(TestMode3Service.class);
    
    @Resource(name = "testMode3Dao")
    private TestMode3Dao testMode3Dao;
    
    /**
     * 新增测试对象实例<br/>
     * 将testMode3插入数据库中保存
     * 1、如果testMode3 为空时抛出参数为空异常
     * 2、如果testMode3 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param testMode3 [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(TestMode3 testMode3) {
        //验证参数是否合法
        AssertUtils.notNull(testMode3, "testMode3 is null.");
		AssertUtils.notEmpty(testMode3.getCode(), "testMode3.code is empty.");
		AssertUtils.notEmpty(testMode3.getName(), "testMode3.name is empty.");
           
        //FIXME:为添加的数据需要填入默认值的字段填入默认值
		testMode3.setLastUpdateDate(new Date());
		testMode3.setValid(true);
		testMode3.setCreateDate(new Date());
        
        //调用数据持久层对实例进行持久化操作
        this.testMode3Dao.insert(testMode3);
    }
    
    /**
     * 根据id删除测试对象实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param id
     * @return boolean 删除的条数>0则为true [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(Long id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TestMode3 condition = new TestMode3();
        condition.setId(id);
        
        int resInt = this.testMode3Dao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }

    /**
     * 根据code删除测试对象实例
     * 1、当code为empty时抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param code
     * @return TestMode3 [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public boolean deleteByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        TestMode3 condition = new TestMode3();
        condition.setCode(code);
        
        int resInt = this.testMode3Dao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据id查询测试对象实例
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TestMode3 [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public TestMode3 findById(Long id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TestMode3 condition = new TestMode3();
        condition.setId(id);
        
        TestMode3 res = this.testMode3Dao.find(condition);
        return res;
    }

    /**
     * 根据code查询测试对象实例
     * 1、当code为empty时抛出异常
     *
     * @param code
     * @return TestMode3 [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public TestMode3 findByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        TestMode3 condition = new TestMode3();
        condition.setCode(code);
        
        TestMode3 res = this.testMode3Dao.find(condition);
        return res;
    }
    
    /**
     * 查询测试对象实例列表
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode3> queryList(
		Boolean valid,
		Map<String,Object> params   
    	) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
		params.put("valid",valid);

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode3> resList = this.testMode3Dao.queryList(params);
        
        return resList;
    }
    
    /**
     * 查询测试对象实例列表
     * <功能详细描述>
     * @param valid
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode3> queryList(
		Boolean valid,
		Querier querier   
    	) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
		if (valid != null) {
            querier.getFilters().add(Filter.eq("valid", valid));
        }

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode3> resList = this.testMode3Dao.queryList(querier);
        
        return resList;
    }
    
    /**
     * 分页查询测试对象实例列表
     * <功能详细描述>
     * @param valid
     * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<TestMode3> queryPagedList(
		Boolean valid,
		Map<String,Object> params,
    	int pageIndex,
        int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
		params.put("valid",valid);
 
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TestMode3> resPagedList = this.testMode3Dao.queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
	/**
     * 分页查询测试对象实例列表
     * <功能详细描述>
     * @param valid
     * @param querier    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<TestMode3> queryPagedList(
		Boolean valid,
		Querier querier,
    	int pageIndex,
        int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
		if (valid != null) {
            querier.getFilters().add(Filter.eq("valid", valid));
        }
 
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TestMode3> resPagedList = this.testMode3Dao.queryPagedList(querier, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 查询测试对象实例数量<br/>
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(
		Boolean valid,
		Map<String,Object> params   
    	) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
		params.put("valid",valid);

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testMode3Dao.count(params);
        
        return res;
    }
    
    /**
     * 查询测试对象实例数量<br/>
     * <功能详细描述>
     * @param valid
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(
		Boolean valid,
		Querier querier   
    	) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
		if (valid != null) {
            querier.getFilters().add(Filter.eq("valid", valid));
        }

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testMode3Dao.count(querier);
        
        return res;
    }
    
    /**
     * 判断测试对象实例是否已经存在<br/>
     * <功能详细描述>
     * @param key2valueMap
     * @param excludeId
     * @return
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Map<String,String> key2valueMap, Long excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("excludeId", excludeId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testMode3Dao.count(params);
        
        return res > 0;
    }
    
    /**
     * 判断测试对象实例是否已经存在<br/>
     * <功能详细描述>
     * @param key2valueMap
     * @param excludeId
     * @return
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Querier querier, Long excludeId) {
        AssertUtils.notNull(querier, "querier is null.");
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testMode3Dao.count(querier,excludeId);
        
        return res > 0;
    }
    
    /**
     * 根据id更新测试对象实例<br/>
     * <功能详细描述>
     * @param testMode3
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(Long id,TestMode3 testMode3) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(testMode3, "testMode3 is null.");
        AssertUtils.notEmpty(id, "id is empty.");
		AssertUtils.notEmpty(testMode3.getName(), "testMode3.name is empty.");

        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        //FIXME:需要更新的字段
		updateRowMap.put("lastUpdateOperatorId", testMode3.getLastUpdateOperatorId());
		updateRowMap.put("name", testMode3.getName());
		updateRowMap.put("testInt", testMode3.getTestInt());
		updateRowMap.put("testLong", testMode3.getTestLong());
		updateRowMap.put("testBigDecimal", testMode3.getTestBigDecimal());
		updateRowMap.put("type", testMode3.getType());
		updateRowMap.put("valid", testMode3.isValid());
		updateRowMap.put("expiryDate", testMode3.getExpiryDate());
		updateRowMap.put("modifyAble", testMode3.isModifyAble());
		updateRowMap.put("parentId", testMode3.getParentId());
		updateRowMap.put("remark", testMode3.getRemark());
		updateRowMap.put("nested1", testMode3.getNested1());
		updateRowMap.put("nested2", testMode3.getNested2());
		updateRowMap.put("success", testMode3.getSuccess());
		updateRowMap.put("effictiveDate", testMode3.getEffictiveDate());
		updateRowMap.put("attributes", testMode3.getAttributes());
		updateRowMap.put("description", testMode3.getDescription());
		updateRowMap.put("lastUpdateDate", new Date());

        boolean flag = this.testMode3Dao.update(id,updateRowMap); 
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
    
    /**
     * 根据id更新测试对象实例<br/>
     * <功能详细描述>
     * @param testMode3
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(TestMode3 testMode3) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(testMode3, "testMode3 is null.");
        AssertUtils.notEmpty(testMode3.getId(), "testMode3.id is empty.");

        boolean flag = updateById(testMode3.getId(),testMode3); 
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }

    /**
     * 根据id禁用测试对象<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean disableById(Long id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("valid", false);
        
        boolean flag = this.testMode3Dao.update(params) > 0;
        
        return flag;
    }
    
    /**
     * 根据id启用测试对象<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean enableById(Long id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("valid", true);
        
        boolean flag = this.testMode3Dao.update(params) > 0;
        
        return flag;
    }

    /**
     * 根据parentId查询测试对象子级实例列表<br/>
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
    public List<TestMode3> queryChildrenByParentId(Long parentId,
			Boolean valid,
			Map<String,Object> params) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId,"parentId is empty.");
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("parentId", parentId);
		params.put("valid",valid);

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode3> resList = this.testMode3Dao.queryList(params);
        
        return resList;
    }
    
    /**
     * 根据parentId查询测试对象子级实例列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode3> queryChildrenByParentId(Long parentId,
			Boolean valid,
			Querier querier) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId,"parentId is empty.");
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
		if (valid != null) {
            querier.getFilters().add(Filter.eq("valid", valid));
        }
		querier.getFilters().add(Filter.eq("parentId", parentId));

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode3> resList = this.testMode3Dao.queryList(querier);
        
        return resList;
    }
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param params
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode3> queryDescendantsByParentId(Long parentId,
			Boolean valid,
            Map<String, Object> params) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId,"parentId is empty.");
        
        //生成查询条件
        Set<Long> ids = new HashSet<>();
        Set<Long> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<TestMode3> resList = doNestedQueryChildren(valid, ids, parentIds, params);
        return resList;
    }
    
    /**
     * 查询嵌套列表<br/>
     * <功能详细描述>
     * @param ids
     * @param parentIds
     * @param params
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<TestMode3> doNestedQueryChildren(
			Boolean valid,
    		Set<Long> ids,Set<Long> parentIds,Map<String, Object> params) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<TestMode3>();
        }
        
        //ids避免数据出错时导致无限循环
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(params);
        queryParams.put("parentIds", parentIds);
        List<TestMode3> resList = queryList(valid, params);
        
        Set<Long> newParentIds = new HashSet<>();
        for (TestMode3 bdTemp : resList) {
            if (!ids.contains(bdTemp.getId())) {
                newParentIds.add(bdTemp.getId());
            }
            ids.add(bdTemp.getId());
        }
        //嵌套查询下一层级
        resList.addAll(doNestedQueryChildren(valid, ids, newParentIds, params));
        return resList;
    }
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode3> queryDescendantsByParentId(Long parentId,
			Boolean valid,
            Querier querier) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId,"parentId is empty.");
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        Set<Long> ids = new HashSet<>();
        Set<Long> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<TestMode3> resList = doNestedQueryChildren(valid, ids, parentIds, querier);
        return resList;
    }
    
    /**
     * 嵌套查询列表<br/>
     * <功能详细描述>
     * @param ids
     * @param parentIds
     * @param querier
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<TestMode3> doNestedQueryChildren(
			Boolean valid,
    		Set<Long> ids,
    		Set<Long> parentIds,
    		Querier querier) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<TestMode3>();
        }
        
        //ids避免数据出错时导致无限循环
        Querier querierClone = (Querier)querier.clone();
        querierClone.getFilters().add(Filter.in("parentId", parentIds));
        List<TestMode3> resList = queryList(valid, querierClone);
        
        Set<Long> newParentIds = new HashSet<>();
        for (TestMode3 bdTemp : resList) {
            if (!ids.contains(bdTemp.getId())) {
                newParentIds.add(bdTemp.getId());
            }
            ids.add(bdTemp.getId());
        }
        //嵌套查询下一层级
        resList.addAll(doNestedQueryChildren(valid, ids, newParentIds, querier));
        return resList;
    }
}
